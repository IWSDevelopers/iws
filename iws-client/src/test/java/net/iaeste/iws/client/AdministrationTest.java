/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.AdministrationTest
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
import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.client.spring.NotificationSpy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class AdministrationTest {

    private final Access access = new AccessClient();
    private AuthenticationToken token = null;
    private final NotificationSpy spy = NotificationSpy.getInstance();
    private final Administration administration = new AdministrationClient();

    @Before
    public void before() {
        if (token == null) {
            final String username = "austria";
            final String password = "austria";

            final AuthenticationRequest request = new AuthenticationRequest(username, password);
            final AuthenticationResponse response = access.generateSession(request);

            token = response.getToken();
        }

        // Clear all messages from the Notification Queue
        spy.clear();
    }

    @After
    public void after() {
        if (token != null) {
            access.deprecateSession(token);
            token = null;
        }
    }

    @Test
    public void testCreateAccountWithPassword() {
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("alpha@gamma.net");
        createUserRequest.setPassword("beta");
        createUserRequest.setFirstname("Alpha");
        createUserRequest.setLastname("Gamma");

        final Fallible result = administration.createUser(token, createUserRequest);
        assertThat(result.isOk(), is(true));
        assertThat(spy.size(), is(1));
        final String notification = spy.getNext().generateNotificationMessage();
        assertThat(notification, containsString("Activation Code"));
    }

    @Test
    public void testCreateAccountWithoutPassword() {
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("beta@gamma.net");
        createUserRequest.setFirstname("Beta");
        createUserRequest.setLastname("Gamma");

        final Fallible result = administration.createUser(token, createUserRequest);
        assertThat(result.isOk(), is(true));
        assertThat(spy.size(), is(1));
        final String notification = spy.getNext().generateNotificationMessage();
        assertThat(notification, containsString("Activation Code"));
    }
}
