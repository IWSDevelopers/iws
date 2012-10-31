/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.Access
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.PermissionResponse;
import net.iaeste.iws.api.responses.SessionResponse;

/**
 * Methods to control the current session for a user, i.e. methods to both
 * request a new session and deprecate an ongoing session. As well as list the
 * permissions that a user has.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface Access {

    /**
     * Based on the given user credentials in the {@code AuthenticationRequest}
     * object, a result object is returned with a newly created
     * {@code AuthenticationToken} object to be used for all subsequent
     * communication.
     *
     * @param request  User Authentication Request object
     * @return Authentication Result Object
     */
    AuthenticationResponse generateSession(AuthenticationRequest request);

    /**
     * The IWS doesn't delete ongoing sessions, it only closes them for further
     * usage. By invoking this method, the currently active session for the
     * given token is being deprecated (i.e. closed).
     *
     * @param token  The {@code AuthenticationToken} to deprecate the session for
     * @return Standard Error object
     */
    Fallible deprecateSession(AuthenticationToken token);

    SessionResponse verifySession(AuthenticationToken token);

    Fallible saveSessionData(AuthenticationToken token, SessionDataRequest request);

    /**
     * Retrieves the list of permissions for a given user, identified by the
     * token.
     *
     * @param token  User {@code AuthenticationToken}
     * @return Authorization Result Object
     */
    PermissionResponse fetchPermissions(AuthenticationToken token);

}
