/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 * @noinspection AssignmentToDateFieldFromParameter
 */
@Entity
@Table(name = "user_to_group")
public class UserGroupEntity implements IWSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(nullable = false, name = "group_id")
    private GroupEntity group = null;

    @ManyToOne(targetEntity = RoleEntity.class)
    @JoinColumn(nullable = false, name = "role_id")
    private RoleEntity role = null;

    @Column(nullable = true, name = "custom_title")
    private String title = null;

    @Column(nullable = false, name = "on_public_list")
    private Boolean onPublicList = true;

    @Column(nullable = false, name = "on_private_list")
    private Boolean onPrivateList = true;

    /** Last time the User Account was modified. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = new Date();

    /** Timestamp when the user was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
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

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
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

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }
}
