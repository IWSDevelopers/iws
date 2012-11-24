/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.OfferResponse
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.util.AbstractFallible;
import net.iaeste.iws.api.util.Copier;

import java.util.List;

/**
 * ToDo Kim; Michal, there is no way to create a positive response, i.e. without error
 * ToDo Kim; Michal, we need both setters and getters, including for the errors
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass
 */
public final class OfferResponse extends AbstractFallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private final Offer offer;
    private final List<String> errors;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public OfferResponse() {
        super(IWSErrors.SUCCESS, IWSConstants.SUCCESS);
        offer = null;
        errors = null;
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public OfferResponse(final IWSError error, final String message) {
        super(error, message);
        offer = null;
        errors = null;
    }

    /**
     * Response is created when processing the offer failed.
     * <p/>
     * Incorrect Offer should never be passed to this constructor. Instead use
     * constructor without list of errors parameter.
     *
     * @param failedOffer list of offer for which something went wrong
     */
    public OfferResponse(final Offer failedOffer, final List<String> errors) {
        super(IWSErrors.PROCESSING_FAILURE, "processing of the Offer failed");
        offer = new Offer(failedOffer);
        this.errors = Copier.copy(errors);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public Offer getOffer() {
        return new Offer(offer);
    }

    // =========================================================================
    // Standard Response Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final OfferResponse that = (OfferResponse) obj;

        if (errors != null ? !errors.equals(that.errors) : that.errors != null) {
            return false;
        }

        return !(offer != null ? !offer.equals(that.offer) : that.offer != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (offer != null ? offer.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (errors != null ? errors.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "OfferResponse{" +
                "offer=" + offer +
                ", errors=" + errors +
                '}';
    }
}
