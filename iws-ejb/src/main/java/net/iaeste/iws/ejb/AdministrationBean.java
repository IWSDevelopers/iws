/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.core.AdministrationController;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.ejb.interceptors.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
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
 * @since   1.7
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdministrationBean extends AbstractBean implements Administration {

    private static final Logger LOG = LoggerFactory.getLogger(AccessBean.class);
    private EntityManager entityManager = null;
    private NotificationManagerLocal notificationManager = null;
    private Administration administration = null;

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
     * {@inheritDoc}
     */
    @Override
    public void postConstruct() {
        final ServiceFactory factory = new ServiceFactory(entityManager, notificationManager.getNotifications());
        administration = new AdministrationController(factory);
    }

    // =========================================================================
    // Implementation of methods from Administration in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible createUser(final AuthenticationToken token, final CreateUserRequest request) {
        Fallible response;

        try {
            response = administration.createUser(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
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
            response = administration.activateUser(activationString);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
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
            response = administration.controlUserAccount(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public FetchUserResponse fetchUser(final AuthenticationToken token, final FetchUserRequest request) {
        FetchUserResponse response;

        try {
            response = administration.fetchUser(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FetchUserResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible processGroup(final AuthenticationToken token, final GroupRequest request) {
        Fallible response;

        try {
            response = administration.processGroup(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible deleteGroup(final AuthenticationToken token, final GroupRequest request) {
        Fallible response;

        try {
            response = administration.deleteGroup(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public FetchGroupResponse fetchGroup(final AuthenticationToken token, final FetchGroupRequest request) {
        FetchGroupResponse response;

        try {
            response = administration.fetchGroup(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FetchGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible processCountries(final AuthenticationToken token, final CountryRequest request) {
        Fallible response;

        try {
            response = administration.processCountries(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public FetchCountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        FetchCountryResponse response;

        try {
            response = administration.fetchCountries(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FetchCountryResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        Fallible response;

        try {
            response = administration.processUserGroupAssignment(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
