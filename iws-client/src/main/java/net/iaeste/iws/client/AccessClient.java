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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.Response;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.responses.VersionResponse;

import java.io.Serializable;

/**
 * Simple Client to work with the IWS Access functionality. The access to
 * be used is controlled via the Factory, which loads the settings from a
 * properties file.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AccessClient implements Access {

    private final Access client;

    /**
     * Default Constructor.
     */
    public AccessClient() {
        client = ClientFactory.getInstance().getAccessImplementation();
    }

    // =========================================================================
    // Implementation of methods from Access in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionResponse version() {
        return client.version();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        return client.generateSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response requestResettingSession(final AuthenticationRequest request) {
        return client.requestResettingSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse resetSession(final String resetSessionToken) {
        return client.resetSession(resetSessionToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> Response saveSessionData(final AuthenticationToken token, final SessionDataRequest<T> request) {
        return client.saveSessionData(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> SessionDataResponse<T> readSessionData(final AuthenticationToken token) {
        return client.readSessionData(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response verifySession(final AuthenticationToken token) {
        return client.verifySession(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response deprecateSession(final AuthenticationToken token) {
        return client.deprecateSession(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response forgotPassword(final String username) {
        return client.forgotPassword(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response resetPassword(final Password password) {
        return client.resetPassword(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response updatePassword(final AuthenticationToken token, final Password password) {
        return client.updatePassword(token, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPermissionResponse fetchPermissions(final AuthenticationToken token) {
        return client.fetchPermissions(token);
    }
}
