/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import net.iaeste.iws.api.enums.NotificationMessageStatus;
import net.iaeste.iws.api.enums.NotificationDeliveryMode;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.entities.notifications.NotificationConsumerEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationJobEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationMessageEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;
import net.iaeste.iws.persistence.views.NotificationJobTasksView;

import java.util.Date;
import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
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
     * Finds all NotificationMessageEntity for selected notification channel
     * with specific status and processing date
     *
     * @param deliveryMode   Type of notification channel
     * @param status Status of the message (i.e. new, processing, ...)
     * @param date   Date when to process the message
     * @return       List of NotificationMessageEntity
     */
    List<NotificationMessageEntity> findNotificationMessages(NotificationDeliveryMode deliveryMode, NotificationMessageStatus status, Date date);

    /**
     * Updates the notification message to the new specified status
     *
     * @param message The message to be updated
     * @param status  New status of the message (i.e. new, processing, ...)
     */
    void updateNotificationMessageStatus(NotificationMessageEntity message, NotificationMessageStatus status);

    /**
     * Finds all NotificationConsumerEntities that are set as active
     *
     * @return List of NotificationConsumerEntity
     */
    List<NotificationConsumerEntity> findActiveNotificationConsumers();

    /**
     * Finds all NotificationJobEntities that are set as processed=false
     *
     * @return List of NotificationJobEntity
     */
    List<NotificationJobEntity> findUnprocessedNotificationJobs();

    /**
     * Finds NotificationConsumerEntity by Id
     *
     * @param id      The consumer id
     * @return        NotificationConsumerEntity
     */
    NotificationConsumerEntity findNotificationConsumerById(final Long id);

    /**
     * Finds NotificationJobTaskEntity by ConsumerId
     *
     * @param consumerId    The consumer id
     * @param attemptsLimit Number of attempts when the processing failed, if the value is larger than limit, ignor the task
     * @return            NotificationJobTaskEntity
     */
    List<NotificationJobTasksView> findUnprocessedNotificationJobTaskByConsumerId(final Long consumerId, final Integer attemptsLimit);

    /**
     * Updates NotificationJobTask entity
     *
     * @param id         NotificaitonJobTask id
     * @param processed  flag processed/unprocessed
     * @param attempts   number of processing attempts
     */
    void updateNotificationJobTask(final Long id, boolean processed, final Integer attempts);
}
