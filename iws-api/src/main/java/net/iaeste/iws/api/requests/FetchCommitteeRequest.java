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
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.util.AbstractPaginatable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchCommitteeRequest", propOrder = { "countryIds", "membership", "statuses" })
public final class FetchCommitteeRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private static final Set<GroupStatus> ALLOWED = EnumSet.of(GroupStatus.ACTIVE, GroupStatus.SUSPENDED);

    @XmlElement(required = true, nillable = true) private List<String> countryIds;
    @XmlElement(required = true, nillable = true) private Membership membership;
    @XmlElement(required = true, nillable = true) private Set<GroupStatus> statuses = EnumSet.copyOf(ALLOWED);

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchCommitteeRequest() {
        countryIds = null;
        membership = null;
    }

    /**
     * Default Constructor, for the case where a list of committees, for the
     * given list of CountryId's.
     *
     * @param countryIds List of Countries to fetch
     */
    public FetchCommitteeRequest(final List<String> countryIds) {
        ensureNotNullOrEmpty("countryIds", countryIds);

        this.countryIds = countryIds;
        this.membership = null;
    }

    /**
     * Default Constructor, for the case where a list of countries, with a
     * specific membership type, should be fetched.
     *
     * @param membership Membership Type
     */
    public FetchCommitteeRequest(final Membership membership) {
        ensureNotNull("membership", membership);

        this.membership = membership;
        this.countryIds = null;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the list of CountryIds to fetch, it will also erase the Membership
     * type, if it is defined. The setter will throw an IllegalArgument
     * Exception, if the given list of CountryIds is null or empty.
     *
     * @param countryIds List of Countries to fetch
     * @throws IllegalArgumentException if the CountryIds is null or empty
     */
    public void setCountryIds(final List<String> countryIds) throws IllegalArgumentException {
        ensureNotNullOrEmpty("countryIds", countryIds);

        this.countryIds = countryIds;
        membership = null;
    }

    /**
     * Retrieves the list of CountryIds or null.
     *
     * @return List of CountryIds to fetch
     */
    public List<String> getCountryIds() {
        return countryIds;
    }

    /**
     * Sets the Membership type to fetch Countries for, it will also erase the
     * CountryIds, if they are defined. The setter will throw an IllegalArgument
     * Exception, if the given membership value is null.
     *
     * @param membership Membership Type
     * @throws IllegalArgumentException if the membership value is null
     */
    public void setMembership(final Membership membership) throws IllegalArgumentException {
        ensureNotNull("membership", membership);

        this.membership = membership;
        countryIds = null;
    }

    /**
     * Retrieves the Membership type or null.
     *
     * @return Membership type or null
     */
    public Membership getMembership() {
        return membership;
    }

    /**
     * Sets the Statuses to be used in the Committee lookup. If the value is
     * null or empty, then the setter will throw an IllegalArgument Exception.
     *
     * @param statuses Set of Status values to include in the lookup
     * @throws IllegalArgumentException if the statuses is null
     */
    public void setStatuses(final Set<GroupStatus> statuses) throws IllegalArgumentException {
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
        final Map<String, String> validation = new HashMap<>();

        if ((countryIds != null) && (membership != null)) {
            validation.put("request", "The request Object contain both CountryIds & Membership, only one can be used.");
        }
        isNotNullAndContains(validation, "statuses", statuses, ALLOWED);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSortBy(final SortingField sortBy) {
        ensureNotNull("sortBy", sortBy);

        switch (sortBy) {
            //case CREATED:
            case NAME:
                page.setSortBy(sortBy);
                break;
            default:
                // If unsupported, we're going to revert to the default
                page.setSortBy(SortingField.CREATED);
        }
    }
}
