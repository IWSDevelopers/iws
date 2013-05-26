package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: martin
 * Date: 5/12/13
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public final class FetchCountries extends AbstractFixture<FetchCountryResponse> {

    private final Administration administration = new AdministrationCaller();
    private FetchCountryRequest request = new FetchCountryRequest();

    public void setCountryIds(final String countryIds)
    {
        request.setCountryIds(new ArrayList<String>(Arrays.asList(countryIds.split(","))));
    }

    public void setMembership(final String membership)  {
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
