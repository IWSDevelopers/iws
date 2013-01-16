/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.OfferGroup
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.*;

import java.util.*;

/**
 * Sharing info for the offer
 *
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class OfferGroup extends AbstractFallible {

    /** {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String offerRefNo = null;
    private String groupExternalId = null;
    private DateTime answered = null;
    private DateTime modified = null;
    private DateTime created = null;

    /**
     * Empty Constructor, required for some communication frameworks.
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
            groupExternalId = offerGroup.groupExternalId;
            answered = Copier.copy(offerGroup.answered);
            modified = Copier.copy(offerGroup.modified);
            created = Copier.copy(offerGroup.created);
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================


    public String getOfferRefNo() {
        return offerRefNo;
    }

    public void setOfferRefNo(String refNo) {
        this.offerRefNo = refNo;
    }

    public String getGroupExternalId() {
        return groupExternalId;
    }

    public void setGroupExternalId(String groupExternalId) {
        this.groupExternalId = groupExternalId;
    }

    public DateTime getAnswered() {
        return answered;
    }

    public void setAnswered(DateTime answered) {
        this.answered = answered;
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

        if (offerRefNo != null ? !offerRefNo.equalsIgnoreCase(offerGroup.offerRefNo) : offerGroup.offerRefNo != null) {
            return false;
        }
        if (groupExternalId != null ? !groupExternalId.equalsIgnoreCase(offerGroup.groupExternalId) : offerGroup.groupExternalId != null) {
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
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (groupExternalId != null ? groupExternalId.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "OfferGroup{" +
                "offerRefNo=" + offerRefNo +
                ", group.externalId=" + groupExternalId +
                ", answered=" + answered +
                ", modified=" + modified +
                ", created=" + created +
                '}';
    }
}
