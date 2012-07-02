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
import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.FacultyRequest;
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
import net.iaeste.iws.core.services.FacultyService;
import net.iaeste.iws.core.services.OfferService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.core.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.6
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
        this.factory = factory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processFaculties(final AuthenticationToken token, final FacultyRequest request) {
        LOG.trace("Starting processFaculties()");
        Fallible response;

        try {
            verifyAccess(token, Permission.PROCESS_FACULTIES);
            verify(request, "To be clarified.");

            final FacultyService service = factory.prepareFacultyService();
            service.processFaculties(token, request);
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
    public FacultyResponse findFaculties(final AuthenticationToken token, final FacultyRequest request) {
        LOG.trace("Starting findFaculties()");
        FacultyResponse response;

        try {
            verifyAccess(token, Permission.LOOKUP_FACULTIES);
            verify(request, "To be clarified.");

            final FacultyService service = factory.prepareFacultyService();
            response = service.findFaculties(token, request);
        } catch (IWSException e) {
            response = new FacultyResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished findFaculties()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processOffers(final AuthenticationToken token, final OfferRequest request) {
        LOG.trace("Starting processOffers()");
        Fallible response;

        try {
            verifyAccess(token, Permission.PROCESS_OFFERS);
            verify(request, "To be clarified.");

            final OfferService service = factory.prepareOfferService();
            service.processOffers(token, request);
            response = new OfferResponse();
        } catch (IWSException e) {
            response = new OfferResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processOffers()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse findOffers(final AuthenticationToken token, final OfferRequest request) {
        LOG.trace("Starting findOffers()");
        OfferResponse response;

        try {
            verifyAccess(token, Permission.LOOKUP_OFFERS);
            verify(request, "To be clarified.");

            final OfferService service = factory.prepareOfferService();
            response = service.findOffers(token, request);
        } catch (IWSException e) {
            response = new OfferResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished findOffers()");
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
            verifyAccess(token, Permission.PROCESS_OFFER_TEMPLATES);
            verify(request, "To be clarified.");

            final OfferService service = factory.prepareOfferService();
            service.processOfferTemplates(token, request);
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
    public OfferTemplateResponse findOfferTemplates(final AuthenticationToken token, final OfferTemplateRequest request) {
        LOG.trace("Starting findOfferTemplates()");
        OfferTemplateResponse response;

        try {
            verifyAccess(token, Permission.LOOKUP_OFFER_TEMPLATES);
            verify(request, "To be clarified.");

            final OfferService service = factory.prepareOfferService();
            response = service.findOfferTemplates(token, request);
        } catch (IWSException e) {
            response = new OfferTemplateResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished findOfferTemplates()");
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
            verifyAccess(token, Permission.PROCESS_OFFER_PUBLISH_GROUPS);
            verify(request, "To be clarified.");

            final OfferService service = factory.prepareOfferService();
            service.processPublishGroups(token, request);
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
    public PublishGroupResponse findPublishGroups(final AuthenticationToken token, final PublishGroupRequest request) {
        LOG.trace("Starting findPublishGroups()");
        PublishGroupResponse response;

        try {
            verifyAccess(token, Permission.LOOKUP_OFFER_PUBLISH_GROUPS);
            verify(request, "To be clarified.");

            final OfferService service = factory.prepareOfferService();
            response = service.findPublishGroups(token, request);
        } catch (IWSException e) {
            response = new PublishGroupResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished findPublishGroups()");
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
            verifyAccess(token, Permission.PROCESS_STUDENTS);
            verify(request, "To be clarified.");

            final StudentService service = factory.prepareStudentService();
            service.processStudents(token, request);
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
    public StudentResponse findStudents(final AuthenticationToken token, final StudentRequest request) {
        LOG.trace("Starting findStudents()");
        StudentResponse response;

        try {
            verifyAccess(token, Permission.LOOKUP_STUDENTS);
            verify(request, "To be clarified.");

            final StudentService service = factory.prepareStudentService();
            response = service.findStudents(token, request);
        } catch (IWSException e) {
            response = new StudentResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished findStudents()");
        return response;
    }
}
