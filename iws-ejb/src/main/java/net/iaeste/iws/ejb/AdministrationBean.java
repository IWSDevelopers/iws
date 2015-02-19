/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.AdministrationBean
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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

    private static final Logger log = LoggerFactory.getLogger(AdministrationBean.class);
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
    public void setNotificationManager(final NotificationManagerLocal notificationManager) {
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
    public Fallible processCountry(final AuthenticationToken token, final CountryRequest request) {
        final long start = System.nanoTime();
        Fallible response;

        try {
            response = controller.processCountry(token, request);
            log.info(session.generateLogAndUpdateSession("processCountry", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("processCountry", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
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
            log.info(session.generateLogAndUpdateSession("fetchCountries", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchCountries", start, e, token, request), e);
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
            log.info(session.generateLogAndUpdateSession("createUser", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("createUser", start, e, token, request), e);
            response = new CreateUserResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible activateUser(final String activationString) {
        final long start = System.nanoTime();
        Fallible response;

        try {
            response = controller.activateUser(activationString);
            log.info(session.generateLog("activateUser", start, response));
        } catch (RuntimeException e) {
            log.error(session.generateLog("activateUser", start, e), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible updateUsername(final String updateCode) {
        final long start = System.nanoTime();
        Fallible response;

        try {
            response = controller.updateUsername(updateCode);
            log.info(session.generateLog("updateUsername", start, response));
        } catch (RuntimeException e) {
            log.error(session.generateLog("updateUsername", start, e), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible controlUserAccount(final AuthenticationToken token, final UserRequest request) {
        final long start = System.nanoTime();
        Fallible response;

        try {
            response = controller.controlUserAccount(token, request);
            log.info(session.generateLogAndUpdateSession("controlUserAccount", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("controlUserAccount", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible changeAccountName(final AuthenticationToken token, final AccountNameRequest request) {
        final long start = System.nanoTime();
        Fallible response;

        try {
            response = controller.changeAccountName(token, request);
            log.info(session.generateLogAndUpdateSession("changeAccountName", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("changeAccountName", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
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
            log.info(session.generateLogAndUpdateSession("fetchUser", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchUser", start, e, token, request), e);
            response = new FetchUserResponse(IWSErrors.ERROR, e.getMessage());
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
            log.info(session.generateLogAndUpdateSession("fetchRoles", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchRoles", start, e, token, request), e);
            response = new FetchRoleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessGroupResponse processGroup(final AuthenticationToken token, final GroupRequest request) {
        final long start = System.nanoTime();
        ProcessGroupResponse response;

        try {
            response = controller.processGroup(token, request);
            log.info(session.generateLogAndUpdateSession("processGroup", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("processGroup", start, e, token, request), e);
            response = new ProcessGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deleteSubGroup(final AuthenticationToken token, final GroupRequest request) {
        final long start = System.nanoTime();
        Fallible response;

        try {
            response = controller.deleteSubGroup(token, request);
            log.info(session.generateLogAndUpdateSession("deleteSubGroup", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("deleteSubGroup", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
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
            log.info(session.generateLogAndUpdateSession("fetchGroup", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchGroup", start, e, token, request), e);
            response = new FetchGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible changeGroupOwner(final AuthenticationToken token, final OwnerRequest request) {
        final long start = System.nanoTime();
        Fallible response;

        try {
            response = controller.changeGroupOwner(token, request);
            log.info(session.generateLogAndUpdateSession("changeGroupOwner", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("changeGroupOwner", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessUserGroupResponse processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        final long start = System.nanoTime();
        ProcessUserGroupResponse response;

        try {
            response = controller.processUserGroupAssignment(token, request);
            log.info(session.generateLogAndUpdateSession("processUserGroupAssignment", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("processUserGroupAssignment", start, e, token, request), e);
            response = new ProcessUserGroupResponse(IWSErrors.ERROR, e.getMessage());
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
            log.info(session.generateLogAndUpdateSession("searchUsers", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("searchUsers", start, e, token, request), e);
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
            log.info(session.generateLogAndUpdateSession("fetchEmergencyList", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchEmergencyList", start, e, token), e);
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
            log.info(session.generateLogAndUpdateSession("fetchContacts", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchContacts", start, e, token, request), e);
            response = new ContactsResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
