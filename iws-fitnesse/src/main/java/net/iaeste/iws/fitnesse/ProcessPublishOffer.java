package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ProcessPublishOffer extends AbstractFixture<PublishOfferResponse> {

    private final Exchange exchange = new ExchangeCaller();
    private PublishOfferRequest request = new PublishOfferRequest();
    private Set<String> offerIds = new HashSet<String>();
    private List<String> groupIds = new ArrayList<String>();

    public void addOfferId(final String offerId) {
        offerIds.add(offerId);
    }

    public void addGroupId(final String groupId) {
        groupIds.add(groupId);
    }

    public void setNominationDeadline(final String nominationDeadline) {
        request.setNominationDeadline(new Date(nominationDeadline));
    }

    public void processPublishOffer() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        request.setOfferIds(offerIds);
        request.setGroupIds(groupIds);
        setResponse(exchange.processPublishOffer(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
