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
package net.iaeste.iws.client.spring;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
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
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.ExchangeBean;
import net.iaeste.iws.ejb.SessionRequestBean;
import net.iaeste.iws.ejb.schedulers.NotificationManagerScheduler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Spring based Exchange Client, which wraps the Exchange Controller from the
 * IWS core module within a transactional layer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Transactional
@Repository("exchangeSpringClient")
public final class ExchangeSpringClient implements Exchange {

    private Exchange client = null;

    /**
     * Injects the {@code EntityManager} instance required to invoke our
     * transactional DAOs. The EntityManager instance can only be injected into
     * the Spring Beans, we cannot create a Spring Bean for the Exchange EJB
     * otherwise.
     *
     * @param entityManager Spring controlled EntityManager instance
     */
    @PersistenceContext
    public void init(final EntityManager entityManager) {
        // Create the Notification Spy, and inject it
        final Notifications notitications = NotificationSpy.getInstance();
        final NotificationManagerScheduler notificationBean = new NotificationManagerScheduler();
        notificationBean.setNotifications(notitications);

        // Create a new SessionRequestBean instance with our entityManager
        final SessionRequestBean sessionRequestBean = new SessionRequestBean();
        sessionRequestBean.setEntityManager(entityManager);
        sessionRequestBean.setSettings(Beans.settings());
        sessionRequestBean.postConstruct();

        // Create an Exchange EJB, and inject the EntityManager & Notification Spy
        final ExchangeBean exchangeBean = new ExchangeBean();
        exchangeBean.setEntityManager(entityManager);
        exchangeBean.setNotificationManager(notificationBean);
        exchangeBean.setSessionRequestBean(sessionRequestBean);
        exchangeBean.setSettings(Beans.settings());
        exchangeBean.postConstruct();

        // Set our Exchange implementation to the Exchange EJB, running within
        // a "Spring Container".
        client = exchangeBean;
    }

    // =========================================================================
    // Implementation of methods from Exchange in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public OfferStatisticsResponse fetchOfferStatistics(final AuthenticationToken token, final OfferStatisticsRequest request) {
        return client.fetchOfferStatistics(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerResponse processEmployer(final AuthenticationToken token, final EmployerRequest request) {
        return client.processEmployer(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FetchEmployerResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerRequest request) {
        return client.fetchEmployers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse processOffer(final AuthenticationToken token, final OfferRequest request) {
        return client.processOffer(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OfferCSVUploadResponse uploadOffers(final AuthenticationToken token, final OfferCSVUploadRequest request) {
        return client.uploadOffers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        return client.fetchOffers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferCSVDownloadResponse downloadOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        return client.downloadOffers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response processPublishingGroup(final AuthenticationToken token, final PublishingGroupRequest request) {
        return client.processPublishingGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FetchPublishingGroupResponse fetchPublishingGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        return client.fetchPublishingGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FetchGroupsForSharingResponse fetchGroupsForSharing(final AuthenticationToken token) {
        return client.fetchGroupsForSharing(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishOfferResponse processPublishOffer(final AuthenticationToken token, final PublishOfferRequest request) {
        return client.processPublishOffer(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FetchPublishedGroupsResponse fetchPublishedGroups(final AuthenticationToken token, final FetchPublishedGroupsRequest request) {
        return client.fetchPublishedGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response processHideForeignOffers(final AuthenticationToken token, final HideForeignOffersRequest request) {
        return client.processHideForeignOffers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response rejectOffer(final AuthenticationToken token, final RejectOfferRequest request) {
        return client.rejectOffer(token, request);
    }
}
