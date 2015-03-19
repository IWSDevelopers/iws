/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ws.mappers.AccessMapper
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.ws.mappers;

import net.iaeste.iws.api.dtos.Authorization;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class AccessMapper extends CommonMapper {

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
                Authorization auth = new Authorization();
                auth.setUserGroup(map(wsAuth.getUserGroup()));
                api.add(auth);
            }
        }

        return api;
    }

    public static net.iaeste.iws.ws.Password map(final Password api) {
        net.iaeste.iws.ws.Password ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Password();

            ws.setNewPassword(api.getNewPassword());
            ws.setOldPassword(api.getOldPassword());
        }

        return ws;
    }
}
