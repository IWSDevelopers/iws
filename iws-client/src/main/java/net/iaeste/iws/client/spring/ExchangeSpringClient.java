/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.ExchangeSpringClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.spring;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.FacultyRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.exchange.FetchFacultiesRequest;
import net.iaeste.iws.api.requests.exchange.FetchGroupsForSharingRequest;
import net.iaeste.iws.api.requests.exchange.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.FetchStudentsRequest;
import net.iaeste.iws.api.requests.exchange.OfferTemplateRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.StudentRequest;
import net.iaeste.iws.api.responses.exchange.FetchFacultyResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishGroupResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishOfferResponse;
import net.iaeste.iws.api.responses.exchange.FetchStudentResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.ejb.ExchangeBean;
import net.iaeste.iws.ejb.NotificationManagerBean;
import net.iaeste.iws.persistence.notification.Notifications;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Spring based Exchange Client, which wraps the Exchange Controller from the
 * IWS core module within a transactional layer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyCoupledClass
 */
@Transactional
@Repository("exchangeSpringClient")
public final class ExchangeSpringClient implements Exchange {

    private Exchange client = null;

    /**
     * Injects the {@code EntityManager} instance required to invoke our
     * transactional daos. The EntityManager instance can only be injected into
     * the Spring Beans, we cannot create a Spring Bean for the Exchange EJB
     * otherwise.
     *
     * @param entityManager Spring controlled EntityManager instance
     */
    @PersistenceContext
    public void init(final EntityManager entityManager) {
        // Create the Notification Spy, and inject it
        final Notifications notitications = NotificationSpy.getInstance();
        final NotificationManagerBean notificationBean = new NotificationManagerBean();
        notificationBean.setNotifications(notitications);

        // Create an Exchange EJB, and inject the EntityManager & Notification Spy
        final ExchangeBean exchangeBean = new ExchangeBean();
        exchangeBean.setEntityManager(entityManager);
        exchangeBean.setNotificationManager(notificationBean);
        exchangeBean.postConstruct();

        // Set our Exchange implementation to the Exchange EJB, running withing
        // a "Spring Container".
        client = exchangeBean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchEmployerInformationResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerInformationRequest request) {
        return client.fetchEmployers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageFaculties(final AuthenticationToken token, final FacultyRequest request) {
        return client.manageFaculties(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchFacultyResponse fetchFaculties(final AuthenticationToken token, final FetchFacultiesRequest request) {
        return client.fetchFaculties(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        return client.processOffer(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        return client.deleteOffer(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        return client.fetchOffers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageOfferTemplate(final AuthenticationToken token, final OfferTemplateRequest request) {
        return client.manageOfferTemplate(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        return client.fetchOfferTemplates(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible managePublishGroup(final AuthenticationToken token, final PublishGroupRequest request) {
        return client.managePublishGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishGroupResponse fetchPublishGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        return client.fetchPublishGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageStudent(final AuthenticationToken token, final StudentRequest request) {
        return client.manageStudent(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchStudentResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        return client.fetchStudents(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupsForSharingResponse fetchGroupsForSharing(final AuthenticationToken token, final FetchGroupsForSharingRequest request) {
        return client.fetchGroupsForSharing(token, request);
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
    public FetchPublishOfferResponse fetchPublishedOfferInfo(final AuthenticationToken token, final FetchPublishOfferRequest request) {
        return client.fetchPublishedOfferInfo(token, request);
    }
}
