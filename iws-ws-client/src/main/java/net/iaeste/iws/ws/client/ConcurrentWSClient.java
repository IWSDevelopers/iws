/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws-client) - net.iaeste.iws.ws.client.ConcurrentWSClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.util.AbstractVerification;
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

    // IWS WebService settings:
    private static final String iwsHost = "localhost";
    // The port is 8080 under both Glassfish and WildFly
    private static final String iwsPort = "8080";
    // Number of concurrent Threads to use, higher is meaner!
    private static final int threads = 5;
    // First registered Exchange Year in the Database
    private static final int startYear = 2005;
    // Current Exchange Year
    private static final int endYear = AbstractVerification.calculateExchangeYear();
    // List of all the current National Secretaries
    private static final String[] users = {
            "edlira.osmani@gmail.com", "iaeste@mincyt.gob.ar", "intour@arminco.com", "lachlanharpley@gmail.com",
            "lukas.schwendinger@iaeste.at", "vugar@yesevent.org", "mahossain2001us@yahoo.com", "sumon@catechedu.com",
            "dva@minedu.unibel.by", "annelies.vermeir@ugent.be", "patriciapaganigasser@gmail.com", "vedro88etf@gmail.com",
            "mmereki@mopipi.ub.bw", "pprado@abipe.org.br", "nationalsecretary@iaestecanada.org", "lauramendezprencke@gmail.com",
            "pnavarrete@corparaucania.cl", "beatrice.chu@polyu.edu.hk", "rtoac@umac.mo", "richard.wu@iaeste-china.org",
            "bernardbaeyens2@gmail.com", "valentina.jovic1@gmail.com", "iaeste@cut.ac.cy", "vaclav.pavlik@iaeste.cz",
            "bl@iaeste.dk", "zong.pust@gmail.com", "iecuador@yahoo.com", "iaeste.egypt@mailcity.com",
            "maret.hein@ttu.ee", "agnes.arcarons@gmail.com", "audrey.stewart@ensam.eu", "moseslewally@yahoo.com",
            "guramsologashvili@yahoo.co.uk", "mueller-graetschel@daad.de", "iaestegh@yahoo.com", "iaeste@central.ntua.gr",
            "szirmai.pal@iaeste.hu", "kok11@hi.is", "nationalsecretary@iaeste.in", "salim_johan@yahoo.com",
            "shekarch@ut.ac.ir", "iaeste@leargas.ie", "iaeste@tx.technion.ac.il", "cbennett@joystyouthexchangeintl.org",
            "office2@iaeste.or.jp", "r.imam@ju.edu.jo", "iaeste_kz@hotmail.com", "charles.kagiri@dkut.ac.ke",
            "iaeste-kenya@jkuat.ac.ke", "professoryou@gmail.com", "patricija.kara@gmail.com", "memouk@aub.edu.lb",
            "bonsu112@gmail.com", "cuspecialassistant@yahoo.com", "vaida.spudyte@gmail.com", "stamenkov.filip@gmail.com",
            "hafizal@usm.my", "maria.stella.portelli@iaeste.org.mt", "consuelo@amipp.org", "batzorig@must.edu.mn",
            "dhakal.khagendra@gmail.com", "iaestenederland@gmail.com", "ingrid.s.mcewan@iaeste.org.au", "rmaguta@yahoo.com",
            "jonilo4love1@yahoo.com", "karolinelekve@gmail.com", "adita@squ.edu.om", "iaeste_pakistan@yahoo.co.uk",
            "victor.sanchez@utp.ac.pa", "maribel.alayza@udep.pe", "iaeste@poec.net", "anna.jerzak@iaeste.pl",
            "silvia.santos@tecnico.ulisboa.pt", "ghina@qu.edu.qa", "iaeste_romania@yahoo.co.uk", "incoming@iaesterussia.ru",
            "zqudah@taibahu.edu.sa", "iaeste@tmf.bg.ac.rs", "smawundu@yahoo.com", "jamukpos@yahoo.com",
            "hirjak.miroslav@gmail.com", "katarina.korencan@iaeste.si", "iaestesp@upvnet.upv.es", "nihal@mrt.ac.lk",
            "anders.freden@chalmers.se", "sabine.lenz@office.iaeste.ch", "iaeste.syr@hotmail.com", "pulatov@bk.ru",
            "fbadundwa2004@yahoo.com", "tck@kmutnb.ac.th", "iaeste.tunisia@gmail.com", "iaeste.turkiye@gmail.com",
            "stri@aer.ntu-kpi.kiev.ua", "iaeste@sharjah.ac.ae", "wendy.waring@britishcouncil.org", "dewert@culturalvistas.org",
            "talatmt@rambler.ru", "iaestevietnam@gmail.com", "awad@mail.najah.edu", "iaestecoach@gmail.com",
            "sawekema@yahoo.com",
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
        log.info("Starting {} threads concurrently to process the Offers for {} users.", threads, users.length);
        final long startTime = System.nanoTime();

        final ExecutorService executor = Executors.newFixedThreadPool(threads);
        final List<Callable<Object>> jobs = new ArrayList<>(users.length);
        for (final String username : users) {
            final ConcurrentWSClient client = new ConcurrentWSClient(iwsHost, iwsPort, username, "faked");
            jobs.add(Executors.callable(client));
        }
        final List<Future<Object>> result = executor.invokeAll(jobs);
        executor.shutdown();

        final DecimalFormat format = new DecimalFormat("###,###.##");
        final String duration = format.format((double) (System.nanoTime() - startTime) / 1000000000);
        log.info("Completed concurrent processing of Offers with {} threads in {} s.", result.size(), duration);
    }

    // =========================================================================
    // Constants, Settings and Constructor
    // =========================================================================

    private static final Logger log = LoggerFactory.getLogger(WSClient.class);
    private static final Object lock = new Object();
    private final String host;
    private final String port;
    private final String username;
    private final String password;

    private Access access = null;
    private Exchange exchange = null;

    /**
     * <p>Concurrent Constructor, takes the Host & Port for the IWS WebService,
     * and additionally the User Credentials, and the Start/End years for the
     * Offers to process.</p>
     *
     * @param host     Hostname (resolvable DNS record or IPv4) for the IWS instance
     * @param port     Portnumber for the IWS instance
     * @param username Username for the User to access
     * @param password Password for the User
     */
    public ConcurrentWSClient(final String host, final String port, final String username, final String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * Runner Method, which will iterate over the Offers for the current User.
     */
    @Override
    public void run() {
        log.info("Starting Offer Processing thread for {}.", username);

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
                    log.debug("Start iterating over Exchange Year {} for Group '{}'.", i, member.getCommitteeName());
                    final FetchOffersResponse domestic = fetchOffers(token, FetchType.DOMESTIC, i);
                    final FetchOffersResponse shared = fetchOffers(token, FetchType.SHARED, i);
                    log.info("Found {} Domestic Offers & {} Shared Offers for {} in {}", domestic.getOffers().size(), shared.getOffers().size(), member.getCommitteeName(), i);

                    for (final Offer offer : domestic.getOffers()) {
                        final OfferResponse response = processOffer(token, offer);
                        if (!response.isOk()) {
                            log.warn("Processing Offer with Reference Number '" + offer.getRefNo() + "' failed: " + processOffer(token, offer).getMessage());
                        }
                    }
                }
            } catch (Throwable t) {
                log.error(t.getMessage(), t);
            } finally {
                // Always remember to log out, otherwise the Account will be
                // blocked for a longer time period
                log.debug("Deprecated Session for user '" + username + "': " + deprecateSession(token).getMessage());
            }
        }

        log.info("Completed Offer Processing thread for {}.", username);
    }

    // =========================================================================
    // Prepare IWS WebServices
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
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchType type, final int year) {
        final FetchOffersRequest offerRequest = new FetchOffersRequest();
        offerRequest.setFetchType(type);
        offerRequest.setExchangeYear(year);

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

    private Group findGroupByType(final FetchPermissionResponse response, final GroupType type) {
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
