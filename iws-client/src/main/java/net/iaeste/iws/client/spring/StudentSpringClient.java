/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.StudentSpringClient
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

import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.exchange.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationRequest;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.NotificationManagerBean;
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
 * @since   1.7
 * @noinspection OverlyCoupledClass
 */
@Transactional
@Repository("studentSpringClient")
public final class StudentSpringClient implements Students {

    private Students client = null;

    /**
     * Injects the {@code EntityManager} instance required to invoke our
     * transactional daos. The EntityManager instance can only be injected into
     * the Spring Beans, we cannot create a Spring Bean for the Exchange EJB
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

        // Create an Exchange EJB, and inject the EntityManager & Notification Spy
        final StudentBean studentBean = new StudentBean();
        studentBean.setEntityManager(entityManager);
        studentBean.setNotificationManager(notificationBean);
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
