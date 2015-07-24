/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.StudentBean
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

import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.constants.IWSErrors;
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
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.StudentController;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.ejb.cdi.IWSBean;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Exchange Bean, serves as the default EJB for the IWS Exchange interface. It
 * uses JNDI instances for the Persistence Context and the Notification Manager
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
@Remote(Students.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StudentBean implements Students {

    private static final Logger LOG = LoggerFactory.getLogger(StudentBean.class);
    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Notifications notifications;
    @Inject @IWSBean private SessionRequestBean session;
    @Inject @IWSBean private Settings settings;
    private Students controller = null;

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
        controller = new StudentController(factory);
    }

    // =========================================================================
    // Implementation of methods from Student in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CreateUserResponse createStudent(final AuthenticationToken token, final CreateUserRequest request) {
        final long start = System.nanoTime();
        CreateUserResponse response;

        try {
            response = controller.createStudent(token, request);
            LOG.info(session.generateLogAndUpdateSession("createStudent", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("createStudent", start, e, token, request), e);
            response = new CreateUserResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StudentResponse processStudent(final AuthenticationToken token, final StudentRequest request) {
        final long start = System.nanoTime();
        StudentResponse response;

        try {
            response = controller.processStudent(token, request);
            LOG.info(session.generateLogAndUpdateSession("processStudent", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("processStudent", start, e, token, request), e);
            response = new StudentResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchStudentsResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        final long start = System.nanoTime();
        FetchStudentsResponse response;

        try {
            response = controller.fetchStudents(token, request);
            LOG.info(session.generateLogAndUpdateSession("fetchStudents", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchStudents", start, e, token, request), e);
            response = new FetchStudentsResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StudentApplicationResponse processStudentApplication(final AuthenticationToken token, final ProcessStudentApplicationsRequest request) {
        final long start = System.nanoTime();
        StudentApplicationResponse response;

        try {
            response = controller.processStudentApplication(token, request);
            LOG.info(session.generateLogAndUpdateSession("processStudentApplication", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("processStudentApplication", start, e, token, request), e);
            response = new StudentApplicationResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchStudentApplicationsResponse fetchStudentApplications(final AuthenticationToken token, final FetchStudentApplicationsRequest request) {
        final long start = System.nanoTime();
        FetchStudentApplicationsResponse response;

        try {
            response = controller.fetchStudentApplications(token, request);
            LOG.info(session.generateLogAndUpdateSession("fetchStudentApplications", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchStudentApplications", start, e, token, request), e);
            response = new FetchStudentApplicationsResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StudentApplicationResponse processApplicationStatus(final AuthenticationToken token, final StudentApplicationRequest request) {
        final long start = System.nanoTime();
        StudentApplicationResponse response;

        try {
            response = controller.processApplicationStatus(token, request);
            LOG.info(session.generateLogAndUpdateSession("processApplicationStatus", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("processApplicationStatus", start, e, token, request), e);
            response = new StudentApplicationResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
