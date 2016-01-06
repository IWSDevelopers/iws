/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.AdminDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence;

import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

import java.util.List;

/**
 * Data Access Object with the functionality to perform the most basic
 * operations on all Users, Groups and Countries.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface AdminDao extends BasicDao {

    // =========================================================================
    // County specific DAO functionality
    // =========================================================================

    /**
     * Retrieves the Emergency Phone list, which is the list of all Owners and
     * Moderators from the National Committees.
     *
     * @return List of all Owners and Moderators of National Groups
     */
    List<UserGroupEntity> findEmergencyList();

    List<UserGroupEntity> findUserGroupsForContactsByExternalUserId(String externalUserId);

    List<UserGroupEntity> findUserGroupsForContactsByExternalGroupId(String externalGroupId);

    List<UserGroupEntity> searchUsers(String firstname, String lastname);

    List<UserGroupEntity> searchUsers(String firstname, String lastname, String externalMemberGroupId);

    List<GroupEntity> findGroupsForContacts();
}
