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
package net.iaeste.iws.ws;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.EmployerRequest;
import net.iaeste.iws.api.requests.exchange.OfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.RejectOfferRequest;
import net.iaeste.iws.api.responses.Response;
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
import net.iaeste.iws.ejb.ExchangeBean;
import net.iaeste.iws.ejb.cdi.IWSBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@SOAPBinding(style = SOAPBinding.Style.RPC)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING)
@WebService(name = "exchangeWS", serviceName = "exchangeWSService", portName = "exchangeWS", targetNamespace = "http://ws.iws.iaeste.net/")
public class ExchangeWS implements Exchange {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeWS.class);

    /**
     * Injection of the IWS Exchange Bean Instance, which embeds the
     * Transactional logic and itself invokes the actual Implementation.
     */
    @Inject @IWSBean private Exchange bean = null;

    /**
     * The WebService Context is only available for Classes, which are annotated
     * with @WebService. So, we need it injected and then in the PostConstruct
     * method, we can create a new RequestLogger instance with it.
     */
    @Resource
    private WebServiceContext context = null;

    private RequestLogger requestLogger = null;

    /**
     * Post Construct method, to initialize our Request Logger instance.
     */
    @PostConstruct
    @WebMethod(exclude = true)
    public void postConstruct() {
        requestLogger = new RequestLogger(context);
    }

    /**
     * Setter for the JNDI injected Bean context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param bean IWS Exchange Bean instance
     */
    @WebMethod(exclude = true)
    public void setExchangeBean(final ExchangeBean bean) {
        this.requestLogger = new RequestLogger(null);
        this.bean = bean;
    }

    // =========================================================================
    // Implementation of methods from Exchange in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public OfferStatisticsResponse fetchOfferStatistics(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final OfferStatisticsRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchOfferStatistics"));
        OfferStatisticsResponse response;

        try {
            response = bean.fetchOfferStatistics(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, OfferStatisticsResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public EmployerResponse processEmployer(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final EmployerRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processEmployer"));
        EmployerResponse response;

        try {
            response = bean.processEmployer(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, EmployerResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchEmployerResponse fetchEmployers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchEmployerRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchEmployers"));
        FetchEmployerResponse response;

        try {
            response = bean.fetchEmployers(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchEmployerResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public OfferResponse processOffer(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final OfferRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processOffer"));
        OfferResponse response;

        try {
            response = bean.processOffer(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, OfferResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public OfferCSVUploadResponse uploadOffers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final OfferCSVUploadRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "uploadOffers"));
        OfferCSVUploadResponse response;

        try {
            response = bean.uploadOffers(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, OfferCSVUploadResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchOffersResponse fetchOffers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchOffersRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchOffers"));
        FetchOffersResponse response;

        try {
            response = bean.fetchOffers(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchOffersResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public OfferCSVDownloadResponse downloadOffers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchOffersRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "downloadOffers"));
        OfferCSVDownloadResponse response;

        try {
            response = bean.downloadOffers(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, OfferCSVDownloadResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchGroupsForSharingResponse fetchGroupsForSharing(
            @WebParam(name = "token") final AuthenticationToken token) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchGroupsForSharing"));
        FetchGroupsForSharingResponse response;

        try {
            response = bean.fetchGroupsForSharing(token);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchGroupsForSharingResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public Response processPublishingGroup(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final PublishingGroupRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processPublishingGroup"));
        Response response;

        try {
            response = bean.processPublishingGroup(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, Response.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchPublishingGroupResponse fetchPublishingGroups(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchPublishGroupsRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchPublishingGroups"));
        FetchPublishingGroupResponse response;

        try {
            response = bean.fetchPublishingGroups(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchPublishingGroupResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public PublishOfferResponse processPublishOffer(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final PublishOfferRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processPublishOffer"));
        PublishOfferResponse response;

        try {
            response = bean.processPublishOffer(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, PublishOfferResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchPublishedGroupsResponse fetchPublishedGroups(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchPublishedGroupsRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchPublishedGroups"));
        FetchPublishedGroupsResponse response;

        try {
            response = bean.fetchPublishedGroups(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchPublishedGroupsResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public Response processHideForeignOffers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final HideForeignOffersRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processHideForeignOffers"));
        Response response;

        try {
            response = bean.processHideForeignOffers(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, Response.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public Response rejectOffer(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final RejectOfferRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "rejectOffer"));
        Response response;

        try {
            response = bean.rejectOffer(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, Response.class);
        }

        return response;
    }
}
