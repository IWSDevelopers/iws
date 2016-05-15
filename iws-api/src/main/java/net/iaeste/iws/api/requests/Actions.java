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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.util.Verifications;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actions", propOrder = "action")
public abstract class Actions extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    protected static final String FIELD_ACTION = "action";

    /** Default allowed Actions for the Folder Request. */
    private static final Set<Action> ALLOWED = EnumSet.noneOf(Action.class);

    @XmlElement(required = true)  protected Action action = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Actions() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor.
     *
     * @param allowed      Set of allowed Actions
     * @param defaultAction Default Action
     */
    protected Actions(final Set<Action> allowed, final Action defaultAction) {
        ALLOWED.addAll(allowed);
        this.action = defaultAction;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

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
    public final void setAction(final Action action) {
        ensureNotNullAndContains("action", action, ALLOWED);
        this.action = action;
    }

    /**
     * <p>Retrieves the current Action, by default it is always set to
     * Process.</p>
     *
     * @return Current Action
     */
    public final Action getAction() {
        return action;
    }

    /**
     * <p>Retrieves a set of allowed Actions for a given Request.</p>
     *
     * @return Set of allowed Actions for a specific Request.
     */
    public final Set<Action> allowedActions() {
        return immutableSet(ALLOWED);
    }
}
