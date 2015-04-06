/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
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
 * misusage. Though the consequences of attempting will simply be a
 * rejection.<br />
 *   The interface is annotated with the WebService annotations and XML elements
 * used by JAXB, although some are implicit so named, it is helpful to
 * explicitly set them, to avoid any future problems.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Access extends Serializable {

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
     * this. As the resetting will also close the existing session.<br />
     *   This method is excluded from the WebService exposure, since it is
     *
     * @param request  User Authentication Request object
     * @return Standard Error object
     */
    FallibleResponse requestResettingSession(AuthenticationRequest request);

    /**
     * Handles the second part of Session Resetting. It will check if there
     * currently exists a Session and if so, then it will close the existing
     * Session, create a new and return this.
     *
     * @param resetSessionToken The Reset token sent to the user
     * @return Authentication Result Object
     */
    AuthenticationResponse resetSession(String resetSessionToken);

    /**
     * Used to save a users session Data in the IWS.
     *
     * @param token  User Authentication Request object
     * @param request  SessionData Request Object
     * @return Standard Error object
     */
    <T extends Serializable> FallibleResponse saveSessionData(AuthenticationToken token, SessionDataRequest<T> request);

    /**
     * Verifies the current Session and returns the associated Session Data in
     * the response.
     *
     * @param token  User Authentication Request object
     * @return Session Response, with Error And Session data
     */
    <T extends Serializable> SessionDataResponse<T> readSessionData(AuthenticationToken token);

    /**
     * For WebServices using the IWS to manage Authentication & Authorization
     * control, it is helpful to verify if a session is still valid or not. This
     * request simply allows this. It returns a standard error object, which
     * contains the verification information.
     *
     * @param token The {@code AuthenticationToken} to deprecate the session for
     * @return Standard Error object
     */
    FallibleResponse verifySession(AuthenticationToken token);

    /**
     * The IWS doesn't delete ongoing sessions, it only closes them for further
     * usage. By invoking this method, the currently active session for the
     * given token is being deprecated (i.e. closed).<br />
     *   When deprecating the Session, all data assigned to the Session is being
     * removed.
     *
     * @param token The {@code AuthenticationToken} to deprecate the session for
     * @return Standard Error object
     */
    FallibleResponse deprecateSession(AuthenticationToken token);

    /**
     * If a user forgot the password, then this request will send a notification
     * to the Users registered e-mail address (username). The e-mail will
     * contain a reset Token, that can be used when invoking the
     * {@code #resetPassword(resetPasswordToken, newPassword)} method.
     *
     * @param username The users username, i.e. e-mail address
     * @return Standard Error object
     */
    FallibleResponse forgotPassword(String username);

    /**
     * Resets a users password in the system.
     *
     * @param resetPasswordToken Reset Password Token, from the notification
     * @param password           Password Object for the user
     * @return Standard Error object
     */
    FallibleResponse resetPassword(String resetPasswordToken, Password password);

    /**
     * Updates a users password in the system.
     *
     * @param token    User {@code AuthenticationToken}
     * @param password Password Object for the user
     * @return Standard Error object
     */
    FallibleResponse updatePassword(AuthenticationToken token, Password password);

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
