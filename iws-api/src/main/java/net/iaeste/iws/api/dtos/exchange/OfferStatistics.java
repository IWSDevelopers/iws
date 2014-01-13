/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.OfferStatistics
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos.exchange;

import static net.iaeste.iws.api.util.Copier.copy;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.exchange.OfferState;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class OfferStatistics implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Map<OfferState, Integer> statistics = new EnumMap<>(OfferState.class);
    private Integer exchangeYear;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
    * Empty Constructor, to use if the setters are invoked. This is required
    * for WebServices to work properly.
    */
    public OfferStatistics() {
    }

    /**
     * Default Constructor.
     *
     * @param statistics   Offer Statistics
     * @param exchangeYear Exchange Year
     */
    public OfferStatistics(final Map<OfferState, Integer> statistics, final Integer exchangeYear) {
        setStatistics(statistics);
        setExchangeYear(exchangeYear);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setStatistics(final Map<OfferState, Integer> statistics) {
        this.statistics = copy(statistics);
    }

    public Map<OfferState, Integer> getStatistics() {
        return copy(statistics);
    }

    public void setExchangeYear(final Integer exchangeYear) {
        this.exchangeYear = exchangeYear;
    }

    public Integer getExchangeYear() {
        return exchangeYear;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OfferStatistics)) {
            return false;
        }

        final OfferStatistics that = (OfferStatistics) obj;

        if (statistics != null ? !statistics.equals(that.statistics) : that.statistics != null) {
            return false;
        }

        return !(exchangeYear != null ? !exchangeYear.equals(that.exchangeYear) : that.exchangeYear != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (statistics != null ? statistics.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (exchangeYear != null ? exchangeYear.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "OfferStatistics{" +
                "statistics=" + statistics +
                ", exchangeYear=" + exchangeYear +
                '}';
    }
}
