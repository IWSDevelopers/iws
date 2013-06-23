package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.requests.exchange.FetchGroupsForSharingRequest;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchGroupsForSharing extends AbstractFixture<FetchGroupsForSharingResponse> {

    private final Exchange exchange = new ExchangeCaller();
    private FetchGroupsForSharingRequest request = new FetchGroupsForSharingRequest();

    public void fetchGroupsForSharing() {
        execute();
    }

    public int numberOfGroups() {
        return getResponse() == null ? -1 : getResponse().getGroups().size();
    }

    public String printGroup(final int groupIndex) {
        final String retVal;

        if (getResponse() == null) {
            retVal = "no response";
        } else if ((groupIndex < 1) || (groupIndex > numberOfGroups())) {
            retVal = "no group for given index";
        } else {
            retVal = getResponse().getGroups().get(groupIndex - 1).toString();
        }

        return retVal;
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(exchange.fetchGroupsForSharing(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
