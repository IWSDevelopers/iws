/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.SimpleNotifications
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.spring;

import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.notification.Notifiable;
import net.iaeste.iws.persistence.notification.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Very simple Logging implementation of the Notification Server.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class NotificationLogger implements Notifications {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationLogger.class);
    private final List<Observer> observers = new ArrayList<>(10);

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final Authentication authentication, final Notifiable obj) {
        LOG.info(authentication.getUser() + " has altered Object " + obj + " belonging to " + authentication.getGroup());
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
}
