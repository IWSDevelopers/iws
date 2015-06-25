/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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

import javax.xml.bind.annotation.XmlType;

/**
 * The different types of Users we have. From IW3, the values were added as part
 * of release 1.13, and the following was found in the SQL update script:
 * <pre>
 *   ALTER TABLE users ADD COLUMN type VARCHAR(1);
 *   ALTER TABLE users ALTER COLUMN type SET DEFAULT 'v';
 *   CHANGE_NS users SET type =
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
 *   In IWS, we've added two more types, student & functional. which complements
 * the previous ones to provide a more suitable range of Accounts, where rules
 * can later be added to these, to improve internal data management.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "UserType")
public enum UserType implements Descriptable<UserType> {

    /**
     * By default, all new Accounts are made with type Volunteer. A Volunteer is
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
     * Some Accounts are purely used with automated tools, such as cron jobs.
     * These Accounts can be treated differently than other accounts.
     */
    FUNCTIONAL("Functional"),

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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }
}
