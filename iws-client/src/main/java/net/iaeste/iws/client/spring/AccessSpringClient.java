/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.AccessSpringClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.spring;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.AccessBean;
import net.iaeste.iws.ejb.NotificationManagerBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Spring based Access Client, which wraps the Access Controller from the
 * IWS core module within a transactional layer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Transactional
@Repository("accessSpringClient")
public final class AccessSpringClient implements Access {

    private static final Integer MAX_ACTIVE_TOKENS = IWSConstants.MAX_ACTIVE_TOKENS;
    private static final Long MAX_IDLE_TIME_FOR_SESSIONS = IWSConstants.MAX_SESSION_IDLE_PERIOD;
    private static final Integer MAX_LOGIN_RETRIES = IWSConstants.MAX_LOGIN_RETRIES;
    private static final long LOGIN_BLOCKED_TIME = IWSConstants.LOGIN_BLOCKING_PERIOD;

    private Access client = null;

    public Settings initSettings() {
        final Settings settings = new Settings();

        settings.setMaxActiveTokens(MAX_ACTIVE_TOKENS);
        settings.setMaxIdleTimeForSessions(MAX_IDLE_TIME_FOR_SESSIONS);
        settings.setMaxLoginRetries(MAX_LOGIN_RETRIES);
        settings.setLoginBlockedTime(LOGIN_BLOCKED_TIME);

        return settings;
    }

    /**
     * Injects the {@code EntityManager} instance required to invoke our
     * transactional daos. The EntityManager instance can only be injected into
     * the Spring Beans, we cannot create a Spring Bean for the Access EJB
     * otherwise.
     *
     * @param entityManager Spring controlled EntityManager instance
     */
    @PersistenceContext
    public void init(final EntityManager entityManager) {
        // Create the Notification Spy, and inject it
        final Notifications notitications = NotificationSpy.getInstance();
        final NotificationManagerBean notificationBean = new NotificationManagerBean();
        notificationBean.setNotifications(notitications);

        // Create an Access EJB, and inject the EntityManager & Notification Spy
        final AccessBean accessBean = new AccessBean();
        accessBean.setEntityManager(entityManager);
        accessBean.setNotificationManager(notificationBean);
        accessBean.setSettings(initSettings());
        accessBean.postConstruct();

        // Set our Access implementation to the Access EJB, running withing a
        // "Spring Container".
        client = accessBean;
    }

    // =========================================================================
    // Implementation of methods from Access in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        return client.generateSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible requestResettingSession(final AuthenticationRequest request) {
        return client.requestResettingSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse resetSession(final String resetSessionToken) {
        return client.resetSession(resetSessionToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> Fallible saveSessionData(final AuthenticationToken token, final SessionDataRequest<T> request) {
        return client.saveSessionData(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> SessionDataResponse<T> readSessionData(final AuthenticationToken token) {
        return client.readSessionData(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deprecateSession(final AuthenticationToken token) {
        return client.deprecateSession(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible forgotPassword(final String username) {
        return client.forgotPassword(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible resetPassword(final String resetPasswordToken, final Password password) {
        return client.resetPassword(resetPasswordToken, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible updatePassword(final AuthenticationToken token, final Password password) {
        return client.updatePassword(token, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPermissionResponse fetchPermissions(final AuthenticationToken token) {
        return client.fetchPermissions(token);
    }
}
