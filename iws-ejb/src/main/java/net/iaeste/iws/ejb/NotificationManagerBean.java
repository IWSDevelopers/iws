/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.NotificationManagerBean
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

import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.cdi.IWSBean;
import net.iaeste.iws.ejb.notifications.NotificationManager;
import net.iaeste.iws.ejb.notifications.NotificationMessageGenerator;
import net.iaeste.iws.ejb.notifications.NotificationMessageGeneratorFreemarker;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.NotificationJpaDao;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Singleton
@Local(NotificationManagerLocal.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotificationManagerBean implements NotificationManagerLocal {

    private static final Logger log = LoggerFactory.getLogger(NotificationManagerBean.class);
    @Inject @IWSBean(IWSBean.Type.SECONDARY) private EntityManager mailEntityManager;
    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Notifications notifications;
    @Inject @IWSBean private Settings settings;
    private NotificationDao dao = null;
    private AccessDao accessDao = null;

    @Resource
    private TimerService timerService;
    private Timer timer = null;

    private final Object lock = new Object();

    private boolean processingIsRunning = false;

    /**
     * Setter for the JNDI injected persistence context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param entityManager Transactional Entity Manager instance
     */
    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Setter for the JNDI injected persistence context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param mailEntityManager Transactional Entity Manager instance for mailing list database
     */
    public void setMailEntityManager(final EntityManager mailEntityManager) {
        this.mailEntityManager = mailEntityManager;
    }

    /**
     * Setter for the JNDI injected Settings bean. This allows us to also test
     * the code, by invoking these setters on the instantiated Object.
     *
     * @param settings Settings Bean
     */
    public void setSettings(final Settings settings) {
        if (settings != null) {
            this.settings = settings;
        }
    }

    /**
     * {@inheritDoc}
     */
    @PostConstruct
    public void postConstruct() {
        log.info("post construct");
        dao = new NotificationJpaDao(entityManager);
        accessDao = new AccessJpaDao(entityManager);

        final NotificationMessageGenerator generator = new NotificationMessageGeneratorFreemarker();
        generator.setSettings(settings);
        final NotificationManager notificationManager = new NotificationManager(entityManager, mailEntityManager, settings, generator, true);
        notificationManager.startupConsumers();
        notifications = notificationManager;
    }

    @Override
    public void setNotifications(final Notifications notifications) {
        this.notifications = notifications;
    }

    @Override
    public Notifications getNotifications() {
        return notifications;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final Authentication authentication, final Notifiable obj, final NotificationType type) {
        log.info("New '" + type + "' notification request at NotificationManagerBean");
        try {
            notifications.notify(authentication, obj, type);
        } catch (IWSException e) {
            log.error("Preparing notification failed", e);
        }

        //TODO if to avoid problems during testing, possible fix by providing mocked TimerService
        if (timerService != null) {
            //TODO to enable smooth notification processing, the processing method runs every 60 seconds
//            if (timer == null) {
//                final String clazz = NotificationManagerBean.class.getSimpleName();
                //trying 10s delay ... failed, 20s ... failed, 60s ... ?
//                org.joda.time.DateTime now = new DateTime().plusSeconds(60);
//                LOG.info("scheduling timer at " + now);
//                this.timer = timerService.createTimer(now.toDate(), clazz);
//            }
//            processJobs();
        } else {
            log.debug("There is no TimerService, probably running outside app server");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final UserEntity user) {
        log.info("New 'user' notification request at NotificationManagerBean");
        try {
            notifications.notify(user);
        } catch (IWSException e) {
            log.error("Preparing notification failed", e);
        }

        //TODO if to avoid problems during testing, possible fix by providing mocked TimerService
        if (timerService != null) {
            //TODO to enable smooth notification processing, the processing method runs every 60 seconds
//            if (timer == null) {
//                final String clazz = NotificationManagerBean.class.getSimpleName();
            //trying 10s delay ... failed, 20s ... failed, 60s ... ?
//                org.joda.time.DateTime now = new DateTime().plusSeconds(60);
//                LOG.info("scheduling timer at " + now);
//                this.timer = timerService.createTimer(now.toDate(), clazz);
//            }
            //start processing jobs immediately to avoid possible problems with timer running another thread
//            processJobs();
        } else {
            log.warn("There is no TimerService, probably running outside app server");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final Observer observer) {
        notifications.addObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteObserver(final Observer observer) {
        notifications.deleteObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers() {
        notifications.notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processJobs() {
        notifications.processJobs();
    }

    @Timeout
    private void processJobs(final Timer timer) {
        //TODO remove log messages when the processing works correctly, i.e. there is no need of timer rescheduling.
        //     the problem is that consumers doesn't see their tasks when they are called just after tasks' creation
        this.timer = null;
        log.info("processJobsScheduled started at " + new DateTime());
        notifications.processJobs();
    }

    // Commented for now, since this causes huge stack traces!
    @Schedule(second = "*/30",minute = "*", hour = "*", info="Every 30 seconds", persistent = false)
    private void processJobsScheduled() {
        log.info("processJobsScheduled started at " + new DateTime());
        final boolean run;
        synchronized (lock) {
            if (!processingIsRunning) {
                run = true;
                processingIsRunning = true;
            } else {
                run = false;
            }
        }

        if (run) {
            try {
                notifications.processJobs();
            } catch (Exception e) {
                log.error("Exception caught in notification processing", e);
            } finally {
                synchronized (lock) {
                    processingIsRunning = false;
                }
            }
        }
    }

    public void setTimerService(final TimerService timerService) {
        //TODO for manual setting of TimerService, most probably for testing
        this.timerService = timerService;
    }
}
