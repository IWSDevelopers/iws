/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.api.requests.exchange.DeletePublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVDownloadRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.OfferTemplateRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.RejectOfferRequest;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.ExchangeController;
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
import javax.jws.WebParam;
import javax.persistence.EntityManager;

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
@Remote(Exchange.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ExchangeBean implements Exchange {

    private static final Logger log = LoggerFactory.getLogger(ExchangeBean.class);
    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Notifications notifications;
    @Inject @IWSBean private SessionRequestBean session;
    @Inject @IWSBean private Settings settings;
    private Exchange controller = null;

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
        controller = new ExchangeController(factory);
    }

    // =========================================================================
    // Implementation of methods from Exchange in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public OfferStatisticsResponse fetchOfferStatistics(final AuthenticationToken token, final OfferStatisticsRequest request) {
        final long start = System.nanoTime();
        OfferStatisticsResponse response;

        try {
            response = controller.fetchOfferStatistics(token, request);
            log.info(session.generateLogAndUpdateSession("fetchOfferStatistics", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchOfferStatistics", start, e, token, request), e);
            response = new OfferStatisticsResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployerResponse processEmployer(final AuthenticationToken token, final ProcessEmployerRequest request) {
        final long start = System.nanoTime();
        EmployerResponse response;

        try {
            response = controller.processEmployer(token, request);
            log.info(session.generateLogAndUpdateSession("processEmployer", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("processEmployer", start, e, token, request), e);
            response = new EmployerResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchEmployerResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerRequest request) {
        final long start = System.nanoTime();
        FetchEmployerResponse response;

        try {
            response = controller.fetchEmployers(token, request);
            log.info(session.generateLogAndUpdateSession("fetchEmployers", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchEmployers", start, e, token, request), e);
            response = new FetchEmployerResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        final long start = System.nanoTime();
        OfferResponse response;

        try {
            response = controller.processOffer(token, request);
            log.info(session.generateLogAndUpdateSession("processOffer", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("processOffer", start, e, token, request), e);
            response = new OfferResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public OfferResponse deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        final long start = System.nanoTime();
        OfferResponse response;

        try {
            response = controller.deleteOffer(token, request);
            log.info(session.generateLogAndUpdateSession("deleteOffer", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("deleteOffer", start, e, token, request), e);
            response = new OfferResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public OfferCSVUploadResponse uploadOffers(final AuthenticationToken token, final OfferCSVUploadRequest request) {
        final long start = System.nanoTime();
        OfferCSVUploadResponse response;

        try {
            response = controller.uploadOffers(token, request);
            log.info(session.generateLogAndUpdateSession("uploadOffers", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("uploadOffers", start, e, token, request), e);
            response = new OfferCSVUploadResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchOffersResponse fetchOffers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchOffersRequest request) {
        final long start = System.nanoTime();
        FetchOffersResponse response;

        try {
            response = controller.fetchOffers(token, request);
            log.info(session.generateLogAndUpdateSession("fetchOffers", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchOffers", start, e, token, request), e);
            response = new FetchOffersResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public OfferCSVDownloadResponse downloadOffers(final AuthenticationToken token, final OfferCSVDownloadRequest request) {
        final long start = System.nanoTime();
        OfferCSVDownloadResponse response;

        try {
            response = controller.downloadOffers(token, request);
            log.info(session.generateLogAndUpdateSession("downloadOffers", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("downloadOffers", start, e, token, request), e);
            response = new OfferCSVDownloadResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchGroupsForSharingResponse fetchGroupsForSharing(final AuthenticationToken token) {
        final long start = System.nanoTime();
        FetchGroupsForSharingResponse response;

        try {
            response = controller.fetchGroupsForSharing(token);
            log.info(session.generateLogAndUpdateSession("fetchGroupsForSharing", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchGroupsForSharing", start, e, token), e);
            response = new FetchGroupsForSharingResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public FallibleResponse processOfferTemplate(final AuthenticationToken token, final OfferTemplateRequest request) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.processOfferTemplate(token, request);
            log.info(session.generateLogAndUpdateSession("processOfferTemplate", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("processOfferTemplate", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchOfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        final long start = System.nanoTime();
        FetchOfferTemplateResponse response;

        try {
            response = controller.fetchOfferTemplates(token, request);
            log.info(session.generateLogAndUpdateSession("fetchOfferTemplates", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchOfferTemplates", start, e, token, request), e);
            response = new FetchOfferTemplateResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public FallibleResponse processPublishingGroup(final AuthenticationToken token, final ProcessPublishingGroupRequest request) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.processPublishingGroup(token, request);
            log.info(session.generateLogAndUpdateSession("processPublishingGroup", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("processPublishingGroup", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchPublishingGroupResponse fetchPublishingGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        final long start = System.nanoTime();
        FetchPublishingGroupResponse response;

        try {
            response = controller.fetchPublishingGroups(token, request);
            log.info(session.generateLogAndUpdateSession("fetchPublishingGroups", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchPublishingGroups", start, e, token, request), e);
            response = new FetchPublishingGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public FallibleResponse deletePublishingGroup(final AuthenticationToken token, final DeletePublishingGroupRequest request) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.deletePublishingGroup(token, request);
            log.info(session.generateLogAndUpdateSession("deletePublishingGroup", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("deletePublishingGroup", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PublishOfferResponse processPublishOffer(final AuthenticationToken token, final PublishOfferRequest request) {
        final long start = System.nanoTime();
        PublishOfferResponse response;

        try {
            response = controller.processPublishOffer(token, request);
            log.info(session.generateLogAndUpdateSession("processPublishOffer", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("processPublishOffer", start, e, token, request), e);
            response = new PublishOfferResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchPublishedGroupsResponse fetchPublishedGroups(final AuthenticationToken token, final FetchPublishedGroupsRequest request) {
        final long start = System.nanoTime();
        FetchPublishedGroupsResponse response;

        try {
            response = controller.fetchPublishedGroups(token, request);
            log.info(session.generateLogAndUpdateSession("fetchPublishedGroups", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchPublishedGroups", start, e, token, request), e);
            response = new FetchPublishedGroupsResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public FallibleResponse processHideForeignOffers(final AuthenticationToken token, final HideForeignOffersRequest request) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.processHideForeignOffers(token, request);
            log.info(session.generateLogAndUpdateSession("processHideForeignOffers", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("processHideForeignOffers", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public FallibleResponse rejectOffer(final AuthenticationToken token, final RejectOfferRequest request) {
        final long start = System.nanoTime();
        FallibleResponse response;

        try {
            response = controller.rejectOffer(token, request);
            log.info(session.generateLogAndUpdateSession("rejectOffer", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("rejectOffer", start, e, token, request), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
