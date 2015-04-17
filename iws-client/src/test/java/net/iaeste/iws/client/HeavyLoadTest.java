/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.HeavyLoadTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.common.exceptions.AuthenticationException;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Trac report #648, shows that there's some serious performance problems within
 * the system. These need adressing. This test is going to perform a heavy run
 * against the, performing some of the most intensive requests in parallel,
 * using a rather large Threadpool. The purpose of the test is not to be run as
 * a regular test, but a way to use VisualVM to help detecting problems.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Ignore("The test is very heavy, and is designed to run against a Migated PostgreSQL database.")
public final class HeavyLoadTest {

    private static final Logger log = LoggerFactory.getLogger(HeavyLoadTest.class);
    private static final Access access = new AccessClient();
    private static final Object lock = new Object();
    private static long userTotalNanos = 0;
    private static long statTotalNanos = 0;
    private static long domesticOfferTotalNanos = 0;
    private static long sharedOfferTotalNanos = 0;

    /**
     * Just ran a couple of Queries against the Migrated PostgreSQL database for
     * Germany:<br />
     * <pre>
     *   select username, firstname, lastname, salt, password, status
     *   from users
     *   where id in (select user_id from user_to_group where role_id <= 3 and group_id = 32) and status = 'ACTIVE';
     *
     *   update users
     *      set password = '3131ac34e8830ac4efe3603c7b060f211e3b7dd518ea8bdadc72a3f07553e68a',
     *      salt = '1daf2a2f-1067-46d6-a4f0-78c0d5dcbe15'
     *    where id in (select user_id from user_to_group where role_id <= 3 and group_id = 32) and status = 'ACTIVE';
     * </pre>
     * The first found the German Staff members, these are the users who have
     * access to German Offers. Second updates the Password to 'germany'. The
     * Salt and Password has been taken from the initial test data.
     */
    private static final String[] accounts = {
            "fahrenbruch@daad.de",
            "felix.egger@mytum.de",
            "gerhard.gevelmann@googlemail.com",
            "iaeste.ka@gmail.com",
            "ji@iaeste-karlsruhe.de",
            "larissa.hammerstein@mytum.de",
            "mertens@daad.de",
            "mts.froehlich@web.de",
            "mueller-graetschel@daad.de",
            "nettekoven@daad.de",
            "pankau@daad.de",
            "zamzow@daad.de" };
    private static final String password = "germany";

    @Test
    public void testHeavyLoad() throws InterruptedException {
        final List<AuthenticationToken> tokens = prepareTokenPool();
        final int threads = tokens.size();
        final int loops = 10;
        log.info("Starting the test with {} threads and {} loops in each thread.", threads, loops);

        final ExecutorService executor = Executors.newFixedThreadPool(threads);
        final List<Callable<Object>> jobs = new ArrayList<>(threads);
        for (int i = 1; i <= threads; i++) {
            final Administration administration = new AdministrationClient();
            final Exchange exchange = new ExchangeClient();
            final Runner runner = new Runner(administration, exchange, tokens.get(i - 1), i, loops);
            jobs.add(Executors.callable(runner));
        }
        final List<Future<Object>> result = executor.invokeAll(jobs);
        assertThat(result.size(), is(threads));

        log.info("Finishing the test.");
        log.info("Fetching MemberGroup with all users {} times, took {} ms", tokens.size() * loops, getUserNanos() / 1000000);
        log.info("Fetching OfferStatistics {} times, took {} ms", tokens.size() * loops, getStatNanos() / 1000000);
        log.info("Fetching All Offers {} times, took {} ms", tokens.size() * loops, getDomesticOfferNanos() / 1000000);
        log.info("Fetching Shared Offers {} times, took {} ms", tokens.size() * loops, getSharedOfferNanos() / 1000000);

        // Now we can clean up the tokens
        cleanupTokenPool(tokens);
    }

    // =========================================================================
    // Internal Helper Methods
    // =========================================================================

    private static List<AuthenticationToken> prepareTokenPool() {
        final List<AuthenticationToken> tokens = new ArrayList<>(accounts.length);

        for (final String account : accounts) {
            final AuthenticationRequest request = new AuthenticationRequest(account, password);
            final AuthenticationResponse response = access.generateSession(request);

            if (!response.isOk()) {
                throw new AuthenticationException(response.getMessage());
            }

            tokens.add(response.getToken());
        }

        return tokens;
    }

