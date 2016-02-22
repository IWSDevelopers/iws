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

import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.MailReply;
import net.iaeste.iws.api.enums.MonitoringLevel;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * This is the Embedded Group Object, which is used by various Views. The
 * intentions of this Object, is to have a unified way of dealing with the
 * read-only Group, so only a single DTO mapping instance is required.
 *   If any one view require more information, then all views must be extended
 * with this. All Group information must be prefixed with "group_" in the view.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Embeddable
public final class EmbeddedGroup {

    @Column(name = "group_external_id", insertable = false, updatable = false)
    private String externalId = null;

    @Column(name = "group_parent_id", insertable = false, updatable = false)
    private Long parentId = null;

    @Column(name = "group_external_parent_id", insertable = false, updatable = false)
    private String externalParentId = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_grouptype", insertable = false, updatable = false)
    private GroupType groupType = null;

    @Column(name = "group_groupname", insertable = false, updatable = false)
    private String groupName = null;

    @Column(name = "group_list_name", insertable = false, updatable = false)
    private String listName = null;

    @Column(name = "group_private_list", insertable = false, updatable = false)
    private Boolean privateList = null;

    @Column(name = "group_public_list", insertable = false, updatable = false)
    private Boolean publicList = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_status", insertable = false, updatable = false)
    private GroupStatus status = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_private_list_reply", insertable = false, updatable = false)
    private MailReply privateReplyTo = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_public_list_reply", insertable = false, updatable = false)
    private MailReply publicReplyTo = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_monitoring_level", insertable = false, updatable = false)
    private MonitoringLevel monitoringLevel = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "group_modified", insertable = false, updatable = false)
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "group_created", insertable = false, updatable = false)
    private Date created = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

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

    public void setExternalParentId(final String externalParentId) {
        this.externalParentId = externalParentId;
    }

    public String getExternalParentId() {
        return externalParentId;
    }

    public void setGroupType(final GroupType groupType) {
        this.groupType = groupType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
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

    public void setPrivateReplyTo(final MailReply privateReplyTo) {
        this.privateReplyTo = privateReplyTo;
    }

    public MailReply getPrivateReplyTo() {
        return privateReplyTo;
    }

    public void setPublicReplyTo(final MailReply publicReplyTo) {
        this.publicReplyTo = publicReplyTo;
    }

    public MailReply getPublicReplyTo() {
        return publicReplyTo;
    }

    public void setMonitoringLevel(final MonitoringLevel monitoringLevel) {
        this.monitoringLevel = monitoringLevel;
    }

    public MonitoringLevel getMonitoringLevel() {
        return monitoringLevel;
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
