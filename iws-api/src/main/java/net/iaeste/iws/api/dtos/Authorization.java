/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Authorization
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

import java.io.Serializable;

/**
 * Contains the information about a permission.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Authorization implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Group group = null;
    private Role role = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Authorization() {
    }

    /**
     * Default Constructor.
     *
     * @param group Group
     * @param role  Role with Permissions
     */
    public Authorization(final Group group, final Role role) {
        setGroup(group);
        setRole(role);
    }

    /**
     * Copy Constructor.
     *
     * @param authorization Authorization Object to copy
     */
    public Authorization(final Authorization authorization) {
        if (authorization == null) {
            throw new IllegalArgumentException("the authorization Object cannot be null.");
        }

        setGroup(authorization.group);
        setRole(role);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroup(final Group group) {
        this.group = new Group(group);
    }

    public Group getGroup() {
        return new Group(group);
    }

    public void setRole(final Role role) {
        this.role = new Role(role);
    }

    public Role getRole() {
        return new Role(role);
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Authorization)) {
            return false;
        }

        final Authorization that = (Authorization) obj;

        if (group != null ? !group.equals(that.group) : that.group != null) {
            return false;
        }

        return !(role != null ? !role.equals(that.role) : that.role != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (group != null ? group.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (role != null ? role.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Authorization{" +
                "group='" + group + '\'' +
                "role='" + role + '\'' +
                '}';
    }
}
