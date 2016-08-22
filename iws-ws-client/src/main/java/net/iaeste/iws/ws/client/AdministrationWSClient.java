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
package net.iaeste.iws.ws.client;

import static net.iaeste.iws.ws.client.mappers.AdministrationMapper.map;

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
import net.iaeste.iws.api.responses.GroupResponse;
import net.iaeste.iws.api.responses.RoleResponse;
import net.iaeste.iws.api.responses.UserGroupResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;
import net.iaeste.iws.ws.AdministrationWS;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class AdministrationWSClient extends CommonWSClient implements Administration {

    // =========================================================================
    // Constructor & Setup of WS Client
    // =========================================================================

    private static final QName ACCESS_SERVICE_NAME = new QName("http://ws.iws.iaeste.net/", "administrationWSService");
    private static final QName ACCESS_SERVICE_PORT = new QName("http://ws.iws.iaeste.net/", "administrationWS");
    private final AdministrationWS client;

    /**
     * IWS Access WebService Client Constructor. Takes the URL for the WSDL as
     * parameter, to generate a new WebService Client instance.<br />
     *   For example: https://iws.iaeste.net:9443/iws-ws/administrationWS?wsdl
     *
     * @param wsdlLocation IWS Administration WSDL URL
     * @throws MalformedURLException if not a valid URL
     */
    public AdministrationWSClient(final String wsdlLocation) throws MalformedURLException {
        super(new URL(wsdlLocation), ACCESS_SERVICE_NAME);
        client = getPort(ACCESS_SERVICE_PORT, AdministrationWS.class);

        // The CXF will by default attempt to read the URL from the WSDL at the
        // Server, which is normally given with the server's name. However, as
        // we're running via a load balancer and/or proxies, this address may
        // not be available or resolvable via DNS. Instead, we force using the
        // same WSDL for requests as we use for accessing the server.
        // Binding: http://cxf.apache.org/docs/client-http-transport-including-ssl-support.html#ClientHTTPTransport%28includingSSLsupport%29-Howtooverridetheserviceaddress?
        ((BindingProvider) client).getRequestContext().put(ENDPOINT_ADDRESS, wsdlLocation);

        // The CXF has a number of default Policy settings, which can all be
        // controlled via the internal Policy Scheme. To override or update the
        // default values, the Policy must be exposed. Which is done by setting
        // a new Policy Scheme which can be access externally.
        // Policy: http://cxf.apache.org/docs/client-http-transport-including-ssl-support.html#ClientHTTPTransport%28includingSSLsupport%29-HowtoconfiguretheHTTPConduitfortheSOAPClient?
        final Client proxy = ClientProxy.getClient(client);
        final HTTPConduit conduit = (HTTPConduit) proxy.getConduit();

        // Finally, set the Policy into the HTTP Conduit.
        conduit.setClient(policy);
    }

    // =========================================================================
    // Implementation of the Exchange Interface
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Response processCountry(final AuthenticationToken token, final CountryRequest request) {
        return map(client.processCountry(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        return map(client.fetchCountries(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateUserResponse createUser(final AuthenticationToken token, final CreateUserRequest request) {
        return map(client.createUser(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response controlUserAccount(final AuthenticationToken token, final UserRequest request) {
        return map(client.controlUserAccount(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response activateUser(final String activationString) {
        return map(client.activateUser(activationString));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response updateUsername(final String updateCode) {
        return map(client.updateUsername(updateCode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response changeAccountName(final AuthenticationToken token, final AccountNameRequest request) {
        return map(client.changeAccountName(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchUserResponse fetchUser(final AuthenticationToken token, final FetchUserRequest request) {
        return map(client.fetchUser(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleResponse processRole(final AuthenticationToken token, final RoleRequest request) {
        return map(client.processRole(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchRoleResponse fetchRoles(final AuthenticationToken token, final FetchRoleRequest request) {
        return map(client.fetchRoles(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupResponse processGroup(final AuthenticationToken token, final GroupRequest request) {
        return map(client.processGroup(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response deleteSubGroup(final AuthenticationToken token, final GroupRequest request) {
        return map(client.deleteSubGroup(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupResponse fetchGroup(final AuthenticationToken token, final FetchGroupRequest request) {
        return map(client.fetchGroup(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response changeGroupOwner(final AuthenticationToken token, final OwnerRequest request) {
        return map(client.changeGroupOwner(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupResponse processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        return map(client.processUserGroupAssignment(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchUserResponse searchUsers(final AuthenticationToken token, final SearchUserRequest request) {
        return map(client.searchUsers(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmergencyListResponse fetchEmergencyList(final AuthenticationToken token) {
        return map(client.fetchEmergencyList(map(token)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContactsResponse fetchContacts(final AuthenticationToken token, final ContactsRequest request) {
        return map(client.fetchContacts(map(token), map(request)));
    }
}
