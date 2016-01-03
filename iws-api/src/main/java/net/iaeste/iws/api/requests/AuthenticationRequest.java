/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.StandardMethods;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authenticationRequest", propOrder = { "username", "password" })
public final class AuthenticationRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * User Credential: Username (the user's private e-mail address).
     */
    @XmlElement(required = true, nillable = false)
    private String username;

    /**
     * User Credential: Password, must follow the requirements defined in the
     * Constants.
     */
    @XmlElement(required = true, nillable = false)
    @StandardMethods(StandardMethods.For.NONE)
    private String password;

    // =========================================================================
    // Object Constructors
    // =========================================================================

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

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUsername(final String username) throws IllegalArgumentException {
        ensureNotNullOrEmpty("username", username);

        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(final String password) throws IllegalArgumentException {
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

        // Sharing the exact value that fails is not a good idea, hence we try
        // to anonymize it a bit. From a security perspective, it is called
        // "Security through Obscurity" - and it is not our only mechanism, but
        // limiting the information that hackers may get, is always a good
        // idea :-)
        if ((username == null) || (password == null) || username.isEmpty() || password.isEmpty()) {
            validation.put("User Credentials", "Missing or invalid value.");
        }

        return validation;
    }
}
