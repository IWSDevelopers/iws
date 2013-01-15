/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.OfferJpaDao
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

import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.OfferEntity;
import net.iaeste.iws.persistence.entities.OfferGroupEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class OfferJpaDao extends BasicJpaDao implements OfferDao {

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public OfferJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findAll() {
        final Query query = entityManager.createNamedQuery("OfferEntity.findAll");

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferEntity findOffer(final Long offerId) {
        OfferEntity entity = null;

        if (offerId != null) {
            final Query query = entityManager.createNamedQuery("OfferEntity.findById");
            query.setParameter("id", offerId);

            final List<OfferEntity> found = query.getResultList();
            if (found.size() == 1) {
                entity = found.get(0);
            }
        }

        return entity;
    }

    @Override
    public OfferEntity findOffer(final String refNo) {
        OfferEntity entity = null;

        if (refNo != null) {
            final Query query = entityManager.createNamedQuery("OfferEntity.findByRefNo");
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
    public List<OfferEntity> findOffers(final List<Long> offerIds) {
        final Query query = entityManager.createNamedQuery("OfferEntity.findByIds");
        query.setParameter("ids", offerIds);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findOffersByEmployerName(final String employerName, final Long ownerId) {
        final Query query = entityManager.createNamedQuery("OfferEntity.findByEmployerName");
        query.setParameter("employerName", employerName);
        query.setParameter("groupId", ownerId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findOffersByLikeEmployerName(final String employerName, final Long ownerId) {
        final Query query = entityManager.createNamedQuery("OfferEntity.findByLikeEmployerName");
        query.setParameter("employerName", '%' + employerName.toLowerCase() + '%');
        query.setParameter("groupId", ownerId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findOffersByOwnerId(final Long ownerId) {
        final Query query = entityManager.createNamedQuery("OfferEntity.findByOwnerId");
        query.setParameter("id", ownerId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findSharedOffers() {
        final Query query = entityManager.createNamedQuery("OfferEntity.findShared");

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferGroupEntity> findGroupsForSharedOffer(final Long offerId) {
        final Query query = entityManager.createNamedQuery("OfferGroupEntity.findGroupsByOffer");
        query.setParameter("oid", offerId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferGroupEntity> findGroupsForSharedOffer(final String refNo) {
        final Query query = entityManager.createNamedQuery("OfferGroupEntity.findGroupsByOfferRefNo");
        query.setParameter("refno", refNo);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer unshareFromAllGroups(final Long offerId) {
        final Query query = entityManager.createNamedQuery("OfferGroupEntity.deleteByOffer");
        query.setParameter("oid", offerId);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer unshareFromAllGroups(final String refNo) {
        /*
        TODO not working, getting 'unexpected token: CROSS'
        final Query query = entityManager.createNamedQuery("OfferGroupEntity.deleteByOfferRefNo");
        query.setParameter("refno", refNo);

        return query.executeUpdate();
        */
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer unshareFromGroups(final Long offerId, final List<Long> groups) {
        //TODO passing empty list fails, correct?
        final Query query = entityManager.createNamedQuery("OfferGroupEntity.deleteByOfferAndGroups");
        query.setParameter("oid", offerId);
        query.setParameter("gids", groups);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer unshareFromGroups(final String refNo, final List<Long> groups) {
        /*
        TODO not working, getting 'unexpected token: CROSS'
        //TODO passing empty list fails, correct?
        final Query query = entityManager.createNamedQuery("OfferGroupEntity.deleteByOfferRefNoAndGroups");
        query.setParameter("refno", refNo);
        query.setParameter("gids", groups);

        return query.executeUpdate();
        */
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final Long offerId) {
        final OfferEntity offer = findOffer(offerId);
        boolean result = false;

        if (offer != null) {
            entityManager.remove(offer);
            result = true;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer delete(final List<Long> offerIds) {
        final Query query = entityManager.createNamedQuery("OfferEntity.deleteByIds");
        query.setParameter("ids", offerIds);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findGroupByExternalIds(List<String> externalIds) {
        final Query query = entityManager.createNamedQuery("group.findByExternalGroupIds");
        query.setParameter("egids", externalIds);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findGroupByExternalId(final String externalId) {
        List<String> externalIds = new ArrayList<>(1);
        externalIds.add(externalId);
        List<GroupEntity> groups = findGroupByExternalIds(externalIds);

        GroupEntity group = null;

        if (groups.size() == 1) {
            group = groups.get(0);
        }

        return group;
    }
}
