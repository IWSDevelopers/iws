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
import javax.persistence.PersistenceException;
import javax.xml.ws.WebServiceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@WebService(name = "exchangeWS", serviceName = "exchangeWSService", portName = "exchangeWS", targetNamespace = "http://ws.iws.iaeste.net/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ExchangeWS implements Exchange {

    private static final Logger log = LoggerFactory.getLogger(ExchangeWS.class);

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
        log.info(requestLogger.prepareLogMessage(token, "fetchOfferStatistics"));
        OfferStatisticsResponse response;

        try {
            response = bean.fetchOfferStatistics(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new OfferStatisticsResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
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
            @WebParam(name = "request") final ProcessEmployerRequest request) {
        log.info(requestLogger.prepareLogMessage(token, "processEmployer"));
        EmployerResponse response;

        try {
            response = bean.processEmployer(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new EmployerResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
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
        log.info(requestLogger.prepareLogMessage(token, "fetchEmployers"));
        FetchEmployerResponse response;

        try {
            response = bean.fetchEmployers(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchEmployerResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
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
            @WebParam(name = "request") final ProcessOfferRequest request) {
        log.info(requestLogger.prepareLogMessage(token, "processOffer"));
        OfferResponse response;

        try {
            response = bean.processOffer(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new OfferResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
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
        log.info(requestLogger.prepareLogMessage(token, "deleteOffer"));
        OfferResponse response;

        try {
            response = bean.deleteOffer(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new OfferResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    @WebResult(name = "response")
    public OfferCSVUploadResponse uploadOffers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final OfferCSVUploadRequest request) {
        log.info(requestLogger.prepareLogMessage(token, "uploadOffers"));
        OfferCSVUploadResponse response;

        try {
            response = bean.uploadOffers(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new OfferCSVUploadResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
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
        log.info(requestLogger.prepareLogMessage(token, "fetchOffers"));
        FetchOffersResponse response;

        try {
            response = bean.fetchOffers(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchOffersResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
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
            @WebParam(name = "request") final OfferCSVDownloadRequest request) {
        log.info(requestLogger.prepareLogMessage(token, "downloadOffers"));
        OfferCSVDownloadResponse response;

        try {
            response = bean.downloadOffers(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new OfferCSVDownloadResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
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
        log.info(requestLogger.prepareLogMessage(token, "fetchGroupsForSharing"));
        FetchGroupsForSharingResponse response;

        try {
            response = bean.fetchGroupsForSharing(token);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchGroupsForSharingResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
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
        log.info(requestLogger.prepareLogMessage(token, "processPublishingGroup"));
        FallibleResponse response;

        try {
            response = bean.processPublishingGroup(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FallibleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
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
        log.info(requestLogger.prepareLogMessage(token, "fetchPublishingGroups"));
        FetchPublishingGroupResponse response;

        try {
            response = bean.fetchPublishingGroups(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchPublishingGroupResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse deletePublishingGroup(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final DeletePublishingGroupRequest request) {
        log.info(requestLogger.prepareLogMessage(token, "deletePublishingGroup"));
        FallibleResponse response;

        try {
            response = bean.deletePublishingGroup(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FallibleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
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
        log.info(requestLogger.prepareLogMessage(token, "processPublishOffer"));
        PublishOfferResponse response;

        try {
            response = bean.processPublishOffer(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new PublishOfferResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
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
        log.info(requestLogger.prepareLogMessage(token, "fetchPublishedGroups"));
        FetchPublishedGroupsResponse response;

        try {
            response = bean.fetchPublishedGroups(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchPublishedGroupsResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
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
        log.info(requestLogger.prepareLogMessage(token, "processHideForeignOffers"));
        FallibleResponse response;

        try {
            response = bean.processHideForeignOffers(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FallibleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
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
        log.info(requestLogger.prepareLogMessage(token, "rejectOffer"));
        FallibleResponse response;

        try {
            response = bean.rejectOffer(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FallibleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }
}
