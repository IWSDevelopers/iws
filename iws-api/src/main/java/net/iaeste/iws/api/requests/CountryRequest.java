/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.CountryRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class CountryRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Country country = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public CountryRequest() {
    }

    /**
     * Default Constructor, for creating or updating a country.
     *
     * @param country Country Object
     */
    public CountryRequest(final Country country) {
        setCountry(country);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the internal Country Object, provided that the given Object is
     * valid, i.e. that it is not null and that it passes the verification
     * test.<br />
     *   Upon successfull verification, a copy of the given Object is stored
     * internally.
     *
     * @param country Country
     */
    public void setCountry(final Country country) {
        ensureNotNullAndVerifiable("country", country);

        this.country = new Country(country);
    }

    /**
     * Returns a copy of the internal Country Object.
     *
     * @return Copy of the Country Object
     */
    public Country getCountry() {
        return new Country(country);
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

        isVerifiable(validation, "country", country);

        return validation;
    }
}
