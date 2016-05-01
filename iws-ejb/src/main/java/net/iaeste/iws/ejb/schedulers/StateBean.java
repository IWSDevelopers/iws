/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.ejb.schedulers;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.common.configuration.InternalConstants;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.monitors.ActiveSessions;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.services.AccountService;
import net.iaeste.iws.ejb.cdi.IWSBean;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
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
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * <p>When the IWS is starting up, we need to ensure that the current State is
 * such that the system will work correctly. The purpose of this Bean is simply
 * to ensure that.</p>
 *
 * <p>The Bean has two parts. First is what happens at startup, the second is to
 * managed a Cron like service, which will run every night during the time where
 * the system has the lowest load.</p>
 *
 * <p>At startup, the Bean will Clear all existing active Sessions, so nothing
 * exists in the database. This will prevent existing Sessions from continuing
 * to work, but as there may be other issues around a re-start, it is the least
 * harmful.</p>
 *
 * <p>The Cron part will run every night, and both discard deprecated Sessions,
 * but also update Accounts. To avoid that inactive/suspended/dead Accounts will
 * clutter up the system.</p>
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

    @Inject @IWSBean private Notifications notifications;
    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Settings settings;
    @Resource private TimerService timerService;

    private ActiveSessions activeSessions = null;
    private ExchangeDao exchangeDao = null;
    private AccessDao accessDao = null;

    private AccountService service = null;

    // =========================================================================
    // Startup Functionality
    // =========================================================================

    /**
     * To ensure that the system State is always consistent, we have two things
     * that must be accomplished. First thing is to load the former state and
     * ensure that the system is working using this as base.<br />
     *   Second part is to have a timer service (cron job), which will once per
     * day run and perform certain cleanup actions.<br />
     *   This method is run once the Bean is initialized and will perform two
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
        accessDao = new AccessJpaDao(entityManager, settings);
        exchangeDao = new ExchangeJpaDao(entityManager, settings);
        service = new AccountService(settings, accessDao, notifications);

        // Second, we're registering the Timer Service. This will ensure that the
        // Bean is invoked daily at 2 in the morning.
        final TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo("IWS State Cleaner");
        timerConfig.setPersistent(false);
        final ScheduleExpression expression = new ScheduleExpression();
        final String[] time = settings.getRunCleanTime().split(":", 2);
        expression.hour(time[0]).minute(time[1]);
        timerService.createCalendarTimer(expression, timerConfig);
        LOG.info("First cleanup run scheduled to begin at {}", expression);

        if (settings.resetSessionsAtStartup()) {
            // Now, remove all deprecated Sessions from the Server. These Sessions
            // may or may not work correctly, since IW4 with JSF is combining the
            // Sessions with a Windows Id, and upon restart - the Windows Id is
            // renewed. Even if it isn't renewed, JSF will not recognize it
            final int deprecated = accessDao.deprecateAllActiveSessions();
            LOG.info("Deprecated {} Stale Sessions.", deprecated);
        } else {
            loadActiveTokens();
        }

        // That's it - we're done :-)
        LOG.info("IWS Initialization Completed.");
    }

    private void loadActiveTokens() {
        final List<SessionEntity> sessions = accessDao.findActiveSessions();
        for (final SessionEntity entity : sessions) {
            activeSessions.registerToken(entity.getSessionKey(), entity.getCreated());
        }
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
        LOG.info("Timeout occurred, will start the Cleanup");
        final long start = System.nanoTime();

        // For the suspension & deleting, we need to use an Authentication
        // Object for history information. We're using the System account for
        // this. Even if nothing is to be deleted, we're still fetching the
        // record here, since the Cron job is only running once every 24 hours,
        // we do not care much for performance problems.
        final UserEntity system = entityManager.find(UserEntity.class, InternalConstants.SYSTEM_ACCOUNT);
        final Authentication authentication = new Authentication(system, timer.getInfo().toString());

        // First, let's get rid of those pesky expired sessions. For more
        // information, see the Trac ticket #900.
        removeDeprecatedSessions();

        // Second, we'll handle Offers which have expired.
        runExpiredOfferProcessing();

        // third, we'll deal with accounts which are inactive. For more
        // information, see the Trac ticket #720.
        final int expired = removeUnusedNewAccounts();
        final int suspended = suspendInactiveAccounts(authentication);
        final int deleted = deleteSuspendedAccounts(authentication);

        final DateFormat dateFormatter = new SimpleDateFormat(IWSConstants.DATE_TIME_FORMAT, IWSConstants.DEFAULT_LOCALE);
        final long duration = (System.nanoTime() - start) / 1000000;
        LOG.info("Cleanup took: {}ms (Users expired {}, suspended {} & deleted {}), next Timeout: {}", duration, expired, suspended, deleted, dateFormatter.format(timer.getNextTimeout()));
    }

    /**
     * Remove deprecated Sessions.
     */
    private int removeDeprecatedSessions() {
        // First, let's calculate the time of expiry. All Sessions with a
        // modification date before this, will be deprecated
        final Long timeout = settings.getMaxIdleTimeForSessions();
        final Date date = new Date(new Date().getTime() - timeout);
        int count = 0;

        // Iterate over the currently active sessions and remove them.
        final List<SessionEntity> sessions = accessDao.findActiveSessions();
        for (final SessionEntity session : sessions) {
            if (session.getModified().before(date.toDate())) {
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
     * Runs the Offer Expiration. Although the state for an Offer is part of
     * both the Offer & Offer Shares, only the Offer itself is updated, thus
     * avoiding that any information is lost.<br />
     * Offers expire if the Nomination Deadline passed. Please see Trac Ticket
     * #1020 for more on the discussion. Please note, that although the code
     * here is passing an auditing, there has been a problem with Offers that
     * has suddenly expired, please see Trac ticket #1052 for details. For this
     * reason, the method is now having increased logging.
     */
    private void runExpiredOfferProcessing() {
        try {
            final List<OfferEntity> offers = exchangeDao.findExpiredOffers(new java.util.Date());
            LOG.info("Found {} Offers to expire.", offers.size());

            for (final OfferEntity offer : offers) {
                offer.setStatus(OfferState.EXPIRED);

                accessDao.persist(offer);
                LOG.info("Offer {} has expired.", offer.getRefNo());
            }
        } catch (IllegalArgumentException | IWSException e) {
            LOG.error("Error in processing expired offers", e);
        }
    }

    /**
     * Remove unused Accounts, i.e. Account with status NEW and which have not
     * been activated following a period of x days.
     *
     * @return Number of Expired (never activated) Accounts
     */
    private int removeUnusedNewAccounts() {
        final Long days = settings.getAccountUnusedRemovedDays();
        LOG.debug("Checking of any accounts exists which has expired, i.e. status NEW after {} days.", days);
        int accounts = 0;

        // We have a User Account, that was never activated. This we can
        // delete completely
        final List<UserEntity> newUsers = accessDao.findAccountsWithState(UserStatus.NEW, days);
        for (final UserEntity user : newUsers) {
            // Just to make sure that we're not removing the System Account, as
            // that would be rather fatal!
            if (user.getId() != InternalConstants.SYSTEM_ACCOUNT) {
                LOG.info("Deleting Expired NEW Account for {} {} <{}>.", user.getFirstname(), user.getLastname(), user.getUsername());
                service.deleteNewUser(user);
                accounts++;
            }
        }

        return accounts;
    }

    /**
     * <p>This will suspend all inactive Accounts, meaning an account which has
     * not been used for a period of time, which is defined in the IWS
     * Properties.</p>
     *
     * <p>Suspension will change the state of the Account from ACTIVE to
     * SUSPENDED. Note that National Secretaries is except from this rule, so
     * any currently active Committee will always have at least one active
     * account.</p>
     *
     * @return Number of Suspended Accounts
     */
    private int suspendInactiveAccounts(final Authentication authentication) {
        final Long days = settings.getAccountInactiveDays();
        LOG.info("Fetching the list of Users to be suspended.");
        final List<UserEntity> users = accessDao.findInactiveAccounts(days);
        int suspended = 0;

        if (!users.isEmpty()) {
            for (final UserEntity user : users) {
                if (maySuspendUser(user)) {
                    LOG.info("Attempting to suspend the User {} {} <{}>.", user.getFirstname(), user.getLastname(), user.getUsername());
                    service.suspendUser(authentication, user);
                    suspended++;
                } else {
                    LOG.info("Ignoring inactive National Secretary {} {} <{}>.", user.getFirstname(), user.getLastname(), user.getUsername());
                }
            }
        }

        return suspended;
    }

    /**
     * <p>It has been an ongoing problem that some accounts have been suspended,
     * although they belong to the National Secretary of a Country. This is
     * problematic, as it prevents that the person can do a correct hand-over if
     * been inactive for too long.</p>
     *
     * <p>Instead, this check is added, and if a User is Owner of either an
     * Administrative, International, Member or National (Staff) Group, then it
     * is skipped.</p>
     *
     * @param user The User to check, if may be suspended
     * @return True if the User may be suspended, otherwise false
     */
    private boolean maySuspendUser(final UserEntity user) {
        final List<UserGroupEntity> relations = accessDao.findAllUserGroups(user);
        final Set<GroupType> allowed = EnumSet.of(GroupType.ADMINISTRATION, GroupType.INTERNATIONAL, GroupType.MEMBER, GroupType.NATIONAL);
        boolean maySuspend = true;

        for (final UserGroupEntity relation : relations) {
            final GroupType type = relation.getGroup().getGroupType().getGrouptype();
            final GroupStatus status = relation.getGroup().getStatus();
            final long roleId = relation.getRole().getId();

            if ((status == GroupStatus.ACTIVE) &&
                (roleId == InternalConstants.ROLE_OWNER) &&
                 allowed.contains(type)) {
                maySuspend = false;
                break;
            }
        }

        return maySuspend;
    }

    /**
     * Delete inactive users. Accounts which have status SUSPENDED will after a
     * period of y days be deleted, meaning that all private information will be
     * removed, and account will have the status set to DELETED.<br />
     *   Note, that although accounts should be deleted, we cannot leave member
     * groups without an owner. Accounts which is currently owner, will simply
     * be ignored. The rule applies only to currently active Groups. Groups
     * which have been suspended will be ignored.
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
                    LOG.info("Attempting to delete Suspended Account for {} {} <{}>.", user.getFirstname(), user.getLastname(), user.getUsername());
                    service.deletePrivateData(authentication, user);
                    accounts++;
                } catch (IWSException e) {
                    LOG.debug(e.getMessage(), e);
                    LOG.warn("Unable to delete the Account for {} {} <{}>, reason: {}", user.getFirstname(), user.getLastname(), user.getUsername(), e.getMessage());
                }
            }
        }

        return accounts;
    }
}
