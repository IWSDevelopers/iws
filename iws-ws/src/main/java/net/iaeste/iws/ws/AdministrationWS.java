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
import net.iaeste.iws.api.requests.RoleRequest;
import net.iaeste.iws.api.requests.SearchUserRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.api.responses.Response;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.responses.groupResponse;
import net.iaeste.iws.api.responses.RoleResponse;
import net.iaeste.iws.api.responses.UserGroupResponse;
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
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@SOAPBinding(style = SOAPBinding.Style.RPC)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING)
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
    @WebResult(name = "fallibleResponse")
    public Response processCountry(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "countryRequest") final CountryRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processCountry"));
        Response response;

        try {
            response = bean.processCountry(token, request);
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
    @WebResult(name = "fetchCountryResponse")
    public FetchCountryResponse fetchCountries(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "fetchCountryRequest") final FetchCountryRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchCountries"));
        FetchCountryResponse response;

        try {
            response = bean.fetchCountries(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchCountryResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "createUserResponse")
    public CreateUserResponse createUser(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "createUserRequest") final CreateUserRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "createUser"));
        CreateUserResponse response;

        try {
            response = bean.createUser(token, request);
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
    @WebResult(name = "fallibleResponse")
    public Response controlUserAccount(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "userRequest") final UserRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "controlUserAccount"));
        Response response;

        try {
            response = bean.controlUserAccount(token, request);
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
    @WebResult(name = "fallibleResponse")
    public Response activateUser(
            @WebParam(name = "activationString") final String activationString) {
        LOG.info(requestLogger.prepareLogMessage("activateUser"));
        Response response;

        try {
            response = bean.activateUser(activationString);
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
    @WebResult(name = "fallibleResponse")
    public Response updateUsername(
            @WebParam(name = "updateCode") final String updateCode) {
        LOG.info(requestLogger.prepareLogMessage("updateUsername"));
        Response response;

        try {
            response = bean.updateUsername(updateCode);
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
    @WebResult(name = "fallibleResponse")
    public Response changeAccountName(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "accountNameRequest") final AccountNameRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "changeAccountName"));
        Response response;

        try {
            response = bean.changeAccountName(token, request);
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
    @WebResult(name = "fetchUserResponse")
    public FetchUserResponse fetchUser(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "fetchUserRequest") final FetchUserRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchUser"));
        FetchUserResponse response;

        try {
            response = bean.fetchUser(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchUserResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fetchRoleResponse")
    public RoleResponse processRole(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "roleRequest") final RoleRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processRole"));
        RoleResponse response;

        try {
            response = bean.processRole(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, RoleResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fetchRoleResponse")
    public FetchRoleResponse fetchRoles(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "fetchRoleRequest") final FetchRoleRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchRoles"));
        FetchRoleResponse response;

        try {
            response = bean.fetchRoles(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchRoleResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "processGroupResponse")
    public groupResponse processGroup(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "groupRequest") final GroupRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processGroup"));
        groupResponse response;

        try {
            response = bean.processGroup(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, groupResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fallibleResponse")
    public Response deleteSubGroup(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "groupRequest") final GroupRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "deleteSubGroup"));
        Response response;

        try {
            response = bean.deleteSubGroup(token, request);
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
    @WebResult(name = "fetchGroupResponse")
    public FetchGroupResponse fetchGroup(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "fetchGroupRequest") final FetchGroupRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchGroup"));
        FetchGroupResponse response;

        try {
            response = bean.fetchGroup(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, FetchGroupResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "fallibleResponse")
    public Response changeGroupOwner(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "ownerRequest") final OwnerRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "changeGroupOwner"));
        Response response;

        try {
            response = bean.changeGroupOwner(token, request);
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
    @WebResult(name = "processUserGroupResponse")
    public UserGroupResponse processUserGroupAssignment(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "userGroupAssignmentRequest") final UserGroupAssignmentRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "processUserGroupAssignment"));
        UserGroupResponse response;

        try {
            response = bean.processUserGroupAssignment(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, UserGroupResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "searchUserResponse")
    public SearchUserResponse searchUsers(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "searchUserRequest") final SearchUserRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "searchUsers"));
        SearchUserResponse response;

        try {
            response = bean.searchUsers(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, SearchUserResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "emergencyListResponse")
    public EmergencyListResponse fetchEmergencyList(
            @WebParam(name = "authenticationToken") final AuthenticationToken token) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchEmergencyList"));
        EmergencyListResponse response;

        try {
            response = bean.fetchEmergencyList(token);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, EmergencyListResponse.class);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "contactsResponse")
    public ContactsResponse fetchContacts(
            @WebParam(name = "authenticationToken") final AuthenticationToken token,
            @WebParam(name = "contactsRequest") final ContactsRequest request) {
        LOG.info(requestLogger.prepareLogMessage(token, "fetchContacts"));
        ContactsResponse response;

        try {
            response = bean.fetchContacts(token, request);
        } catch (RuntimeException e) {
            response = RequestLogger.handleError(e, ContactsResponse.class);
        }

        return response;
    }
}
