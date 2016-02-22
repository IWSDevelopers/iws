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
package net.iaeste.iws.ws.client.mappers;

import net.iaeste.iws.api.dtos.Authorization;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class AccessMapper extends CommonMapper {

    /** Private Constructor, this is a Utility Class. */
    private AccessMapper() {
    }

    public static net.iaeste.iws.ws.AuthenticationRequest map(final AuthenticationRequest api) {
        net.iaeste.iws.ws.AuthenticationRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.AuthenticationRequest();

            ws.setUsername(api.getUsername());
            ws.setPassword(api.getPassword());
        }

        return ws;
    }

    public static AuthenticationResponse map(final net.iaeste.iws.ws.AuthenticationResponse ws) {
        AuthenticationResponse api = null;

        if (ws != null) {
            api = new AuthenticationResponse(map(ws.getError()), ws.getMessage());
            api.setToken(map(ws.getToken()));
        }

        return api;
    }

    public static <T extends Serializable> net.iaeste.iws.ws.SessionDataRequest map(final SessionDataRequest<T> api) {
        net.iaeste.iws.ws.SessionDataRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.SessionDataRequest();

            ws.setSessionData(api.getSessionData());
        }

        return ws;
    }

    public static <T extends Serializable> SessionDataResponse<T> map(final net.iaeste.iws.ws.SessionDataResponse ws) {
        SessionDataResponse<T> api = null;

        if (ws != null) {
            api = new SessionDataResponse<>(map(ws.getError()), ws.getMessage());

            api.setSessionData(ws.getSessionData());
            api.setModified(map(ws.getModified()));
            api.setCreated(map(ws.getCreated()));
        }

        return api;
    }

    public static FetchPermissionResponse map(final net.iaeste.iws.ws.FetchPermissionResponse ws) {
        FetchPermissionResponse api = null;

        if (ws != null) {
            api = new FetchPermissionResponse(map(ws.getError()), ws.getMessage());

            api.setUserId(ws.getUserId());
            api.setAuthorizations(mapAuthorizationList(ws.getAuthorizations()));
        }

        return api;
    }

    public static List<Authorization> mapAuthorizationList(final List<net.iaeste.iws.ws.Authorization> ws) {
        List<Authorization> api = null;

        if (ws != null) {
            api = new ArrayList<>(ws.size());
            for (final net.iaeste.iws.ws.Authorization wsAuth : ws) {
                final Authorization authorization = new Authorization();
                authorization.setUserGroup(map(wsAuth.getUserGroup()));
                api.add(authorization);
            }
        }

        return api;
    }

    public static net.iaeste.iws.ws.Password map(final Password api) {
        net.iaeste.iws.ws.Password ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Password();

            ws.setNewPassword(api.getNewPassword());
            ws.setIdentification(api.getIdentification());
        }

        return ws;
    }
}
