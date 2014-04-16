/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.administration.UserAccountTest
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

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.exchange.Student;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.requests.AccountNameRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchRoleRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.AccessClient;
import net.iaeste.iws.client.AdministrationClient;
import net.iaeste.iws.client.StudentClient;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class UserAccountTest extends AbstractAdministration {

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
    public void testAccountNameChange() {
        token.setGroupId(findMemberGroup(token).getGroupId());
        final String username = "alfons@iaeste.se";
        final CreateUserRequest createRequest = new CreateUserRequest(username, "Alfons", "Åberg");
        final CreateUserResponse createResponse = administration.createUser(token, createRequest);
        final AccountNameRequest request = new AccountNameRequest(createResponse.getUser(), "Aaberg");
        final Fallible response = administration.changeAccountName(token, request);
        assertThat(response.isOk(), is(true));
        final FetchUserRequest fetchRequest = new FetchUserRequest(createResponse.getUser().getUserId());
        final FetchUserResponse fetchResponse = administration.fetchUser(token, fetchRequest);
        assertThat(fetchResponse.isOk(), is(true));
        assertThat(fetchResponse.getUser().getLastname(), is("Aaberg"));
    }

    /**
     * Test for the Trac Bug report #452 - to reproduce:
     * <ol>
     * <li>login as austria</li>
     * <li>create a new user</li>
     * <li>activate the user</li>
     * <li>delete the user</li>
     * </ol>
     */
    @Test
    public void testDeletingActiveUser() {
        // 1. Login as Austria
        //      - The test is using Austria per default

        // 2. Create a new User
        final String username = "mozart@iaeste.at";
        final CreateUserRequest createRequest = new CreateUserRequest(username, "Wolfgang", "Amadeus");
        final CreateUserResponse createResponse = administration.createUser(token, createRequest);
        assertThat(createResponse.isOk(), is(true));

        // 3. Acticate the new User
        final NotificationType type = NotificationType.ACTIVATE_USER;
        final NotificationField field = NotificationField.CODE;
        final String activationCode = spy.getNext(type).getFields().get(field);
        final Fallible activateResponse = client.activateUser(activationCode);
        assertThat(activateResponse.isOk(), is(true));

        // 4. Delete the new User
        final UserRequest deleteRequest = new UserRequest();
        deleteRequest.setUser(createResponse.getUser());
        deleteRequest.setNewStatus(UserStatus.DELETED);
        final Fallible deleteResponse = administration.controlUserAccount(token, deleteRequest);
        assertThat(deleteResponse.isOk(), is(true));
    }

    @Test
    public void testDeletingNewUser() {
        final String username = "bach@iaeste.at";
        final CreateUserRequest createRequest = new CreateUserRequest(username, "Johann", "Sebastian");
        final CreateUserResponse createResponse = administration.createUser(token, createRequest);
        assertThat(createResponse.isOk(), is(true));

        final UserRequest deleteRequest = new UserRequest();
        deleteRequest.setUser(createResponse.getUser());
        deleteRequest.setNewStatus(UserStatus.DELETED);
        final Fallible deleteResponse = administration.controlUserAccount(token, deleteRequest);
        assertThat(deleteResponse.isOk(), is(true));

        // Okay, now we're using the activation link to activate the Account
        final NotificationType type = NotificationType.ACTIVATE_USER;
        final NotificationField field = NotificationField.CODE;
        final String activationCode = spy.getNext(type).getFields().get(field);
        final Fallible activateResponse = client.activateUser(activationCode);
        assertThat(activateResponse.isOk(), is(false));
        assertThat(activateResponse.getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        assertThat(activateResponse.getMessage(), is("No account for this user was found."));
    }

    /**
     * Test for the Trac Bug report #442.
     */
    @Test
    public void testCreateUserWithExistingUsername() {
        final String username = "michael.pickelbauer@iaeste.at";
        final CreateUserRequest request1 = new CreateUserRequest(username, "Michael", "Pickelbauer");
        final CreateUserRequest request2 = new CreateUserRequest(username, "Hugo", "Mayer");

        // Now let's create the two user accounts
        final CreateUserResponse response1 = administration.createUser(token, request1);
        final CreateUserResponse response2 = administration.createUser(token, request2);

        // The first request should work like a charm
        assertThat(response1, is(not(nullValue())));
        assertThat(response1.isOk(), is(true));

        // The second request should fail, as we already have a user with this
        // username in the system
        assertThat(response2, is(not(nullValue())));
        assertThat(response2.isOk(), is(false));
        assertThat(response2.getError(), is(IWSErrors.USER_ACCOUNT_EXISTS));
        assertThat(response2.getMessage(), is("An account for the user with username " + username + " already exists."));
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
        final String memberGroupId = findMemberGroup(token).getGroupId();
        token.setGroupId(memberGroupId);
        final FetchGroupRequest groupRequest = new FetchGroupRequest(memberGroupId);
        final FetchGroupResponse groupResponse = administration.fetchGroup(token, groupRequest);
        assertThat(groupResponse, is(not(nullValue())));
    }

    /**
     * Testing updating user actually updates the fields. See Trac #438, #524.
     */
    @Test
    public void testUpdatePerson() {
        final AuthenticationToken myToken = login("finland@iaeste.fi", "finland");
        final Group memberGroup = findMemberGroup(myToken);
        final FetchGroupRequest groupRequest = new FetchGroupRequest(memberGroup.getGroupId());
        groupRequest.setUsersToFetch(FetchGroupRequest.FetchType.ACTIVE);
        final FetchGroupResponse groupResponse1 = administration.fetchGroup(myToken, groupRequest);
        assertThat(groupResponse1.isOk(), is(true));
        assertThat(groupResponse1.getMembers().size(), is(1));

        // Now, let's update the Object, and send it into the IWS
        final User myself = groupResponse1.getMembers().get(0).getUser();
        final Person person = myself.getPerson();
        final Address address = new Address();
        address.setStreet1("Mainstreet 1");
        address.setPostalCode("12345");
        address.setCity("Cooltown");
        address.setState("Coolstate");
        person.setAddress(address);
        person.setFax("My fax");
        person.setBirthday(new Date("01-JAN-1980"));
        person.setMobile("+1 1234567890");
        person.setGender(Gender.UNKNOWN);
        person.setPhone("+1 0987654321");
        myself.setPerson(person);
        final UserRequest updateRequest = new UserRequest();
        updateRequest.setUser(myself);
        final Fallible updateResult = administration.controlUserAccount(myToken, updateRequest);
        assertThat(updateResult.isOk(), is(true));

        // Let's find the account again, and verify that the updates were applied
        final FetchUserRequest userRequest = new FetchUserRequest(myself.getUserId());
        final FetchUserResponse userResponse = administration.fetchUser(myToken, userRequest);
        assertThat(userResponse.isOk(), is(true));
        final User myUpdate = userResponse.getUser();
        final Person updatedPerson = myUpdate.getPerson();
        assertThat(updatedPerson.getAlternateEmail(), is(person.getAlternateEmail()));
        assertThat(updatedPerson.getBirthday(), is(person.getBirthday()));
        assertThat(updatedPerson.getFax(), is(person.getFax()));
        assertThat(updatedPerson.getGender(), is(person.getGender()));
        assertThat(updatedPerson.getMobile(), is(person.getMobile()));
        assertThat(updatedPerson.getPhone(), is(person.getPhone()));

        final Address updatedAddress = updatedPerson.getAddress();
        assertThat(updatedAddress.getStreet1(), is(address.getStreet1()));
        assertThat(updatedAddress.getStreet2(), is(address.getStreet2()));
        assertThat(updatedAddress.getPostalCode(), is(address.getPostalCode()));
        assertThat(updatedAddress.getCity(), is(address.getCity()));
        assertThat(updatedAddress.getState(), is(address.getState()));

        // Wrapup... logout
        logout(myToken);
    }

    /**
     * The test attempts to run through the different steps in the user creation
     * process, and verify that the new Student account has the expected
     * permissions. This is the steps being tested:
     * <ol>
     * <li><b>Success</b>: Create new Student Account</li>
     * <li><b>Failure</b>: Attempt to login before activation</li>
     * <li><b>Success</b>: Activate Student Account</li>
     * <li><b>Success</b>: Login with newly created & activated Student Account</li>
     * <li><b>Success</b>: Read Permissions for Student, verify Apply for Open Offers</li>
     * <li><b>Success</b>: Logout from Student Account</li>
     * </ol>
     */
    @Test
    public void testCreateStudentAccount() {
        // For this test, we also need the Access Client
        final AccessClient accessClient = new AccessClient();

        // Create the new User Request Object
        final String username = "student@gamma.net";
        final String password = "myPassword";
        final CreateUserRequest createUserRequest = new CreateUserRequest(username, password, "Student", "Graduate");
        createUserRequest.setStudentAccount(true);

        final Students students = new StudentClient();
        final FetchStudentsRequest fetchStudentsRequest = new FetchStudentsRequest();
        final FetchStudentsResponse beforeStudentsResponse = students.fetchStudents(token, fetchStudentsRequest);

        // Now, perform the actual test - create the Account, and verify that
        // the response is ok, and that a Notification was sent
        final Fallible result = administration.createUser(token, createUserRequest);
        assertThat(result.isOk(), is(true));
        assertThat(spy.size(), is(0));

        //TODO Pavel 2014-04-15: students are not supposed to receive activation email now so the following test should not work
        //                       since no notification is generated. Once the emails are sent, following lines have to be uncommented
//        assertThat(spy.size(), is(1));
//        final String activationCode = spy.getNext().getFields().get(NotificationField.CODE);
//        assertThat(activationCode, is(not(nullValue())));

        // Attempt to login using the new User Account. It should not yet work,
        // since the account is not activated
//        final AuthenticationRequest request = new AuthenticationRequest(username, password);
//        final AuthenticationResponse response1 = accessClient.generateSession(request);
//        assertThat(response1.isOk(), is(false));
//        assertThat(response1.getError(), is(IWSErrors.AUTHENTICATION_ERROR));

        // Verify that the Students exists
        final FetchStudentsResponse afterFetchStudentsResponse = students.fetchStudents(token, fetchStudentsRequest);
        assertThat(afterFetchStudentsResponse.isOk(), is(true));
        assertThat(afterFetchStudentsResponse.getStudents().size(), is(beforeStudentsResponse.getStudents().size() + 1));

        // Activate the Account
//        final Fallible acticationResult = administration.activateUser(activationCode);
//        assertThat(acticationResult.isOk(), is(true));

        // Now, attempt to login again
//        final AuthenticationResponse response2 = accessClient.generateSession(request);
//        assertThat(response2.isOk(), is(true));
//        assertThat(response2.getToken(), is(not(nullValue())));

        //// Now, read the Permissions that the student has, basically, there is
        //// only 1 permission - which is applying for Open Offers
        //final FetchPermissionResponse permissionResponse = accessClient.fetchPermissions(response2.getToken());
        //assertThat(permissionResponse.isOk(), is(true));
        //// The following fails, since the order of the UserGroup Object in the
        //// Authorization Object is undefined, for this reason, the code is
        //// commented out
        //assertThat(permissionResponse.getAuthorizations().get(0).getUserGroup().getRole().getPermissions().contains(Permission.APPLY_FOR_OPEN_OFFER), is(true));

        // Deprecate the Students Session, the test is over :-)
//        final Fallible deprecateSessionResult = accessClient.deprecateSession(response2.getToken());
//        assertThat(deprecateSessionResult.isOk(), is(true));
    }

    /**
     * This test is a verification of the bugfix for Trac bug #704, concerning
     * deletion of Student Accounts. Although not explicitly listed, Accounts
     * are only completely deleted, if the User in question is in status new.
     */
    @Test
    public void testCreateAndDeleteStudentAccount() {
        // Create the new User Request Object
        final String username = "student@flipflop.net";
        final String password = "myPassword";
        final CreateUserRequest createUserRequest = new CreateUserRequest(username, password, "Student", "Graduate");
        createUserRequest.setStudentAccount(true);

        final Students students = new StudentClient();
        final FetchStudentsRequest fetchStudentsRequest = new FetchStudentsRequest();
        final FetchStudentsResponse beforeStudentsResponse = students.fetchStudents(token, fetchStudentsRequest);

        // Now, perform the actual test - create the Account, and verify that
        // the response is ok, and that a Notification was sent
        final CreateUserResponse created = administration.createUser(token, createUserRequest);
        assertThat(created.isOk(), is(true));

        // Verify that the Students exists
        final FetchStudentsResponse afterFetchStudentsResponse = students.fetchStudents(token, fetchStudentsRequest);
        assertThat(afterFetchStudentsResponse.isOk(), is(true));
        assertThat(afterFetchStudentsResponse.getStudents().size(), is(beforeStudentsResponse.getStudents().size() + 1));

        // Delete the Student Account
        final UserRequest deleteRequest = new UserRequest();
        deleteRequest.setUser(created.getUser());
        deleteRequest.setNewStatus(UserStatus.DELETED);
        final Fallible deleteResult = administration.controlUserAccount(token, deleteRequest);
        assertThat(deleteResult.isOk(), is(true));

        // Verify that the Student is deleted
        final FetchStudentsResponse deleteFetchStudentsResponse = students.fetchStudents(token, fetchStudentsRequest);
        assertThat(deleteFetchStudentsResponse.isOk(), is(true));
        assertThat(deleteFetchStudentsResponse.getStudents().size(), is(beforeStudentsResponse.getStudents().size()));
    }

    @Test
    public void testCreateAndListStudentAccounts() {
        final Students students = new StudentClient();

        // create some student users
        final CreateUserRequest createUserRequest1 = new CreateUserRequest("s001@university.edu", "password1", "Student1", "Graduate1");
        createUserRequest1.setStudentAccount(true);

        final CreateUserRequest createUserRequest2 = new CreateUserRequest("s002@university.edu", "password2", "Student2", "Graduate2");
        createUserRequest2.setStudentAccount(true);

        final CreateUserRequest createUserRequest3 = new CreateUserRequest("s003@university.edu", "password3", "Student3", "Graduate3");
        createUserRequest3.setStudentAccount(true);

        final CreateUserResponse createUserResponse1 = administration.createUser(token, createUserRequest1);
        assertThat(createUserResponse1.isOk(), is(true));

        final CreateUserResponse createUserResponse2 = administration.createUser(token, createUserRequest2);
        assertThat(createUserResponse2.isOk(), is(true));

        final CreateUserResponse createUserResponse3 = administration.createUser(token, createUserRequest3);
        assertThat(createUserResponse3.isOk(), is(true));

        // fetch the students group
        final FetchStudentsRequest studentsRequest = new FetchStudentsRequest();
        final FetchStudentsResponse studentsResponse = students.fetchStudents(token, studentsRequest);
        assertThat(studentsResponse, is(not(nullValue())));
        assertThat(studentsResponse.isOk(), is(true));

        assertThat(studentsResponse.getStudents().size(), is(greaterThanOrEqualTo(3)));

        // Extract the users from the group
        final List<Student> foundStudents = studentsResponse.getStudents();
        final List<User> users = new ArrayList<>(foundStudents.size());
        for (final Student student : foundStudents) {
            users.add(student.getUser());
        }

        assertThat(users.contains(createUserResponse1.getUser()), is(true));
        assertThat(users.contains(createUserResponse2.getUser()), is(true));
        assertThat(users.contains(createUserResponse3.getUser()), is(true));
    }

    /**
     * Testing that the generated Alias is correctly set to the name plus an
     * increasing number, of multiple people with the same name are
     * created.<br />
     * Note, the test is using pure lowercase variants, since the IWS will
     * internally convert all usernames and aliases to lowercase.
     */
    @Test
    public void createDuplicateAccount() {
        final String username1 = "william@shakespeare.com";
        final String username2 = "william@shakespeare.net";
        final String username3 = "william@shakespeare.org";
        final String firstname = "william";
        final String lastname = "shakespeare";
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
        final FetchRoleRequest request = new FetchRoleRequest(findNationalGroup(token).getGroupId());
        final FetchRoleResponse response = administration.fetchRoles(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        // There should be a total of 5 roles for this Group
        assertThat(response.getRoles().size(), is(5));
    }
}
