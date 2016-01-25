/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.emails.MailSynchronizer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.emails;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.services.MailService;
import net.iaeste.iws.ejb.cdi.IWSBean;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.UserEntity;
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

/**
 * The Mail Synchronize Bean is a Scheduler which will ensure that the IWS User
 * and Group changes is in reflected on the mail system, so e-mail addresses
 * will always work.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MailSynchronizer {

    private static final Logger LOG = LoggerFactory.getLogger(MailSynchronizer.class);

    /**
     * The System Account is the Account used by the Notification system & this
     * Bean to write history information, and log
     */
    private static final long SYSTEM_ACCOUNT = 2553L;

    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Settings settings;
    @Resource private TimerService timerService;

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
        LOG.info("Starting IWS Mail Synchronize Bean.");

        // Registering the Timer Service. This will ensure that the Scheduler
        // is invoked at frequent intervals.
        final TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo("IWS Mail Synchronize");
        timerConfig.setPersistent(false);
        final ScheduleExpression expression = new ScheduleExpression();
        final String[] time = settings.getMailSynchronizeTime().split(":", 2);
        expression.hour(time[0]).minute(time[1]);
        timerService.createCalendarTimer(expression, timerConfig);
        LOG.info("First synchronize run scheduled to begin at {}", expression);
    }

    // =========================================================================
    // Timeout Functionality
    // =========================================================================

    /**
     * <p>Timeout Method, which will start to synchronize mail settings.</p>
     *
     * <p>Mailing lists are a central part of the system. However, there has
     * been several problems with mailing lists getting out of sync or that
     * someone is present who shouldn't be. This method will ensure the lists
     * and subscribers to the lists are correct.</p>
     *
     * @param timer Timer information, useful for logging
     */
    @Timeout
    public void doCleanup(final Timer timer) {
        LOG.info("Starting to Synchronize Mail System.");
        final long start = System.nanoTime();
        final MailService mailService = new MailService(settings, entityManager);

        // For the suspension & deleting, we need to use an Authentication
        // Object for history information. We're using the System account for
        // this. Even if nothing is to be deleted, we're still fetching the
        // record here, since the Cron job is only running once every 24 hours,
        // we do not care much for performance problems.
        final UserEntity system = entityManager.find(UserEntity.class, SYSTEM_ACCOUNT);
        final Authentication authentication = new Authentication(system, timer.getInfo().toString());

        // 1. Mailing Lists
        //    Mailing Lists is one of the core parts of the IWS. Each Group can
        //    have either a public, a private or both kinds of mailing lists.
        //    And Users can be subscribed to either one or the other.
        //      Please see the GroupType for more information about what kind
        //    of Mailing Lists a Group can have.
        mailService.processMissingMailingLists(authentication);

        // 2. Aliases.
        //    Aliases is a feature, whereby a Group which have changed name can
        //    keep the old name for a period. The period is determined by an
        //    expiration date associated with the Alias. But it can also be that
        //    there is no expiration. Most National Committees going from
        //    Cooperating Institution to Full Member require a transition
        //    period, before their new address is known to all. Others, like
        //    the Board have aliases of a permanent basis, like president - the
        //    president alias must always work.
        //      Aliases are always re-processed, as it can be that they change.
        //    And as there is a rather limited amount of Aliases, reducing the
        //    logic for handling these makes it easier.
        //      Aliases is treated as a public mailing list, but is marked as a
        //    "limited alias", as they can expire. But otherwise it is treated
        //    just as any other public mailing list.
        mailService.processAliases(authentication);

        // 3. Mailing List Subscriptions
        //    Users may belong to an arbitrary number of Groups, and their
        //    relation to a Group is also reflected in their inclusion on
        //    Mailing Lists. Regardless of the relationship, a User will always
        //    be present on the Lists which the Group may have, so if a Group
        //    may have two Lists (Public & Private), the User has a relation to
        //    both. However, each relation also have a state, and only if the
        //    User according to the Group Relation is allowed to receive and
        //    write to a list, will the state allow it.
        mailService.processMissingMailingListSubscriptions(authentication);

        // 4. Synchronize State.
        //    The above points deals with the actual lists and subscriptions,
        //    but not with the changing state. This must also be dealt with,
        //    and this is why it is important to re-synchronize the state
        //    constantly. If a User is suspended, re-activated, deleted or
        //    there is simple changes to their User Group relation - must be
        //    updated, so these changes is reflected upon their subscription.
        mailService.synchronizeMailStates();

        // 5. Virtual Mailing Lists
        //    In IWS, almost all Mailing Lists is build based on the rules for
        //    a given Group. However, there is also exceptions to this rule,
        //    and these fall under the Virtual Mailing Lists category.
        //      A Virtual Mailing List is one where the Subscription Rule is
        //    rather special. By default, there's is 2 such Mailing Lists, the
        //    NC's Mailing Lists, going to all National Committees, Board &
        //    International Groups. And the Announce List, which is limited
        //    regarding who may write to it, but otherwise goes to everybody.
        //      As the Mailing List Synchronization above cannot and should not
        //    be aware of Rules regarding Virtual Mailing lists, it is important
        //    that we handle Virtual Mailing Lists as the last part, so we rules
        //    can be adjusted accordingly.
        mailService.synchronizeVirtualLists(authentication);

        final DateFormat dateFormatter = new SimpleDateFormat(IWSConstants.DATE_TIME_FORMAT, IWSConstants.DEFAULT_LOCALE);
        final long duration = (System.nanoTime() - start) / 1000000;
        LOG.info("Synchronizing Mail System took: {}ms, next Timeout: {}.", duration, dateFormatter.format(timer.getNextTimeout()));
    }
}
