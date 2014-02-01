/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.AccountNameRequest
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
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AccountNameRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private User user = null;
    private String firstname = null;
    private String lastname = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public AccountNameRequest() {
    }

    /**
     * Default Constructor,
     *
     * @param user      The user to change the account name for
     * @param lastname  The users new lastname
     */
    public AccountNameRequest(final User user, final String lastname) {
        setUser(user);
        setLastname(lastname);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the User Account to change the name of, i.e. to change either the
     * firstname or lastname of.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * value is not a valid User Object.
     *
     * @param user User Object
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setUser(final User user) throws IllegalArgumentException {
        ensureNotNullAndVerifiable("user", user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setFirstname(final String firstname) throws IllegalArgumentException {
        ensureNotTooLong("firstname", firstname, 50);
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(final String lastname) throws IllegalArgumentException {
        ensureNotTooLong("lastname", lastname, 50);
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
        final Map<String, String> validation = new HashMap<>(1);

        isNotNull(validation, "user", user);

        return validation;
    }
}
