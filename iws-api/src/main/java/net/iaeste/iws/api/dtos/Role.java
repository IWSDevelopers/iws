/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.util.AbstractVerification;

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
 * @since   IWS 1.0
 */
public final class Role extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String roleId = null;
    private String roleName = null;
    private Set<Permission> permissions = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

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
    public Role(final String roleId, final String roleName, final Set<Permission> permissions) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.permissions = permissions;
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

    /**
     * Sets the Role Id, which is the internally generated key for this Object.
     * Note, that the presence of the value will determine if the IWS should
     * process this record as if it exist or not. If the Id is set, but no
     * record exists, then the system will reply with an error. Likewise, if no
     * Id is provided, but the record exists, the system will reply with an
     * error.<br />
     *   The value must be a valid Id, otherwise the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param roleId Role Id
     * @throws IllegalArgumentException if the Id is set but invalid
     * @see AbstractVerification#UUID_FORMAT
     */
    public void setRoleId(final String roleId) throws IllegalArgumentException {
        ensureValidId("roleId", roleId);
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    /**
     * Sets the name of the Role. The name must be defined but not exceed the
     * maximum allowed length of 50 characters. If the name is either null,
     * empty or too long, then the method will thrown an
     * {@code IllegalArgumentException}.
     *
     * @param roleName Name of this Role
     * @throws IllegalArgumentException if the name is either null, empty or too long
     */
    public void setRoleName(final String roleName) throws IllegalArgumentException {
        ensureNotNullOrEmptyOrTooLong("roleName", roleName, 50);
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    /**
     * The Role contains a set of Permissions. This set may not be null. It is
     * allowed to contain any internally defined Permission. Please note, that a
     * Role is general, and although some permissions may be added, the
     * functionality is linked together with the Groups, so the system will
     * determine if a User is allowed to perform the action based on the
     * combined information of both Permission and Group, not just the
     * one.<br />
     *   The IWS uses the mathematical "Set" to verify if a Permission is
     * allowed for a User in the given context or not. If both the Role and the
     * Group have the Permission assigned, then the user may perform the action,
     * otherwise not.<br />
     *   If the permission is null, then the method will thrown an
     * {@code IllegalArgumentException}.
     *
     * @param permissions Set of Permissions for this Role
     * @throws IllegalArgumentException
     */
    public void setPermissions(final Set<Permission> permissions) throws IllegalArgumentException {
        ensureNotNull("permissions", permissions);
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
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

        isNotNull(validation, "roleName", roleName);

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

        // Permissions are ignored, since the Id & Name are suppose to be unique

        if ((roleId != null) ? !roleId.equals(role.roleId) : (role.roleId != null)) {
            return false;
        }

        return !((roleName != null) ? !roleName.equals(role.roleName) : (role.roleName != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        // Permissions are ignored, since the Id & Name are suppose to be unique
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((roleId != null) ? roleId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((roleName != null) ? roleName.hashCode() : 0);

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
