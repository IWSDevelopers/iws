/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * @since   IWS 1.0
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
public final class UserPermissionView extends AbstractView<UserPermissionView> {

    @EmbeddedId
    private UserPermissionViewId id = new UserPermissionViewId();

    @Column(name = "euid")
    private String externalUserId = null;

    @Column(name = "egid")
    private String externalGroupId = null;

    @Column(name = "erid")
    private String externalRoleId = null;

    @Column(name = "eugid")
    private String externalUserGroupId = null;

    @Column(name = "username")
    private String userName = null;

    @Column(name = "groupname")
    private String groupname = null;

    @Column(name = "group_fullname")
    private String groupFullname = null;

    @Column(name = "grouptype")
    @Enumerated(EnumType.STRING)
    private GroupType groupType = null;

    @Column(name = "country")
    private String countryCode = null;

    @Column(name = "group_description")
    private String groupDescription = null;

    @Column(name = "role")
    private String role = null;

    @Column(name = "title")
    private String title = null;

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

    public Long getUserId() {
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

    public void setExternalRoleId(final String externalRoleId) {
        this.externalRoleId = externalRoleId;
    }

    public String getExternalRoleId() {
        return externalRoleId;
    }

    public void setExternalUserGroupId(final String externalUserGroupId) {
        this.externalUserGroupId = externalUserGroupId;
    }

    public String getExternalUserGroupId() {
        return externalUserGroupId;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setGroupname(final String groupname) {
        this.groupname = groupname;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupFullname(final String groupFullname) {
        this.groupFullname = groupFullname;
    }

    public String getGroupFullname() {
        return groupFullname;
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

    public void setRole(final String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
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
    static final class UserPermissionViewId implements Serializable {

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

        private void setGroupId(final Long groupId) {
            this.groupId = groupId;
        }

        private Long getGroupId() {
            return groupId;
        }

        private void setPermissionId(final Long permissionId) {
            this.permissionId = permissionId;
        }

        private Long getPermissionId() {
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

            result = (IWSConstants.HASHCODE_MULTIPLIER * result) + groupId.hashCode();
            result = (IWSConstants.HASHCODE_MULTIPLIER * result) + permissionId.hashCode();

            return result;
        }
    }
}
