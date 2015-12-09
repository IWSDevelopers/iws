/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.responses.FallibleResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "offerStatisticsResponse", propOrder = { "domesticStatistics", "foreignStatistics" })
public final class OfferStatisticsResponse extends FallibleResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private OfferStatistics domesticStatistics = null;
    @XmlElement(required = true, nillable = true) private OfferStatistics foreignStatistics = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     * Constructor is used in {@code OfferResponse} when deleting an offer.
     */
    public OfferStatisticsResponse() {
        // Required for WebServices to work. Comment added to please Sonar.
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

    public void setDomesticStatistics(final OfferStatistics domesticStatistics) {
        this.domesticStatistics = domesticStatistics;
    }

    public OfferStatistics getDomesticStatistics() {
        // If the response is null - then we're just returning an empty
        // Statistics Object, to avoid NullPointerExceptions.
        return domesticStatistics != null ? domesticStatistics : new OfferStatistics();
    }

    public void setForeignStatistics(final OfferStatistics foreignStatistics) {
        this.foreignStatistics = foreignStatistics;
    }

    public OfferStatistics getForeignStatistics() {
        // If the response is null - then we're just returning an empty
        // Statistics Object, to avoid NullPointerExceptions.
        return foreignStatistics != null ? foreignStatistics : new OfferStatistics();
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

        if ((domesticStatistics != null) ? !domesticStatistics.equals(that.domesticStatistics) : (that.domesticStatistics != null)) {
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

        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((domesticStatistics != null) ? domesticStatistics.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((foreignStatistics != null) ? foreignStatistics.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "OfferStatisticsResponse{" +
                "domesticStatistics=" + domesticStatistics +
                ", foreignStatistics=" + foreignStatistics +
                '}';
    }
}
