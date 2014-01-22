/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.UserGroupEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.common.exceptions.NotificationException;
import net.iaeste.iws.common.monitoring.Monitored;
import net.iaeste.iws.common.monitoring.MonitoringLevel;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.Externable;

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
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection AssignmentToDateFieldFromParameter
 */
@NamedQueries({
        @NamedQuery(name = "usergroup.findById",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.id = :id"),
        @NamedQuery(name = "usergroup.findByIw3UserAndGroup",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.user.oldId = :iw3User" +
                        "  and ug.group.oldId = :iw3Group"),
        @NamedQuery(name = "usergroup.findByExternalId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.externalId = :eid"),
        @NamedQuery(name = "usergroup.findMemberByGroupAndUser",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.group.groupType.grouptype = 'MEMBER'" +
                        "  and ug.group.id = :gid" +
                        "  and ug.user.externalId = :euid"),
        @NamedQuery(name = "usergroup.findByGroupAndUser",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.group.id = :gid" +
                        "  and ug.user.id= :uid"),
        @NamedQuery(name = "usergroup.findMemberByUserExternalId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.group.groupType.grouptype = 'MEMBER'" +
                        "  and ug.user.externalId = :euid"),
        @NamedQuery(name = "usergroup.findNationalSecretaryByMemberGroup",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.group.groupType.grouptype = 'NATIONAL'" +
                        "  and ug.role.id = 1" +
                        "  and ug.group.parentId = :mgid"),
        @NamedQuery(name = "usergroup.findOwnerByGroup",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.group.externalId = :egid" +
                        "  and ug.role.id = 1"),
        @NamedQuery(name = "usergroup.findMemberByUserId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.group.groupType.grouptype = 'MEMBER'" +
                        "  and ug.user.id = :uid"),
        @NamedQuery(name = "userGroup.findByGroupIdAndExternalUserId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.group.id = :gid" +
                        "  and ug.user.externalId = :euid"),
        @NamedQuery(name = "userGroup.findByGroupIdAndUserId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group = :group" +
                        "  and ug.user = :user"),
        // The roles are hardcoded to Owner, Moderator & Member, see
        // IWSConstants for more information
        @NamedQuery(name = "usergroup.findGroupMembers",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.group.id = :gid" +
                        "  and ug.role.id <= 3"),
        // The roles are hardcoded to Students, see IWSConstants for more
        // information
        @NamedQuery(name = "usergroup.findStudents",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.group.parentId = :pid" +
                        "  and ug.role.id = 5"),
        @NamedQuery(name = "usergroup.findGroupMembersOnPublicList",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.user.status = 'ACTIVE'" +
                        "  and ug.group.id = :gid" +
                        "  and ug.onPublicList = true"),
        @NamedQuery(name = "usergroup.findGroupMembersOnPrivateList",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.user.status = 'ACTIVE'" +
                        "  and ug.group.id = :gid" +
                        "  and ug.onPrivateList = true"),
        @NamedQuery(name = "usergroup.findAllUserGroups",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.user.id = :uid"),
        // The roles are hardcoded to Owner, Moderator & Member, see
        // IWSConstants for more information
        @NamedQuery(name = "usergroup.findncs",
                query = "select distinct ug from UserGroupEntity ug " +
                        "where ug.group.status = 'ACTIVE'" +
                        "  and ug.role.id <= 2" +
                        "  and (ug.group.groupType.grouptype = 'NATIONAL'" +
                        "    or ug.group.groupType.grouptype = 'INTERNATIONAL')")
})
@Entity
@Table(name = "user_to_group")
@Monitored(name = "User2Group", level = MonitoringLevel.DETAILED)
public class UserGroupEntity implements Externable<UserGroupEntity>, Notifiable {

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
     * An External Id is used, the External Id is a Uniqie UUID value, which in
     * all external references is referred to as the "Id". Although this can be
     * classified as StO (Security through Obscrutity), there is no need to
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
        if ((obj != null) && (id != null) && id.equals(obj.id)) {
            title = obj.title;
            onPublicList = obj.onPublicList;
            onPrivateList = obj.onPrivateList;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<NotificationField, String> prepareNotifiableFields(final NotificationType type) {
        final Map<NotificationField, String> fields = new EnumMap<>(NotificationField.class);

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
