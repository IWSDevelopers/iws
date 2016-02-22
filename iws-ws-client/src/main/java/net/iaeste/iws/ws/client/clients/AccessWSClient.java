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
package net.iaeste.iws.ws.client.clients;

import static net.iaeste.iws.ws.client.mappers.AccessMapper.map;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.ws.AccessWS;
import net.iaeste.iws.ws.client.mappers.CommonMapper;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class AccessWSClient extends CommonWSClient implements Access {

    // =========================================================================
    // Constructor & Setup of WS Client
    // =========================================================================

    private static final QName ACCESS_SERVICE_NAME = new QName("http://ws.iws.iaeste.net/", "accessWSService");
    private static final QName ACCESS_SERVICE_PORT = new QName("http://ws.iws.iaeste.net/", "accessWS");
    private final AccessWS client;

    /**
     * IWS Access WebService Client Constructor. Takes the URL for the WSDL as
     * parameter, to generate a new WebService Client instance.<br />
     *   For example: https://iws.iaeste.net:9443/iws-ws/accessWS?wsdl
     *
     * @param wsdlLocation IWS Access WSDL URL
     * @throws MalformedURLException if not a valid URL
     */
    public AccessWSClient(final String wsdlLocation) throws MalformedURLException {
        super(new URL(wsdlLocation), ACCESS_SERVICE_NAME);
        client = getPort(ACCESS_SERVICE_PORT, AccessWS.class);

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
    // Implementation of the Access Interface
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        return map(client.generateSession(map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse requestResettingSession(final AuthenticationRequest request) {
        return CommonMapper.map(client.requestResettingSession(map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse resetSession(final String resetSessionToken) {
        return map(client.resetSession(resetSessionToken));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> FallibleResponse saveSessionData(final AuthenticationToken token, final SessionDataRequest<T> request) {
        return map(client.saveSessionData(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> SessionDataResponse<T> readSessionData(final AuthenticationToken token) {
        return map(client.readSessionData(map(token)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse verifySession(final AuthenticationToken token) {
        return CommonMapper.map(client.verifySession(CommonMapper.map(token)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse deprecateSession(final AuthenticationToken token) {
        return CommonMapper.map(client.deprecateSession(CommonMapper.map(token)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse forgotPassword(final String username) {
        return CommonMapper.map(client.forgotPassword(username));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse resetPassword(final Password password) {
        return CommonMapper.map(client.resetPassword(map(password)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse updatePassword(final AuthenticationToken token, final Password password) {
        return CommonMapper.map(client.updatePassword(CommonMapper.map(token), map(password)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPermissionResponse fetchPermissions(final AuthenticationToken token) {
        return map(client.fetchPermissions(CommonMapper.map(token)));
    }
}
