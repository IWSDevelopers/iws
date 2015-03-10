/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@WebService(name = "studentWS", serviceName = "studentWSService", portName = "studentWS", targetNamespace = "http://ws.iws.iaeste.net/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class StudentWS implements Students {

    /**
     * Injection of the IWS Students Bean Instance, which embeds the
     * Transactional logic and itself invokes the actual Implemenation.
     */
    @Inject @IWSBean private Students bean = null;

    /**
     * Setter for the JNDI injected Bean context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param bean IWS Student Bean instance
     */
    @WebMethod(exclude = true)
    public void setStudentBean(final StudentBean bean) {
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
        return bean.createStudent(token, request);
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
        return bean.processStudent(token, request);
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
        return bean.fetchStudents(token, request);
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
        return bean.processStudentApplication(token, request);
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
        return bean.fetchStudentApplications(token, request);
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
        return bean.processApplicationStatus(token, request);
    }
}
