/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import net.iaeste.iws.api.constants.IWSConstants;
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
 * @since   IWS 1.0
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

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Column(name = "grouptype", unique = true, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private GroupType grouptype = null;

    @Column(name = "who_may_join", length = 10, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private GroupType.WhoMayJoin whoMayJoin = null;

    @Column(name = "description", length = 2048, nullable = false, updatable = false)
    private String description = null;

    @Column(name = "private_list", nullable = false, updatable = false)
    private Boolean privateList = null;

    @Column(name = "public_list", nullable = false, updatable = false)
    private Boolean publicList = null;

    @Column(name = "folder_type", length = 10, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private GroupType.FolderType folderType = null;

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
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Long getId() {
        return id;
    }

    public final void setGrouptype(final GroupType grouptype) {
        this.grouptype = grouptype;
    }

    public final GroupType getGrouptype() {
        return grouptype;
    }

    public final void setWhoMayJoin(final GroupType.WhoMayJoin whoMayJoin) {
        this.whoMayJoin = whoMayJoin;
    }

    public final GroupType.WhoMayJoin getWhoMayJoin() {
        return whoMayJoin;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final String getDescription() {
        return description;
    }

    public final void setPrivateList(final Boolean privateList) {
        this.privateList = privateList;
    }

    public final Boolean getPrivateList() {
        return privateList;
    }

    public final void setPublicList(final Boolean publicList) {
        this.publicList = publicList;
    }

    public final Boolean getPublicList() {
        return publicList;
    }

    public final void setFolderType(final GroupType.FolderType folderType) {
        this.folderType = folderType;
    }

    public final GroupType.FolderType getFolderType() {
        return folderType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Date getCreated() {
        return created;
    }
}
