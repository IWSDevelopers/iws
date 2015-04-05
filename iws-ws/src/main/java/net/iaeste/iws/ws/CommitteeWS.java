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
@WebService(name = "committeeWS", serviceName = "committeeWSService", portName = "committeeWS", targetNamespace = "http://ws.iws.iaeste.net/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class CommitteeWS implements Committees {

    private static final Logger log = LoggerFactory.getLogger(CommitteeWS.class);

    /**
     * Injection of the IWS Committees Bean Instance, which embeds the
     * Transactional logic and itself invokes the actual Implemenation.
     */
    @Inject @IWSBean private Committees bean = null;

    /**
     * The WebService Context is only available for Classes, which is annotated
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
        log.info(requestLogger.prepareLogMessage(token, "fetchCommittees"));
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
        log.info(requestLogger.prepareLogMessage(token, "processCommittee"));
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
        log.info(requestLogger.prepareLogMessage(token, "fetchInternationalGroups"));
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
        log.info(requestLogger.prepareLogMessage(token, "processInternationalGroup"));
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
        log.info(requestLogger.prepareLogMessage(token, "fetchSurveyOfCountry"));
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
        log.info(requestLogger.prepareLogMessage(token, "processSurveyOfCountry"));
        return bean.processSurveyOfCountry(token, request);
    }
}
