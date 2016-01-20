/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;
import net.iaeste.iws.persistence.views.UserPermissionView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AccessJpaDao extends BasicJpaDao implements AccessDao {

    private static final Integer DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

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
    public UserEntity findActiveUserByUsername(final String username) {
        final Query query = entityManager.createNamedQuery("user.findActiveByUserName");
        query.setParameter("username", username);

        return findSingleResult(query, "User");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findExistingUserByUsername(final String username) {
        final Query query = entityManager.createNamedQuery("user.findExistingByUsername");
        query.setParameter("username", username);

        return findSingleResult(query, "User");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findUserByUsername(final String username) {
        final Query query = entityManager.createNamedQuery("user.findByUserName");
        query.setParameter("username", username);

        return findSingleResult(query, "User");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findActiveUserByCode(final String code) {
        final Query query = entityManager.createNamedQuery("user.findActiveByCode");
        query.setParameter("code", code);

        return findSingleResult(query, "User");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findNewUserByCode(final String code) {
        final Query query = entityManager.createNamedQuery("user.findNewByCode");
        query.setParameter("code", code);

        return findSingleResult(query, "User");
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

        return findSingleResult(query, "Session");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionEntity findActiveSession(final AuthenticationToken token) {
        return findActiveSession(token.getToken());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionEntity findActiveSession(final String token) {
        final Query query = entityManager.createNamedQuery("session.findByToken");
        query.setParameter("key", token);

        return findUniqueResult(query, "AuthenticationToken");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SessionEntity> findActiveSessions() {
        final Query query = entityManager.createNamedQuery("session.findActive");
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer deprecateAllActiveSessions() {
        final Query query = entityManager.createNamedQuery("session.deprecateAllActiveSessions");
        query.setParameter("deprecated", generateTimestamp());

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer deprecateSession(final SessionEntity session) {
        final Query query = entityManager.createNamedQuery("session.deprecate");
        query.setParameter("deprecated", generateTimestamp());
        query.setParameter("id", session.getId());

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
            throw new AuthorizationException("User is not permitted to perform action '" + permission.getName() + "'.");
        } else {
            throw new AuthorizationException("User permission " + permission.getName() + " could not be uniquely identified for user, please provide the Group for the user.");
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

        return findSingleResult(query, "Group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findActiveGroupMembers(final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("userGroup.findActiveGroupMembers");
        query.setParameter("gid", group.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findAllGroupMembers(final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("userGroup.findAllGroupMembers");
        query.setParameter("gid", group.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findStudents(final Long memberGroupId) {
        final Query query = entityManager.createNamedQuery("userGroup.findStudents");
        query.setParameter("pid", memberGroupId);

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
    public List<StudentEntity> findStudentWithApplications(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("application.findStudentByUserId");
        query.setParameter("uid", user.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteStudent(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("student.deleteByUserId");
        query.setParameter("uid", user.getId());

        return query.executeUpdate();
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

        return !query.getResultList().isEmpty();
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
    public GroupEntity findNationalGroup(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("group.findNationalByUser");
        query.setParameter("uid", user.getId());

        return findSingleResult(query, "Group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findNationalGroupByLocalGroup(final Authentication authentication) {
        final Query query = entityManager.createNamedQuery("group.findNationalByLocalGroupAndUser");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("uid", authentication.getUser().getId());

        return findSingleResult(query, "Group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findPrivateGroup(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("group.findGroupByUserAndType");
        query.setParameter("uid", user.getId());
        query.setParameter("type", GroupType.PRIVATE);

        return findSingleResult(query, "Group");
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
    public GroupEntity findGroupByExternalId(final String externalId) {
        final Query query = entityManager.createNamedQuery("group.findByExternalId");
        query.setParameter("id", externalId);

        return findUniqueResult(query, "Group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleEntity findRoleById(final Long id) {
        final Query query = entityManager.createNamedQuery("role.findById");
        query.setParameter("id", id);

        return findSingleResult(query, "Role");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleEntity findRoleByUserAndGroup(final String externalUserId, final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("role.findByUserAndGroup");
        query.setParameter("euid", externalUserId);
        query.setParameter("gid", group.getId());

        return findSingleResult(query, "Role");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findActiveUserByExternalId(final String externalUserId) {
        final Query query = entityManager.createNamedQuery("user.findActiveByExternalId");
        query.setParameter("euid", externalUserId);

        return findSingleResult(query, "User");
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
    public UserEntity findNationalSecretaryByMemberGroup(final GroupEntity memberGroup) {
        final Query query = entityManager.createNamedQuery("userGroup.findNationalSecretaryByMemberGroup");
        query.setParameter("mgid", memberGroup.getParentId());

        final UserGroupEntity userGroupEntity = findUniqueResult(query, "UserGroup");
        return userGroupEntity.getUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findMemberByExternalId(final String externalUserId, final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("userGroup.findMemberByGroupAndUser");
        query.setParameter("gid", group.getId());
        query.setParameter("euid", externalUserId);

        return findSingleResult(query, "UserGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findMemberByExternalId(final String externalUserId) {
        final Query query = entityManager.createNamedQuery("userGroup.findMemberByUserExternalId");
        query.setParameter("euid", externalUserId);

        return findUniqueResult(query, "UserGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findMemberGroupByUser(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("userGroup.findMemberByUserId");
        query.setParameter("uid", user.getId());

        return findUniqueResult(query, "UserGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findByGroupAndExternalUserId(final GroupEntity group, final String externalUserId) {
        final Query query = entityManager.createNamedQuery("userGroup.findByGroupIdAndExternalUserId");
        query.setParameter("gid", group.getId());
        query.setParameter("euid", externalUserId);

        return findSingleResult(query, "UserGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findByGroupAndUser(final GroupEntity group, final UserEntity user) {
        final Query query = entityManager.createNamedQuery("userGroup.findByGroupIdAndUserId");
        query.setParameter("group", group);
        query.setParameter("user", user);

        return findSingleResult(query, "UserGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupTypeEntity findGroupTypeByType(final GroupType groupType) {
        final Query query = entityManager.createNamedQuery("grouptype.findByType");
        query.setParameter("type", groupType);

        return findSingleResult(query, "GroupType");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryEntity findCountryByCode(final String countryCode) {
        final Query query = entityManager.createNamedQuery("country.findByCountryCode");
        query.setParameter("code", countryCode);

        return findSingleResult(query, "Country");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleEntity findRoleByExternalIdAndGroup(final String reid, final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("role.findByExternalIdAndGroup");
        query.setParameter("reid", reid);
        query.setParameter("cid", (group.getCountry() != null) ? group.getCountry().getId() : null);
        query.setParameter("gid", group.getId());

        return findUniqueResult(query, "Role");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleEntity findRoleByExternalId(final String externalId) {
        final Query query = entityManager.createNamedQuery("role.findByExternalId");
        query.setParameter("erid", externalId);

        return findUniqueResult(query, "Role");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findStudentGroup(final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("group.findStudentGroup");
        query.setParameter("gid", group.getId());

        return findSingleResult(query, "Group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findAllUserGroups(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("userGroup.findAllUserGroups");
        query.setParameter("uid", user.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findGroupMemberByUsernameAndGroupExternalId(final String username, final String groupExternalId) {
        final Query query = entityManager.createNamedQuery("userGroup.findByUsernameAndGroupExternalId");
        query.setParameter("username", username);
        query.setParameter("egid", groupExternalId);

        return findSingleResult(query, "UserGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserEntity> findAccountsWithState(final UserStatus status, final Long daysBeforeExpiration) {
        final Date date = new Date(new Date().getTime() - (daysBeforeExpiration * DAY_IN_MILLIS));
        final Query query = entityManager.createNamedQuery("user.findAccountsWithStateAfterModification");
        query.setParameter("status", status);
        query.setParameter("days", date);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserEntity> findInactiveAccounts(final Long daysBeforeBecomingInactive) {
        // We need to run a query with more than just a sub-select, we need a
        // pre-query to things on. We could do this with a view, but views must
        // be mapped and requires entities. Alternatively, we can use this
        // native query, which uses standard SQL - in a form not (yet)
        // supported by JPA. The latter was chosen to reduce complexity for
        // something which is running as a cron job.
        //   Note; The query below is assuming that Users have logged into the
        // IWS. The latest time is present if they have, if they haven't, then
        // they are still listed even if younger than 1 year, so the latest flag
        // must also be evaluated!
        final String jpql =
                "with activity as (" +
                "  select" +
                "    u.id           as id," +
                "    max(s.created) as latest" +
                "  from" +
                "    users u" +
                "    left join sessions s on u.id = s.user_id" +
                "  where u.status = 'ACTIVE'" +
                "  group by" +
                "    u.id)" +
                "select id, latest " +
                "from activity " +
                "where latest is null" +
                "   or latest < :date";

        final Query query = entityManager.createNativeQuery(jpql);
        final int expires = (int) -daysBeforeBecomingInactive;
        final Date date = new net.iaeste.iws.api.util.Date().plusDays(expires).toDate();
        query.setParameter("date", date);

        // Our native query is returning a list of UserId's. These must be read
        // out and converted so we can use them to fetch the actual User
        // entities, which we are interested in.
        final List<Object[]> result = query.getResultList();
        final Collection<Long> userIds = new ArrayList<>(result.size());
        final Collection<Long> unusedUIds = new ArrayList<>(0);
        for (final Object[] record : result) {
            // The latest timestamp is set if the user have logged in, so we can
            // simply apply a null check and leave it with that :-)
            if (record[1] != null) {
                userIds.add(((Integer) record[0]).longValue());
            } else {
                unusedUIds.add(((Integer) record[0]).longValue());
            }
        }

        // Okay, now we have a list of UserId's, which we can use to retrieve
        // the actual User Entities
        final List<UserEntity> users = findUsersById(userIds);

        // Now, if we have some unused Id's, then it is important to check these
        // as well, to see if they fall into the unused category. We apply the
        // same rule, but to the Created timestamp.
        final List<UserEntity> unusedUsers = findUsersById(unusedUIds);
        for (final UserEntity user : unusedUsers) {
            if (user.getCreated().before(date)) {
                users.add(user);
            }
        }

        return users;
    }

    private List<UserEntity> findUsersById(final Collection<Long> userIds) {
        final List<UserEntity> result;

        if (!userIds.isEmpty()) {
            final Query userQuery = entityManager.createNamedQuery("user.findUsersByIds");
            userQuery.setParameter("ids", userIds);

            result = userQuery.getResultList();
        } else {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findNcsMember(final String username) {
        final Query query = entityManager.createNamedQuery("userGroup.userOnNCsList");
        query.setParameter("username", username);
        final List<UserEntity> found = query.getResultList();

        UserEntity result = null;
        if ((found != null) && (found.size() == 1)) {
            result = found.get(0);
        }

        return result;
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
