/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.ForeignOfferStatisticsId
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.views;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.exchange.OfferState;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * Internal Class, used as Embedded Id, it contains the combined Primary
 * Key for the View. This is required, since Views normally doesn't have a
 * single column, which can act as the primary key by itself. Hence, for
 * the UserPermission View, the combined UserId, GroupId & PermissionId
 * makes up the Primary Key.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Embeddable
public class ForeignOfferStatisticsId implements Serializable {

    /** {@see IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, insertable = false, updatable = false)
    private OfferState status = null;

    @Column(name = "group_id", nullable = false, insertable = false, updatable = false)
    private Long groupId = null;

    /**
     * Default Empty JPA Constructor.
     */
    public ForeignOfferStatisticsId() {
        status = null;
        groupId = null;
    }

    public void setStatus(final OfferState status) {
        this.status = status;
    }

    public OfferState getStatus() {
        return status;
    }

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    // =========================================================================
    // Methods required by JPA
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ForeignOfferStatisticsId)) {
            return false;
        }

        final ForeignOfferStatisticsId that = (ForeignOfferStatisticsId) obj;

        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) {
            return false;
        }

        return status == that.status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (groupId != null ? groupId.hashCode() : 0);

        return result;
    }
}
