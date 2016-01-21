/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.MailingListJpaDao
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
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.common.utils.StringUtils;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.MailingListDao;
import net.iaeste.iws.persistence.entities.AliasEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.MailinglistEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.UserMailinglistEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class MailingListJpaDao extends BasicJpaDao implements MailingListDao {

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public MailingListJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MailinglistEntity> findMailinglists(final GroupEntity group) {
        final Query query = entityManager.createQuery("select m from MailinglistEntity m where m.group = :group");
        query.setParameter("group", group);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMailinglistEntity findMailingListSubscription(final MailinglistEntity list, final UserGroupEntity member) {
        final Query query = entityManager.createQuery("select u from UserMailinglistEntity u where u.mailinglist = :list and u.userGroup = :member");
        query.setParameter("list", list);
        query.setParameter("member", member);

        final List<UserMailinglistEntity> found = query.getResultList();
        UserMailinglistEntity entity = null;
        if (!found.isEmpty()) {
            entity = found.get(0);
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMailinglistEntity findMailingListSubscription(final Long listId, final String emailAddress) {
        final Query query = entityManager.createNamedQuery("userMailingList.findSubscriptionByUserAddressAndListId");
        query.setParameter("lid", listId);
        query.setParameter("userAddress", StringUtils.toLower(emailAddress));

        return findSingleResult(query, "mailinglist");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMailinglistEntity> findMailingListSubscription(final UserGroupEntity userGroup) {
        final Query query = entityManager.createNamedQuery("userMailingList.findSubscriptionForUser");
        query.setParameter("ug", userGroup);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailinglistEntity findNcsList(final String ncsList) {
        final Query query = entityManager.createNamedQuery("mailingList.findByMailingListAddress");
        query.setParameter("address", StringUtils.toLower(ncsList));

        return findSingleResult(query, "NCs List");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMailinglistEntity findListByName(final String name) {
        final Query query = entityManager.createNamedQuery("userMailingList.findByMailingListAddress");
        query.setParameter("address", StringUtils.toLower(name));

        return findSingleResult(query, "UserMailinglist");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MailinglistEntity> findListsByGroup(final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("mailingList.findListsByGroup");
        query.setParameter("group", group);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AliasEntity> findAliasesForGroup(final GroupEntity group) {
        final String jql =
                "select a " +
                "from AliasEntity a " +
                "where a.group = :group" +
                "  and a.deprecated is null";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("group", group);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findUnprocessedGroups() {
        final String jql =
                "select g from GroupEntity g " +
                "where g.status <> 'DELETED'" +
                "  and g.id not in (" +
                "    select m.group.id" +
                "    from MailinglistEntity m)";
        final Query query = entityManager.createQuery(jql);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findUnprocessedSubscriptions() {
        final String jql =
                "select u from UserGroupEntity u " +
                "where u.user.status <> 'DELETED'" +
                "  and u.id not in (" +
                "    select s.userGroup.id" +
                "    from UserMailinglistEntity s)";
        final Query query = entityManager.createQuery(jql);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int activateMailinglists() {
        return changeMailingListState(GroupStatus.ACTIVE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int suspendMailinglists() {
        return changeMailingListState(GroupStatus.SUSPENDED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteDeadMailinglists() {
        final String jql =
                "delete from MailinglistEntity " +
                "where group.id in (" +
                "    select g.id" +
                "    from GroupEntity g" +
                "    where g.status = :status)";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("status", GroupStatus.DELETED);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteMailingLists(final GroupEntity group) {
        final String jql =
                "delete from MailinglistEntity " +
                "where group = :group";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("group", group);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int activateMailinglistSubscriptions() {
        return changeMailingListSubscriptionState(UserStatus.ACTIVE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int suspendMailinglistSubscriptions() {
        return changeMailingListSubscriptionState(UserStatus.SUSPENDED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteDeadMailinglistSubscriptions() {
        final String jql =
                "delete from UserMailinglistEntity " +
                "where userGroup.id in (" +
                "    select s.id" +
                "    from UserGroupEntity s" +
                "    where s.user.status = 'DELETED')";
        final Query query = entityManager.createQuery(jql);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailinglistEntity findAliasList(final AliasEntity alias) {
        // The data is always written with lowercase in the table, and as the
        // data is not suppose to be touched by "human hands", it is safe to
        // assume that we can find it alone via this query.
        final String jql =
                "select m " +
                "from MailinglistEntity m " +
                "where listAddress = :address";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("address", alias.getAliasAddress());

        // In the mailing list Entity, it is the complete public e-mail address
        // which is stored, and this address must be unique! So, looking up the
        // address based on the list alone should ensure that we find either a
        // mailing list or nothing.
        final List<MailinglistEntity> list = query.getResultList();
        MailinglistEntity entity = null;
        if (list.size() == 1) {
            entity = list.get(0);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deprecateAlias(final Authentication authentication, final AliasEntity alias) {
        alias.setDeprecated(generateTimestamp());
        persist(authentication, alias);
    }

    private int changeMailingListState(final GroupStatus status) {
        final String jql =
                "update MailinglistEntity set" +
                "   status = :status," +
                "   modified = current_timestamp " +
                "where group.id in (" +
                "    select m.group.id" +
                "    from MailinglistEntity m" +
                "    where m.group.status = :status" +
                "      and m.status <> m.group.status)";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("status", status);

        return query.executeUpdate();
    }

    private int changeMailingListSubscriptionState(final UserStatus status) {
        final String jql =
                "update UserMailinglistEntity set" +
                "   status = :status," +
                "   modified = current_timestamp " +
                "where userGroup.id in (" +
                "    select s.userGroup.id" +
                "    from UserMailinglistEntity s" +
                "    where s.userGroup.user.status = :status" +
                "      and s.status <> s.userGroup.user.status)";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("status", status);

        return query.executeUpdate();
    }
}
