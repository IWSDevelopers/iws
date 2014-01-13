/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.UserType
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
 * The different types of Users we have. From IW3, the values were added as part
 * of release 113, and the following was found in the SQL update script:
 * <pre>
 *   ALTER TABLE users ADD COLUMN type VARCHAR(1);
 *   ALTER TABLE users ALTER COLUMN type SET DEFAULT 'v';
 *   UPDATE users SET type =
 *     CASE
 *       WHEN volunteer = 't' THEN 'v'
 *       WHEN volunteer = 'f' THEN 'e'
 *       ELSE 'x'
 *     END;
 *   ALTER TABLE users DROP COLUMN volunteer;
 * </pre>
 * Based on the above SQL changes, it is assumed that we have three types.
 * During the migration, it was discovered that 30 accounts were having type
 * 'x', interpreted as Unknown.<br />
 *   In IWS, we've added a fourth type, student. Instead, hence the Student type
 * was added existing list of Volunteers, Employed & Unknown.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public enum UserType {

    /**
     * By default, all new accounts are made with type Volunteer. A Volunteer is
     * defined as someone associated (working for) IAESTE, but is not payed for
     * their services.
     */
    VOLUNTEER("Volunteer"),

    /**
     * All users who are payed for their IAESTE work are listed with type
     * Employed.
     */
    EMPLOYED("Employed"),

    /**
     * All User Accounts solely for Students are listed with this type. Students
     * are
     */
    STUDENT("Student"),

    /**
     * The fallback type, if nothing else is provided. The Unknown type should
     * never be used.
     */
    UNKNOWN("Unknown");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    UserType(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
