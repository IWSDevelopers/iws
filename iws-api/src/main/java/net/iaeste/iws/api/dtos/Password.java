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
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.api.util.StandardMethods;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * This Object is used for the requests, where a user needs to change the
 * Passwords, i.e. as a follow-up action when the forgot password was invoked,
 * or just generally wishing to change the password.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "password", propOrder = { "newPassword", "identification" })
public final class Password extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The new Password for the user. This field is mandatory for both the
     * update Password and reset Password requests.
     */
    @XmlElement(required = true)
    @StandardMethods(StandardMethods.For.NONE)
    private String newPassword = null;

    /**
     * <p>Both a resetPassword &amp; updatePassword request requires additional
     * information to properly identify the User who is performing the
     * Request.</p>
     *
     * <p>For the Reset Request, this is the given Password Token. For the
     * Update Request, it is the existing Password. As it is not possible within
     * this Object to determine which variant is required, the identification
     * field will contain either the one or other.</p>
     */
    @XmlElement(required = true)
    @StandardMethods(StandardMethods.For.NONE)
    private String identification = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Password() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor for the update Password request.
     *
     * @param newPassword New Password for the user
     * @param identification Old User Password or Password Token
     */
    public Password(final String newPassword, final String identification) {
        setNewPassword(newPassword);
        setIdentification(identification);
    }

    /**
     * Copy Constructor.
     *
     * @param password Password Object to copy
     */
    public Password(final Password password) {
        if (password != null) {
            this.newPassword = password.newPassword;
            this.identification = password.identification;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Ensures that the given New Password is valid, and sets it. This value
     * is required for both the update Password and the reset Password
     * requests.</p>
     *
     * <p>The IWS will only store a salted and hashed version of the Password
     * internally. Hence, the password will never be retrievable in clear-text
     * from the IWS.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the value
     * is either null, or too short. Since the value is never stored nor logged,
     * there are no other restrictions on it.</p>
     *
     * @param newPassword New Password for a user
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setNewPassword(final String newPassword) {
        ensureNotNullOrTooShort("newPassword", newPassword, IWSConstants.MINIMAL_PASSWORD_LENGTH);
        this.newPassword = newPassword;
    }

    /**
     * Retrieves the New Password, required for both the update Password and the
     * reset Password requests.
     *
     * @return New Password for a user
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * <p>Sets (parts of the) Identification information for the Request, which
     * is either the old User Password (Update Password Request) or Password
     * Token (Reset Password Request). The value must be set otherwise the
     * request will fail.</p>
     *
     * <p>If the value is invalid, i.e. null or empty - then the method will
     * throw an {@code IllegalArgumentException}.</p>
     *
     * @param identification Old User Password or given Password Token
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setIdentification(final String identification) {
        ensureNotNullOrEmpty("identification", identification);
        this.identification = identification;
    }

    public String getIdentification() {
        return identification;
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        // As the Setters are verifying all given values, we only
        // need to run checks against nonnull fields here.
        isNotNull(validation, "newPassword", newPassword);
        isNotNull(validation, "identification", identification);

        return validation;
    }
}
