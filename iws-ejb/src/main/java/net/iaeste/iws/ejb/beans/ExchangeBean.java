/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.beans.ExchangeBean
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.beans;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FacultyRequest;
import net.iaeste.iws.api.requests.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.FetchFacultiesRequest;
import net.iaeste.iws.api.requests.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.FetchStudentsRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.requests.StudentRequest;
import net.iaeste.iws.api.responses.FacultyResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;
import net.iaeste.iws.api.responses.StudentResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.core.ExchangeController;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.ejb.ExchangeRemote;
import net.iaeste.iws.ejb.NotificationManagerLocal;
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
 * Exchange Bean, serves as the default EJB for the IWS Exchange interface. It
 * uses JDNI instances for the Persistence Context and the Notification Manager
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
 * @since   1.7
 * @noinspection OverlyCoupledClass
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ExchangeBean extends AbstractBean implements ExchangeRemote {

    private static final Logger LOG = LoggerFactory.getLogger(AccessBean.class);
    private EntityManager entityManager = null;
    private NotificationManagerLocal notificationManager = null;
    private Exchange exchange = null;

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
        exchange = new ExchangeController(factory);
    }

    // =========================================================================
    // Implementation of methods from CommitteeRemote (Committees in API)
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public FetchEmployerInformationResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerInformationRequest request) {
        FetchEmployerInformationResponse response;

        try {
            response = exchange.fetchEmployers(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FetchEmployerInformationResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible manageFaculties(final AuthenticationToken token, final FacultyRequest request) {
        Fallible response;

        try {
            response = exchange.manageFaculties(token, request);
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
    public FacultyResponse fetchFaculties(final AuthenticationToken token, final FetchFacultiesRequest request) {
        FacultyResponse response;

        try {
            response = exchange.fetchFaculties(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FacultyResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        OfferResponse response;

        try {
            response = exchange.processOffer(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new OfferResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public OfferResponse deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        OfferResponse response;

        try {
            response = exchange.deleteOffer(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new OfferResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        FetchOffersResponse response;

        try {
            response = exchange.fetchOffers(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FetchOffersResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible manageOfferTemplate(final AuthenticationToken token, final OfferTemplateRequest request) {
        Fallible response;

        try {
            response = exchange.manageOfferTemplate(token, request);
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
    public OfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        OfferTemplateResponse response;

        try {
            response = exchange.fetchOfferTemplates(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new OfferTemplateResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible managePublishGroup(final AuthenticationToken token, final PublishGroupRequest request) {
        Fallible response;

        try {
            response = exchange.managePublishGroup(token, request);
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
    public PublishGroupResponse fetchPublishGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        PublishGroupResponse response;

        try {
            response = exchange.fetchPublishGroups(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new PublishGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible manageStudent(final AuthenticationToken token, final StudentRequest request) {
        Fallible response;

        try {
            response = exchange.manageStudent(token, request);
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
    public StudentResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        StudentResponse response;

        try {
            response = exchange.fetchStudents(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new StudentResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
