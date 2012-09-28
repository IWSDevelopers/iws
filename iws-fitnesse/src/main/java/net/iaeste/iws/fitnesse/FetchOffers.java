/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.fitnesse.callers.AccessCaller;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class FetchOffers extends AbstractFixture<FetchOffersResponse> {
    private final Exchange exchange = new ExchangeCaller();
    private final AccessCaller ac = new AccessCaller();
    private AuthenticationToken token;
    private FetchOffersRequest request;
    private String username;
    private String password;

    public FetchOffers() {
        reset();

    }

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
     * @param username
     * @param password
     */
    public void setUsernameAndPassword(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setToken(final String token) {
        this.token = new AuthenticationToken(token);
    }

    /** @return number of offers fetched otherwise or -1 if there is no response */
    public int numberOfFetchedOffers() {
        if (response == null) {
            return -1;
        }
        return response.getOffers().size();
    }

    /**
     * prints offer
     *
     * @param pfferIndex index of offer to display
     * @return String representation of offer or error message if offer does not exist for given number
     */
    public String printOffer(final int pfferIndex) {
        if (response == null) {
            return "no response";
        }
        if (pfferIndex < 1 || pfferIndex > numberOfFetchedOffers()) {
            return "no offer for given index";
        }
        return response.getOffers().get(pfferIndex - 1).toString();
    }

    /** alias function for execute */
    public void fetch() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        getToken();
        response = exchange.fetchOffers(token, request);
    }

    @Override
    public void reset() {
        request = null;
        response = null;
        username = null;
        password = null;
        token = null;
    }

    private void getToken() {
        if (token == null) {
            final AuthenticationRequest authRequest = new AuthenticationRequest(username, password);
            final AuthenticationResponse authResponse = ac.generateSession(authRequest);
            token = authResponse.getToken();
        }
    }
}
