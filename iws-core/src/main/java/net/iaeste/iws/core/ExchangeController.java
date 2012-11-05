/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
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
import net.iaeste.iws.core.services.ExchangeService;
import net.iaeste.iws.core.services.FacultyService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.core.services.StudentService;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class ExchangeController extends CommonController implements Exchange {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeController.class);
    private final ServiceFactory factory;

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory The ServiceFactory
     */
    public ExchangeController(final ServiceFactory factory) {
        super(factory.getAccessDao());

        this.factory = factory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchEmployerInformationResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerInformationRequest request) {
        LOG.trace("Starting fetchFaculties()");
        FetchEmployerInformationResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_OFFERS);
            verify(request, "To be clarified.");

            final ExchangeService service = factory.prepareOfferService();
            response = service.fetchEmployers(authentication, request);
        } catch (IWSException e) {
            response = new FetchEmployerInformationResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchEmployers()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processFaculties(final AuthenticationToken token, final FacultyRequest request) {
        LOG.trace("Starting processFaculties()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_FACULTIES);
            verify(request, "To be clarified.");

            final FacultyService service = factory.prepareFacultyService();
            service.processFaculties(authentication, request);
            response = new FacultyResponse();
        } catch (IWSException e) {
            response = new FacultyResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processFaculties()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FacultyResponse fetchFaculties(final AuthenticationToken token, final FetchFacultiesRequest request) {
        LOG.trace("Starting fetchFaculties()");
        FacultyResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_FACULTIES);
            verify(request, "To be clarified.");

            final FacultyService service = factory.prepareFacultyService();
            response = service.fetchFaculties(authentication, request);
        } catch (IWSException e) {
            response = new FacultyResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchFaculties()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        LOG.trace("Starting processOffer()");
        OfferResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.MANAGE_OFFERS);
            verify(request, "To be clarified.");

            final ExchangeService service = factory.prepareOfferService();
            response = service.manageOffer(authentication, request);
        } catch (IWSException e) {
            response = new OfferResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processOffer()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        LOG.trace("Starting deleteOffer()");
        OfferResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.MANAGE_OFFERS);
            verify(request, "To be clarified.");

            final ExchangeService service = factory.prepareOfferService();
            service.deleteOffer(authentication, request);
            response = new OfferResponse();
        } catch (IWSException e) {
            response = new OfferResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processOffer()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        LOG.trace("Starting fetchOffers()");
        FetchOffersResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_OFFERS);
            verify(request, "To be clarified.");

            final ExchangeService service = factory.prepareOfferService();
            response = service.fetchOffers(authentication, request);
        } catch (IWSException e) {
            response = new FetchOffersResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchOffers()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processOfferTemplates(final AuthenticationToken token, final OfferTemplateRequest request) {
        LOG.trace("Starting processOfferTemplates()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_OFFER_TEMPLATES);
            verify(request, "To be clarified.");

            final ExchangeService service = factory.prepareOfferService();
            service.processOfferTemplates(authentication, request);
            response = new OfferTemplateResponse();
        } catch (IWSException e) {
            response = new OfferTemplateResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processOfferTemplates()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        LOG.trace("Starting fetchOfferTemplates()");
        OfferTemplateResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_OFFER_TEMPLATES);
            verify(request, "To be clarified.");

            final ExchangeService service = factory.prepareOfferService();
            response = service.fetchOfferTemplates(authentication, request);
        } catch (IWSException e) {
            response = new OfferTemplateResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchOfferTemplates()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processPublishGroups(final AuthenticationToken token, final PublishGroupRequest request) {
        LOG.trace("Starting processPublishGroups()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_OFFER_PUBLISH_GROUPS);
            verify(request, "To be clarified.");

            final ExchangeService service = factory.prepareOfferService();
            service.processPublishGroups(authentication, request);
            response = new PublishGroupResponse();
        } catch (IWSException e) {
            response = new PublishGroupResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processPublishGroups()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishGroupResponse fetchPublishGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        LOG.trace("Starting fetchPublishGroups()");
        PublishGroupResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_OFFER_PUBLISH_GROUPS);
            verify(request, "To be clarified.");

            final ExchangeService service = factory.prepareOfferService();
            response = service.fetchPublishGroups(authentication, request);
        } catch (IWSException e) {
            response = new PublishGroupResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchPublishGroups()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processStudents(final AuthenticationToken token, final StudentRequest request) {
        LOG.trace("Starting processStudents()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_STUDENTS);
            verify(request, "To be clarified.");

            final StudentService service = factory.prepareStudentService();
            service.processStudents(authentication, request);
            response = new StudentResponse();
        } catch (IWSException e) {
            response = new StudentResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processStudents()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        LOG.trace("Starting fetchStudents()");
        StudentResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_STUDENTS);
            verify(request, "To be clarified.");

            final StudentService service = factory.prepareStudentService();
            response = service.fetchStudents(authentication, request);
        } catch (IWSException e) {
            response = new StudentResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchStudents()");
        return response;
    }
}
