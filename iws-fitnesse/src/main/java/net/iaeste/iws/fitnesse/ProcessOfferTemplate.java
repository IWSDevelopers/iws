package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.requests.exchange.OfferTemplateRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ProcessOfferTemplate extends AbstractFixture<Fallible> {

    private final Exchange exchange = new ExchangeCaller();
    private OfferTemplateRequest request = new OfferTemplateRequest();

    public void processOfferTemplate() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(exchange.processOfferTemplate(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
