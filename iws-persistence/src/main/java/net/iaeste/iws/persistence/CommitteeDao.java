/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.CommitteeDao
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

import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

import java.util.List;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public interface CommitteeDao extends BasicDao {

    List<UserGroupEntity> findCommitteesByContryIds(List<String> countryIds, Set<GroupStatus> statuses);

    List<UserGroupEntity> findCommitteesByMembership(Membership membership, Set<GroupStatus> statuses);

    List<UserGroupEntity> findGroupsByTypeAndStatuses(GroupType type, Set<GroupStatus> statuses);

    GroupEntity findGroupByExternalId(String externalGroupId);

    UserEntity findUserByExternalId(String externalUserId);

    GroupEntity findGroupByName(String groupName);

    GroupEntity findGroupByNameAndType(String groupName, GroupType type);

    GroupTypeEntity findGroupTypeByType(GroupType type);

    RoleEntity findRole(Long id);

    UserGroupEntity findGroupOwner(GroupEntity groupEntity);

    UserGroupEntity findExistingRelation(GroupEntity groupEntity, UserEntity userEntity);

    List<GroupEntity> findAllCommitteesForCountry(CountryEntity country);

    GroupEntity findMemberGroupForStaff(GroupEntity staff);

    List<GroupEntity> findSubgroups(GroupEntity group);

    UserEntity findUserByUsername(String username);

    UserGroupEntity findUserGroupRelation(GroupEntity group, UserEntity user);
}
