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

import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.PublishingGroupEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ExchangeJpaDao extends BasicJpaDao implements ExchangeDao {

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     * @param settings      IWS System Settings
     */
    public ExchangeJpaDao(final EntityManager entityManager, final Settings settings) {
        super(entityManager, settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerEntity findEmployer(final Authentication authentication, final String externalId) {
        final Query query = entityManager.createNamedQuery("employer.findByExternalId");
        query.setParameter("eid", externalId);
        query.setParameter("pid", authentication.getGroup().getParentId());

        return findUniqueResult(query, "Employer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerEntity findUniqueEmployer(final Authentication authentication, final Employer employer) {
        final Query query = entityManager.createNamedQuery("employer.findEmployerByValues");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("name", employer.getName());
        query.setParameter("department", employer.getDepartment());
        query.setParameter("workingPlace", employer.getWorkingPlace());

        return findSingleResult(query, "Employer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findAllOffers(final Authentication authentication) {
        final Query query = entityManager.createNamedQuery("offer.findAllForGroup");
        query.setParameter("gid", authentication.getGroup().getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferEntity findOfferByOwnerAndExternalId(final Authentication authentication, final String externalId) {
        final Query query = entityManager.createNamedQuery("offer.findByGroupAndExternalId");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("eid", externalId);

        return findSingleResult(query, "Offer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferEntity findOfferByRefNo(final Authentication authentication, final String refNo) {
        OfferEntity entity = null;

        if (refNo != null) {
            final Query query = entityManager.createNamedQuery("offer.findByGroupAndRefNo");
            query.setParameter("gid", authentication.getGroup().getId());
            query.setParameter("refNo", refNo);

            final List<OfferEntity> found = query.getResultList();
            if (found.size() == 1) {
                entity = found.get(0);
            }
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferEntity findOfferByExternalIdAndRefNo(final Authentication authentication, final String externalId, final String refNo) {
        final Query query = entityManager.createNamedQuery("offer.findByGroupAndExternalIdAndRefNo");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("eid", externalId);
        query.setParameter("refno", refNo);
        final List<OfferEntity> found = query.getResultList();

        OfferEntity entity = null;
        if (!found.isEmpty()) {
            entity = found.get(0);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferGroupEntity> findInfoForSharedOffer(final Long offerId) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByOffer");
        query.setParameter("oid", offerId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferGroupEntity> findInfoForSharedOffer(final String externalOfferId) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByExternalOfferId");
        query.setParameter("eoid", externalOfferId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferGroupEntity findInfoForSharedOffer(final GroupEntity group, final String offerId) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByGroupAndExternalId");
        query.setParameter("eoid", offerId);
        query.setParameter("gid", group.getId());

        return findSingleResult(query, "OfferGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferGroupEntity> findInfoForSharedOffers(final GroupEntity group, final Set<String> offerIds) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByGroupAndExternalIds");
        query.setParameter("eoids", expandEmptyCollection(offerIds, ""));
        query.setParameter("gid", group.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer unshareFromAllGroups(final Long offerId) {
        final Query query = entityManager.createNamedQuery("offerGroup.deleteByOffer");
        query.setParameter("oid", offerId);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer unshareFromGroups(final Long offerId, final List<Long> groups) {
        final Query query = entityManager.createNamedQuery("offerGroup.deleteByOfferIdAndGroups");
        query.setParameter("oid", offerId);
        query.setParameter("gids", expandEmptyCollection(groups, -1L));

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findGroupByExternalIds(final List<String> externalIds) {
        final Query query = entityManager.createNamedQuery("group.findByExternalGroupIds");
        query.setParameter("egids", expandEmptyCollection(externalIds, ""));

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findGroupsForSharing(final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("group.findGroupsForSharing");
        query.setParameter("gid", group.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findExpiredOffers(final Date currentDate) {
        final Query query = entityManager.createNamedQuery("offer.findExpired");
        query.setParameter("date", currentDate);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOfferState(final List<Long> ids, final OfferState state) {
        final Query query = entityManager.createNamedQuery("offer.updateStateByIds");
        query.setParameter("ids", expandEmptyCollection(ids, -1L));
        query.setParameter("status", state);

        query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideOfferGroups(final List<Long> ids) {
        final Query query = entityManager.createNamedQuery("offerGroup.hideByIds");
        query.setParameter("ids", expandEmptyCollection(ids, -1L));

        query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishingGroupEntity getSharingListByExternalIdAndOwnerId(final String externalId, final Long groupId) {
        final Query query = entityManager.createNamedQuery("publishingGroup.findByExternalIdAndGroupId");
        query.setParameter("eid", externalId);
        query.setParameter("gid", groupId);

        return findSingleResult(query, "PublishingGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PublishingGroupEntity> getSharingListForOwner(final Long id) {
        final Query query = entityManager.createNamedQuery("publishingGroup.findForOwner");
        query.setParameter("gid", id);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findNationalGroupsById(final List<String> groupIds) {
        final String jql =
                "select g from  GroupEntity g " +
                "where g.groupType.grouptype = :type" +
                "  and g.externalId in :list";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("type", GroupType.NATIONAL);
        query.setParameter("list", groupIds);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findNationalGroupsByIdForSharing(final GroupEntity owner, final List<String> groupIds) {
        final String jql =
                "select g from GroupEntity g " +
                "where g.id != :owner" +
                "  and g.groupType.grouptype = :type" +
                "  and g.externalId in :ids";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("owner", owner.getId());
        query.setParameter("type", GroupType.NATIONAL);
        query.setParameter("ids", groupIds);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferEntity findOfferByOwnerAndIdentifier(final Authentication authentication, final String identifier) {
        final String jql =
                "select o from OfferEntity o " +
                "where o.employer.group = :owner" +
                "  and o.status != :state" +
                "  and (o.externalId = :identifier" +
                "     or o.refNo = :identifier)";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("owner", authentication.getGroup());
        query.setParameter("state", OfferState.DELETED);
        query.setParameter("identifier", identifier);

        return findSingleResult(query, "Offer");
    }
}
