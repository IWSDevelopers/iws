/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.daos.IWSDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.daos;

import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface IWSDao {

    void persist(Object entity);

    /**
     * Finds a Country based on the given Id (ISO_3166-1_alpha-2).
     *
     * @param countryCode  Two letter CountryId, i.e. DE for Germany
     * @return Found Country or null
     */
    CountryEntity findCountry(String countryCode);

    /**
     * Finds a role based on the Id. Returns either the found RoleEntity or if
     * nothing was found - null.
     *
     * @param id  Id of the Role to find
     * @return Found RoleEntity or null
     */
    RoleEntity findRoleById(Long id);

    List<GroupEntity> findAllGroups();

    List<GroupEntity> findAllGroups(GroupType type);

    GroupTypeEntity findGroupType(GroupType groupType);

    UserEntity findUserByIW3Id(Integer oldId);

    GroupEntity findGroupByIW3Id(Integer oldId);

    UserGroupEntity findIw3UserGroup(Integer iw3UserId, Integer iw3GroupId);

    EmployerEntity findUniqueEmployer(GroupEntity group, EmployerEntity employer);

    OfferEntity findOfferByOldOfferId(Integer oldOfferId);

    Long countRefNos(String countryCode, String year, String serialNumber);

    List<UserEntity> findAllUsers();

    List<UserGroupEntity> findNCs();

    List<UserGroupEntity> findGroupMembers(Long groupId);
}
