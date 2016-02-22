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
package net.iaeste.iws.api;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AccountNameRequest;
import net.iaeste.iws.api.requests.ContactsRequest;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchRoleRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.OwnerRequest;
import net.iaeste.iws.api.requests.SearchUserRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.api.responses.ProcessUserGroupResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;

/**
 * <p>Handles Administration of User Accounts, Groups, Roles and Countries.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Administration {

    /**
     * <p>The IWS uses an internal listing of Countries, that are based on the
     * UN list. This method will allow to correct mistakes in existing records
     * or add new Countries to the list of existing.</p>
     * <p>The IWS will not allow that Country records are deleted, nor that the
     * names of Countries will conflict, i.e. multiple Countries having the same
     * names.</p>
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Request data, must contain the Country Record
     * @return Standard Error Object
     */
    FallibleResponse processCountry(AuthenticationToken token, CountryRequest request);

    /**
     * <p>Retrieves a list of Countries from the internal UN listing of
     * Countries, together with some limited information about the Staff and
     * National Secretary for this Country.</p>
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Fetch Country Request Object
     * @return Response Object with the found countries and error information
     */
    FetchCountryResponse fetchCountries(AuthenticationToken token, FetchCountryRequest request);

    /**
     * <p>Creates a new User Account, with the data from the Request Object. The
     * will have Status {@link net.iaeste.iws.api.enums.UserStatus#NEW}, and an
     * e-mail is send to the user via the provided username. The e-mail will
     * contain an Activation Link, which is then used to activate the
     * account.</p>
     *
     * <p>Note, the account cannot be used before it is activated. If the
     * Account is been deleted before Activation is completed, then all
     * information is removed from the system. If the Account is deleted after
     * activation, the User Account Object will remain in the system, though
     * all data will be removed.</p>
     *
     * <p>By default, this method will create a new User for the IntraWeb.
     * However, the Request Object contains a boolean field called
     * studentAccount. If this is set, then the account will be created for a
     * student, and not for a normal user. A student is assigned to the global
     * Student members group, and additionally to a countries Student group. A
     * student account is very limited in access, and can only see their own
     * data as well as any offer they are linked to.</p>
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Request data, must contain username, password as well as
     *                first and last name
     * @return Standard Error Object
     */
    CreateUserResponse createUser(AuthenticationToken token, CreateUserRequest request);

    /**
     * <p>With this request, it is possible to alter the User Account specified
     * in the Request Object. The changes can include Blocking an Active
     * Account, and thus preventing the user from accessing or re-activating a
     * Blocked Account or even Delete an Account.</p>
     *
     * <p>If the request is made by the user itself, it is then possible for the
     * user to update the data associated with him or her. This reflects on
     * personal information, and privacy settings. It is also possible for a
     * user to delete his or her account from the system, Though, it is not
     * possible to either activate or deactivate the account.</p>
     *
     * <p>Note; deletion is a non-reversible action. Although the Account is
     * deleted, only private data associated with the account is deleted, the
     * account itself remains in the system with status deleted.</p>
     *
     * <p>Note; All users may invoke this call, but to change the status of an
     * account, requires that the user has the right permissions.</p>
     *
     * <p>Note; Regardless of who is making the request, it is not possible to
     * alter the status of someone who is currently having the role Owner, of
     * the Members Group.</p>
     *
     * <p>Note; Updating or resetting Passwords, is handled via a set of methods
     * defined in the {@code Access} interface.</p>
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Request data, must contain the User Account and the new
     *                state for it
     * @return Standard Error Object
     */
    FallibleResponse controlUserAccount(AuthenticationToken token, UserRequest request);

    /**
     * <p>Users cannot access the IWS, until their account has been activated,
     * this happens via an e-mail that is sent to their e-mail address
     * (username), with an activation link.</p>
     *
     * <p>Once activation link is activated, this method should be invoked,
     * which will handle the actual activation process. Meaning, that if an
     * account is found in status "new", and with the given activation code,
     * then it is being updated to status "active", the code is removed and the
     * updates are saved.</p>
     *
     * @param activationString Code used to activate the Account with
     * @return Standard Error Object
     */
    FallibleResponse activateUser(String activationString);

    /**
     * <p>Users who have changed their username, can invoke the
     * controlUserAccount method with a request for a username update. The
     * system will then generate a notification with a code that is then used
     * to update the username.</p>
     *
     * <p>Only users who have an active account can update their username's.</p>
     *
     * <p>Once updated, the user can then use the new username to log into the
     * system with.</p>
     *
     * @param updateCode Code used for updating the username for the account
     * @return Standard Error Object
     */
    FallibleResponse updateUsername(String updateCode);

    /**
     * <p>The request will allow an update of the name of an Account, i.e.
     * updating the users first and lastname's.</p>
     *
     * <p>It is only allowed to update one of the names, meaning that it is not
     * possible to update both a users firstname and lastname at the same time.
     * The request will first check if the lastname should be updated. If not,
     * then the request will update the users firstname.</p>
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Request data, must contain the User Account and the new
     *                name for it
     * @return Standard Error Object
     */
    FallibleResponse changeAccountName(AuthenticationToken token, AccountNameRequest request);

    /**
     * <p>Retrieves the details about a user. The amount of details depends upon
     * the users privacy settings. If the privacy settings are high, then only
     * the user itself can view all the details.</p>
     *
     * <p>Note, that by default all privacy settings are set to high, meaning
     * that users have to actively lower them before others can view this
     * information.</p>
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Fetch User Request Object
     * @return Response Object with the found users and error information
     */
    FetchUserResponse fetchUser(AuthenticationToken token, FetchUserRequest request);

    FetchRoleResponse fetchRoles(AuthenticationToken token, FetchRoleRequest request);

    ProcessGroupResponse processGroup(AuthenticationToken token, GroupRequest request);

    /**
     * <p>This request allows a user to delete a subgroup to the one that is
     * currently defined in the Token Object.</p>
     *
     * <p>The subgroup must be empty, i.e. with no further Groups underneath,
     * otherwise the system will reject the request. Users associated with the
     * Group will loose their association, and Data attached to the Group will
     * be deleted from the System.</p>
     *
     * <p>Only Groups of type Local Committee or Work Group can be deleted with
     * this request.</p>
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Fetch Group Request Object
     * @return Standard Error Object
     */
    FallibleResponse deleteSubGroup(AuthenticationToken token, GroupRequest request);

    /**
     * <p>Retrieves the requested Group and the depending on the flags, it will
     * also fetch the associated Users and/or subgroups.</p>
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Fetch Group Request Object
     * @return Response Object with the found group &amp; users and error information
     */
    FetchGroupResponse fetchGroup(AuthenticationToken token, FetchGroupRequest request);

    /**
     * <p>As their can only be a single Owner of a Group, the changing of such is
     * not part of the #processUserGroupAssignment request, if attempted, an
     * Exception is thrown.</p>
     *
     * <p>This request set the given User (which must be Active) as the new
     * Owner, and reduce the current (invoking) User as Moderator instead,
     * regardless if the given User is a member of the Group or not.</p>
     *
     * <p>Note, that two special cases exists for this request. Changing either
     * a National Secretary or the General Secretary, since the request must be
     * made against the respective National or International Groups, and the
     * this change will also update the current owner of the Member Group. For
     * this reason, the new NS or GS - must be an Active Member of the Member
     * Group!</p>
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Fetch Group Request Object
     * @return Standard Error Object
     */
    FallibleResponse changeGroupOwner(AuthenticationToken token, OwnerRequest request);

    /**
     * <p>The UserGroup Assignment controls how a User may interact with a
     * Group. The request will allow Users to make minor corrections to their
     * own records, and depending on their permissions, it is possible for them
     * to make more corrections to other Users. The only thing that cannot be
     * altered with this request, is the current Owner. Since the Owner is a
     * special User, and there must always be an Owner!</p>
     *
     * <p>If a User is attempting to process their own records, then, they may
     * only change their Private mail flag and their title. Only the Owner may
     * set the Public List flag, meaning that the user will receive e-mails send
     * to the public mailing list.</p>
     *
     * <p>If invoked by an administrator against a different user, then it is
     * possible to change the persons permissions, though it is not possible to
     * use this request to assign a new owner to a Group, this is handled via a
     * {@link #changeGroupOwner} request.</p>
     *
     * @param token    Authentication information about the user invoking the
     *                 request
     * @param request  Request data, must contain the UserGroup settings
     * @return Response with altered relation and error information
     */
    ProcessUserGroupResponse processUserGroupAssignment(AuthenticationToken token, UserGroupAssignmentRequest request);

    /**
     * <p>Search functionality for Members. When adding users to a Group, it
     * must be possible to search among members from various groups.</p>
     *
     * <p>If the Request also contains a Member Group, then the search will be
     * limited to this, otherwise the search will be among all users where the
     * status is either active or new.</p>
     *
     * @param token    Authentication information about the user invoking the
     *                 request
     * @param request  Request data, must contain the name and optionally Group
     * @return Response with altered relation and error information
     */
    SearchUserResponse searchUsers(AuthenticationToken token, SearchUserRequest request);

    /**
     * <p>Fetches the list of all National Committee Members, which is used to
     * generate the emergency contact list. This list is only available to
     * members of the National Committee, and will also display certain private
     * information.</p>
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @return List of all National Committees with Error information
     */
    EmergencyListResponse fetchEmergencyList(AuthenticationToken token);

    /**
     * <p>This Request is similar to the Contacts module from the old IntraWeb.
     * The request will fetch either of three things:</p>
     * <ul>
     *   <li>
     *     <b>View User Details</b><br>
     *     Reads out the User details, unless the information if they are
     *     public plus all the Groups the user is a member of.
     *   </li>
     *   <li>
     *     <b>View Group Details</b><br>
     *     Reads out the Group details, including a list of all the users who
     *     are currently associated with it.
     *   </li>
     *   <li>
     *     <b>Groups</b><br>
     *     If no specific information is provided, then a list of all Member and
     *     International Groups is returned.
     *   </li>
     * </ul>
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Request for one of the requested types of fetching
     * @return Matching response, with error information
     */
    ContactsResponse fetchContacts(AuthenticationToken token, ContactsRequest request);
}
