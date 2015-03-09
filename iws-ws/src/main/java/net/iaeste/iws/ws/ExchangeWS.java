/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws) - net.iaeste.iws.ws.ExchangeWS
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ws;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.DeletePublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVDownloadRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.OfferTemplateRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.RejectOfferRequest;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.ejb.ExchangeBean;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@WebService(name = "exchangeWS", serviceName = "exchangeWSService", portName = "exchangeWS", targetNamespace = "http://ws.iws.iaeste.net/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ExchangeWS implements Exchange {

    @EJB(beanInterface = Exchange.class, lookup = "java:global/iws/iws-ejb/ExchangeBean!net.iaeste.iws.api.Exchange")
    //@Inject @IWSBean
    private Exchange bean = null;

    /**
     * Setter for the JNDI injected Bean context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param bean IWS Exchange Bean instance
     */
    @WebMethod(exclude = true)
    public void setExchangeBean(final ExchangeBean bean) {
        this.bean = bean;
    }

    // =========================================================================
    // Implementation of methods from Exchange in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    // TODO For now this method is excluded, due to the enummap used. It must be verified what is a better option.
    @WebMethod(exclude = true)
    @WebResult(name = "response")
    public OfferStatisticsResponse fetchOfferStatistics(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final OfferStatisticsRequest request) {
        return new OfferStatisticsResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public EmployerResponse processEmployer(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final ProcessEmployerRequest request) {
        return bean.processEmployer(token, request);
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
        return bean.fetchEmployers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public OfferResponse processOffer(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final ProcessOfferRequest request) {
        return bean.processOffer(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public OfferResponse deleteOffer(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final DeleteOfferRequest request) {
        return new OfferResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // TODO For now this method is excluded, due to the enummap used. It must be verified what is a better option.
    @WebMethod(exclude = true)
    @WebResult(name = "response")
    public OfferCSVUploadResponse uploadOffers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final OfferCSVUploadRequest request) {
        return new OfferCSVUploadResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
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
        return bean.fetchOffers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public OfferCSVDownloadResponse downloadOffers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final OfferCSVDownloadRequest request) {
        return new OfferCSVDownloadResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchGroupsForSharingResponse fetchGroupsForSharing(
            @WebParam(name = "token") final AuthenticationToken token) {
        return new FetchGroupsForSharingResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse processOfferTemplate(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final OfferTemplateRequest request) {
        return new FallibleResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchOfferTemplateResponse fetchOfferTemplates(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchOfferTemplatesRequest request) {
        return new FetchOfferTemplateResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse processPublishingGroup(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final ProcessPublishingGroupRequest request) {
        return new FallibleResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
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
        return new FetchPublishingGroupResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse deletePublishingGroup(
            @WebParam(name = "token") final AuthenticationToken token, final DeletePublishingGroupRequest request) {
        return new FallibleResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
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
        return new PublishOfferResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // TODO For now this method is excluded, due to the enummap used. It must be verified what is a better option.
    @WebMethod(exclude = true)
    @WebResult(name = "response")
    public FetchPublishedGroupsResponse fetchPublishedGroups(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchPublishedGroupsRequest request) {
        return new FetchPublishedGroupsResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse processHideForeignOffers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final HideForeignOffersRequest request) {
        return new FallibleResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse rejectOffer(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final RejectOfferRequest request) {
        return new FallibleResponse(IWSErrors.ILLEGAL_ACTION, "Method is not accessible via WebServices.");
    }
}
