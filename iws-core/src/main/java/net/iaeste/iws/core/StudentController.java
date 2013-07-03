/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.StudentController
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

import net.iaeste.iws.api.Student;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.exchange.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.core.services.StudentService;
import net.iaeste.iws.persistence.Authentication;
import org.apache.log4j.Logger;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StudentController extends CommonController implements Student {

    private static final Logger LOG = Logger.getLogger(StudentController.class);
    private final ServiceFactory factory;

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory The ServiceFactory
     */
    public StudentController(final ServiceFactory factory) {
        super(factory.getAccessDao());

        this.factory = factory;
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
    public StudentApplicationResponse processStudentApplication(final AuthenticationToken token, final ProcessStudentApplicationsRequest request) {
        LOG.trace("Starting processStudentApplication()");
        StudentApplicationResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_STUDENT_APPLICATION);
            verify(request);

            final StudentService service = factory.prepareStudentService();
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

            final StudentService service = factory.prepareStudentService();
            response = service.fetchStudentApplications(authentication, request);
        } catch (IWSException e) {
            response = new FetchStudentApplicationsResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchStudentApplications()");
        return response;
    }
}
