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
package net.iaeste.iws.ejb.notifications;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.utils.Observer;

import javax.persistence.EntityManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class NotificationConsumerClassLoader {

    public Observer findConsumerClass(final String name, final EntityManager iwsEntityManager, final Settings settings) {
        try {
            final Class<?> consumerClass = Class.forName(name);
            final Constructor<?> constructor = consumerClass.getDeclaredConstructor();
            final Object consumer = constructor.newInstance();
            if (consumer instanceof Observer) {
                final Observer observer = (Observer)consumer;
                observer.init(iwsEntityManager, settings);
                return observer;
            }

            throw new IWSException(IWSErrors.ERROR, "Class " + name + " is not valid notification consumer");
        } catch (ClassNotFoundException e) {
            throw new IWSException(IWSErrors.ERROR, "Consumer " + name + " cannot be loaded", e);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new IWSException(IWSErrors.ERROR, "Error during loading " + name + " consumer", e);
        }
    }
}
