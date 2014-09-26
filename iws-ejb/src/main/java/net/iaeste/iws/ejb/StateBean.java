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
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * When the IWS is starting up, we need to ensure that the current State is such
 * that the system will work correctly. The purpose of this Bean is simply to
 * ensure that.<br />
 *   The Bean has two parts. First is what happens at startup, the second is to
 * managed a Cron like service, which will run every night during the time where
 * the system has the lowest load.<br />
 *   At startup, the Bean will Clear all existing active Sessions, so nothing
 * exists in the database. This will prevent existing Sessions from continuing
 * to work, but as there may be other issues around a re-start, it is the least
 * harmful.<br />
 *   The Cron part will run every night, and both discard deprecated Sessions,
 * but also update Accounts. To avoid that inactive/suspended/dead Accounts will
 * clutter up the system.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Startup
@Singleton
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StateBean {

    private static final Logger log = LoggerFactory.getLogger(StateBean.class);

    /**
     * Simple Formatter Object, used to log when the cleaning will nect occur.
     */
    private static final DateFormat formatter = new SimpleDateFormat(IWSConstants.DATE_TIME_FORMAT, IWSConstants.DEFAULT_LOCALE);

    /**
     * The initial Expiration, is when the Timer should run for the first time,
     * this is set to be in a minute, so the startup will not be disturbed by a
     * potentially big database operation.<br />
     *   The initial time is calculated using our internal Date Object which is
     * set to use midnight of the startup-date as base. This is a time in the
     * past, so to ensure that it is starting at 2 in the morning (time with the
     * lowest activity), we simply add 26 hours to it.
     */
    private static final Long INITIAL_EXPIRATION = new net.iaeste.iws.api.util.Date().toDate().getTime() + (26 * 60 * 60 * 1000L);

    /**
     * The interval between invocations of the Timer is set to be 24 hours,
     * which is infrequent enough to ensure that it is not disrupting normal
     * operations, yet frequent enough to ensure that garbage is removed from
     * the system in a timely fashion.
     */
    private static final Long INTERVAL_DURATION = 86400000L;

    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Settings settings;
    @Resource private TimerService timerService;

    private ActiveSessions activeSessions = null;
    private AccessDao accessDao = null;

    // =========================================================================
    // Startup Functionality
    // =========================================================================

    /**
     * To ensure that the system State is always consistent, we have two things
     * that must be accomplished. First thing is to load the former state and
     * ensure that the system is working using this as base.<br />
     *   Second part is to have a timer service (cron job), which will once per
     * day run and perform certain cleanup actions.<br />
     *   This method is run once the Bean is inistialized and will perform two
     * things, first it will initialize the Timer Service, so it can run at
     * frequent Intervals, secondly, it will initialize the Sessions.<br />
     *   Heavier maintenance operations like cleaning up accounts is left for
     * the Timed Service to handle, since it may otherwise put the server under
     * unnecessary pressure during the initial Startup Phase.
     */
    @PostConstruct
    public void startup() {
        log.info("Starting IWS Initialization.");

        // First, we need to initialize our dependencies
        activeSessions = ActiveSessions.getInstance(settings);
        accessDao = new AccessJpaDao(entityManager);

        // Just to ensure that at the startup - no other timers exists, which
        // may cause conflicts with the new ones we're adding
        for (final Timer timer : timerService.getTimers()) {
            timer.cancel();
        }

        // Second, we're registering the Timer Service. This will ensure that the
        // Bean is invoked at 2 in the morning and every 24 hours later.
        final TimerConfig config = new TimerConfig(StateBean.class.getSimpleName(), false);
        timerService.createIntervalTimer(INITIAL_EXPIRATION, INTERVAL_DURATION, config);
        log.info("First cleanup run scheduled to begin at " + formatter.format(new Date(INITIAL_EXPIRATION)));

        // Now, remove all deprecated Sessions from the Server. These Sessions
        // may or may not work correctly, since IW4 with JSF is combining the
        // Sessions with a Windows Id, and upon restart - the Windows Id is
        // renewed. Even if it isn't renewed, JSF will not recognize it
        final int deprecated = accessDao.deprecateAllActiveSessions();
        log.info("Deprecated {} Sessions.", deprecated);

        // That's it - we're done :-)
        log.info("IWS Initialization Completed.");
    }

    // =========================================================================
    // Timeout Functionality
    // =========================================================================

    /**
     * Timeout Method, which will start the frequent cleaning.
     *
     * @param timer Timer information, useful for logging
     */
    @Timeout
    public void doCleanup(final Timer timer) {
        log.info("Timout occurred, will start the Cleanup");
        final long start = System.currentTimeMillis();

        // First, let's get rid of those pesky expired sessions. For more
        // information, see the Trac ticket #900.
        removeDeprecatedsessions();

        // Second, we'll deal with accounts which are inactive. For more
        // information, see the Trac ticket #720.
        removeUnusedAccounts();
        suspendInactiveAccounts();
        deleteSuspendedAccounts();

        final long duration = System.currentTimeMillis() - start;
        log.info("Cleanup took: " + duration + " ms, next Timeout: " + formatter.format(timer.getNextTimeout()));
    }

    /**
     * Remove deprecated Sessions.
     */
    private void removeDeprecatedsessions() {
        // First, let's calculate the time of expiry. All Sessions with a
        // modification date before this, will be deprecated
        final Long timeout = settings.getMaxIdleTimeForSessions();
        final Date date = new Date(new Date().getTime() - timeout);

        // Iterate over the currently active sessions and remove them.
        for (final SessionEntity session : accessDao.findActiveSessions()) {
            if (session.getModified().before(date)) {
                accessDao.deprecateSession(session);
            }
        }

        // As we've removed the dead sessions from the database, let's also
        // remove them from the Active Session Map.
        activeSessions.findAndRemoveExpiredTokens();
    }

    /**
     * Remove unused Accounts, i.e. Account with status NEW and which have not
     * been activated following a period of x days.
     */
    private void removeUnusedAccounts() {
        log.info("TODO: Implement Proper Account Handling from ticket #720.");
    }

    /**
     * Suspend inactive users. Accounts which are currently having status
     * ACTIVE, but have not been used after z days, will be set to SUSPENDED.
     */
    private void suspendInactiveAccounts() {
        log.info("TODO: Implement Proper Account Handling from ticket #720.");
    }

    /**
     * Delete inactive users. Accounts which have status SUSPENDED will after a
     * period of y days be deleted, meaning that all private information will be
     * removed, and account will have the status set to DELETED.
     */
    private void deleteSuspendedAccounts() {
        log.info("TODO: Implement Proper Account Handling from ticket #720.");
    }
}
