/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.Institution
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

import javax.xml.bind.annotation.XmlType;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlType(name = "Institution")
public enum Institution implements Descriptable<Institution> {

    ASSOCIATION("Association"),
    UNIVERSITY("University"),
    NGO("NGO"),
    GOVERNMENT_INSTITUTIONS("Government Institutions"),
    OTHER("Other");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    Institution(final String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }
}
