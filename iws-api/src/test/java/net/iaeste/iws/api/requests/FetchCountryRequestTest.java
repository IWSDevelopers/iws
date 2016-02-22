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
