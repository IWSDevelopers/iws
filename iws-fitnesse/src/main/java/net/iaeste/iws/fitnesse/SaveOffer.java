/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.SaveOffer
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
public final class SaveOffer extends AbstractFixture<OfferResponse> {
    private static final String TOKEN = "12345678901234567890123456789012";
    private final Exchange exchange = new ExchangeCaller();
    private AuthenticationToken token;
    private Offer offer;
    private ProcessOfferRequest request;

    public SaveOffer() {
        reset();
    }

    /**
     * alias function for execute
     */
    public void save() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        request = new ProcessOfferRequest(offer);
        response = exchange.processOffer(token, request);
    }

    @Override
    public void reset() {
        request = null;
        response = null;
        token = new AuthenticationToken(TOKEN);
        offer = OfferTestUtility.getMinimalOffer();
    }
}
