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

import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationRequest;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.responses.student.StudentResponse;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.schedulers.NotificationManagerScheduler;
import net.iaeste.iws.ejb.SessionRequestBean;
import net.iaeste.iws.ejb.StudentBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Spring based Exchange Client, which wraps the Exchange Controller from the
 * IWS core module within a transactional layer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Transactional
@Repository("studentSpringClient")
public final class StudentSpringClient implements Students {

    private Students client = null;

    /**
     * Injects the {@code EntityManager} instance required to invoke our
     * transactional DAOs. The EntityManager instance can only be injected into
     * the Spring Beans, we cannot create a Spring Bean for the Exchange EJB
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

        // Create a new SessionRequestBean instance wiht out entityManager
        final SessionRequestBean sessionRequestBean = new SessionRequestBean();
        sessionRequestBean.setEntityManager(entityManager);
        sessionRequestBean.postConstruct();

        // Create an Exchange EJB, and inject the EntityManager & Notification Spy
        final StudentBean studentBean = new StudentBean();
        studentBean.setEntityManager(entityManager);
        studentBean.setNotificationManager(notificationBean);
        studentBean.setSessionRequestBean(sessionRequestBean);
        studentBean.setSettings(Beans.settings());
        studentBean.postConstruct();

        // Set our Exchange implementation to the Exchange EJB, running withing
        // a "Spring Container".
        client = studentBean;
    }

    // =========================================================================
    // Implementation of methods from Student in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateUserResponse createStudent(final AuthenticationToken token, final CreateUserRequest request) {
        return client.createStudent(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentResponse processStudent(final AuthenticationToken token, final StudentRequest request) {
        return client.processStudent(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchStudentsResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        return client.fetchStudents(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentApplicationResponse processStudentApplication(final AuthenticationToken token, final ProcessStudentApplicationsRequest request) {
        return client.processStudentApplication(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchStudentApplicationsResponse fetchStudentApplications(final AuthenticationToken token, final FetchStudentApplicationsRequest request) {
        return client.fetchStudentApplications(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentApplicationResponse processApplicationStatus(final AuthenticationToken token, final StudentApplicationRequest request) {
        return client.processApplicationStatus(token, request);
    }
}
