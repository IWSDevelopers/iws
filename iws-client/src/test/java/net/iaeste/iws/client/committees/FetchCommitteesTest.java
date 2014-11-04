/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.committees.FetchCommitteesTest
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
        assertThat(response.getCommittees().size(), is(86));
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
        assertThat(response.getCommittees().size(), is(60));
    }

    @Test
    public void testFetchingAssociateMemberCommittees() {
        final FetchCommitteeRequest request = new FetchCommitteeRequest(Membership.ASSOCIATE_MEMBER);
        final FetchCommitteeResponse response = committees.fetchCommittees(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        assertThat(response.getCommittees().size(), is(8));
    }

    @Test
    public void testFetchingCoopMemberCommittees() {
        final FetchCommitteeRequest request = new FetchCommitteeRequest(Membership.COOPERATING_INSTITUTION);
        final FetchCommitteeResponse response = committees.fetchCommittees(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        assertThat(response.getCommittees().size(), is(18));
    }
}
