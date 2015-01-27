/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IWSMimeTypeEntity
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
@Table(name = "mimetypes")
public class IW3MimeTypeEntity implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "mimetypeid", nullable = false, length = 10)
    private Integer mimetypeid = null;

    @Column(name = "mimetype", length = 50)
    private String mimetype = null;

    @Column(name = "description", length = 50)
    private String description = null;

    @Column(name = "icon", length = 50)
    private String icon = null;

    @Column(name = "style", length = 25)
    private String style = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = null;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setMimetypeid(final Integer mimetypeid) {
        this.mimetypeid = mimetypeid;
    }

    public Integer getMimetypeid() {
        return mimetypeid;
    }

    public void setMimetype(final String mimetype) {
        this.mimetype = mimetype;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setStyle(final String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
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
