/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws) - net.iaeste.iws.ws.AdministrationWS
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

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AccountNameRequest;
import net.iaeste.iws.api.requests.ContactsRequest;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchRoleRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.OwnerRequest;
import net.iaeste.iws.api.requests.SearchUserRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.api.responses.ProcessUserGroupResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;
import net.iaeste.iws.ejb.AdministrationBean;
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
@WebService(name = "administrationWS", serviceName = "administrationWSService", portName = "administrationWS", targetNamespace = "http://ws.iws.iaeste.net/")
public class AdministrationWS implements Administration {

    private static final Logger LOG = LoggerFactory.getLogger(AdministrationWS.class);

    /**
     * Injection of the IWS Administration Bean Instance, which embeds the
     * Transactional logic and itself invokes the actual Implementation.
     */
    @Inject @IWSBean private Administration bean;

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
     * @param bean IWS Administration Bean instance
     */
    @WebMethod(exclude = true)
    public void setAdministrationBean(final AdministrationBean bean) {
        this.requestLogger = new RequestLogger(null);
        this.bean = bean;
    }

    // =========================================================================
    // Implementation of methods from Administration in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse processCountry(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final CountryRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processCountry"));
        FallibleResponse response;

        try {
            response = bean.processCountry(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
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
    public FetchCountryResponse fetchCountries(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchCountryRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchCountries"));
        FetchCountryResponse response;

        try {
            response = bean.fetchCountries(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchCountryResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public CreateUserResponse createUser(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final CreateUserRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "createUser"));
        CreateUserResponse response;

        try {
            response = bean.createUser(token, request);
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
    public FallibleResponse controlUserAccount(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final UserRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "controlUserAccount"));
        FallibleResponse response;

        try {
            response = bean.controlUserAccount(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
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
    public FallibleResponse activateUser(
            @WebParam(name = "activationString") final String activationString) {
        LOG.info(requestLogger.prepareLogMessage("activateUser"));
        FallibleResponse response;

        try {
            response = bean.activateUser(activationString);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
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
    public FallibleResponse updateUsername(
            @WebParam(name = "updateCode") final String updateCode) {
        LOG.info(requestLogger.prepareLogMessage("updateUsername"));
        FallibleResponse response;

        try {
            response = bean.updateUsername(updateCode);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
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
    public FallibleResponse changeAccountName(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final AccountNameRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "changeAccountName"));
        FallibleResponse response;

        try {
            response = bean.changeAccountName(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
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
    public FetchUserResponse fetchUser(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchUserRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchUser"));
        FetchUserResponse response;

        try {
            response = bean.fetchUser(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchUserResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchRoleResponse fetchRoles(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchRoleRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchRoles"));
        FetchRoleResponse response;

        try {
            response = bean.fetchRoles(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchRoleResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public ProcessGroupResponse processGroup(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final GroupRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processGroup"));
        ProcessGroupResponse response;

        try {
            response = bean.processGroup(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new ProcessGroupResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse deleteSubGroup(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final GroupRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "deleteSubGroup"));
        FallibleResponse response;

        try {
            response = bean.deleteSubGroup(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
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
    public FetchGroupResponse fetchGroup(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchGroupRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchGroup"));
        FetchGroupResponse response;

        try {
            response = bean.fetchGroup(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchGroupResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse changeGroupOwner(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final OwnerRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "changeGroupOwner"));
        FallibleResponse response;

        try {
            response = bean.changeGroupOwner(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
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
    public ProcessUserGroupResponse processUserGroupAssignment(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final UserGroupAssignmentRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processUserGroupAssignment"));
        ProcessUserGroupResponse response;

        try {
            response = bean.processUserGroupAssignment(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new ProcessUserGroupResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public SearchUserResponse searchUsers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final SearchUserRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "searchUsers"));
        SearchUserResponse response;

        try {
            response = bean.searchUsers(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new SearchUserResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public EmergencyListResponse fetchEmergencyList(
            @WebParam(name = "token") final AuthenticationToken token) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchEmergencyList"));
        EmergencyListResponse response;

        try {
            response = bean.fetchEmergencyList(token);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new EmergencyListResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public ContactsResponse fetchContacts(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final ContactsRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchContacts"));
        ContactsResponse response;

        try {
            response = bean.fetchContacts(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new ContactsResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }
}
