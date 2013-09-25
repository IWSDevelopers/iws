/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.AccessDao
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
import net.iaeste.iws.persistence.views.UserPermissionView;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface AccessDao extends BasicDao {

    /**
     * Finds a {@code UserEntity} based on the given (unique) username. If no
     * such Entity exists, then a null is returned, if more than one account (!)
     * exists, then a fatal Exception is thrown, otherwise the found Entity is
     * returned.
     *
     * @param username Username
     * @return Found {@code UserEntity}
     * @throws IWSException if multiple accounts exists
     */
    UserEntity findUserByUsername(String username) throws IWSException;

    UserEntity findUserByCodeAndStatus(String code, UserStatus status);

    UserEntity findUserByAlias(String alias);

    Long findNumberOfAliasesForName(String name);

    SessionEntity findActiveSession(UserEntity user);

    SessionEntity findActiveSession(AuthenticationToken token);

    Integer deprecateSession(UserEntity user);

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

    GroupTypeEntity findGroupType(GroupType groupType);

    GroupEntity findGroupByPermission(UserEntity user, String groupId, Permission permission);

    GroupEntity findGroup(UserEntity user, String externalGroupId);

    List<UserGroupEntity> findGroupUsers(GroupEntity group);

    List<GroupEntity> findSubGroups(Long parentId);

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

    GroupEntity findMemberGroup(UserEntity user);

    GroupEntity findNationalGroup(UserEntity user);

    GroupEntity findPrivateGroup(UserEntity user);

    List<GroupEntity> findGroupByNameAndParent(String groupName, GroupEntity parent);

    GroupEntity findGroupByExternalId(String externalId);

    /**
     * Find a Role by the name. However, as it is possible to have multiple
     * roles with the same name, but assigned to different Countries or Groups,
     * the result of this method is a list.
     *
     * @param role Name of the role to find Entities for
     * @return List of all Roles in the IWS, matching the given name
     */
    List<RoleEntity> findRolesByName(String role);

    /**
     * Finds a role based on the Id. Returns either the found RoleEntity or if
     * nothing was found - null.
     *
     * @param id  Id of the Role to find
     * @return Found RoleEntity or null
     */
    RoleEntity findRoleById(Long id);

    RoleEntity findRoleByUserAndGroup(String externalUserId, GroupEntity group);

    UserEntity findUserByExternalId(String externalUserId);
    UserGroupEntity findMemberByGroupAndUser(GroupEntity group, UserEntity user);

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
     * Finds all users subscribed to group's public mailing list
     *
     * @param  group
     * @return List of UserGroupEntity
     */
    List<UserGroupEntity> findGroupUsersOnPublicList(GroupEntity group);

    /**
     * Finds all users subscribed to group's private mailing list
     *
     * @param  group
     * @return List of UserGroupEntity
     */
    List<UserGroupEntity> findGroupUsersOnPrivateList(GroupEntity group);

    /**
     * Finds all groups the user is member
     *
     * @param  user UserEntity
     * @return List of GroupEntity
     */
    List<UserGroupEntity> findAllUserGroups(UserEntity user);
}
