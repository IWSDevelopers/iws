/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.GroupTypeEntity
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

import net.iaeste.iws.api.enums.GroupType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries({
        @NamedQuery(name = "grouptype.findAll",
                query = "select gt from GroupTypeEntity gt"),
        @NamedQuery(name= "grouptype.findByType",
                query = "select gt from GroupTypeEntity gt " +
                        "where gt.grouptype = :type"),
        @NamedQuery(name = "grouptype.findByName",
                query = "select gt from GroupTypeEntity gt " +
                        "where lower(gt.grouptype) = lower(:name)")
})
@Entity
@Table(name = "grouptypes")
public class GroupTypeEntity implements IWSEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Column(name = "grouptype", unique = true, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private GroupType grouptype = null;

    @Column(name = "description", length = 2048, nullable = false, updatable = false)
    private String description = null;

    /**
     * This field is just a placeholder, to allow that we can use the normal IWS
     * Persistence Logic to perform database operations on this Entity.
     */
    @Transient
    private Date created = null;

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

    public void setGrouptype(final GroupType grouptype) {
        this.grouptype = grouptype;
    }

    public GroupType getGrouptype() {
        return grouptype;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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
