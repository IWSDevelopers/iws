/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.exchange.OfferStatistics;
import net.iaeste.iws.api.util.AbstractFallible;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class OfferStatisticsResponse extends AbstractFallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private OfferStatistics dommesticStatistics = null;
    private OfferStatistics foreignStatistics = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     * Constructor is used in {@code OfferResponse} when deleteing an offer.
     */
    public OfferStatisticsResponse() {
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public OfferStatisticsResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setDommesticStatistics(final OfferStatistics dommesticStatistics) {
        this.dommesticStatistics = dommesticStatistics;
    }

    public OfferStatistics getDommesticStatistics() {
        return dommesticStatistics;
    }

    public void setForeignStatistics(final OfferStatistics foreignStatistics) {
        this.foreignStatistics = foreignStatistics;
    }

    public OfferStatistics getForeignStatistics() {
        return foreignStatistics;
    }

    // =========================================================================
    // Standard Response Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OfferStatisticsResponse)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        final OfferStatisticsResponse that = (OfferStatisticsResponse) obj;

        if ((dommesticStatistics != null) ? !dommesticStatistics.equals(that.dommesticStatistics) : (that.dommesticStatistics != null)) {
            return false;
        }

        return !((foreignStatistics != null) ? !foreignStatistics.equals(that.foreignStatistics) : (that.foreignStatistics != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((dommesticStatistics != null) ? dommesticStatistics.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((foreignStatistics != null) ? foreignStatistics.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "OfferStatisticsResponse{" +
                "dommesticStatistics=" + dommesticStatistics +
                ", foreignStatistics=" + foreignStatistics +
                '}';
    }
}
