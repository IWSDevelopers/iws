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
 * @since   IWS 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchInternationalGroupRequest", propOrder = { "statuses" })
public final class FetchInternationalGroupRequest extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private static final Set<GroupStatus> ALLOWED = EnumSet.of(GroupStatus.ACTIVE, GroupStatus.SUSPENDED);

    @XmlElement(required = true, nillable = false)
    private Set<GroupStatus> statuses = EnumSet.copyOf(ALLOWED);

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchInternationalGroupRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor.
     *
     * @param statuses List of Statuses for the Groups to fetch
     */
    public FetchInternationalGroupRequest(final Set<GroupStatus> statuses) {
        setStatuses(statuses);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Statuses to be used in the International Group lookup. If the
     * value is null or empty, then the setter will throw an IllegalArgument
     * Exception.
     *
     * @param statuses Set of Status values to include in the lookup
     * @throws IllegalArgumentException if the statuses is null
     */
    public void setStatuses(final Set<GroupStatus> statuses) {
        ensureNotNullAndContains("statuses", statuses, ALLOWED);

        this.statuses = statuses;
    }

    public Set<GroupStatus> getStatuses() {
        return statuses;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(1);

        isNotNullAndContains(validation, "statuses", statuses, ALLOWED);

        return validation;
    }
}
