/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.ContactsType
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
 * For the View Contacts request, there can be three different types of requests
 * made. This enum type is there to control the type better. It is set during
 * initialization of the Request Object, and returned as part of the response.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public enum ContactsType {

    USER("Viewing User Detauls"),
    GROUP("Viewing Group Details"),
    OTHER("Viewing Member & International Groups");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    ContactsType(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
