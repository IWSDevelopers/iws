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

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.Serializable;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@WebService(name = "accessWS", serviceName = "accessWSService", portName = "accessWS", targetNamespace = "http://ws.iws.iaeste.net/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class AccessWS implements Access {

    @EJB(beanInterface = Access.class, lookup = "java:global/iws/iws-ejb/AccessBean!net.iaeste.iws.api.Access")
    //@Inject @IWSBean
    private Access bean = null;

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
    // Implementation of methods from Access in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public AuthenticationResponse generateSession(
            @WebParam(name = "request") final AuthenticationRequest request) {
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
        return bean.fetchPermissions(token);
    }
}