/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ws.AccessWSClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.ws;

import static net.iaeste.iws.client.ws.AccessMapper.map;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.ws.AccessWS;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class AccessWSClient extends Service implements Access {

    //public static void main(String[] args) throws MalformedURLException {
    //    final Access client = new AccessWSClient("http://localhost:8080/iws-ws/AccessWS?wsdl");
    //    final AuthenticationRequest request = new AuthenticationRequest();
    //    request.setUsername("test@iaeste.net");
    //    request.setPassword("tester");
    //    final AuthenticationResponse response = client.generateSession(request);
    //    if (response.isOk()) {
    //        System.out.println(response.toString());
    //    } else {
    //        System.out.println(response.getError());
    //        System.out.println(response.getMessage());
    //    }
    //}

    // =========================================================================
    // Constructor & Setup of WS Client
    // =========================================================================

    private static final QName ACCESS_SERVICE_NAME = new QName("http://ws.iws.iaeste.net/", "accessWSService");
    private static final QName ACCESS_SERVICE_PORT = new QName("http://ws.iws.iaeste.net/", "accessWS");
    private static final String ENDPOINT_ADDRESS = BindingProvider.ENDPOINT_ADDRESS_PROPERTY;
    private final HTTPClientPolicy policy = new HTTPClientPolicy();
    private final AccessWS client;

    /**
     * IWS Access WebService Client Constructor. Takes the URL for the WSDL as
     * parameter, to generate a new WebService Client instance.<br />
     *   For example: https://iws.iaeste.net/iws-ws/AccessWS?wsdl
     *
     * @param wsdlLocation IWS Access WSDL URL
     * @throws MalformedURLException if not a valid URL
     */
    public AccessWSClient(final String wsdlLocation) throws MalformedURLException {
        super(new URL(wsdlLocation), ACCESS_SERVICE_NAME);
        client = super.getPort(ACCESS_SERVICE_PORT, AccessWS.class);

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
        ((HTTPConduit) ClientProxy.getClient(client).getConduit()).setClient(policy);
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
    public Fallible requestResettingSession(final AuthenticationRequest request) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse resetSession(final String resetSessionToken) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> Fallible saveSessionData(final AuthenticationToken token, final SessionDataRequest<T> request) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> SessionDataResponse<T> readSessionData(final AuthenticationToken token) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse verifySession(final AuthenticationToken token) {
        return map(client.verifySession(map(token)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse deprecateSession(final AuthenticationToken token) {
        return map(client.deprecateSession(map(token)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible forgotPassword(final String username) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible resetPassword(final String resetPasswordToken, final Password password) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse updatePassword(final AuthenticationToken token, final Password password) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPermissionResponse fetchPermissions(final AuthenticationToken token) {
        return null;
    }
}
