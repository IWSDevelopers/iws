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
import net.iaeste.iws.api.exceptions.VerificationException;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass
 */
public final class DeleteOfferRequest extends AbstractRequest {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Long offerId;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public DeleteOfferRequest() {
        offerId = null;
    }

    /**
     * The Id of the Offer to delete.
     *
     * @param offerId Id of the Offer to delete
     */
    public DeleteOfferRequest(final Long offerId) {
        this.offerId = offerId;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setOfferId(final Long offerId) {
        this.offerId = offerId;
    }

    public Long getOfferId() {
        return offerId;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
        verifyLimits("OfferId", offerId, 1, Integer.MAX_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DeleteOfferRequest)) {
            return false;
        }

        final DeleteOfferRequest that = (DeleteOfferRequest) obj;
        return !(offerId != null ? !offerId.equals(that.offerId) : that.offerId != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return offerId != null ? offerId.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DeleteOfferRequest{" +
                "offer=" + offerId +
                '}';
    }
}
