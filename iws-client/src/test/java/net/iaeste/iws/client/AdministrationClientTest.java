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

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.util.Fallible;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class AdministrationClientTest extends AbstractClientTest {

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
        final String notification = spy.getNext().generateNotificationMessage();
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
        final String notification = spy.getNext().generateNotificationMessage();
        assertThat(notification, containsString("Activation Code"));
    }
}
