/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchCountryRequestTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.enums.Membership;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class FetchCountryRequestTest {

    @Test
    public void testMembershipConstructor() {
        final Membership membership = Membership.FULL_MEMBER;

        final FetchCountryRequest request = new FetchCountryRequest(membership);
        request.verify();

        assertThat(request.getCountryIds(), is(nullValue()));
        assertThat(request.getMembership(), is(membership));
    }

    @Test
    public void testCountryIdsConstructor() {
        final List<String> countryIds = new ArrayList<>(2);
        countryIds.add("DE");
        countryIds.add("FR");

        final FetchCountryRequest request = new FetchCountryRequest(countryIds);
        request.verify();

        assertThat(request.getCountryIds(), is(countryIds));
        assertThat(request.getMembership(), is(nullValue()));
    }

    @Test
    public void testOverridingMembership() {
        final Membership membership = Membership.FULL_MEMBER;
        final List<String> countryIds = new ArrayList<>(2);
        countryIds.add("DE");
        countryIds.add("FR");

        final FetchCountryRequest request = new FetchCountryRequest(membership);
        request.setCountryIds(countryIds);

        assertThat(request.getCountryIds(), is(countryIds));
        assertThat(request.getMembership(), is(nullValue()));
    }

    @Test
    public void testOverridingCountryIds() {
        final Membership membership = Membership.FULL_MEMBER;
        final List<String> countryIds = new ArrayList<>(2);
        countryIds.add("DE");
        countryIds.add("FR");

        // Fetching Countries will only allow that one search parameter is
        // available, setting one will therefore override the other, as this
        // test shows
        final FetchCountryRequest request = new FetchCountryRequest(countryIds);
        assertThat(request.getCountryIds(), is(countryIds));
        assertThat(request.getMembership(), is(nullValue()));

        request.setMembership(membership);
        assertThat(request.getCountryIds(), is(nullValue()));
        assertThat(request.getMembership(), is(membership));

        request.setCountryIds(countryIds);
        assertThat(request.getCountryIds(), is(countryIds));
        assertThat(request.getMembership(), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullCountryIds() {
        final List<String> countryIds = null;

        new FetchCountryRequest(countryIds);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullMembership() {
        final Membership membership = null;

        new FetchCountryRequest(membership);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSettingNullCountryIds() {
        final FetchCountryRequest request = new FetchCountryRequest();

        request.setCountryIds(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSettingNullMembership() {
        final FetchCountryRequest request = new FetchCountryRequest();

        request.setMembership(null);
    }
}
