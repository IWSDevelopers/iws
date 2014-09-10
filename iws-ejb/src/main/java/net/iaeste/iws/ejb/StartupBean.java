/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.StartupBean
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.monitors.ActiveSessions;
import net.iaeste.iws.ejb.cdi.IWSBean;
import net.iaeste.iws.persistence.entities.SessionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Startup
@Singleton
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StartupBean {

    private static DateFormat formatter = new SimpleDateFormat(IWSConstants.TIMESTAMP_FORMAT, IWSConstants.DEFAULT_LOCALE);
    private static final Logger log = LoggerFactory.getLogger(StorageBean.class);

    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Settings settings;
    private ActiveSessions activeSessions = null;

    @PostConstruct
    public void startup() {
        log.info("Starting the IWS");

        log.debug("Preparing the Active Session Mapping");
        activeSessions = ActiveSessions.getInstance(settings);

        log.debug("Loading all currently Active Sessions");
        final List<SessionEntity> sessions = loadActiveSessions();

        log.debug("Deprecating all expired Sessions");
        deprecateInactiveSessions(sessions);

        log.debug("Registering not expired Session");
        registerActiveSessions(sessions);

        log.info("IWS setup completed");
    }

    private List<SessionEntity> loadActiveSessions() {
        final Query query = entityManager.createNamedQuery("session.findActive");
        return query.getResultList();
    }

    private void registerActiveSessions(final List<SessionEntity> sessions) {
        for (final SessionEntity entity : sessions) {
            if ("0".equals(entity.getDeprecated())) {
                activeSessions.registerToken(entity.getSessionKey());
            }
        }
    }

    private void deprecateInactiveSessions(final List<SessionEntity> sessions) {
        final Map<String, SessionEntity> tokenMap = tokenizeList(sessions);
        for (final String token : activeSessions.findAndRemoveExpiredTokens()) {
            log.debug("Deprecating inactive session with token " + token);
            deprecateSession(tokenMap.get(token));
        }
    }

    private Map<String, SessionEntity> tokenizeList(final List<SessionEntity> sessions) {
        final Map<String, SessionEntity> map = new HashMap<>(sessions.size());

        for (final SessionEntity entity : sessions) {
            map.put(entity.getSessionKey(), entity);
        }

        return map;
    }

    private void deprecateSession(final SessionEntity entity) {
        entity.setDeprecated(formatter.format(new Date()));
        entity.setSessionData(null);
        entity.setModified(new Date());

        entityManager.persist(entity);
    }
}
