/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.CommitteeBean
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

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.FetchCountrySurveyRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.CountrySurveyRequest;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
import net.iaeste.iws.api.responses.FetchCountrySurveyRespose;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.CommitteeController;
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
 * Committee Bean, serves as the default EJB for the IWS Committee interface.
 * It uses JNDI instances for the Persistence Context and the Notification
 * Manager Bean.<br />
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
@Remote(Committees.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CommitteeBean implements Committees {

    private static final Logger LOG = LoggerFactory.getLogger(CommitteeBean.class);
    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Notifications notifications;
    @Inject @IWSBean private SessionRequestBean session;
    @Inject @IWSBean private Settings settings;
    private Committees controller = null;

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
        controller = new CommitteeController(factory);
    }

    // =========================================================================
    // Implementation of methods from Committees in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCommitteeResponse fetchCommittees(final AuthenticationToken token, final FetchCommitteeRequest request) {
        final long start = System.nanoTime();
        FetchCommitteeResponse response;

        try {
            response = controller.fetchCommittees(token, request);
            LOG.info(session.generateLogAndUpdateSession("fetchCommittees", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchCommittees", start, e, token, request), e);
            response = new FetchCommitteeResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse processCommittee(final AuthenticationToken token, final CommitteeRequest request) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.processCommittee(token, request);
            LOG.info(session.generateLogAndUpdateSession("processCommittee", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("processCommittee", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchInternationalGroupResponse fetchInternationalGroups(final AuthenticationToken token, final FetchInternationalGroupRequest request) {
        final long start = System.nanoTime();
        FetchInternationalGroupResponse response;

        try {
            response = controller.fetchInternationalGroups(token, request);
            LOG.info(session.generateLogAndUpdateSession("fetchInternationalGroups", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchInternationalGroups", start, e, token, request), e);
            response = new FetchInternationalGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse processInternationalGroup(final AuthenticationToken token, final InternationalGroupRequest request) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.processInternationalGroup(token, request);
            LOG.info(session.generateLogAndUpdateSession("processInternationalGroup", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("processInternationalGroup", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCountrySurveyRespose fetchCountrySurvey(final AuthenticationToken token, final FetchCountrySurveyRequest request) {
        final long start = System.nanoTime();
        FetchCountrySurveyRespose response;

        try {
            response = controller.fetchCountrySurvey(token, request);
            LOG.info(session.generateLogAndUpdateSession("fetchCountrySurvey", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("fetchCountrySurvey", start, e, token, request), e);
            response = new FetchCountrySurveyRespose(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse processCountrySurvey(final AuthenticationToken token, final CountrySurveyRequest request) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.processCountrySurvey(token, request);
            LOG.info(session.generateLogAndUpdateSession("processCountrySurvey", start, response, token));
        } catch (RuntimeException e) {
            LOG.error(session.generateLogAndSaveRequest("processCountrySurvey", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
