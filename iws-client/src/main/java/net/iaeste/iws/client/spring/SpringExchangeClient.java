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
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;
import net.iaeste.iws.api.responses.StudentResponse;
import net.iaeste.iws.core.ExchangeController;
import net.iaeste.iws.core.services.ServiceFactory;
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

    @PersistenceContext
    private EntityManager entityManager;

    private final Object lock = new Object();
    private Exchange exchange = null;

    // =========================================================================
    // IWS API Exchange Functionality
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchEmployerInformationResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerInformationRequest request) {
        return getExchange().fetchEmployers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processFaculties(final AuthenticationToken token, final FacultyRequest request) {
        return getExchange().processFaculties(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FacultyResponse fetchFaculties(final AuthenticationToken token, final FetchFacultiesRequest request) {
        return getExchange().fetchFaculties(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        return getExchange().processOffer(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        return getExchange().deleteOffer(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        return getExchange().fetchOffers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processOfferTemplates(final AuthenticationToken token, final OfferTemplateRequest request) {
        return getExchange().processOfferTemplates(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        return getExchange().fetchOfferTemplates(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processPublishGroups(final AuthenticationToken token, final PublishGroupRequest request) {
        return getExchange().processPublishGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishGroupResponse fetchPublishGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        return getExchange().fetchPublishGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processStudents(final AuthenticationToken token, final StudentRequest request) {
        return getExchange().processStudents(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        return getExchange().fetchStudents(token, request);
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * Since Spring only performs the injection of resources after class
     * instantiation, we need a second place to actually create the Exchange
     * Controller instance that we wish to use for our communication with the
     * IWS. This is required to have a proper Transactional mechanism
     * surrounding the calls, so we don't have to worry about the current
     * state.<br />
     *   The method uses synchronization to create the instance, to ensure that
     * the creation of a new Instance is thread safe.
     *
     * @return Exchange Instance with Transactions
     */
    private Exchange getExchange() {
        synchronized (lock) {
            if (exchange == null) {
                final ServiceFactory factory = new ServiceFactory(entityManager);
                exchange = new ExchangeController(factory);
            }

            return exchange;
        }
    }
}
