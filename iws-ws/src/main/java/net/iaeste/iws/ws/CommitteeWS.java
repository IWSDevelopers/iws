/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws) - net.iaeste.iws.ws.CommitteeWS
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

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.FetchSurveyOfCountryRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.SurveyOfCountryRequest;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
import net.iaeste.iws.api.responses.FetchSurveyOfCountryRespose;
import net.iaeste.iws.ejb.CommitteeBean;
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
@WebService(name = "committeeWS", serviceName = "committeeWSService", portName = "committeeWS", targetNamespace = "http://ws.iws.iaeste.net/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class CommitteeWS implements Committees {

    /**
     * Injection of the IWS Committees Bean Instance, which embeds the
     * Transactional logic and itself invokes the actual Implemenation.
     */
    @Inject @IWSBean private Committees bean = null;

    /**
     * Setter for the JNDI injected Bean context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param bean IWS Committee Bean instance
     */
    @WebMethod(exclude = true)
    public void setCommitteeBean(final CommitteeBean bean) {
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
        return bean.fetchCommittees(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse processCommittee(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final CommitteeRequest request) {
        return bean.processCommittee(token, request);
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
        return bean.fetchInternationalGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse processInternationalGroup(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final InternationalGroupRequest request) {
        return bean.processInternationalGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchSurveyOfCountryRespose fetchSurveyOfCountry(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchSurveyOfCountryRequest request) {
        return bean.fetchSurveyOfCountry(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse processSurveyOfCountry(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final SurveyOfCountryRequest request) {
        return bean.processSurveyOfCountry(token, request);
    }
}
