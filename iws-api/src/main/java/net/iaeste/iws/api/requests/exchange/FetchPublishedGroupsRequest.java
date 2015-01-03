/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.util.AbstractPaginatable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request Object for fetching sharing info for posted list of offer Id's.
 * The offers have to be owned by the group to which the user is
 * currently logged in otherwise the exception is thrown.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class FetchPublishedGroupsRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private List<String> offerIds = null;
    private Integer exchangeYear;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchPublishedGroupsRequest() {
        this.exchangeYear = calculateExchangeYear();
    }

    /**
     * Default Constructor.
     *
     * @param offerIds offerIds of the offer for which the sharing info is to be fetched
     */
    public FetchPublishedGroupsRequest(final List<String> offerIds) {
        setOfferIds(offerIds);
        this.exchangeYear = calculateExchangeYear();
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the list of OfferIds to read the sharing information for. The list
     * must be valid, i.e. not null or empty and the values must all be valid
     * OfferIds.<br />
     *   If attempted to set invalid ids, then the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param offerIds List of Offer Ids
     * @throws IllegalArgumentException if the given argument is invalid
     */
    public void setOfferIds(final List<String> offerIds) throws IllegalArgumentException {
        ensureNotNullOrEmptyAndValidIds("offerIds", offerIds);
        this.offerIds = offerIds;
    }

    public List<String> getOfferIds() {
        return offerIds;
    }

    /**
     * Sets the ExchangeYear to read which Groups the provided Offers were
     * shared with, by default, it is the current Exchange Year. The value must
     * be defined, i.e. no null and also within the IAESTE Years, i.e. founding
     * year to the current Exchange Year.<br />
     *   If attempted to set to an invalid year, then the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param exchangeYear Exchange YeAr
     * @throws IllegalArgumentException if the given argument is invalid
     */
    public void setExchangeYear(final Integer exchangeYear) throws IllegalArgumentException {
        ensureNotNullAndWithinLimits("exchangeYear", exchangeYear, IWSConstants.FOUNDING_YEAR, calculateExchangeYear());
        this.exchangeYear = exchangeYear;
    }

    public Integer getExchangeYear() {
        return exchangeYear;
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

        isNotNull(validation, "OfferIds", offerIds);
        isNotNull(validation, "exchangeYear", exchangeYear);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSortBy(final SortingField sortBy) {
        ensureNotNull("sortBy", sortBy);

        switch (sortBy) {
            case NAME:
                page.setSortBy(sortBy);
                break;
            default:
                // If unsupported, we're going to revert to the default
                page.setSortBy(SortingField.CREATED);
        }
    }
}
