/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.SpringExchangeClient
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
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FacultyRequest;
import net.iaeste.iws.api.requests.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.FetchFacultiesRequest;
import net.iaeste.iws.api.requests.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.FetchStudentsRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.requests.StudentRequest;
import net.iaeste.iws.api.responses.FacultyResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.api.responses.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;
import net.iaeste.iws.api.responses.StudentResponse;
import net.iaeste.iws.core.ExchangeController;
import net.iaeste.iws.core.services.ServiceFactory;
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
 */
@Transactional
@Repository("springExchangeClient")
public final class SpringExchangeClient implements Exchange {

    private Exchange exchange = null;

    /**
     * Injects the {@code EntityManager} instance required to invoke our
     * transactional daos. The EntityManager instance can only be injected into
     * the beans, we cannot create a bean for the Access Controller otherwise.
     *
     * @param entityManager Spring controlled EntityManager instance
     */
    @PersistenceContext
    public void init(final EntityManager entityManager) {
        final Notifications notitications = NotificationSpy.getInstance();
        final ServiceFactory factory = new ServiceFactory(entityManager, notitications);
        exchange = new ExchangeController(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchEmployerInformationResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerInformationRequest request) {
        return exchange.fetchEmployers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageFaculties(final AuthenticationToken token, final FacultyRequest request) {
        return exchange.manageFaculties(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FacultyResponse fetchFaculties(final AuthenticationToken token, final FetchFacultiesRequest request) {
        return exchange.fetchFaculties(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        return exchange.processOffer(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        return exchange.deleteOffer(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        return exchange.fetchOffers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageOfferTemplate(final AuthenticationToken token, final OfferTemplateRequest request) {
        return exchange.manageOfferTemplate(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        return exchange.fetchOfferTemplates(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible managePublishGroup(final AuthenticationToken token, final PublishGroupRequest request) {
        return exchange.managePublishGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishGroupResponse fetchPublishGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        return exchange.fetchPublishGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageStudent(final AuthenticationToken token, final StudentRequest request) {
        return exchange.manageStudent(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        return exchange.fetchStudents(token, request);
    }
}
