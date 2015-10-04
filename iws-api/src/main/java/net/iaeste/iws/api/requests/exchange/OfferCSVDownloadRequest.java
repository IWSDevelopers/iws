/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "offerCSVDownloadRequest", propOrder = { "fetchType", "offerIds", "exchangeYear" })
public class OfferCSVDownloadRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private FetchType fetchType;
    @XmlElement(required = true, nillable = false) private List<String> offerIds;
    @XmlElement(required = true, nillable = false) private Integer exchangeYear;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public OfferCSVDownloadRequest() {
        this.fetchType = null;
        this.offerIds = new ArrayList<>(0);
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

    /**
     * Sets the mandatory FetchType for the CSV Downloading of Offers, the type
     * can be either Domestic (a Committee's own Offers) or Shared (Offers from
     * other Committee's). However, the value cannot be null.<br />
     *   The method will thrown an {@code IllegalArgumentException} if the given
     * value is null.
     *
     * @param fetchType Type of Offers to be fetched
     * @throws IllegalArgumentException if the parameter is null
     */
    public void setFetchType(final FetchType fetchType) throws IllegalArgumentException {
        ensureNotNull("fetchType", fetchType);
        this.fetchType = fetchType;
    }

    public FetchType getFetchType() {
        return fetchType;
    }

    /**
     * Sets a list of Offer Id's, which is suppose to be fetched. The Id's must
     * either belong to the Country (if the FetchType is domestic) or the Id's
     * must belong to Offers shared (if the FetchType is shared). If the list
     * of Id's is empty, then all Offers matching the FetchType and Exchange
     * Year will be retrieved.<br />
     *   The method will thrown an {@code IllegalArgumentException} if the given
     * value is null.
     *
     * @param offerIds List of OfferId's to be fetched, may be empty
     * @throws IllegalArgumentException if the parameter is null
     */
    public void setOfferIds(final List<String> offerIds) throws IllegalArgumentException {
        ensureNotNull("offerIds", offerIds);
        this.offerIds = offerIds;
    }

    public List<String> getOfferIds() {
        return offerIds;
    }

    /**
     * Sets the mandatory Exchange Year for the CSV Downloading of Offers. The
     * year must be within the known Exchange years for IAESTE, which
     * theoretically is from the founding year until the current. However, the
     * IWS is only having data from 2004 and onward. The latest year to read
     * from will be the current Exhange Year.<br />
     *   The method will thrown an {@code IllegalArgumentException} if the given
     * value is null.
     *
     * @param exchangeYear Exchange Year to retrieve offers from
     * @throws IllegalArgumentException if the parameter is null or not within limits
     * @see IWSConstants#FOUNDING_YEAR
     * @see #calculateExchangeYear()
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
