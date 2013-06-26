/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.notification.Notifications
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

import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.UserEntity;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public interface Notifications extends Observable {

    /**
     * Classes to be observed, should extend the Notifiable interface.
     *
     * @param authentication Authentication information (user + group)
     * @param obj            Instance to notify about changes for
     * @param type           Type of Notification Message to send
     * @param delayed        Is the sending of the notification delayed (true) or immediately (false)
     */
    void notify(Authentication authentication, Notifiable obj, NotificationType type, boolean delayed);

    /**
     * For the Forgot password functionality, we only hav a {@code UserEntity}
     * Object at hand.
     *
     * @param user {@code UserEntity} Object
     */
    void notify(UserEntity user);
}
