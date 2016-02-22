/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
