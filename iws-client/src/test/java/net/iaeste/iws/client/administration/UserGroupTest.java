/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.administration.UserGroupTest
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
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchRoleRequest;
import net.iaeste.iws.api.requests.OwnerRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.util.Fallible;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class UserGroupTest extends AbstractAdministration {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
        token = login("denmark@iaeste.dk", "denmark");
        spy.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tearDown() {
        logout(token);
    }

    @Test
    public void testFetchingRoles() {
        // Brazil has been added as Owner to the Board
        final AuthenticationToken myToken = login("brazil@iaeste.br", "brazil");
        final String sidGroupId = "80962576-3e38-4858-be0d-57252e7316b1";
        final FetchRoleRequest request = new FetchRoleRequest(sidGroupId);
        final FetchRoleResponse response = client.fetchRoles(myToken, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getRoles().isEmpty(), is(false));

        // Always remember to logout
        logout(myToken);
    }

    @Test
    public void testAddingUserToNationalGroup() {
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("user@iaeste.dk");
        createUserRequest.setFirstname("Firstname");
        createUserRequest.setLastname("Lastname");
        final CreateUserResponse createUserResponse = client.createUser(token, createUserRequest);
        assertThat(createUserResponse.isOk(), is(true));

        final Group nationalGroup = findNationalGroup(token);
        final FetchRoleRequest fetchRoleRequest = new FetchRoleRequest(nationalGroup.getGroupId());
        final FetchRoleResponse fetchRoleResponse = client.fetchRoles(token, fetchRoleRequest);

        // Add the user to the National Group
        final UserGroup userGroup = new UserGroup();
        userGroup.setGroup(nationalGroup);
        userGroup.setUser(createUserResponse.getUser());
        userGroup.setRole(fetchRoleResponse.getRoles().get(1));
        final UserGroupAssignmentRequest request = new UserGroupAssignmentRequest(userGroup);
        final Fallible response = client.processUserGroupAssignment(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));

        // Delete the user again
        request.setDeleteUser(true);
        final Fallible deleteResponse = client.processUserGroupAssignment(token, request);
        assertThat(deleteResponse, is(not(nullValue())));
        assertThat(deleteResponse.isOk(), is(true));
    }

    @Test
    public void testChangingOwnershipOfLocalCommittee() {
        final User user = createAndActiveUser(token, "alfa@iaeste.dk", "Alfa", "Beta");
        final Group group = createGroup(token, GroupType.LOCAL, "LC Copenhagen");

        final OwnerRequest request = new OwnerRequest(group, user);
        final Fallible response = client.changeGroupOwner(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        final FetchGroupRequest groupRequest = new FetchGroupRequest(group.getGroupId());
        groupRequest.setFetchUsers(true);
        final FetchGroupResponse groupResponse = client.fetchGroup(token, groupRequest);
        assertThat(groupResponse.isOk(), is(true));
        assertThat(groupResponse.getMembers().size(), is(2));
    }

    @Test
    public void testChangingNationalSecretaryToNewMember() {
        final User user = createUser(token, "beta@iaeste.dk", "Beta", "Alfa");
        final Group group = findNationalGroup(token);

        final OwnerRequest request = new OwnerRequest(group, user);
        final Fallible response = client.changeGroupOwner(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(response.getMessage(), is("Cannot reassign ownership to an inactive person."));
    }

    @Test
    public void testChangingNationalSecretaryToActiveMember() {
        // We need to use a different token, since this test will otherwise
        // cause other tests to fail!
        final AuthenticationToken alternativeToken = login("norway@iaeste.no", "norway");
        final User user = createAndActiveUser(alternativeToken, "gamma@iaeste.dk", "Gamma", "Beta");
        final Group group = findNationalGroup(alternativeToken);

        // Change the Owner
        final OwnerRequest request = new OwnerRequest(group, user);
        final Fallible response = client.changeGroupOwner(alternativeToken, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));

        // Ensure that we now have 2 members
        final FetchGroupRequest groupRequest = new FetchGroupRequest(group.getGroupId());
        groupRequest.setFetchUsers(true);
        final FetchGroupResponse groupResponse = client.fetchGroup(alternativeToken, groupRequest);
        assertThat(groupResponse.isOk(), is(true));
        assertThat(groupResponse.getMembers().size(), is(2));

        // And just to verify that we're no longer the owner - we're attempting
        // to change the Ownership again, and this time expecting an
        // Authorization error
        final Fallible failedResponse = client.changeGroupOwner(token, request);
        assertThat(failedResponse, is(not(nullValue())));
        assertThat(failedResponse.isOk(), is(false));
        assertThat(failedResponse.getError(), is(IWSErrors.AUTHORIZATION_ERROR));
        assertThat(failedResponse.getMessage().contains("is not permitted to perform action 'Change Group Owner'."), is(true));
        logout(alternativeToken);
    }

    @Test
    public void testChangingNationalSecretaryToSelf() {
        final FetchGroupRequest groupRequest = new FetchGroupRequest(GroupType.NATIONAL);
        groupRequest.setFetchUsers(true);
        final FetchGroupResponse groupResponse = client.fetchGroup(token, groupRequest);
        final Group group = groupResponse.getGroup();
        final UserGroup user = groupResponse.getMembers().get(0);

        final OwnerRequest request = new OwnerRequest(group, user.getUser());
        final Fallible response = client.changeGroupOwner(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(response.getMessage(), is("Cannot reassign ownership to the current Owner."));
    }

    @Test
    public void testChangingNationalSecretatyToNonMember() {
        final AuthenticationToken newToken = login("norway@iaeste.no", "norway");
        final User user = createAndActiveUser(newToken, "member@iaeste.no", "New", "Member");
        logout(newToken);
        final Group group = findNationalGroup(token);

        final OwnerRequest request = new OwnerRequest(group, user);
        final Fallible response = client.changeGroupOwner(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(response.getMessage(), is("Cannot reassign National Secretary to a person who is not a member from Denmark."));
    }
}
