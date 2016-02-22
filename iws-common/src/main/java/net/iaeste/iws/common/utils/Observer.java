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
package net.iaeste.iws.common.utils;

import net.iaeste.iws.common.configuration.Settings;

import javax.persistence.EntityManager;

/**
 * The Observer interface is used by the "observers" in the
 * <a href="http://en.wikipedia.org/wiki/Observer_pattern">Observer Design
 * Pattern</a>. The Observable interface is used by those classes, who wishes
 * to become observable or "subjects".
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 * @see <a href="http://en.wikipedia.org/wiki/Observer_pattern">Observer Design Pattern</a>
 */
public interface Observer {

    void setId(Long id);

    Long getId();

    /**
     * Method to initialize Observer
     *
     * @param entityManager IWS Entity Manager
     * @param settings      IWS Settings
     */
    void init(EntityManager entityManager, Settings settings);

    /**
     * Whenever an observer needs to be notified of a change, this method is
     * invoked with the subject as parameter. The method is invoked by the
     * Observable notifyObservers method.
     *
     * @param  subject  the observable object
     */
    void update(Observable subject);
}
