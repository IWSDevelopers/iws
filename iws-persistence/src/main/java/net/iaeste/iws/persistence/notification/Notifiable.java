/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.notification.Notifiable
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.notification;

import net.iaeste.iws.api.enums.NotificationSubject;
import net.iaeste.iws.persistence.entities.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * Please note, that the current information here is very much incomplete. A
 * possible solution would be to use a standard Templating Engine for the
 * information, to be send. This way, the method themselves just have to refer
 * to the name of the Template that holds the required information, and by
 * combining the Object with the Template, it should be possible to generate it
 * all.
 *   Example for Templating Engine: Velocity, Freemaker
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface Notifiable {

    /**
     * Notifications are templates that needs processing before being sent of.
     * This method will prepare the required fields for a given Notification
     * Type. Since not all types of notifications need the same fields, it is
     * required that only the necessary fields for a given Notification Type
     * should be present.
     *
     * @param type Notification Type
     * @return Map with required fields for the given Notification Type
     */
    Map<NotificationField, String> prepareNotifiableFields(NotificationType type);

    /**
     * The Notification relies on a message being sent in some sort of format.
     * However, this is not yet clarified exactly how it should be done. So
     * for now - this is just a simple placeholder. So we at least can test
     * those parts of the System, that relies on Notifications as part of the
     * flow, i.e. Create User Account, Forgot Password, etc.
     *
     * @return Simple Message
     * @deprecated as we should rather use the #prepareNotifiableFields instead
     */
    @Deprecated
    String generateNotificationMessage(NotificationType type);

    /**
     *
     * @return Subject
     * @deprecated since the Subject should be clear from the context (type)
     */
    @Deprecated
    NotificationSubject getNotificationSubject();

    /**
     *
     * @return List of Users
     * @deprecated since the Objects should not care for the recipients
     */
    @Deprecated
    List<UserEntity> getRecipients();
}
