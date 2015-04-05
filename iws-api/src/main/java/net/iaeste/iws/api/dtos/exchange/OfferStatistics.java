/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.exchange.OfferState;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OfferStatistics", propOrder = { "statistics", "exchangeYear" })
public final class OfferStatistics implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private HashMap<OfferState, Integer> statistics = new HashMap<>();
    @XmlElement(required = true, nillable = true) private Integer exchangeYear = null;

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
    public OfferStatistics(final HashMap<OfferState, Integer> statistics, final Integer exchangeYear) {
        this.statistics = statistics;
        this.exchangeYear = exchangeYear;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setStatistics(final HashMap<OfferState, Integer> statistics) {
        // Since the result is a pure read-only from the IWS, it is safe to
        // return it without a defensive copying first
        this.statistics = statistics;
    }

    public Map<OfferState, Integer> getStatistics() {
        // Since the result is a pure read-only from the IWS, it is safe to
        // return it without a defensive copying first
        return statistics;
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

        if ((statistics != null) ? !statistics.equals(that.statistics) : (that.statistics != null)) {
            return false;
        }

        return !((exchangeYear != null) ? !exchangeYear.equals(that.exchangeYear) : (that.exchangeYear != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((statistics != null) ? statistics.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((exchangeYear != null) ? exchangeYear.hashCode() : 0);

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
