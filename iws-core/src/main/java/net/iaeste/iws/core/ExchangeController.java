/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.requests.exchange.*;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.exchange.*;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.util.Fallible;
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
 * @noinspection OverlyCoupledClass
 */
public final class ExchangeController extends CommonController implements Exchange {

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
            verify(request);

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
    @Deprecated
    public Fallible manageFaculties(final AuthenticationToken token, final FacultyRequest request) {
        LOG.trace("Starting manageFaculties()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_FACULTIES);
            verify(request);

            final FacultyService service = factory.prepareFacultyService();
            service.processFaculties(authentication, request);
            response = new FetchFacultyResponse();
        } catch (IWSException e) {
            response = new FetchFacultyResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished manageFaculties()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public FetchFacultyResponse fetchFaculties(final AuthenticationToken token, final FetchFacultiesRequest request) {
        LOG.trace("Starting fetchFaculties()");
        FetchFacultyResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_FACULTIES);
            verify(request);

            final FacultyService service = factory.prepareFacultyService();
            response = service.fetchFaculties(authentication, request);
        } catch (IWSException e) {
            response = new FetchFacultyResponse(e.getError(), e.getMessage());
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
            verify(request);

            final ExchangeService service = factory.prepareOfferService();
            response = service.processOffer(authentication, request);
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
            verify(request);

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
            verify(request);

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
    public Fallible processOfferTemplate(final AuthenticationToken token, final OfferTemplateRequest request) {
        LOG.trace("Starting processOfferTemplate()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_OFFER_TEMPLATES);
            verify(request);

            final ExchangeService service = factory.prepareOfferService();
            service.processOfferTemplates(authentication, request);
            response = new FetchOfferTemplateResponse();
        } catch (IWSException e) {
            response = new FetchOfferTemplateResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processOfferTemplate()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        LOG.trace("Starting fetchOfferTemplates()");
        FetchOfferTemplateResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_OFFER_TEMPLATES);
            verify(request);

            final ExchangeService service = factory.prepareOfferService();
            response = service.fetchOfferTemplates(authentication, request);
        } catch (IWSException e) {
            response = new FetchOfferTemplateResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchOfferTemplates()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible managePublishGroup(final AuthenticationToken token, final PublishGroupRequest request) {
        LOG.trace("Starting managePublishGroup()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_OFFER_PUBLISH_GROUPS);
            verify(request);

            final ExchangeService service = factory.prepareOfferService();
            service.processPublishGroups(authentication, request);
            response = new FetchPublishGroupResponse();
        } catch (IWSException e) {
            response = new FetchPublishGroupResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished managePublishGroup()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishGroupResponse fetchPublishGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        LOG.trace("Starting fetchPublishGroups()");
        FetchPublishGroupResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_OFFER_PUBLISH_GROUPS);
            verify(request);

            final ExchangeService service = factory.prepareOfferService();
            response = service.fetchPublishGroups(authentication, request);
        } catch (IWSException e) {
            response = new FetchPublishGroupResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchPublishGroups()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processStudent(final AuthenticationToken token, final StudentRequest request) {
        LOG.trace("Starting processStudent()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_STUDENTS);
            verify(request);

            final StudentService service = factory.prepareStudentService();
            service.processStudents(authentication, request);
            response = new FetchStudentResponse();
        } catch (IWSException e) {
            response = new FetchStudentResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processStudent()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchStudentResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        LOG.trace("Starting fetchStudents()");
        FetchStudentResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_STUDENTS);
            verify(request);

            final StudentService service = factory.prepareStudentService();
            response = service.fetchStudents(authentication, request);
        } catch (IWSException e) {
            response = new FetchStudentResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchStudents()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupsForSharingResponse fetchGroupsForSharing(AuthenticationToken token, FetchGroupsForSharingRequest request) {
        LOG.trace("Starting fetchNationalGroups()");
        FetchGroupsForSharingResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_GROUPS);
            verify(request);

            final ExchangeService service = factory.prepareOfferService();
            response = service.fetchGroupsForSharing(authentication, request);
        } catch (IWSException e) {
            response = new FetchGroupsForSharingResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchNationalGroups()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishOfferResponse processPublishOffer(final AuthenticationToken token, final PublishOfferRequest request) {
        LOG.trace("Starting processPublishOffer()");
        PublishOfferResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_PUBLISH_OFFER);
            verify(request);

            final ExchangeService service = factory.prepareOfferService();
            service.processPublishOffer(authentication, request);
            response = new PublishOfferResponse();
        } catch (IWSException e) {
            response = new PublishOfferResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processPublishOffer()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishOfferResponse fetchPublishedOffer(final AuthenticationToken token, final FetchPublishOfferRequest request) {
        LOG.trace("Starting fetchPublishedOffer()");
        FetchPublishOfferResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_PUBLISH_OFFER);
            verify(request);

            final ExchangeService service = factory.prepareOfferService();
            response = service.fetchPublishedOfferInfo(authentication, request);
        } catch (IWSException e) {
            response = new FetchPublishOfferResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchPublishedOffer()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentApplicationResponse processStudentApplication(final AuthenticationToken token, final ProcessStudentApplicationsRequest request) {
        LOG.trace("Starting processStudentApplication()");
        StudentApplicationResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_STUDENT_APPLICATION);
            verify(request);

            final ExchangeService service = factory.prepareOfferService();
            response = service.processStudentApplication(authentication, request);
        } catch (IWSException e) {
            response = new StudentApplicationResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processStudentApplication()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchStudentApplicationsResponse fetchStudentApplications(final AuthenticationToken token, final FetchStudentApplicationsRequest request) {
        LOG.trace("Starting fetchStudentApplications()");
        FetchStudentApplicationsResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.LOOKUP_STUDENT_APPLICATION);
            verify(request);

            final ExchangeService service = factory.prepareOfferService();
            response = service.fetchStudentApplications(authentication, request);
        } catch (IWSException e) {
            response = new FetchStudentApplicationsResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchStudentApplications()");
        return response;
    }
}
