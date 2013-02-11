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
import net.iaeste.iws.api.enums.NotificationSubject;
import net.iaeste.iws.api.enums.NotificationType;
import net.iaeste.iws.persistence.entities.NotificationMessageEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;

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
     * @param subject Subject of the notification
     * @return        UserNotificationEntity
     */
    UserNotificationEntity findUserNotificationSetting(UserEntity user, NotificationSubject subject);

    /**
     * Finds all NotificationMessageEntity for selected notification channel
     * with specific status and processing date
     *
     * @param type   Type of notification channel
     * @param status Status of the message (i.e. new, processing, ...)
     * @param date   Date when to process the message
     * @return       List of NotificationMessageEntity
     */
    List<NotificationMessageEntity> findNotificationMessages(NotificationType type, NotificationMessageStatus status, Date date);

    /**
     * Updates the notification message to the new specified status
     *
     * @param message The message to be updated
     * @param status  New status of the message (i.e. new, processing, ...)
     */
    void updateNotificationMessageStatus(NotificationMessageEntity message, NotificationMessageStatus status);
}
