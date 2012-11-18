/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.UserNotificationJpaDao
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
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.NotificationMessageStatus;
import net.iaeste.iws.api.enums.NotificationSubject;
import net.iaeste.iws.api.enums.NotificationType;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.NotificationMessageEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class NotificationJpaDao extends BasicJpaDao implements NotificationDao {

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
    public UserNotificationEntity findUserNotificationSetting(final User user, final NotificationSubject subject) {
        final Query query = entityManager.createNamedQuery("notifications.findSettingByUserAndSubject");
        query.setParameter("id", user.getUserId());
        query.setParameter("subject", subject);

        final List<UserNotificationEntity> result = query.getResultList();

        if (result.size() != 1) {
            throw new IWSException(IWSErrors.AUTHORIZATION_ERROR, "No user notification (" + subject + ") for the user with id: '" + user.getUserId() + "' was found.");
        }

        return result.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NotificationMessageEntity> findNotificationMessages(final NotificationType type, final NotificationMessageStatus status, final Date date) {
        final Query query = entityManager.createNamedQuery("notifications.findMessagesByStatus");
        query.setParameter("status", status);
        query.setParameter("date", date);
        query.setParameter("type", type);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateNotificationMessageStatus(final NotificationMessageEntity message, final NotificationMessageStatus status) {
        final Query query = entityManager.createNamedQuery("notifications.updateStatus");
        query.setParameter("status", status);
        query.setParameter("id", message.getId());
        query.executeUpdate();
    }

}