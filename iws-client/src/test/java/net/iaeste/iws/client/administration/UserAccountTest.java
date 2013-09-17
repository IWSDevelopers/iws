/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.administration.AdministrationClientTest
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

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchRoleRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.AccessClient;
import net.iaeste.iws.client.AdministrationClient;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection BreakStatement
 */
public final class UserAccountTest extends AbstractAdministration {

    //private static final String AUSTRIA_MEMBER_GROUP = "2cc7e1bb-01e8-43a2-9643-2e964cbd41c5";
    private final Administration administration = new AdministrationClient();

    @Override
    public void setup() {
        token = login("austria@iaeste.at", "austria");
        spy.clear();
    }

    @Override
    public void tearDown() {
        logout(token);
    }

    @Test
    public void testCreateAccountWithPassword() {
        // Create the new User Request Object
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("alpha@gamma.net");
        createUserRequest.setPassword("beta");
        createUserRequest.setFirstname("Alpha");
        createUserRequest.setLastname("Gamma");

        // Now, perform the actual test - create the Account, and verify that
        // the response is ok, and that a Notification was sent
        final CreateUserResponse result = administration.createUser(token, createUserRequest);
        assertThat(result.isOk(), is(true));
        assertThat(result.getUser(), is(not(nullValue())));
        assertThat(result.getUser().getUserId(), is(not(nullValue())));
        // Creating a new User should generate an Activate User notification
        final NotificationType type = NotificationType.ACTIVATE_USER;
        assertThat(spy.size(type), is(1));
        final String activationCode = spy.getNext(type).getFields().get(NotificationField.CODE);
        assertThat(activationCode, is(not(nullValue())));
    }

    @Test
    public void testCreateAccountWithoutPassword() {
        // Create the new User Request Object
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("beta@gamma.net");
        createUserRequest.setFirstname("Beta");
        createUserRequest.setLastname("Gamma");

        // Now, perform the actual test - create the Account, and verify that
        // the response is ok, and that a Notification was sent
        final Fallible result = administration.createUser(token, createUserRequest);
        assertThat(result.isOk(), is(true));
        // Creating a new User should generate an Activate User notification
        final NotificationType type = NotificationType.ACTIVATE_USER;
        assertThat(spy.size(type), is(1));
        final String activationCode = spy.getNext(type).getFields().get(NotificationField.CODE);
        assertThat(activationCode, is(not(nullValue())));

        // Check that the user is in the list of members
        final String memberGroupId = findMemberGroup(token).getId();
        token.setGroupId(memberGroupId);
        final FetchGroupRequest groupRequest = new FetchGroupRequest(memberGroupId);
        final FetchGroupResponse groupResponse = administration.fetchGroup(token, groupRequest);
        assertThat(groupResponse, is(not(nullValue())));
    }

    /**
     * The test attempts to run through the different steps in the user creation
     * process, and verify that the new Student account has the expected
     * permissions. This is the steps being tested:
     * <ol>
     *   <li><b>Success</b>: Create new Student Account</li>
     *   <li><b>Failure</b>: Attempt to login before activation</li>
     *   <li><b>Success</b>: Activate Student Account</li>
     *   <li><b>Success</b>: Login with newly created & activated Student Account</li>
     *   <li><b>Success</b>: Read Permissions for Student, verify Apply for Open Offers</li>
     *   <li><b>Success</b>: Logout from Student Account</li>
     * </ol>
     */
    @Test
    @Ignore("Ignored 2013-07-24 by Kim - Reason: The test is having sporaric failures, needs investigation!")
    public void testCreateStudentAccount() {
        // For this test, we also need the Access Client
        final AccessClient accessClient = new AccessClient();

        // Create the new User Request Object
        final String username = "student@gamma.net";
        final String password = "myPassword";
        final CreateUserRequest createUserRequest = new CreateUserRequest(username, password, "Student", "Graduate");
        createUserRequest.setStudentAccount(true);

        // Now, perform the actual test - create the Account, and verify that
        // the response is ok, and that a Notification was sent
        final Fallible result = administration.createUser(token, createUserRequest);
        assertThat(result.isOk(), is(true));
        assertThat(spy.size(), is(1));
        final String activationCode = spy.getNext().getFields().get(NotificationField.CODE);
        assertThat(activationCode, is(not(nullValue())));

        // Attempt to login using the new User Account. It should not yet work,
        // since the account is not activated
        final AuthenticationRequest request = new AuthenticationRequest(username, password);
        final AuthenticationResponse response1 = accessClient.generateSession(request);
        assertThat(response1.isOk(), is(false));
        assertThat(response1.getError(), is(IWSErrors.NO_USER_ACCOUNT_FOUND));

        // Activate the Account
        final Fallible acticationResult = administration.activateUser(activationCode);
        assertThat(acticationResult.isOk(), is(true));

        // Now, attempt to login again
        final AuthenticationResponse response2 = accessClient.generateSession(request);
        assertThat(response2.isOk(), is(true));
        assertThat(response2.getToken(), is(not(nullValue())));

        // Now, read the Permissions that the student has, basically, there is
        // only 1 permission - which is applying for Open Offers
        final FetchPermissionResponse permissionResponse = accessClient.fetchPermissions(response2.getToken());
        assertThat(permissionResponse.isOk(), is(true));
        assertThat(permissionResponse.getAuthorizations().get(0).getRole().getPermissions().contains(Permission.APPLY_FOR_OPEN_OFFER), is(true));

        // Deprecate the Students Session, the test is over :-)
        final Fallible deprecateSessionResult = accessClient.deprecateSession(response2.getToken());
        assertThat(deprecateSessionResult.isOk(), is(true));
    }

