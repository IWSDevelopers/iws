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
package net.iaeste.iws.ejb;

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
import net.iaeste.iws.api.responses.GroupResponse;
import net.iaeste.iws.api.responses.RoleResponse;
import net.iaeste.iws.api.responses.UserGroupResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.AdministrationController;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.ejb.cdi.IWSBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Administration Bean, serves as the default EJB for the IWS Administration
 * interface. It uses JDNI instances for the Persistence Context and the
 * Notification Manager Bean.<br />
 *   The default implemenentation will catch any uncaught Exception. However,
 * there are some types of Exceptions that should be handled by the Contained,
 * and not by our error handling. Thus, only Runtime exceptions are caught. If
 * a Checked Exception is discovered that also needs our attention, then the
 * error handling must be extended to also deal with this. But for now, this
 * should suffice.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Stateless
@Remote(Administration.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdministrationBean implements Administration {

    private static final Logger LOG = LoggerFactory.getLogger(AdministrationBean.class);
    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Notifications notifications;
    @Inject @IWSBean private SessionRequestBean session;
    @Inject @IWSBean private Settings settings;
    private Administration controller = null;

    /**
     * Setter for the JNDI injected persistence context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param entityManager Transactional Entity Manager instance
     */
    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Setter for the JNDI injected notification bean. This allows us to also
     * test the code, by invoking these setters on the instantited Object.
     *
     * @param notificationManager Notification Manager Bean
     */
    public void setNotificationManager(final NotificationManager notificationManager) {
        this.notifications = notificationManager;
    }

    /**
     * Setter for the JNDI injected Session Request bean. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param sessionRequestBean Session Request Bean
     */
    public void setSessionRequestBean(final SessionRequestBean sessionRequestBean) {
        this.session = sessionRequestBean;
    }

    /**
     * Setter for the JNDI injected Settings bean. This allows us to also test
     * the code, by invoking these setters on the instantiated Object.
     *
     * @param settings Settings Bean
     */
    public void setSettings(final Settings settings) {
        this.settings = settings;
    }

    @PostConstruct
    public void postConstruct() {
        final ServiceFactory factory = new ServiceFactory(entityManager, notifications, settings);
        controller = new AdministrationController(factory);
    }

    // =========================================================================
    // Implementation of methods from Administration in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Response processCountry(final AuthenticationToken token, final CountryRequest request) {
        final long start = System.nanoTime();
        Response response;

        try {
            response = controller.processCountry(token, request);
            LOG.info(session.generateLogAndUpdateSession("processCountry", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("processCountry", start, e, token, request), e);
            response = new Response(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchCountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        final long start = System.nanoTime();
        FetchCountryResponse response;

        try {
            response = controller.fetchCountries(token, request);
            LOG.info(session.generateLogAndUpdateSession("fetchCountries", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchCountries", start, e, token, request), e);
            response = new FetchCountryResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateUserResponse createUser(final AuthenticationToken token, final CreateUserRequest request) {
        final long start = System.nanoTime();
        CreateUserResponse response;

        try {
            response = controller.createUser(token, request);
            LOG.info(session.generateLogAndUpdateSession("createUser", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("createUser", start, e, token, request), e);
            response = new CreateUserResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response activateUser(final String activationString) {
        final long start = System.nanoTime();
        Response response;

        try {
            response = controller.activateUser(activationString);
            LOG.info(session.generateLog("activateUser", start, response));
        } catch (RuntimeException e) {
            LOG.error(session.generateLog("activateUser", start, e), e);
            response = new Response(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response updateUsername(final String updateCode) {
        final long start = System.nanoTime();
        Response response;

        try {
            response = controller.updateUsername(updateCode);
            LOG.info(session.generateLog("updateUsername", start, response));
        } catch (RuntimeException e) {
            LOG.error(session.generateLog("updateUsername", start, e), e);
            response = new Response(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response controlUserAccount(final AuthenticationToken token, final UserRequest request) {
        final long start = System.nanoTime();
        Response response;

        try {
            response = controller.controlUserAccount(token, request);
            LOG.info(session.generateLogAndUpdateSession("controlUserAccount", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("controlUserAccount", start, e, token, request), e);
            response = new Response(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response changeAccountName(final AuthenticationToken token, final AccountNameRequest request) {
        final long start = System.nanoTime();
        Response response;

        try {
            response = controller.changeAccountName(token, request);
            LOG.info(session.generateLogAndUpdateSession("changeAccountName", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("changeAccountName", start, e, token, request), e);
            response = new Response(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchUserResponse fetchUser(final AuthenticationToken token, final FetchUserRequest request) {
        final long start = System.nanoTime();
        FetchUserResponse response;

        try {
            response = controller.fetchUser(token, request);
            LOG.info(session.generateLogAndUpdateSession("fetchUser", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchUser", start, e, token, request), e);
            response = new FetchUserResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public RoleResponse processRole(final AuthenticationToken token, final RoleRequest request) {
        final long start = System.nanoTime();
        RoleResponse response;

        try {
            response = controller.processRole(token, request);
            LOG.info(session.generateLogAndUpdateSession("processRole", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("processRole", start, e, token, request), e);
            response = new RoleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchRoleResponse fetchRoles(final AuthenticationToken token, final FetchRoleRequest request) {
        final long start = System.nanoTime();
        FetchRoleResponse response;

        try {
            response = controller.fetchRoles(token, request);
            LOG.info(session.generateLogAndUpdateSession("fetchRoles", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchRoles", start, e, token, request), e);
            response = new FetchRoleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupResponse processGroup(final AuthenticationToken token, final GroupRequest request) {
        final long start = System.nanoTime();
        GroupResponse response;

        try {
            response = controller.processGroup(token, request);
            LOG.info(session.generateLogAndUpdateSession("processGroup", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("processGroup", start, e, token, request), e);
            response = new GroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response deleteSubGroup(final AuthenticationToken token, final GroupRequest request) {
        final long start = System.nanoTime();
        Response response;

        try {
            response = controller.deleteSubGroup(token, request);
            LOG.info(session.generateLogAndUpdateSession("deleteSubGroup", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("deleteSubGroup", start, e, token, request), e);
            response = new Response(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchGroupResponse fetchGroup(final AuthenticationToken token, final FetchGroupRequest request) {
        final long start = System.nanoTime();
        FetchGroupResponse response;

        try {
            response = controller.fetchGroup(token, request);
            LOG.info(session.generateLogAndUpdateSession("fetchGroup", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchGroup", start, e, token, request), e);
            response = new FetchGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response changeGroupOwner(final AuthenticationToken token, final OwnerRequest request) {
        final long start = System.nanoTime();
        Response response;

        try {
            response = controller.changeGroupOwner(token, request);
            LOG.info(session.generateLogAndUpdateSession("changeGroupOwner", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("changeGroupOwner", start, e, token, request), e);
            response = new Response(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupResponse processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        final long start = System.nanoTime();
        UserGroupResponse response;

        try {
            response = controller.processUserGroupAssignment(token, request);
            LOG.info(session.generateLogAndUpdateSession("processUserGroupAssignment", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("processUserGroupAssignment", start, e, token, request), e);
            response = new UserGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SearchUserResponse searchUsers(final AuthenticationToken token, final SearchUserRequest request) {
        final long start = System.nanoTime();
        SearchUserResponse response;

        try {
            response = controller.searchUsers(token, request);
            LOG.info(session.generateLogAndUpdateSession("searchUsers", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("searchUsers", start, e, token, request), e);
            response = new SearchUserResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public EmergencyListResponse fetchEmergencyList(final AuthenticationToken token) {
        final long start = System.nanoTime();
        EmergencyListResponse response;

        try {
            response = controller.fetchEmergencyList(token);
            LOG.info(session.generateLogAndUpdateSession("fetchEmergencyList", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchEmergencyList", start, e, token), e);
            response = new EmergencyListResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ContactsResponse fetchContacts(final AuthenticationToken token, final ContactsRequest request) {
        final long start = System.nanoTime();
        ContactsResponse response;

        try {
            response = controller.fetchContacts(token, request);
            LOG.info(session.generateLogAndUpdateSession("fetchContacts", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchContacts", start, e, token, request), e);
            response = new ContactsResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
