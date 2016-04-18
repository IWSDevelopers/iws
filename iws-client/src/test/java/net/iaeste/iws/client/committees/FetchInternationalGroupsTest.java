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
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.CommitteeClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class FetchInternationalGroupsTest extends AbstractTest {

    private final Committees committees = new CommitteeClient();

    /**
     * {@inheritDoc}
     */
    @Before
    @Override
    public void setUp() {
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
    public void testFetchingAllInternationalGroups() {
        final FetchInternationalGroupRequest request = new FetchInternationalGroupRequest();
        final FetchInternationalGroupResponse response = committees.fetchInternationalGroups(token, request);

        // From the test data, we have a three Active/Suspended International Groups: SID, IDT, Alumni
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        assertThat(response.getGroups().size(), is(3));
    }

    @Test
    public void testFetchingActiveInternationalGroups() {
        final Set<GroupStatus> statuses = EnumSet.of(GroupStatus.ACTIVE);
        final FetchInternationalGroupRequest request = new FetchInternationalGroupRequest(new HashSet(statuses));
        final FetchInternationalGroupResponse response = committees.fetchInternationalGroups(token, request);

        // From the test data, we have a two Active International Groups: SID, IDT
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        assertThat(response.getGroups().size(), is(2));
    }

    @Test
    public void testFetchingSuspendedInternationalGroups() {
        final HashSet<GroupStatus> statuses = new HashSet();
        statuses.add(GroupStatus.SUSPENDED);
        final FetchInternationalGroupRequest request = new FetchInternationalGroupRequest(statuses);
        final FetchInternationalGroupResponse response = committees.fetchInternationalGroups(token, request);

        // From the test data, we have a single Suspended International Group: Alumni
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        assertThat(response.getGroups().size(), is(1));
    }
}
