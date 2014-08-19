/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.ejb.interceptors.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
public class AdministrationBean extends AbstractBean implements Administration {

    private static final Logger log = LoggerFactory.getLogger(AdministrationBean.class);
    private EntityManager entityManager = null;
    private NotificationManagerLocal notificationManager = null;
    private Settings settings = new Settings();
    private Administration controller = null;

    /**
     * Setter for the JNDI injected persistence context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param entityManager Transactional Entity Manager instance
     */
    @PersistenceContext(unitName = "iwsDatabase")
    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Setter for the JNDI injected notification bean. This allows us to also
     * test the code, by invoking these setters on the instantited Object.
     *
     * @param notificationManager Notification Manager Bean
     */
    @EJB(beanInterface = NotificationManagerLocal.class)
    public void setNotificationManager(final NotificationManagerLocal notificationManager) {
        this.notificationManager = notificationManager;
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

    /**
     * {@inheritDoc}
     */
    @Override
    @PostConstruct
    public void postConstruct() {
        if (settings.getDoJndiLookup()) {
            settings.init();
        }

        final ServiceFactory factory = new ServiceFactory(entityManager, notificationManager, settings);
        controller = new AdministrationController(factory);
    }

    // =========================================================================
    // Implementation of methods from Administration in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible processCountry(final AuthenticationToken token, final CountryRequest request) {
        Fallible response;

        try {
            response = controller.processCountry(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FetchCountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        FetchCountryResponse response;

        try {
            response = controller.fetchCountries(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FetchCountryResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public CreateUserResponse createUser(final AuthenticationToken token, final CreateUserRequest request) {
        CreateUserResponse response;

        try {
            response = controller.createUser(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new CreateUserResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible activateUser(final String activationString) {
        Fallible response;

        try {
            response = controller.activateUser(activationString);
            log.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible updateUsername(final String updateCode) {
        Fallible response;

        try {
            response = controller.updateUsername(updateCode);
            log.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible controlUserAccount(final AuthenticationToken token, final UserRequest request) {
        Fallible response;

        try {
            response = controller.controlUserAccount(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible changeAccountName(final AuthenticationToken token, final AccountNameRequest request) {
        Fallible response;

        try {
            response = controller.changeAccountName(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FetchUserResponse fetchUser(final AuthenticationToken token, final FetchUserRequest request) {
        FetchUserResponse response;

        try {
            response = controller.fetchUser(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FetchUserResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FetchRoleResponse fetchRoles(final AuthenticationToken token, final FetchRoleRequest request) {
        FetchRoleResponse response;

        try {
            response = controller.fetchRoles(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FetchRoleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public ProcessGroupResponse processGroup(final AuthenticationToken token, final GroupRequest request) {
        ProcessGroupResponse response;

        try {
            response = controller.processGroup(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new ProcessGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible deleteSubGroup(final AuthenticationToken token, final GroupRequest request) {
        Fallible response;

        try {
            response = controller.deleteSubGroup(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FetchGroupResponse fetchGroup(final AuthenticationToken token, final FetchGroupRequest request) {
        FetchGroupResponse response;

        try {
            response = controller.fetchGroup(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FetchGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible changeGroupOwner(final AuthenticationToken token, final OwnerRequest request) {
        Fallible response;

        try {
            response = controller.changeGroupOwner(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public ProcessUserGroupResponse processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        ProcessUserGroupResponse response;

        try {
            response = controller.processUserGroupAssignment(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new ProcessUserGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SearchUserResponse searchUsers(final AuthenticationToken token, final SearchUserRequest request) {
        SearchUserResponse response;

        try {
            response = controller.searchUsers(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new SearchUserResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EmergencyListResponse fetchEmergencyList(final AuthenticationToken token) {
        EmergencyListResponse response;

        try {
            response = controller.fetchEmergencyList(token);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new EmergencyListResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ContactsResponse fetchContacts(final AuthenticationToken token, final ContactsRequest request) {
        ContactsResponse response;

        try {
            response = controller.fetchContacts(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new ContactsResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
