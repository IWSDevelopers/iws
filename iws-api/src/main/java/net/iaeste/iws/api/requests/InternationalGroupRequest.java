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
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.util.Verifications;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "internationalGroupRequest", propOrder = { "group", "user", "status", "action" })
public final class InternationalGroupRequest extends Verifications implements Actionable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** Default allowed Actions for the Committee Request. */
    private static final Set<Action> ALLOWED = EnumSet.of(Action.PROCESS, Action.ACTIVATE, Action.SUSPEND, Action.DELETE);

    @XmlElement(required = true) private Group group = null;
    @XmlElement(required = true) private User user = null;
    @XmlElement(required = true) private GroupStatus status = GroupStatus.ACTIVE;

    /**
     * <p>Action to perform on an International Group, by default we're assuming
     * that it must be processed, i.e. either created or updated.</p>
     */
    @XmlElement(required = true) private Action action = Action.PROCESS;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public InternationalGroupRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Simple Constructor, to use when only need to change an International
     * Group. This will not change the current Owner of the Group.
     *
     * @param group Group to update
     */
    public InternationalGroupRequest(final Group group) {
        setGroup(group);
    }

    /**
     * Full Constructor, to use when creating a new International Group or when
     * updating the current Coordinator (Owner) of the International Group.
     *
     * @param group International Group to either create or update
     * @param user  User to set as Coordinator (Owner)
     */
    public InternationalGroupRequest(final Group group, final User user) {
        setGroup(group);
        setUser(user);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Group to be processed. The Group Object must be valid, otherwise
     * the method will throw an IllegalArgument Exception.
     *
     * @param group Group to process
     * @throws IllegalArgumentException if Group is null or nor verifiable
     */
    public void setGroup(final Group group) {
        ensureNotNullAndVerifiable("group", group);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    /**
     * Sets the User to be added as Coordinator (Owner) of the International
     * Group. The User must be verifiable, otherwise the method will throw an
     * IllegalArgument Exception.
     *
     * @param user User to set as Coordinator (Owner)
     * @throws IllegalArgumentException if not verifiable
     */
    public void setUser(final User user) {
        ensureVerifiable("user", user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /**
     * Sets the Status of the Group, if it should change. The Status must be one
     * of the known valid stats of a Group, which includes "Active", "Suspended"
     * or "Deleted". If "Deleted" is chosen, then the Group will be deleted from
     * the system.
     *
     * @param status New Status for the Group
     * @throws IllegalArgumentException if status is null
     */
    public void setStatus(final GroupStatus status) {
        ensureNotNull("status", status);
        this.status = status;
    }

    public GroupStatus getStatus() {
        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAction(final Action action) {
        ensureNotNullAndContains("action", action, ALLOWED);
        this.action = action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getAction() {
        return action;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Action> allowedActions() {
        return immutableSet(ALLOWED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNullAndVerifiable(validation, "group", group);
        isVerifiable(validation, "user", user);
        isNotNull(validation, "status", status);
        isNotNull(validation, "action", action);

        return validation;
    }
}
