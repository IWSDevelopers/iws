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
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Authorization;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVDownloadRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.ws.client.clients.AccessWSClient;
import net.iaeste.iws.ws.client.clients.ExchangeWSClient;
import net.iaeste.iws.ws.client.exceptions.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Concurrent WS Client, which will will run with requests from all Committees
 * spread over n threads.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class ConcurrentWSClient implements Runnable {

    /**
     * <p>The number of Threads to run with in parallel. The Job Queue will
     * contain all the National Secretaries (see below), but only n of these
     * will be processed concurrently. If the number is set to low, then the
     * test may take very long, and if set too high - it may not properly
     * spread the load.</p>
     * <p>Default value is 5, which is a fairly low number yet - high enough
     * to keep the IWS busy.</p>
     */
    private static final int threads = 3;

    /**
     * <p>The IWS Host, is the server where IWS is currently running, including
     * Protocol information, resolvable DNS name and port.</p>
     * <ul>
     *   <li><b>http://localhost:8080</b> <i>if IWS is running locally under Glassfish.</i></li>
     *   <li><b>http://localhost:9080</b> <i>if IWS is running locally under WildFly.</i></li>
     *   <li><b>https://iws.iaeste.net:9443</b> <i>for the production IWS instance.</i></li>
     * </ul>
     */
    private static final String iwsHost = "http://localhost:9080";

    /**
     * <p>The test will iterate over a number of Exchange Years. The Exchange
     * Year is ending/starting around September 1st. The first year with Offers
     * registered is 2005, but to reduce the test, a later year can also be
     * chosen.</p>
     */
    private static final int startYear = 2015;

    /**
     * <p>The last Exchange Year to use, by default it is set to the
     * current, using the API Exchange Year Calculator.</p>
     */
    private static final int endYear = Verifications.calculateExchangeYear();

    /**
     * <p>This is a list of All National Secretaries order by the number of
     * Offers they have, with the Country with most being the first. Although
     * Threads are not ordered, the Jobs themselves are, so by sorting by the
     * Country with most Offers in the beginning, we can prevent that the
     * longest running jobs is started so late that other Threads have
     * completed.</p>
     * <p>The list was compiled on May 1st, 2015.</p>
     */
    private static final String[] users = {
            "mueller-graetschel@daad.de", "nationalsecretary@iaeste.in", "anna.jerzak@iaeste.pl", "pprado@abipe.org.br", "iaeste.turkiye@gmail.com",
            "sabine.lenz@office.iaeste.ch", "iaestesp@upvnet.upv.es", "lukas.schwendinger@iaeste.at", "iaeste@tmf.bg.ac.rs", "richard.wu@iaeste-china.org",
            "iaeste.tunisia@gmail.com", "wendy.waring@britishcouncil.org", "vaclav.pavlik@iaeste.cz", "annelies.vermeir@ugent.be", "valentina.jovic1@gmail.com",
            "bernardbaeyens2@gmail.com", "szirmai.pal@iaeste.hu", "shekarch@ut.ac.ir", "stamenkov.filip@gmail.com", "iaestegh@yahoo.com",
            "vedro88etf@gmail.com", "office2@iaeste.or.jp", "tck@kmutnb.ac.th", "katarina.korencan@iaeste.si", "iaestevietnam@gmail.com",
            "hirjak.miroslav@gmail.com", "incoming@iaesterussia.ru", "silvia.santos@tecnico.ulisboa.pt", "karolinelekve@gmail.com", "professoryou@gmail.com",
            "agnes.arcarons@gmail.com", "adita@squ.edu.om", "dewert@culturalvistas.org", "stri@aer.ntu-kpi.kiev.ua", "r.imam@ju.edu.jo",
            "iaeste_kz@hotmail.com", "iecuador@yahoo.com", "iaeste@mincyt.gob.ar", "iaeste_romania@yahoo.co.uk", "bl@iaeste.dk",
            "beatrice.chu@polyu.edu.hk", "pulatov@bk.ru", "iaeste@cut.ac.cy", "lachlanharpley@gmail.com", "iaeste@central.ntua.gr",
            "dva@minedu.unibel.by", "memouk@aub.edu.lb", "awad@mail.najah.edu", "iaeste@tx.technion.ac.il", "anders.freden@chalmers.se",
            "batzorig@must.edu.mn", "iaeste@leargas.ie", "maria.stella.portelli@iaeste.org.mt", "iaeste@sharjah.ac.ae", "maribel.alayza@udep.pe",
            "nationalsecretary@iaestecanada.org", "victor.sanchez@utp.ac.pa", "iaeste-kenya@jkuat.ac.ke", "sumon@catechedu.com", "jonilo4love1@yahoo.com",
            "rtoac@umac.mo", "vaida.spudyte@gmail.com", "ghina@qu.edu.qa", "iaestenederland@gmail.com", "jamukpos@yahoo.com",
            "iaeste.egypt@mailcity.com", "consuelo@amipp.org", "nihal@mrt.ac.lk", "iaeste@poec.net", "fbadundwa2004@yahoo.com",
            "maret.hein@ttu.ee", "moseslewally@yahoo.com", "rmaguta@yahoo.com", "dhakal.khagendra@gmail.com", "patriciapaganigasser@gmail.com",
            "iaeste_pakistan@yahoo.co.uk", "talatmt@rambler.ru", "edlira.osmani@gmail.com", "sawekema@yahoo.com", "cbennett@joystyouthexchangeintl.org",
            "vugar@yesevent.org", "patricija.kara@gmail.com", "iaeste.syr@hotmail.com", "mahossain2001us@yahoo.com", "mmereki@mopipi.ub.bw",
            "cuspecialassistant@yahoo.com", "charles.kagiri@dkut.ac.ke", "lauramendezprencke@gmail.com", "kok11@hi.is", "ingrid.s.mcewan@iaeste.org.au",
            "hafizal@usm.my", "pnavarrete@corparaucania.cl", "guramsologashvili@yahoo.co.uk"
    };

    /**
     * <p>Concurrent main method, which will iterate over the list of users
     * provided, and for each user, retrieve offers from all registered Exchange
     * Years (2005 - 2015), and simply update all of them.</p>
     *
     * <p>The goal of this, is to stress test the IWS running under either
     * WildFly or Glassfish.</p>
     *
     * @param args Command Line Parameters
     */
    public static void main(final String[] args) throws InterruptedException {
        LOG.info("Starting {} threads concurrently to process the Offers for {} users from {} until {}.", threads, users.length, startYear, endYear);
        final long startTime = System.nanoTime();

        final ExecutorService executor = Executors.newFixedThreadPool(threads);
        final List<Callable<Object>> jobs = new ArrayList<>(users.length);
        for (final String username : users) {
            final ConcurrentWSClient client = new ConcurrentWSClient(iwsHost, username, "faked");
            jobs.add(Executors.callable(client));
        }
        final List<Future<Object>> result = executor.invokeAll(jobs);
        executor.shutdown();

        final DecimalFormat format = new DecimalFormat("###,###.##");
        final String duration = format.format((double) (System.nanoTime() - startTime) / 60000000000L);
        LOG.info("Completed concurrent processing of Offers with {} threads in {} minutes.", result.size(), duration);
    }

    // =========================================================================
    // Constants, Settings and Constructor
    // =========================================================================

    private static final Logger LOG = LoggerFactory.getLogger(ConcurrentWSClient.class);
    private final Object lock = new Object();
    private final String host;
    private final String username;
    private final String password;

    private Access access = null;
    private Exchange exchange = null;

    /**
     * <p>Concurrent Constructor, takes the Host & Port for the IWS WebService,
     * and additionally the User Credentials, and the Start/End years for the
     * Offers to process.</p>
     *   Production Hostname; https://iws.iaeste.net:9443
     *
     * @param host     Hostname (resolvable DNS record or IPv4) for the IWS instance
     * @param username Username for the User to access
     * @param password Password for the User
     */
    public ConcurrentWSClient(final String host, final String username, final String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    /**
     * Runner Method, which will iterate over the Offers for the current User.
     */
    @Override
    public void run() {
        LOG.info("Starting Offer Processing thread for {}.", username);

        // Before we can do anything, we first need to log in
        final AuthenticationResponse authResponse = login(username, password);

        // If the login request was successful, then we can make further things
        if (authResponse.isOk()) {
            final AuthenticationToken token = authResponse.getToken();

            try {
                final FetchPermissionResponse permissionResponse = fetchPermissions(token);
                final Group member = findGroupByType(permissionResponse, GroupType.MEMBER);

                // Iterate over the Exchange Years
                for (int i = startYear; i <= endYear; i++) {
                    LOG.debug("Start iterating over Exchange Year {} for Group '{}'.", i, member.getCommitteeName());
                    final FetchOffersResponse domestic = fetchOffers(token, FetchType.DOMESTIC, i);
                    final FetchOffersResponse shared = fetchOffers(token, FetchType.SHARED, i);
                    LOG.info("Found {} Domestic Offers & {} Shared Offers for {} in {}", domestic.getOffers().size(), shared.getOffers().size(), member.getCommitteeName(), i);

                    for (final Offer offer : domestic.getOffers()) {
                        final OfferResponse response = processOffer(token, offer);
                        if (!response.isOk()) {
                            LOG.warn("Processing Offer with Reference Number '{}' failed: {}", offer.getRefNo(), processOffer(token, offer).getMessage());
                        }
                    }
                }
            } catch (RuntimeException t) {
                LOG.error(t.getMessage(), t);
            } finally {
                // Always remember to log out, otherwise the Account will be
                // blocked for a longer time period
                LOG.debug("Deprecated Session for user '{}': {}", username, deprecateSession(token).getMessage());
            }
        }

        LOG.info("Completed Offer Processing thread for {}.", username);
    }

    // =========================================================================
    // Prepare IWS WebServices
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

    // =========================================================================
    // Class Methods, used to perform various actions
    // =========================================================================

    /**
     * <p>Sample IWS Login request. The request requires two parameters,
     * username (the e-mail address whereby the User is registered), and the
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
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchType type, final int year) {
        final FetchOffersRequest offerRequest = new FetchOffersRequest();
        offerRequest.setFetchType(type);
        offerRequest.setExchangeYear(year);

        return getExchange().fetchOffers(token, offerRequest);
    }

    /**
     * <p>Sample IWS Download Offers request. The request requires two parameters,
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
    public OfferCSVDownloadResponse downloadOffers(final AuthenticationToken token, final FetchType type, final int year) {
        final OfferCSVDownloadRequest request = new OfferCSVDownloadRequest();
        request.setFetchType(type);
        request.setExchangeYear(year);

        return getExchange().downloadOffers(token, request);
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

    private static Group findGroupByType(final FetchPermissionResponse response, final GroupType type) {
        Group group = null;

        for (final Authorization authorization : response.getAuthorizations()) {
            final Group current = authorization.getUserGroup().getGroup();
            if (current.getGroupType() == type) {
                group = current;
                break;
            }
        }

        return group;
    }
}
