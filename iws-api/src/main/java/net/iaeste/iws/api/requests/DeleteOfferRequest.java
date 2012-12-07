/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.DeleteOfferRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class DeleteOfferRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String offerRefNo;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public DeleteOfferRequest() {
        offerRefNo = null;
    }

    /**
     * The Id of the Offer to delete.
     *
     * @param offerRefNo Id of the Offer to delete
     */
    public DeleteOfferRequest(final String offerRefNo) {
        this.offerRefNo = offerRefNo;
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

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /** {@inheritDoc} */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        final Offer o = new Offer();
        o.setRefNo(offerRefNo);
        final String refNoKey = "refNo";

        final Map<String, String> errors = o.validate();
        if (errors.containsKey(refNoKey)) {
            validation.put(refNoKey, errors.get(refNoKey));
        }

        return validation;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DeleteOfferRequest)) {
            return false;
        }

        final DeleteOfferRequest that = (DeleteOfferRequest) obj;
        return !(offerRefNo != null ? !offerRefNo.equals(that.offerRefNo) : that.offerRefNo != null);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return offerRefNo != null ? offerRefNo.hashCode() : 0;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "DeleteOfferRequest{" +
                "offer=" + offerRefNo +
                '}';
    }
}
