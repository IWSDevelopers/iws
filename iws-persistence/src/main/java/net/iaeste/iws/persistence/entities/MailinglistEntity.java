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
import net.iaeste.iws.api.enums.MailinglistType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Entity
@Table(name = "mailing_lists")
public final class MailinglistEntity extends AbstractUpdateable<MailinglistEntity> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Column(name = "list_address", length = 50, unique = true, updatable = false)
    private String listAddress = null;

    /**
     * The group for which the alias exist. If the GroupType is private, then
     * the alias is a user alias, otherwise it is a mailing list alias.
     */
    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false, updatable = false)
    private GroupEntity group = null;

    @Column(name = "subject_prefix", length = 50, updatable = false)
    private String subjectPrefix = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "list_type", length = 25, nullable = false, updatable = false)
    private MailinglistType listType = MailinglistType.PUBLIC_LIST;

    @Enumerated(EnumType.STRING)
    @Column(name = "replyto_style", length = 25, nullable = false, updatable = false)
    private MailReply mailReply = MailReply.REPLY_TO_SENDER;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25, nullable = false)
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

    public void setListAddress(final String listAddress) {
        this.listAddress = listAddress;
    }

    public String getListAddress() {
        return listAddress;
    }

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setSubjectPrefix(final String subjectPrefix) {
        this.subjectPrefix = subjectPrefix;
    }

    public String getSubjectPrefix() {
        return subjectPrefix;
    }

    public void setListType(final MailinglistType listType) {
        this.listType = listType;
    }

    public MailinglistType getListType() {
        return listType;
    }

    public void setMailReply(final MailReply mailReply) {
        this.mailReply = mailReply;
    }

    public MailReply getMailReply() {
        return mailReply;
    }

    public void setStatus(final GroupStatus status) {
        this.status = status;
    }

    public GroupStatus getStatus() {
        return status;
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
    // Entity Standard Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final MailinglistEntity obj) {
        boolean differs = false;

        if (!Objects.equals(subjectPrefix, obj.subjectPrefix) || (status != obj.status)) {
            differs = true;
        }

        return differs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final MailinglistEntity obj) {
        if (canMerge(obj)) {
            subjectPrefix = obj.subjectPrefix;
            status = obj.status;
        }
    }
}
