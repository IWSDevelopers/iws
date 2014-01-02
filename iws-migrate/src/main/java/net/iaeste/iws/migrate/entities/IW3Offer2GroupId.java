/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3Offer2GroupId
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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Embeddable
public class IW3Offer2GroupId implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @ManyToOne(targetEntity = IW3OffersEntity.class)
    @JoinColumn(name = "offerid", referencedColumnName = "offerid", nullable = false, updatable = false)
    private IW3OffersEntity offer = null;

    @ManyToOne(targetEntity = IW3GroupsEntity.class)
    @JoinColumn(name = "groupid", referencedColumnName = "groupid", nullable = false, updatable = false)
    private IW3GroupsEntity group = null;

    /**
     * Default Empty JPA Constructor.
     */
    protected IW3Offer2GroupId() {
        offer = null;
        group = null;
    }

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setOffer(final IW3OffersEntity offer) {
        this.offer = offer;
    }

    public IW3OffersEntity getOffer() {
        return offer;
    }

    public void setGroup(final IW3GroupsEntity group) {
        this.group = group;
    }

    public IW3GroupsEntity getGroup() {
        return group;
    }
}
