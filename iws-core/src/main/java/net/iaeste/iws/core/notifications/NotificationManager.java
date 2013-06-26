/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.notifications.NotificationManager
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.notifications;

import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.NotificationMessageStatus;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.NotificationMessageEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;
import net.iaeste.iws.persistence.notification.Notifiable;
import net.iaeste.iws.persistence.notification.NotificationType;
import net.iaeste.iws.persistence.notification.Notifications;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Notes; It is good to see the notification system evolving. There is just a
 * couple of things that has to be clarified. One is that the actual handling
 * of sending, should be done in different ways. If users request that
 * information is send at specific intervals, daily, weekly, etc. then the
 * handling should be done via a "cron" job. That is a Timer job in EJB, or
 * perhaps via an external queuing system like Quartz.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass, ChainOfInstanceofChecks, ObjectAllocationInLoop
 */
public final class NotificationManager implements Notifications {

    private final NotificationDao dao;
    private final NotificationDirectEmailSender immedeateEmailSender;
    private final NotificationMessageGenerator messageGenerator;
    private final List<Observer> observers = new ArrayList<>(10);

    public NotificationManager(final NotificationDao dao, final NotificationDirectEmailSender immedeateEmailSender, final NotificationMessageGenerator messageGenerator) {
        this.dao = dao;
        this.immedeateEmailSender = immedeateEmailSender;
        this.messageGenerator = messageGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final Authentication authentication, final Notifiable obj, final NotificationType type, final boolean delayed) {
        // Save the general information about the Object to be notified.
        final List<UserEntity> users = obj.getRecipients();

        for (final UserEntity user : users) {
            //get user settings
            //TODO user should have permanent entry in the notification setting to receive messages about NotificationSubject.USER immediately
            final UserNotificationEntity userNotification = dao.findUserNotificationSetting(user, obj.getNotificationSubject());

            if (userNotification != null) {
                final NotificationMessageEntity message = new NotificationMessageEntity();
                message.setStatus(NotificationMessageStatus.NEW);
                message.setProcessAfter(getNotificationTime(userNotification.getFrequency()));

                final Map<String, String> messageTexts = messageGenerator.generateFromTemplate(obj, type);
                message.setMessage(messageTexts.get("body"));
                message.setMessageTitle(messageTexts.get("title"));

                if (delayed) {
                    dao.persist(message);
                }
                else {
                    immedeateEmailSender.send(message);
                }
            }
        }

        notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final UserEntity user) {

    }

    private static java.util.Date getNotificationTime(final NotificationFrequency frequency) {
        Date result;

        switch (frequency) {
            case DAILY:
                result = new Date();
                result = result.plusDays(1);
                break;
            case WEEKLY:
                DateMidnight dateMidnight = new DateMidnight();
                if (dateMidnight.getDayOfWeek() < DateTimeConstants.FRIDAY) {
                    dateMidnight = dateMidnight.withDayOfWeek(DateTimeConstants.FRIDAY);
                } else {
                    dateMidnight = dateMidnight.plusWeeks(1).withDayOfWeek(DateTimeConstants.FRIDAY);
                }
                result = new Date(dateMidnight);
                break;
            case IMMEDIATELY:
            default:
                result = new Date();
        }

        return result.toDate();
    }

    @Override
    public void addObserver(final Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(final Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (final Observer observer : observers) {
            observer.update(this);
        }
    }
}
