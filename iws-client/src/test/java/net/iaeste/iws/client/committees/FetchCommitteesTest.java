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
package net.iaeste.iws.client.committees;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.CommitteeClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class FetchCommitteesTest extends AbstractTest {

    private final Committees committees = new CommitteeClient();

    /**
     * {@inheritDoc}
     */
    @Before
    @Override
    public void setup() {
        token = login("australia@iaeste.au", "australia");
    }

    /**
     * {@inheritDoc}
     */
    @After
    @Override
    public void tearDown() {
        logout(token);
    }

    @Test
    public void testFetchingAllCommittees() {
        final FetchCommitteeRequest request = new FetchCommitteeRequest();
        final FetchCommitteeResponse response = committees.fetchCommittees(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void testFetchingCommitteesByCountryIds() {
        // List of the Founding Members:
        //   BE  Belgium
        //   CS  Czechoslovakia <- Splitted into Czech Republic & Slovakia
        //   DK  Denmark
        //   FI  Finland
        //   FR  France
        //   NL  Netherlands
        //   NO  Norway
        //   SE  Sweden
        //   CH  Switzerland
        //   UK  United Kingdom
        final String[] ids = { "BE", "CS", "DK", "FI", "FR", "NL", "NO", "SE", "CH", "UK" };
        final FetchCommitteeRequest request = new FetchCommitteeRequest(Arrays.asList(ids));
        final FetchCommitteeResponse response = committees.fetchCommittees(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        // As Czechoslovakia no longer exists, we're expecting a result of 9
        assertThat(response.getCommittees().size(), is(9));
    }

    @Test
    public void testFetchingFullMemberCommittees() {
        final FetchCommitteeRequest request = new FetchCommitteeRequest(Membership.FULL_MEMBER);
        final FetchCommitteeResponse response = committees.fetchCommittees(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void testFetchingAssociateMemberCommittees() {
        final FetchCommitteeRequest request = new FetchCommitteeRequest(Membership.ASSOCIATE_MEMBER);
        final FetchCommitteeResponse response = committees.fetchCommittees(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void testFetchingCoopMemberCommittees() {
        final FetchCommitteeRequest request = new FetchCommitteeRequest(Membership.COOPERATING_INSTITUTION);
        final FetchCommitteeResponse response = committees.fetchCommittees(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
    }
}
