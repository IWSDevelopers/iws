/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Role
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
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Copier;

import java.security.Permissions;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Roles are simple collections of Permissions, with a name to associate them
 * with. The lis of Permissions is the all the Permissions that the Role may
 * undertake, this is held together with the Permissions or Actions that a Group
 * may perform, and the joined result of these two sets is then the actual set
 * of Permissions that a user with this Role, may undertake in the Context of
 * the Group.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Role extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String roleId = null;
    private String roleName = null;
    private Set<Permissions> permissions = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Role() {
    }

    /**
     * Default Constructor. Note, that the list of Permissions is all the
     * permissions that this role has, not necessarily the ones that apply to a
     * specific group.
     *
     * @param roleId      Id of the Role
     * @param roleName    Name of the Role
     * @param permissions Associated Permissions for this Role
     */
    public Role(final String roleId, final String roleName, final Set<Permissions> permissions) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.permissions = Copier.copy(permissions);
    }

    /**
     * Copy Constructor.
     *
     * @param role Role Object to copy
     */
    public Role(final Role role) {
        if (role != null) {
            roleId = role.roleId;
            roleName = role.roleName;
            permissions = role.permissions;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setRoleId(final String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setPermissions(final Set<Permissions> permissions) {
        this.permissions = Copier.copy(permissions);
    }

    public Set<Permissions> getPermissions() {
        return Copier.copy(permissions);
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Role)) {
            return false;
        }

        final Role role = (Role) obj;

        if (permissions != null ? !permissions.equals(role.permissions) : role.permissions != null) {
            return false;
        }

        if (roleId != null ? !roleId.equals(role.roleId) : role.roleId != null) {
            return false;
        }

        return !(roleName != null ? !roleName.equals(role.roleName) : role.roleName != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (roleId != null ? roleId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (roleName != null ? roleName.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (permissions != null ? permissions.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
