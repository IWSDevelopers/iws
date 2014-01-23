/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Copier;
import net.iaeste.iws.api.util.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Sharing info for the offer
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class OfferGroup extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String offerRefNo = null;
    private String groupId = null;
    private OfferState status = OfferState.SHARED;
    private String comment = null;
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

    /**
     * Sets the Offer Reference Number, for the Offer which the Group is granted
     * access to.<br />
     *   If the value is illegal, i.e. null or not a valid Reference Number,
     * then the method will thrown an {@code IllegalArgumentException}.
     *
     * @param offerRefNo Offer Reference Number.
     * @throws IllegalArgumentException if value is either null or invalid
     */
    public void setOfferRefNo(final String offerRefNo) throws IllegalArgumentException {
        ensureNotNullAndValidRefno("offerRefNo", offerRefNo);
        this.offerRefNo = offerRefNo;
    }

    public String getOfferRefNo() {
        return offerRefNo;
    }

    /**
     * Sets the Id of the Group that is granted access to an Offer. The GroupId
     * must be valid, otherwise the method will thrown an
     * {@code IllegalArgumentException}.
     *
     * @param groupId Group Id
     * @throws IllegalArgumentException if value is null or invalid
     */
    public void setGroupId(final String groupId) throws IllegalArgumentException {
        ensureNotNullAndValidId("groupId", groupId);
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the Status for this Offer Group relation. The status must be set,
     * and will by default be set to New.<br />
     *   The method will thrown an {@code IllegalArgumentException} if the
     * Offer State is set to null.
     *
     * @param status Offer Status
     * @throws java.lang.IllegalArgumentException if the valus is null
     */
    public void setStatus(final OfferState status) throws IllegalArgumentException {
        ensureNotNull("status", status);
        this.status = status;
    }

    public OfferState getStatus() {
        return status;
    }

    public void setComment(final String comment) {
        ensureNotTooLong("comment", comment, 500);
        this.comment = comment;
    }

    public String getComment() {
        return comment;
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
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "offerRefNo", offerRefNo);
        isNotNull(validation, "groupId", groupId);

        return validation;
    }

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

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + ((offerRefNo != null) ? offerRefNo.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + ((groupId != null) ? groupId.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + ((status != null) ? status.hashCode() : 0);

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
