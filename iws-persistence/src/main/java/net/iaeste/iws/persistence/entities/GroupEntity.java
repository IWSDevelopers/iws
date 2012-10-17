/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.GroupEntity
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@NamedQueries({
        @NamedQuery(
                name = "gorup.findAll",
                query = "select g from GroupEntity g"),
        @NamedQuery(
                name = "group.findById",
                query = "select g from GroupEntity g " +
                        "where g.id = :id")
})
@Entity
@Table(name = "groups")
public class GroupEntity implements Object {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "groupname")
    private String groupName;

    /**
     * Empty Constructor, JPA requirement.
     */
    public GroupEntity() {
        id = null;
        groupName = null;
    }

    /**
     * Default Constructor, for creating new entity.
     *
     * @param groupName  Group Name
     */
    public GroupEntity(final String groupName) {
        id = null;
        this.groupName = groupName;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
