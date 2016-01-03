/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.Action
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
 * <p>When a IWS Processing Request is made, then it can sometimes be hard from
 * the context to guess what needs to be done. And to avoid that we split up the
 * Processing requests in more special parts, an Action is instead assigned, so
 * it is possible to tell what should be done.</p>
 *
 * <p>The Actions described in this Enum is of a more general nature, not all
 * Actions apply to the same Request, which is why all Processing Requests is
 * also having a list of allowed Actions, which the requested Action is checked
 * against.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlType(name = "action")
public enum Action {

    /**
     * Create a new Object, depending on the Context, it can be Group, User or
     * new Committee, i.e. Co-operating Institution or anything else.
     */
    CREATE,

    /**
     * Updating a Committee, i.e. change Institution Name &amp; Abbreviation.
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
     * Allows a Data Object with location information to be moved, i.e. a Folder
     * or a File can be moved from one Folder to another, provided both have the
     * same Group ownership.
     */
    MOVE,

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
     * <p>Deletion may also be done against other data, where the process
     * of deleting it will determine how much is erased.</p>
     */
    DELETE
}
