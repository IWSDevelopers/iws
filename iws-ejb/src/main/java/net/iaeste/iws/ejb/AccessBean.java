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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.AccessController;
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
import java.io.Serializable;

/**
 * Access Bean, serves as the default EJB for the IWS Access interface. It uses
 * JND instances for the Persistence Context and the Notification Manager
 * Bean.<br />
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
@Remote(Access.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AccessBean implements Access {

    private static final Logger LOG = LoggerFactory.getLogger(AccessBean.class);
    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Notifications notifications;
    @Inject @IWSBean private Settings settings;
    @Inject @IWSBean private SessionRequestBean session;
    private Access controller = null;

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
     * Setter for the JNDI injected Settings bean. This allows us to also test
     * the code, by invoking these setters on the instantiated Object.
     *
     * @param settings Settings Bean
     */
    public void setSettings(final Settings settings) {
        this.settings = settings;
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

    @PostConstruct
    public void postConstruct() {
        final ServiceFactory factory = new ServiceFactory(entityManager, notifications, settings);
        controller = new AccessController(factory);
    }

    // =========================================================================
    // Implementation of methods from Access in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        final long start = System.nanoTime();
        AuthenticationResponse response;

        try {
            response = controller.generateSession(request);
            LOG.info(session.generateLog("generateSession", start, response));
        } catch (RuntimeException e) {
            LOG.error(session.generateLog("generateSession", start, e));
            response = new AuthenticationResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse requestResettingSession(final AuthenticationRequest request) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.requestResettingSession(request);
            LOG.info(session.generateLog("requestResettingSession", start, response));
        } catch (RuntimeException e) {
            LOG.error(session.generateLog("requestResettingSession", start, e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse resetSession(final String resetSessionToken) {
        final long start = System.nanoTime();
        AuthenticationResponse response;

        try {
            response = controller.resetSession(resetSessionToken);
            LOG.info(session.generateLog("resetSession", start, response));
        } catch (RuntimeException e) {
            LOG.error(session.generateLog("resetSession", start, e));
            response = new AuthenticationResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> FallibleResponse saveSessionData(final AuthenticationToken token, final SessionDataRequest<T> request) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.saveSessionData(token, request);
            LOG.info(session.generateLogAndUpdateSession("saveSessionData", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("saveSessionData", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> SessionDataResponse<T> readSessionData(final AuthenticationToken token) {
        final long start = System.nanoTime();
        SessionDataResponse<T> response;

        try {
            response = controller.readSessionData(token);
            LOG.info(session.generateLogAndUpdateSession("readSessionData", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("readSessionData", start, e, token), e);
            response = new SessionDataResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse verifySession(final AuthenticationToken token) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.verifySession(token);
            LOG.info(session.generateLogAndUpdateSession("verifySession", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("verifySession", start, e, token), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse deprecateSession(final AuthenticationToken token) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.deprecateSession(token);
            LOG.info(session.generateLogAndUpdateSession("deprecateSession", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("deprecateSession", start, e, token), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse forgotPassword(final String username) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.forgotPassword(username);
            LOG.info(session.generateLog("forgotPassword", start, response));
        } catch (RuntimeException e) {
            LOG.error(session.generateLog("forgotPassword", start, e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse resetPassword(final Password password) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.resetPassword(password);
            LOG.info(session.generateLog("resetPassword", start, response));
        } catch (RuntimeException e) {
            LOG.error(session.generateLog("resetPassword", start, e), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse updatePassword(final AuthenticationToken token, final Password password) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.updatePassword(token, password);
            LOG.info(session.generateLogAndUpdateSession("updatePassword", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("updatePassword", start, e, token), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchPermissionResponse fetchPermissions(final AuthenticationToken token) {
        final long start = System.nanoTime();
        FetchPermissionResponse response;

        try {
            response = controller.fetchPermissions(token);
            LOG.info(session.generateLogAndUpdateSession("fetchPermissions", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchPermissions", start, e, token), e);
            response = new FetchPermissionResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
