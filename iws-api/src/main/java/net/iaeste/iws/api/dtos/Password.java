/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
import java.util.UUID;

/**
 * This Object is used for the requests, where a user needs to change the
 * Passwords, i.e. as a follow-up action when the forgot password was invoked,
 * or just generally wishing to change the password.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection MethodReturnAlwaysConstant
 */
public final class Password extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The actual password, stored as a plaintext value. */
    private String password = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Password() {
    }

    /**
     * Default Constructor.
     *
     * @param password New Password for the user
     */
    public Password(final String password) {
        setPassword(password);
    }

    /**
     * Copy Constructor.
     *
     * @param password Password Object to copy
     */
    public Password(final Password password) {
        if (password != null) {
            this.password = password.password;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Ensures that the given Password is valid, and sets it.
     *
     * @param password New Password for a user
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setPassword(final String password) throws IllegalArgumentException {
        ensureNotNullOrTooShort("password", password, IWSConstants.MINIMAL_PASSWORD_LENGTH);

        this.password = password;
    }

    /**
     * Retrieves the internal Password value.
     *
     * @return New Password for a user
     */
    public String getPassword() {
        return password;
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
        isNotNull(validation, "password", password);

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
        return "Password{password='xxxxxxxx'}";
    }
}
