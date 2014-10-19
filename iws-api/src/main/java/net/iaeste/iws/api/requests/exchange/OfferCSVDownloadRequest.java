/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.exchange.OfferCSVDownloadRequest
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
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.util.AbstractPaginatable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class OfferCSVDownloadRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private FetchType fetchType;
    private List<String> offerIds;
    private Integer exchangeYear;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public OfferCSVDownloadRequest() {
        this.fetchType = null;
        this.offerIds = null;
        this.exchangeYear = calculateExchangeYear();
    }

    public OfferCSVDownloadRequest(final FetchType fetchType, final List<String> offerIds, final Integer exchangeYear) {
        this.fetchType = fetchType;
        this.offerIds = offerIds;
        this.exchangeYear = exchangeYear;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setFetchType(final FetchType fetchType) {
        this.fetchType = fetchType;
    }

    public FetchType getFetchType() {
        return fetchType;
    }

    public void setOfferIds(final List<String> offerIds) {
        this.offerIds = offerIds;
    }

    public List<String> getOfferIds() {
        return offerIds;
    }

    public void setExchangeYear(final Integer exchangeYear) {
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

        isNotNull(validation, "fetchType", fetchType);
        isNotNull(validation, "offerIds", offerIds);
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
            default:
                // If unsupported, we're going to revert to the default
                page.setSortBy(SortingField.CREATED);
        }
    }
}
