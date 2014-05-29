/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.notification.NotificationField
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.common.notification;

/**
 * For all notifications, we need to have some defined values that can be used
 * when generating the Notification to be send. This enum contains the different
 * fields that can be used.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
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
     * The e-mail address (username), required for sending the e-mails.
     */
    EMAIL,

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
    LASTNAME,

    /**
     * The name of the Group that has been either Created or have had a change
     * in the members.
     */
    GROUP_NAME,

    /**
     * The GroupType of the Group that has been either Created or have had a
     * change in the members.
     */
    GROUP_TYPE,

    /**
     * The mailing alias of the Group that
     */
    GROUP_LIST_NAME,

    /**
     * The name of the Role, that a user has been assigned in a Group.
     */
    ROLE,

    /**
     * If a user is on a groups public mailinglist or not.
     */
    ON_PUBLIC_LIST,

    /**
     * If a user is on a groups private mailinglist or not.
     */
    ON_PRIVATE_LIST,

    /**
     * If a user may send messages to the privage mailing list or not.
     */
    WRITE_PRIVATE_LIST,

    /**
     * The name of the Country that the Group which has been created or has a change
     * in the members belong to.
     */
    COUNTRY_NAME,

    /**
     * External ID of the group
     */
    GROUP_EXTERNAL_ID,

    USER_STATUS
}
