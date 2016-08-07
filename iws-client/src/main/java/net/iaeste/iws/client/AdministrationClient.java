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

import net.iaeste.iws.api.Administration;
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
import net.iaeste.iws.api.requests.RoleRequest;
import net.iaeste.iws.api.requests.SearchUserRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.api.responses.Response;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.responses.groupResponse;
import net.iaeste.iws.api.responses.RoleResponse;
import net.iaeste.iws.api.responses.UserGroupResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AdministrationClient implements Administration {

    private final Administration client;

    /**
     * Default Constructor.
     */
    public AdministrationClient() {
        client = ClientFactory.getInstance().getAdministrationImplementation();
    }

    // =========================================================================
    // Implementation of methods from Administration in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateUserResponse createUser(final AuthenticationToken token, final CreateUserRequest request) {
        return client.createUser(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response activateUser(final String activationString) {
        return client.activateUser(activationString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response updateUsername(final String updateCode) {
        return client.updateUsername(updateCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response controlUserAccount(final AuthenticationToken token, final UserRequest request) {
        return client.controlUserAccount(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response changeAccountName(final AuthenticationToken token, final AccountNameRequest request) {
        return client.changeAccountName(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchUserResponse fetchUser(final AuthenticationToken token, final FetchUserRequest request) {
        return client.fetchUser(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleResponse processRole(final AuthenticationToken token, final RoleRequest request) {
        return client.processRole(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchRoleResponse fetchRoles(final AuthenticationToken token, final FetchRoleRequest request) {
        return client.fetchRoles(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public groupResponse processGroup(final AuthenticationToken token, final GroupRequest request) {
        return client.processGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response deleteSubGroup(final AuthenticationToken token, final GroupRequest request) {
        return client.deleteSubGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupResponse fetchGroup(final AuthenticationToken token, final FetchGroupRequest request) {
        return client.fetchGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response changeGroupOwner(final AuthenticationToken token, final OwnerRequest request) {
        return client.changeGroupOwner(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupResponse processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        return client.processUserGroupAssignment(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchUserResponse searchUsers(final AuthenticationToken token, final SearchUserRequest request) {
        return client.searchUsers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response processCountry(final AuthenticationToken token, final CountryRequest request) {
        return client.processCountry(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        return client.fetchCountries(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmergencyListResponse fetchEmergencyList(final AuthenticationToken token) {
        return client.fetchEmergencyList(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContactsResponse fetchContacts(final AuthenticationToken token, final ContactsRequest request) {
        return client.fetchContacts(token, request);
    }
}
