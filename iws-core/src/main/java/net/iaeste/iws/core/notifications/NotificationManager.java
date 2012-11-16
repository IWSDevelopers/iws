/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.NotificationMessageStatus;
import net.iaeste.iws.api.enums.NotificationSubject;
import net.iaeste.iws.api.enums.NotificationType;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.NotificationMessageEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;
import net.iaeste.iws.persistence.notification.Notifiable;
import net.iaeste.iws.persistence.notification.Notifications;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 */
public final class NotificationManager implements Notifications {

    private final NotificationDao dao;
    private final List<Observer> observers = new ArrayList<>(10);

    public NotificationManager(final NotificationDao dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final Authentication authentication, final Notifiable obj) {
        // Save the general information about the Object to be notified.
        notifyObservers();
    }

    /**
     * The method has been deprecated, since this design requires that each and
     * every Object that we wish to send notifications about should be directly
     * implemented here. Rather, than leaving it to the Object itself.
     *
     * @param offer Offer
     * @deprecated Please see the notify method
     */
    @Deprecated
    public void processUpdatedOffer(final Offer offer) {
        //read from database who wants to be notified for the offer
        //TODO: waiting for #97
        final List<User> users = new ArrayList<>(0);

        for (final User user : users) {
            //get user settings
            final UserNotificationEntity userNotification = dao.findUserNotificationSetting(user, NotificationSubject.OFFER);
            if (userNotification == null) {
                //user hasn't set any notification
                continue;
            }

            final NotificationMessageEntity message = new NotificationMessageEntity();
            message.setStatus(NotificationMessageStatus.NEW);
            message.setProcessAfter(getNotificationTime(userNotification.getFrequency()));
            //create message
            final String messageText = offer.generateMessage(NotificationType.EMAIL, "updated");
            message.setMessage(messageText);

            //save messages into DB
            dao.persist(message);
        }

        notifyObservers();
    }

    /**
     * The method has been deprecated, since this design requires that each and
     * every Object that we wish to send notifications about should be directly
     * implemented here. Rather, than leaving it to the Object itself.
     *
     * @param offer Offer
     * @deprecated Please see the notify method
     */
    @Deprecated
    public void processNewOffer(final Offer offer) {
        //read from database who wants to be notified for the offer
        //TODO: waiting for #97
        final List<User> users = new ArrayList<>(10);

        for (final User user : users) {
            //get user settings
            final UserNotificationEntity userNotification = dao.findUserNotificationSetting(user, NotificationSubject.OFFER);
            if (userNotification == null) {
                //user hasn't set any notification
                continue;
            }

            final NotificationMessageEntity message = new NotificationMessageEntity();
            message.setStatus(NotificationMessageStatus.NEW);
            message.setProcessAfter(getNotificationTime(userNotification.getFrequency()));
            //create message
            final String messageText = offer.generateMessage(NotificationType.EMAIL, "new");
            message.setMessage(messageText);

            //save messages into DB
            dao.persist(message);
        }

        notifyObservers();
    }

    /**
     * @param frequency
     * @return
     */
    private Date getNotificationTime(final NotificationFrequency frequency) {
        Date result = null;
        switch (frequency) {
            case IMMEDIATELY:
                result = new Date();
                break;
            case DAILY:
                result = new Date();
                //TODO: get a date of the next day
                break;
            case WEEKLY:
                //TODO: get a date of the first day of next week
                result = new Date();
        }
        return result;
    }

    private List<User> getRecipients() {
        return null;
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
