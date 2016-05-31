/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * @since   IWS 1.0
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
        LOG.info("{} has altered Object {} belonging to {}", authentication.getUser(), obj, authentication.getGroup());
        final NotificationMessage message = new NotificationMessage(obj, type);
        notifiables.add(message);
        notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final UserEntity user) {
        LOG.info("{} has forgotten the password.", user);
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
        // This method is not important for the NotificationSpy
    }

    /**
     * Remove all messages from the Notification Queue.
     */
    public void clear() {
        notifiables.clear();
    }

    /**
     * Returns the number of Notifications currently being held in this Spy.
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
     * @return First Notification from the Stack or null if stack is empty
     */
    public NotificationMessage getNext(final NotificationType... types) {
        NotificationMessage message = null;

        if (!notifiables.isEmpty()) {
            final int index;

            if ((types != null) && (types.length == 1)) {
                index = findNextIndex(types[0]);
            } else {
                index = 0;
            }

            message = notifiables.get(index);
            notifiables.remove(index);
        }

        return message;
    }

    private int findNextIndex(final NotificationType type) {
        int index = -1;

        for (int i = 0; i < notifiables.size(); i++) {
            if ((index == -1) && (type == notifiables.get(i).getType())) {
                index = i;
            }
        }

        return index;
    }
}
