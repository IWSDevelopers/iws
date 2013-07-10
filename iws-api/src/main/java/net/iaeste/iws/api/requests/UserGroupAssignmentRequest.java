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
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.User;
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

    /** The user that will be added to a group. **/
    private User user = null;

    /** The group to which the user will be added.**/
    private Group group = null;

    /** The role which te user will be given in the Group. */
    private Role role = null;

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
     *
     * @param user
     * @param group
     * @deprecated since the role is missing.
     */
    @Deprecated
    public UserGroupAssignmentRequest(final User user, final Group group) {
        this.user = new User(user);
        this.group = group;
    }

    /**
     * Default Constructor.
     *
     * @param user  User
     * @param group Group
     * @param role  Role
     */
    public UserGroupAssignmentRequest(final User user, final Group group, final Role role) {
        this.user = new User(user);
        this.group = group;
        this.role = role;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUser(final User user) throws IllegalArgumentException {
        ensureNotNull("user", user);

        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setGroup(final Group group) throws IllegalArgumentException {
        ensureNotNull("group", group);

        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setRole(final Role role) throws IllegalArgumentException {
        ensureNotNull("role", role);

        this.role = role;
    }

    public Role getRole() {
        return role;
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

        isNotNull(validation, "user", user);
        isNotNull(validation, "group", group);
        // TODO Correct the tests that uses this logic, so the commented line can be added again
        //isNotNull(validation, "role", role);

        return validation;
    }
}
