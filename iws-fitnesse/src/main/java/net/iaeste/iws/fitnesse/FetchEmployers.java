package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.requests.exchange.FetchEmployerInformationRequest;
import net.iaeste.iws.api.responses.exchange.FetchEmployerInformationResponse;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchEmployers extends AbstractFixture<FetchEmployerInformationResponse> {

    private final Exchange exchange = new ExchangeCaller();
    private FetchEmployerInformationRequest request = new FetchEmployerInformationRequest();

    public void fetchEmployers() {
        execute();
    }

    public void setName(final String name)
    {
        request.setName(name);
    }

    public int numberOfEmployers() {
        return getResponse() == null ? -1 : getResponse().getEmployers().size();
    }

    public String printEmployerInformation(final int employerIndex) {
        final String retVal;

        if (getResponse() == null) {
            retVal = "no response";
        } else if ((employerIndex < 1) || (employerIndex > numberOfEmployers())) {
            retVal = "no employer for given index";
        } else {
            retVal = getResponse().getEmployers().get(employerIndex - 1).toString();
        }

        return retVal;
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(exchange.fetchEmployers(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
