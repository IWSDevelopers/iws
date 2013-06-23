package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.responses.exchange.FetchPublishGroupResponse;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchPublishGroups extends AbstractFixture<FetchPublishGroupResponse> {

    private final Exchange exchange = new ExchangeCaller();
    private FetchPublishGroupsRequest request = new FetchPublishGroupsRequest();

    public void fetchPublishGroups() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(exchange.fetchPublishGroups(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
