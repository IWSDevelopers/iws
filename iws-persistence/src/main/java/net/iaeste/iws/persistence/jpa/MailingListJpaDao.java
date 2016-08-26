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

    // Internal constants for the different query parameters, which is (re)used
    private static final String PARAMETER_GROUP_STATUS = "groupStatus";
    private static final String PARAMETER_USER_STATUS = "userStatus";
    private static final String PARAMETER_ADDRESS = "address";
    private static final String PARAMETER_STATUS = "status";
    private static final String PARAMETER_ONLIST = "onList";
    private static final String PARAMETER_MAYWRITE = "mayWrite";

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public MailingListJpaDao(final EntityManager entityManager, final Settings settings) {
        super(entityManager, settings);
    }

    // =========================================================================
    // Implementation of the MailingList DAO
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MailinglistEntity> findMailingList(final GroupEntity group) {
        return entityManager
                .createNamedQuery("mailinglist.findByGroupId")
                .setParameter("gid", group.getId())
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailinglistEntity findMailingList(final String address) {
        final Query query = entityManager
                .createNamedQuery("mailinglist.findByAddress")
                .setParameter(PARAMETER_ADDRESS, address);

        return findSingleResult(query, "Mailinglist");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AliasEntity> findAliases() {
        return entityManager
                .createNamedQuery("alias.findAll")
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMailinglistEntity findSubscription(final MailinglistEntity list, final UserGroupEntity subscriber) {
        final Query query = entityManager
                .createNamedQuery("userMailinglist.findSubscription")
                .setParameter("mailinglist", list)
                .setParameter("subscriber", subscriber.getId());

        return findSingleResult(query, "UserMailinglist");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findUnprocessedGroups() {
        return entityManager
                .createNamedQuery("mailinglist.findUnprocessedGroups")
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findUnprocessedSubscriptions() {
        return entityManager
                .createNamedQuery("userMailinglist.findUnprocessedSubscriptions")
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findMissingNcsSubscribers() {
        return entityManager
                .createNamedQuery("userMailinglist.findMissingNcsSubscribers")
                .setParameter(PARAMETER_GROUP_STATUS, GroupStatus.ACTIVE)
                .setParameter(PARAMETER_USER_STATUS, UserStatus.ACTIVE)
                .setParameter("types", EnumSet.of(GroupType.ADMINISTRATION, GroupType.INTERNATIONAL, GroupType.NATIONAL))
                .setParameter(PARAMETER_ADDRESS, settings.getNcsList() + '@' + settings.getPrivateMailAddress())
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findDeprecatedNcsSubscribers() {
        return entityManager
                .createNamedQuery("userMailinglist.findDeprecatedNcsSubscribers")
                .setParameter("groupStatus", GroupStatus.ACTIVE)
                .setParameter("userStatus", UserStatus.ACTIVE)
                .setParameter(PARAMETER_ADDRESS, settings.getNcsList() + '@' + settings.getPrivateMailAddress())
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findMissingAnnounceSubscribers() {
        return entityManager
                .createNamedQuery("userMailinglist.findMissingAnnounceSubscribers")
                .setParameter("type", GroupType.MEMBER)
                .setParameter(PARAMETER_STATUS, UserStatus.ACTIVE)
                .setParameter(PARAMETER_ADDRESS, settings.getAnnounceList() + '@' + settings.getPrivateMailAddress())
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateSubscribedAddress() {
        final List<UserMailinglistEntity> subscribers = entityManager
                .createNamedQuery("userMailinglist.findChangedSubscribers")
                .getResultList();

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
        return entityManager
                .createNamedQuery("userMailinglist.deleteSubscriber")
                .setParameter("mailinglist", mailingList.getId())
                .executeUpdate();
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
        return entityManager
                .createNamedQuery("mailinglist.updateState")
                .setParameter(PARAMETER_STATUS, status)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteDeprecatedMailinglists() {
        return entityManager
                .createNamedQuery("mailinglist.deleteDeprecatedLists")
                .setParameter(PARAMETER_STATUS, GroupStatus.DELETED)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteMailingLists(final GroupEntity group) {
        return entityManager
                .createNamedQuery("mailinglist.deleteListByGroup")
                .setParameter("gid", group.getId())
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int activatePrivateMailinglistSubscriptions() {
        return entityManager
                .createNamedQuery("userMailinglist.updatePrivateSubscriptions")
                .setParameter(PARAMETER_STATUS, UserStatus.ACTIVE)
                .setParameter(PARAMETER_ONLIST, true)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int activatePublicMailinglistSubscriptions() {
        return entityManager
                .createNamedQuery("userMailinglist.updatePublicSubscriptions")
                .setParameter(PARAMETER_STATUS, UserStatus.ACTIVE)
                .setParameter(PARAMETER_ONLIST, true)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addWritePermission() {
        return entityManager
                .createNamedQuery("userMailinglist.updateWritePermission")
                .setParameter(PARAMETER_MAYWRITE, true)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int removeWritePermission() {
        return entityManager
                .createNamedQuery("userMailinglist.updateWritePermission")
                .setParameter(PARAMETER_MAYWRITE, false)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int suspendPrivateMailinglistSubscriptions() {
        return entityManager
                .createNamedQuery("userMailinglist.updatePrivateSubscriptions")
                .setParameter(PARAMETER_STATUS, UserStatus.SUSPENDED)
                .setParameter(PARAMETER_ONLIST, false)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int suspendPublicMailinglistSubscriptions() {
        return entityManager
                .createNamedQuery("userMailinglist.updatePublicSubscriptions")
                .setParameter(PARAMETER_STATUS, UserStatus.SUSPENDED)
                .setParameter(PARAMETER_ONLIST, false)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteDeprecatedMailinglistSubscriptions() {
        return entityManager
                .createNamedQuery("userMailinglist.deleteDeprecatedSubscribers")
                .executeUpdate();
    }
}
