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
import net.iaeste.iws.api.exceptions.NotImplementedException;

import java.util.List;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class OfferResponse extends AbstractResponse {

    /**
     * {@link IWSConstants#SERIAL_VERSION_UID}.
     */
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
     * Incorrect Offer should never be passed to this constructor. Instead use constructor without list of errors parameter.
     *
     * @param failedOffer list of offer for which something went wrong
     */
    public OfferResponse(final Offer failedOffer, final List<String> errors) {
        super(IWSErrors.PROCESSING_FAILURE, "processing of the Offer failed");
        this.offer = new Offer(failedOffer);
        this.errors = errors;
    }

    public Offer getOffer() {
        return new Offer(offer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        throw new NotImplementedException("TBD");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        throw new NotImplementedException("TBD");
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        throw new NotImplementedException("TBD");
    }
}
