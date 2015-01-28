/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.AccessBean
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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.AccessController;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.ejb.cdi.IWSBean;
import net.iaeste.iws.ejb.interceptors.Profiler;
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
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Access Bean, serves as the default EJB for the IWS Access interface. It uses
 * JND instances for the Persistence Context and the Notification Manager
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
@Remote(Access.class)
@WebService(serviceName = "access")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AccessBean extends AbstractBean implements Access {

    private static final Logger log = LoggerFactory.getLogger(AccessBean.class);
    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Notifications notifications;
    @Inject @IWSBean private Settings settings;
    private Access controller = null;

    /**
     * Setter for the JNDI injected persistence context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param entityManager Transactional Entity Manager instance
     */
    @WebMethod(exclude = true)
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
    public void setNotificationManager(final NotificationManagerLocal notificationManager) {
        this.notifications = notificationManager;
    }

    /**
     * Setter for the JNDI injected Settings bean. This allows us to also test
     * the code, by invoking these setters on the instantiated Object.
     *
     * @param settings Settings Bean
     */
    @WebMethod(exclude = true)
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
        final ServiceFactory factory = new ServiceFactory(entityManager, notifications, settings);
        controller = new AccessController(factory);
    }

    // =========================================================================
    // Implementation of methods from Access in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    @Interceptors(Profiler.class)
    public AuthenticationResponse generateSession(
            @WebParam(name="request") final AuthenticationRequest request) {
        AuthenticationResponse response;

        try {
            response = controller.generateSession(request);
            log.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e));
            response = new AuthenticationResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    public Fallible requestResettingSession(final AuthenticationRequest request) {
        Fallible response;

        try {
            response = controller.requestResettingSession(request);
            log.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    public AuthenticationResponse resetSession(final String resetSessionToken) {
        AuthenticationResponse response;

        try {
            response = controller.resetSession(resetSessionToken);
            log.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e));
            response = new AuthenticationResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    public <T extends Serializable> Fallible saveSessionData(final AuthenticationToken token, final SessionDataRequest<T> request) {
        Fallible response;

        try {
            response = controller.saveSessionData(token, request);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    public <T extends Serializable> SessionDataResponse<T> readSessionData(final AuthenticationToken token) {
        SessionDataResponse<T> response;

        try {
            response = controller.readSessionData(token);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token), e);
            response = new SessionDataResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse verifySession(
            @WebParam(name = "token") final AuthenticationToken token) {
        FallibleResponse response;

        try {
            response = controller.verifySession(token);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse deprecateSession(
            @WebParam(name="token") final AuthenticationToken token) {
        FallibleResponse response;

        try {
            response = controller.deprecateSession(token);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    public Fallible forgotPassword(final String username) {
        Fallible response;

        try {
            response = controller.forgotPassword(username);
            log.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod(exclude = true)
    public Fallible resetPassword(final String resetPasswordToken, final Password password) {
        Fallible response;

        try {
            response = controller.resetPassword(resetPasswordToken, password);
            log.info(generateResponseLog(response));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e));
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FallibleResponse updatePassword(
            @WebParam(name="token") final AuthenticationToken token,
            @WebParam(name="password") final Password password) {
        FallibleResponse response;

        try {
            response = controller.updatePassword(token, password);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token), e);
            response = new FallibleResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FetchPermissionResponse fetchPermissions(
            @WebParam(name="token") final AuthenticationToken token) {
        FetchPermissionResponse response;

        try {
            response = controller.fetchPermissions(token);
            log.info(generateResponseLog(response, token));
        } catch (RuntimeException e) {
            log.error(generateErrorLog(e, token), e);
            response = new FetchPermissionResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
