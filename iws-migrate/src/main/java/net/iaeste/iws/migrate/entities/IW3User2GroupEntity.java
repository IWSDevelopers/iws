/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3User2GroupEntity
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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * The IW3 User2Group table. Henrik made a correction following the problems
 * discovered with lack of uniqueness for the Id's. The Primary Key is now an
 * Embedded Id.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "usergroup.countAll",
                query = "select count(ug) from IW3User2GroupEntity ug"),
        @NamedQuery(name = "usergroup.findAll",
                query = "select ug from IW3User2GroupEntity ug " +
                        "order by ug.created asc"),
})
@Entity
@Table(name = "user2group")
public class IW3User2GroupEntity implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @EmbeddedId
    private IW3User2GroupId id = new IW3User2GroupId();

    @ManyToOne(targetEntity = IW3RolesEntity.class)
    @JoinColumn(name = "roleid", referencedColumnName = "roleid", nullable = false, updatable = false)
    private IW3RolesEntity role = null;

    @Column(name = "usertitle", length = 50)
    private byte[] usertitle = null;

    @Column(name = "onmailinglist")
    private Boolean onmailinglist = null;

    @Column(name = "mailinglistalias")
    private Boolean mailinglistalias = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = null;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setId(final IW3User2GroupId id) {
        this.id = id;
    }

    public IW3User2GroupId getId() {
        return id;
    }

    public IW3UsersEntity getUser() {
        return id.getUserId();
    }

    public IW3GroupsEntity getGroup() {
        return id.getGroupId();
    }

    public void setRole(final IW3RolesEntity roleid) {
        this.role = roleid;
    }

    public IW3RolesEntity getRole() {
        return role;
    }

    public void setUsertitle(final byte[] usertitle) {
        this.usertitle = usertitle;
    }

    public byte[] getUsertitle() {
        return usertitle;
    }

    public void setOnmailinglist(final Boolean onmailinglist) {
        this.onmailinglist = onmailinglist;
    }

    public Boolean getOnmailinglist() {
        return onmailinglist;
    }

    public void setMailinglistalias(final Boolean mailinglistalias) {
        this.mailinglistalias = mailinglistalias;
    }

    public Boolean getMailinglistalias() {
        return mailinglistalias;
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
