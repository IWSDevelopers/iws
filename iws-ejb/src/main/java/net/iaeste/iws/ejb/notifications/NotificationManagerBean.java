/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.ejb.notifications;

import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationConsumerEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationJobEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationJobTaskEntity;
import net.iaeste.iws.persistence.jpa.NotificationJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

/**
 * Notes; It is good to see the notification system evolving. There is just a
 * couple of things that has to be clarified. One is that the actual handling
 * of sending, should be done in different ways. If users request that
 * information is send at specific intervals, daily, weekly, etc. then the
 * handling should be done via a "cron" job. That is a Timer job in EJB, or
 * perhaps via an external queuing system like Quartz.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotificationManagerBean implements Notifications {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationManagerBean.class);

    private final List<Observer> observers = new ArrayList<>(10);

    private final EntityManager entityManager;
    private final Settings settings;
    private final NotificationDao dao;
    private final boolean hostedInBean;

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager
     * @param settings      IWS Settings
     * @param hostedInBean  If Hosted in Bean
     */
    public NotificationManagerBean(final EntityManager entityManager, final Settings settings, final boolean hostedInBean) {
        this.entityManager = entityManager;
        this.settings = settings;
        this.hostedInBean = hostedInBean;

        dao = new NotificationJpaDao(entityManager, settings);
    }

    /**
     * Startup notification manager - load from DB registered notification
     * consumers and subscribe them to the manager.
     */
    public final void startupConsumers() {
        final List<NotificationConsumerEntity> consumers = dao.findActiveNotificationConsumers();
        final NotificationConsumerClassLoader classLoader = new NotificationConsumerClassLoader();

        for (final NotificationConsumerEntity consumer : consumers) {
            final Observer observer = classLoader.findConsumerClass(consumer.getClassName(), entityManager, settings);
            observer.setId(consumer.getId());
            addObserver(observer);
        }
    }

    // =========================================================================
    // Implementation of the Notifications Interface
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public final void notify(final Authentication authentication, final Notifiable obj, final NotificationType type) {
        LOG.info("New '{}' notification request at NotificationManager", type);

        if (obj != null) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 ObjectOutputStream objectStream = new ObjectOutputStream(outputStream)) {
                objectStream.writeObject(prepareNotification(obj, type));
                final byte[] bytes = outputStream.toByteArray();
                final NotificationJobEntity job = new NotificationJobEntity();
                job.setNotificationType(type);
                job.setObject(bytes);
                dao.persist(job);
                LOG.info("New notification job for '{}' created", type);
                if (!hostedInBean) {
                    processJobs();
                }
            } catch (IWSException e) {
                LOG.error("Preparing notification job failed: {}", e.getMessage(), e);
            } catch (IOException e) {
                LOG.warn("Serializing of Notifiable instance for NotificationType {} failed: {}", type, e.getMessage(), e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void notify(final UserEntity user) {
        LOG.info("New 'user' notification request at NotificationManager");

        if (user != null) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 ObjectOutputStream objectStream = new ObjectOutputStream(outputStream)) {
                objectStream.writeObject(prepareNotification(user, NotificationType.RESET_PASSWORD));
                final byte[] bytes = outputStream.toByteArray();
                final NotificationJobEntity job = new NotificationJobEntity();
                job.setNotificationType(NotificationType.RESET_PASSWORD);
                job.setObject(bytes);
                dao.persist(job);
                LOG.info("New notification job for '{}' created", NotificationType.RESET_PASSWORD);
                if (!hostedInBean) {
                    processJobs();
                }
            } catch (IWSException e) {
                LOG.error("Preparing notification job failed: {}", e.getMessage(), e);
            } catch (IOException e) {
                LOG.warn("Serializing of Notifiable instance for NotificationType.RESET_PASSWORD failed: {}", e.getMessage(), e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public final void processJobs() {
        final Date now = new Date();
        final List<NotificationJobEntity> unprocessedJobs = dao.findUnprocessedNotificationJobs(now);
        if (!unprocessedJobs.isEmpty()) {
            for (final NotificationJobEntity job : unprocessedJobs) {
                prepareJobTasks(job);

                job.setNotified(true);
                job.setModified(new Date());
                dao.persist(job);
            }
        }

        notifyObservers();
    }

    private void prepareJobTasks(final NotificationJobEntity job) {
        for (final Observer observer : observers) {
            final NotificationConsumerEntity consumer = dao.findNotificationConsumerById(observer.getId());
            final NotificationJobTaskEntity task = new NotificationJobTaskEntity();
            task.setJob(job);
            task.setConsumer(consumer);
            dao.persist(task);
        }
    }

    // =========================================================================
    // Implementation of the Observable Interface
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addObserver(final Observer observer) {
        observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void deleteObserver(final Observer observer) {
        observers.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void notifyObservers() {
        for (final Observer observer : observers) {
            observer.update(this);
        }
    }

    // =========================================================================
    // Internal methods
    // =========================================================================

    private static EnumMap<NotificationField, String> prepareNotification(final Notifiable notifiable, final NotificationType type) {
        final EnumMap<NotificationField, String> map = new EnumMap<>(NotificationField.class);
        map.putAll(notifiable.prepareNotifiableFields(type));

        return map;
    }
}
