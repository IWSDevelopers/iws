/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ws.AccessMapper
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.ws;

import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class AccessMapper {

    public static net.iaeste.iws.ws.AuthenticationRequest map(final AuthenticationRequest api) {
        final net.iaeste.iws.ws.AuthenticationRequest ws = new net.iaeste.iws.ws.AuthenticationRequest();

        ws.setUsername(api.getUsername());
        ws.setPassword(api.getPassword());

        return ws;
    }

    public static AuthenticationResponse map(final net.iaeste.iws.ws.AuthenticationResponse ws) {
        final AuthenticationResponse api = new AuthenticationResponse(map(ws.getError()), ws.getMessage());

        api.setToken(map(ws.getToken()));
        return api;
    }

    public static AuthenticationToken map(net.iaeste.iws.ws.AuthenticationToken ws) {
        final AuthenticationToken api = new AuthenticationToken();

        if (ws != null) {
            api.setToken(ws.getToken());
            api.setGroupId(ws.getGroupId());
        }

        return api;
    }

    public static net.iaeste.iws.ws.AuthenticationToken map(AuthenticationToken api) {
        final net.iaeste.iws.ws.AuthenticationToken ws = new net.iaeste.iws.ws.AuthenticationToken();

        ws.setToken(api.getToken());
        ws.setGroupId(api.getGroupId());

        return ws;
    }

    public static FallibleResponse map(final net.iaeste.iws.ws.FallibleResponse ws) {
        return new FallibleResponse(map(ws.getError()), ws.getMessage());
    }

    public static IWSError map(final net.iaeste.iws.ws.IWSError ws) {
        return new IWSError(ws.getError(), ws.getDescription());
    }

    public static net.iaeste.iws.ws.Password map(final Password api) {
        final net.iaeste.iws.ws.Password ws = new net.iaeste.iws.ws.Password();

        ws.setNewPassword(api.getNewPassword());
        ws.setOldPassword(api.getOldPassword());

        return ws;
    }
}
