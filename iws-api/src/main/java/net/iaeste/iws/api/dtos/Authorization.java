/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - Authorization
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
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.util.Copier;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

/**
 * Contains the information about a permission.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection SuppressionAnnotation, CastToConcreteClass
 */
public final class Authorization implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Group group = null;
    private Set<Permission> permissions = EnumSet.noneOf(Permission.class);

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Authorization() {
    }

    /**
     * Default Constructor.
     *
     * @param group       Group
     * @param permissions Authorization
     */
    public Authorization(final Group group, final Set<Permission> permissions) {
        this.group = group;
        this.permissions = Copier.copy(permissions);
    }

    /**
     * Copy Constructor.
     *
     * @param authorization Authorization Object to copy
     */
    public Authorization(final Authorization authorization) {
        if (authorization != null) {
            permissions = Copier.copy(authorization.permissions);
            group = authorization.group;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroup(final Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setPermissions(final Set<Permission> permissions) {
        this.permissions = Copier.copy(permissions);
    }

    public Set<Permission> getPermission() {
        return permissions;
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
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

        return !(permissions != null ? !permissions.equals(that.permissions) : that.permissions != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (group != null ? group.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (permissions != null ? permissions.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Authorization{" +
                "group='" + group + '\'' +
                ", permission='" + permissions + '\'' +
                '}';
    }
}
