/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse
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
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.util.AbstractFallible;

import java.util.List;
import java.util.Map;

/**
 * Response Object for returning sharing info for specified list of offers.
 * Returned value is a Map with an offer ID as key and a list of OfferGroup
 * as sharing details for particular offer in the map value.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class FetchPublishedGroupsResponse extends AbstractFallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Map<String, List<Group>> offersGroups = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchPublishedGroupsResponse() {
    }

    /**
     * Default Constructor.
     *
     * @param offersGroups List of Offers found
     */
    public FetchPublishedGroupsResponse(final Map<String, List<Group>> offersGroups) {
        this.offersGroups = offersGroups;
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public FetchPublishedGroupsResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setOffersGroups(final Map<String, List<Group>> offersGroups) {
        this.offersGroups = offersGroups;
    }

    public Map<String, List<Group>> getOffersGroups() {
        return offersGroups;
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

        if (!(obj instanceof FetchPublishedGroupsResponse)) {
            return false;
        }

        final FetchPublishedGroupsResponse that = (FetchPublishedGroupsResponse) obj;
        return !((offersGroups != null) ? !offersGroups.equals(that.offersGroups) : (that.offersGroups != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((offersGroups != null) ? offersGroups.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FetchOffersResponse{" +
                "offers=" + offersGroups +
                '}';
    }
}
