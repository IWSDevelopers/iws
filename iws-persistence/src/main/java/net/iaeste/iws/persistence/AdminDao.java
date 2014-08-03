/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.persistence.entities.CountryEntity;
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
     * Finds the given Country by the name, if no such country exists, then the
     * method will return a null value, otherwise the found Country.
     *
     * @param countryName  The Name of the Country to find
     * @return Found {@code CountryEntity} or null
     */
    CountryEntity findCountryByName(String countryName);

    /**
     * Retrieves the Emergency Phone list, which is the list of all Owners and
     * Moderators from the National Committees.
     *
     * @return List of all Owners and Moderators of National Groups
     */
    List<UserGroupEntity> findEmergencyList();

    List<UserGroupEntity> findGroupMembers(String externalGroupId);

    List<UserGroupEntity> findUserGroups(String externalUserId);

    List<UserGroupEntity> searchUsers(String firstname, String lastname);

    List<UserGroupEntity> searchUsers(String firstname, String lastname, String externalMemberGroupId);

    List<GroupEntity> findGroupsForContacts();

    List<GroupEntity> findSubGroupsByParentId(Long parentGroupId);
}
