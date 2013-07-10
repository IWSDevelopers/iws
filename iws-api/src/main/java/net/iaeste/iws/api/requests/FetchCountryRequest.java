/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchCountryRequest
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
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.util.AbstractPaginatable;
import net.iaeste.iws.api.util.Copier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * For listing of countries, this request Object is used. It can be generated
 * with a number of variants. If the specific countryId is known, it can be
 * given, otherwise a list of CountryIds can be given or a Membership type. It
 * is not possible to mix the list of CountryIds with Membership type, since
 * this allows for mutually exclusive combinations, i.e. a combination of a
 * CountryId where the country in question has one type of Membership, and the
 * given Membership is of a different type, meaning that the result would be
 * rather strange in nature, since it would be "yes, we know the country, but
 * you asked for it in a different context".<br />
 *   The provided constructors and setters, is written, so it is not possible
 * to set both values.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchCountryRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private List<String> countryIds;
    private Membership membership;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchCountryRequest() {
        countryIds = null;
        membership = null;
    }

    /**
     * Default Constructor, for the case where a list of countries, with a
     * specific membership type, should be fetched.
     *
     * @param membership Membership Type
     */
    public FetchCountryRequest(final Membership membership) {
        if (membership == null) {
            throw new IllegalArgumentException("Null value for Membership is not allowed.");
        }

        this.membership = membership;
        countryIds = null;
    }

    /**
     * Default Constructor, for the case where a list of countries, matching the
     * given Ids, should be fetched.
     *
     * @param countryIds List of Countries to fetch
     */
    public FetchCountryRequest(final List<String> countryIds) {
        if (countryIds == null || countryIds.isEmpty()) {
            throw new IllegalArgumentException("Null value or empty list of CountryIds is not allowed.");
        }

        this.countryIds = Copier.copy(countryIds);
        membership = null;
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
        if (countryIds == null || countryIds.isEmpty()) {
            throw new IllegalArgumentException("Null value or empty list of CountryIds is not allowed.");
        }

        this.countryIds = Copier.copy(countryIds);
        membership = null;
    }

    /**
     * Retrieves the list of CountryIds or null.
     *
     * @return List of CountryIds to fetch
     */
    public List<String> getCountryIds() {
        return Copier.copy(countryIds);
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
        if (membership == null) {
            throw new IllegalArgumentException("Null value for Membership is not allowed.");
        }

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

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        if (countryIds == null && membership == null) {
            validation.put("countryIds", "Either the CountryIds or the Membership must be defined.");
            validation.put("membership", "Either the CountryIds or the Membership must be defined.");
        }

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSortBy(final SortingField sortBy) {
        if (sortBy == null) {
            throw new IllegalArgumentException("The SortingField cannot be null.");
        }

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
