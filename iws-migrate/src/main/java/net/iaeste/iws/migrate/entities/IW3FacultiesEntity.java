/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3FacultiesEntity
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries({
        @NamedQuery(name = "faculty.findByName",
                query = "select f from IW3FacultiesEntity f " +
                        "where lower(f.faculty) = lower(:name)"),
        @NamedQuery(name = "faculty.findByXml",
                query = "select f from IW3FacultiesEntity f " +
                        "where lower(f.facultyxml) = lower(:xml)")
})
@Entity
@Table(name = "faculties")
public class IW3FacultiesEntity implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "facultyid", nullable = false, length = 10)
    private Integer facultyid = null;

    @Column(name = "faculty", nullable = false, length = 2147483647)
    private byte[] faculty = null;

    @Column(name = "facultyxml", nullable = false, length = 2147483647)
    private String facultyxml = null;

    @Column(name = "description", length = 2147483647)
    private String description = null;

    @Column(name = "status", length = 10)
    private String status = null;

    @Column(name = "facultycategory", length = 2147483647)
    private String facultycategory = null;

    @Column(name = "facultysubject", length = 2147483647)
    private String facultysubject = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = null;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setFacultyid(final Integer facultyid) {
        this.facultyid = facultyid;
    }

    public Integer getFacultyid() {
        return facultyid;
    }

    public void setFaculty(final byte[] faculty) {
        this.faculty = faculty;
    }

    public byte[] getFaculty() {
        return faculty;
    }

    public void setFacultyxml(final String facultyxml) {
        this.facultyxml = facultyxml;
    }

    public String getFacultyxml() {
        return facultyxml;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setFacultycategory(final String facultycategory) {
        this.facultycategory = facultycategory;
    }

    public String getFacultycategory() {
        return facultycategory;
    }

    public void setFacultysubject(final String facultysubject) {
        this.facultysubject = facultysubject;
    }

    public String getFacultysubject() {
        return facultysubject;
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
