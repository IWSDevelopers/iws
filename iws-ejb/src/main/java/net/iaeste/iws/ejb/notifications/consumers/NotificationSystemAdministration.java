/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.consumers.NotificationSystemAdministration
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.notifications.consumers;

import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;
import net.iaeste.iws.persistence.views.NotificationJobTasksView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

/**
 * Notification consumer for administration the system (mailing list, aliases, etc.)
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection ObjectAllocationInLoop
 */
public class NotificationSystemAdministration implements Observer {
    private Long id = null;
    private static Integer ATTEMPTS_LIMIT = 3;

    private final NotificationDao dao;
    private final AccessDao accessDao;

    public NotificationSystemAdministration(final NotificationDao dao, final AccessDao accessDao) {
        this.dao = dao;
        this.accessDao = accessDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable subject) {
        processMessages();
    }

    private void processMessages() {
        final List<NotificationJobTasksView> jobTasks = dao.findUnprocessedNotificationJobTaskByConsumerId(id, ATTEMPTS_LIMIT);
        for (final NotificationJobTasksView jobTask : jobTasks) {
            try {
                final ByteArrayInputStream inputStream = new ByteArrayInputStream(jobTask.getObject());
                final ObjectInputStream objectStream = new ObjectInputStream(inputStream);
                final Map<NotificationField, String> fields = (Map<NotificationField, String>) objectStream.readObject();
                NotificationProcessTaskStatus processedStatus = NotificationProcessTaskStatus.ERROR;
                if (fields != null) {
                    processedStatus = processTask(fields, jobTask.getNotificationType());
                }
                boolean processed = (processedStatus != NotificationProcessTaskStatus.ERROR);
                dao.updateNotificationJobTask(jobTask.getId(), processed, jobTask.getattempts()+1);
            } catch (IOException|ClassNotFoundException ignored) {
                //TODO write to log and skip the task or throw an exception?
            }
        }
    }

    private NotificationProcessTaskStatus processTask(final Map<NotificationField, String> fields, final NotificationType type) {
        NotificationProcessTaskStatus ret = NotificationProcessTaskStatus.NOT_FOR_ME;
        switch (type) {
            case NEW_USER:
                prepareUserNotificationSetting(fields.get(NotificationField.EMAIL), type);
                ret = NotificationProcessTaskStatus.OK;
                break;
        }
        return ret;
    }

    private void prepareUserNotificationSetting(final String username, final NotificationType type) {
        final UserEntity user = accessDao.findUserByUsername(username);
        if (user != null) {
            final UserNotificationEntity userNotification = new UserNotificationEntity(user, NotificationType.ACTIVATE_USER, NotificationFrequency.IMMEDIATELY);
            dao.persist(userNotification);
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }
}
