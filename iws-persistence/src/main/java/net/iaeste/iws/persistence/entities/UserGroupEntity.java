/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import net.iaeste.iws.common.exceptions.NotificationException;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;

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
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 * @noinspection AssignmentToDateFieldFromParameter
 */
@NamedQueries({
        @NamedQuery(
                name = "usergroup.findById",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.id = :id"),
        @NamedQuery(
                name = "usergroup.findByExternalId",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.externalId = :eid"),
        @NamedQuery(
                name = "usergroup.findMemberByGroupAndUser",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.groupType.grouptype = 'MEMBERS'" +
                        "  and ug.group.id = :gid" +
                        "  and ug.user.externalId = :euid"),
        @NamedQuery(
                name = "usergroup.findMemberByUser",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.groupType.grouptype = 'MEMBERS'" +
                        "  and ug.user.externalId = :euid"),
        @NamedQuery(
                name = "usergroup.findGroupMembers",
                query = "select ug from UserGroupEntity ug " +
                        "where ug.group.id = :gid")
})
@Entity
@Table(name = "user_to_group")
public class UserGroupEntity implements Updateable<UserGroupEntity>, Notifiable {

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
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group = null;

    @ManyToOne(targetEntity = RoleEntity.class)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role = null;

    @Column(name = "custom_title")
    private String title = null;

    @Column(name = "on_public_list", nullable = false)
    private Boolean onPublicList = true;

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
    public void merge(final UserGroupEntity obj) {
        if ((obj != null) && (id != null) && id.equals(obj.id)) {
            role = obj.role;
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
                fields.put(NotificationField.ON_PUBLIC_LIST, onPublicList.toString());
                fields.put(NotificationField.ON_PRIVATE_LIST, onPrivateList.toString());
            case NEW_GROUP_OWNER:
                fields.put(NotificationField.GROUP_NAME, group.getGroupName());
                fields.put(NotificationField.GROUP_TYPE, group.getGroupType().getGrouptype().name());
                fields.put(NotificationField.FIRSTNAME, user.getFirstname());
                fields.put(NotificationField.LASTNAME, user.getLastname());
                break;
            default:
                throw new NotificationException("NotificationType " + type + " is not supported in this context.");
        }

        return fields;
    }
}
