/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static net.iaeste.iws.api.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.student.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationRequest;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.responses.student.StudentResponse;
import net.iaeste.iws.core.services.AccountService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.core.services.StudentService;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StudentController extends CommonController implements Students {

    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory The ServiceFactory
     */
    public StudentController(final ServiceFactory factory) {
        super(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateUserResponse createStudent(final AuthenticationToken token, final CreateUserRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting createStudent()"));
        }
        CreateUserResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.CREATE_STUDENT_ACCOUNT);

            final AccountService service = factory.prepareAccountService();
            response = service.createStudent(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new CreateUserResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished createStudent()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentResponse processStudent(final AuthenticationToken token, final StudentRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting processStudent()"));
        }
        StudentResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_STUDENT);

            final StudentService service = factory.prepareStudentService();
            response = service.processStudent(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new StudentResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished processStudent()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchStudentsResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting fetchStudents()"));
        }
        FetchStudentsResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.FETCH_STUDENTS);

            final StudentService service = factory.prepareStudentService();
            response = service.fetchStudents(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchStudentsResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished fetchStudents()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentApplicationResponse processStudentApplication(final AuthenticationToken token, final ProcessStudentApplicationsRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting processStudentApplication()"));
        }
        StudentApplicationResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_STUDENT_APPLICATION);

            final StudentService service = factory.prepareStudentService();
            response = service.processStudentApplication(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new StudentApplicationResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished processStudentApplication()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchStudentApplicationsResponse fetchStudentApplications(final AuthenticationToken token, final FetchStudentApplicationsRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting fetchStudentApplications()"));
        }
        FetchStudentApplicationsResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.FETCH_STUDENT_APPLICATION);

            final StudentService service = factory.prepareStudentService();
            response = service.fetchStudentApplications(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchStudentApplicationsResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished fetchStudentApplications()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentApplicationResponse processApplicationStatus(final AuthenticationToken token, final StudentApplicationRequest request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Starting processApplicationStatus()"));
        }
        StudentApplicationResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_STUDENT_APPLICATION);

            final StudentService service = factory.prepareStudentService();
            response = service.processApplicationStatus(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new StudentApplicationResponse(e.getError(), e.getMessage());
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace(formatLogMessage(token, "Finished processApplicationStatus()"));
        }
        return response;
    }
}
