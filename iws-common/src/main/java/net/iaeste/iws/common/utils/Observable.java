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

/**
 * Although there already exists a Java.util implementation of the Observer
 * design pattern, we're adding our own to provide logging/debugging mechanisms.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Observable {

    /**
     * Adds the Observer object to the list of observers to be notified by the
     * subject.
     *
     * @param observer Observer object to be notified by the subject
     */
    void addObserver(Observer observer);

    /**
     * Deletes an Observer object from the list of observers. This means that
     * the observer object will no longer receive notifications by the subject.
     *
     * @param observer Observer object to be removed
     */
    void deleteObserver(Observer observer);

    /**
     * Iterates over the list of observers, and invokes their update methods,
     * thus notifying them of the update.
     */
    void notifyObservers();
}
