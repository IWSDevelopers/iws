/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.exchange.StudyLevel
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

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public enum StudyLevel {

    B("Begin (1-3 semesters)"),
    M("Middle (4-6 semesters)"),
    E("End (7 or semesters)");

    // ========================================================================
    // Private Constructor & functionality
    // ========================================================================

    private final String description;

    StudyLevel(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
