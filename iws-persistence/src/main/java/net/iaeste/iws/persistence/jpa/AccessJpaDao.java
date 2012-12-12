/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.exceptions.AuthenticationException;
import net.iaeste.iws.common.exceptions.AuthorizationException;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
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
    public UserEntity findUserByCredentials(final String username, final String passwordHashcode) {
        final Query query = entityManager.createNamedQuery("user.loginCredentials");
        query.setParameter("username", username);
        query.setParameter("password", passwordHashcode);
        final List<UserEntity> result = query.getResultList();

        if (result.size() != 1) {
            throw new IWSException(IWSErrors.AUTHORIZATION_ERROR, "No account for the user '" + username + "' was found.");
        }

        return result.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findUserByUsername(final String username) {
        final Query query = entityManager.createNamedQuery("user.findByUserName");
        query.setParameter("username", username);
        final List<UserEntity> list = query.getResultList();

        final UserEntity user;
        if (list.size() == 1) {
            user = list.get(0);
        } else if (list.isEmpty()) {
            user = null;
        } else {
            throw new IWSException(IWSErrors.DATABASE_CONSTRAINT_INCONSISTENCY, "There exists multiple records for a user with username " + username);
        }

        return user;
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

        final UserEntity user;
        if (list.size() == 1) {
            user = list.get(0);
        } else {
            throw new IWSException(IWSErrors.DATABASE_CONSTRAINT_INCONSISTENCY, "There exists multiple records for a user with the code " + code);
        }

        return user;
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
        // SQL Grammar Exception
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
    public GroupEntity findMemberGroup(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("group.findGroupByUserAndType");
        query.setParameter("uid", user.getId());
        query.setParameter("type", GroupType.MEMBER.name());

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
        query.setParameter("type", GroupType.PRIVATE.name());

        return (GroupEntity) query.getSingleResult();
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
    public RoleEntity findRoleByUserAndGrouo(final String externalUserId, final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("role.findByUserAndGroup");
        query.setParameter("euid", externalUserId);
        query.setParameter("gid", group.getId());

        return (RoleEntity) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findUserByExternalId(final String externalUserId) {
        final Query query = entityManager.createNamedQuery("user.findByExternalId");
        query.setParameter("euid", externalUserId);

        return (UserEntity) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findMemberByExternalId(final String externalUserId, final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("usergroup.findMemberByGroupAndUser");
        query.setParameter("gid", group.getId());
        query.setParameter("euid", externalUserId);

        return (UserGroupEntity) query.getSingleResult();
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

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private <T extends IWSEntity> T findUniqueResult(final Query query, final String name) {
        final List<T> found = query.getResultList();

        if (found.isEmpty()) {
            throw new AuthenticationException("No " + name + " was found.");
        }

        if (found.size() > 1) {
            throw new AuthenticationException("Multiple " + name + "s were found.");
        }

        return found.get(0);
    }
}
