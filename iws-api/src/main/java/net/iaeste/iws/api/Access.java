/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.api.responses.PermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;

import java.io.Serializable;

/**
 * Access to the IWS, requires a Session. This Interface, holds all required
 * functionality to properly work with a Session, which includes creating,
 * verifying, updating and deprecating them. Additionally, the method to fetch
 * the list of Permissions is also here.<br />
 *   The usage of the IWS follows this flow:
 * <pre>
 *     1. Create a new Session
 *     2. Iterate as long as desired
 *        a) Verify the users Session, returns the current Session Data
 *        b) Perform various actions against the IWS, using the Session
 *        c) If SessionData needs saving, save them
 *     3. Once work is completed, deprecate the Session
 * </pre>
 * It is important to underline, that a User may only have 1 (one) active
 * Session at the time. Meaning, that the same user may not log into different
 * IWS based systems at the same time. This feature was added to prevent Account
 * misusage. Though the consequences of attempting will simply be a rejection.
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
     * Used to save a users session Data in the IWS.
     *
     * @param token  User Authentication Request object
     * @param request  SessionData Request Object
     * @return Standard Error object
     */
    <T extends Serializable> Fallible saveSessionData(AuthenticationToken token, SessionDataRequest<T> request);

    /**
     * Verifies the current Session and returns the associated Session Data in
     * the response.
     *
     * @param token  User Authentication Request object
     * @return Session Response, with Error And Session data
     */
    <T extends Serializable> SessionDataResponse<T> fetchSessionData(AuthenticationToken token);

    /**
     * The IWS doesn't delete ongoing sessions, it only closes them for further
     * usage. By invoking this method, the currently active session for the
     * given token is being deprecated (i.e. closed).<br />
     *   When deprecating the Session, all data assigned to the Session is being
     * removed.
     *
     * @param token  The {@code AuthenticationToken} to deprecate the session for
     * @return Standard Error object
     */
    Fallible deprecateSession(AuthenticationToken token);

    /**
     * Retrieves the list of permissions for a given user, identified by the
     * token. If a GroupId is set in the token, then a list of Permissions that
     * the user may perform against the content of this group is returned.
     * Otherwise, all the Groups that the user is a member of is returned,
     * together with their associated permissions.
     *
     * @param token  User {@code AuthenticationToken}
     * @return Authorization Result Object
     */
    PermissionResponse fetchPermissions(AuthenticationToken token);
}
