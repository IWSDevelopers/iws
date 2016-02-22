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
package net.iaeste.iws.persistence.entities;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.common.exceptions.NotificationException;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.monitoring.Monitored;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.EnumMap;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "userGroup.findContactForExternalGroupId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.user.status = " + EntityConstants.USER_STATUS_ACTIVE +
                        "  and ug.group.externalId = :egid"),
        @NamedQuery(name = "userGroup.findContactForExternalUserId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.user.status = " + EntityConstants.USER_STATUS_ACTIVE +
                        "  and ug.user.externalId = :euid"),
        @NamedQuery(name = "userGroup.searchByFirstNameAndLastNameInMembers",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_MEMBER +
                        "  and ug.user.status <> " + EntityConstants.USER_STATUS_DELETED +
                        "  and ug.user.status <> " + EntityConstants.USER_STATUS_SUSPENDED +
                        "  and (lower(ug.user.firstname) like :firstname" +
                        "   or lower(ug.user.lastname) like :lastname)"),
        @NamedQuery(name = "userGroup.searchByFirstNameAndLastNameInSpecificMember",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_MEMBER +
                        "  and ug.group.externalId = :egid" +
                        "  and ug.user.status <> " + EntityConstants.USER_STATUS_DELETED +
                        "  and ug.user.status <> " + EntityConstants.USER_STATUS_SUSPENDED +
                        "  and (lower(ug.user.firstname) like :firstname" +
                        "   or lower(ug.user.lastname) like :lastname)"),
        @NamedQuery(name = "userGroup.findMemberByGroupAndUser",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_MEMBER +
                        "  and ug.group.id = :gid" +
                        "  and ug.user.externalId = :euid"),
        @NamedQuery(name = "userGroup.findByGroupAndUser",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.group.id = :gid" +
                        "  and ug.user.id= :uid"),
        @NamedQuery(name = "userGroup.findMemberByUserExternalId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_MEMBER +
                        "  and ug.user.externalId = :euid"),
        @NamedQuery(name = "userGroup.findNationalSecretaryByMemberGroup",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL +
                        "  and ug.role.id = " + EntityConstants.ROLE_OWNER +
                        "  and ug.group.parentId = :mgid"),
        @NamedQuery(name = "userGroup.findMemberByUserId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_MEMBER +
                        "  and ug.user.id = :uid"),
        @NamedQuery(name = "userGroup.findByGroupIdAndExternalUserId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.group.id = :gid" +
                        "  and ug.user.externalId = :euid"),
        @NamedQuery(name = "userGroup.findByGroupIdAndUserId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group = :group" +
                        "  and ug.user = :user"),
        // The roles are hardcoded to Owner, Moderator & Member, see
        // IWSConstants for more information
        @NamedQuery(name = "userGroup.findActiveGroupMembers",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.user.status = " + EntityConstants.USER_STATUS_ACTIVE +
                        "  and ug.group.id = :gid"),
        @NamedQuery(name = "userGroup.findAllGroupMembers",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.user.status <> " + EntityConstants.USER_STATUS_DELETED +
                        "  and ug.group.id = :gid"),
        // The roles are hardcoded to Students, see IWSConstants for more
        // information
        @NamedQuery(name = "userGroup.findStudents",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.group.parentId = :pid" +
                        "  and ug.role.id = " + EntityConstants.ROLE_STUDENT),
        @NamedQuery(name = "userGroup.findAllUserGroups",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.user.id = :uid"),
        // The roles are hardcoded to Owner & Moderator, see
        // IWSConstants for more information
        @NamedQuery(name = "userGroup.emergencyList",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.user.status = " + EntityConstants.USER_STATUS_ACTIVE +
                        "  and ug.role.id <= " + EntityConstants.ROLE_MODERATOR +
                        "  and ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL),
        // Under IW3, the schema for the NC's mailing list was that
        // Owner/Moderator of the Staff's were on it. At the same time the
        // list was limited to Owners of the International Groups. This rule
        // was inflexible and instead, the new rule is that members who are on
        // the public list of the following Groups (Administration, National &
        // International) are all included. For now, a hybrid version is used,
        // which allows both variants.
        @NamedQuery(name = "userGroup.findNCs",
                query = "select distinct ug from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.user.status = " + EntityConstants.USER_STATUS_ACTIVE +
                        "  and ug.onPublicList = true" +
                        "  and (ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_ADMINISTRATION +
                        "    or ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_INTERNATIONAL +
                        "    or ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL + ')'),
        @NamedQuery(name = "userGroup.userOnNCsList",
                query = "select distinct ug.user from UserGroupEntity ug " +
                        "where ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.user.status = " + EntityConstants.USER_STATUS_ACTIVE +
                        "  and ug.onPublicList = true" +
                        "  and (ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_ADMINISTRATION +
                        "    or ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_INTERNATIONAL +
                        "    or ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL + ')' +
                        "  and ug.user.username = :username"),
        @NamedQuery(name = "userGroup.findByUsernameAndGroupExternalId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.externalId = :egid" +
                        "  and ug.user.username = :username")
})
@Entity
@Table(name = "user_to_group")
@Monitored(name = "User2Group", level = MonitoringLevel.DETAILED)
public final class UserGroupEntity extends AbstractUpdateable<UserGroupEntity> implements Externable<UserGroupEntity>, Notifiable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "user_to_group_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * The content of this Entity is exposed externally, however to avoid that
     * someone tries to spoof the system by second guessing our Sequence values,
     * An External Id is used, the External Id is a Unique UUID value, which in
     * all external references is referred to as the "Id". Although this can be
     * classified as StO (Security through Obscurity), there is no need to
     * expose more information than necessary.
     */
    @Column(name = "external_id", length = 36, unique = true, nullable = false, updatable = false)
    private String externalId = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private UserEntity user = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false, updatable = false)
    private GroupEntity group = null;

    @Monitored(name="User2Group Role", level = MonitoringLevel.DETAILED)
    @ManyToOne(targetEntity = RoleEntity.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private RoleEntity role = null;

    @Monitored(name="User2Group Custom Title", level = MonitoringLevel.DETAILED)
    @Column(name = "custom_title")
    private String title = null;

    @Monitored(name="User2Group On Public Mailinglist", level = MonitoringLevel.DETAILED)
    @Column(name = "on_public_list", nullable = false)
    private Boolean onPublicList = true;

    @Monitored(name="User2Group On Private Mailinglist", level = MonitoringLevel.DETAILED)
    @Column(name = "on_private_list", nullable = false)
    private Boolean onPrivateList = true;

    @Monitored(name="User2Group Write To Private Mailinglist", level = MonitoringLevel.DETAILED)
    @Column(name = "write_to_private_list", nullable = false)
    private Boolean writeToPrivateList = true;

    /**
     * Last time the Entity was modified.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified = new Date();

    /**
     * Timestamp when the Entity was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    private Date created = new Date();

    // =========================================================================
    // Entity Constructors
    // =========================================================================

    /**
     * Empty Constructor, JPA requirement.
     */
    public UserGroupEntity() {
    }

    /**
     * Default Constructor, for creating a new User Group Relation.
     *
     * @param user   The User to grant membership of a Group
     * @param group  The Group to grant the user membership to
     * @param role   The Role of the user in the Group
     */
    public UserGroupEntity(final UserEntity user, final GroupEntity group, final RoleEntity role) {
        this.user = user;
        this.group = group;
        this.role = role;

        // Just set the default title of the user to the name of the Role
        title = role.getRole();
    }

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExternalId() {
        return externalId;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setRole(final RoleEntity role) {
        this.role = role;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setOnPublicList(final Boolean onPublicList) {
        this.onPublicList = onPublicList;
    }

    public Boolean getOnPublicList() {
        return onPublicList;
    }

    public void setOnPrivateList(final Boolean onPrivateList) {
        this.onPrivateList = onPrivateList;
    }

    public Boolean getOnPrivateList() {
        return onPrivateList;
    }

    public void setWriteToPrivateList(final Boolean writeToPrivateList) {
        this.writeToPrivateList = writeToPrivateList;
    }

    public Boolean getWriteToPrivateList() {
        return writeToPrivateList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModified(final Date modified) {
        this.modified = modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getModified() {
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getCreated() {
        return created;
    }

    // =========================================================================
    // Other Methods required for this Entity
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final UserGroupEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final UserGroupEntity obj) {
        if (canMerge(obj)) {
            title = which(title, obj.title);
            onPublicList = which(onPublicList, obj.onPublicList);
            onPrivateList = which(onPrivateList, obj.onPrivateList);
            writeToPrivateList = which(writeToPrivateList, obj.writeToPrivateList);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnumMap<NotificationField, String> prepareNotifiableFields(final NotificationType type) {
        final EnumMap<NotificationField, String> fields = new EnumMap<>(NotificationField.class);

        switch (type) {
            case CHANGE_IN_GROUP_MEMBERS:
                fields.put(NotificationField.ROLE, role.getRole());
                fields.put(NotificationField.ON_PUBLIC_LIST, String.valueOf(onPublicList));
                fields.put(NotificationField.ON_PRIVATE_LIST, String.valueOf(onPrivateList));
                fields.put(NotificationField.GROUP_TYPE, group.getGroupType().getGrouptype().name());
                fields.put(NotificationField.GROUP_EXTERNAL_ID, group.getExternalId());
                fields.put(NotificationField.EMAIL, user.getUsername());
                fields.put(NotificationField.USER_STATUS, user.getStatus().name());
                fields.put(NotificationField.ROLE, role.getRole());
                break;
            case NEW_GROUP_OWNER:
                fields.put(NotificationField.GROUP_EXTERNAL_ID, group.getExternalId());
                fields.put(NotificationField.GROUP_TYPE, group.getGroupType().getGrouptype().name());
                fields.put(NotificationField.GROUP_NAME, group.getGroupName());
                fields.put(NotificationField.FIRSTNAME, user.getFirstname());
                fields.put(NotificationField.LASTNAME, user.getLastname());
                fields.put(NotificationField.EMAIL, user.getUsername());
                break;
            default:
                throw new NotificationException("NotificationType " + type + " is not supported in this context.");
        }

        return fields;
    }
}
