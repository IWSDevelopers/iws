/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.Actions
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
@XmlType(name = "Action")
public enum Action {

    /**
     * Create a new Cooperating Institution.
     */
    CREATE,

    /**
     * Updating a Committee, i.e. change Institution Name & Abbreviation.
     */
    UPDATE,

    /**
     * Upgrade a Committee from Cooperating Institution to Associate Member,
     * if there is currently only a single Cooperating Institution for the
     * given Country, or upgrades an Associate Member to Full Member.
     */
    UPGRADE,

    /**
     * Processing a record means either Creating a new record or update an
     * existing, based on the current state of the system.
     */
    PROCESS,

    /**
     * Change the National Secretary.
     */
    CHANGE_NS,

    /**
     * Upgrades a Committee from Cooperating Institution to Associate Member.
     */
    MERGE,

    /**
     * Activate a currently Suspended Committee.
     */
    ACTIVATE,

    /**
     * Suspend a currently Active Committee.
     */
    SUSPEND,

    /**
     * <p>Deletes a record from the system. For Users, only NEW or SUSPENDED
     * Users can be deleted, otherwise the rule apply that only suspended Users,
     * Groups or Committees can be deleted.</p>
     *
     * <p></p>Deletion may also be done against other data, where the process
     * of deleting it will determine how much is erased.</p>
     */
    DELETE
}
