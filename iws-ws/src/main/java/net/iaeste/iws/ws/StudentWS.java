/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.ws;

import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationsRequest;
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
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@SOAPBinding(style = SOAPBinding.Style.RPC)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING)
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
            response = RequestLogger.handleError(e, CreateUserResponse.class);
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
            response = RequestLogger.handleError(e, StudentResponse.class);
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
            response = RequestLogger.handleError(e, FetchStudentsResponse.class);
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
            @WebParam(name = "request") final StudentApplicationsRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processStudentApplication"));
        StudentApplicationResponse response;

        try {
            response = bean.processStudentApplication(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, StudentApplicationResponse.class);
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
            response = RequestLogger.handleError(e, FetchStudentApplicationsResponse.class);
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
            response = RequestLogger.handleError(e, StudentApplicationResponse.class);
        }

        return response;
    }
}
