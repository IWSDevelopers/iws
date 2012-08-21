/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.FetchOfferResponse
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
import net.iaeste.iws.api.utils.Copier;

import java.util.List;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class FetchOffersResponse extends AbstractResponse {

    /**
     * {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private final List<Offer> offers;
    // TODO: To Be Discussed
    private final List<String> errors;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchOffersResponse() {
        super(IWSErrors.SUCCESS, IWSConstants.SUCCESS);
        offers = null;
        errors = null;
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public FetchOffersResponse(final IWSError error, final String message) {
        super(error, message);
        offers = null;
        errors = null;
    }

    public FetchOffersResponse(List<Offer> offers) {
        this.offers = offers;
        this.errors = null;
    }


    public List<Offer> getOffers() {
        return Copier.copy(offers);
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
    public String toString() {
        throw new NotImplementedException("TBD");
    }
}
