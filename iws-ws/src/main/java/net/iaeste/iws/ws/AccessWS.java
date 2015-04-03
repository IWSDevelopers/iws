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

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.io.Serializable;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@WebService(name = "accessWS", serviceName = "accessWSService", portName = "accessWS", targetNamespace = "http://ws.iws.iaeste.net/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class AccessWS implements Access {

    private static final Logger log = LoggerFactory.getLogger(AccessWS.class);

    /**
     * Injection of the IWS Access Bean Instance, which embeds the Transactional
     * logic and itself invokes the actual Implemenation.
     */
    @Inject @IWSBean private Access bean = null;

    @Resource
    private WebServiceContext context;

    /**
     * Setter for the JNDI injected Bean context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param bean IWS Access Bean instance
     */
    @WebMethod(exclude = true)
    public void setAccessBean(final AccessBean bean) {
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
        log.info("Incoming 'generateSession' WebService Request from " + readClientIp());
        return bean.generateSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse requestResettingSession(
            @WebParam(name = "request") final AuthenticationRequest request) {
        log.info("Incoming 'requestResettingSession' WebService Request from " + readClientIp());
        return bean.requestResettingSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public AuthenticationResponse resetSession(
            @WebParam(name = "resetSessionToken") final String resetSessionToken) {
        log.info("Incoming 'resetSession' WebService Request from " + readClientIp());
        return bean.resetSession(resetSessionToken);
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
        log.info("Incoming 'saveSessionData' WebService Request from " + readClientIp());
        return bean.saveSessionData(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public <T extends Serializable> SessionDataResponse<T> readSessionData(
            @WebParam(name = "token") final AuthenticationToken token) {
        log.info("Incoming 'readSessionData' WebService Request from " + readClientIp());
        return bean.readSessionData(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse verifySession(
            @WebParam(name = "token") final AuthenticationToken token) {
        log.info("Incoming 'verifySession' WebService Request from " + readClientIp());
        return bean.verifySession(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse deprecateSession(
            @WebParam(name = "token") final AuthenticationToken token) {
        log.info("Incoming 'deprecateSession' WebService Request from " + readClientIp());
        return bean.deprecateSession(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse forgotPassword(
            @WebParam(name = "username") final String username) {
        log.info("Incoming 'forgotPassword' WebService Request from " + readClientIp());
        return bean.forgotPassword(username);
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
        log.info("Incoming 'resetPassword' WebService Request from " + readClientIp());
        return bean.resetPassword(resetPasswordToken, password);
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
        log.info("Incoming 'updatePassword' WebService Request from " + readClientIp());
        return bean.updatePassword(token, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchPermissionResponse fetchPermissions(
            @WebParam(name = "token") final AuthenticationToken token) {
        log.info("Incoming 'fetchPermissions' WebService Request from " + readClientIp());
        return bean.fetchPermissions(token);
    }

    // =========================================================================
    // Internal methods
    // =========================================================================

    /**
     * For logging purposes, we can read the IP address of the requesting
     * Client. This will help track usage, and also to see who is using what
     * features.
     *
     * @return Requesting Client IP Address
     */
    private String readClientIp() {
        final String servlet = MessageContext.SERVLET_REQUEST;
        final Object request = context.getMessageContext().get(servlet);

        return ((ServletRequest) request).getRemoteAddr();
    }
}
