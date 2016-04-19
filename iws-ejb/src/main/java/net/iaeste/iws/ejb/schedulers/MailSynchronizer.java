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
import net.iaeste.iws.common.configuration.InternalConstants;
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

    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Settings settings;
    @Resource private TimerService timerService;

    /**
     * <p>Starts the The Timer Scheduler to handle Mail Synchronization.</p>
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

    /**
     * <p>This is the Mail Synchronize Scheduler, which will run at predefined
     * intervals to synchronize the User/Group information.</p>
     *
     * @param timer Timer information, useful for logging
     */
    @Timeout
    public void synchronize(final Timer timer) {
        LOG.info("Starting to Synchronize Mail System.");
        final long start = System.nanoTime();
        final MailService mailService = new MailService(settings, entityManager);
        int changes = 0;

        // For the suspension & deleting, we need to use an Authentication
        // Object for history information. We're using the System account for
        // this. Even if nothing is to be deleted, we're still fetching the
        // record here, since the Cron job is only running once every 24 hours,
        // we do not care much for performance problems.
        final UserEntity system = entityManager.find(UserEntity.class, InternalConstants.SYSTEM_ACCOUNT);
        final Authentication authentication = new Authentication(system, timer.getInfo().toString());

        // First part, create missing Mailing lists. The service will read a
        // list of all Groups, which currently have no Mailing List and create
        // them.
        changes += mailService.processMissingMailingLists(authentication);

        // Second step is to ensure that all the Aliases is expanded.
        changes += mailService.processAliases(authentication);

        // Third step is to ensure that all Subscriptions are present.
        changes += mailService.processMissingMailingListSubscriptions(authentication);

        // Fourth step, synchronize States between Groups & Lists as well as
        // between Users & Subscriptions
        changes += mailService.synchronizeMailStates();

        // 5. Virtual Mailing Lists
        changes += mailService.synchronizeVirtualLists(authentication);

        final DateFormat dateFormatter = new SimpleDateFormat(IWSConstants.DATE_TIME_FORMAT, IWSConstants.DEFAULT_LOCALE);
        final long duration = (System.nanoTime() - start) / 1000000;

        if (changes > 0) {
            LOG.info("Synchronizing Mail System, with {} changes, took: {}ms, next Timeout: {}.", changes, duration, dateFormatter.format(timer.getNextTimeout()));
        } else {
            LOG.info("Synchronizing Mail System, without changes, took: {}ms, next Timeout: {}.", duration, dateFormatter.format(timer.getNextTimeout()));
        }
    }
}
