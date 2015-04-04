/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.committees.FetchInternationalGroupsTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
