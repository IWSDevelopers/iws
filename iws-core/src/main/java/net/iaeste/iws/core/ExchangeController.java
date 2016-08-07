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

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.exchange.EmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.requests.exchange.OfferRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
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
import net.iaeste.iws.core.services.ExchangeCSVFetchService;
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

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerResponse processEmployer(final AuthenticationToken token, final EmployerRequest request) {
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

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchEmployerResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerRequest request) {
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

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse processOffer(final AuthenticationToken token, final OfferRequest request) {
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

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferCSVUploadResponse uploadOffers(final AuthenticationToken token, final OfferCSVUploadRequest request) {
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

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
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

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferCSVDownloadResponse downloadOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        OfferCSVDownloadResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.FETCH_OFFERS);

            final ExchangeCSVFetchService service = factory.prepareExchangeCSVFetchService();
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

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response processPublishingGroup(final AuthenticationToken token, final PublishingGroupRequest request) {
        Response response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);

            final ExchangeService service = factory.prepareExchangeService();
            service.processPublishingGroups(authentication, request);
            response = new Response();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new Response(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishingGroupResponse fetchPublishingGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
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

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupsForSharingResponse fetchGroupsForSharing(final AuthenticationToken token) {
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

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishOfferResponse processPublishOffer(final AuthenticationToken token, final PublishOfferRequest request) {
        PublishOfferResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);

            final ExchangeService service = factory.prepareExchangeService();
            response = service.processPublishOffer(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new PublishOfferResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishedGroupsResponse fetchPublishedGroups(final AuthenticationToken token, final FetchPublishedGroupsRequest request) {
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

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response processHideForeignOffers(final AuthenticationToken token, final HideForeignOffersRequest request) {
        Response response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);

            final ExchangeService service = factory.prepareExchangeService();
            service.processHideForeignOffers(authentication, request);
            response = new Response();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new Response(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response rejectOffer(final AuthenticationToken token, final RejectOfferRequest request) {
        Response response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);

            final ExchangeService service = factory.prepareExchangeService();
            service.rejectOffer(authentication, request);
            response = new Response();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new Response(e.getError(), e.getMessage());
        }

        return response;
    }
}
