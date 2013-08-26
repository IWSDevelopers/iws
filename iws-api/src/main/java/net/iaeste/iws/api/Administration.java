/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.Administration
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
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchRoleRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.util.Fallible;

import javax.ejb.Remote;

/**
 * Handles Administration of User Accounts, Groups, Roles and Countries.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Remote
public interface Administration {

    /**
     * Creates a new User Account, with the data from the Request Object. The
     * will have Status {@link net.iaeste.iws.api.enums.UserStatus#NEW}, and an
     * e-mail is send to the user via the provided username. The e-mail will
     * contain an Activation Link, which is then used to activate the
     * account.<br />
     *   Note, the account cannot be used before it is activated. If the Account
     * is been deleted before Activation is completed, then all information is
     * removed from the system. If the Account is deleted after activation, the
     * User Account Object will remain in the system, though all data will be
     * removed.<br />
     *   By default, this method will create a new User for the IntraWeb.
     * However, the Request Object contains a boolean field called
     * studentAccount. If this is set, then the account will be created for a
     * student, and not for a normal user. A student is assigned to the global
     * Student members group, and additionally to a countries Student group. A
     * student account is very limited in access, and can only see their own
     * data as well as any offer they are linked to.
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Request data, must contain username, password as well as
     *                first and last name
     * @return Standard Error Object
     */
    CreateUserResponse createUser(AuthenticationToken token, CreateUserRequest request);

    /**
     * Users cannot access the IWS, until their account has been activated, this
     * happens via an e-mail that is sent to their e-mail address (username),
     * with an activation link.<br />
     *   Once activation link is activated, this method should be invoked, which
     * will handle the actual activation process. Meaning, that if an account is
     * found in status "new", and with the given activation code, then it is
     * being updated to status "active", the code is removed and the updates are
     * saved.
     *
     * @param activationString Code used to activate the Account with
     * @return Standard Error Object
     */
    Fallible activateUser(String activationString);

    /**
     * Users who have changed their username, can invoke the controlUserAccount
     * method with a request for a username update. The system will then
     * generate a notification with a code that is then used to update the
     * username.<br />
     *   Only users who have an active account can update their usernames.<br />
     *   Once updated, the user can then use the new username to log into the
     * system with.
     *
     * @param updateCode Code used for updating the username for the account
     * @return Standard Error Object
     */
    Fallible updateUsername(String updateCode);

    /**
     * With this request, it is possible to alter the User Account specified in
     * the Request Object. The changes can include Blocking an Active Account,
     * and thus preventing the user from accessing or re-activating a Blocked
     * Account or even Delete an Account.<br />
     *   If the request is made by the user itself, it is then possible for the
     * user to update the data associated with him or her. This reflects on
     * personal information, and privacy settings. It is also possible for a
     * user to delete his or her account from the system, Though, it is not
     * possible to either activate or deactivate the account.<br />
     *   Note; deletion is a non-reversible action. Although the Account is
     * deleted, only private data associated with the account is deleted, the
     * account itself remains in the system with status deleted.<br />
     *   Note; All users may invoke this call, but to change the status of an
     * account, requires that the user has the right permissions.<br />
     *   Note; Regardless of who is making the request, it is not possible to
     * alter the status of someone who is currently having the role Owner, of
     * the Members Group.<br />
     *   Note; Updating or resetting Passwords, is handled via a set of methods
     * defined in the {@code Access} interface.
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Request data, must contain the User Account and the new
     *                state for it
     * @return Standard Error Object
     */
    Fallible controlUserAccount(AuthenticationToken token, UserRequest request);

    /**
     * Retrieves a list of users from the system.
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Fetch User Request Object
     * @return Response Object with the found users and error information
     */
    FetchUserResponse fetchUser(AuthenticationToken token, FetchUserRequest request);

    FetchRoleResponse fetchRoles(AuthenticationToken token, FetchRoleRequest request);

    Fallible processGroup(AuthenticationToken token, GroupRequest request);

    /**
     * Deletes a Groups from the system. This is done with a multitude of
     * checks, to avoid that groups are removed needlessly.
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Fetch Group Request Object
     * @return Standard Error Object
     */
    Fallible deleteGroup(AuthenticationToken token, GroupRequest request);

    /**
     * Retrieves the requested Group and the depending on the flags, it will
     * also fetch the associated Users and/or subgroups.
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Fetch Group Request Object
     * @return Response Object with the found group & users and error information
     */
    FetchGroupResponse fetchGroup(AuthenticationToken token, FetchGroupRequest request);

    Fallible processUserGroupAssignment(AuthenticationToken token, UserGroupAssignmentRequest request);

    /**
     * The IWS uses an internal listing of Countries, that are based on the UN
     * list. This method will allow to correct mistakes in existing records or
     * add new Countries to the list of existing.<br />
     *   The IWS will not allow that Country records are deleted, nor that the
     * names of Countries will conflict, i.e. multiple Countries having the same
     * names.
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Request data, must contain the Country Record
     * @return Standard Error Object
     */
    Fallible processCountries(AuthenticationToken token, CountryRequest request);

    /**
     * Retrieves a list of Countries from the internal UN listing of Countries,
     * together with some limited information about the Staff and National
     * Secretary for this Country.
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Fetch Country Request Object
     * @return Response Object with the found countries and error information
     */
    FetchCountryResponse fetchCountries(AuthenticationToken token, FetchCountryRequest request);
}
