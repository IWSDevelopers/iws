/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.PermissionEntity
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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries({
        @NamedQuery(name = "permission.findAll",
                query = "select p from PermissionEntity p"),
        @NamedQuery(name = "permission.findAllNotRestricted",
                query = "select p from PermissionEntity p " +
                        "where p.restricted = false"),
        @NamedQuery(name = "permission.findByName",
                query = "select p from PermissionEntity p " +
                        "where p.permission = :permission")
})
@Entity
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * The name of the Permission.
     */
    @Column(name = "permission", length = 50, unique = true, nullable = false, updatable = false)
    private String permission= null;

    /**
     * Determines if usage of this Permission is restricted or not, if the value
     * is true, then it is restricted and cannot be used for customized Roles.
     */
    @Column(name = "restricted", nullable = false, updatable = false)
    private Boolean restricted = null;

    /**
     * Description of the Permission.
     */
    @Column(name = "description", length = 2048, nullable = false, updatable = false)
    private String description = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPermission(final String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setRestricted(final Boolean restricted) {
        this.restricted = restricted;
    }

    public Boolean getRestricted() {
        return restricted;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
