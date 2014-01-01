/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.EmbeddedGroup
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

import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;

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
 * @since   1.7
 */
@Embeddable
public class EmbeddedGroup {

    @Column(name = "group_external_id", insertable = false, updatable = false)
    private String externalId = null;

    @Column(name = "group_parent_id", insertable = false, updatable = false)
    private Long parentId = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_grouptype", insertable = false, updatable = false)
    private GroupType groupType = null;

    @Column(name = "group_groupname", insertable = false, updatable = false)
    private String groupName = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_status", insertable = false, updatable = false)
    private GroupStatus status = null;

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
}
