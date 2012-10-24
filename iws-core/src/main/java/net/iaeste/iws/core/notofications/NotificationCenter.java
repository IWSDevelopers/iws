/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.notifications.NotificationsCenter
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.core.notofications;

import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.NotificationMessageStatus;
import net.iaeste.iws.api.enums.NotificationSubject;
import net.iaeste.iws.api.enums.NotificationType;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.core.transformers.UserTransformer;
import net.iaeste.iws.persistence.UserNotificationDao;
import net.iaeste.iws.persistence.entities.NotificationMessageEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;

import java.util.*;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class NotificationCenter implements Observable {
    private final UserNotificationDao dao;
    List<Observer> observers = new ArrayList<>();

    public NotificationCenter(final UserNotificationDao dao) {
        this.dao = dao;
    }

    public void processUpdatedOffer(Offer offer) {
        //read from database who wants to be notified for the offer
        //TODO: waiting for #97
        List<User> users = new ArrayList<>();

        for(User u : users) {
            //get user settings
            UserNotificationEntity userNotification = dao.findUserNotificationSetting(u, NotificationSubject.OFFER);
            if(userNotification == null) {
                //user hasn't set any notification
                continue;
            }

            NotificationMessageEntity message = new NotificationMessageEntity();
            message.setUser(UserTransformer.transform(u));
            message.setStatus(NotificationMessageStatus.NEW);
            message.setProcessAfter(getNotificationTime(userNotification.getFrequency()));
            //create message
            String messageText = offer.generateMessage(NotificationType.EMAIL, "updated");
            message.setMessage(messageText);

            //save messages into DB
            dao.persist(message);
        }

        notifyObservers();
    }

    public void processNewOffer(Offer offer) {
        //read from database who wants to be notified for the offer
        //TODO: waiting for #97
        List<User> users = new ArrayList<>();

        for(User u : users) {
            //get user settings
            UserNotificationEntity userNotification = dao.findUserNotificationSetting(u, NotificationSubject.OFFER);
            if(userNotification == null) {
                //user hasn't set any notification
                continue;
            }

            NotificationMessageEntity message = new NotificationMessageEntity();
            message.setUser(UserTransformer.transform(u));
            message.setStatus(NotificationMessageStatus.NEW);
            message.setProcessAfter(getNotificationTime(userNotification.getFrequency()));
            //create message
            String messageText = offer.generateMessage(NotificationType.EMAIL, "new");
            message.setMessage(messageText);

            //save messages into DB
            dao.persist(message);
        }

        notifyObservers();
    }

    private Date getNotificationTime(NotificationFrequency frequency) {
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
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update(this);
        }
    }
}
