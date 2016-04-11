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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.ejb.AccessBean;
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
import java.io.Serializable;

/**
 * <p>This is the IWS Access WebService (SOAP), which is used for the common
 * features regarding accessing the IWS. It deals with Session &amp; Permission
 * handling.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@SOAPBinding(style = SOAPBinding.Style.RPC)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING)
@WebService(name = "accessWS", serviceName = "accessWSService", portName = "accessWS", targetNamespace = "http://ws.iws.iaeste.net/")
public class AccessWS implements Access {

    private static final Logger LOG = LoggerFactory.getLogger(AccessWS.class);

    /**
     * Injection of the IWS Access Bean Instance, which embeds the Transactional
     * logic and itself invokes the actual Implementation.
     */
    @Inject @IWSBean private Access bean;

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
     * @param bean IWS Access Bean instance
     */
    @WebMethod(exclude = true)
    public void setAccessBean(final AccessBean bean) {
        this.requestLogger = new RequestLogger(null);
        this.bean = bean;
    }

    // =========================================================================
    // WebService implementation of the API Access interface
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "authenticationResponse")
    public AuthenticationResponse generateSession(
            @WebParam(name = "authenticationRequest") final AuthenticationRequest request) {
        // There's loads of request for the generateSession request, which is
        // one of the few requests which doesn't require a token. It can be
        // used in various attacks, so as a measure to see where it came from,
        // we're logging a bit more here.
        final String who = ((request != null) && (request.getUsername() != null)) ? (" for '" + request.getUsername() + "'.") : "";
        LOG.info("{}{}", requestLogger.prepareLogMessage("generateSession"), who);
        AuthenticationResponse response;

        try {
            response = bean.generateSession(request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, AuthenticationResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fallibleResponse")
    public FallibleResponse requestResettingSession(
            @WebParam(name = "authenticationRequest") final AuthenticationRequest request) {
        LOG.info(requestLogger.prepareLogMessage("requestResettingSession"));
        FallibleResponse response;

        try {
            response = bean.requestResettingSession(request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FallibleResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "authenticationResponse")
    public AuthenticationResponse resetSession(
            @WebParam(name = "resetSessionToken") final String resetSessionToken) {
        LOG.info(requestLogger.prepareLogMessage("resetSession"));
        AuthenticationResponse response;

        try {
            response = bean.resetSession(resetSessionToken);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, AuthenticationResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fallibleResponse")
    public <T extends Serializable> FallibleResponse saveSessionData(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "sessionDataRequest") final SessionDataRequest<T> request) {
        LOG.info(requestLogger.prepareLogMessage(token, "saveSessionData"));
        FallibleResponse response;

        try {
            response = bean.saveSessionData(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FallibleResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "sessionDataResponse")
    public <T extends Serializable> SessionDataResponse<T> readSessionData(
            @WebParam(name = "authenticationToken") final AuthenticationToken token) {
        LOG.info(requestLogger.prepareLogMessage(token, "readSessionData"));
        SessionDataResponse<T> response;

        try {
            response = bean.readSessionData(token);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, SessionDataResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fallibleResponse")
    public FallibleResponse verifySession(
            @WebParam(name = "authenticationToken") final AuthenticationToken token) {
        LOG.info(requestLogger.prepareLogMessage(token, "verifySession"));
        FallibleResponse response;

        try {
            response = bean.verifySession(token);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FallibleResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fallibleResponse")
    public FallibleResponse deprecateSession(
            @WebParam(name = "authenticationToken") final AuthenticationToken token) {
        LOG.info(requestLogger.prepareLogMessage(token, "deprecateSession"));
        FallibleResponse response;

        try {
            response = bean.deprecateSession(token);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FallibleResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fallibleResponse")
    public FallibleResponse forgotPassword(
            @WebParam(name = "username") final String username) {
        LOG.info(requestLogger.prepareLogMessage("forgotPassword"));
        FallibleResponse response;

        try {
            response = bean.forgotPassword(username);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FallibleResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fallibleResponse")
    public FallibleResponse resetPassword(
            @WebParam(name = "password") final Password password) {
        LOG.info(requestLogger.prepareLogMessage("resetPassword"));
        FallibleResponse response;

        try {
            response = bean.resetPassword(password);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FallibleResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fallibleResponse")
    public FallibleResponse updatePassword(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "password") final Password password) {
        LOG.info(requestLogger.prepareLogMessage(token, "updatePassword"));
        FallibleResponse response;

        try {
            response = bean.updatePassword(token, password);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FallibleResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fetchPermissionResponse")
    public FetchPermissionResponse fetchPermissions(
            @WebParam(name = "authenticationToken") final AuthenticationToken token) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchPermissions"));
        FetchPermissionResponse response;

        try {
            response = bean.fetchPermissions(token);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchPermissionResponse.class);
        }

        return response;
    }
}
