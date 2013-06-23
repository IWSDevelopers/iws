package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class DeleteOffer extends AbstractFixture<OfferResponse> {

    private final Exchange exchange = new ExchangeCaller();
    private DeleteOfferRequest request = new DeleteOfferRequest();

    public void deleteOffer() {
        execute();
    }

    public void setOfferRefNo(final String offerRefNo) {
        request.setOfferRefNo(offerRefNo);
    }

    public void printOffer() {
        getResponse().getOffer().toString();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(exchange.deleteOffer(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
