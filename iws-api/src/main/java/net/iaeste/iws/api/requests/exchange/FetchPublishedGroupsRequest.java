/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchPublishedGroupsRequest", propOrder = { "identifiers", "exchangeYear" })
public final class FetchPublishedGroupsRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private List<String> identifiers = null;
    @XmlElement(required = true, nillable = false) private Integer exchangeYear;

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
     * @param identifiers identifiers of the offer for which the sharing info is to be fetched
     */
    public FetchPublishedGroupsRequest(final List<String> identifiers) {
        setIdentifiers(identifiers);
        this.exchangeYear = calculateExchangeYear();
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets a list of Identifiers, meaning either the Id of the Offers or their
     * Reference Number, which both can be used to uniquely identify an
     * Offer.<br />
     *   The method will thrown an {@code IllegalArgumentException} if the given
     * value is null.
     *
     * @param identifiers List of OfferId's or Reference Numbers to be fetched, may be empty
     * @throws IllegalArgumentException if the parameter is null
     */
    public void setIdentifiers(final List<String> identifiers) throws IllegalArgumentException {
        ensureNotNullAndValidIdentifiers("identifiers", identifiers);
        this.identifiers = identifiers;
    }

    public List<String> getIdentifiers() {
        return identifiers;
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

        isNotNull(validation, "identifiers", identifiers);
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
