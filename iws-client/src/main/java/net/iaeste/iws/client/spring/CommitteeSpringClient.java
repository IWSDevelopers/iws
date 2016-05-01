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
package net.iaeste.iws.client.spring;

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.FetchCountrySurveyRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.CountrySurveyRequest;
import net.iaeste.iws.api.responses.CommitteeResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
import net.iaeste.iws.api.responses.FetchCountrySurveyResponse;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.CommitteeBean;
import net.iaeste.iws.ejb.schedulers.NotificationManagerScheduler;
import net.iaeste.iws.ejb.SessionRequestBean;
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
        final NotificationManagerScheduler notificationBean = new NotificationManagerScheduler();
        notificationBean.setNotifications(notitications);

        // Create a new SessionRequestBean instance with our entityManager
        final SessionRequestBean sessionRequestBean = new SessionRequestBean();
        sessionRequestBean.setEntityManager(entityManager);
        sessionRequestBean.setSettings(Beans.settings());
        sessionRequestBean.postConstruct();

        // Create an Committees EJB, and inject the EntityManager & Notification Spy
        final CommitteeBean committeeBean = new CommitteeBean();
        committeeBean.setEntityManager(entityManager);
        committeeBean.setNotificationManager(notificationBean);
        committeeBean.setSessionRequestBean(sessionRequestBean);
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
    public FetchCommitteeResponse fetchCommittees(final AuthenticationToken token, final FetchCommitteeRequest request) {
        return client.fetchCommittees(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitteeResponse processCommittee(final AuthenticationToken token, final CommitteeRequest request) {
        return client.processCommittee(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchInternationalGroupResponse fetchInternationalGroups(final AuthenticationToken token, final FetchInternationalGroupRequest request) {
        return client.fetchInternationalGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse processInternationalGroup(final AuthenticationToken token, final InternationalGroupRequest request) {
        return client.processInternationalGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCountrySurveyResponse fetchCountrySurvey(final AuthenticationToken token, final FetchCountrySurveyRequest request) {
        return client.fetchCountrySurvey(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse processCountrySurvey(final AuthenticationToken token, final CountrySurveyRequest request) {
        return client.processCountrySurvey(token, request);
    }
}
