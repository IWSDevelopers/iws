/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.CountryType
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public enum CountryType {

    /**
     * Default Type for requesting a list of Countries. The Listing is based on
     * the current list of members. Members are here defined as Full, Associate,
     * Co-operating Institutions and former members where information still
     * exists in the system.<br />
     *   When this type is invoked, the list of National Secretaries and the
     * mailing list for Committees are also added.
     */
    COMMITTEES("IAESTE Committees"),

    /**
     * This listing means that the retrieval will not focus on the IAESTE
     * Members, but rather on all countries. The listing should follow the UN
     * list of countries, but it may differ since it is not automatically
     * updated whenever changes occur to the UN listing.<br />
     *   When this type is invoked, then the result will deliberately not
     * contain any information that is IAESTE related, other than the current
     * membership type.
     */
    COUNTRIES("Country List");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    CountryType(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
