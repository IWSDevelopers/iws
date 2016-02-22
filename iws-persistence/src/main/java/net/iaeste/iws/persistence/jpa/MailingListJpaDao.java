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
package net.iaeste.iws.persistence.jpa;

import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.MailinglistType;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.persistence.MailingListDao;
import net.iaeste.iws.persistence.entities.AliasEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.MailinglistEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.UserMailinglistEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.EnumSet;
import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class MailingListJpaDao extends BasicJpaDao implements MailingListDao {

    private static final Logger LOG = LoggerFactory.getLogger(MailingListJpaDao.class);

    private final Settings settings;

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public MailingListJpaDao(final EntityManager entityManager, final Settings settings) {
        super(entityManager);

        this.settings = settings;
    }

    // =========================================================================
    // Implementation of the MailingList DAO
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MailinglistEntity> findMailingList(final GroupEntity group) {
        final String jql =
                "select m " +
                "from MailinglistEntity m " +
                "where m.group.id = :group";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("group", group.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailinglistEntity findMailingList(final String address) {
        final String jql =
                "select m " +
                "from MailinglistEntity m " +
                "where m.listAddress = :address";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("address", address);

        return findSingleResult(query, "Mailinglist");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AliasEntity> findAliases() {
        final String jql =
                "select a from AliasEntity a";
        final Query query = entityManager.createQuery(jql);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMailinglistEntity findSubscription(final MailinglistEntity list, final UserGroupEntity subscriber) {
        final String jql =
                "select u " +
                "from UserMailinglistEntity u " +
                "where u.mailinglist = :list" +
                "  and u.userGroup.id = :subscriber";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("list", list);
        query.setParameter("subscriber", subscriber.getId());

        return findSingleResult(query, "UserMailinglist");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findUnprocessedGroups() {
        final String jql =
                "select g from GroupEntity g " +
                "where g.status = 'ACTIVE'" +
                "  and (g.publicList = true" +
                "    or g.privateList = true)" +
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
                "where u.user.status = 'ACTIVE'" +
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
    public List<UserGroupEntity> findMissingNcsSubscribers() {
        final String jql =
                "select ug " +
                "from UserGroupEntity ug " +
                "where ug.group.status = :groupStatus" +
                "  and ug.user.status = :userStatus" +
                "  and ug.onPublicList = true" +
                "  and ug.group.groupType.grouptype in (:types)" +
                "  and ug not in (" +
                "    select um.userGroup" +
                "    from UserMailinglistEntity um" +
                "    where um.mailinglist.listAddress = :address)";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("groupStatus", GroupStatus.ACTIVE);
        query.setParameter("userStatus", UserStatus.ACTIVE);
        query.setParameter("types", EnumSet.of(GroupType.ADMINISTRATION, GroupType.INTERNATIONAL, GroupType.NATIONAL));
        query.setParameter("address", settings.getNcsList() + '@' + settings.getPrivateMailAddress());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findDeprecatedNcsSubscribers() {
        final String jql =
                "select ug " +
                "from UserGroupEntity ug " +
                "where (ug.group.status != :groupStatus" +
                "  or ug.user.status != :userStatus" +
                "  or ug.onPublicList = false)" +
                "  and ug in (" +
                "    select um.userGroup" +
                "    from UserMailinglistEntity um" +
                "    where um.mailinglist.listAddress = :address)";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("groupStatus", GroupStatus.ACTIVE);
        query.setParameter("userStatus", UserStatus.ACTIVE);
        query.setParameter("address", settings.getNcsList() + '@' + settings.getPrivateMailAddress());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findMissingAnnounceSubscribers() {
        final String jql =
                "select ug " +
                "from UserGroupEntity ug " +
                "where ug.group.groupType.grouptype = :type" +
                "  and ug.user.status = :status" +
                "  and ug not in (" +
                "    select um.userGroup" +
                "    from UserMailinglistEntity um" +
                "    where um.mailinglist.listAddress = :address)";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("type", GroupType.MEMBER);
        query.setParameter("status", UserStatus.ACTIVE);
        query.setParameter("address", settings.getAnnounceList() + '@' + settings.getPrivateMailAddress());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateSubscribedAddress() {
        final String jql =
                "select u " +
                "from UserMailinglistEntity u " +
                "where u.member != u.userGroup.user.username";
        final Query query = entityManager.createQuery(jql);
        final List<UserMailinglistEntity> subscribers = query.getResultList();

        for (final UserMailinglistEntity subscriber : subscribers) {
            final UserEntity user = subscriber.getUserGroup().getUser();
            final String current = subscriber.getMember();
            final String updated = user.getUsername();
            final String name = user.getFirstname() + ' ' + user.getLastname();
            LOG.info("Updating Username for {} from '{}' to '{}'.", name, current, updated);

            subscriber.setMember(updated);
            persist(subscriber);
        }

        return subscribers.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteMailinglistSubscriptions(final MailinglistEntity mailingList) {
        final String jql =
                "delete from UserMailinglistEntity " +
                "where mailinglist.id = :list";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("list", mailingList.getId());

        return query.executeUpdate();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteDeprecatedMailinglists() {
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
                "where group.id = :group";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("group", group.getId());

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int activatePrivateMailinglistSubscriptions() {
        final String jql =
                "update UserMailinglistEntity set" +
                "   status = :status," +
                "   modified = current_timestamp " +
                "where userGroup.id in (" +
                "    select s.userGroup.id" +
                "    from UserMailinglistEntity s" +
                "    where s.mailinglist.listType = :type" +
                "      and s.status != :status" +
                "      and (s.userGroup.user.status = :status" +
                "        and s.userGroup.onPrivateList = true))";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("status", UserStatus.ACTIVE);
        query.setParameter("type", MailinglistType.PRIVATE_LIST);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int activatePublicMailinglistSubscriptions() {
        final String jql =
                "update UserMailinglistEntity set" +
                "   status = :status," +
                "   modified = current_timestamp " +
                "where userGroup.id in (" +
                "    select s.userGroup.id" +
                "    from UserMailinglistEntity s" +
                "    where s.mailinglist.listType != :type" +
                "      and s.status != :status" +
                "      and (s.userGroup.user.status = :status" +
                "        and s.userGroup.onPublicList = true))";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("status", UserStatus.ACTIVE);
        query.setParameter("type", MailinglistType.PRIVATE_LIST);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addWritePermission() {
        final String jql =
                "update UserMailinglistEntity set" +
                "   mayWrite = true," +
                "   modified = current_timestamp " +
                "where userGroup.id in (" +
                "    select s.userGroup.id" +
                "    from UserMailinglistEntity s" +
                "    where s.mailinglist.listType = :type" +
                "      and s.mayWrite = false" +
                "      and s.userGroup.writeToPrivateList = true)";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("type", MailinglistType.PRIVATE_LIST);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int removeWritePermission() {
        final String jql =
                "update UserMailinglistEntity set" +
                "   mayWrite = false," +
                "   modified = current_timestamp " +
                "where userGroup.id in (" +
                "    select s.userGroup.id" +
                "    from UserMailinglistEntity s" +
                "    where s.mailinglist.listType = :type" +
                "      and s.mayWrite = true" +
                "      and s.userGroup.writeToPrivateList = false)";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("type", MailinglistType.PRIVATE_LIST);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int suspendPrivateMailinglistSubscriptions() {
        final String jql =
                "update UserMailinglistEntity set" +
                "   status = :status," +
                "   modified = current_timestamp " +
                "where userGroup.id in (" +
                "    select s.userGroup.id" +
                "    from UserMailinglistEntity s" +
                "    where s.mailinglist.listType = :type" +
                "      and s.status != :status" +
                "      and (s.userGroup.user.status = :status" +
                "        or s.userGroup.onPrivateList = false))";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("status", UserStatus.SUSPENDED);
        query.setParameter("type", MailinglistType.PRIVATE_LIST);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int suspendPublicMailinglistSubscriptions() {
        final String jql =
                "update UserMailinglistEntity set" +
                        "   status = :status," +
                        "   modified = current_timestamp " +
                        "where userGroup.id in (" +
                        "    select s.userGroup.id" +
                        "    from UserMailinglistEntity s" +
                        "    where s.mailinglist.listType != :type" +
                        "      and s.status != :status" +
                        "      and (s.userGroup.user.status = :status" +
                        "        or s.userGroup.onPublicList = false))";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("status", UserStatus.SUSPENDED);
        query.setParameter("type", MailinglistType.PRIVATE_LIST);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteDeprecatedMailinglistSubscriptions() {
        final String jql =
                "delete from UserMailinglistEntity " +
                "where userGroup.id in (" +
                "    select s.id" +
                "    from UserGroupEntity s" +
                "    where s.user.status = 'DELETED')";
        final Query query = entityManager.createQuery(jql);

        return query.executeUpdate();
    }
}
