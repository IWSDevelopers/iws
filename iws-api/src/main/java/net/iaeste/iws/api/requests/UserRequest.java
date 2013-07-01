/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.UserRequest
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
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * Request Object for altering a User Account in the system. With this Object,
 * it is possible to block an active Account or re-activate a blocked Account,
 * and even delete an Account.<br />
 *   Note; deletion is a non-reversible action.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class UserRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private User user = null;
    private String newUsername = null;
    private UserStatus newStatus = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public UserRequest() {
    }

    /**
     * Default Constructor.
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
     * If a user must change their username, i.e. the registered e-mail address,
     * then this value must be set here. This change can be invoked both by
     * users and administrators. The change will not be directly updated, but
     * only marked, until the user has approved the change with a code that is
     * being sent.
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
     * If the user is should change status, then the new status must be defined
     * here. It is possible for someone with the rights to control a user
     * account, to activate, deactivate & delete accounts. However, a users may
     * not perform the status change operations on themselves.<br />
     *   The only exception for users, regarding the status change rule, is that
     * a user must activate an account.
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

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(1);

        isVerifiable(validation, "user", user);

        return validation;
    }
}
