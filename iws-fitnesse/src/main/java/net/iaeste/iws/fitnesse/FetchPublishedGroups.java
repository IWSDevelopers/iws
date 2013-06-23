package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchPublishedGroups extends AbstractFixture<FetchPublishedGroupsResponse> {

    private final Exchange exchange = new ExchangeCaller();
    private FetchPublishedGroupsRequest request = new FetchPublishedGroupsRequest();
    private List<String> offerIds = new ArrayList<String>();

    public void addOfferId(final String offerId)
    {
        offerIds.add(offerId);
    }

    public int numberOfOfferGroups() {
        return getResponse() == null ? -1 : getResponse().getOffersGroups().size();
    }

    public String printOfferGroup(final int offerGroupIndex) {
        final String retVal;

        if (getResponse() == null) {
            retVal = "no response";
        } else if ((offerGroupIndex < 1) || (offerGroupIndex > numberOfOfferGroups())) {
            retVal = "no offer group for given index";
        } else {
            retVal = getResponse().getOffersGroups().get(offerGroupIndex - 1).toString();
        }

        return retVal;
    }

    public void fetchPublishedGroups() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        request.setOfferIds(offerIds);
        setResponse(exchange.fetchPublishedGroups(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
