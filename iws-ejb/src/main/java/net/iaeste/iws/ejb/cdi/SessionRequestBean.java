/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.cdi.DecoratorBean
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.cdi;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.persistence.entities.RequestEntity;
import net.iaeste.iws.persistence.entities.SessionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

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

    private static final Logger log = LoggerFactory.getLogger(SessionRequestBean.class);

    @Inject @IWSBean private EntityManager entityManager;

    /**
     * Interceptors are using the InvocationContext to help access all
     * information about the request. Very useful, and this method is splitting
     * this information up so it can be used to invoke one of the other methods
     * in this Class.<br />
     *   Unfortunately, Interceptors and JTA doesn't mix, so the usage of this
     * method via Interceptors is causing runtime errors.
     *
     * @param invocation Invocation Context
     * @param obj Response Object
     */
    public void updateSession(final InvocationContext invocation, final Object obj) {
        if (invocation != null) {
            final Object[] parameters = invocation.getParameters();

            if ((parameters != null) && (parameters.length == 2) && (parameters[0] instanceof AuthenticationToken) && (obj != null) && (obj instanceof Fallible)) {
                final String request = invocation.getMethod().getName();
                final AuthenticationToken token = (AuthenticationToken) parameters[0];
                final Fallible response = (Fallible) obj;

                if ((obj != null) && (parameters[1] instanceof Serializable)) {
                    saveRequest(request, token, response, (Serializable) parameters[1]);
                } else {
                    saveRequest(request, token, response);
                }
            }
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public <R extends Fallible> R saveRequest(final String request, final AuthenticationToken token, final R response, final Serializable obj) {
        final SessionEntity session = findAndUpdateSession(token);

        if (!response.getError().equals(IWSErrors.SUCCESS)) {
            if (session != null) {
                final RequestEntity entity = prepareEntity(session, request, response);
                if ((obj != null) && Objects.equals(response.getError(), IWSErrors.FATAL)) {
                    entity.setSessionData(serialize(obj));
                }
                log.info("Saving Requests information with the request Object.");
                entityManager.persist(entity);
            } else {
                log.warn("Attempted to save request information for deprecated Session.");
            }
        }

        return response;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public <R extends Fallible> R saveRequest(final String request, final AuthenticationToken token, final R response) {
        final SessionEntity session = findAndUpdateSession(token);

        if (!response.getError().equals(IWSErrors.SUCCESS)) {
            if (session != null) {
                final RequestEntity entity = prepareEntity(session, request, response);
                log.info("Saving Requests information.");
                entityManager.persist(entity);
            } else {
                log.warn("Attempted to save request information for deprecated Session.");
            }
        }

        return response;
    }

    private <R extends Fallible> RequestEntity prepareEntity(final SessionEntity session, final String request, final R response) {
        final RequestEntity entity = new RequestEntity();
        entity.setSession(session);
        entity.setRequest(request);
        entity.setErrorcode((long) response.getError().getError());
        entity.setErrormessage(response.getMessage());

        return entity;
    }

    private SessionEntity findAndUpdateSession(final AuthenticationToken token) {
        final String jql = "select s from SessionEntity s where s.sessionKey = :key";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("key", token.getToken());
        final List<SessionEntity> sessions = query.getResultList();

        final SessionEntity entity;
        if (sessions.size() == 1 && sessions.get(0).getDeprecated() != null) {
            entity = sessions.get(0);

            // Before returning the found Entity, let's just update the last
            // usage to now.
            entity.setModified(new Date());
            entityManager.persist(entity);
        } else {
            entity = null;
        }

        return entity;
    }

    private <T extends Serializable> byte[] serialize(final T data) {
        final byte[] result;

        if (data != null) {
            try (final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                 final GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
                 final ObjectOutputStream objectStream = new ObjectOutputStream(zipStream)) {

                objectStream.writeObject(data);
                objectStream.close();

                result = byteStream.toByteArray();
            } catch (IOException e) {
                throw new VerificationException(e);
            }
        } else {
            result = null;
        }

        return result;
    }

}
