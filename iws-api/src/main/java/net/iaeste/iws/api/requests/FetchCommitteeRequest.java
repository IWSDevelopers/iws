/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchCommitteeRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.util.AbstractPaginatable;

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
public class FetchCommitteeRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private List<String> countryIds;
    private Membership membership;
    private Set<GroupStatus> statuses = EnumSet.of(GroupStatus.ACTIVE, GroupStatus.SUSPENDED);

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchCommitteeRequest() {
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
        ensureNotNullOrEmpty("statuses", statuses);

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
