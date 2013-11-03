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

import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.UserEntity;
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
 * @noinspection StaticNonFinalField, SynchronizationOnStaticField
 */
public final class NotificationSpy implements Notifications {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationSpy.class);
    private static NotificationSpy instance = null;
    private static final Object LOCK = new Object();
    private final List<Observer> observers = new ArrayList<>(10);
    private final List<NotificationMessage> notifiables = new ArrayList<>(10);

    /**
     * This Spy is implemented as a Singleton. Hence, there is only this private
     * constructor, that is invoked via the {@link #getInstance()} method.
     */
    private NotificationSpy() {
    }

    /**
     * Singleton instantiator. Returns the internally stored active instance for
     * this class. If no such instance exists, then a new instance is created
     * and returned.
     *
     * @return Singleton instance for this class
     */
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
    public void notify(final Authentication authentication, final Notifiable obj, final NotificationType type) {
        LOG.info(authentication.getUser() + " has altered Object " + obj + " belonging to " + authentication.getGroup());
        final NotificationMessage message = new NotificationMessage(obj, type);
        notifiables.add(message);
        notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final UserEntity user) {
        LOG.info(user + " has forgotten the password.");
        final NotificationMessage message = new NotificationMessage(user, NotificationType.RESET_PASSWORD);
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
     * {@inheritDoc}
     */
    @Override
    public void processJobs() {
        //this method is not important for NotificationSpy
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
     * @param types If defined, then it'll return number of matching messages
     * @return Number of Notifications in the Spy
     */
    public Integer size(final NotificationType... types) {
        Integer size = 0;

        if ((types != null) && (types.length == 1)) {
            final NotificationType type = types[0];
            for (final NotificationMessage message : notifiables) {
                if (type == message.getType()) {
                    size++;
                }
            }
        } else {
            size = notifiables.size();
        }

        return size;
    }

    /**
     * Reads the first Notification from the Notification Stack, and pops it
     * from the stack. As long as a non-null value is returned, the Stack is not
     * empty.
     *
     * @param types If defined, then it'll fetch the first matching type
     * @return First Notitication from the Stack or null if stack is empty
     */
    public NotificationMessage getNext(final NotificationType... types) {
        NotificationMessage message = null;

        if (!notifiables.isEmpty()) {
            int index = -1;
            if ((types != null) && (types.length == 1)) {
                final NotificationType type = types[0];
                for (int i = 0; i < notifiables.size(); i++) {
                    if ((index == -1) && (type == notifiables.get(i).getType())) {
                        index = i;
                    }
                }
            } else {
                index = 0;
            }

            message = notifiables.get(index);
            notifiables.remove(index);
        }

        return message;
    }
}
