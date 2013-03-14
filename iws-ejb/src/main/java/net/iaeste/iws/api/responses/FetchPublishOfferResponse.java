/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.FetchPublishOfferResponse
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.OfferGroup;
import net.iaeste.iws.api.util.AbstractFallible;
import net.iaeste.iws.api.util.Copier;

import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass
 */
public final class FetchPublishOfferResponse extends AbstractFallible {

    /** {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;


    private List<OfferGroup> offerGroups;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchPublishOfferResponse() {
        super(IWSErrors.SUCCESS, IWSConstants.SUCCESS);
        offerGroups = null;
    }

    /**
     * Default Constructor.
     *
     * @param offerGroups List of Offers found
     */
    public FetchPublishOfferResponse(final List<OfferGroup> offerGroups) {
        this.offerGroups = Copier.copy(offerGroups);
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public FetchPublishOfferResponse(final IWSError error, final String message) {
        super(error, message);
        offerGroups = null;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setOfferGroups(final List<OfferGroup> offerGroups) {
        this.offerGroups = Copier.copy(offerGroups);
    }

    public List<OfferGroup> getOfferGroups() {
        return Copier.copy(offerGroups);
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

        if (!(obj instanceof FetchPublishOfferResponse)) {
            return false;
        }

        final FetchPublishOfferResponse that = (FetchPublishOfferResponse) obj;
        return !(offerGroups != null ? !offerGroups.equals(that.offerGroups) : that.offerGroups != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (offerGroups != null ? offerGroups.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FetchOffersResponse{" +
                "offers=" + offerGroups +
                '}';
    }
}
