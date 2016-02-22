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
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.util.Verifiable;

import java.util.Set;

/**
 * <p>To ensure that the Processing methods all share a common ground, they must
 * implement this interface, which will control the Actions that is allowed.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public interface Actionable extends Verifiable {

    /**
     * <p>Retrieves a set of allowed Actions for a given Request.</p>
     *
     * @return Set of allowed Actions for a specific Request.
     */
    Set<Action> allowedActions();

    /**
     * <p>Sets the Action for the Processing Request. By default, it is set to
     * Process, meaning that the IWS will attempt to either create or update
     * the Object in question. However, more options exists, based on the actual
     * context.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the value
     * is set to null, or a non allows value.</p>
     *
     * @param action Current Action
     * @throws IllegalArgumentException if the value is null or not allowed
     */
    void setAction(Action action);

    /**
     * <p>Retrieves the current Action, by default it is always set to
     * Process.</p>
     *
     * @return Current Action
     */
    Action getAction();
}
