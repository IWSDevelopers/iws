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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class OfferStatistics extends AbstractVerification {

    /** {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    ///**
    // * Empty Constructor, to use if the setters are invoked. This is required
    // * for WebServices to work properly.
    // */
    //public OfferStatistics() {
    //}

    ///**
    // * Copy Constructor.
    // *
    // * @param statistics Offer Statistics Object to copy
    // */
    //public OfferStatistics(final OfferStatistics statistics) {
    //    if (statistics != null) {
    //    }
    //}

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "OfferStatistics{" +
                '}';
    }
}