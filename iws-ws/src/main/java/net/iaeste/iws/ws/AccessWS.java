/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws) - net.iaeste.iws.ws.AccessWS
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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSErrors;
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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@SOAPBinding(style = SOAPBinding.Style.RPC)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@WebService(name = "accessWS", serviceName = "accessWSService", portName = "accessWS", targetNamespace = "http://ws.iws.iaeste.net/")
public class AccessWS implements Access {

    private static final Logger log = LoggerFactory.getLogger(AccessWS.class);

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
    @WebResult(name = "response")
    public AuthenticationResponse generateSession(
            @WebParam(name = "request") final AuthenticationRequest request) {
        log.info(requestLogger.prepareLogMessage("generateSession"));
        AuthenticationResponse response;

        try {
            response = bean.generateSession(request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new AuthenticationResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse requestResettingSession(
            @WebParam(name = "request") final AuthenticationRequest request) {
        log.info(requestLogger.prepareLogMessage("requestResettingSession"));
        FallibleResponse response;

        try {
            response = bean.requestResettingSession(request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FallibleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public AuthenticationResponse resetSession(
            @WebParam(name = "resetSessionToken") final String resetSessionToken) {
        log.info(requestLogger.prepareLogMessage("resetSession"));
        AuthenticationResponse response;

        try {
            response = bean.resetSession(resetSessionToken);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new AuthenticationResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public <T extends Serializable> FallibleResponse saveSessionData(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final SessionDataRequest<T> request) {
        log.info(requestLogger.prepareLogMessage(token, "saveSessionData"));
        FallibleResponse response;

        try {
            response = bean.saveSessionData(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FallibleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public <T extends Serializable> SessionDataResponse<T> readSessionData(
            @WebParam(name = "token") final AuthenticationToken token) {
        log.info(requestLogger.prepareLogMessage(token, "readSessionData"));
        SessionDataResponse<T> response;

        try {
            response = bean.readSessionData(token);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new SessionDataResponse<>(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse verifySession(
            @WebParam(name = "token") final AuthenticationToken token) {
        log.info(requestLogger.prepareLogMessage(token, "verifySession"));
        FallibleResponse response;

        try {
            response = bean.verifySession(token);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FallibleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse deprecateSession(
            @WebParam(name = "token") final AuthenticationToken token) {
        log.info(requestLogger.prepareLogMessage(token, "deprecateSession"));
        FallibleResponse response;

        try {
            response = bean.deprecateSession(token);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FallibleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse forgotPassword(
            @WebParam(name = "username") final String username) {
        log.info(requestLogger.prepareLogMessage("forgotPassword"));
        FallibleResponse response;

        try {
            response = bean.forgotPassword(username);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FallibleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse resetPassword(
            @WebParam(name = "resetPasswordToken") final String resetPasswordToken,
            @WebParam(name = "password") final Password password) {
        log.info(requestLogger.prepareLogMessage("resetPassword"));
        FallibleResponse response;

        try {
            response = bean.resetPassword(resetPasswordToken, password);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FallibleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse updatePassword(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "password") final Password password) {
        log.info(requestLogger.prepareLogMessage(token, "updatePassword"));
        FallibleResponse response;

        try {
            response = bean.updatePassword(token, password);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FallibleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchPermissionResponse fetchPermissions(
            @WebParam(name = "token") final AuthenticationToken token) {
        log.info(requestLogger.prepareLogMessage(token, "fetchPermissions"));
        FetchPermissionResponse response;

        try {
            response = bean.fetchPermissions(token);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            log.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchPermissionResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }
}
