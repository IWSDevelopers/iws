/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.NotificationConsumerClassLoader
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.notifications;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.NotificationDao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CustomClassloader
 */
public final class NotificationConsumerClassLoader {

    public Observer findConsumerClass(final String name, final NotificationDao dao, final AccessDao accessDao) {
        try {
//            this doesn't work in glassfish
//            final Class<?> consumerClass = loadClass(name);
            //TODO all consumers have to have same constructor parameters -> any idea how to make it dynamic?
            final Class<?> consumerClass = Class.forName(name);
            final Constructor<?> constructor = consumerClass.getDeclaredConstructor(NotificationDao.class, AccessDao.class);
            final Object consumer = constructor.newInstance(dao, accessDao);
            if (consumer instanceof Observer) {
                return (Observer) consumer;
            }

            throw new IWSException(IWSErrors.ERROR, "Class " + name + " is not valid notification consumer");
        } catch (ClassNotFoundException ignored) {
            throw new IWSException(IWSErrors.ERROR, "Consumer " + name + " cannot be loaded");
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new IWSException(IWSErrors.ERROR, "Error during loading " + name + " consumer", e);
        }
    }
}