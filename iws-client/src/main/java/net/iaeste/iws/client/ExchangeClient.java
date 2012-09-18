/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ExchangeClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client;

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
import net.iaeste.iws.api.responses.FetchEmployersResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;
import net.iaeste.iws.api.responses.StudentResponse;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class ExchangeClient implements Exchange {

    private final Exchange exchange;

    /**
     * Default Constructor.
     */
    public ExchangeClient() {
        final ConnectionFactory factory = new ConnectionFactory();
        exchange = factory.getExchangeImplementation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchEmployersResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerInformationRequest request) {
        return exchange.fetchEmployers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processFaculties(final AuthenticationToken token, final FacultyRequest request) {
        return exchange.processFaculties(token, request);
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
        // TODO: remove if transactions work
        ConnectionFactory factory = new ConnectionFactory();
        factory.getEntityManager().getTransaction().begin();
        OfferResponse response = exchange.processOffer(token, request);
        factory.getEntityManager().getTransaction().commit();
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
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
    public Fallible processOfferTemplates(final AuthenticationToken token, final OfferTemplateRequest request) {
        return exchange.processOfferTemplates(token, request);
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
    public Fallible processPublishGroups(final AuthenticationToken token, final PublishGroupRequest request) {
        return exchange.processPublishGroups(token, request);
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
    public Fallible processStudents(final AuthenticationToken token, final StudentRequest request) {
        return exchange.processStudents(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        return exchange.fetchStudents(token, request);
    }
}
