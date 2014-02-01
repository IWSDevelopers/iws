/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.notifications.Notifications
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.notifications;

import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationType;

/**
 * Classes to be observed, should extend the Notifiable interface.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Notifications extends Observable {

    /**
     * For (almost) all notifications. this method should be invoked, since it
     * will ensure that the correct notification is persisted and send to the
     * notification queue.
     *
     * @param authentication Authentication information (user + group)
     * @param obj            Instance to notify about changes for
     * @param type           Type of Notification Message to send
     */
    void notify(Authentication authentication, Notifiable obj, NotificationType type);

    /**
     * For the Forgot password functionality, we only have the {@code UserEntity}
     * Object at hand.
     *
     * @param user {@code UserEntity} Object
     */
    void notify(UserEntity user);

    /**
     * Notify methods prepare jobs to be processed. The processing is invoked by this method
     * and creates tasks for consumers. It also trigger consumers
     */
    void processJobs();
}
