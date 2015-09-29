/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.exchange.FetchOffersRequest
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
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.util.AbstractPaginatable;

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
@XmlType(name = "FetchOffersRequest", propOrder = { "fetchType", "exchangeYear", "states", "retrieveCurrentAndNextExchangeYear" })
public final class FetchOffersRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** Allowed States for an Offer to be retrieved. */
    private static final Set<OfferState> ALLOWED = EnumSet.of(
            OfferState.NEW,          OfferState.OPEN,        OfferState.SHARED,
            OfferState.APPLICATIONS, OfferState.NOMINATIONS, OfferState.CLOSED,
            OfferState.COMPLETED,    OfferState.AT_EMPLOYER, OfferState.ACCEPTED,
            OfferState.EXPIRED,      OfferState.REJECTED);

    @XmlElement(required = true, nillable = false) private FetchType fetchType;
    @XmlElement(required = true, nillable = false) private Integer exchangeYear;
    @XmlElement(required = true, nillable = false) private Set<OfferState> states = ALLOWED;
    @XmlElement(required = true, nillable = false) private boolean retrieveCurrentAndNextExchangeYear = false;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchOffersRequest() {
        this.fetchType = null;
        this.exchangeYear = calculateExchangeYear();
    }

    /**
     * Default Constructor.
     */
    public FetchOffersRequest(final FetchType fetchType) {
        this.fetchType = fetchType;
        this.exchangeYear = calculateExchangeYear();
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setFetchType(final FetchType fetchType) {
        ensureNotNull("fetchType", fetchType);
        this.fetchType = fetchType;
    }

    public FetchType getFetchType() {
        return fetchType;
    }

    public void setExchangeYear(final Integer exchangeYear) {
        ensureNotNullAndWithinLimits("exchangeYear", exchangeYear, IWSConstants.FOUNDING_YEAR, calculateExchangeYear());
        this.exchangeYear = exchangeYear;
    }

    public Integer getExchangeYear() {
        return exchangeYear;
    }

    /**
     * Sets the different States, that an Offer can have. By default all States
     * are allowed.
     *
     * @param states The States to use in the filter
     */
    public void setStates(Set<OfferState> states) {
        ensureNotNullAndContains("states", states, ALLOWED);
        this.states = states;
    }

    public Set<OfferState> getStates() {
        return states;
    }

    /**
     * Sets the flag to determine if Offers from both the current Exchange Year
     * and the new Exchange Year shall be fetched. This option will allow Users
     * to see Offers from both years following September 1st, when the new
     * Exchange Year starts. By default, this is set to false, so only the
     * Exchange year counts.<br />
     *   This value is mandatory. If set to null, then a
     * {$code VerificationException} is thrown.
     *
     * @param retrieveCurrentAndNextExchangeYear True if both shall be retrieved
     */
    public void setRetrieveCurrentAndNextExchangeYear(final boolean retrieveCurrentAndNextExchangeYear) throws VerificationException {
        ensureNotNull("retrieveCurrentAndNextExchangeYear", retrieveCurrentAndNextExchangeYear);
        this.retrieveCurrentAndNextExchangeYear = retrieveCurrentAndNextExchangeYear;
    }

    public boolean getRetrieveCurrentAndNextExchangeYear() {
        return retrieveCurrentAndNextExchangeYear;
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
        isNotNull(validation, "exchangeYear", exchangeYear);
        isNotNull(validation, "states", states);
        isNotNull(validation, "retrieveCurrentAndNextExchangeYear", retrieveCurrentAndNextExchangeYear);

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
