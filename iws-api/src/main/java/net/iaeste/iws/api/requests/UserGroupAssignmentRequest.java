/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.UserGroupAssignmentRequest
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
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class UserGroupAssignmentRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private UserGroup userGroup = null;
    private boolean deleteUser = false;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public UserGroupAssignmentRequest() {
    }

    /**
     * Default Constructor.
     *
     */
    public UserGroupAssignmentRequest(final UserGroup userGroup) {
        setUserGroup(userGroup);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUserGroup(final UserGroup userGroup) {
        ensureNotNullAndVerifiable("userGroup", userGroup);
        this.userGroup = new UserGroup(userGroup);
    }

    public UserGroup getUserGroup() {
        return new UserGroup(userGroup);
    }

    public void setDeleteUser(final boolean deleteUser) {
        this.deleteUser = deleteUser;
    }

    public boolean isDeleteUserRequest() {
        return deleteUser;
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

        isNotNull(validation, "userGroup", userGroup);

        return validation;
    }
}
