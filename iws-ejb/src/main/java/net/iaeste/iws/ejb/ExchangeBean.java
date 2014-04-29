/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.OfferTemplateRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.ExchangeController;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.ejb.interceptors.Profiler;
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
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
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
 * @since   IWS 1.0
 */
@Stateless
@Remote(Exchange.class)
@WebService(serviceName = "exchange")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ExchangeBean extends AbstractBean implements Exchange {

    private static final Logger log = LoggerFactory.getLogger(ExchangeBean.class);
    private EntityManager entityManager = null;
    private NotificationManagerLocal notificationManager = null;
    private Settings settings = new Settings();
    private Exchange controller = null;

    /**
     * Setter for the JNDI injected persistence context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param entityManager Transactional Entity Manager instance
     */
    @WebMethod(exclude = true)
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
    @WebMethod(exclude = true)
    @EJB(beanInterface = NotificationManagerLocal.class)
    public void setNotificationManager(final NotificationManagerLocal notificationManager) {
        this.notificationManager = notificationManager;
    }

    /**
     * Setter for the JNDI injected Settings bean. This allows us to also test
     * the code, by invoking these setters on the instantiated Object.
     *
     * @param settings Settings Bean
     */
    @WebMethod(exclude = true)
    @Inject
    public void setSettings(final Settings settings) {
        this.settings = settings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostConstruct
    @WebMethod(exclude = true)
    public void postConstruct() {
        if (settings.getDoJndiLookup()) {
            settings.init();
        }

        final ServiceFactory factory = new ServiceFactory(entityManager, notificationManager, settings);
        controller = new ExchangeController(factory);
    }

    // =========================================================================
    // Implementation of methods from Exchange in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public OfferStatisticsResponse fetchOfferStatistics(final AuthenticationToken token, final OfferStatisticsRequest request) {
        OfferStatisticsResponse response;

        try {
            response = controller.fetchOfferStatistics(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new OfferStatisticsResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    @Interceptors(Profiler.class)
    public EmployerResponse processEmployer(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final ProcessEmployerRequest request) {
        EmployerResponse response;

        try {
            response = controller.processEmployer(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new EmployerResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FetchEmployerResponse fetchEmployers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchEmployerRequest request) {
        FetchEmployerResponse response;

        try {
            response = controller.fetchEmployers(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FetchEmployerResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    @Interceptors(Profiler.class)
    public OfferResponse processOffer(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final ProcessOfferRequest request) {
        OfferResponse response;

        try {
            response = controller.processOffer(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new OfferResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    @Interceptors(Profiler.class)
    public OfferResponse deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        OfferResponse response;

        try {
            response = controller.deleteOffer(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new OfferResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FetchOffersResponse fetchOffers(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchOffersRequest request) {
        FetchOffersResponse response;

        try {
            response = controller.fetchOffers(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FetchOffersResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FetchGroupsForSharingResponse fetchGroupsForSharing(final AuthenticationToken token) {
        FetchGroupsForSharingResponse response;

        try {
            response = controller.fetchGroupsForSharing(token);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FetchGroupsForSharingResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    @Interceptors(Profiler.class)
    public Fallible processOfferTemplate(final AuthenticationToken token, final OfferTemplateRequest request) {
        Fallible response;

        try {
            response = controller.processOfferTemplate(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FetchOfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        FetchOfferTemplateResponse response;

        try {
            response = controller.fetchOfferTemplates(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FetchOfferTemplateResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    @Interceptors(Profiler.class)
    public Fallible processPublishingGroup(final AuthenticationToken token, final ProcessPublishingGroupRequest request) {
        Fallible response;

        try {
            response = controller.processPublishingGroup(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FetchPublishingGroupResponse fetchPublishingGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        FetchPublishingGroupResponse response;

        try {
            response = controller.fetchPublishingGroups(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FetchPublishingGroupResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    @WebMethod(exclude = true)
    @Interceptors(Profiler.class)
    public Fallible deletePublishingGroup(final AuthenticationToken token, final DeletePublishingGroupRequest request) {
        Fallible response;

        try {
            response = controller.deletePublishingGroup(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    @Interceptors(Profiler.class)
    public PublishOfferResponse processPublishOffer(final AuthenticationToken token, final PublishOfferRequest request) {
        PublishOfferResponse response;

        try {
            response = controller.processPublishOffer(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new PublishOfferResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    @Interceptors(Profiler.class)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FetchPublishedGroupsResponse fetchPublishedGroups(final AuthenticationToken token, final FetchPublishedGroupsRequest request) {
        FetchPublishedGroupsResponse response;

        try {
            response = controller.fetchPublishedGroups(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FetchPublishedGroupsResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    @Interceptors(Profiler.class)
    public Fallible processHideForeignOffers(final AuthenticationToken token, final HideForeignOffersRequest request) {
        Fallible response;

        try {
            response = controller.processHideForeignOffers(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
