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

import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 * This entity handles Attachments (Files) for an Object (Table & RecordId). The
 * construction is made generic, since many Objects may have attachments, and
 * rather than having a table for each, we instead have one table for all, where
 * one of the key elements is the Table & RecordId. The key element is that the
 * Object (Table & RecordId) and File (FileId) must be unique.<br />
 *   Although JPA support Collection mapping it can only be done using the
 * ElementCollection Annotation, which is limited to either Embeddable or
 * simple Object Types. Complex Object Types, like other Entities requires that
 * we resort to very complex Annotation logic which will eventually make the
 * code harder to understand / maintain and also more error prone. For this
 * reason, it is initially implemented using a simpler construction, which can
 * later on be improved, hopefully without needing to change the data model.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "attachments.findForRecord",
                query = "select a from AttachmentEntity a " +
                        "where a.table = :table" +
                        "  and a.record = :recordid"),
        @NamedQuery(name = "attachments.deleteByFile",
                query = "delete AttachmentEntity a " +
                        "where a.file.id = :fid"),
        @NamedQuery(name = "attachments.findForRecordAndFile",
                query = "select a from AttachmentEntity a " +
                        "where a.table = :table" +
                        "  and a.record = :recordid" +
                        "  and a.file.id = :fileid"),
        @NamedQuery(name = "file.findApplicationBySendingGroupAndExternalFileId",
                query = "select a.file from AttachmentEntity a, ApplicationEntity sa " +
                        "where a.table = :table" +
                        "  and a.record = sa.id" +
                        "  and sa.offerGroup.group.id = :gid" +
                        "  and a.file.externalId = :fid"),
        @NamedQuery(name = "file.findApplicationByReceivingGroupAndExternalFileId",
                query = "select a.file from AttachmentEntity a, ApplicationEntity sa " +
                        "where a.table = :table" +
                        "  and a.record = sa.id" +
                        "  and sa.offerGroup.offer.employer.group.externalId = :gid" +
                        "  and a.file.externalId = :fid")
})
@Entity
@Table(name = "attachments")
public final class AttachmentEntity implements IWSEntity {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Column(name = "attached_to_table", length = 50, nullable = false, updatable = false)
    private String table = null;

    @Column(name = "attached_to_record", nullable = false, updatable = false)
    private Long record = null;

    @ManyToOne(targetEntity = FileEntity.class)
    @JoinColumn(name = "attached_file_id", referencedColumnName = "id", updatable = false)
    private FileEntity file = null;

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

    public void setTable(final String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    public void setRecord(final Long record) {
        this.record = record;
    }

    public Long getRecord() {
        return record;
    }

    public void setFile(final FileEntity file) {
        this.file = file;
    }

    public FileEntity getFile() {
        return file;
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
}
