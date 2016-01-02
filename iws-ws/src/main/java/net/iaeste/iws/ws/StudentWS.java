/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws) - net.iaeste.iws.ws.StudentWS
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ws;

import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationRequest;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.responses.student.StudentResponse;
import net.iaeste.iws.ejb.StudentBean;
import net.iaeste.iws.ejb.cdi.IWSBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@SOAPBinding(style = SOAPBinding.Style.RPC)
//@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@WebService(name = "studentWS", serviceName = "studentWSService", portName = "studentWS", targetNamespace = "http://ws.iws.iaeste.net/")
public class StudentWS implements Students {

    private static final Logger LOG = LoggerFactory.getLogger(StudentWS.class);

    /**
     * Injection of the IWS Students Bean Instance, which embeds the
     * Transactional logic and itself invokes the actual Implementation.
     */
    @Inject @IWSBean private Students bean = null;

    /**
     * The WebService Context is only available for Classes, which are annotated
     * with @WebService. So, we need it injected and then in the PostConstruct
     * method, we can create a new RequestLogger instance with it.
     */
    @Resource
    private WebServiceContext context = null;

    private RequestLogger requestLogger = null;

    /**
     * Post Construct method, to initialize our Request Logger instance.
     */
    @PostConstruct
    @WebMethod(exclude = true)
    public void postConstruct() {
        requestLogger = new RequestLogger(context);
    }

    /**
     * Setter for the JNDI injected Bean context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param bean IWS Student Bean instance
     */
    @WebMethod(exclude = true)
    public void setStudentBean(final StudentBean bean) {
        this.requestLogger = new RequestLogger(null);
        this.bean = bean;
    }

    // =========================================================================
    // Implementation of methods from Students in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public CreateUserResponse createStudent(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final CreateUserRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "createStudent"));
        CreateUserResponse response;

        try {
            response = bean.createStudent(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new CreateUserResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public StudentResponse processStudent(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final StudentRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processStudent"));
        StudentResponse response;

        try {
            response = bean.processStudent(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new StudentResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchStudentsResponse fetchStudents(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchStudentsRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchStudents"));
        FetchStudentsResponse response;

        try {
            response = bean.fetchStudents(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchStudentsResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public StudentApplicationResponse processStudentApplication(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final ProcessStudentApplicationsRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processStudentApplication"));
        StudentApplicationResponse response;

        try {
            response = bean.processStudentApplication(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new StudentApplicationResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchStudentApplicationsResponse fetchStudentApplications(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchStudentApplicationsRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchStudentApplications"));
        FetchStudentApplicationsResponse response;

        try {
            response = bean.fetchStudentApplications(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchStudentApplicationsResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public StudentApplicationResponse processApplicationStatus(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final StudentApplicationRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processApplicationStatus"));
        StudentApplicationResponse response;

        try {
            response = bean.processApplicationStatus(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new StudentApplicationResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }
}
