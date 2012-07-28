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
import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.requests.FacultyRequest;
import net.iaeste.iws.api.requests.FetchFacultiesRequest;
import net.iaeste.iws.api.requests.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.FetchStudentsRequest;
import net.iaeste.iws.api.requests.OfferRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.requests.StudentRequest;
import net.iaeste.iws.api.responses.FacultyResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;
import net.iaeste.iws.api.responses.StudentResponse;
import net.iaeste.iws.core.ExchangeController;
import net.iaeste.iws.core.services.ServiceFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {Config.class})
public class SpringExchangeClient implements Exchange {

    @PersistenceContext
    private EntityManager entityManager;
    private final Exchange exchange;

    /**
     * Default Constructor, initializes the Core Service Factory with the Spring
     * based EntityManager instance.
     */
    public SpringExchangeClient() {
        final ServiceFactory factory = new ServiceFactory(entityManager);
        exchange = new ExchangeController(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Fallible processFaculties(final AuthenticationToken token, final FacultyRequest request) {
        return exchange.processFaculties(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public FacultyResponse fetchFaculties(final AuthenticationToken token, final FetchFacultiesRequest request) {
        return exchange.fetchFaculties(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Fallible processOffers(final AuthenticationToken token, final OfferRequest request) {
        return exchange.processOffers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OfferResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        return exchange.fetchOffers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Fallible processOfferTemplates(final AuthenticationToken token, final OfferTemplateRequest request) {
        return exchange.processOfferTemplates(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        return exchange.fetchOfferTemplates(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Fallible processPublishGroups(final AuthenticationToken token, final PublishGroupRequest request) {
        return exchange.processPublishGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public PublishGroupResponse fetchPublishGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        return exchange.fetchPublishGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Fallible processStudents(final AuthenticationToken token, final StudentRequest request) {
        return exchange.processStudents(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public StudentResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        return exchange.fetchStudents(token, request);
    }
}
