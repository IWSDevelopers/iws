/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection InstanceMethodNamingConvention
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
        final FetchCountryResponse response1 = client.fetchCountries(token, request1);
        final FetchCountryResponse response2 = client.fetchCountries(token, request2);
        final FetchCountryResponse response3 = client.fetchCountries(token, request3);

        assertThat(response1.isOk(), is(true));
        assertThat(response1.getCountries().isEmpty(), is(false));
        assertThat(response2.isOk(), is(true));
        assertThat(response2.getCountries().isEmpty(), is(false));
        assertThat(response3.isOk(), is(true));
        assertThat(response3.getCountries().isEmpty(), is(false));
    }
}
