/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.AttachedFiles
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

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findAttachments",
                query = "select v from AttachedFileView v " +
                        "where v.recordTable = :table" +
                        "  and v.recordId = :recordId")
})
@Table(name = "file_attachments")
public class AttachedFileView  extends AbstractView<AttachedFileView> {

    @Id
    @Column(name = "attachment_id", insertable = false, updatable = false)
    private Long id = null;

    @Column(name = "attachment_record_id", insertable = false, updatable = false)
    private Long recordId = null;

    @Column(name = "attachment_record_table", insertable = false, updatable = false)
    private String recordTable = null;

    @Embedded
    private EmbeddedFile file = null;

    @Embedded
    private EmbeddedGroup group = null;

    @Embedded
    private EmbeddedUser user = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "attachment_created", insertable = false, updatable = false)
    private Date created = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRecordId(final Long recordId) {
        this.recordId = recordId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordTable(final String recordTable) {
        this.recordTable = recordTable;
    }

    public String getRecordTable() {
        return recordTable;
    }

    public void setFile(final EmbeddedFile file) {
        this.file = file;
    }

    public EmbeddedFile getFile() {
        return file;
    }

    public void setGroup(final EmbeddedGroup group) {
        this.group = group;
    }

    public EmbeddedGroup getGroup() {
        return group;
    }

    public void setUser(final EmbeddedUser user) {
        this.user = user;
    }

    public EmbeddedUser getUser() {
        return user;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    // =========================================================================
    // Standard View Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AttachedFileView)) {
            return false;
        }

        // As the view is reading from the current data model, and the Id is
        // always unique. It is sufficient to compare against this field.
        final AttachedFileView that = (AttachedFileView) obj;
        return id.equals(that.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final AttachedFileView o) {
        return 0;
    }
}
