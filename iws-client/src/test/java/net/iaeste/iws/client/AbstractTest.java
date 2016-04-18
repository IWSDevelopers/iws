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
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import org.junit.After;
import org.junit.Before;

/**
 * Common functionality for all our tests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public abstract class AbstractTest {

    protected static final Access access = new AccessClient();
    protected static final Administration administration = new AdministrationClient();
    protected final NotificationSpy spy = NotificationSpy.getInstance();
    protected AuthenticationToken token = null;

    /**
     * The Before method must be defined, to setup the test, mostly it'll just
     * perform a login and save the Token, and perhaps also clear the content of
     * the Spy.
     */
    @Before
    public abstract void setUp();

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
        request.setUsersToFetch(FetchGroupRequest.UserFetchType.ACTIVE);
        final FetchGroupResponse response = administration.fetchGroup(token, request);
        assertThat(response.isOk(), is(true));

        return response.getGroup();
    }

    /**
     * Reads the next Code from the Notifications.
     *
     * @param type Notification Type
     * @return Code from the Message
     */
    protected String readCode(final NotificationType type) {
        return spy.getNext(type).getFields().get(NotificationField.CODE);
    }
}
