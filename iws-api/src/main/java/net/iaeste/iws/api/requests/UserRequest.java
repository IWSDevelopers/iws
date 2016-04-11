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
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.util.Verifications;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Request Object for altering a User Account in the system. With this
 * Object, it is possible to block an active Account or re-activate a blocked
 * Account, and even delete an Account.</p>
 *
 * <p><i>Note;</i> deletion is a non-reversible action.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userRequest", propOrder = { "user", "newStatus", "newUsername", "newPassword", "password" })
public final class UserRequest extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private User user = null;
    @XmlElement(required = true, nillable = true)  private UserStatus newStatus = null;
    @XmlElement(required = true, nillable = true)  private String newUsername = null;
    @XmlElement(required = true, nillable = true)  private String newPassword = null;
    @XmlElement(required = true, nillable = true)  private String password = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public UserRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * <p>Default Constructor.</p>
     *
     * @param user User Object to process
     */
    public UserRequest(final User user) {
        this.user = user;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUser(final User user) {
        ensureNotNullAndVerifiable("user", user);
        this.user = new User(user);
    }

    public User getUser() {
        return new User(user);
    }

    /**
     * <p>If the user is should change status, then the new status must be
     * defined here. It is possible for someone with the rights to control a
     * user account, to activate, deactivate &amp; delete accounts. However, a
     * user may not perform the status change operations on themselves.</p>
     *
     * <p>The only exception for users, regarding the status change rule, is
     * that a user must activate an account.</p>
     *
     * @param newStatus New Status value for a user
     */
    public void setNewStatus(final UserStatus newStatus) {
        ensureNotNull("newStatus", newStatus);
        this.newStatus = newStatus;
    }

    public UserStatus getNewStatus() {
        return newStatus;
    }

    /**
     * <p>If a user must change their username, i.e. the registered e-mail
     * address, then this value must be set here. This change can be invoked
     * both by users and administrators. The change will not be directly
     * updated, but only marked, until the user has approved the change with a
     * code that is being sent.</p>
     *
     * <p>Note, that if a user wishes to update his or her username, then they
     * must also provide their password. Otherwise, they will get an error from
     * the system.</p>
     *
     * @param newUsername New username (e-mail address) for the user
     */
    public void setNewUsername(final String newUsername) {
        ensureNotNullAndValidEmail("newUsername", newUsername);
        this.newUsername = newUsername;
    }

    public String getNewUsername() {
        return newUsername;
    }

    /**
     * <p>If a user wishes to update the current password, then the new password
     * must be provided, together with the existing for verification.</p>
     *
     * <p>The new Password must follow the internal regular expression for
     * Passwords, otherwise an {@code java.lang.IllegalArgumentException} is
     * thrown.</p>
     *
     * <p>Note, the error message from the Exception will not display the new
     * Password, as this will potentially be logged somewhere, which could be a
     * serious problem for the user.</p>
     *
     * @param newPassword New Password for the user
     * @see IWSConstants#PASSWORD_REGEX
     */
    public void setNewPassword(final String newPassword) {
        ensureNotNullAndFollowRegex("newPassword", newPassword, IWSConstants.PASSWORD_PATTERN, IWSConstants.PASSWORD_REGEX);
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    /**
     * <p>If a user wishes to update the current username, then the password
     * must also be provided, to have an authentication mechanism - this will
     * prevent that someone abuses a currently active session.</p>
     *
     * <p>The password is not needed, if an administrator initiates the
     * update.</p>
     *
     * @param password Current Password, to authenticate user
     */
    public void setPassword(final String password) {
        ensureNotNullOrEmpty("password", password);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(1);

        isNotNull(validation, "user", user);

        return validation;
    }
}
