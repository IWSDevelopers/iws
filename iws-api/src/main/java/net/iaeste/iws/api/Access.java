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
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.util.Fallible;

import javax.ejb.Remote;
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
@Remote
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
     * In case a user has lost the SessionKey, and is thus incapable getting
     * into the system by normal means. It is possible to request that the
     * Session is being reset, i.e. that a new Session is created.<br />
     *   This request will then send an immediate notification to the e-mail
     * address of the given User, with a reset code. This code can then be
     * send back to the system and a new Session is then created to replace the
     * old, locked, Session.<br />
     *   Although the main part of this functionality is similar to just making
     * a normal login request, the main goal here is to avoid that a misuse of
     * the account is taking place - so only the owner should be able to do
     * this. As the resetting will also close the existing session.
     *
     * @param request  User Authentication Request object
     * @return Standard Error object
     */
    Fallible requestResettingSession(AuthenticationRequest request);

    /**
     * Handles the second part of Session Resetting. It will check if there
     * currently exists a Session and if so, then it will close the existing
     * Session, create a new and return this.
     *
     * @param resetSessionString The Reset token sent to the user
     * @return Authentication Result Object
     */
    AuthenticationResponse resetSession(String resetSessionString);

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
    <T extends Serializable> SessionDataResponse<T> readSessionData(AuthenticationToken token);

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
    FetchPermissionResponse fetchPermissions(AuthenticationToken token);
}
