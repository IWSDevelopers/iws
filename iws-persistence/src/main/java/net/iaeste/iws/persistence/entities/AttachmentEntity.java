/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.AttachmentEntity
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
 * ElementCollection Annotation, which is limited to either Embeddables or
 * simple Object Types. Complex Object Types, like other Entities requires that
 * we resort to very complex Annotation logic which will eventually make the
 * code harder to understand / maintain and also more error prone. For this
 * reason, it is initially implemented using a simpler construction, which can
 * later on be improved, hopefully without needing to change the datamodel.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries({
        @NamedQuery(name = "attachments.findById",
                query = "select a from AttachmentEntity a " +
                        "where a.id = :id"),
        @NamedQuery(name = "attachments.findForRecord",
                query = "select a from AttachmentEntity a " +
                        "where a.table = :table" +
                        "  and a.record = :recordid"),
        @NamedQuery(name = "attachments.findForRecordAndFile",
                query = "select a from AttachmentEntity a " +
                        "where a.table = :table" +
                        "  and a.record = :recordid" +
                        "  and a.file.id = :fileid")
})
@Entity
@Table(name = "attachments")
public class AttachmentEntity implements IWSEntity {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "country_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
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
