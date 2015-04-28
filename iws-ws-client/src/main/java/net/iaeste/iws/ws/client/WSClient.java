package net.iaeste.iws.ws.client;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.ws.client.clients.AccessWSClient;
import net.iaeste.iws.ws.client.clients.ExchangeWSClient;
import net.iaeste.iws.ws.client.exceptions.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

/**
 * Sample Client to communicate with the IWS WebServices. This Client will
 * demonstrate how to communicate with a WebService, and uses the Mappers to
 * ensure that the Requests will be using the IWS API directly.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class WSClient {

    private static final String switzerland = "incoming@office.iaeste.ch";
    private static final String germany = "pankau@daad.de";

    /**
     * Simple Main method to allow invocation from Command Line. To demonstrate
     * how the IWS WebService can be invoked.
     *
     * @param args Command Line Parameters
     */
    public static void main(final String[] args) {
        final WSClient client = new WSClient("localhost", "8080");

        // Before we can do anything, we first need to log in
        final AuthenticationResponse authResponse = client.login(germany, "faked");

        // If the login request was successful, then we can make further things
        if (authResponse.isOk()) {
            final AuthenticationToken token = authResponse.getToken();

            // We're running the next requests within a try block, since we need
            // to ensure that the Session is closed in the end.
            try {
                // Access related requests
                log.info("PermissionResponse: " + client.fetchPermissions(token).getMessage());

                // Exchange related requests
                log.info("Offer Statistics: " + client.fetchOfferStatistics(token).getMessage());
                log.info("Fetch Employers: " + client.fetchEmployers(token).getMessage());
                final FetchOffersResponse domesticOfferResponse = client.fetchOffers(token, FetchType.DOMESTIC);
                final FetchOffersResponse sharedOfferResponse = client.fetchOffers(token, FetchType.SHARED);
                log.info("Fetch Domestic Offers: " + domesticOfferResponse.getMessage() + " with " + domesticOfferResponse.getOffers().size() + " Offers.");
                log.info("Fetch Shared Offers: " + sharedOfferResponse.getMessage() + " with " + sharedOfferResponse.getOffers().size() + " Offers.");
                if (domesticOfferResponse.isOk() && domesticOfferResponse.getOffers() != null) {
                    for (final Offer offer : domesticOfferResponse.getOffers()) {
                        log.info("Processing Employer '" + offer.getEmployer().getName() + "': " + client.processEmployer(token, offer.getEmployer()).getMessage());
                        log.info("Processing Offer with Reference Number '" + offer.getRefNo() + "': " + client.processOffer(token, offer).getMessage());
                    }
                }
            } catch (Throwable t) {
                log.error(t.getMessage(), t);
            } finally {
                // Always remember to log out, otherwise the Account will be
                // blocked for a longer time period
                log.info("DeprecateSession: " + client.deprecateSession(token).getMessage());
            }
        }
    }

    // =========================================================================
    // Constants, Settings and Constructor
    // =========================================================================

    private static final Logger log = LoggerFactory.getLogger(WSClient.class);
    private static final Object lock = new Object();
    private final String host;
    private final String port;

    private Access access = null;
    private Exchange exchange = null;

    /**
     * <p>Default Constructor, takes the Host Address of the server where IWS is
     * currently running. The Host address can either be a resolvable DNS name
     * for the server, or it can be the IPv4 Number for the Server. However, a
     * second parameter is also required, the Port number where the IWS Service
     * is made available.</p>
     *
     * @param host Hostname (resolvable DNS record or IPv4) for the IWS instance
     * @param port Portnumber for the IWS instance
     */
    public WSClient(final String host, final String port) {
        this.host = host;
        this.port = port;
    }

    // =========================================================================
    // Class Methods, used to perform various actions
    // =========================================================================

    /**
     * <p>Sample IWS Login request. The request requires two parametes, username
     * (the e-mail address whereby the User is registered), and the
     * password.</p>
     *
     * <p>The method will build and send the Request Object, and return the
     * Response Object from the IWS.</p>
     *
     * @param username E-mail address whereby the user is registered in the IWS
     * @param password The user's password for the IWS
     * @return Respons Object from the IWS
     */
    public AuthenticationResponse login(final String username, final String password) {
        final AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword(password);

        return getAccess().generateSession(request);
    }

    /**
     * <p>Sample IWS Deprecate Session request. The request requires just the
     * current Session Token to be deprecated.</p>
     *
     * <p>The method will build and send the Request Object, and return the
     * Response Object from the IWS.</p>
     *
     * @param token User Authentication (Session) Token
     * @return Respons Object from the IWS
     */
    public FallibleResponse deprecateSession(final AuthenticationToken token) {
        return getAccess().deprecateSession(token);
    }

    /**
     * <p>Sample IWS Fetch Permissions request. The request requires just the
     * current Session Token, and will return the Groups which the user is a
     * member of, together with the permissions that the user has in each
     * Group.</p>
     *
     * <p>The method will build and send the Request Object, and return the
     * Response Object from the IWS.</p>
     *
     * @param token User Authentication (Session) Token
     * @return Respons Object from the IWS
     */
    public FetchPermissionResponse fetchPermissions(final AuthenticationToken token) {
        return getAccess().fetchPermissions(token);
    }

    /**
     * <p>Sample IWS Fetch Offer Statistics request. This request requires only
     * the Users current Token (Session), and will retrieve a listing of Offer
     * Statistics. Although the Statistics can take the year as parameter, it is
     * omitted here, so we always retrieve the current Exchange Year.</p>
     *
     * <p>The method will build and send the Request Object, and return the
     * Response Object from the IWS.</p>
     *
     * @param token User Authentication (Session) Token
     * @return Respons Object from the IWS
     */
    public OfferStatisticsResponse fetchOfferStatistics(final AuthenticationToken token) {
        final OfferStatisticsRequest request = new OfferStatisticsRequest();

        return getExchange().fetchOfferStatistics(token, request);
    }

    /**
     * <p>Sample IWS Fetch Employer request. The request requires just the
     * current Session Token.</p>
     *
     * <p>The method will build and send the Request Object, and return the
     * Response Object from the IWS.</p>
     *
     * @param token User Authentication (Session) Token
     * @return Respons Object from the IWS
     */
    public FetchEmployerResponse fetchEmployers(final AuthenticationToken token) {
        final FetchEmployerRequest employerRequest = new FetchEmployerRequest();

        return getExchange().fetchEmployers(token, employerRequest);
    }

    /**
     * <p>Sample IWS Process Employer Request. The request requires st current
     * token, and an Employer to be processed.</p>
     *
     * <p>The method will build and send the Request Object, and return the
     * Response Object from the IWS.</p>
     *
     * @param token    User Authentication (Session) Token
     * @param employer The Employer to be processed (created or updated)
     * @return Response Object from the IWS
     */
    private EmployerResponse processEmployer(final AuthenticationToken token, final Employer employer) {
        final Employer copy = new Employer(employer);
        final ProcessEmployerRequest request = new ProcessEmployerRequest(copy);

        return getExchange().processEmployer(token, request);
    }

    /**
     * <p>Sample IWS Fetch Offers request. The request requires two parameters,
     * the current Session Token and the type of Offers to be fetched. The
     * Exchange Year is omitted, as it is the current.</p>
     *
     * <p>The method will build and send the Request Object, and return the
     * Response Object from the IWS.</p>
     *
     * @param token User Authentication (Session) Token
     * @param type  Type of Offers to be fetch (domestic or shared)
     * @return Respons Object from the IWS
     */
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchType type) {
        final FetchOffersRequest offerRequest = new FetchOffersRequest();
        offerRequest.setFetchType(type);

        return getExchange().fetchOffers(token, offerRequest);
    }

    /**
     * <p>Sample IWS Process Offer Request. The request requires st current
     * token, and an Employer to be processed.</p>
     *
     * <p>The method will build and send the Request Object, and return the
     * Response Object from the IWS.</p>
     *
     * @param token User Authentication (Session) Token
     * @param offer The Offer to be processed (created or updated)
     * @return Response Object from the IWS
     */
    private OfferResponse processOffer(final AuthenticationToken token, final Offer offer) {
        final ProcessOfferRequest request = new ProcessOfferRequest();
        request.setOffer(offer);

        return getExchange().processOffer(token, request);
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private Access getAccess() {
        synchronized (lock) {
            if (access == null) {
                try {
                    access = new AccessWSClient("http://" + host + ':' + port + "/iws-ws/accessWS?wsdl");
                } catch (MalformedURLException e) {
                    throw new WebServiceException(e);
                }
            }

            return access;
        }
    }

    private Exchange getExchange() {
        synchronized (lock) {
            if (exchange == null) {
                try {
                    exchange = new ExchangeWSClient("http://" + host + ':' + port + "/iws-ws/exchangeWS?wsdl");
                } catch (MalformedURLException e) {
                    throw new WebServiceException(e);
                }
            }

            return exchange;
        }
    }
}
