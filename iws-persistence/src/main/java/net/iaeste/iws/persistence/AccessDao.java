/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.views.UserPermissionView;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface AccessDao extends BasicDao {

    UserEntity findUserByCredentials(String username, String passwordHashcode);

    UserEntity findUserByUsername(String username);

    UserEntity findUserByCodeAndStatus(String code, UserStatus status);

    SessionEntity findActiveSession(UserEntity user);

    SessionEntity findActiveSession(AuthenticationToken token);

    Integer deprecateSession(UserEntity user);

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

    GroupEntity findGroup(UserEntity user, String groupId, Permission permission);

    GroupEntity findGroup(UserEntity user, String groupId);

    GroupEntity findNationalGroup(UserEntity user);

    GroupEntity findPrivateGroup(UserEntity user);

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
}
