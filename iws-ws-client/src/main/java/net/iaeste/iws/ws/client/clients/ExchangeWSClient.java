/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ws.ExchangeWSClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ws.client.clients;

import static net.iaeste.iws.ws.client.mappers.ExchangeMapper.map;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.DeletePublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVDownloadRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.RejectOfferRequest;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.ws.ExchangeWS;
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
public final class ExchangeWSClient extends CommonWSClient implements Exchange {

    // =========================================================================
    // Constructor & Setup of WS Client
    // =========================================================================

    private static final QName ACCESS_SERVICE_NAME = new QName("http://ws.iws.iaeste.net/", "exchangeWSService");
    private static final QName ACCESS_SERVICE_PORT = new QName("http://ws.iws.iaeste.net/", "exchangeWS");
    private final ExchangeWS client;

    /**
     * IWS Access WebService Client Constructor. Takes the URL for the WSDL as
     * parameter, to generate a new WebService Client instance.<br />
     *   For example: https://iws.iaeste.net/iws-ws/AccessWS?wsdl
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

        // If we're dealing with a HTTPS request, then we'll initialize and
        // set the TLS Client Parameters. The check is primitive, but covers
        // the general case.
        if ("https://".equals(wsdlLocation.substring(0, 8).toLowerCase(IWSConstants.DEFAULT_LOCALE))) {
            // Before doing anything else, we're initializing the SSL Conduit
            initializeSSL();

            // Add the TLS Client Parameters & our new Policy
            conduit.setTlsClientParameters(tlsClientParameters);
        }

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
    public OfferStatisticsResponse fetchOfferStatistics(final AuthenticationToken token, final OfferStatisticsRequest request) {
        return map(client.fetchOfferStatistics(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerResponse processEmployer(final AuthenticationToken token, final ProcessEmployerRequest request) {
        return map(client.processEmployer(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchEmployerResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerRequest request) {
        return map(client.fetchEmployers(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        return map(client.processOffer(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        return map(client.deleteOffer(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferCSVUploadResponse uploadOffers(final AuthenticationToken token, final OfferCSVUploadRequest request) {
        return new OfferCSVUploadResponse(IWSErrors.NOT_IMPLEMENTED, "Functionality requires an API change, which has not yet been made.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        return map(client.fetchOffers(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferCSVDownloadResponse downloadOffers(final AuthenticationToken token, final OfferCSVDownloadRequest request) {
        return map(client.downloadOffers(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupsForSharingResponse fetchGroupsForSharing(final AuthenticationToken token) {
        return map(client.fetchGroupsForSharing(map(token)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse processPublishingGroup(final AuthenticationToken token, final ProcessPublishingGroupRequest request) {
        return map(client.processPublishingGroup(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishingGroupResponse fetchPublishingGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        return map(client.fetchPublishingGroups(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public FallibleResponse deletePublishingGroup(final AuthenticationToken token, final DeletePublishingGroupRequest request) {
        return map(client.deletePublishingGroup(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishedGroupsResponse fetchPublishedGroups(final AuthenticationToken token, final FetchPublishedGroupsRequest request) {
        return new FetchPublishedGroupsResponse(IWSErrors.NOT_IMPLEMENTED, "Functionality requires an API change, which has not yet been made.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishOfferResponse processPublishOffer(final AuthenticationToken token, final PublishOfferRequest request) {
        return map(client.processPublishOffer(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse processHideForeignOffers(final AuthenticationToken token, final HideForeignOffersRequest request) {
        return map(client.processHideForeignOffers(map(token), map(request)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse rejectOffer(final AuthenticationToken token, final RejectOfferRequest request) {
        return map(client.rejectOffer(map(token), map(request)));
    }
}
