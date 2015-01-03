/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Embeddable
public class IW3Offer2GroupId implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Column(name = "offerid", nullable = false, updatable = false)
    private Integer offerId = null;

    @Column(name = "groupid", nullable = false, updatable = false)
    private Integer groupId = null;

    /**
     * Default Empty JPA Constructor.
     */
    protected IW3Offer2GroupId() {
        offerId = null;
        groupId = null;
    }

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setOfferId(final Integer offerId) {
        this.offerId = offerId;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setGroupId(final Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }
}
