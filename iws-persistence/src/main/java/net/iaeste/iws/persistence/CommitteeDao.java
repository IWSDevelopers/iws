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

    /**
     * Finds a list of Committee National Secretaries (with group type
     * NATIONAL), matching the given list of countries and status values.
     *
     * @param countryIds CountryId's (2 letter), to use for the lookup
     * @param statuses   The Status values to lookup with
     * @return List of Committees with their National Secretaries
     */
    List<UserGroupEntity> findCommitteesByContryIds(List<String> countryIds, Set<GroupStatus> statuses);

    List<UserGroupEntity> findCommitteesByMembership(Membership membership, Set<GroupStatus> statuses);

    List<UserGroupEntity> findGroupsByTypeAndStatuses(GroupType type, Set<GroupStatus> statuses);

    GroupEntity findGroupByExternalId(String externalGroupId);

    UserEntity findUserByExternalId(String externalUserId);

    GroupEntity findGroupByName(String groupName);

    GroupEntity findGroupByNameAndType(String groupName, GroupType type);

    GroupTypeEntity findGroupTypeByType(GroupType type);

    /**
     * Finds a Role in the IWS, based on the given Id. If no role is found, or
     * if multiple roles is found, an {@link net.iaeste.iws.persistence.exceptions.IdentificationException}
     * is thrown. Otherwise, the found Role is returned.
     *
     * @param id Role Id
     * @return Role
     * @throws net.iaeste.iws.persistence.exceptions.IdentificationException if none or multiple results is found
     */
    RoleEntity findRole(Long id);

    /**
     * Finds and returns the Owner of a Group. If no Owner is found, then an
     * {@link net.iaeste.iws.persistence.exceptions.IdentificationException} is
     * thrown - this is something that is considered a flaw and should never
     * happen.
     *
     * @param groupEntity Group to find the Owner for
     * @return Group Owner
     * @throws net.iaeste.iws.persistence.exceptions.IdentificationException if no Owner is found
     */
    UserGroupEntity findGroupOwner(GroupEntity groupEntity);

    UserGroupEntity findExistingRelation(GroupEntity groupEntity, UserEntity userEntity);

    List<GroupEntity> findAllCommitteesForCountry(CountryEntity country);

    /**
     * Finds the Member Group for the given Staff, if no Member Group is found,
     * then an {@link net.iaeste.iws.persistence.exceptions.IdentificationException}
     * is thrown otherwise the found Member Group is returned.
     *
     * @param staff The Staff to find the Member (parent) Group for
     * @return Found Member Group
     * @throws net.iaeste.iws.persistence.exceptions.IdentificationException if no MemberGroup was found
     */
    GroupEntity findMemberGroupForStaff(GroupEntity staff);

    List<GroupEntity> findSubgroups(GroupEntity group);

    UserEntity findUserByUsername(String username);

    /**
     * Finds the relation between a Group and a User, and returns this. If no
     * relation is found, then an {@link net.iaeste.iws.persistence.exceptions.IdentificationException}
     * is thrown.
     *
     * @param group Group Entity to find a relation for
     * @param user  User Entity to find a relation for
     * @return Found UserGroup relation
     * @throws net.iaeste.iws.persistence.exceptions.IdentificationException if no relation is found
     */
    UserGroupEntity findUserGroupRelation(GroupEntity group, UserEntity user);
}
