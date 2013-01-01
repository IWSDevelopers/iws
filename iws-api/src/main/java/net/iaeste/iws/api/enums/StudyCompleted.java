/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.StudyCompleted
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
 * StudyCompleted Object for the Exchange Module.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public enum StudyCompleted {

    /**
     * Beginning (1-3 Semesters)
     */
    BEGINNING,

    /**
     * Middle (4-6 Semesters)
     */
    MIDDLE,

    /**
     * Beginning or Middle (1-6  Semesters)
     */
    BEGINNING_MIDDLE,

    /**
     * End (7 and more semesters)
     */
    END,

    /**
     * Beginning or End
     */
    BEGINNING_END,

    /**
     * Middle or End (4 and more semesters)
     */
    MIDDLE_END,

    /**
     * Beginning or Middle or End
     */
    BEGINNING_MIDDLE_END
}
