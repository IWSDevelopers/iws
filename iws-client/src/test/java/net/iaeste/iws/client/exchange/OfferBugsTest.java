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
package net.iaeste.iws.client.exchange;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.client.ExchangeClient;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Testing of reported bugs.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class OfferBugsTest extends AbstractOfferTest {

    private static final int exchangeYear = Verifications.calculateExchangeYear();
    private static final String PL_YEAR = "PL-" + exchangeYear;

    private final Exchange exchange = new ExchangeClient();
    private AuthenticationToken austriaToken = null;
    private AuthenticationToken croatiaToken = null;

    @Override
    public void setUp() {
        token = login("poland@iaeste.pl", "poland");
        austriaToken = login("austria@iaeste.at", "austria");
        croatiaToken = login("croatia@iaeste.hr", "croatia");
    }

    @Override
    public void tearDown() {
        logout(token);
        logout(austriaToken);
        logout(croatiaToken);
    }

    /**
     * See Trac bug report #451.
     */
    @Test
    public void testSavingOfferWithoutCountry() {
        final String refno = PL_YEAR + "-BUG451-R";
        final Offer offer = TestData.prepareFullOffer(refno, "Poland A/S");
        // Now setting the value to null, we need to go through some hoops here,
        // as our defensive copying will otherwise prevent the null from being
        // set properly!
        final Employer employer = offer.getEmployer();
        final Address address = employer.getAddress();
        address.setCountry(null);
        employer.setAddress(address);
        offer.setEmployer(employer);
        final ProcessOfferRequest request = prepareRequest(offer);

        // Invoking the IWS with our null-country request
        final OfferResponse response = exchange.processOffer(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        assertThat(response.getOffer().getEmployer().getAddress().getCountry().getCountryCode(), is("PL"));

        // Find All offers, should review this one.
        final FetchOffersRequest fetchRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, fetchRequest);
        assertThat(fetchResponse.getOffers().isEmpty(), is(false));
        final Offer offerWithNS = fetchResponse.getOffers().get(0);
        assertThat(offerWithNS.getNsFirstname(), is("NS"));
        assertThat(offerWithNS.getNsLastname(), is("Poland"));
    }

    /**
     * During the 2015 Annual Conference an Offer was inserted in into the IW,
     * and caused subsequent problems as it contained 5 Specializations,
     * although only 3 is allowed. After inquiring the delegation who created
     * the Offer, they informed that they had read the CSV upload documentation
     * stating that the Specializations must be delimited by pipes "|". So
     * instead of using the normal Form in IW4 to upload the offers, they added
     * all of them with pipes.<br />
     *   The result was that the Offer DTO had a set of Strings with only one
     * value (the delimited specializations), thus omitting the standard checks
     * in the Offer DTO. When inserted. the Set is expanded into a pipe "|"
     * delimited String and that all worked fine, but reading it out again
     * caused an error.<br />
     *   This test is replicating the issue and will demonstrate the flaw, and
     * also the fix. For more information, see Trac ticket #971 (#966).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLongStringSetsTicket971() {
        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-SPECIAL", "Specialization Employer");
        final Set<String> specializations = new HashSet<>();
        final String specialization = "First | Second | Third | Fourth | Fifth";
        specializations.add(specialization);
        offer.setSpecializations(specializations);

        final ProcessOfferRequest request = prepareRequest(offer);
        final OfferResponse response = exchange.processOffer(token, request);
        assertThat(response.isOk(), is(true));
    }
}
