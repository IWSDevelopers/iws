/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.FetchCountries
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

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchCountries extends AbstractFixture<FetchCountryResponse> {

    private static final Pattern SPLIT_ON_COMMA = Pattern.compile(",");
    private final Administration administration = new AdministrationCaller();
    private FetchCountryRequest request = new FetchCountryRequest();

    public void setCountryIds(final String countryIds) {
        request.setCountryIds(new ArrayList<>(Arrays.asList(SPLIT_ON_COMMA.split(countryIds))));
    }

    public void setMembership(final String membership) {
        request.setMembership(Membership.valueOf(membership));
    }

    public int numberOfCountries() {
        return getResponse() == null ? -1 : getResponse().getCountries().size();
    }

    public String printCountry(final int countryIndex) {
        final String retVal;

        if (getResponse() == null) {
            retVal = "no response";
        } else if ((countryIndex < 1) || (countryIndex > numberOfCountries())) {
            retVal = "no country for given index";
        } else {
            retVal = getResponse().getCountries().get(countryIndex - 1).toString();
        }

        return retVal;
    }

    public void fetchCountries() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(administration.fetchCountries(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
