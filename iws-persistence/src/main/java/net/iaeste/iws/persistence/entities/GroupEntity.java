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
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.MailReply;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.common.exceptions.NotificationException;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.monitoring.Monitored;

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
        @NamedQuery(name = "group.findById",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.id = :id"),
        @NamedQuery(name = "group.findByExternalId",
                query = "select g from GroupEntity g " +
                        "where g.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and g.externalId = :id"),
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "external_parent_id", updatable = false)
    private String externalParentId = null;

    @ManyToOne(targetEntity = GroupTypeEntity.class)
    @JoinColumn(name = "grouptype_id", referencedColumnName = "id", nullable = false, updatable = false)
    private GroupTypeEntity groupType = null;

    @Monitored(name="Group Name", level = MonitoringLevel.DETAILED)
    @Column(name = "group_name", length = 50)
    private String groupName = null;

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

    @Monitored(name="Group Private List ReplyTo", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "private_list_reply", nullable = false)
    private MailReply privateReplyTo = null;

    @Monitored(name="Group Public List ReplyTo", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "public_list_reply", nullable = false)
    private MailReply publicReplyTo = null;

    @Monitored(name="Group Status", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GroupStatus status = GroupStatus.ACTIVE;

    @Monitored(name="Monitoring Level", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "monitoring_level", nullable = false)
    private MonitoringLevel monitoringLevel = MonitoringLevel.NONE;

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
    // Entity Setters & Getters
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getExternalId() {
        return externalId;
    }

    public final void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    public final Long getParentId() {
        return parentId;
    }

    public final void setExternalParentId(final String externalParentId) {
        this.externalParentId = externalParentId;
    }

    public final String getExternalParentId() {
        return externalParentId;
    }

    public final void setGroupType(final GroupTypeEntity groupType) {
        this.groupType = groupType;
    }

    public final GroupTypeEntity getGroupType() {
        return groupType;
    }

    public final void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public final String getGroupName() {
        return groupName;
    }

    public final void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public final String getFullName() {
        return fullName;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final String getDescription() {
        return description;
    }

    public final void setCountry(final CountryEntity country) {
        this.country = country;
    }

    public final CountryEntity getCountry() {
        return country;
    }

    public final void setListName(final String listName) {
        this.listName = listName;
    }

    public final String getListName() {
        return listName;
    }

    public final void setPrivateList(final Boolean privateList) {
        this.privateList = privateList;
    }

    public final Boolean getPrivateList() {
        return privateList;
    }

    public final void setPublicList(final Boolean publicList) {
        this.publicList = publicList;
    }

    public final Boolean getPublicList() {
        return publicList;
    }

    public final void setPrivateReplyTo(final MailReply privateReplyTo) {
        this.privateReplyTo = privateReplyTo;
    }

    public final MailReply getPrivateReplyTo() {
        return privateReplyTo;
    }

    public final void setPublicReplyTo(final MailReply publicReplyTo) {
        this.publicReplyTo = publicReplyTo;
    }

    public final MailReply getPublicReplyTo() {
        return publicReplyTo;
    }

    public final void setStatus(final GroupStatus status) {
        this.status = status;
    }

    public final GroupStatus getStatus() {
        return status;
    }

    public final void setMonitoringLevel(final MonitoringLevel monitoringLevel) {
        this.monitoringLevel = monitoringLevel;
    }

    public final MonitoringLevel getMonitoringLevel() {
        return monitoringLevel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setModified(final Date modified) {
        this.modified = modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Date getModified() {
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Date getCreated() {
        return created;
    }

    // =========================================================================
    // Other Methods required for this Entity
    // =========================================================================


    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GroupEntity)) {
            return false;
        }

        final GroupEntity that = (GroupEntity) obj;

        // The ExternalId is sufficient to compare two internal GroupEntity
        // Objects, and even to compare if a not found external Group Object
        // matches an internal.
        return (externalId != null) ? externalId.equals(that.externalId) : (that.externalId == null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        // The External Id is sufficient for the HashCode value of this Object
        return (externalId != null) ? externalId.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean diff(final GroupEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void merge(final GroupEntity obj) {
        if (canMerge(obj)) {
            groupName = which(groupName, obj.groupName);
            fullName = which(fullName, obj.fullName);
            description = which(description, obj.description);
            listName = which(listName, obj.listName);
            privateList = which(privateList, obj.privateList);
            publicList = which(publicList, obj.publicList);
            privateReplyTo = which(privateReplyTo, obj.privateReplyTo);
            publicReplyTo = which(publicReplyTo, obj.publicReplyTo);
            monitoringLevel = which(monitoringLevel, obj.monitoringLevel);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Map<NotificationField, String> prepareNotifiableFields(final NotificationType type) {
        final EnumMap<NotificationField, String> fields = new EnumMap<>(NotificationField.class);

        switch (type) {
            case NEW_GROUP:
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
    public final String toString() {
        return "GroupEntity{" +
                "id='" + externalId + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
