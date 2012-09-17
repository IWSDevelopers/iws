/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.ShouldFetchOffer
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
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ShouldFetchOffer extends AbstractFixture<FetchOffersResponse> {
    private static final String TOKEN = "12345678901234567890123456789012";
    private final Exchange exchange = new ExchangeCaller();
    private AuthenticationToken token;
    private FetchOffersRequest request;

    public void setRequestType(final String fetchType) {
        request = new FetchOffersRequest(FetchType.valueOf(fetchType));
    }


    public int numberOfFetchedOffers() {
        return response.getOffers().size();
    }


    @Override
    public void execute() throws StopTestException {
        response = exchange.fetchOffers(token, request);
    }

    @Override
    public void reset() {
        token = new AuthenticationToken(TOKEN);
        request = null;
        response = null;
    }
}
