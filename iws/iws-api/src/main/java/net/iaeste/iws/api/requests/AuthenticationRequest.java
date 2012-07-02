/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.AuthenticationRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.exceptions.VerificationException;

/**
 * @author  Kim Jensen
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection SuppressionAnnotation
 */
public final class AuthenticationRequest extends AbstractRequest {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    // User Authentication information, i.e. plaintext username & password
    private String username;
    private String password;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public AuthenticationRequest() {
        username = null;
        password = null;
    }

    /**
     * Default Constructor.
     *
     * @param username  Username
     * @param password  Password in plaintext, i.e. not encrypted or hashed
     */
    public AuthenticationRequest(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
        if ((username == null) || username.isEmpty() || (password == null) || password.isEmpty()) {
            throw new VerificationException("Username and Password must be defined, i.e. non-null.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        //noinspection CastToConcreteClass
        final AuthenticationRequest that = (AuthenticationRequest) obj;
        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }

        return !(username != null ? !username.equals(that.username) : that.username != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (username != null ? username.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (password != null ? password.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AuthenticationRequest[username=" + username + ",password=" + password + ']';
    }
}
