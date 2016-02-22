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
package net.iaeste.iws.core;

import static net.iaeste.iws.api.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
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
import net.iaeste.iws.core.services.ExchangeCSVService;
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

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeController.class);

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
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting fetchOfferStatistics()"));
        }
        OfferStatisticsResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.FETCH_OFFER_STATISTICS);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchOfferStatistics(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new OfferStatisticsResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished fetchOfferStatistics()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerResponse processEmployer(final AuthenticationToken token, final ProcessEmployerRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting processEmployer()"));
        }
        EmployerResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_EMPLOYER);

            final ExchangeService service = factory.prepareExchangeService();
            response = service.processEmployer(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new EmployerResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished processEmployer()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchEmployerResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting fetchEmployers()"));
        }
        FetchEmployerResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.FETCH_EMPLOYERS);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchEmployers(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchEmployerResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished fetchEmployers()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting processOffer()"));
        }
        OfferResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_OFFER);

            final ExchangeService service = factory.prepareExchangeService();
            response = service.processOffer(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new OfferResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished processOffer()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting deleteOffer()"));
        }
        OfferResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_OFFER);

            final ExchangeService service = factory.prepareExchangeService();
            service.deleteOffer(authentication, request);
            response = new OfferResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new OfferResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished processOffer()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferCSVUploadResponse uploadOffers(final AuthenticationToken token, final OfferCSVUploadRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting uploadOffers()"));
        }
        OfferCSVUploadResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_OFFER);

            final ExchangeCSVService service = factory.prepareExchangeCSVService();
            response = service.uploadOffers(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new OfferCSVUploadResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished uploadOffers()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting fetchOffers()"));
        }
        FetchOffersResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.FETCH_OFFERS);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchOffers(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchOffersResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished fetchOffers()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferCSVDownloadResponse downloadOffers(final AuthenticationToken token, final OfferCSVDownloadRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting downloadOffers()"));
        }
        OfferCSVDownloadResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.FETCH_OFFERS);

            final ExchangeCSVService service = factory.prepareExchangeCSVService();
            response = service.downloadOffers(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new OfferCSVDownloadResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished downloadOffers()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse processPublishingGroup(final AuthenticationToken token, final ProcessPublishingGroupRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting processPublishGroup()"));
        }
        FallibleResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);

            final ExchangeService service = factory.prepareExchangeService();
            service.processPublishingGroups(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished processPublishGroup()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishingGroupResponse fetchPublishingGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting fetchPublishGroups()"));
        }
        FetchPublishingGroupResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.FETCH_PUBLISH_GROUPS);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchPublishGroups(authentication);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchPublishingGroupResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished fetchPublishGroups()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     * @deprecated
     */
    @Override
    @Deprecated
    public FallibleResponse deletePublishingGroup(final AuthenticationToken token, final DeletePublishingGroupRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting processPublishGroup()"));
        }
        FallibleResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);

            final ExchangeService service = factory.prepareExchangeService();
            service.deletePublishingGroup(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished processPublishGroup()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupsForSharingResponse fetchGroupsForSharing(final AuthenticationToken token) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting fetchGroupsForSharing()"));
        }
        FetchGroupsForSharingResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_GROUPS_FOR_SHARING);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchGroupsForSharing(authentication);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchGroupsForSharingResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished fetchGroupsForSharing()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishOfferResponse processPublishOffer(final AuthenticationToken token, final PublishOfferRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting processPublishOffer()"));
        }
        PublishOfferResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);

            final ExchangeService service = factory.prepareExchangeService();
            service.processPublishOffer(authentication, request);
            response = new PublishOfferResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new PublishOfferResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished processPublishOffer()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishedGroupsResponse fetchPublishedGroups(final AuthenticationToken token, final FetchPublishedGroupsRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting fetchPublishedGroups()"));
        }
        FetchPublishedGroupsResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.FETCH_PUBLISH_GROUPS);

            final ExchangeFetchService service = factory.prepareExchangeFetchService();
            response = service.fetchPublishedOfferInfo(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchPublishedGroupsResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished fetchPublishedGroups()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse processHideForeignOffers(final AuthenticationToken token, final HideForeignOffersRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting processHideForeignOffers()"));
        }
        FallibleResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);

            final ExchangeService service = factory.prepareExchangeService();
            service.processHideForeignOffers(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished processHideForeignOffers()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse rejectOffer(final AuthenticationToken token, final RejectOfferRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting rejectOffer()"));
        }
        FallibleResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);

            final ExchangeService service = factory.prepareExchangeService();
            service.rejectOffer(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished rejectOffer()"));
        }
        return response;
    }
}