    /**
     * Testing that the generated Alias is correctly set to the name plus an
     * increasing number, of multiple people with the same name are created.
     */
    @Test
    public void createDuplicateAccount() {
        final String username1 = "user1@email.org";
        final String username2 = "user2@email.org";
        final String username3 = "user3@email.org";
        final String firstname = "firstname";
        final String lastname = "lastname";
        final String address = "@iaeste.org";

        final CreateUserRequest request1 = new CreateUserRequest(username1, firstname, lastname);
        final CreateUserResponse response1 = administration.createUser(token, request1);
        assertThat(response1, is(not(nullValue())));
        assertThat(response1.isOk(), is(true));
        assertThat(response1.getUser(), is(not(nullValue())));
        assertThat(response1.getUser().getAlias(), is(firstname + '.' + lastname + address));

        final CreateUserRequest request2 = new CreateUserRequest(username2, firstname, lastname);
        final CreateUserResponse response2 = administration.createUser(token, request2);
        assertThat(response2, is(not(nullValue())));
        assertThat(response2.isOk(), is(true));
        assertThat(response2.getUser(), is(not(nullValue())));
        assertThat(response2.getUser().getAlias(), is(firstname + '.' + lastname + 2 + address));

        final CreateUserRequest request3 = new CreateUserRequest(username3, firstname, lastname);
        final CreateUserResponse response3 = administration.createUser(token, request3);
        assertThat(response3, is(not(nullValue())));
        assertThat(response3.isOk(), is(true));
        assertThat(response3.getUser(), is(not(nullValue())));
        assertThat(response3.getUser().getAlias(), is(firstname + '.' + lastname + 3 + address));
    }

    @Test
    public void deleteNewAccount() {
        // Create the new User Request Object
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("gamma@beta.net");
        createUserRequest.setFirstname("Gamme");
        createUserRequest.setLastname("Beta");

        // First, we create the User account that we wish to delete
        final CreateUserResponse response = administration.createUser(token, createUserRequest);
        assertThat(response.isOk(), is(true));

        // Now, try to delete the account
        final UserRequest request = new UserRequest();
        request.setUser(response.getUser());
        request.setNewStatus(UserStatus.DELETED);
        final Fallible deletedUserResponse = administration.controlUserAccount(token, request);

        assertThat(deletedUserResponse, is(not(nullValue())));
        assertThat(deletedUserResponse.isOk(), is(true));
    }

    @Test
    public void testFetchRoles() {
        // Uses the National Group for Austria to get the list of Groups. As
        // Member Groups are not allowed to fetch the information, controlling
        // of accounts in Members is done together with handling of National
        // Members. The main reason for this, is to avoid that someone may
        // perform actions on the National Group, but invoking their Membership
        // privileges without having a National Group membersip.
        final FetchRoleRequest request = new FetchRoleRequest("c7b15f81-4f83-48e8-9ffb-9e73255f5e5e");
        final FetchRoleResponse response = administration.fetchRoles(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        // There should be a total of 5 roles for this Group
        assertThat(response.getRoles().size(), is(5));
    }

    @Test
    @Ignore("Test is currently being ignored, since the logic is being re-constructed.")
    public void testAddingUserToMemberGroup() {
        final String memberGroupId = findMemberGroup(token).getId();
        final FetchGroupRequest fetchGroupRequest = new FetchGroupRequest(memberGroupId);
        final FetchGroupResponse fetchGroupResponse = administration.fetchGroup(token, fetchGroupRequest);
        final UserGroupAssignmentRequest userGroupAssignmentRequest = new UserGroupAssignmentRequest();
        final Fallible userGroupResponse = administration.processUserGroupAssignment(token, userGroupAssignmentRequest);

        assertThat(userGroupResponse, is(not(nullValue())));
        assertThat(userGroupResponse.isOk(), is(false));
        assertThat(userGroupResponse.getError(), is(IWSErrors.AUTHORIZATION_ERROR));
        assertThat(userGroupResponse.getMessage(), is("User is not permitted to perform actions of type: PROCESS_USER_GROUP_ASSIGNMENT"));
    }

    @Test
    @Ignore("Test is currently being ignored, since the logic is being re-constructed.")
    public void testAddingUserToGroup() {
        final Group nsGroup = findNationalGroup(token);
        final FetchGroupRequest fetchGroupRequest = new FetchGroupRequest(nsGroup.getId());
        final FetchGroupResponse fetchGroupResponse = administration.fetchGroup(token, fetchGroupRequest);
        final UserGroupAssignmentRequest userGroupAssignmentRequest = new UserGroupAssignmentRequest();
        final Fallible userGroupResponse = administration.processUserGroupAssignment(token, userGroupAssignmentRequest);

        assertThat(userGroupResponse, is(not(nullValue())));
        assertThat(userGroupResponse.isOk(), is(false));
        assertThat(userGroupResponse.getError(), is(IWSErrors.NOT_IMPLEMENTED));
        assertThat(userGroupResponse.getMessage(), is("Method pending implementation."));
    }
}
