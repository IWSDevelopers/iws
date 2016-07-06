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

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.views.UserPermissionView;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface AccessDao extends BasicDao {

    /**
     * Finds an {@code UserEntity} based on the given (unique) username, only
     * active users are processed.<br />
     *   If no such Entity exists, then a null is returned, if more than one
     * account (!) exists, then a fatal Exception is thrown, otherwise the found
     * Entity is returned.
     *
     * @param username Username
     * @return Found {@code UserEntity}
     * @throws IWSException if multiple accounts exists
     */
    UserEntity findActiveUserByUsername(String username);

    /**
     * Finds an {@code UserEntity} based on the given (unique) username, only
     * existing users are searched, meaning that the status of the account may
     * not be deleted.<br />
     *   If no such Entity exists, then a null is returned, if more than one
     * account (!) exists, then a fatal Exception is thrown, otherwise the found
     * Entity is returned.
     *
     * @param username Username
     * @return Found {@code UserEntity}
     * @throws IWSException if multiple accounts exists
     */
    UserEntity findExistingUserByUsername(String username);

    /**
     * Finds an {@code UserEntity} based on the given (unique) username,
     * with no restriction on its status.<br />
     *   If no such Entity exists, then a null is returned, if more than one
     * account (!) exists, then a fatal Exception is thrown, otherwise the found
     * Entity is returned.
     *
     * @param username Username
     * @return Found {@code UserEntity}
     * @throws IWSException if multiple accounts exists
     */
    UserEntity findUserByUsername(String username);

    /**
     * Finds a User from the given Code, and returns it. If no User was found,
     * then an {@link IdentificationException} is thrown.
     *
     * @param code User Session Key
     * @return User
     * @throws IdentificationException if no User was found with the given Active Session Key
     */
    UserEntity findActiveUserByCode(String code);

    UserEntity findNewUserByCode(String code);

    SessionEntity findActiveSession(UserEntity user);

    SessionEntity findActiveSession(AuthenticationToken token);

    SessionEntity findActiveSession(String token);

    /**
     * Finds all active Sessions from the database.
     */
    List<SessionEntity> findActiveSessions();

    /**
     * This will deprecate all currently active Sessons and return the number of
     * records which was updated.
     *
     * @return Number of Sessions deprecated
     */
    Integer deprecateAllActiveSessions();

    Integer deprecateSession(SessionEntity session);

    /**
     * Deletes all sessions for a given User, provided no error information is
     * associated with the session. All sessions with such information will be
     * deleted manually by the administrators, once the cause of the error has
     * been identified and potential bugs fixed.
     *
     * @param user User to delete all Sessions for
     * @return Number of Sessions deleted
     */
    int deleteSessions(UserEntity user);

    /**
     * Fetches a list of Permissions, that a user has towards a specific Group.
     * If no GroupId is given, then all permissions that a user has in the IWS
     * is returned.
     *
     * @param authentication  User Authentication information
     * @param externalGroupId Optional (external) GroupId of the Group to see
     *                        the permissions for
     * @return List of results from the PermissionView
     */
    List<UserPermissionView> findPermissions(Authentication authentication, String externalGroupId);

    GroupEntity findGroupByPermission(UserEntity user, String groupId, Permission permission);

    GroupEntity findGroup(UserEntity user, String externalGroupId);

    List<UserGroupEntity> findActiveGroupMembers(GroupEntity group);

    List<UserGroupEntity> findAllGroupMembers(GroupEntity group);

    List<UserGroupEntity> findStudents(Long memberGroupId);

    List<GroupEntity> findSubGroups(Long parentId);

    List<StudentEntity> findStudentWithApplications(UserEntity user);

    /**
     * Students are not linked from the UserEntity, so when deleting a
     * UserEntity in status new, we need to also delete the student.
     *
     * @param user User to delete the Student Entity for
     * @return Number of records deleted
     */
    int deleteStudent(UserEntity user);

    /**
     * Checks if there exists other groups with similar names (checked
     * case-insensitive), and returns true if so, otherwise false.
     *
     * @param group Group to compare potential new name for
     * @param name  The name to check
     * @return True if a group exists with similar name, otherwise false
     */
    Boolean hasGroupsWithSimilarName(GroupEntity group, String name);

    GroupEntity findGroupByUserAndType(UserEntity user, GroupType type);

    /**
     * Finds the Users National Group and returns this. If no National Group is
     * found, then an {@link IdentificationException} is thrown.
     *
     * @param user User to find the National Group for
     * @return National Group
     * @throws IdentificationException if no Group was found
     */
    GroupEntity findNationalGroup(UserEntity user);

    /**
     * Finds the National Group for a Given Local Group, and returns this. If
     * no such Group can be found, then an {@link IdentificationException} is
     * thrown as that indicates that there is a data quality problem in the IWS
     * Database.
     *
     * @param authentication User Authentication with Local Group Id
     * @return National Group
     * @throws IdentificationException if the National Group could not be found
     */
    GroupEntity findNationalGroupByLocalGroup(Authentication authentication);

    GroupEntity findPrivateGroup(UserEntity user);

    List<GroupEntity> findGroupByNameAndParent(String groupName, GroupEntity parent);

    GroupEntity findGroupByExternalId(String externalId);

    /**
     * Finds a Role based on the Id, and returns it. If no Role was found, then
     * an {@link IdentificationException} is thrown.
     *
     * @param id  Id of the Role to find
     * @return Found RoleEntity
     * @throws IdentificationException if no Role was found
     */
    RoleEntity findRoleById(Long id);

    RoleEntity findRoleByUserAndGroup(String externalUserId, GroupEntity group);

    UserEntity findNationalSecretaryByMemberGroup(GroupEntity memberGroup);

    UserEntity findActiveUserByExternalId(String externalUserId);

    UserEntity findUserByExternalId(String externalUserId);

    /**
     * Finds a user from the given Member Group. If no such user account is
     * associated with the the Group, then a null is returned, otherwise the
     * found UserEntity is returned.
     *
     * @param group          The MemberGroup to find the user from
     * @param externalUserId The users external Id
     * @return Found UserGroupEntity or null
     */
    UserGroupEntity findMemberByExternalId(String externalUserId, GroupEntity group);

    UserGroupEntity findMemberByExternalId(String externalUserId);

    UserGroupEntity findMemberGroupByUser(UserEntity user);

    UserGroupEntity findByGroupAndExternalUserId(GroupEntity group, String externalUserId);

    UserGroupEntity findByGroupAndUser(GroupEntity group, UserEntity user);

    GroupTypeEntity findGroupTypeByType(GroupType groupType);

    CountryEntity findCountryByCode(String countryCode);

    /**
     * Attempts to find the Student Group, which is associated with given
     * Members Group. The Members Group is the parent Group, and the lookup will
     * find the Group with GroupType STUDENTS, that is associated with it. Only
     * Country specific Members Groups have a Student Group associated, other
     * Members Group (for example, Global Members), do not have a Student Group
     * associated.<br />
     *   If no Student Group is found, then a null value is returned.
     *
     * @param group The MemberGroup to find the associated Student Group for
     * @return Student Group or null
     */
    GroupEntity findStudentGroup(GroupEntity group);

    RoleEntity findRoleByExternalIdAndGroup(String reid, GroupEntity group);

    RoleEntity findRoleByExternalId(String externalId);

    /**
     * Finds a list of User Accounts, which is having status NEW, but have not
     * been updated for x days. Where x is a number defined via the IWS
     * Settings, and injected here as parameter.
     *
     * @param  daysBeforeExpiration Days before a NEW account has expired
     * @return List of found User Accounts
     */
    List<UserEntity> findAccountsWithState(UserStatus status, Long daysBeforeExpiration);

    /**
     * Accounts, which have not been in used for a while is considered Inactive,
     * and will be suspended. Finding these is a rather heavy operation, as it
     * requires a check against the Session Table, to see who have been logging
     * in and who haven't.<br />
     *   The check is made against the Sessions, where the Login records is
     * reviewed. The last login record is used as base, and is compared against
     * the given number of days before an account is considered abandoned
     * (inactive).
     *
     * @param daysBeforeBecomingInactive Days before an account is inactive
     * @return List of Inactive Accounts
     */
    List<UserEntity> findInactiveAccounts(Long daysBeforeBecomingInactive);

    /**
     * Returns the Entity for the User, if the User is on the NC's mailing list,
     * otherwise a null is returned.
     *
     * @param username User's username (e-mail address)
     * @return Found User Entity if on the list, otherwise null
     */
    UserEntity findNcsMember(String username);

    int deletePrivateGroup(UserEntity user);
}
