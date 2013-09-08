/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.AccessJpaDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.jpa;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.common.exceptions.AuthenticationException;
import net.iaeste.iws.common.exceptions.AuthorizationException;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.views.UserPermissionView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection ReturnOfNull, CastToConcreteClass
 */
public class AccessJpaDao extends BasicJpaDao implements AccessDao {

    /**
     * Default Constructor.
     *
     * @param entityManager  Entity Manager instance to use
     */
    public AccessJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findUserByUsername(final String username) {
        final Query query = entityManager.createNamedQuery("user.findByUserName");
        query.setParameter("username", username);
        final List<UserEntity> list = query.getResultList();

        return resolveResultList(list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findUserByCodeAndStatus(final String code, final UserStatus status) {
        final Query query = entityManager.createNamedQuery("user.findByCodeAndStatus");
        query.setParameter("code", code);
        query.setParameter("status", status);
        final List<UserEntity> list = query.getResultList();

        return resolveResultList(list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findUserByAlias(final String alias) {
        final Query query = entityManager.createNamedQuery("user.findByAlias");
        query.setParameter("alias", alias);
        final List<UserEntity> list = query.getResultList();

        return resolveResultList(list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionEntity findActiveSession(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("session.findByUser");
        query.setParameter("id", user.getId());
        final List<SessionEntity> found = query.getResultList();

        if (found.size() > 1) {
            throw new AuthenticationException("Multiple Active sessions exists.");
        }

        return found.isEmpty() ? null : found.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionEntity findActiveSession(final AuthenticationToken token) {
        final Query query = entityManager.createNamedQuery("session.findByToken");
        query.setParameter("key", token.getToken());

        return findUniqueResult(query, "AuthenticationToken");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer deprecateSession(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("session.deprecate");
        // Funny, setting the status to false directly in the query, causes an
        // SQL Grammar Exception, though it actually makes sense since booleans
        // are implemented differently in dofferent databases, so JPA needs the
        // query pbject to convert it properly
        query.setParameter("status", false);
        query.setParameter("id", user.getId());

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteSessions(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("session.deleteUserSessions");
        query.setParameter("uid", user.getId());

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserPermissionView> findPermissions(final Authentication authentication, final String externalGroupId) {
        final Query query;
        if (externalGroupId != null) {
            query = entityManager.createNamedQuery("view.findUserGroupPermissions");
            query.setParameter("egid", externalGroupId);
        } else {
            query = entityManager.createNamedQuery("view.findAllUserPermissions");
        }
        query.setParameter("uid", authentication.getUser().getId());

        final List<UserPermissionView> list = query.getResultList();
        if (list.isEmpty()) {
            throw new AuthorizationException("User is not a member of the group with Id: " + externalGroupId);
        }

        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupTypeEntity findGroupType(final GroupType groupType) {
        final Query query = entityManager.createNamedQuery("grouptype.findByName");
        // Query runs a String lower check on the value
        query.setParameter("name", groupType.name());
        final List<GroupTypeEntity> groupTypes = query.getResultList();

        return groupTypes.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findGroupByPermission(final UserEntity user, final String groupId, final Permission permission) {
        final Query query;

        if (groupId == null) {
            query = entityManager.createNamedQuery("group.findByPermission");
        } else {
            query = entityManager.createNamedQuery("group.findByExternalGroupIdAndPermission");
            query.setParameter("egid", groupId);
        }
        query.setParameter("uid", user.getId());
        query.setParameter("permission", permission);
        final List<GroupEntity> groups = query.getResultList();

        if (groups.size() == 1) {
            return groups.get(0);
        } else if (groups.isEmpty()) {
            throw new AuthorizationException("User is not permitted to perform actions of type: " + permission);
        } else {
            throw new AuthorizationException("User permission could not be uniquely identified, please provide the Group for the user.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findGroup(final UserEntity user, final String externalGroupId) {
        final Query query = entityManager.createNamedQuery("group.findByUserAndExternalId");
        query.setParameter("uid", user.getId());
        query.setParameter("eid", externalGroupId);

        return (GroupEntity) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findGroupUsers(final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("usergroup.findGroupMembers");
        query.setParameter("gid", group.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findSubGroups(final Long parentId) {
        final Query query = entityManager.createNamedQuery("group.findSubGroupsByParentId");
        query.setParameter("pid", parentId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean hasGroupsWithSimilarName(final GroupEntity group, final String name) {
        final Query query = entityManager.createNamedQuery("group.findGroupsWithSimilarNames");
        query.setParameter("gid", group.getId());
        query.setParameter("pid", group.getParentId());
        query.setParameter("name", name.toLowerCase(IWSConstants.DEFAULT_LOCALE));

        final List<GroupEntity> result = query.getResultList();
        return !result.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findGroupByUserAndType(final UserEntity user, final GroupType type) {
        final Query query = entityManager.createNamedQuery("group.findGroupByUserAndType");
        query.setParameter("uid", user.getId());
        query.setParameter("type", type);

        return findUniqueResult(query, "Group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findMemberGroup(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("group.findGroupByUserAndType");
        query.setParameter("uid", user.getId());
        query.setParameter("type", GroupType.MEMBER);

        return (GroupEntity) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findNationalGroup(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("group.findNationalOrSarByUser");
        query.setParameter("uid", user.getId());

        return (GroupEntity) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findPrivateGroup(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("group.findGroupByUserAndType");
        query.setParameter("uid", user.getId());
        query.setParameter("type", GroupType.PRIVATE);

        return (GroupEntity) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findGroupByNameAndParent(final String groupName, final GroupEntity parent) {
        final Query query = entityManager.createNamedQuery("group.findGroupByParentAndName");
        query.setParameter("pid", parent.getId());
        query.setParameter("name", groupName);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleEntity findRoleById(final Long id) {
        final Query query = entityManager.createNamedQuery("role.findById");
        query.setParameter("id", id);

        return (RoleEntity) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleEntity findRoleByUserAndGroup(final String externalUserId, final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("role.findByUserAndGroup");
        query.setParameter("euid", externalUserId);
        query.setParameter("gid", group.getId());

        return findSingleResult(query, "role");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findUserByExternalId(final String externalUserId) {
        final Query query = entityManager.createNamedQuery("user.findByExternalId");
        query.setParameter("euid", externalUserId);

        return findUniqueResult(query, "user");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findMemberByGroupAndUser(final GroupEntity group, final UserEntity user) {
        final Query query = entityManager.createNamedQuery("usergroup.findByGroupAndUser");
        query.setParameter("gid", group.getId());
        query.setParameter("uid", user.getId());

        return findSingleResult(query, "usergroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findMemberByExternalId(final String externalUserId, final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("usergroup.findMemberByGroupAndUser");
        query.setParameter("gid", group.getId());
        query.setParameter("euid", externalUserId);

        return findSingleResult(query, "usergroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findMemberByExternalId(final String externalUserId) {
        final Query query = entityManager.createNamedQuery("usergroup.findMemberByUserExternalId");
        query.setParameter("euid", externalUserId);

        return findUniqueResult(query, "usergroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findMemberGroupByUser(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("usergroup.findMemberByUserId");
        query.setParameter("uid", user.getId());

        return findUniqueResult(query, "userGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleEntity> findRolesByName(final String role) {
        final Query query = entityManager.createNamedQuery("role.findRoleByName");
        query.setParameter("role", role);
        final List<RoleEntity> list = query.getResultList();

        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupTypeEntity findGroupTypeByType(final GroupType groupType) {
        final Query query = entityManager.createNamedQuery("grouptype.findByType");
        query.setParameter("type", groupType);
        final List<GroupTypeEntity> list = query.getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryEntity findCountryByCode(final String countryCode) {
        final Query query = entityManager.createNamedQuery("country.findByCountryCode");
        query.setParameter("code", countryCode);
        final List<CountryEntity> list = query.getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleEntity findRoleByExternalIdAndGroup(final String reid, final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("role.findByExternalIdAndGroup");
        query.setParameter("reid", reid);
        query.setParameter("cid", group.getCountry().getId());
        query.setParameter("gid", group.getId());

        return findUniqueResult(query, "role");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleEntity findRoleByExternalId(final String externalId) {
        final Query query = entityManager.createNamedQuery("role.findByExternalId");
        query.setParameter("erid", externalId);

        return findUniqueResult(query, "role");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findStudentGroup(final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("group.findStudentGroup");
        query.setParameter("gid", group.getId());
        final List<GroupEntity> list = query.getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * Overrides the parent method, since for Access, we're expecting a
     * different exception, if a unique result was not found.
     *
     * @param query      Query to resolve
     * @param entityName Name of the entity expected, used if exception is thrown
     * @return Unique result
     */
    @Override
    protected <T extends IWSEntity> T findUniqueResult(final Query query, final String entityName) {
        final List<T> found = query.getResultList();

        if (found.isEmpty()) {
            throw new AuthenticationException("No " + entityName + " was found.");
        }

        if (found.size() > 1) {
            throw new AuthenticationException("Multiple " + entityName + "s were found.");
        }

        return super.findUniqueResult(query, entityName);
    }
}
