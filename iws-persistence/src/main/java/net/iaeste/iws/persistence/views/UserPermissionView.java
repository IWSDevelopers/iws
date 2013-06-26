/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.UserPermissionView
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.views;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Permission;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CompareToUsesNonFinalVariable, ClassEscapesDefinedScope
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findAllUserPermissions",
                query = "select v from UserPermissionView v " +
                        "where v.id.userId = :uid"),
        @NamedQuery(name = "view.findUserGroupPermissions",
                query = "select v from UserPermissionView v " +
                        "where v.id.userId = :uid" +
                        "  and v.externalGroupId = :egid")
})
@Table(name = "user_permissions")
public class UserPermissionView extends AbstractView<UserPermissionView> {

    /** {@see IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @EmbeddedId
    private UserPermissionViewId id = new UserPermissionViewId();

    @Column(name = "euid")
    private String externalUserId = null;

    @Column(name = "egid")
    private String externalGroupId = null;

    @Column(name = "username")
    private String userName = null;

    @Column(name = "groupname")
    private String groupName = null;

    @Column(name = "grouptype")
    @Enumerated(EnumType.STRING)
    private GroupType groupType = null;

    @Column(name = "country")
    private String countryCode = null;

    @Column(name = "group_description")
    private String groupDescription = null;

    @Column(name = "rid")
    private Integer roleId = null;

    @Column(name = "rolename")
    private String roleName = null;

    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private Permission permission = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final UserPermissionViewId id) {
        this.id = id;
    }

    public UserPermissionViewId getId() {
        return id;
    }

    public void setUserId(final Long userId) {
        id.setUserId(userId);
    }

    public Long getUserID() {
        return id.getUserId();
    }

    public void setGroupId(final Long groupId) {
        id.setGroupId(groupId);
    }

    public Long getGroupId() {
        return id.getGroupId();
    }

    public void setPermissionId(final Long permissionId) {
        id.setPermissionId(permissionId);
    }

    public Long getPermissionId() {
        return id.getPermissionId();
    }

    public void setExternalUserId(final String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalGroupId(final String externalGroupId) {
        this.externalGroupId = externalGroupId;
    }

    public String getExternalGroupId() {
        return externalGroupId;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupType(final GroupType groupType) {
        this.groupType = groupType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setGroupDescription(final String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setRoleId(final Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setPermission(final Permission permission) {
        this.permission = permission;
    }

    public Permission getPermission() {
        return permission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserPermissionView)) {
            return false;
        }

        final UserPermissionView that = (UserPermissionView) obj;

        return id.equals(that.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final UserPermissionView o) {
        final int result = permission.compareTo(o.permission);

        return sortAscending ? result : -result;
    }

    /**
     * Internal Class, used as Embedded Id, it contains the combined Primary
     * Key for the View. This is required, since Views normally doesn't have a
     * single column, which can act as the primary key by itself. Hence, for
     * the UserPermission View, the combined UserId, GroupId & PermissionId
     * makes up the Primary Key.
     * @noinspection JpaObjectClassSignatureInspection, PackageVisibleInnerClass
     */
    @Embeddable
    static class UserPermissionViewId implements Serializable {

        /** {@see IWSConstants#SERIAL_VERSION_UID}. */
        private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

        @Column(name = "uid") private Long userId;
        @Column(name = "gid") private Long groupId;
        @Column(name = "pid") private Long permissionId;

        /**
         * Default Empty JPA Constructor.
         */
        private UserPermissionViewId() {
            userId = null;
            groupId = null;
            permissionId = null;
        }

        void setUserId(final Long userId) {
            this.userId = userId;
        }

        Long getUserId() {
            return userId;
        }

        protected void setGroupId(final Long groupId) {
            this.groupId = groupId;
        }

        protected Long getGroupId() {
            return groupId;
        }

        protected void setPermissionId(final Long permissionId) {
            this.permissionId = permissionId;
        }

        protected Long getPermissionId() {
            return permissionId;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof UserPermissionViewId)) {
                return false;
            }

            final UserPermissionViewId that = (UserPermissionViewId) obj;

            if (!groupId.equals(that.groupId)) {
                return false;
            }
            if (!permissionId.equals(that.permissionId)) {
                return false;
            }

            return userId.equals(that.userId);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int result = userId.hashCode();

            result = IWSConstants.HASHCODE_MULTIPLIER * result + groupId.hashCode();
            result = IWSConstants.HASHCODE_MULTIPLIER * result + permissionId.hashCode();

            return result;
        }
    }
}
