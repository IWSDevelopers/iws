/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Password
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
public final class Password extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The new Password for the user. This field is mandatory for both the
     * update Password and reset Password requests.
     */
    @XmlElement(required = true, nillable = false)
    private String newPassword = null;

    /**
     * Both a resetPassword & updatePassword request requires additional
     * information to properly identify the User who is performing the
     * Request.<br />
     *   For the Reset Request, this is the given Password Token. For the Update
     * Request, it is the existing Password. As it is not possible within this
     * Object to determine which variant is required, the identification field
     * will contain either the one or other.
     */
    @XmlElement(required = true, nillable = false)
    private String identification = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Password() {
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
     * Ensures that the given New Password is valid, and sets it. This value is
     * required for both the update Password and the reset Password
     * requests.<br />
     *   The IWS will only store a salted and hashed version of the Password
     * internally. Hence, the password will never be retrievable in clear-text
     * from the IWS.<br />
     *   The method will throw an {@code IllegalArgumentException} if the value
     * is either null, or too short. Since the value is never stored nor logged,
     * there are no other restrictions on it.<br />
     *
     * @param newPassword New Password for a user
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setNewPassword(final String newPassword) throws IllegalArgumentException {
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
     * Sets (parts of the) Identification information for the Request, which is
     * either the old User Password (Update Password Request) or Password Token
     * (Reset Password Request). The value must be set otherwise the request
     * will fail.<br />
     *   If the value is invalid, i.e. null or empty - then the method will
     * throw an {@code IllegalArgumentException}.
     *
     * @param identification Old User Password or given Password Token
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setIdentification(final String identification) throws IllegalArgumentException {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        // Passwords are very sensitive information, if someone were to log or
        // compare this Object with something else, we wish to convey that it is
        // a sensitive Object, hence standards methods are returning negative
        // information only
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        // Passwords are very sensitive information, if someone were to log or
        // compare this Object with something else, we wish to convey that it is
        // a sensitive Object, hence standards methods are returning negative
        // information only
        //   However, to avoid that multiple Password Objects is given the same
        // hashCode value, we're generating and returning a UUID.
        return UUID.randomUUID().hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // Passwords are very sensitive information, if someone were to log or
        // compare this Object with something else, we wish to convey that it is
        // a sensitive Object, hence standards methods are returning negative
        // information only
        return "Password{...}";
    }
}
