/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.exchange.LanguageOperator
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums.exchange;

import javax.xml.bind.annotation.XmlType;

/**
 * Possible choices for selecting if something is required or optional
 *
 * @author  Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "LanguageOperator")
public enum LanguageOperator {

    A("And"),
    O("Or");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    LanguageOperator(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
