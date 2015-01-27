/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3FileEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.entities;

import net.iaeste.iws.api.constants.IWSConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Entity
@Table(name = "files")
public class IW3FileEntity implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "fileid", nullable = false, length = 10)
    private Long fileid = null;

    @Column(name = "userid")
    private Integer userId = null;

    @Column(name = "groupid")
    private Integer groupId = null;

    @Column(name = "filetype", length = 1)
    private String filetype = null;

    @Column(name = "filename", length = 100)
    private String filename = null;

    @Column(name = "systemname", length = 100)
    private String systemname = null;

    @Column(name = "filesize")
    private Integer filesize = null;

    @Column(name = "folderid")
    private Integer folderid = null;

    @ManyToOne(targetEntity = IW3MimeTypeEntity.class)
    @JoinColumn(name = "mimetypeid", referencedColumnName = "mimetypeid", nullable = false, updatable = false)
    private IW3MimeTypeEntity mimetype = null;

    @Column(name = "description", length = 205)
    private String description = null;

    @Column(name = "keywords", length = 250)
    private String keywords = null;

    @Column(name = "checksum", length = 32)
    private String checksum = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = null;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setFileid(final Long fileid) {
        this.fileid = fileid;
    }

    public Long getFileid() {
        return fileid;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setGroupId(final Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setFiletype(final String filetype) {
        this.filetype = filetype;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setSystemname(final String systemname) {
        this.systemname = systemname;
    }

    public String getSystemname() {
        return systemname;
    }

    public void setFilesize(final Integer filesize) {
        this.filesize = filesize;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public void setFolderid(final Integer folderid) {
        this.folderid = folderid;
    }

    public Integer getFolderid() {
        return folderid;
    }

    public void setMimetype(final IW3MimeTypeEntity mimetype) {
        this.mimetype = mimetype;
    }

    public IW3MimeTypeEntity getMimetype() {
        return mimetype;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setKeywords(final String keywords) {
        this.keywords = keywords;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setChecksum(final String checksum) {
        this.checksum = checksum;
    }

    public String getChecksum() {
        return checksum;
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
