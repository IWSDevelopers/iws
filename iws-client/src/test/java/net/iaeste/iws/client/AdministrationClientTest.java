/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.AdministrationClientTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.util.Fallible;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class AdministrationClientTest extends AbstractClientTest {

    private static final Pattern STRING_PATTERN = Pattern.compile("=");
    private final Administration administration = new AdministrationClient();

    @Override
    public void before() {
        token = login("austria", "austria");
        // Clear all messages from the Notification Queue
        spy.clear();
    }

    @Override
    public void after() {
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
        final Fallible result = administration.createUser(token, createUserRequest);
        assertThat(result.isOk(), is(true));
        assertThat(spy.size(), is(1));
        final String notification = spy.getNext().getMessage();
        assertThat(notification, containsString("Activation Code"));
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
        assertThat(spy.size(), is(1));
        final String notification = spy.getNext().getMessage();
        assertThat(notification, containsString("Activation Code"));
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
        final String notification = spy.getNext().getMessage();
        assertThat(notification, containsString("Activation Code"));

        // Attempt to login using the new User Account. It should not yet work,
        // since the account is not activated
        final AuthenticationRequest request = new AuthenticationRequest(username, password);
        final AuthenticationResponse response1 = accessClient.generateSession(request);
        assertThat(response1.isOk(), is(false));
        assertThat(response1.getError(), is(IWSErrors.AUTHORIZATION_ERROR));

        // Activate the Account
        final String activationCode = readActivationCode(notification);
        final Fallible acticationResult = administration.activateUser(activationCode);
        assertThat(acticationResult.isOk(), is(true));

        // Now, attempt to login again
        final AuthenticationResponse response2 = accessClient.generateSession(request);
        assertThat(response2.isOk(), is(true));
        assertThat(response2.getToken(), is(not(nullValue())));

//        // Now, read the Permissions that the student has, basically, there is
//        // only 1 permission - which is applying for Open Offers
//        final FetchPermissionResponse permissionResponse = accessClient.fetchPermissions(response2.getToken());
//        assertThat(permissionResponse.isOk(), is(true));

        // Deprecate the Students Session, the test is over :-)
        final Fallible deprecateSessionResult = accessClient.deprecateSession(response2.getToken());
        assertThat(deprecateSessionResult.isOk(), is(true));
    }

    private static String readActivationCode(final String notificationMessage) {
        final String[] array = STRING_PATTERN.split(notificationMessage);

        return array[2].trim();
    }
}
