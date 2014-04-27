/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.ExchangeController
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core;

import static net.iaeste.iws.core.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.DeletePublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.OfferTemplateRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.core.services.ExchangeFetchService;
import net.iaeste.iws.core.services.ExchangeService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ExchangeController extends CommonController implements Exchange {

    private static final Logger log = LoggerFactory.getLogger(ExchangeController.class);

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory The ServiceFactory
     */
    public ExchangeController(final ServiceFactory factory) {
        super(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferStatisticsResponse fetchOfferStatistics(final AuthenticationToken token, final OfferStatisticsRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchOfferStatistics()"));
        }
        OfferStatisticsResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_OFFER_STATISTICS);
            verify(request);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchOfferStatistics(authentication, request);
        } catch (IWSException e) {
            response = new OfferStatisticsResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchOfferStatistics()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerResponse processEmployer(final AuthenticationToken token, final ProcessEmployerRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting processEmployer()"));
        }
        EmployerResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_EMPLOYER);
            verify(request);

            final ExchangeService service = factory.prepareExchangeService();
            response = service.processEmployer(authentication, request);
        } catch (IWSException e) {
            response = new EmployerResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processEmployer()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchEmployerResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchEmployers()"));
        }
        FetchEmployerResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_EMPLOYERS);
            verify(request);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchEmployers(authentication, request);
        } catch (IWSException e) {
            response = new FetchEmployerResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchEmployers()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting processOffer()"));
        }
        OfferResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_OFFER);
            verify(request);

            final ExchangeService service = factory.prepareExchangeService();
            response = service.processOffer(authentication, request);
        } catch (IWSException e) {
            response = new OfferResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processOffer()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting deleteOffer()"));
        }
        OfferResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_OFFER);
            verify(request);

            final ExchangeService service = factory.prepareExchangeService();
            service.deleteOffer(authentication, request);
            response = new OfferResponse();
        } catch (IWSException e) {
            response = new OfferResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processOffer()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchOffers()"));
        }
        FetchOffersResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_OFFERS);
            verify(request);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchOffers(authentication, request);
        } catch (IWSException e) {
            response = new FetchOffersResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchOffers()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processOfferTemplate(final AuthenticationToken token, final OfferTemplateRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting processOfferTemplate()"));
        }
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_OFFER_TEMPLATES);
            verify(request);

            final ExchangeService service = factory.prepareExchangeService();
            service.processOfferTemplates(authentication, request);
            response = new FetchOfferTemplateResponse();
        } catch (IWSException e) {
            response = new FetchOfferTemplateResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processOfferTemplate()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchOfferTemplates()"));
        }
        FetchOfferTemplateResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_OFFER_TEMPLATES);
            verify(request);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchOfferTemplates(authentication, request);
        } catch (IWSException e) {
            response = new FetchOfferTemplateResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchOfferTemplates()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processPublishingGroup(final AuthenticationToken token, final ProcessPublishingGroupRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting processPublishGroup()"));
        }
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);
            verify(request);

            final ExchangeService service = factory.prepareExchangeService();
            service.processPublishingGroups(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processPublishGroup()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishingGroupResponse fetchPublishingGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchPublishGroups()"));
        }
        FetchPublishingGroupResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_PUBLISH_GROUPS);
            verify(request);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchPublishGroups(authentication, request);
        } catch (IWSException e) {
            response = new FetchPublishingGroupResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchPublishGroups()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deletePublishingGroup(final AuthenticationToken token, final DeletePublishingGroupRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting processPublishGroup()"));
        }
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);
            verify(request);

            final ExchangeService service = factory.prepareExchangeService();
            service.deletePublishingGroup(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processPublishGroup()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupsForSharingResponse fetchGroupsForSharing(final AuthenticationToken token) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchGroupsForSharing()"));
        }
        FetchGroupsForSharingResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_GROUPS_FOR_SHARING);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchGroupsForSharing(authentication);
        } catch (IWSException e) {
            response = new FetchGroupsForSharingResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchGroupsForSharing()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishOfferResponse processPublishOffer(final AuthenticationToken token, final PublishOfferRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting processPublishOffer()"));
        }
        PublishOfferResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);
            verify(request);

            final ExchangeService service = factory.prepareExchangeService();
            service.processPublishOffer(authentication, request);
            response = new PublishOfferResponse();
        } catch (IWSException e) {
            response = new PublishOfferResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processPublishOffer()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishedGroupsResponse fetchPublishedGroups(final AuthenticationToken token, final FetchPublishedGroupsRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchPublishedGroups()"));
        }
        FetchPublishedGroupsResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_PUBLISH_GROUPS);
            verify(request);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchPublishedOfferInfo(authentication, request);
        } catch (IWSException e) {
            response = new FetchPublishedGroupsResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchPublishedGroups()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processHideForeignOffers(final AuthenticationToken token, final HideForeignOffersRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting processHideForeignOffers()"));
        }
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);
            verify(request);

            final ExchangeService service = factory.prepareExchangeService();
            service.processHideForeignOffers(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processHideForeignOffers()"));
        }
        return response;
    }
}
