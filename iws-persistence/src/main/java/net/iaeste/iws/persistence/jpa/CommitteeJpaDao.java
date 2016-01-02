/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.CommitteeJpaDao
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

import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.common.configuration.InternalConstants;
import net.iaeste.iws.persistence.CommitteeDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.EntityConstants;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class CommitteeJpaDao extends BasicJpaDao implements CommitteeDao {

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public CommitteeJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    // =========================================================================
    // Implementation of the Committee Dao Interface
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findCommitteesByContryIds(final List<String> countryIds, final Set<GroupStatus> statuses) {
        final String jql =
                "select u " +
                "from UserGroupEntity u " +
                "where u.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL +
                "  and u.role.id = " + EntityConstants.ROLE_OWNER +
                "  and u.group.country.countryCode in :countryIds" +
                "  and u.group.status in :statuses";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("countryIds", countryIds);
        query.setParameter("statuses", statuses);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findCommitteesByMembership(final Membership membership, final Set<GroupStatus> statuses) {
        final String jql =
                "select u " +
                "from UserGroupEntity u " +
                "where u.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL +
                "  and u.role.id = " + EntityConstants.ROLE_OWNER +
                "  and u.group.country.membership = :membership" +
                "  and u.group.status in :statuses";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("membership", membership);
        query.setParameter("statuses", statuses);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findGroupsByTypeAndStatuses(final GroupType type, final Set<GroupStatus> statuses) {
        final String jql =
                "select u " +
                "from UserGroupEntity u " +
                "where u.group.groupType.grouptype = :type" +
                "  and u.role.id = " + EntityConstants.ROLE_OWNER +
                "  and u.group.status in :statuses";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("type", type);
        query.setParameter("statuses", statuses);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findGroupByExternalId(final String externalGroupId) {
        final Set<GroupStatus> statuses = EnumSet.of(GroupStatus.ACTIVE, GroupStatus.SUSPENDED);
        final String jql =
                "select g from GroupEntity g " +
                "where g.status in :statuses" +
                "  and g.externalId = :egid";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("statuses", statuses);
        query.setParameter("egid", externalGroupId);

        return findSingleResult(query, "group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findUserByExternalId(final String externalUserId) {
        final Query query = entityManager.createNamedQuery("user.findActiveByExternalId");
        query.setParameter("euid", externalUserId);

        return findSingleResult(query, "user");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findGroupByName(final String groupName) {
        final String jql =
                "select g from GroupEntity g " +
                "where lower(g.groupName) = lower(:name)";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("name", groupName.trim());

        return findSingleResult(query, "group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findGroupByNameAndType(final String groupName, final GroupType type) {
        final String jql =
                "select g from GroupEntity g " +
                "where lower(g.groupName) = lower(:name)" +
                "  and g.groupType.grouptype = :type";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("name", groupName.trim());
        query.setParameter("type", type);

        return findSingleResult(query, "group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupTypeEntity findGroupTypeByType(final GroupType type) {
        final Query query = entityManager.createNamedQuery("grouptype.findByType");
        query.setParameter("type", type);

        return findSingleResult(query, "groupType");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleEntity findRole(final Long id) {
        final Query query = entityManager.createNamedQuery("role.findById");
        query.setParameter("id", id);

        return findSingleResult(query, "role");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findGroupOwner(final GroupEntity groupEntity) {
        final String jql =
                "select ug from UserGroupEntity ug " +
                "where ug.group.id = :gid" +
                "  and ug.role.id = :rid";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("gid", groupEntity.getId());
        query.setParameter("rid", InternalConstants.ROLE_OWNER);

        return findSingleResult(query, "UserGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findExistingRelation(final GroupEntity groupEntity, final UserEntity userEntity) {
        final Query query = entityManager.createNamedQuery("usergroup.findByGroupAndUser");
        query.setParameter("gid", groupEntity.getId());
        query.setParameter("uid", userEntity.getId());

        return findSingleResult(query, "UserGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findAllCommitteesForCountry(final CountryEntity country) {
        final String jql =
                "select g from GroupEntity g " +
                "where g.country.id = :cid" +
                "  and g.status <> " + EntityConstants.GROUP_STATUS_DELETED +
                "  and g.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL;

        final Query query = entityManager.createQuery(jql);
        query.setParameter("cid", country.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findMemberGroupForStaff(final GroupEntity staff) {
        final String jql =
                "select g from GroupEntity g " +
                "where g.id = :pgid";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("pgid", staff.getParentId());

        return findSingleResult(query, "Group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findGroupMembers(final GroupEntity group, final Set<UserStatus> statuses) {
        final String jql =
                "select ug from UserGroupEntity ug " +
                "where ug.user.status in :status" +
                "  and ug.group.id = :gid";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("status", statuses);
        query.setParameter("gid", group.getId());

        return query.getResultList();
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
    public int deleteSessions(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("session.deleteUserSessions");
        query.setParameter("uid", user.getId());

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findSubgroups(final GroupEntity group) {
        final String jql =
                "select g from GroupEntity g " +
                "where g.status in :statuses" +
                "  and g.parentId = :gid";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("statuses", EnumSet.of(GroupStatus.ACTIVE, GroupStatus.SUSPENDED));
        query.setParameter("gid", group.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findUserByUsername(final String username) {
        final String jql =
                "select u from UserEntity u " +
                "where lower(u.username) = lower(:username)";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("username", username);

        return findSingleResult(query, "User");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findUserGroupRelation(final GroupEntity group, final UserEntity user) {
        final String jql =
                "select ug from UserGroupEntity ug " +
                "where ug.group.id = :gid" +
                "  and ug.user.id = :uid";

        final Query query = entityManager.createQuery(jql);
        query.setParameter("gid", group.getId());
        query.setParameter("uid", user.getId());

        return findSingleResult(query, "User");
    }
}
