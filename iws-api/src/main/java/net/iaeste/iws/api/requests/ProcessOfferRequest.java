/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.ProcessOfferRequest
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
import net.iaeste.iws.api.exceptions.VerificationException;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection CastToConcreteClass
 * @since   1.7
 */
public final class ProcessOfferRequest extends AbstractRequest {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The Offer Object to process. */
    private Offer offer;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public ProcessOfferRequest() {
        offer = null;
    }

    /**
     * Default Constructor, sets the Offer to be processed. If the Offer exists,
     * it will be updated otherwise a new Offer will be created.
     *
     * @param offer object to create or update
     */
    public ProcessOfferRequest(final Offer offer) {
        this.offer = new Offer(offer);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setOffer(final Offer offer) {
        this.offer = new Offer(offer);
    }

    public Offer getOffer() {
        return new Offer(offer);
    }

    // Todo Kim; @Michal, The following should not be part of this Object, this is business Logic decisions
    public boolean isCreateRequest() {
        if (offer == null) {
            return false;
        }
        return offer.getId() == null;
    }

    public boolean isUpdateRequest() {
        if (offer == null) {
            return false;
        }
        return offer.getId() != null;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
        verifyObject("Offer", offer);
    }

    // ToDo Kim; @Michael, The following methods are not requied or needed for Request Objects, only for DTO objects
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ProcessOfferRequest)) {
            return false;
        }

        final ProcessOfferRequest that = (ProcessOfferRequest) obj;
        return !(offer != null ? !offer.equals(that.offer) : that.offer != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return offer != null ? offer.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ProcessOfferRequest{" +
                "offer=" + offer +
                '}';
    }
}
