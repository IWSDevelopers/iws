/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.OfferGroup
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.util.AbstractFallible;
import net.iaeste.iws.api.util.Copier;
import net.iaeste.iws.api.util.DateTime;

/**
 * Sharing info for the offer
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class OfferGroup extends AbstractFallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private OfferState status = null;
    private String offerRefNo = null;
    private String groupId = null;
    private DateTime modified = null;
    private DateTime created = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public OfferGroup() {
    }

    /**
     * Copy constructor.
     * <p/>
     *
     * @param offerGroup OfferGroup to copy
     */
    public OfferGroup(final OfferGroup offerGroup) {
        if (offerGroup != null) {
            offerRefNo = offerGroup.offerRefNo;
            groupId = offerGroup.groupId;
            modified = Copier.copy(offerGroup.modified);
            created = Copier.copy(offerGroup.created);
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setOfferRefNo(final String offerRefNo) {
        this.offerRefNo = offerRefNo;
    }

    public String getOfferRefNo() {
        return offerRefNo;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public OfferState getStatus() {
        return status;
    }

    public void setStatus(final OfferState status) {
        this.status = status;
    }

    /**
     * For internal use only.
     *
     * @param modified DateTime of last modification of the OfferGroup
     */
    public void setModified(final DateTime modified) {
        this.modified = modified;
    }

    public DateTime getModified() {
        return modified;
    }

    /**
     * For internal use only.
     *
     * @param created DateTime of the creation of the Offer
     */
    public void setCreated(final DateTime created) {
        this.created = created;
    }

    public DateTime getCreated() {
        return created;
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OfferGroup)) {
            return false;
        }

        final OfferGroup offerGroup = (OfferGroup) obj;

        if ((offerRefNo != null) ? !offerRefNo.equalsIgnoreCase(offerGroup.offerRefNo) : (offerGroup.offerRefNo != null)) {
            return false;
        }
        if ((groupId != null) ? !groupId.equals(offerGroup.groupId) : (offerGroup.groupId != null)) {
            return false;
        }
        if (status != offerGroup.status) {
            return false;
        }

        // #modified and #created are not relevant for the equality of the offers.
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (offerRefNo != null ? offerRefNo.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (groupId != null ? groupId.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (status != null ? status.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "OfferGroup{" +
                "offerRefNo=" + offerRefNo +
                ", groupId=" + groupId +
                ", status=" + status +
                ", modified=" + modified +
                ", created=" + created +
                '}';
    }
}