    private static void cleanupTokenPool(final List<AuthenticationToken> pool) {
        for (final AuthenticationToken token : pool) {
            access.deprecateSession(token).isOk();
        }
    }

    private static void recordUserNanos(final long userNanos) {
        synchronized (lock) {
            userTotalNanos += userNanos;
        }
    }

    private static long getUserNanos() {
        synchronized (lock) {
            return userTotalNanos;
        }
    }

    private static void recordStatNanos(final long statNanos) {
        synchronized (lock) {
            statTotalNanos += statNanos;
        }
    }

    private static long getStatNanos() {
        synchronized (lock) {
            return statTotalNanos;
        }
    }

    private static void recordDomesticOfferNanos(final long domesticOfferNanos) {
        synchronized (lock) {
            domesticOfferTotalNanos += domesticOfferNanos;
        }
    }

    private static long getDomesticOfferNanos() {
        synchronized (lock) {
            return domesticOfferTotalNanos;
        }
    }

    private static void recordSharedOfferNanos(final long sharedOfferNanos) {
        synchronized (lock) {
            sharedOfferTotalNanos += sharedOfferNanos;
        }
    }

    private static long getSharedOfferNanos() {
        synchronized (lock) {
            return sharedOfferTotalNanos;
        }
    }

    // =========================================================================
    // Internal Help Classes
    // =========================================================================

    /**
     * Runner is a simple Class, which implements the Runnable interface, to
     * facilitate a mechanism whereby we can invoke numerous threads that run
     * parallel to properly test that PoSi works well in a multi-threaded
     * environment.
     */
    private static final class Runner implements Runnable {

        private final Administration administration;
        private final Exchange exchange;
        private final AuthenticationToken token;
        private final int thread;
        private final int loops;

        private Runner(final Administration administration, final Exchange exchange, final AuthenticationToken token, final int thread, final int loops) {
            this.administration = administration;
            this.exchange = exchange;
            this.token = token;
            this.thread = thread;
            this.loops = loops;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            log.info("Thread {} starting.", thread);
            long userNanos = 0;
            long statNanos = 0;
            long domesticOfferNanos = 0;
            long sharedOfferNanos = 0;

            for (int i = 0; i <= loops; i++) {
                final FetchGroupRequest request = new FetchGroupRequest(GroupType.MEMBER);
                request.setUsersToFetch(FetchGroupRequest.UserFetchType.ACTIVE);

                final long tmp1 = System.nanoTime();
                assertThat(administration.fetchGroup(token, request).isOk(), is(true));
                final long tmp2 = System.nanoTime();
                assertThat(exchange.fetchOfferStatistics(token, new OfferStatisticsRequest()).isOk(), is(true));
                final long tmp3 = System.nanoTime();
                assertThat(exchange.fetchOffers(token, new FetchOffersRequest(FetchType.DOMESTIC)).isOk(), is(true));
                final long tmp4 = System.nanoTime();
                assertThat(exchange.fetchOffers(token, new FetchOffersRequest(FetchType.SHARED)).isOk(), is(true));
                final long tmp5 = System.nanoTime();
                userNanos += tmp2 - tmp1;
                statNanos += tmp3 - tmp2;
                domesticOfferNanos += tmp4 - tmp3;
                sharedOfferNanos += tmp5 - tmp4;
            }

            // Massive logging, to get all the results out
            recordUserNanos(userNanos);
            recordStatNanos(statNanos);
            recordDomesticOfferNanos(domesticOfferNanos);
            recordSharedOfferNanos(sharedOfferNanos);
            log.debug("Thread {} :: Fetching MemberGroup with all users {} times, took {} ns", thread, loops, userNanos);
            log.debug("Thread {} :: Fetching OfferStatistics {} times, took {} ns", thread, loops, statNanos);
            log.debug("Thread {} :: Fetching All Offers {} times, took {} ns", thread, loops, domesticOfferNanos);
            log.debug("Thread {} :: Fetching Shared Offers {} times, took {} ns", thread, loops, sharedOfferNanos);
            log.debug("Thread {} finishing.", thread);
        }
    }
}
