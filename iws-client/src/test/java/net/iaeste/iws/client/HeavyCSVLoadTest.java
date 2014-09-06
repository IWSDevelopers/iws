/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.HeavyCSVLoadTest
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

import net.iaeste.iws.api.constants.IWSConstants;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The purpose of this test is to provide a mechanism whereby we can run a heavy
 * test against the WebServlet.<br />
 *   The test simulates the different Exchange Bots that keep accessing the
 * server at short intervals, sending a port request to the URL:
 * <pre>
 *   http://HOSTNAME:8080/intraweb/exchange/inbox.csv
 * </pre>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Ignore("The test is very heavy, and was made to detect memory problems, see #839, #851.")
public class HeavyCSVLoadTest {

    private static final Logger log = LoggerFactory.getLogger(HeavyCSVLoadTest.class);

    /** The URL to connection to, for retreiving the Offers. */
    private static final String URL = "http://localhost:8080/intraweb/exchange/inbox.csv";

    private static final int LOOPS = 100;

    /**
     * Note, all passwords for these users needs to be updated. This is the SQL
     * Query to do so, setting the password to "botmadness":
     * <pre>
     * update users set
     *   password = 'eb9fa04291e57c050e7887f1ec2ea19cb3a34dbcb29cd671447ee17b83ce6d51',
     *   salt = 'f52156a4-0752-492a-84c1-8f27e88b2f2e'
     * where username ilike '%_offers_bot@iaeste.%'
     *    or username = 'admins@iaeste.tu-dresden.de';
     * </pre>
     */
    private static final String[] ACCOUNTS = {
            "admins@iaeste.tu-dresden.de",
            "finnland_offers_bot@iaeste.fi",
            "vietnam_offers_bot@iaeste.vn",
            "belgium_offers_bot@iaeste.be",
            "netherlands_offers_bot@iaeste.nl",
            "spain_offers_bot@iaeste.es",
            "malta_offers_bot@iaeste.mt",
            "australia_offers_bot@iaeste.au",
            "bolivia_offers_bot@iaeste.bo",
            "bih_offers_bot@iaeste.bh",
            "poland_offers_bot@iaeste.pl",
            "slovenia_offers_bot@iaeste.si"
    };

    /** This is the password for the ACCOUNTS above. */
    private static final String password = "botmadness";

    @Test
    public void runPostRequests() throws InterruptedException {
        // The test is running all requests in parallel for each account. The
        // IWS is only allowed that a single active session exists for each
        // user. However, the individual thread started will repeat the request
        // for the number of times specified by the LOOPS constant
        final ExecutorService executor = Executors.newFixedThreadPool(ACCOUNTS.length);
        final List<Callable<Object>> jobs = new ArrayList<>(ACCOUNTS.length);
        for (final String username : ACCOUNTS) {
            final Runner runner = new Runner(username);
            jobs.add(Executors.callable(runner));
        }

        // Now, invoke the threads and let them do their work. This will put the
        // Application Server under pressure, and show if we have any problems
        final List<Future<Object>> result = executor.invokeAll(jobs);
        assertThat(result.size(), is(ACCOUNTS.length));
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

        private final String username;
        private int length = 0;

        private Runner(final String username) {
            this.username = username;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            log.info("Thread <{}> starting.", username);
            try {
                for (int i = 1; i <= LOOPS; i++) {
                    // Send a request and store the result
                    final String csv = sendRequest(username);
                    if (csv.length() > 150) {
                        log.info("{}. Iteration: User <{}> received {} bytes from the server.", i, username, csv.length());
                    } else {
                        log.info("{}. Iteration: Read folloring for user <{}>: '{}'.", i, username, csv);
                    }

                    // First iteration, we'll save the length from the
                    // response, and also save the CSV response For all further
                    // requests, we expect the same answer, otherwise an
                    // Exception is thrown. Although the hashCode would be
                    // better to store - we have no guarantee that the order of
                    // the result is always the same, therefore are we only
                    // using the length as a check
                    if (i == 1) {
                        length = csv.length();
                        saveCSV(csv);
                    } else {
                        if (length != csv.length()) {
                            log.warn("{}. Iteration: User <{}> Receiving CSV had different length than the first!", i, username);
                        }
                    }
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            log.info("Thread <{}> ending.", username);
        }

        private String sendRequest(final String username) throws IOException {
            HttpURLConnection connection = prepareConnection();
            String parameters = "username=" + username + "&password=" + password;
            sendOutputPostRequest(connection, parameters);

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                log.error("Response Code : " + responseCode);
            }

            return readInputResponse(connection);
        }

        private HttpURLConnection prepareConnection() throws IOException {
            final URL url = new URL(URL);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //add reuqest header
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Send post request
            connection.setDoOutput(true);

            return connection;
        }

        private void sendOutputPostRequest(final HttpURLConnection connection, final String parameters) throws IOException {
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.writeBytes(parameters);
                outputStream.flush();
            }
        }

        private String readInputResponse(final HttpURLConnection connection) throws IOException {
            try (InputStream stream = connection.getInputStream();
                 InputStreamReader reader = new InputStreamReader(stream, IWSConstants.DEFAULT_ENCODING);
                 BufferedReader buffer = new BufferedReader(reader)) {
                final StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = buffer.readLine()) != null) {
                    response.append(inputLine);
                }

                return response.toString();
            }
        }

        private void saveCSV(final String csv) throws IOException {
            final String filename = System.getProperty("java.io.tmpdir") + '/' + username + ".csv";
            final Path path = Paths.get(filename);
            Files.write(path, csv.getBytes(IWSConstants.DEFAULT_ENCODING));
        }
    }
}
