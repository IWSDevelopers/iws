/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.StateBean
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
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.monitors.ActiveSessions;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.services.AccountService;
import net.iaeste.iws.ejb.cdi.IWSBean;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
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
import java.util.List;

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
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StateBean {

    private static final Logger LOG = LoggerFactory.getLogger(StateBean.class);

    /**
     * The System Account is the Account used by the Notification system & this
     * Bean to write history information, and log
     */
    private static final long SYSTEM_ACCOUNT = 2553L;

    /**
     * The initial Expiration, is when the Timer should run for the first time,
     * this is set to be in at 2 in the morning, so neither the startup will nor
     * any other peak load periods will be disturbed by a potentially big
     * database operation.<br />
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

    @Inject @IWSBean private Notifications notifications;
    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Settings settings;
    @Resource private TimerService timerService;

    private ActiveSessions activeSessions = null;
    private AccountService service = null;
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
        LOG.info("Starting IWS Initialization.");

        // First, we need to initialize our dependencies
        activeSessions = ActiveSessions.getInstance(settings);
        accessDao = new AccessJpaDao(entityManager);
        service = new AccountService(settings, accessDao, notifications);

        // Just to ensure that at the startup - no other timers exists, which
        // may cause conflicts with the new ones we're adding
        for (final Timer timer : timerService.getTimers()) {
            timer.cancel();
        }

        // Second, we're registering the Timer Service. This will ensure that the
        // Bean is invoked at 2 in the morning and every 24 hours later.
        final TimerConfig config = new TimerConfig(StateBean.class.getSimpleName(), false);
        timerService.createIntervalTimer(INITIAL_EXPIRATION, INTERVAL_DURATION, config);
        final DateFormat dateFormatter = new SimpleDateFormat(IWSConstants.DATE_TIME_FORMAT, IWSConstants.DEFAULT_LOCALE);
        LOG.info("First cleanup run scheduled to begin at " + dateFormatter.format(new Date(INITIAL_EXPIRATION)));

        // Now, remove all deprecated Sessions from the Server. These Sessions
        // may or may not work correctly, since IW4 with JSF is combining the
        // Sessions with a Windows Id, and upon restart - the Windows Id is
        // renewed. Even if it isn't renewed, JSF will not recognize it
        final int deprecated = accessDao.deprecateAllActiveSessions();
        LOG.info("Deprecated {} Stale Sessions.", deprecated);

        // That's it - we're done :-)
        LOG.info("IWS Initialization Completed.");
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
        LOG.info("Timout occurred, will start the Cleanup");
        final long start = System.nanoTime();

        // For the suspension & deleting, we need to use an Authentication
        // Object for history information. We're using the System account for
        // this. Even if nothing is to be deleted, we're still fetching the
        // record here, since the Cron job is only running once every 24 hours,
        // we do not care much for performance problems.
        final UserEntity system = entityManager.find(UserEntity.class, SYSTEM_ACCOUNT);
        final Authentication authentication = new Authentication(system, timer.getInfo().toString());

        // First, let's get rid of those pesky expired sessions. For more
        // information, see the Trac ticket #900.
        removeDeprecatedsessions();

        // Second, we'll deal with accounts which are inactive. For more
        // information, see the Trac ticket #720.
        final int expired = removeUnusedAccounts();
        final int suspended = suspendInactiveAccounts(authentication);
        final int deleted = deleteSuspendedAccounts(authentication);

        final DateFormat dateFormatter = new SimpleDateFormat(IWSConstants.DATE_TIME_FORMAT, IWSConstants.DEFAULT_LOCALE);
        final long duration = (System.nanoTime() - start) / 1000000;
        LOG.info("Cleanup took: {}ms (expired {}, suspended {} & deleted {}), next Timeout: {}", duration, expired, suspended, deleted, dateFormatter.format(timer.getNextTimeout()));
    }

    /**
     * Remove deprecated Sessions.
     */
    private int removeDeprecatedsessions() {
        // First, let's calculate the time of expiry. All Sessions with a
        // modification date before this, will be deprecated
        final Long timeout = settings.getMaxIdleTimeForSessions();
        final Date date = new Date(new Date().getTime() - timeout);
        int count = 0;

        // Iterate over the currently active sessions and remove them.
        final List<SessionEntity> sessions = accessDao.findActiveSessions();
        for (final SessionEntity session : sessions) {
            if (session.getModified().before(date)) {
                // Remove the found Session from the ActiveSessions Registry ...
                activeSessions.removeToken(session.getSessionKey());
                // ... and remove it from the database as well
                accessDao.deprecateSession(session);
                count++;
            }
        }

        return count;
    }

    /**
     * Remove unused Accounts, i.e. Account with status NEW and which have not
     * been activated following a period of x days.
     *
     * @return Number of Expired (never activated) Accounts
     */
    private int removeUnusedAccounts() {
        final Long days = settings.getAccountUnusedRemovedDays();
        LOG.debug("Checking of any accounts exists which has expired, i.e. status NEW after {} days.", days);
        int accounts = 0;

        // We have a User Account, that was never activated. This we can
        // delete completely
        final List<UserEntity> newUsers = accessDao.findAccountsWithState(UserStatus.NEW, days);
        for (final UserEntity user : newUsers) {
            if (user.getId() != SYSTEM_ACCOUNT) {
                LOG.info("Deleting Expired NEW Account for {} {} <{}>.", user.getFirstname(), user.getLastname(), user.getUsername());
                service.deleteNewUser(user);
                accounts++;
            }
        }

        return accounts;
    }

    /**
     * Suspend inactive users. Accounts which are currently having status
     * ACTIVE, but have not been used after z days, will be set to SUSPENDED.
     *
     * @return Number of Suspended Accounts
     */
    private int suspendInactiveAccounts(final Authentication authentication) {
        // Note, that we have 2 problems here. The first problem is to fetch all
        // Users, who've been inactive for a a period of x days. This is the
        // permanent problem to solve with this function, since otherwise
        // inactive accounts, i.e. accounts where no sessions exists will be
        // dealt with also.
        //   However, it leaves remaining accounts, which was migrated and have
        // status ACTIVE, but was never used. Dealing with these accounts is
        // beyond the scope of this Bean - and must be dealt with separately. It
        // is easy to find these, since the SALT they have for their encrypted
        // Passwords is either "undefined" or "heartbleed".
        //   Accounts which have not been used since the Migration from IW3 to
        // IWS in January 2014, was given the "undefined" SALT, since IW3 used a
        // simple MD5 checksum, and IWS uses a Salted SHA2. As of September
        // 2014, 1.645 Accounts still have this Salt. Which means these Accounts
        // can all be considered abandoned or deprecated.
        //   In April 2014, an OpenSSL flaw submerged dubbed Heartbleed. All
        // Accounts was re-salted with the salt "heartbleed", to force a renewal
        // of the Passwords. As of September 2014, 375 Accounts still have this
        // Salt, and have thus never updated the Passwords. However, Re-salting
        // was never applied to the originally migrated Accounts, so these will
        // be caught by this method.
        //   The complexity of this Update is rather high, since we have to look
        // at a number of parameters, which either is or should be indexed! This
        // is an SQL query to solve the issue, which must be converted to JPA:
        // -----------------------------------
        // select
        //   s.user_id,
        //   s.max(modified)
        // from
        //   sessions s,
        //   users u
        // where s.user_id = u.id
        //   and u.status = 'ACTIVE'
        //   and modified < 'DATE'::date
        // group by user_id
        // order by max(modified);
        // -----------------------------------
        final Long days = settings.getAccountInactiveDays();
        LOG.info("Fetching the list of Users to be suspended.");
        final List<UserEntity> users = accessDao.findInactiveAccounts(days);
        if (!users.isEmpty()) {
            for (final UserEntity user : users) {
                LOG.info("Suspending the User {} {} <{}>.", user.getFirstname(), user.getLastname(), user.getUsername());
                service.suspendUser(authentication, user);
            }
        }

        // For now - just returning the number of Records
        return users.size();
    }

    /**
     * Delete inactive users. Accounts which have status SUSPENDED will after a
     * period of y days be deleted, meaning that all private information will be
     * removed, and account will have the status set to DELETED.
     *
     * @return Number of Deleted Accounts
     */
    private int deleteSuspendedAccounts(final Authentication authentication) {
        final Long days = settings.getAccountSuspendedDays();
        LOG.debug("Checking if Accounts exists with status SUSPENDED after {} days.", days);
        int accounts = 0;

        final List<UserEntity> suspendedUsers = accessDao.findAccountsWithState(UserStatus.SUSPENDED, days);
        if (!suspendedUsers.isEmpty()) {
            for (final UserEntity user : suspendedUsers) {
                try {
                    LOG.info("Deleting Suspended Account for {} {} <{}>.", user.getFirstname(), user.getLastname(), user.getUsername());
                    service.deletePrivateData(authentication, user);
                    accounts++;
                } catch (IWSException e) {
                    LOG.warn("Unable to delete the Account for {} {} <{}>, reason: {}", user.getFirstname(), user.getLastname(), user.getUsername(), e.getMessage());
                }
            }
        }

        return accounts;
    }
}
