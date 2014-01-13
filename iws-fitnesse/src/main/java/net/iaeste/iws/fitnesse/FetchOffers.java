/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.FetchOffers
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
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * The flow with using the Token is not correct. You should create a Token as
 * the first action, and deprecate it as the last.
 *
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchOffers extends AbstractFixture<FetchOffersResponse> {

    // We need to use the Callers, since it wraps the IWS calls with Exception
    // handling, to throw StopTest Exceptions, which FitNesse requires
    private final Exchange exchange = new ExchangeCaller();
    private FetchOffersRequest request = null;

    /**
     * specify which offers should be fetched
     *
     * @param fetchType String value of FetchType enum
     */
    public void setRequestType(final String fetchType) {
        request = new FetchOffersRequest(FetchType.valueOf(fetchType));
    }

    /**
     * Sets username and password so the AuthenticationToken could be fetched.
     *
     * @param username Username
     * @param password Password
     */
    public void setUsernameAndPassword(final String username, final String password) {
        setUsername(username);
        setPassword(password);
    }

    /**
     * @return number of offers fetched otherwise or -1 if there is no response
     */
    public int numberOfFetchedOffers() {
        return getResponse() == null ? -1 : getResponse().getOffers().size();
    }

    /**
     * prints offer
     *
     * @param offerIndex index of offer to display
     * @return String representation of offer or error message if offer does not
     *         exist for given number
     */
    public String printOffer(final int offerIndex) {
        final String retVal;

        if (getResponse() == null) {
            retVal = "no response";
        } else if ((offerIndex < 1) || (offerIndex > numberOfFetchedOffers())) {
            retVal = "no offer for given index";
        } else {
            retVal = getResponse().getOffers().get(offerIndex - 1).toString();
        }

        return retVal;
    }

    /**
     * Alias for the execute function.
     */
    public void fetch() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(exchange.fetchOffers(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
