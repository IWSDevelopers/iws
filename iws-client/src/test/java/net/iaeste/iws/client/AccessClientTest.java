/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.PermissionResponse;
import org.junit.Test;

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
        assertThat(response.getMessage(), is("User Credentials may not be null."));
    }

    @Test
    public void testGeneratingSession() {
        final String username = "Frodo";
        final String password = "frodo";
        final AuthenticationRequest request = new AuthenticationRequest(username, password);

        final AuthenticationResponse response = access.generateSession(request);

        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(response.isOk(), is(true));
        assertThat(response.getError(), is(IWSErrors.SUCCESS));
        assertThat(response.getToken(), is(not(nullValue())));
        assertThat(response.getToken().getToken().length(), is(64));
    }

    @Test
    public void testCrapValues() {
        final AuthenticationToken token = new AuthenticationToken("9e107d9d372bb6826bd81d3542a419d6");

        final PermissionResponse response = access.fetchPermissions(token);
        final List<Authorization> permissions = response.getAuthorizations();

        // Verify that the call went through :-)
        assertThat(response.isOk(), is(true));
        assertThat(response.getError(), is(IWSErrors.SUCCESS));
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));

        // Request went through, now we can check the response
        assertThat(permissions.size(), is(2));
        assertThat(permissions.get(0).getGroupType(), is("Half Size"));
        assertThat(permissions.get(0).getPermission(), is("Daggers"));
        assertThat(permissions.get(1).getGroupType(), is("Fellowship"));
        assertThat(permissions.get(1).getPermission(), is("Daggers"));
    }
}
