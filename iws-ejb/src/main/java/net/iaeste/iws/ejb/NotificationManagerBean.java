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
import net.iaeste.iws.api.util.Date;
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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotificationManagerBean implements NotificationManagerLocal {
    private static final Logger LOG = Logger.getLogger(NotificationManagerBean.class);
    private EntityManager entityManager = null;
    private NotificationDao dao = null;
    private AccessDao accessDao = null;
    private Notifications notifications = null;

    @Resource
    private TimerService timerService;

    /**
     * Setter for the JNDI injected persistence context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param entityManager Transactional Entity Manager instance
     */
    @PersistenceContext(unitName = "iwsDatabase")
    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @PostConstruct
    public void postConstruct() {
        dao = new NotificationJpaDao(entityManager);
        accessDao = new AccessJpaDao(entityManager);

        final NotificationManager notificationManager = new NotificationManager(dao, accessDao, new NotificationMessageGeneratorFreemarker(), true);
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
        try {
            notifications.notify(authentication, obj, type);
        } catch (IWSException e) {
            LOG.error("Preparing notification failed", e);
        }

        //TODO if to avoid problems during testing, possible fix by providing mocked TimerService
        if (timerService != null) {
            final String clazz = NotificationManagerBean.class.getSimpleName();
            final Date now = new Date();
            timerService.createTimer(now.toDate(), clazz);
        } else {
            LOG.warn("There is no TimerService, probably running outside app server");
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
            final String clazz = NotificationManagerBean.class.getSimpleName();
            final Date now = new Date();
            timerService.createTimer(now.toDate(), clazz);
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
        notifications.processJobs();
    }

    public void setTimerService(final TimerService timerService) {
        //TODO for manual setting of TimerService, most probably for testing
        this.timerService = timerService;
    }
}
