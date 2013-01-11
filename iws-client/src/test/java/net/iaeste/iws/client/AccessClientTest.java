/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Authorization;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.api.responses.PermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class AccessClientTest {

    private final Access access = new AccessClient();

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
        final String username = "austria";
        final String password = "austria";
        final AuthenticationRequest request = new AuthenticationRequest(username, password);

        final AuthenticationResponse response = client.generateSession(request);

        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(response.isOk(), is(true));
        assertThat(response.getError(), is(IWSErrors.SUCCESS));
        assertThat(response.getToken(), is(not(nullValue())));
        assertThat(response.getToken().getToken().length(), is(64));

        final PermissionResponse permissionResponse = access.fetchPermissions(response.getToken());

        // Now, let's try to see if we can deprecate the Session, and thus
        // ensure that the first Object is properly persisted
        final Fallible result = client.deprecateSession(response.getToken());
        assertThat(result.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(result.isOk(), is(true));
        assertThat(result.getError(), is(IWSErrors.SUCCESS));
    }

    @Test
    public void testCallWithInvalidToken() {
        final AuthenticationToken token = new AuthenticationToken("9e107d9d372bb6826bd81d3542a419d6");

        final PermissionResponse response = access.fetchPermissions(token);
        final List<Authorization> permissions = response.getAuthorizations();

        // Verify that the call went through - however, as we just invented a
        // "token", we should get an error back
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.AUTHENTICATION_ERROR));
        assertThat(response.getMessage(), is("No AuthenticationToken was found."));
    }

    @Test
    public void testSavingReadingSessionData() {
        // Create a new Token, that we can use for the test
        final AuthenticationToken token = access.generateSession(new AuthenticationRequest("austria", "austria")).getToken();

        // Perform the actual test, first we create a simple Object, and saves it
        final Date data = new Date();
        final SessionDataRequest<Date> sessionData = new SessionDataRequest<>(data);
        final Fallible saving = access.saveSessionData(token, sessionData);
        assertThat(saving.isOk(), is(true));

        // Object saved, now - let's read it from the IWS
        final SessionDataResponse<Date> response = access.fetchSessionData(token);
        assertThat(response.isOk(), is(true));
        assertThat(response.getSessionData(), is(data));

        // Finalize the test, by deprecating the token
        assertThat(access.deprecateSession(token).isOk(), is(true));
    }

    @Test
    public void testFetchPermissions() {
        final String userId = "13452874-0c0f-4caf-8101-a8e9b41d6e69";
        // Create a new Token, that we can use for the test
        final AuthenticationToken token = access.generateSession(new AuthenticationRequest("austria", "austria")).getToken();

        final PermissionResponse responseAll = access.fetchPermissions(token);
        assertThat(responseAll.isOk(), is(true));
        // Should add more assertions, however - there's still changes coming to
        // the Permission layer - so for now, we'll leave it Otherwise we will
        // constantly have to verify this.
        token.setGroupId("c7b15f81-4f83-48e8-9ffb-9e73255f5e5e");
        final PermissionResponse responseNational = access.fetchPermissions(token);
        assertThat(responseNational.isOk(), is(true));
        assertThat(responseNational.getUserId(), is(userId));

        // When we make a request for a specific Group, we only expect to find a single element
        assertThat(responseNational.getAuthorizations().size(), is(1));
        assertThat(responseNational.getAuthorizations().get(0).getPermission().contains(Permission.MANAGE_OFFERS), is(true));
        token.setGroupId("invalid");

        // Finally, let's see what happens when we try to find the information
        // from a Group, that we are not a member of
        final PermissionResponse responseInvalid = access.fetchPermissions(token);
        assertThat(responseInvalid.isOk(), is(false));
        assertThat(responseInvalid.getError(), is(IWSErrors.AUTHORIZATION_ERROR));

        // Finalize the test, by deprecating the token
        assertThat(access.deprecateSession(token).isOk(), is(true));
    }
}
