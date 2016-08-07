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

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.CountrySurveyRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchCountrySurveyRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.responses.CommitteeResponse;
import net.iaeste.iws.api.responses.Response;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchCountrySurveyResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
import net.iaeste.iws.ejb.CommitteeBean;
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
@WebService(name = "committeeWS", serviceName = "committeeWSService", portName = "committeeWS", targetNamespace = "http://ws.iws.iaeste.net/")
public class CommitteeWS implements Committees {

    private static final Logger LOG = LoggerFactory.getLogger(CommitteeWS.class);

    /**
     * Injection of the IWS Committees Bean Instance, which embeds the
     * Transactional logic and itself invokes the actual Implementation.
     */
    @Inject @IWSBean private Committees bean = null;

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
     * @param bean IWS Committee Bean instance
     */
    @WebMethod(exclude = true)
    public void setCommitteeBean(final CommitteeBean bean) {
        this.requestLogger = new RequestLogger(null);
        this.bean = bean;
    }

    // =========================================================================
    // Implementation of methods from Committees in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchCommitteeResponse fetchCommittees(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchCommitteeRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchCommittees"));
        FetchCommitteeResponse response;

        try {
            response = bean.fetchCommittees(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchCommitteeResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public CommitteeResponse processCommittee(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final CommitteeRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processCommittee"));
        CommitteeResponse response;

        try {
            response = bean.processCommittee(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, CommitteeResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchInternationalGroupResponse fetchInternationalGroups(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchInternationalGroupRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchInternationalGroups"));
        FetchInternationalGroupResponse response;

        try {
            response = bean.fetchInternationalGroups(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchInternationalGroupResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public Response processInternationalGroup(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final InternationalGroupRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processInternationalGroup"));
        Response response;

        try {
            response = bean.processInternationalGroup(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, Response.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchCountrySurveyResponse fetchCountrySurvey(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchCountrySurveyRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchCountrySurvey"));
        FetchCountrySurveyResponse response;

        try {
            response = bean.fetchCountrySurvey(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchCountrySurveyResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public Response processCountrySurvey(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final CountrySurveyRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processCountrySurvey"));
        Response response;

        try {
            response = bean.processCountrySurvey(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, Response.class);
        }

        return response;
    }
}
