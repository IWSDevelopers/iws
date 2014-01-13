/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;

/**
 * This Object is used for the requests, where a user needs to change the
 * Passwords, i.e. as a follow-up action when the forgot password was invoked,
 * or just generally wishing to change the password.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Password extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The new Password for the user. This field is mandatory for both the
     * update Password and reset Password requests.
     */
    private String newPassword = null;

    /**
     * When updating the Password, it is important that the old Password is also
     * set, since anyone gaining access to an open Session may attempt to hijack
     * it.<br />
     *   Note, this field is only used when invoking the update Password, if the
     * forgot password functionality was used, then it is a different matter.
     */
    private String oldPassword = null;

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
     * Default Constructor for the reset Password request.
     *
     * @param newPassword New Password for the user
     */
    public Password(final String newPassword) {
        setNewPassword(newPassword);
    }

    /**
     * Default Constructor for the update Password request.
     *
     * @param newPassword New Password for the user
     * @param oldPassword Old Password for the user
     */
    public Password(final String newPassword, final String oldPassword) {
        setNewPassword(newPassword);
        setOldPassword(oldPassword);
    }

    /**
     * Copy Constructor.
     *
     * @param password Password Object to copy
     */
    public Password(final Password password) {
        if (password != null) {
            this.newPassword = password.newPassword;
            this.oldPassword = password.oldPassword;
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
     * Ensures that the given Old Password is valid, and sets it. This value is
     * required for the update Password request.
     *
     * @param oldPassword Old Password for a user
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setOldPassword(final String oldPassword) throws IllegalArgumentException {
        ensureNotNull("oldPassword", oldPassword);
        this.oldPassword = oldPassword;
    }

    /**
     * Retrieves the Old Password, required for the update Password request.
     *
     * @return Old Password for a user
     */
    public String getOldPassword() {
        return oldPassword;
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
        // need to run checks against nonnull fields here
        isNotNull(validation, "newPassword", newPassword);

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
        return 0;
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
        return "Password{password='xxxxxxxx'}";
    }
}
