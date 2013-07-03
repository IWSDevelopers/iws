/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.ExchangeBean
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

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.exchange.FetchGroupsForSharingRequest;
import net.iaeste.iws.api.requests.exchange.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.OfferTemplateRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishGroupResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.core.ExchangeController;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.ejb.interceptors.Profiler;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
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
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ExchangeBean extends AbstractBean implements Exchange {

    private static final Logger LOG = Logger.getLogger(ExchangeBean.class);
    private EntityManager entityManager = null;
    private NotificationManagerLocal notificationManager = null;
    private Exchange controller = null;

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
    @PostConstruct
    public void postConstruct() {
        final ServiceFactory factory = new ServiceFactory(entityManager, notificationManager.getNotifications());
        controller = new ExchangeController(factory);
    }

    // =========================================================================
    // Implementation of methods from Exchange in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public FetchEmployerInformationResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerInformationRequest request) {
        FetchEmployerInformationResponse response;

        try {
            response = controller.fetchEmployers(token, request);
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
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        OfferResponse response;

        try {
            response = controller.processOffer(token, request);
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
            response = controller.deleteOffer(token, request);
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
            response = controller.fetchOffers(token, request);
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
    public Fallible processOfferTemplate(final AuthenticationToken token, final OfferTemplateRequest request) {
        Fallible response;

        try {
            response = controller.processOfferTemplate(token, request);
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
    public FetchOfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        FetchOfferTemplateResponse response;

        try {
            response = controller.fetchOfferTemplates(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FetchOfferTemplateResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public Fallible processPublishGroup(final AuthenticationToken token, final PublishGroupRequest request) {
        Fallible response;

        try {
            response = controller.processPublishGroup(token, request);
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
    public FetchPublishGroupResponse fetchPublishGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        FetchPublishGroupResponse response;

        try {
            response = controller.fetchPublishGroups(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FetchPublishGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public FetchGroupsForSharingResponse fetchGroupsForSharing(final AuthenticationToken token, final FetchGroupsForSharingRequest request) {
        FetchGroupsForSharingResponse response;

        try {
            response = controller.fetchGroupsForSharing(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FetchGroupsForSharingResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public PublishOfferResponse processPublishOffer(final AuthenticationToken token, final PublishOfferRequest request) {
        PublishOfferResponse response;

        try {
            response = controller.processPublishOffer(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new PublishOfferResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors(Profiler.class)
    public FetchPublishedGroupsResponse fetchPublishedGroups(final AuthenticationToken token, final FetchPublishedGroupsRequest request) {
        FetchPublishedGroupsResponse response;

        try {
            response = controller.fetchPublishedGroups(token, request);
            LOG.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            LOG.error(generateErrorLog(e));
            response = new FetchPublishedGroupsResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
