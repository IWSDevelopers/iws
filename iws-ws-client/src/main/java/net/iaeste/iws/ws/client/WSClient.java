package net.iaeste.iws.ws.client;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.ws.client.clients.AccessWSClient;
import net.iaeste.iws.ws.client.clients.ExchangeWSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

/**
 * Sample Client to communicate with the IWS WebServices.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class WSClient {

    private static final Logger log = LoggerFactory.getLogger(WSClient.class);

    private WSClient() {
    }

    public static void main(final String[] args) throws MalformedURLException {
        final AccessWSClient client = new AccessWSClient("http://localhost:8080/iws-ws/accessWS?wsdl");
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest("kim@dawn.dk", "faked");
        final AuthenticationResponse authenticationResponse = client.generateSession(authenticationRequest);
        final AuthenticationToken token = authenticationResponse.getToken();
        log.debug("CreateSession Error = " + authenticationResponse.getError());
        log.debug("CreateSession Message = " + authenticationResponse.getMessage());
        if (authenticationResponse.isOk()) {
            final ExchangeWSClient exchange = new ExchangeWSClient("http://localhost:8080/iws-ws/exchangeWS?wsdl");
            final OfferStatisticsRequest statRequest = new OfferStatisticsRequest();
            final OfferStatisticsResponse statResponse = exchange.fetchOfferStatistics(token, statRequest);
            log.info("Offer Statistics: " + statResponse.getMessage());
            final FetchOffersRequest offerRequest = new FetchOffersRequest();
            offerRequest.setFetchType(FetchType.DOMESTIC);
            final FetchOffersResponse domesticResponse = exchange.fetchOffers(token, offerRequest);
            log.info("Fetch Domestic Offers: " + domesticResponse.getMessage());
            offerRequest.setFetchType(FetchType.SHARED);
            final FetchOffersResponse sharedResponse = exchange.fetchOffers(token, offerRequest);
            log.info("Fetch Shared Offers: " + sharedResponse.getMessage());

            final FetchEmployerRequest employerRequest = new FetchEmployerRequest();
            final FetchEmployerResponse employerResponse = exchange.fetchEmployers(token, employerRequest);
            log.info("Fetch Employers: " + employerResponse.getMessage());
            final FetchPermissionResponse permissionResponse = client.fetchPermissions(token);
            log.info("PermissionResponse Error = " + permissionResponse.getError());
            log.info("PermissionResponse Message = " + permissionResponse.getMessage());
        }

        // Always attempt to deprecate the Session, otherwise we may end up
        // being unable to access
        final FallibleResponse deprecationResponse = client.deprecateSession(token);
        log.info("DeprecateSession Error = " + deprecationResponse.getError());
        log.info("DeprecateSession Error = " + deprecationResponse.getMessage());
    }
}
