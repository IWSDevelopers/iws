/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.NotificationJpaDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.persistence.jpa;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.notifications.NotificationConsumerEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationJobEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;
import net.iaeste.iws.persistence.views.NotificationJobTasksView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class NotificationJpaDao extends BasicJpaDao implements NotificationDao {

    /**
     * Default Constructor.
     *
     * @param entityManager  Entity Manager instance to use
     */
    public NotificationJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserNotificationEntity findUserNotificationSetting(final UserEntity user, final NotificationType type) {
        final Query query = entityManager.createNamedQuery("notifications.findSettingByUserAndType");
        query.setParameter("id", user.getId());
        query.setParameter("type", type);

        final List<UserNotificationEntity> result = query.getResultList();
        UserNotificationEntity entity = null;

        if (result.size() == 1) {
            entity = result.get(0);
        } else if (result.size() > 1) {
            throw new IWSException(IWSErrors.AUTHORIZATION_ERROR, "No user notification (" + type + ") for the user with id: '" + user.getId() + "' was found.");
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NotificationConsumerEntity> findActiveNotificationConsumers() {
        final Query query = entityManager.createNamedQuery("notifications.findConsumersByActive");
        query.setParameter("active", true);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NotificationJobEntity> findUnprocessedNotificationJobs(final Date date) {
        final Query query = entityManager.createNamedQuery("notifications.findJobsByNotifiedAndDate");
        query.setParameter("notified", false);
        query.setParameter("date", date);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationConsumerEntity findNotificationConsumerById(final Long id) {
        final Query query = entityManager.createNamedQuery("notifications.findConsumersById");
        query.setParameter("id", id);

        return (NotificationConsumerEntity) query.getSingleResult();
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public List<NotificationJobTasksView> findUnprocessedNotificationJobTaskByConsumerId(final Long consumerId, final Integer attemptsLimit) {
        final Query query = entityManager.createNamedQuery("view.NotificationJobTasksByConsumerId");
        query.setParameter("consumerId", consumerId);
        query.setParameter("attempts", attemptsLimit);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateNotificationJobTask(final Long id, final boolean processed, final Integer attempts) {
        final Query query = entityManager.createNamedQuery("notifications.updateJobTaskProcessedAndAttempts");
        query.setParameter("processed", processed);
        query.setParameter("attempts", attempts);
        query.setParameter("id", id);

        query.executeUpdate();
    }
}
