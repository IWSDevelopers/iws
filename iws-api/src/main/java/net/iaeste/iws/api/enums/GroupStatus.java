/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.GroupStatus
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
 * @since   IWS 1.0
 */
@XmlType(name = "groupStatus")
public enum GroupStatus {

    /**
     * The default value for all Groups, upon creation. It means that the Group
     * can be used and all data accessed without restrictions.
     */
    ACTIVE,

    /**
     * Groups, which are currently suspended, will not be visible or accessible.
     * If the Group is a Members Group, the users from this Group can no longer
     * log into the system. The purpose of this option, is to prevent misusage
     * of the system, without deleting the information. A Group with status
     * Blocked, can always be reactivated.
     */
    SUSPENDED,

    /**
     * Groups, which are Deleted, can no longer be used. Data belonging to the
     * Group will be deleted, and if the Group is a Members Group, then the
     * current members can no longer use the IWS, as this translates to their
     * accounts being deleted as well.<br />
     *   Shared information belonging to the Group, will not be deleted from
     * the system.<br />
     *   Note; This is a non-reversible state.
     */
    DELETED
}
