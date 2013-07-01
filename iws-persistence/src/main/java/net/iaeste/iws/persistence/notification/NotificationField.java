/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.notification.NotificationField
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

/**
 * For all notifications, we need to have some defined values that can be used
 * when generating the Notification to be send. This enum contains the different
 * fields that can be used.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public enum NotificationField {

    /**
     * The code is a value that is used as an alternative to the Session Token,
     * when it is important to uniquely identify a user. The field is used for
     * the following operations:
     * <ul>
     *   <li>Activate User Account</li>
     *   <li>Reset (Forgot) Password</li>
     *   <li>Reset Session</li>
     * </ul>
     */
    CODE,

    /**
     * The username (e-mail address) for a person, required for sending the
     * e-mails.
     */
    USERNAME,

    /**
     * For updating a users username (e-mail address), required for sending the
     * update e-mail with code for changing.
     */
    NEW_USERNAME,

    /**
     * As accounts can be generated without a password, it must be communicated
     * to the user, this field then contains the unencrypted password for a
     * user.
     */
    CLEARTEXT_PASSWORD,

    /**
     * A Users firstname.
     */
    FIRSTNAME,

    /**
     * A Users lastname.
     */
    LASTNAME
}
