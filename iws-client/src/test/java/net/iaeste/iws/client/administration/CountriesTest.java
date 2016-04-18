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
package net.iaeste.iws.client.administration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.enums.CountryType;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CountriesTest extends AbstractAdministration {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUp() {
        token = login("canada@iaeste.ca", "canada");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tearDown() {
        logout(token);
    }

    // =========================================================================
    // Positive Tests
    // =========================================================================

    @Test
    public void testFindindCountries() {
        final List<String> countryIds = new ArrayList<>();
        countryIds.add("AT");
        countryIds.add("DE");
        countryIds.add("DK");
        final FetchCountryRequest request1 = new FetchCountryRequest(countryIds);
        final FetchCountryRequest request2 = new FetchCountryRequest(Membership.FULL_MEMBER);
        final FetchCountryRequest request3 = new FetchCountryRequest();
        final FetchCountryResponse response1 = administration.fetchCountries(token, request1);
        final FetchCountryResponse response2 = administration.fetchCountries(token, request2);
        final FetchCountryResponse response3 = administration.fetchCountries(token, request3);

        assertThat(response1.isOk(), is(true));
        assertThat(response1.getCountries().isEmpty(), is(false));
        assertThat(response2.isOk(), is(true));
        assertThat(response2.getCountries().isEmpty(), is(false));
        assertThat(response3.isOk(), is(true));
        assertThat(response3.getCountries().isEmpty(), is(false));
    }

    /**
     * There was a bug report from UAE (see trac ticket #763), the report stated
     * that Algeria, Iraq, Sudan & Palestine wasn't in the Nationalities list.
     * The purpose of this test is to verify that they are.<br />
     *   The test is written using the test database, which only contain those
     * countries who are also members. This means that the test database will
     * not be able to show that the code really works. Therefore the test has
     * also been run via the real database using a real user account, to ensure
     * that it really works. However, this cannot be committed.<br />
     *   Since the comment above states that there's some limitations in the
     * system, it is obvious that the test data should be expanded to cover more
     * examples to ensure that the tests can be written properly.
     */
    @Test
    public void testIfCertainCountriesExists() {
        final FetchCountryRequest request = new FetchCountryRequest();
        request.setCountryType(CountryType.COUNTRIES);
        final FetchCountryResponse response = administration.fetchCountries(token, request);

        assertThat(response.isOk(), is(true));
        // 86 members + 3 listed
        assertThat(response.getCountries().size(), is(89));
    }
}
