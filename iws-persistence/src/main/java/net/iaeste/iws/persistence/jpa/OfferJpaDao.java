/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.persistence.entities.OfferEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class OfferJpaDao implements OfferDao {

    private final EntityManager entityManager;

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public OfferJpaDao(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(final OfferEntity offer) {
        entityManager.persist(offer);
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
}
