/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.CreateUserRequest
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This is the Request Object for creating new User Accounts in IWS. The account
 * requires some data, which is included here. By default, the user must provide
 * a valid e-mail address - as the IWS Account ActivationCode is sent to this
 * e-mail address. The user account cannot be used until it has been activated.
 * Accounts that has not been activated within 14 days will be considered dead
 * and be wiped from the system.<br />
 *   Additionally to the username (e-mail address), a password must be chosen.
 * The system makes no checks against the strength of the password, nor will the
 * system enforce regular changes. However, the user should pick a strong
 * password. Besides this, the users first and last names must also be
 * provided.<br />
 *   It is important to note, that the users names (first, last) cannot be
 * altered, unless a DBA (Database Administrator) directly intervenes and makes
 * this change. It is done so, since the IWS is a multi-user & multi-group
 * system, and the user should not give accounts to others, but rather create
 * and delete accounts.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class CreateUserRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The e-mail compliance regular expression. */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(IWSConstants.EMAIL_REGEX);

    private String username = null;
    private String password = null;
    private String firstname = null;
    private String lastname = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public CreateUserRequest() {
    }

    /**
     * Default Constructor for creating users without a pre-defined Password.
     * However, the username and the first/last names must be set. The System
     * will automatically generate a password, and set it in the e-mail
     * delivered.
     *
     * @param username  The users e-mail address, is used as username in IWS
     * @param firstname The users given name, can only be altered by the DBA's
     * @param lastname  The users Family name, can only be altered by the DBA's
     */
    public CreateUserRequest(final String username, final String firstname, final String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     * Default Constructor. All users generated must have this information set.
     *
     * @param username  The users e-mail address, is used as username in IWS
     * @param password  Chosen Password in clear-text
     * @param firstname The users given name, can only be altered by the DBA's
     * @param lastname  The users Family name, can only be altered by the DBA's
     */
    public CreateUserRequest(final String username, final String password, final String firstname, final String lastname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

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

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        if (!EMAIL_PATTERN.matcher(username).matches()) {
            validation.put("username", "invalid e-mail address.");
        }
        isWithinLimits(validation, "username", username, 1, 50);
        isNotEmpty(validation, "password", password);
        isWithinLimits(validation, "firstname", firstname, 1, 50);
        isWithinLimits(validation, "lastname", lastname, 1, 50);

        return validation;
    }
}
