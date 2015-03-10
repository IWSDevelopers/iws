/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.ejb.AdministrationBean;
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
@WebService(name = "administrationWS", serviceName = "administrationWSService", portName = "administrationWS", targetNamespace = "http://ws.iws.iaeste.net/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class AdministrationWS implements Administration {

    /**
     * Injection of the IWS Administration Bean Instance, which embeds the
     * Transactional logic and itself invokes the actual Implemenation.
     */
    @Inject @IWSBean private Administration bean = null;

    /**
     * Setter for the JNDI injected Bean context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param bean IWS Administration Bean instance
     */
    @WebMethod(exclude = true)
    public void setAdministrationBean(final AdministrationBean bean) {
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
        return bean.processCountry(token, request);
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
        return bean.fetchCountries(token, request);
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
        return bean.createUser(token, request);
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
        return bean.controlUserAccount(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse activateUser(
            @WebParam(name = "activationString") final String activationString) {
        return bean.activateUser(activationString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse updateUsername(
            @WebParam(name = "updateCode") final String updateCode) {
        return bean.updateUsername(updateCode);
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
        return bean.changeAccountName(token, request);
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
        return bean.fetchUser(token, request);
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
        return bean.fetchRoles(token, request);
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
        return bean.processGroup(token, request);
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
        return bean.deleteSubGroup(token, request);
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
        return bean.fetchGroup(token, request);
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
        return bean.changeGroupOwner(token, request);
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
        return bean.processUserGroupAssignment(token, request);
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
        return bean.searchUsers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public EmergencyListResponse fetchEmergencyList(
            @WebParam(name = "token") final AuthenticationToken token) {
        return bean.fetchEmergencyList(token);
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
        return bean.fetchContacts(token, request);
    }
}
