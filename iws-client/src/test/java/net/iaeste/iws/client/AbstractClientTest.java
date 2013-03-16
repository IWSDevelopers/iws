/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.AbstractClientTest
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
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.common.exceptions.AuthenticationException;
import org.junit.After;
import org.junit.Before;

/**
 * Common functionality for our tests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public abstract class AbstractClientTest {

    private static final Access access = new AccessClient();
    protected final NotificationSpy spy = NotificationSpy.getInstance();
    protected AuthenticationToken token = null;

    /**
     * The Before method must be defined, to setup the test, mostly it'll just
     * perform a login and save the Token, and perhaps also clear the content of
     * the Spy.
     */
    @Before
    public abstract void before();

    /**
     * The After method must be defined, to ensure that we have properly cleaned
     * up after us.
     */
    @After
    public abstract void after();

    /**
     * Attempts to log into the IWS, using the given username and password. Upon
     * successful login, the Token is returned.<br />
     *   If unable to login, an {@code AuthenticationException} is thrown.
     *
     * @param username Username to login with
     * @param password Password for the user
     * @return Authentication Token
     * @throws AuthenticationException if unable to log in
     */
    protected AuthenticationToken login(final String username, final String password) {
        final AuthenticationRequest request = new AuthenticationRequest(username, password);
        final AuthenticationResponse response = access.generateSession(request);

        if (!response.isOk()) {
            throw new AuthenticationException(response.getMessage());
        }

        return response.getToken();
    }

    protected void logout(final AuthenticationToken token) {
        access.deprecateSession(token).isOk();
    }
}
