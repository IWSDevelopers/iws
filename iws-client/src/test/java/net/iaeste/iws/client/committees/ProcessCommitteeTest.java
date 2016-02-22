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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.CountryType;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.MailReply;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.responses.CommitteeResponse;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.AdministrationClient;
import net.iaeste.iws.client.CommitteeClient;
import net.iaeste.iws.common.notification.NotificationType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class ProcessCommitteeTest extends AbstractTest {

    private final Administration administration = new AdministrationClient();
    private final Committees committees = new CommitteeClient();

    /**
     * {@inheritDoc}
     */
    @Before
    @Override
    public void setup() {
        // We have configured Australia as a member of the Board
        token = login("australia@iaeste.au", "australia");
        spy.clear();
    }

    /**
     * {@inheritDoc}
     */
    @After
    @Override
    public void tearDown() {
        logout(token);
    }

    // =========================================================================
    // Testing Committee Processing
    // =========================================================================

    /**
     * Following test will perform a number of sub tests as well, since we
     * would like to see how the IWS handles the following cases:
     * <ol>
     *   <li><b>1.</b> Create new Co-Operating Institution; DD Country AA => Success</li>
     *   <li><b>2.</b> Create the same Co-Operating Institution again => Fail</li>
     *   <li><b>3.</b> Create new Co-Operating Institution DaD Country AA => Success</li>
     *   <li><b>4.</b> Retrieve a list of Committees from Country AA => 2 Committees</li>
     * </ol>
     */
    @Test
    public void testCreateCommittee() {
        // Before we start, let's fetch the country information for Country AA,
        // and verify that the current status is not a Co-Operating Institution
        final Country before = fetchCountry("AA");
        assertThat(before.getMembership(), is(Membership.LISTED));

        // First create a new Committee and verify it was done correctly
        final CommitteeResponse createResponse = createCommittee("AA", "Donald", "Duck", "DD");
        assertThat(createResponse.isOk(), is(true));

        // Now, we're repeating the same request, expecting an error!
        final Fallible failedCreateResponse = createCommittee("AA", "Donald", "Duck", "DD");
        assertThat(failedCreateResponse.isOk(), is(false));
        assertThat(failedCreateResponse.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(failedCreateResponse.getMessage(), containsString("A Committee with the name "));

        // Okay, we forgot to give the correct name
        final CommitteeResponse newCreateResponse = createCommittee("AA", "Daisy", "Duck", "DaD");
        assertThat(newCreateResponse.isOk(), is(true));

        // Now, let's see if we can find the newly created Committees
        final List<String> countryIds = new ArrayList<>();
        countryIds.add("AA");
        final FetchCommitteeResponse committeeResponse = committees.fetchCommittees(token, new FetchCommitteeRequest(countryIds));
        assertThat(committeeResponse.isOk(), is(true));
        assertThat(committeeResponse.getCommittees().size(), is(2));

        // And wrap up with running the initial check again, this time
        // expecting a different result.
        final Country after = fetchCountry("AA");
        assertThat(after.getMembership(), is(Membership.COOPERATING_INSTITUTION));
    }

    @Test
    public void testCreateCommitteeForExistingAssociateMember() {
        // Azerbaijan is an Associate Member
        final Fallible failedCreateResponse = createCommittee("AZ", "Donald", "Duck", "DD");
        assertThat(failedCreateResponse.isOk(), is(false));
        assertThat(failedCreateResponse.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(failedCreateResponse.getMessage(), is("Cannot create a new Cooperating Institution for a Member Country."));
    }

    @Test
    public void testCreateCommitteeForExistingFullMember() {
        // Austria is a Full Member
        final Fallible failedCreateResponse = createCommittee("AT", "Donald", "Duck", "DD");
        assertThat(failedCreateResponse.isOk(), is(false));
        assertThat(failedCreateResponse.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(failedCreateResponse.getMessage(), is("Cannot create a new Cooperating Institution for a Member Country."));
    }

    @Test
    public void testCreateCommitteeInvalidCountry() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CREATE);
        request.setCountryCode("XX");
        request.setFirstname("Donald");
        request.setLastname("Duck");
        request.setUsername("Donald@duck.com");
        request.setInstitutionName("Donald Duck");
        request.setInstitutionAbbreviation("DD");

        final Fallible response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.OBJECT_IDENTIFICATION_ERROR));
        assertThat(response.getMessage(), is("No country was found."));
    }

    @Test
    public void testCreateCommitteeForAssociateMember() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CREATE);
        request.setCountryCode("AZ");
        request.setFirstname("Donald");
        request.setLastname("Duck");
        request.setUsername("Donald@duck.com");
        request.setInstitutionName("Donald Duck");
        request.setInstitutionAbbreviation("DD");

        final Fallible response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response.getMessage(), is("Cannot create a new Cooperating Institution for a Member Country."));
    }

    @Test
    public void testCreateCommitteeForFullMember() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CREATE);
        request.setCountryCode("DE");
        request.setFirstname("Donald");
        request.setLastname("Duck");
        request.setUsername("Donald@duck.com");
        request.setInstitutionName("Donald Duck");
        request.setInstitutionAbbreviation("DD");

        final Fallible response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response.getMessage(), is("Cannot create a new Cooperating Institution for a Member Country."));
    }

    // =========================================================================
    // Testing NS Change of a Committee
    // =========================================================================

    @Test
    public void testChangeCommitteeNS() {
        final CommitteeResponse committee = createCommittee("AC", "Alan", "Miller", "AM");

        // We need to make sure that the existing account is also active, so
        // we will activate it.
        final String activationCode = readCode(NotificationType.ACTIVATE_NEW_USER);
        final Fallible activateResponse = administration.activateUser(activationCode);
        assertThat(activateResponse.isOk(), is(true));

        // Let's try to set the NS to a new User.
        final CommitteeRequest request1 = new CommitteeRequest();
        request1.setAction(Action.CHANGE_NS);
        request1.setNationalCommittee(committee.getCommittee().getGroup());
        request1.setFirstname("Bob");
        request1.setLastname("Whitehead");
        request1.setUsername("Bob@Whitehead.com");
        final CommitteeResponse response1 = committees.processCommittee(token, request1);
        assertThat(response1.isOk(), is(true));

        // and now revert the NS
        final CommitteeRequest request2 = new CommitteeRequest();
        request2.setAction(Action.CHANGE_NS);
        request2.setNationalCommittee(committee.getCommittee().getGroup());
        request2.setNationalSecretary(committee.getCommittee().getUser());
        final CommitteeResponse response2 = committees.processCommittee(token, request2);
        assertThat(response2.isOk(), is(true));
    }

    @Test
    public void testChangingNSofInvalidCommittee() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setNationalCommittee(prepareInvalidGroup(GroupType.NATIONAL));
        request.setNationalSecretary(prepareInvalidUser());

        final CommitteeResponse response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response.getMessage(), is("Attempting to change National Secretary non-existing Committee."));
    }

    @Test
    public void testChangingNsForSuspendedCommittee() {
        final UserGroup committee = findCommittees(Membership.ASSOCIATE_MEMBER, 0);
        final Group group = committee.getGroup();

        // We have to suspend a committee before we can run this test.
        final CommitteeRequest suspendRequest = new CommitteeRequest();
        suspendRequest.setAction(Action.SUSPEND);
        suspendRequest.setNationalCommittee(group);
        final CommitteeResponse suspendResponse = committees.processCommittee(token, suspendRequest);
        assertThat(suspendResponse.isOk(), is(true));

        // Now run the actual test
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CHANGE_NS);
        request.setNationalCommittee(group);
        request.setNationalSecretary(prepareInvalidUser());
        final CommitteeResponse response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response.getMessage(), is("Attempting to change National Secretary for a suspended Committee, is not allowed."));

        // Wrap up the test with re-activating the committee.
        final CommitteeRequest activateRequest = new CommitteeRequest();
        activateRequest.setAction(Action.ACTIVATE);
        activateRequest.setNationalCommittee(group);
        final CommitteeResponse activateResponse = committees.processCommittee(token, activateRequest);
        assertThat(activateResponse.isOk(), is(true));
    }

    @Test
    public void testSetNsToSuspendedUser() {
        // In our test database, the first Committee is Argentina, which have a
        // suspended user as NS
        final UserGroup committee = findCommittees(Membership.FULL_MEMBER, 0);

        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CHANGE_NS);
        request.setNationalCommittee(committee.getGroup());
        request.setNationalSecretary(committee.getUser());

        final CommitteeResponse response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response.getMessage(), is("National Secretary provided is not a valid User."));
    }

    @Test
    public void testSetNsToExistingNs() {
        final UserGroup committee = findCommittees(Membership.FULL_MEMBER, 1);

        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CHANGE_NS);
        request.setNationalCommittee(committee.getGroup());
        request.setNationalSecretary(committee.getUser());

        final CommitteeResponse response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response.getMessage(), is("Attempting to make existing National Secretary new National Secretary is not allowed."));
    }

    @Test
    public void testSetNsToExistingUser1() {
        // Tries to set the NS to a user which already exist with the given
        // username in the system. Note, that the IWS is expected to fail, not
        // because it is the same user, but because the username is already
        // registered.
        final UserGroup committee = findCommittees(Membership.FULL_MEMBER, 0);

        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CHANGE_NS);
        request.setNationalCommittee(committee.getGroup());
        request.setFirstname(committee.getUser().getFirstname());
        request.setLastname(committee.getUser().getLastname());
        request.setUsername(committee.getUser().getUsername());

        final CommitteeResponse response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response.getMessage(), is("Cannot create new National Secretary for existing User."));
    }

    @Test
    public void testSetNsToExistingUser2() {
        // This takes an existing user account from a different committee, and
        // tries to set that as NS, which is illegal
        final UserGroup committee1 = findCommittees(Membership.FULL_MEMBER, 0);
        final UserGroup committee2 = findCommittees(Membership.FULL_MEMBER, 1);

        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CHANGE_NS);
        request.setNationalCommittee(committee1.getGroup());
        request.setNationalSecretary(committee2.getUser());

        final CommitteeResponse response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response.getMessage(), is("New National Secretary is not a member of the Committee."));
    }

    // =========================================================================
    // Testing Upgrade Committee
    // =========================================================================

    @Test
    public void testUpgradeCommittee() {
        final CommitteeResponse response1 = createCommittee("AB", "Speed", "Racer", "RS");
        assertThat(response1.isOk(), is(true));

        // When 2 Committees exist, we cannot upgrade!
        final CommitteeResponse response2 = createCommittee("AB", "Rex", "Racer", "RR");
        assertThat(response2.isOk(), is(true));

        final CommitteeRequest request = new CommitteeRequest();
        request.setNationalCommittee(response1.getCommittee().getGroup());
        request.setAction(Action.UPGRADE);

        // Now, we expect a fail - as you cannot upgrade from Co-operating
        // Institution while another Committee exist
        final CommitteeResponse response3 = committees.processCommittee(token, request);
        assertThat(response3.isOk(), is(false));
        assertThat(response3.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response3.getMessage(), is("Attempting to upgrade a Cooperating Institution to Associate Membership, while other Cooperating Institutions still exist for the Country."));

        // Cannot upgrade as long as we have 2 Committees, solution is either to merge (unspecified amd this not supported)
        // Now, we're suspending & deleting the second Committee, and then it
        // should work. Note, that we cannot delete an Active Committee, it
        // must be suspended first.
        final CommitteeRequest suspendRequest = new CommitteeRequest();
        suspendRequest.setNationalCommittee(response2.getCommittee().getGroup());
        suspendRequest.setAction(Action.SUSPEND);
        final CommitteeResponse response4 = committees.processCommittee(token, suspendRequest);
        assertThat(response4.isOk(), is(true));

        // Upgrading Co-Operating Institution to Associate Member
        final CommitteeResponse response5 = committees.processCommittee(token, request);
        assertThat(response5.isOk(), is(false));
        assertThat(response5.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response5.getMessage(), is("Attempting to upgrade a Cooperating Institution to Associate Membership, while other Cooperating Institutions still exist for the Country."));

        suspendRequest.setAction(Action.DELETE);
        final CommitteeResponse deleteSecond = committees.processCommittee(token, suspendRequest);
        assertThat(deleteSecond.isOk(), is(true));

        // Upgrading Co-Operating Institution to Associate Member
        final CommitteeResponse response7 = committees.processCommittee(token, request);
        assertThat(response7.isOk(), is(true));

        // Now, we're trying to suspend the Group, and then upgrade it
        final CommitteeRequest suspendRequest2 = new CommitteeRequest();
        suspendRequest2.setNationalCommittee(response1.getCommittee().getGroup());
        suspendRequest2.setAction(Action.SUSPEND);
        final CommitteeResponse response8 = committees.processCommittee(token, suspendRequest2);
        assertThat(response8.isOk(), is(true));

        // Upgrading Associate Member to Full Member
        final CommitteeResponse response9 = committees.processCommittee(token, request);
        assertThat(response9.isOk(), is(false));
        assertThat(response9.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response9.getMessage(), is("Attempting to upgrade a non-Active Committee, is not allowed."));

        // Let's reactivate the Committee, and then try again to upgrade
        final CommitteeRequest activateRequest = new CommitteeRequest();
        activateRequest.setNationalCommittee(response1.getCommittee().getGroup());
        activateRequest.setAction(Action.ACTIVATE);
        final CommitteeResponse response10 = committees.processCommittee(token, activateRequest);
        assertThat(response10.isOk(), is(true));

        // Upgrading Associate Member to Full Member
        final CommitteeResponse response11 = committees.processCommittee(token, request);
        assertThat(response11.isOk(), is(true));

        // Upgrading a Full Member...
        final CommitteeResponse response12 = committees.processCommittee(token, request);
        assertThat(response12.isOk(), is(false));
        assertThat(response12.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response12.getMessage(), is("Attempting to upgrade a Committee, which is already a Full Member."));
    }

    @Test
    public void testUpgradeNonNationalCommittee() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.UPGRADE);

        // First test, let's try to upgrade a non National Group
        try {
            request.setNationalCommittee(prepareInvalidGroup(GroupType.PRIVATE));
            fail("What - we can process a non-Committee Group.");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Cannot process a Committee which is not having type Staff"));
        }

        // Second test, let's try to upgrade an invalid National Group
        request.setNationalCommittee(prepareInvalidGroup(GroupType.NATIONAL));
        final Fallible response1 = committees.processCommittee(token, request);
        assertThat(response1.isOk(), is(false));
        assertThat(response1.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response1.getMessage(), is("Attempting to upgrade non-existing Committee."));
    }

    // =========================================================================
    // Testing Suspend/Activate Committee
    // =========================================================================

    @Test
    public void testSuspensionAndActivation() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.ACTIVATE);
        request.setNationalCommittee(findNationalGroup("UZ"));

        // First, let's just make a negative testing... We should not be
        // allowed to activate an active group!
        final Fallible response1 = committees.processCommittee(token, request);
        assertThat(response1.isOk(), is(false));
        assertThat(response1.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response1.getMessage(), is("Cannot activate an already active Committee."));

        // Now, repeat but with a suspension
        request.setAction(Action.SUSPEND);
        final CommitteeResponse response2 = committees.processCommittee(token, request);
        assertThat(response2.isOk(), is(true));

        // As the Group is now suspended, let's perform a second negative test
        final CommitteeResponse response3 = committees.processCommittee(token, request);
        assertThat(response3.isOk(), is(false));
        assertThat(response3.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response3.getMessage(), is("Cannot suspend an already suspended Committee."));

        // Finally, let's reactivate the Group
        request.setAction(Action.ACTIVATE);
        final Fallible response4 = committees.processCommittee(token, request);
        assertThat(response4.isOk(), is(true));
    }

    @Test
    public void testActivationNegative() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.ACTIVATE);

        // First test, let's try to activate a non National Group
        try {
            request.setNationalCommittee(prepareInvalidGroup(GroupType.PRIVATE));
            fail("What - we can process a non-Committee Group.");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Cannot process a Committee which is not having type Staff"));
        }

        // Second test, let's try to activate an invalid National Group
        request.setNationalCommittee(prepareInvalidGroup(GroupType.NATIONAL));
        final Fallible response1 = committees.processCommittee(token, request);
        assertThat(response1.isOk(), is(false));
        assertThat(response1.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response1.getMessage(), is("Attempting to activate non-existing Committee."));
    }

    @Test
    public void testSuspensionNegative() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.SUSPEND);

        // First test, let's try to suspend a non National Group
        try {
            request.setNationalCommittee(prepareInvalidGroup(GroupType.PRIVATE));
            fail("What - we can process a non-Committee Group.");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Cannot process a Committee which is not having type Staff"));
        }

        // Second test, let's try to suspend an invalid National Group
        request.setNationalCommittee(prepareInvalidGroup(GroupType.NATIONAL));
        final Fallible response1 = committees.processCommittee(token, request);
        assertThat(response1.isOk(), is(false));
        assertThat(response1.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response1.getMessage(), is("Attempting to suspend non-existing Committee."));
    }

    // =========================================================================
    // Testing Delete Committee
    // =========================================================================

    @Test
    public void testDeleteCommittee() {
        assertThat(Boolean.TRUE, is(true));
    }

    @Test
    public void testDeleteCommitteeNegative() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.DELETE);

        // First test, let's try to delete a non National Group
        try {
            request.setNationalCommittee(prepareInvalidGroup(GroupType.PRIVATE));
            fail("What - we can process a non-Committee Group.");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Cannot process a Committee which is not having type Staff"));
        }

        // Second test, let's try to delete an invalid National Group
        request.setNationalCommittee(prepareInvalidGroup(GroupType.NATIONAL));
        final Fallible response1 = committees.processCommittee(token, request);
        assertThat(response1.isOk(), is(false));
        assertThat(response1.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response1.getMessage(), is("Attempting to delete non-existing Committee."));
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private Country fetchCountry(final String countryCode) {
        final List<String> codes = new ArrayList<>(1);
        codes.add(countryCode);

        final FetchCountryRequest request = new FetchCountryRequest();
        request.setCountryType(CountryType.COUNTRIES);
        request.setCountryIds(codes);

        final FetchCountryResponse response = administration.fetchCountries(token, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getCountries().size(), is(1));

        return response.getCountries().get(0);
    }

    private Group prepareInvalidGroup(final GroupType type) {
        final Group group = new Group();
        group.setGroupName("bla");
        group.setGroupType(type);
        group.setPrivateList(false);
        group.setPrivateListReplyTo(MailReply.REPLY_TO_LIST);
        group.setPublicList(false);
        group.setPublicListReplyTo(MailReply.REPLY_TO_SENDER);

        return group;
    }

    private User prepareInvalidUser() {
        final User user = new User();
        user.setUserId(UUID.randomUUID().toString());

        return user;
    }

    private UserGroup findCommittees(final Membership membership, final int index) {
        final FetchCommitteeRequest request = new FetchCommitteeRequest();
        request.setMembership(membership);

        final FetchCommitteeResponse response = committees.fetchCommittees(token, request);
        assertThat(response.isOk(), is(true));

        return response.getCommittees().get(index);
    }

    private Group findNationalGroup(final String countryCode) {
        final FetchCommitteeRequest request = new FetchCommitteeRequest();
        final List<String> countryCodes = new ArrayList<>(1);
        countryCodes.add(countryCode);
        request.setCountryIds(countryCodes);
        final FetchCommitteeResponse response = committees.fetchCommittees(token, request);

        return response.getCommittees().get(0).getGroup();
    }

    private CommitteeResponse createCommittee(final String countryCode, final String firstname, final String lastname, final String abbreviation) {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CREATE);
        request.setCountryCode(countryCode);
        request.setFirstname(firstname);
        request.setLastname(lastname);
        request.setUsername(firstname + '@' + lastname + ".com");
        request.setInstitutionName(firstname + ' ' + lastname);
        request.setInstitutionAbbreviation(abbreviation);

        return committees.processCommittee(token, request);
    }
}
