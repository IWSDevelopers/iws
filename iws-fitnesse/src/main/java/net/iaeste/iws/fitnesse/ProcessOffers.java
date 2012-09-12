/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.OfferVerify
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ProcessOffers extends AbstractFixture<OfferResponse> {
    private Exchange exchange = new ExchangeCaller();
    private AuthenticationToken token;
    private Offer offer;

    public void setToken(final String token) {
        if (token == null) {
            this.token = null;
        } else {
            this.token = new AuthenticationToken(token);
        }
    }

    @Override
    public void execute() throws StopTestException {
        final ProcessOfferRequest request = buildRequest();

        response = exchange.processOffer(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        super.reset();

        offer = null;
    }

    private ProcessOfferRequest buildRequest() {
        final ProcessOfferRequest request = new ProcessOfferRequest();
        request.setOffer(offer);
        return request;
    }

    public void setOffer(final String offer) {
        switch (offer) {
            case "minimal":
                this.offer = OfferTestUtility.getMinimalOffer();
                break;
            case "full":
                this.offer = OfferTestUtility.getFullOffer();
                break;
            default:
                this.offer = OfferTestUtility.getEmptyOffer();
                break;
        }
    }
}