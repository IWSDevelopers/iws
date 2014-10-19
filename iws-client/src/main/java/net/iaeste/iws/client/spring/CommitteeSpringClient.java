/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.CommitteeSpringClient
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

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchSurveyOfCountryRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.SurveyOfCountryRequest;
import net.iaeste.iws.api.responses.FetchSurveyOfCountryRespose;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.CommitteeBean;
import net.iaeste.iws.ejb.NotificationManagerBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Transactional
@Repository("committeeSpringClient")
public class CommitteeSpringClient implements Committees {

    private Committees client = null;

    /**
     * Injects the {@code EntityManager} instance required to invoke our
     * transactional daos. The EntityManager instance can only be injected into
     * the Spring Beans, we cannot create a Spring Bean for the Committees EJB
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

        // Create an Committees EJB, and inject the EntityManager & Notification Spy
        final CommitteeBean committeeBean = new CommitteeBean();
        committeeBean.setEntityManager(entityManager);
        committeeBean.setNotificationManager(notificationBean);
        committeeBean.setSettings(Beans.settings());
        committeeBean.postConstruct();

        // Set our Committees implementation to the Committees EJB,
        // running withing a "Spring Container".
        client = committeeBean;
    }

    // =========================================================================
    // Implementation of methods from Committees in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processCommittee(final AuthenticationToken token, final CommitteeRequest request) {
        return client.processCommittee(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processInternationalGroup(final AuthenticationToken token, final InternationalGroupRequest request) {
        return client.processInternationalGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchSurveyOfCountryRespose fetchSurveyOfCountry(final AuthenticationToken token, final FetchSurveyOfCountryRequest request) {
        return client.fetchSurveyOfCountry(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processSurveyOfCountry(final AuthenticationToken token, final SurveyOfCountryRequest request) {
        return client.processSurveyOfCountry(token, request);
    }
}
