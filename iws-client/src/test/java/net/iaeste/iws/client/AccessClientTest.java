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
import net.iaeste.iws.common.notification.NotificationType;
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
        assertThat(inactiveResponse.getError(), is(IWSErrors.SESSION_EXPIRED));
        assertThat(inactiveResponse.getMessage(), is("The token has expired."));
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
        final FallibleResponse fetchPermissionResponse2 = access.verifySession(newResponse.getToken());
        final FallibleResponse fetchPermissionResponse3 = access.verifySession(response.getToken());
        assertThat(fetchPermissionResponse2.isOk(), is(true));
        assertThat(fetchPermissionResponse3.isOk(), is(false));
        assertThat(fetchPermissionResponse3.getError(), is(IWSErrors.SESSION_EXPIRED));
        assertThat(fetchPermissionResponse3.getMessage(), is("The token has expired."));

        // And clean-up, so no sessions are lurking around
        assertThat(access.deprecateSession(newResponse.getToken()).isOk(), is(true));
    }

    @Test
    public void testCallWithInvalidToken() {
        final AuthenticationToken invalidToken = new AuthenticationToken("5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fcc309bbe63889dba363e");

        final FallibleResponse response = access.verifySession(invalidToken);
        //final List<Authorization> permissions = response.getAuthorizations();

        // Verify that the call went through - however, as we just invented a
        // "token", we should get an error back
        assertThat(response.isOk(), is(false));
        // Now, we have the problem internally - that the Session was never
        // created, yet if we have to add DB checks against an unused or not
        // existing token, it would be extra overhead. so although the error
        // is not the correct one, we'll live with it as it would otherwise
        // require a rewrite of the internal handling, which in the end would
        // still only return an error.
        assertThat(response.getError(), is(IWSErrors.SESSION_EXPIRED));
        assertThat(response.getMessage(), is("The token has expired."));
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
    public void testResetPasswordInvalidRequest() {
        final Fallible invalidAccountResponse = access.forgotPassword("Some Crap");
        assertThat(invalidAccountResponse.isOk(), is(false));
        assertThat(invalidAccountResponse.getError(), is(IWSErrors.VERIFICATION_ERROR));
        assertThat(invalidAccountResponse.getMessage(), is("Invalid e-mail address provided."));
    }

    @Test
    public void testResetPasswordUnknownUsername() {
        final Fallible validEmailResponse = access.forgotPassword("user@domain.com");
        assertThat(validEmailResponse.isOk(), is(false));
        assertThat(validEmailResponse.getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        assertThat(validEmailResponse.getMessage(), is("No account for this user was found."));
    }
    @Test
    public void testResetPasswordCountrySuspended() {
        // Bu default, the Albian Test Group is Suspended
        final Fallible suspendedGroupResponse = access.forgotPassword("albania@iaeste.al");
        assertThat(suspendedGroupResponse.isOk(), is(false));
        assertThat(suspendedGroupResponse.getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        assertThat(suspendedGroupResponse.getMessage(), is("No account for this user was found."));
    }

    @Test
    public void testResetPasswordUserSuspended() {
        // By default, the Argentinian User is Suspended
        final Fallible suspendedUserResponse = access.forgotPassword("argentina@iaeste.ar");
        assertThat(suspendedUserResponse.isOk(), is(false));
        assertThat(suspendedUserResponse.getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        assertThat(suspendedUserResponse.getMessage(), is("No account for this user was found."));
    }

    @Test
    public void testResetPasswordValidRequest() {
        final Fallible forgotResponse = access.forgotPassword("uzbekistan@iaeste.uz");
        assertThat(forgotResponse.isOk(), is(true));

        // Verify Token sent with the Notification
        final NotificationType type = NotificationType.RESET_PASSWORD;
        assertThat(spy.size(type), is(1));
        final String resetCode = spy.getNext(type).getFields().get(NotificationField.CODE);
        assertThat(resetCode, is(not(nullValue())));
        assertThat(resetCode.length(), is(64));

        // Reset the Password for the User
        final String newPassword = "Uzbekistan123";
        final Password password = new Password(newPassword, resetCode);
        final Fallible resetResponse = access.resetPassword(password);
        assertThat(resetResponse.isOk(), is(true));

        // Login with the new Password
        final AuthenticationResponse loginResponse = access.generateSession(new AuthenticationRequest("uzbekistan@iaeste.uz", newPassword));
        assertThat(loginResponse.isOk(), is(true));

        // Wrap up the test with logging out :-)
        final Fallible logoutResponse = access.deprecateSession(loginResponse.getToken());
        assertThat(logoutResponse.isOk(), is(true));
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
        final Password password = new Password();
        password.setNewPassword(newPassword);
        final Fallible update1 = access.updatePassword(userToken, password);
        assertThat(update1.isOk(), is(false));
        assertThat(update1.getError(), is(IWSErrors.VERIFICATION_ERROR));

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
