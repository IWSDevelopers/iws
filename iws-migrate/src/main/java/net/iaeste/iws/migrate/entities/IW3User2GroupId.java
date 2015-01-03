/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3User2GroupId
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

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Internal Class, used as Embedded Id, it contains the combined Primary
 * Key for the View. This is required, since Views normally doesn't have a
 * single column, which can act as the primary key by itself. Hence, for
 * the UserPermission View, the combined UserId, GroupId & PermissionId
 * makes up the Primary Key.
 */
@Embeddable
public class IW3User2GroupId implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @ManyToOne(targetEntity = IW3UsersEntity.class)
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false, updatable = false)
    private IW3UsersEntity user = null;

    @ManyToOne(targetEntity = IW3GroupsEntity.class)
    @JoinColumn(name = "groupid", referencedColumnName = "groupid", nullable = false, updatable = false)
    private IW3GroupsEntity group = null;

    /**
     * Default Empty JPA Constructor.
     */
    protected IW3User2GroupId() {
        user = null;
        group = null;
    }

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setUser(final IW3UsersEntity user) {
        this.user = user;
    }

    public IW3UsersEntity getUserId() {
        return user;
    }

    public void setGroup(final IW3GroupsEntity group) {
        this.group = group;
    }

    public IW3GroupsEntity getGroupId() {
        return group;
    }
}
