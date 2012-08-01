/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Entity
@NamedQueries(@NamedQuery(name = "findAllUserPermissions",
        query = "SELECT v FROM UserPermissionView v where id.userId = :uid"))
@Table(name = "user_permissions")
public class UserPermissionView extends AbstractView {

    /** {@see IWBaseConstats#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @EmbeddedId
    private UserPermissionViewId id = new UserPermissionViewId();

    @Column(name = "username")
    private String userName = null;

    @Column(name = "groupname")
    private String groupName = null;

    @Column(name = "tid")
    private Integer groupTypeId = null;

    @Column(name = "grouptype")
    private String groupType = null;

    @Column(name = "rid")
    private Integer roleId = null;

    @Column(name = "rolename")
    private String roleName = null;

    @Column(name = "permission")
    private String permission = null;

    /**
     * Empty Constructor, required by JPA.
     */
    public UserPermissionView() {
    }

    public void setId(final UserPermissionViewId id) {
        this.id = id;
    }

    public UserPermissionViewId getId() {
        return id;
    }

    public void setUserId(final Integer userId) {
        id.setUserId(userId);
    }

    public Integer getUserID() {
        return id.getUserId();
    }

    public void setGroupId(final Integer groupId) {
        id.setGroupId(groupId);
    }

    public Integer getGroupId() {
        return id.getGroupId();
    }

    public void setPermissionId(final Integer permissionId) {
        id.setPermissionId(permissionId);
    }

    public Integer getPermissionId() {
        return id.getPermissionId();
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

    public void setGroupTypeId(final Integer groupTypeId) {
        this.groupTypeId = groupTypeId;
    }

    public Integer getGroupTypeId() {
        return groupTypeId;
    }

    public void setGroupType(final String groupType) {
        this.groupType = groupType;
    }

    public String getGroupType() {
        return groupType;
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

    public void setPermission(final String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    /**
     * Internal Class, used as Embedded Id, it contains the combined Primary
     * Key for the View. This is required, since Views normally doesn't have a
     * single column, which can act as the primary key by itself. Hence, for
     * the UserPermission View, the combined UserId, GroupId & PermissionId
     * makes up the Primary Key.
     */
    @Embeddable
    static class UserPermissionViewId implements Serializable {

        /** {@see IWBaseConstats#SERIAL_VERSION_UID}. */
        private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

        @Column(name = "uid") private Integer userId;
        @Column(name = "gid") private Integer groupId;
        @Column(name = "pid") private Integer permissionId;

        /**
         * Default Empty JPA Constructor.
         */
        UserPermissionViewId() {
            userId = null;
            groupId = null;
            permissionId = null;
        }

        void setUserId(final Integer userId) {
            this.userId = userId;
        }

        Integer getUserId() {
            return userId;
        }

        public void setGroupId(final Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setPermissionId(final Integer permissionId) {
            this.permissionId = permissionId;
        }

        public Integer getPermissionId() {
            return permissionId;
        }
    }
}
