/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.SessionRequestBean
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

import static net.iaeste.iws.api.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.api.util.Serializer;
import net.iaeste.iws.common.exceptions.AuthenticationException;
import net.iaeste.iws.ejb.cdi.IWSBean;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.entities.RequestEntity;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;

/**
 * To improve the internal error handling, it helps if all error cases saved
 * with all relevant information. The purpose of this Bean is to do precisely
 * that. Store information for each request and if an unknown error occurred,
 * then the complete request is saved for later review to improve the
 * system.<br />
 *   Due to time constraints, the class is written, but glassfish is putting up
 * a fight when it comes to using it via JEE Decorators. Interceptors is another
 * possible mechanism, but it is not allowed to combine Interceptors with JTA.
 * Leaving only the option that all requests are invoking this before returning
 * the result.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@IWSBean
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SessionRequestBean {

    private static final Logger LOG = LoggerFactory.getLogger(SessionRequestBean.class);

    @Inject @IWSBean private EntityManager entityManager;

    // For internal data management, we're using the Access DAO
    private AccessDao dao = null;

    // Success, Warning or Failure messages to print for each request.
    private static final String SUCCESS = "%s completed successfully in %s ms.";
    private static final String WARINING = "%s completed in %s ms with warning: %s";
    private static final String FAILURE = "%s completed in %s ms with failure: %s";

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

    @PostConstruct
    public void postConstruct() {
        dao = new AccessJpaDao(entityManager);
    }

    // =========================================================================
    // Common functionality to deal with Sessions, Requests and Log Messages
    // =========================================================================

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String generateLogAndUpdateSession(final String method, final Long start, final Fallible response, final AuthenticationToken token) {
        saveRequest(method, response, token);

        return generateLog(method, start, response, token);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String generateLogAndSaveRequest(final String method, final Long start, final Throwable cause, final AuthenticationToken token, final Serializable request) {
        saveRequest(method, cause, token, request);

        return generateLog(method, start, cause, token);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String generateLogAndSaveRequest(final String method, final Long start, final Throwable cause, final AuthenticationToken token) {
        saveRequest(method, cause, token, null);

        return generateLog(method, start, cause, token);
    }

    // =========================================================================
    // Common functionality to Generate the Log Messages
    // =========================================================================

    /**
     * Generates the standard log messages, requests which completed normally.
     * The log message will, depending on the result, be either success or
     * warning messages to be printed out. The message will contain the method,
     * duration and result.
     *
     * @param method   The name of the Method being invoked
     * @param start    The time when the request started in nanoseconds
     * @param fallible The result of the request, either success or failure
     * @param token    The users Authentication Token for traceability
     * @return Formatted log message with request, duration and result
     */
    public String generateLog(final String method, final Long start, final Fallible fallible, final AuthenticationToken token) {
        // The symbols used can vary from locale to locale, so we're setting
        // them according to the default Locale in IWS.
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols(IWSConstants.DEFAULT_LOCALE);

        // The milliseconds that a request takes is converted from the date/time
        // format and into a nice printable number, via this formatter.
        final DecimalFormat format = new DecimalFormat("###,###.##", symbols);

        final String duration = format.format((System.nanoTime() - start) / 1000000.0);
        final String logMessage;

        if (fallible.isOk()) {
            logMessage = formatLogMessage(token, SUCCESS, method, duration);
        } else {
            final String message = fallible.getMessage();
            logMessage = formatLogMessage(token, WARINING, method, duration, message);
        }

        return logMessage;
    }

    /**
     * Generates the standard log messages, requests which completed normally.
     * The log message will, depending on the result, be either success or
     * warning messages to be printed out. The message will contain the method,
     * duration and result.
     *
     * @param method   The name of the Method being invoked
     * @param start    The time when the request started in nanoseconds
     * @param fallible The result of the request, either success or failure
     * @return Formatted log message with request, duration and result
     */
    public String generateLog(final String method, final Long start, final Fallible fallible) {
        return generateLog(method, start, fallible, null);
    }

    /**
     * Generates the failure log messages, which happens if an unknown problem
     * occurred during the processing to be printed out. The message will
     * contain the method, duration and the error cause.
     *
     * @param method The name of the Method being invoked
     * @param start  The time when the request started in nanoseconds
     * @param cause  The cause of the problem that occurred
     * @param token  The users Authentication Token for traceability
     * @return Formatted log message with request, duration and failure message
     */
    public String generateLog(final String method, final Long start, final Throwable cause, final AuthenticationToken token) {
        // The milliseconds that a request takes is converted from the date/time
        // format and into a nice printable number, via this formatter.
        final DecimalFormat format = new DecimalFormat("###,###.##");
        final String duration = format.format((double) (System.nanoTime() - start) / 1000000);

        return formatLogMessage(token, FAILURE, method, duration, cause.getMessage());
    }

    /**
     * Wrapper for the full Generate Log method.
     *
     * @param method The name of the Method being invoked
     * @param start  The time when the request started in nanoseconds
     * @param cause  The cause of the problem that occurred
     * @return Formatted log message with request, duration and failure message
     */
    public String generateLog(final String method, final long start, final Throwable cause) {
        return generateLog(method, start, cause, null);
    }

    // =========================================================================
    // Internal Helper Methods
    // =========================================================================

    private void saveRequest(final String request, final Throwable cause, final AuthenticationToken token, final Serializable obj) {
        final SessionEntity session = findAndUpdateSession(token);

        if (session != null) {
            final RequestEntity entity = prepareEntity(session, cause, request);
            if (obj != null) {
                entity.setRequestObject(Serializer.serialize(obj));
            }
            LOG.debug("Saving Requests information with the request Object.");
            dao.persist(entity);
        } else {
            LOG.warn("Attempted to save request information for deprecated Session.");
        }
    }

    private void saveRequest(final String request, final Fallible response, final AuthenticationToken token) {
        final SessionEntity session = findAndUpdateSession(token);

        if (!response.getError().equals(IWSErrors.SUCCESS)) {
            if (session != null) {
                final RequestEntity entity = prepareEntity(session, response, request);
                LOG.info("Saving Requests information.");
                dao.persist(entity);
            }
        }
    }

    private RequestEntity prepareEntity(final SessionEntity session, final Fallible response, final String request) {
        final RequestEntity entity = new RequestEntity();
        entity.setSession(session);
        entity.setRequest(request);
        entity.setErrorcode((long) response.getError().getError());
        entity.setErrormessage(response.getMessage());

        return entity;
    }

    private RequestEntity prepareEntity(final SessionEntity session, final Throwable cause, final String request) {
        final RequestEntity entity = new RequestEntity();
        entity.setSession(session);
        entity.setRequest(request);
        entity.setErrorcode((long) IWSErrors.FATAL.getError());
        // To better facilitate Error analysis, we need the actual error
        // message, hence, in the database this field is not allowed to be null.
        //   However, from the production logs, there are cases where we do not
        // get an error message. So to avoid seeing anymore SQL Constraint
        // Violation Exceptions, we simply check the error message and if it is
        // null - we'll set it to a default error that will hopefully help
        // improving the internal error handling.
        entity.setErrormessage(cause.getMessage() != null ? cause.getMessage() : "No Error Message!");

        return entity;
    }

    private SessionEntity findAndUpdateSession(final AuthenticationToken token) {
        SessionEntity entity = null;

        if (token != null) {
            try {
                entity = dao.findActiveSession(token);
                entity.setModified(new Date());
                dao.persist(entity);
            } catch (AuthenticationException | IdentificationException e) {
                LOG.info(e.getMessage());
                entity = null;
            }
        }

        return entity;
    }
}
