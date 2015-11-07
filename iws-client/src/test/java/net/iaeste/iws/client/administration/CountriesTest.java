/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.administration.CountriesTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
    public void setup() {
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
