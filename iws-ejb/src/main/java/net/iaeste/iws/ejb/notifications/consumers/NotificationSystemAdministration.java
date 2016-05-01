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
package net.iaeste.iws.ejb.notifications.consumers;

import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.NotificationJpaDao;
import net.iaeste.iws.persistence.views.NotificationJobTasksView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Notification consumer for administration the system.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class NotificationSystemAdministration implements Observer {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationSystemAdministration.class);

    private Long id = null;
    private static final Integer ATTEMPTS_LIMIT = 3;
    private boolean initialized = false;

    private AccessDao accessDao = null;
    private NotificationDao notificationDao = null;

    // =========================================================================
    // Observer Implementation
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final EntityManager entityManager, final Settings settings) {
        this.accessDao = new AccessJpaDao(entityManager, settings);
        this.notificationDao = new NotificationJpaDao(entityManager, settings);

        initialized = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable subject) {
        if (initialized) {
            try {
                final List<NotificationJobTasksView> jobTasks = notificationDao.findUnprocessedNotificationJobTaskByConsumerId(id, ATTEMPTS_LIMIT);
                for (final NotificationJobTasksView jobTask : jobTasks) {
                    LOG.info("Processing system notification job task {}", jobTask.getId());
                    processTask(jobTask);
                }
            } catch (IWSException e) {
                LOG.error("IWS error occurred: {}.", e.getMessage(), e);
            } catch (RuntimeException e) {
                // Catching all other Exceptions to prevent stopping
                // notification processing and leaving error message in log
                LOG.error("System error occurred: {}.", e.getMessage(), e);
            }
        } else {
            LOG.warn("Update called for uninitialized observer.");
        }
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private NotificationProcessTaskStatus processTask(final Map<NotificationField, String> fields, final NotificationType type) {
        final NotificationProcessTaskStatus status;

        switch (type) {
            case NEW_USER:
                status = prepareNewUserNotificationSetting(fields.get(NotificationField.EMAIL));
                break;
            case USER_ACTIVATED:
                status = prepareActivatedUser(fields.get(NotificationField.EMAIL));
                break;
            default:
                // For all other cases
                status = NotificationProcessTaskStatus.NOT_FOR_ME;
        }

        return status;
    }

    private void processTask(final NotificationJobTasksView task) {
        if ((task != null) && (task.getObject() != null)) {
            LOG.info("Processing system notification job task {}.", task.getId());

            try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(task.getObject());
                 final ObjectInputStream objectStream = new ObjectInputStream(inputStream)) {

                final Map<NotificationField, String> fields = (Map<NotificationField, String>) objectStream.readObject();
                NotificationProcessTaskStatus processedStatus = NotificationProcessTaskStatus.ERROR;
                //TODO task is not processed, so value false is hardcoded for now, should be changed or deleted once problems are solved
                notificationDao.updateNotificationJobTask(task.getId(), false, task.getAttempts() + 1);
                if (fields != null) {
                    processedStatus = processTask(fields, task.getNotificationType());
                }
                final boolean processed = processedStatus != NotificationProcessTaskStatus.ERROR;
                LOG.info("Notification job task {} attempt number is going to be updated to {}, processed set to {}", task.getId(), task.getAttempts() + 1, processed);
                notificationDao.updateNotificationJobTask(task.getId(), processed, task.getAttempts() + 1);
                LOG.info("Notification job task {} was updated", task.getId());
            } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
                LOG.info("Notification job task {} failed, task is going to be updated to {}, processed set to false.", task.getId(), task.getAttempts() + 1);
                notificationDao.updateNotificationJobTask(task.getId(), false, task.getAttempts() + 1);
                LOG.info("Notification job task {} was updated", task.getId());
                LOG.error(e.getMessage(), e);
            } catch (IWSException e) {
                //prevent throwing IWSException out, it stops the timer to run this processing
                notificationDao.updateNotificationJobTask(task.getId(), false, task.getAttempts() + 1);
                LOG.error("Error during notification processing.", e);
            }
        } else {
            if (task != null) {
                LOG.error("Processing of the {} which contains no Object, cannot be completed.", task);
            } else {
                LOG.error("Processing of a NULL task will not work.");
            }
        }
    }

    private NotificationProcessTaskStatus prepareNewUserNotificationSetting(final String username) {
        final UserEntity user = accessDao.findUserByUsername(username);
        final NotificationProcessTaskStatus status;

        if (user != null) {
            LOG.info("User {} to prepare {} notification for found.", user.getId(), NotificationType.ACTIVATE_NEW_USER);
            final UserNotificationEntity userNotification = notificationDao.findUserNotificationSetting(user, NotificationType.ACTIVATE_NEW_USER);

            if (userNotification == null) {
                final UserNotificationEntity entity = new UserNotificationEntity(user, NotificationType.ACTIVATE_NEW_USER, NotificationFrequency.IMMEDIATELY);
                notificationDao.persist(entity);
                LOG.info("Notification setting {} for user {} created.", NotificationType.ACTIVATE_NEW_USER, user.getId());
            }

            status = NotificationProcessTaskStatus.OK;
        } else {
            LOG.warn("User {} to prepare notification for was not found.", username);
            status = NotificationProcessTaskStatus.ERROR;
        }

        return status;
    }

    private NotificationProcessTaskStatus prepareActivatedUser(final String username) {
        final UserEntity user = accessDao.findActiveUserByUsername(username);
        final NotificationProcessTaskStatus status;

        if (user != null) {
            LOG.info("Activated user {} was found", user.getId());
            status = prepareActivatedUserNotificationSetting(user);
        }
        else {
            LOG.warn("Activated user {} was not found", username);
            status = NotificationProcessTaskStatus.ERROR;
        }

        return status;
    }

    private NotificationProcessTaskStatus prepareActivatedUserNotificationSetting(final UserEntity user) {
        final Set<NotificationType> notificationTypes = EnumSet.noneOf(NotificationType.class);
        notificationTypes.add(NotificationType.UPDATE_USERNAME);
        notificationTypes.add(NotificationType.RESET_PASSWORD);
        notificationTypes.add(NotificationType.RESET_SESSION);
        notificationTypes.add(NotificationType.NEW_GROUP_OWNER);
        final NotificationProcessTaskStatus status;

        if (user != null) {
            for (final NotificationType notificationType : notificationTypes) {
                LOG.info("Setting {} for user {}.", notificationType, user.getId());

                if (notificationDao.findUserNotificationSetting(user, notificationType) == null) {
                    final UserNotificationEntity entity = new UserNotificationEntity(user, notificationType, NotificationFrequency.IMMEDIATELY);
                    notificationDao.persist(entity);
                    LOG.info("Setting {} for user {} created.", notificationType, user.getId());
                } else {
                    LOG.info("Setting {} for user {} exists already.", notificationType, user.getId());
                }
            }
            status = NotificationProcessTaskStatus.OK;
        } else {
            LOG.warn("No user to set system notification for.");
            status = NotificationProcessTaskStatus.ERROR;
        }

        return status;
    }
}
