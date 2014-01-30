/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3GroupsEntity
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@NamedQueries(@NamedQuery(name = "groups.findAll",
        query = "select g from IW3GroupsEntity g " +
                "order by g.groupid asc"))
@Entity
@Table(name = "groups")
public class IW3GroupsEntity implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "groupid", nullable = false, length = 10)
    private Integer groupid = null;

    @OneToOne(targetEntity = IW3GrouptypesEntity.class)
    @JoinColumn(name = "grouptypeid", referencedColumnName = "grouptypeid", nullable = false, updatable = false)
    private IW3GrouptypesEntity grouptype = null;

    @Column(name = "groupname", length = 50)
    private byte[] groupname = null;

    @Column(name = "fullname", length = 100)
    private byte[] fullname = null;

    @Column(name = "grouptitle", length = 100)
    private String grouptitle = null;

    @Column(name = "groupdescription", length = 250)
    private byte[] groupdescription = null;

    @Column(name = "mailinglistname", length = 75)
    private byte[] mailinglistname = null;

    @Column(name = "mailinglistlimit", length = 10)
    private Integer mailinglistlimit = null;

    @Column(name = "mailinglisttype", length = 10)
    private String mailinglisttype = null;

    @Column(name = "discsize", length = 10)
    private Integer discsize = null;

    @Column(name = "discused", length = 10)
    private Integer discused = null;

    @Column(name = "status", length = 10)
    private String status = null;

    @Column(name = "parentid", length = 10)
    private Integer parentid = null;

    @Column(name = "parentstatus", length = 10)
    private String parentstatus = null;

    @Column(name = "countryid", length = 2)
    private String countryid = null;

    @Column(name = "realcountryid", length = 2)
    private String realcountryid = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = null;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setGroupid(final Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGrouptype(final IW3GrouptypesEntity grouptype) {
        this.grouptype = grouptype;
    }

    public IW3GrouptypesEntity getGrouptype() {
        return grouptype;
    }

    public void setGroupname(final byte[] groupname) {
        this.groupname = groupname;
    }

    public byte[] getGroupname() {
        return groupname;
    }

    public void setFullname(final byte[] fullname) {
        this.fullname = fullname;
    }

    public byte[] getFullname() {
        return fullname;
    }

    public void setGrouptitle(final String grouptitle) {
        this.grouptitle = grouptitle;
    }

    public String getGrouptitle() {
        return grouptitle;
    }

    public void setGroupdescription(final byte[] groupdescription) {
        this.groupdescription = groupdescription;
    }

    public byte[] getGroupdescription() {
        return groupdescription;
    }

    public void setMailinglistname(final byte[] mailinglistname) {
        this.mailinglistname = mailinglistname;
    }

    public byte[] getMailinglistname() {
        return mailinglistname;
    }

    public void setMailinglistlimit(final Integer mailinglistlimit) {
        this.mailinglistlimit = mailinglistlimit;
    }

    public Integer getMailinglistlimit() {
        return mailinglistlimit;
    }

    public void setMailinglisttype(final String mailinglisttype) {
        this.mailinglisttype = mailinglisttype;
    }

    public String getMailinglisttype() {
        return mailinglisttype;
    }

    public void setDiscsize(final Integer discsize) {
        this.discsize = discsize;
    }

    public Integer getDiscsize() {
        return discsize;
    }

    public void setDiscused(final Integer discused) {
        this.discused = discused;
    }

    public Integer getDiscused() {
        return discused;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setParentid(final Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentstatus(final String parentstatus) {
        this.parentstatus = parentstatus;
    }

    public String getParentstatus() {
        return parentstatus;
    }

    public void setCountryid(final String countryid) {
        this.countryid = countryid;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setRealcountryid(final String realcountryid) {
        this.realcountryid = realcountryid;
    }

    public String getRealcountryid() {
        return realcountryid;
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
