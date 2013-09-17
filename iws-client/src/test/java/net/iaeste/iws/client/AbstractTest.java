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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.common.exceptions.AuthenticationException;
import org.junit.After;
import org.junit.Before;

/**
 * Common functionality for all our tests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public abstract class AbstractTest {

    private static final Access access = new AccessClient();
    private static final Administration administration = new AdministrationClient();
    protected final NotificationSpy spy = NotificationSpy.getInstance();
    protected AuthenticationToken token = null;

    /**
     * The Before method must be defined, to setup the test, mostly it'll just
     * perform a login and save the Token, and perhaps also clear the content of
     * the Spy.
     */
    @Before
    public abstract void setup();

    /**
     * The After method must be defined, to ensure that we have properly cleaned
     * up after us.
     */
    @After
    public abstract void tearDown();

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
    protected static AuthenticationToken login(final String username, final String password) {
        final AuthenticationRequest request = new AuthenticationRequest(username, password);
        final AuthenticationResponse response = access.generateSession(request);

        if (!response.isOk()) {
            throw new AuthenticationException(response.getMessage());
        }

        return response.getToken();
    }

    /**
     * Deprecates an active Session. To avoid that an error in one test will
     * cause issues in others.
     *
     * @param token Authentication Token to deprecate
     */
    protected static void logout(final AuthenticationToken token) {
        access.deprecateSession(token).isOk();
    }

    /**
     * Finds the Member Group for the current Token owner.
     *
     * @param token Authentication Token to find the Member Group for
     * @return Member Group
     */
    protected static Group findMemberGroup(final AuthenticationToken token) {
        return findGroup(token, GroupType.MEMBER);
    }

    /**
     * Finds the National Group for the current Token owner.
     *
     * @param token Authentication Token to find the National Group for
     * @return Member Group
     */
    protected static Group findNationalGroup(final AuthenticationToken token) {
        // There's a rumour going around that SAR's are a thing of the past. So
        // we only need to fetch the Group with type NATIONAL
        return findGroup(token, GroupType.NATIONAL);
    }

    /**
     * Finds the Group of the given Type for the current Token owner. Note, that
     * if there exists more than one Group of the given type, then the request
     * will fail.
     *
     * @param token Authentication Token to find the Group for
     * @param type  Type of Group to find
     * @return Group of the given Type
     */
    private static Group findGroup(final AuthenticationToken token, final GroupType type) {
        final FetchGroupRequest request = new FetchGroupRequest(type);
        final FetchGroupResponse response = administration.fetchGroup(token, request);
        assertThat(response.isOk(), is(true));

        return response.getGroup();
    }
}
