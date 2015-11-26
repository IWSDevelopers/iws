/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-leargas) - net.iaeste.iws.leargas.clients.ExchangeWSClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.leargas.clients;

import net.iaeste.iws.ws.AuthenticationToken;
import net.iaeste.iws.ws.ExchangeWS;
import net.iaeste.iws.ws.FetchOffersRequest;
import net.iaeste.iws.ws.FetchOffersResponse;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * WebService Client for the IWS Exchange API.
 *
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public final class ExchangeWSClient extends CommonWSClient {

    // =========================================================================
    // Constructor & Setup of WS Client
    // =========================================================================

    private static final QName ACCESS_SERVICE_NAME = new QName("http://ws.iws.iaeste.net/", "exchangeWSService");
    private static final QName ACCESS_SERVICE_PORT = new QName("http://ws.iws.iaeste.net/", "exchangeWS");
    private final ExchangeWS client;

    /**
     * IWS Access WebService Client Constructor. Takes the URL for the WSDL as
     * parameter, to generate a new WebService Client instance.<br />
     *   For example: https://iws.iaeste.net:9443/iws-ws/exchangeWS?wsdl
     *
     * @param wsdlLocation IWS Exchange WSDL URL
     * @throws MalformedURLException if not a valid URL
     */
    public ExchangeWSClient(final String wsdlLocation) throws MalformedURLException {
        super(new URL(wsdlLocation), ACCESS_SERVICE_NAME);
        client = getPort(ACCESS_SERVICE_PORT, ExchangeWS.class);

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

    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        return client.fetchOffers(token, request);
    }
}
