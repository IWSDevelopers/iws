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
package net.iaeste.iws.leargas.clients;

import net.iaeste.iws.ws.AccessWS;
import net.iaeste.iws.ws.AuthenticationRequest;
import net.iaeste.iws.ws.AuthenticationResponse;
import net.iaeste.iws.ws.AuthenticationToken;
import net.iaeste.iws.ws.FallibleResponse;
import net.iaeste.iws.ws.FetchPermissionResponse;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * WebService Client for the IWS Access API.
 *
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public final class AccessWSClient extends CommonWSClient {

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

        // make sure to initialize tlsParams prior to this call somewhere
        //http.setTlsClientParameters(getTlsParams());
        // The CXF will by default attempt to read the URL from the WSDL at the
        // Server, which is normally given with the server's name. However, as
        // we're running via a loadbalancer and/or proxies, this address may not
        // be available or resolvable via DNS. Instead, we force using the same
        // WSDL for requests as we use for accessing the server.
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
    // Access Functionality needed
    // =========================================================================

    /**
     * This method will try to create a new AuthenticationToken, which is
     * required for all subsequent requests made. The token is build by the IWS,
     * after the Authentication has been completed.<br />
     *   If the request failed, then the general Error information from the
     * Response Object will hopefully provide enough details to correct the
     * error.
     *
     * @param username Username, the users e-mail address
     * @param password Password
     * @return AuthenticationResponse with a new Session Token
     */
    public AuthenticationResponse generateSession(final String username, final String password) {
        final AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword(password);

        return client.generateSession(request);
    }

    public FallibleResponse deprecateSession(final AuthenticationToken token) {
        return client.deprecateSession(token);
    }

    public FetchPermissionResponse fetchPermissions(final AuthenticationToken token) {
        return client.fetchPermissions(token);
    }
}
