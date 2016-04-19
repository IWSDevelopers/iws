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
package net.iaeste.iws.ejb.schedulers;

import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.NotificationManager;
import net.iaeste.iws.ejb.cdi.IWSBean;
import net.iaeste.iws.ejb.notifications.NotificationManagerBean;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Singleton
@Local(NotificationManager.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotificationManagerScheduler implements NotificationManager {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationManagerScheduler.class);
    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Notifications notifications;
    @Inject @IWSBean private Settings settings;

    @PostConstruct
    public void postConstruct() {
        final NotificationManagerBean notificationManager = new NotificationManagerBean(entityManager, settings, true);
        notificationManager.startupConsumers();
        notifications = notificationManager;
    }

    @Schedule(second = "*/30",minute = "*", hour = "*", info="Every 30 seconds", persistent = false)
    public void processJobsScheduled() {
        LOG.trace("processJobsScheduled started at {}", new DateTime());
        try {
            notifications.processJobs();
        } catch (RuntimeException e) {
            LOG.error("Exception caught in notification processing", e);
        }
    }

    // =========================================================================
    // Implementation of the NotificationManagerLocal Interface
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNotifications(final Notifications notifications) {
        this.notifications = notifications;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Notifications getNotifications() {
        return notifications;
    }

    // =========================================================================
    // Implementation of the Notifications Interface
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final Authentication authentication, final Notifiable obj, final NotificationType type) {
        LOG.info("New '{}' notification request at NotificationManagerBean", type);
        try {
            notifications.notify(authentication, obj, type);
        } catch (IWSException e) {
            LOG.error("Preparing notification failed", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final UserEntity user) {
        LOG.info("New 'user' notification request at NotificationManagerBean");
        try {
            notifications.notify(user);
        } catch (IWSException e) {
            LOG.error("Preparing notification failed", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processJobs() {
        notifications.processJobs();
    }

    // =========================================================================
    // Implementation of the Observable Interface
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final Observer observer) {
        notifications.addObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteObserver(final Observer observer) {
        notifications.deleteObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers() {
        notifications.notifyObservers();
    }
}
