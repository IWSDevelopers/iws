/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.PersistUserRequest
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
public final class ManageUserAccountRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private User user = null;

    private UserStatus status = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public ManageUserAccountRequest() {
    }

    public ManageUserAccountRequest(final User user, final UserStatus status) {
        this.user = user;
        this.status = status;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUser(final User user) {
        this.user = new User(user);
    }

    public User getUser() {
        return new User(user);
    }

    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
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

        isNotVerifiable(validation, "user", user);
        isNotNull(validation, "status", status);

        return validation;
    }
}
