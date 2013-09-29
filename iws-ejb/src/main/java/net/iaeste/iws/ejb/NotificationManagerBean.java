/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.notifications.NotificationManager;
import net.iaeste.iws.ejb.notifications.NotificationMessageGeneratorFreemarker;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.NotificationJpaDao;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Singleton
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotificationManagerBean implements NotificationManagerLocal {
    private static final Logger LOG = Logger.getLogger(NotificationManagerBean.class);
    private EntityManager iwsEntityManager = null;
    private EntityManager mailingListEntityManager = null;
    private NotificationDao dao = null;
    private AccessDao accessDao = null;
    private Notifications notifications = null;

    @Resource
    private TimerService timerService;
    private Timer timer = null;

    private final Object LOCK = new Object();

    private boolean processingIsRunning = false;

    /**
     * Setter for the JNDI injected persistence context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param iwsEntityManager Transactional Entity Manager instance
     */
    @PersistenceContext(unitName = "iwsDatabase")
    public void setIwsEntityManager(final EntityManager iwsEntityManager) {
        this.iwsEntityManager = iwsEntityManager;
    }

    /**
     * Setter for the JNDI injected persistence context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param mailingListEntityManager Transactional Entity Manager instance for mailing list database
     */
    @PersistenceContext(unitName = "mailingListDatabase")
    public void setMailingListEntityManager(final EntityManager mailingListEntityManager) {
        this.mailingListEntityManager = mailingListEntityManager;
    }

    /**
     * {@inheritDoc}
     */
    @PostConstruct
    public void postConstruct() {
        dao = new NotificationJpaDao(iwsEntityManager);
        accessDao = new AccessJpaDao(iwsEntityManager);

        final NotificationManager notificationManager = new NotificationManager(iwsEntityManager, mailingListEntityManager, new NotificationMessageGeneratorFreemarker(), true);
//        final NotificationManager notificationManager = new NotificationManager(iwsEntityManager, iwsEntityManager, new NotificationMessageGeneratorFreemarker(), true);
        notificationManager.startupConsumers();
        notifications = notificationManager;

//        final String clazz = NotificationManagerBean.class.getSimpleName();
//        timer = timerService.createTimer(new Date().toDate(), clazz);
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
        try {
            notifications.notify(authentication, obj, type);
        } catch (IWSException e) {
            LOG.error("Preparing notification failed", e);
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
            LOG.debug("There is no TimerService, probably running outside app server");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final UserEntity user) {
        try {
            notifications.notify(user);
        } catch (IWSException e) {
            LOG.error("Preparing notification failed", e);
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
            LOG.warn("There is no TimerService, probably running outside app server");
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
        LOG.info("processJobsScheduled started at " + new DateTime());
        notifications.processJobs();
    }

    @Schedule(minute = "*/1", hour = "*", info="Every 60 seconds")
    private void processJobsScheduled() {
        //TODO remove log messages when the processing works correctly, i.e. there is no need of timer rescheduling.
        //     the problem is that consumers doesn't see their tasks when they are called just after tasks' creation
//        LOG.info("processJobsScheduled started at " + new DateTime());
        final boolean run;
        synchronized (LOCK) {
            run = processingIsRunning==false;
        }

        if (run) {
            synchronized (LOCK) {
                processingIsRunning = true;
            }
            notifications.processJobs();
            synchronized (LOCK) {
                processingIsRunning = false;
            }
        }
    }

    public void setTimerService(final TimerService timerService) {
        //TODO for manual setting of TimerService, most probably for testing
        this.timerService = timerService;
    }
}
