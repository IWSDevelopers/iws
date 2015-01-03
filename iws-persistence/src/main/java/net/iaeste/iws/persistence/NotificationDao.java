/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.NotificationDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence;

import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.entities.notifications.NotificationConsumerEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationJobEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;
import net.iaeste.iws.persistence.views.NotificationJobTasksView;

import java.util.Date;
import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface NotificationDao extends BasicDao {

    /**
     * Finds UserNotificationEntity (e.g. user's setting) for specified subject
     * (i.e. event, offer, user, ...)
     *
     * @param user    The User to receive the notification
     * @param type    Type of notification
     * @return        UserNotificationEntity, null if no setting is found, exception if more than 1 setting is found
     */
    UserNotificationEntity findUserNotificationSetting(UserEntity user, NotificationType type);

    /**
     * Finds all NotificationConsumerEntities that are set as active
     *
     * @return List of NotificationConsumerEntity
     */
    List<NotificationConsumerEntity> findActiveNotificationConsumers();

    /**
     * Finds all NotificationJobEntities that are set as processed=false
     *
     * @param date Date how old Jobs will be retrieved
     * @return List of NotificationJobEntity
     */
    List<NotificationJobEntity> findUnprocessedNotificationJobs(Date date);

    /**
     * Finds NotificationConsumerEntity by Id
     *
     * @param id      The consumer id
     * @return        NotificationConsumerEntity
     */
    NotificationConsumerEntity findNotificationConsumerById(Long id);

    /**
     * Finds NotificationJobTaskEntity by ConsumerId
     *
     * @param consumerId    The consumer id
     * @param attemptsLimit Number of attempts when the processing failed, if the value is larger than limit, ignor the task
     * @return            NotificationJobTaskEntity
     */
    List<NotificationJobTasksView> findUnprocessedNotificationJobTaskByConsumerId(Long consumerId, Integer attemptsLimit);

    /**
     * Updates NotificationJobTask entity
     *
     * @param id         NotificaitonJobTask id
     * @param processed  flag processed/unprocessed
     * @param attempts   number of processing attempts
     */
    void updateNotificationJobTask(Long id, boolean processed, Integer attempts);
}
