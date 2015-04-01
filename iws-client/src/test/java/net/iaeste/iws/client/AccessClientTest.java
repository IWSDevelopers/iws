/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.AccessClientTest
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.common.configuration.InternalConstants;
import net.iaeste.iws.common.notification.NotificationField;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AccessClientTest extends AbstractTest {

    @Override
    public void setup() {
    }

    @Override
    public void tearDown() {
    }

    /**
     * This test verifies that an Account which is in status Suspended, cannot
     * access the system.
     */
    @Test
    public void testSuspendedAccount() {
        final String username = "albania@iaeste.al";
        final AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword("albania");
        final AuthenticationResponse response = access.generateSession(request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        assertThat(response.getMessage(), is("No account for the user 'albania@iaeste.al' was found."));
    }

    /**
     * This test verifies that members of a country which is in status
     * Suspended, cannot access the system.
     */
    @Test
    public void testSuspendedCountry() {
        final String username = "argentina@iaeste.ar";
        final AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword("argentina");
        final AuthenticationResponse response = access.generateSession(request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        assertThat(response.getMessage(), is("No account for the user '" + username + "' was found."));
    }

    @Test
    public void testInvalidGenerateSession() {
        final AuthenticationRequest request = new AuthenticationRequest();
        final AuthenticationResponse response = access.generateSession(request);

        assertThat(response.isOk(), is(false));
        assertThat(response.getToken(), is(nullValue()));
        assertThat(response.getError(), is(IWSErrors.VERIFICATION_ERROR));
        assertThat(response.getMessage(), is("Validation failed: {User Credentials=Missing or invalid value.}"));
    }

    @Test
    public void testGenerateAndDeprecateSession() {
        final Access client = new AccessClient();
        final String username = "austria@iaeste.at";
        final String password = "austria";
        final AuthenticationRequest request = new AuthenticationRequest(username, password);

        final AuthenticationResponse response = client.generateSession(request);

        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(response.isOk(), is(true));
        assertThat(response.getError(), is(IWSErrors.SUCCESS));
        assertThat(response.getToken(), is(not(nullValue())));
        assertThat(response.getToken().getToken().length(), is(64));

        final FetchPermissionResponse fetchPermissionResponse = access.fetchPermissions(response.getToken());
        assertThat(fetchPermissionResponse.isOk(), is(true));

        // Now, let's try to see if we can deprecate the Session, and thus
        // ensure that the first Object is properly persisted
        final Fallible result = client.deprecateSession(response.getToken());
        assertThat(result.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(result.isOk(), is(true));
        assertThat(result.getError(), is(IWSErrors.SUCCESS));
    }

    @Test
    public void testVerifySession() {
        final Access client = new AccessClient();
        final String username = "austria@iaeste.at";
        final String password = "austria";
        final AuthenticationRequest request = new AuthenticationRequest(username, password);

        // Login
        final AuthenticationResponse response = client.generateSession(request);
        final AuthenticationToken myToken = response.getToken();

        // Verify that our current token is valid
        final FallibleResponse aliveResponse = client.verifySession(myToken);
        assertThat(aliveResponse.isOk(), is(true));

        // Logout
        final FallibleResponse deprecateResponse = client.deprecateSession(myToken);
        assertThat(deprecateResponse.isOk(), is(true));

        // Verify that our current token is invalid
        final FallibleResponse inactiveResponse = client.verifySession(myToken);
        assertThat(inactiveResponse.isOk(), is(false));
        assertThat(inactiveResponse.getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        assertThat(inactiveResponse.getMessage(), is("No AuthenticationToken was found."));
    }

    @Test
    public void testExceedingLoginAttempts() {
        final AuthenticationRequest request = new AuthenticationRequest("sweden@iaeste.se", "wrongPassword");
        for (int i = 0; i < InternalConstants.MAX_LOGIN_RETRIES; i++) {
            assertThat(access.generateSession(request).getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        }

        final AuthenticationResponse response = access.generateSession(request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.EXCEEDED_LOGIN_ATTEMPTS));
        assertThat(response.getMessage().contains("User have attempted to login too many times unsuccessfully. The account is being Blocked"), is(true));
    }

    @Test
    public void testResettingSession() {
        final Access client = new AccessClient();
        // We need to reset the spy to avoid other notifications disturbing us
        spy.clear();
        final String username = "austria@iaeste.at";
        final String password = "austria";
        final AuthenticationRequest request = new AuthenticationRequest(username, password);

        // Generate a Session, and verify that it works
        final AuthenticationResponse response = client.generateSession(request);
        final FetchPermissionResponse fetchPermissionResponse = access.fetchPermissions(response.getToken());
        assertThat(fetchPermissionResponse.isOk(), is(true));

        // Now we've forgotten our session, so request a reset
        access.requestResettingSession(request);
        final String resetCode = spy.getNext().getFields().get(NotificationField.CODE);
        assertThat(resetCode, is(not(nullValue())));
        final AuthenticationResponse newResponse = access.resetSession(resetCode);

        // Now verify that control was handed over to the new Session
        final FetchPermissionResponse fetchPermissionResponse2 = access.fetchPermissions(newResponse.getToken());
        final FetchPermissionResponse fetchPermissionResponse3 = access.fetchPermissions(response.getToken());
        assertThat(fetchPermissionResponse2.isOk(), is(true));
        assertThat(fetchPermissionResponse3.isOk(), is(false));
        assertThat(fetchPermissionResponse3.getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        assertThat(fetchPermissionResponse3.getMessage(), is("No AuthenticationToken was found."));

        // And clean-up, so no sessions are lurking around
        assertThat(access.deprecateSession(newResponse.getToken()).isOk(), is(true));
    }

    @Test
    public void testCallWithInvalidToken() {
        final AuthenticationToken invalidToken = new AuthenticationToken("9e107d9d372bb6826bd81d3542a419d6");

        final FetchPermissionResponse response = access.fetchPermissions(invalidToken);
        //final List<Authorization> permissions = response.getAuthorizations();

        // Verify that the call went through - however, as we just invented a
        // "token", we should get an error back
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        assertThat(response.getMessage(), is("No AuthenticationToken was found."));
    }

    @Test
    public void testSavingReadingSessionData() {
        // Create a new Token, that we can use for the test
        final AuthenticationToken newToken = access.generateSession(new AuthenticationRequest("austria@iaeste.at", "austria")).getToken();

        // Perform the actual test, first we create a simple Object, and saves it
        final Date data = new Date();
        final SessionDataRequest<Date> sessionData = new SessionDataRequest<>(data);
        final Fallible saving = access.saveSessionData(newToken, sessionData);
        assertThat(saving.isOk(), is(true));

        // Object saved, now - let's read it from the IWS
        final SessionDataResponse<Date> response = access.readSessionData(newToken);
        assertThat(response.isOk(), is(true));
        assertThat(response.getSessionData(), is(data));

        // Finalize the test, by deprecating the token
        assertThat(access.deprecateSession(newToken).isOk(), is(true));
    }

    @Test
    public void testUpdatePassword() {
        // Create a new Token, that we can use for the test
        final AuthenticationToken adminToken = access.generateSession(new AuthenticationRequest("austria@iaeste.at", "austria")).getToken();

        // First, create a new Account, so we don't mess up any other tests
        final String username = "updating@iaeste.at";
        final String oldPassword = "oldPassword";
        final CreateUserRequest createUserRequest = new CreateUserRequest(username, oldPassword, "testFirstName", "testLastName");
        final Fallible createUserResponse = administration.createUser(adminToken, createUserRequest);
        assertThat(createUserResponse.isOk(), is(true));
        // Now, we don't need the old token anymore
        access.deprecateSession(adminToken);

        // Activate the Account
        final String activationCode = spy.getNext().getFields().get(NotificationField.CODE);
        final Fallible acticationResult = administration.activateUser(activationCode);
        assertThat(acticationResult.isOk(), is(true));

        // Now we can start the actual testing. First, we're trying up update the
        // users password without providing the old password. This should fail
        final AuthenticationToken userToken = access.generateSession(new AuthenticationRequest(username, oldPassword)).getToken();
        final String newPassword = "newPassword";
        final Fallible update1 = access.updatePassword(userToken, new Password(newPassword));
        assertThat(update1.isOk(), is(false));
        assertThat(update1.getError(), is(IWSErrors.CANNOT_UPDATE_PASSWORD));

        // Now, we're trying to update the password by providing a false old password
        final Fallible update2 = access.updatePassword(userToken, new Password(newPassword, "bla"));
        assertThat(update2.isOk(), is(false));
        assertThat(update2.getError(), is(IWSErrors.CANNOT_UPDATE_PASSWORD));

        // Finally, let's update the password using the correct old password
        final Fallible update3 = access.updatePassword(userToken, new Password(newPassword, oldPassword));
        assertThat(update3.isOk(), is(true));

        // Let's check that it also works... Logout, and log in again :-)
        access.deprecateSession(userToken);
        final AuthenticationResponse response = access.generateSession(new AuthenticationRequest(username, newPassword));
        assertThat(response.isOk(), is(true));
        assertThat(response.getError(), is(IWSErrors.SUCCESS));
    }

    @Test
    public void testFetchPermissions() {
        final String userId = "c50aaeec-c0de-425a-b487-4530cbfbe115";
        // Create a new Token, that we can use for the test
        final AuthenticationToken authToken = access.generateSession(new AuthenticationRequest("austria@iaeste.at", "austria")).getToken();

        final FetchPermissionResponse responseAll = access.fetchPermissions(authToken);
        assertThat(responseAll.isOk(), is(true));
        // Should add more assertions, however - there's still changes coming to
        // the Permission layer - so for now, we'll leave it Otherwise we will
        // constantly have to verify this.
        authToken.setGroupId(findNationalGroup(authToken).getGroupId());
        final FetchPermissionResponse responseNational = access.fetchPermissions(authToken);
        assertThat(responseNational.isOk(), is(true));

        // When we make a request for a specific Group, we only expect to find a single element
        assertThat(responseNational.getAuthorizations().size(), is(1));
        assertThat(responseNational.getAuthorizations().get(0).getUserGroup().getRole().getPermissions().contains(Permission.PROCESS_OFFER), is(true));
        authToken.setGroupId(userId);

        // Finally, let's see what happens when we try to find the information
        // from a Group, that we are not a member of
        final FetchPermissionResponse responseInvalid = access.fetchPermissions(authToken);
        assertThat(responseInvalid.isOk(), is(false));
        assertThat(responseInvalid.getError(), is(IWSErrors.AUTHORIZATION_ERROR));

        // Finalize the test, by deprecating the token
        assertThat(access.deprecateSession(authToken).isOk(), is(true));
    }
}
