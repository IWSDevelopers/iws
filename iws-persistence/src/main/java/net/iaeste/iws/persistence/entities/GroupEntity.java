/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.common.exceptions.NotificationException;
import net.iaeste.iws.persistence.monitoring.Monitored;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.Externable;

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
import java.util.EnumMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        // Query is used by the Migration Tool
        @NamedQuery(name = "group.findAll",
                query = "select g from GroupEntity g"),
        // Query is used by the Migration Tool
        @NamedQuery(name = "group.findAllGroupType",
                query = "select g from GroupEntity g " +
                        "where g.groupType.grouptype = :type"),
        // Query is used by the Migration Tool
        @NamedQuery(name = "group.findByIW3Id",
                query = "select g from GroupEntity g " +
                        "where g.oldId = :oldid"),
        @NamedQuery(name = "group.findById",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.id = :id"),
        @NamedQuery(name = "group.findByExternalId",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.externalId = :id"),
        @NamedQuery(name = "group.findAllNationalAndInternational",
                query = "select g from GroupEntity g " +
                        "where g.groupType.grouptype = " + EntityConstants.GROUPTYPE_MEMBER +
                        "   or g.groupType.grouptype = " + EntityConstants.GROUPTYPE_INTERNATIONAL +
                        "order by g.groupType.grouptype asc, g.groupName asc"),
        @NamedQuery(name = "group.findAllForContacts",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.groupType.grouptype in" +
                                " (" + EntityConstants.GROUPTYPE_ADMINISTRATION +
                                ", " + EntityConstants.GROUPTYPE_MEMBER +
                                ", " + EntityConstants.GROUPTYPE_INTERNATIONAL +
                                ", " + EntityConstants.GROUPTYPE_NATIONAL +
                                ", " + EntityConstants.GROUPTYPE_LOCAL +
                                ", " + EntityConstants.GROUPTYPE_WORKGROUP + ") " +
                        "order by g.groupType.grouptype asc, g.groupName asc"),
        @NamedQuery(name = "group.findByUserAndExternalId",
                query = "select g from GroupEntity g, UserGroupEntity ug " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.id = ug.group.id" +
                        "  and g.externalId = :eid" +
                        "  and ug.user.id = :uid"),
        @NamedQuery(name = "group.findGroupByUserAndType",
                query = "select g from GroupEntity g, UserGroupEntity ug " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.id = ug.group.id" +
                        "  and g.groupType.grouptype = :type" +
                        "  and ug.user.id = :uid"),
        @NamedQuery(name = "group.findGroupByParent",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "and g.parentId = :pid"),
        @NamedQuery(name = "group.findGroupByParentAndName",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.parentId = :pid" +
                        "  and lower(g.groupName) like lower(:name)"),
        @NamedQuery(name = "group.findNationalByUser",
                query = "select g from GroupEntity g, UserGroupEntity ug " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.id = ug.group.id" +
                        "  and ug.user.id = :uid" +
                        "  and g.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL),
        @NamedQuery(name = "group.findNationalByLocalGroupAndUser",
                query = "select ng from GroupEntity ng, GroupEntity lg, UserGroupEntity ug " +
                        "where ng.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ng.parentId = lg.parentId" +
                        "  and ng.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL +
                        "  and lg.groupType.grouptype = " + EntityConstants.GROUPTYPE_LOCAL +
                        "  and lg.id = ug.group.id" +
                        "  and ug.group.id = :gid" +
                        "  and ug.user.id = :uid"),
        @NamedQuery(name = "group.findSubGroupsByParentId",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.parentId = :pid"),
        @NamedQuery(name = "group.findByPermission",
                query = "select g from GroupEntity g, UserPermissionView v " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.id = v.id.groupId" +
                        "  and v.id.userId = :uid" +
                        "  and v.permission = :permission"),
        @NamedQuery(name = "group.findByExternalGroupIdAndPermission",
                query = "select g from GroupEntity g, UserPermissionView v " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.id = v.id.groupId" +
                        "  and v.id.userId = :uid" +
                        "  and g.externalId = :egid" +
                        "  and v.permission = :permission"),
        @NamedQuery(name = "group.findStudentGroup",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.parentId = :gid" +
                        "  and g.groupType.grouptype = " + EntityConstants.GROUPTYPE_STUDENT),
        @NamedQuery(name = "group.findGroupsWithSimilarNames",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.id != :gid" +
                        "  and g.parentId = :pid" +
                        "  and lower(g.groupName) = lower(:name)"),
        @NamedQuery(name = "group.findByExternalGroupIds",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.externalId in :egids"),
        // find all "member countries" for sharing and exclude the own group
        @NamedQuery(name = "group.findGroupsForSharing",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.id <> :gid" +
                        "  and g.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL +
                        "order by g.groupName")
})
@Entity
@Table(name = "groups")
@Monitored(name = "Group", level = MonitoringLevel.DETAILED)
public class GroupEntity extends AbstractUpdateable<GroupEntity> implements Externable<GroupEntity>, Notifiable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "group_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * The content of this Entity is exposed externally, however to avoid that
     * someone tries to spoof the system by second guessing our Sequence values,
     * An External Id is used, the External Id is a Unique UUID value, which in
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
    @JoinColumn(name = "grouptype_id", referencedColumnName = "id", nullable = false, updatable = false)
    private GroupTypeEntity groupType = null;

    @Monitored(name="Group Name", level = MonitoringLevel.DETAILED)
    @Column(name = "group_name", length = 50)
    private String groupName;

    @Monitored(name="Group Full Name", level = MonitoringLevel.DETAILED)
    @Column(name = "full_name", length = 125)
    private String fullName = null;

    @Monitored(name="Group Description", level = MonitoringLevel.DETAILED)
    @Column(name = "group_description", length = 250)
    private String description = null;

    @ManyToOne(targetEntity = CountryEntity.class)
    @JoinColumn(name = "country_id", referencedColumnName = "id", updatable = false)
    private CountryEntity country = null;

    @Monitored(name="Group Mailinglist Name", level = MonitoringLevel.DETAILED)
    @Column(name = "list_name", length = 100)
    private String listName = null;

    @Monitored(name="Group Has Private List", level = MonitoringLevel.DETAILED)
    @Column(name = "private_list", nullable = false)
    private Boolean privateList = null;

    @Monitored(name="Group Has Public List", level = MonitoringLevel.DETAILED)
    @Column(name = "public_list", nullable = false)
    private Boolean publicList = null;

    @Monitored(name="Group Status", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GroupStatus status = GroupStatus.ACTIVE;

    @Monitored(name="Monitoring Level", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "monitoring_level", nullable = false)
    private MonitoringLevel monitoringLevel = MonitoringLevel.NONE;

    /**
     * For the data migration, it is problematic to use the old Id's, hence
     * we're storing the old Id in a separate field - which is purely internal,
     * and should be dropped once the IWS has become feature complete in
     * relation to IW3. The plan is that this should happen during 2014. Once
     * the IWS is feature complete, it means that all old data has been properly
     * migrated over, and the old Id is then considered obsolete.
     */
    @Column(name = "old_iw3_id")
    private Integer oldId = null;

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

    public void setPrivateList(final Boolean privateList) {
        this.privateList = privateList;
    }

    public Boolean getPrivateList() {
        return privateList;
    }

    public void setPublicList(final Boolean publicList) {
        this.publicList = publicList;
    }

    public Boolean getPublicList() {
        return publicList;
    }

    public void setStatus(final GroupStatus status) {
        this.status = status;
    }

    public GroupStatus getStatus() {
        return status;
    }

    public void setMonitoringLevel(final MonitoringLevel monitoringLevel) {
        this.monitoringLevel = monitoringLevel;
    }

    public MonitoringLevel getMonitoringLevel() {
        return monitoringLevel;
    }

    public void setOldId(final Integer oldId) {
        this.oldId = oldId;
    }

    public Integer getOldId() {
        return oldId;
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
    public boolean diff(final GroupEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final GroupEntity obj) {
        if (obj != null) {
            groupName = which(groupName, obj.groupName);
            fullName = which(fullName, obj.fullName);
            description = which(description, obj.description);
            listName = which(listName, obj.listName);
            privateList = which(privateList, obj.privateList);
            publicList = which(publicList, obj.publicList);
            monitoringLevel = which(monitoringLevel, obj.monitoringLevel);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<NotificationField, String> prepareNotifiableFields(final NotificationType type) {
        final Map<NotificationField, String> fields = new EnumMap<>(NotificationField.class);
        //TODO process_mailing_list
        switch (type) {
            case NEW_GROUP:
            //case CHANGE_IN_GROUP_MEMBERS:
            //case NEW_GROUP_OWNER:
                fields.put(NotificationField.GROUP_NAME, groupName);
                if (country != null) {
                    fields.put(NotificationField.COUNTRY_NAME, country.getCountryName());
                }
                fields.put(NotificationField.GROUP_TYPE, groupType.getGrouptype().name());
                fields.put(NotificationField.GROUP_EXTERNAL_ID, externalId);
                break;
            case PROCESS_MAILING_LIST:
                fields.put(NotificationField.GROUP_NAME, groupName);
                if (country != null) {
                    fields.put(NotificationField.COUNTRY_NAME, country.getCountryName());
                }
                fields.put(NotificationField.GROUP_TYPE, groupType.getGrouptype().name());
                fields.put(NotificationField.GROUP_EXTERNAL_ID, externalId);
                break;
            default:
                throw new NotificationException("NotificationType " + type + " is not supported in this context.");
        }

        return fields;
    }

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
