/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.ws.client;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.EmployerRequest;
import net.iaeste.iws.api.requests.exchange.OfferRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.ws.client.clients.AccessWSClient;
import net.iaeste.iws.ws.client.clients.AdministrationWSClient;
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

    private static final Logger LOG = LoggerFactory.getLogger(WSClient.class);

    // =========================================================================
    // Constants, Settings and Constructor
    // =========================================================================

    private static final Object lock = new Object();
    private final String host;

    private Access access = null;
    private Administration administration = null;
    private Exchange exchange = null;

    /**
     * <p>Default Constructor, takes the Host Address of the server where IWS is
     * currently running. The Host address can either be a resolvable DNS name
     * for the server, or it can be the IPv4 Number for the Server. However, a
     * second parameter is also required, the Port number where the IWS Service
     * is made available.</p>
     *   Production Hostname; https://iws.iaeste.net:9443
     *
     * @param host Hostname (resolvable DNS record or IPv4) for the IWS instance
     */
    private WSClient(final String host) {
        this.host = host;
    }

    /**
     * Simple Main method to allow invocation from Command Line. To demonstrate
     * how the IWS WebService can be invoked.
     *
     * @param args Command Line Parameters
     */
    public static void main(final String[] args) {
        final WSClient client = new WSClient("http://localhost:9080");

        // Before we can do anything, we first need to log in
        final AuthenticationResponse authResponse = client.login("pankau@daad.de", "faked");

        // If the login request was successful, then we can make further things
        if (authResponse.isOk()) {
            final AuthenticationToken token = authResponse.getToken();

            // We're running the next requests within a try block, since we need
            // to ensure that the Session is closed in the end.
            try {
                runRequests(client, token);
            } catch (RuntimeException t) {
                LOG.error(t.getMessage(), t);
            } finally {
                // Always remember to log out, otherwise the Account will be
                // blocked for a longer time period
                LOG.info("DeprecateSession: {}.", client.deprecateSession(token).getMessage());
            }
        }
    }

    private static void runRequests(final WSClient client, final AuthenticationToken token) {
        // Access related requests
        final FetchPermissionResponse permissionResponse = client.fetchPermissions(token);
        LOG.info("PermissionResponse: {}.", permissionResponse.getMessage());

        final EmergencyListResponse emergency = client.fetchEmergencyList(token);
        LOG.info("Received the Emergency List with {} records.", emergency.getEmergencyContacts().size());

        final OfferCSVDownloadResponse downloadResponse = client.downloadOffers(token, FetchType.DOMESTIC);
        LOG.info("Offer CSV Download: {}.", downloadResponse.getMessage());

        // Exchange related requests
        LOG.info("Offer Statistics: {}.", client.fetchOfferStatistics(token).getMessage());
        LOG.info("Fetch Employers: {}.", client.fetchEmployers(token).getMessage());
        final FetchOffersResponse domesticOfferResponse = client.fetchOffers(token, FetchType.DOMESTIC);
        final FetchOffersResponse sharedOfferResponse = client.fetchOffers(token, FetchType.SHARED);
        LOG.info("Fetch Domestic Offers: {} with {} Offers.", domesticOfferResponse.getMessage(), domesticOfferResponse.getOffers().size());
        LOG.info("Fetch Shared Offers: {} with {} Offers.", sharedOfferResponse.getMessage(), sharedOfferResponse.getOffers().size());
        if (domesticOfferResponse.isOk() && (domesticOfferResponse.getOffers() != null)) {
            for (final Offer offer : domesticOfferResponse.getOffers()) {
                LOG.info("Processing Employer '{}': {}.", offer.getEmployer().getName(), client.processEmployer(token, offer.getEmployer()).getMessage());
                LOG.info("Processing Offer with Reference Number '{}': {}.", offer.getRefNo(), client.processOffer(token, offer).getMessage());
            }
        }
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
     * @return Response Object from the IWS
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
     * @return Response Object from the IWS
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
     * @return Response Object from the IWS
     */
    public FetchPermissionResponse fetchPermissions(final AuthenticationToken token) {
        return getAccess().fetchPermissions(token);
    }

    /**
     * <p>Fetches the Emergency List, which is a list that includes all
     * Administrative Staff (Owner or Moderator) as well as the Board.</p>
     *
     * @param token User Authentication (Session) Token
     * @return Response Object from the IWS
     */
    public EmergencyListResponse fetchEmergencyList(final AuthenticationToken token) {
        return getAdministration().fetchEmergencyList(token);
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
     * @return Response Object from the IWS
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
     * @return Response Object from the IWS
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
        final EmployerRequest request = new EmployerRequest();
        request.setEmployer(employer);

        return getExchange().processEmployer(token, request);
    }

    /**
     * <p>Sample IWS Download Offers request. The request requires two
     * parameters, the current Session Token and the type of Offers to be
     * fetched. The Exchange Year is omitted, as it is the current.</p>
     *
     * <p>The method will build and send the Request Object, and return the
     * Response Object from the IWS.</p>
     *
     * @param token User Authentication (Session) Token
     * @param type  Type of Offers to be fetch (domestic or shared)
     * @return Response Object from the IWS
     */
    public OfferCSVDownloadResponse downloadOffers(final AuthenticationToken token, final FetchType type) {
        final FetchOffersRequest request = new FetchOffersRequest();
        request.setFetchType(type);

        return getExchange().downloadOffers(token, request);
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
     * @return Response Object from the IWS
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
        final OfferRequest request = new OfferRequest();
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
                    access = new AccessWSClient(host + "/iws-ws/accessWS?wsdl");
                } catch (MalformedURLException e) {
                    throw new WebServiceException(e);
                }
            }

            return access;
        }
    }

    private Administration getAdministration() {
        synchronized (lock) {
            if (administration == null) {
                try {
                    administration = new AdministrationWSClient(host + "/iws-ws/administrationWS?wsdl");
                } catch (MalformedURLException e) {
                    throw new WebServiceException(e);
                }
            }

            return administration;
        }
    }

    private Exchange getExchange() {
        synchronized (lock) {
            if (exchange == null) {
                try {
                    exchange = new ExchangeWSClient(host + "/iws-ws/exchangeWS?wsdl");
                } catch (MalformedURLException e) {
                    throw new WebServiceException(e);
                }
            }

            return exchange;
        }
    }
}
