/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.GroupEntity
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

import net.iaeste.iws.api.enums.GroupStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection AssignmentToDateFieldFromParameter
 */
@NamedQueries({
        @NamedQuery(name = "group.findAll",
                query = "select g from GroupEntity g"),
        @NamedQuery(name = "group.findById",
                query = "select g from GroupEntity g " +
                        "where g.id = :id"),
        @NamedQuery(name = "group.findByExternalId",
                query = "select g from GroupEntity g " +
                        "where g.externalId = :id"),
        @NamedQuery(name = "group.findByUserAndExternalId",
                query = "select g from GroupEntity g, UserGroupEntity ug " +
                        "where g.id = ug.group.id" +
                        "  and g.externalId = :eid" +
                        "  and ug.user.id = :uid"),
        @NamedQuery(name = "group.findGroupByUserAndType",
                query = "select g from GroupEntity g, UserGroupEntity ug " +
                        "where g.id = ug.group.id" +
                        "  and g.groupType.grouptype = :type" +
                        "  and ug.user.id = :uid"),
        @NamedQuery(name = "group.findNationalOrSarByUser",
                query = "select g from GroupEntity g, UserGroupEntity ug " +
                        "where g.id = ug.group.id" +
                        "  and ug.user.id = :uid" +
                        "  and (g.groupType.grouptype = 'NATIONAL'" +
                        "   or g.groupType.grouptype = 'SAR')"),
        @NamedQuery(name = "group.findSubGroupsByParentId",
                    query = "select g from GroupEntity g " +
                        "where g.status <> 'DELETED'" +
                        "  and g.parentId = :pid"),
        @NamedQuery(name = "group.findByPermission",
                query = "select g from GroupEntity g, UserPermissionView v " +
                        "where g.id = v.id.groupId" +
                        "  and v.id.userId = :uid" +
                        "  and v.permission = :permission"),
        @NamedQuery(name = "group.findByExternalGroupIdAndPermission",
                query = "select g from GroupEntity g, UserPermissionView v " +
                        "where g.id = v.id.groupId" +
                        "  and v.id.userId = :uid" +
                        "  and g.externalId = :egid" +
                        "  and v.permission = :permission"),
        @NamedQuery(name = "group.findStudentGroup",
                query = "select g from GroupEntity g " +
                        "where g.parentId = :gid" +
                        "  and g.groupType.grouptype = 'STUDENTS'"),
        @NamedQuery(name = "group.findGroupsWithSimilarNames",
                query = "select g from GroupEntity g " +
                        "where g.parentId = :pid" +
                        "  and lower(g.groupName) = :name"),
        @NamedQuery(name = "group.findByExternalGroupIds",
                query = "select g from GroupEntity g " +
                        "where g.externalId in :egids"),
        // find all "member countries" for sharing and exclude the own group
        @NamedQuery(name = "group.findGroupsForSharing",
                query = "select g from GroupEntity g " +
                        "where g.id <> :gid" +
                        "  and (g.groupType.grouptype = 'NATIONAL'" +
                        "  or  g.groupType.grouptype = 'SAR')" +
                        "order by g.groupName")
})
@Entity
@Table(name = "groups")
public class GroupEntity implements IWSEntity {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "group_sequence")
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

    /**
     * The Parent Id is a reference to the Parent Group. However, JPA does
     * (understandably) not allow cyclic references, hence we cannot set the
     * Object type of the ParentId to a GroupEntity, rather we're just referring
     * to it as a Long value. Most Groups will have a ParentId, but some will
     * not. Hence, this field may be null, but once a Group has been created in
     * the hierarchy, then the Parent cannot be altered.
     */
    @Column(name = "parent_id", updatable = false)
    private Long parentId = null;

    @ManyToOne(targetEntity = GroupTypeEntity.class)
    @JoinColumn(name = "grouptype_id", nullable = false, updatable = false)
    private GroupTypeEntity groupType = null;

    @Column(name = "groupname", length = 50)
    private String groupName;

    @Column(name = "full_name", length = 100)
    private String fullName = null;

    @Column(name = "group_description", length = 250)
    private String description = null;

    @ManyToOne(targetEntity = CountryEntity.class)
    @JoinColumn(name = "country_id", updatable = false)
    private CountryEntity country = null;

    @Column(name = "list_name")
    private String listName = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GroupStatus status = GroupStatus.ACTIVE;

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
    public GroupEntity() {
        this.id = null;
        this.groupName = null;
    }

    /**
     * Default Constructor, for creating new entity.
     *
     * @param groupName Group Name
     */
    public GroupEntity(final String groupName) {
        this.id = null;
        this.groupName = groupName;
    }

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

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

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setGroupType(final GroupTypeEntity groupType) {
        this.groupType = groupType;
    }

    public GroupTypeEntity getGroupType() {
        return groupType;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCountry(final CountryEntity country) {
        this.country = country;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setListName(final String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setStatus(final GroupStatus status) {
        this.status = status;
    }

    public GroupStatus getStatus() {
        return status;
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

    // =========================================================================
    // Other Methods required for this Entity
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "GroupEntity{" +
                "id='" + externalId + '\'' +
                ", groupname='" + groupName + '\'' +
                '}';
    }
}
