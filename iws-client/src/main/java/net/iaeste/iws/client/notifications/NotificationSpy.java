/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.notifications.NotificationSpy
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.notifications;

import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.notification.Notifiable;
import net.iaeste.iws.persistence.notification.NotificationMessageType;
import net.iaeste.iws.persistence.notification.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * For the tests, it is nice to have a way to read the notifications that is
 * being send. This Spy will help with this. It is a Singleton, that stores all
 * Notifications being send, and they can then be processed one-by-one.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection StaticNonFinalField
 */
public final class NotificationSpy implements Notifications {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationSpy.class);
    private static NotificationSpy instance = null;
    private static final Object LOCK = new Object();
    private final List<Observer> observers = new ArrayList<>(10);
    private final List<NotificationMessage> notifiables = new ArrayList<>(10);

    private NotificationSpy() {
    }

    public static NotificationSpy getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new NotificationSpy();
            }

            return instance;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final Authentication authentication, final Notifiable obj, final NotificationMessageType type) {
        LOG.info(authentication.getUser() + " has altered Object " + obj + " belonging to " + authentication.getGroup());
        final NotificationMessage message = new NotificationMessage(obj, type);
        notifiables.add(message);
        notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final Observer observer) {
        observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteObserver(final Observer observer) {
        observers.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers() {
        for (final Observer observer : observers) {
            observer.update(this);
        }
    }

    /**
     * Remove all messages from the Notification Queue.
     */
    public void clear() {
        notifiables.clear();
    }

    /**
     * Returns the number of Notiications currently being held in this Spy.
     *
     * @return Number of Notifications in the Spy
     */
    public Integer size() {
        return notifiables.size();
    }

    /**
     * Reads the first Notification from the Notification Stack, and pops it
     * from the stack. As long as a non-null value is returned, the Stack is not
     * empty.
     *
     * @return First Notitication from the Stack
     */
    public NotificationMessage getNext() {
        final NotificationMessage message;

        if (!notifiables.isEmpty()) {
            message = notifiables.remove(0);
        } else {
            message = null;
        }

        return message;
    }
}
