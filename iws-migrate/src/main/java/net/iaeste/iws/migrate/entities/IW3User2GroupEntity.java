/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
 * The IW3 User2Group is having a problem - we don't have any Id value for it,
 * and given the duplicate entries, we cannot just add a composite Id! Hence,
 * the trick is to update the table with the following queries before the
 * migration is performed:
 * <pre>
 *   begin;
 *   create sequence user2group_sequence start with 1 increment by 1;
 *   alter table user2group add id integer not null primary key;
 *   update user2group set id = nextval('user2group_sequence');
 *   commit;
 * </pre>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries(
        @NamedQuery(name = "usergroup.findAll",
        query = "select ug from IW3User2GroupEntity ug")
)
@Entity
@Table(name = "user2group")
public class IW3User2GroupEntity {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "user2group_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Integer id = null;

    @ManyToOne(targetEntity = IW3UsersEntity.class)
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false, updatable = false)
    private IW3UsersEntity user = null;

    @ManyToOne(targetEntity = IW3GroupsEntity.class)
    @JoinColumn(name = "groupid", referencedColumnName = "groupid", nullable = false, updatable = false)
    private IW3GroupsEntity group = null;

    @ManyToOne(targetEntity = IW3RolesEntity.class)
    @JoinColumn(name = "roleid", referencedColumnName = "roleid", nullable = false, updatable = false)
    private IW3RolesEntity role = null;

    @Column(name = "usertitle", length = 50)
    private String usertitle = null;

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

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUser(final IW3UsersEntity userid) {
        this.user = userid;
    }

    public IW3UsersEntity getUser() {
        return user;
    }

    public void setGroup(final IW3GroupsEntity groupid) {
        this.group = groupid;
    }

    public IW3GroupsEntity getGroup() {
        return group;
    }

    public void setRole(final IW3RolesEntity roleid) {
        this.role = roleid;
    }

    public IW3RolesEntity getRole() {
        return role;
    }

    public void setUsertitle(final String usertitle) {
        this.usertitle = usertitle;
    }

    public String getUsertitle() {
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
