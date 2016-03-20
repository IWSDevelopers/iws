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
package net.iaeste.iws.common.notification;

/**
 * The same Objects can have many different types of Notifications, this will
 * help the Class determine, exactly which one is suppose to be generated.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public enum NotificationType {

    /**
     * If the Object only supports a single type of Notifications, then this
     * type should be used as the default.
     */
    GENERAL,

    /**
     * For updating a users username.
     */
    UPDATE_USERNAME,

    /**
     * For post user name update actions
     */
    USERNAME_UPDATED,

    /**
     * For Activating newly created User Account.
     */
    ACTIVATE_NEW_USER,

    /**
     * For Activating a suspended User Account.
     */
    ACTIVATE_SUSPENDED_USER,

    /**
     * For Suspending an Active User Account.
     */
    SUSPEND_ACTIVE_USER,

    /**
     * New user is being created
     */
    NEW_USER,

    /**
     * For sending of reset password requests.
     */
    RESET_PASSWORD,

    /**
     * For handling resetting Session requests.
     */
    RESET_SESSION,

    /**
     * When a new Group is created, the Owner should be informed about the new
     * Group, and additionally the mailinglist for the new Group should be
     * created.
     */
    NEW_GROUP,

    /**
     * Whenever there is a change in the membership of a Group, then the system
     * must be informed about it, so the mailing lists can be updated.
     */
    CHANGE_IN_GROUP_MEMBERS,

    /**
     * When the ownership of a Group is changing, the new owner should be
     * informed about this change.
     */
    NEW_GROUP_OWNER,

    PROCESS_EMAIL_ALIAS,

    PROCESS_MAILING_LIST,

    /**
     * When user access activation link and activation is successful,
     * some system setting has to be prepared
     */
    USER_ACTIVATED,

    NEW_STUDENT
}
