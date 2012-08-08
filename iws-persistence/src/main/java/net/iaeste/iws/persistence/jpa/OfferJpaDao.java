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

import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.entities.OfferEntity;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class OfferJpaDao implements OfferDao {

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

    @Override
    public List<OfferEntity> findAll() {
        return entityManager.createNamedQuery("OfferEntity.findAll", OfferEntity.class).getResultList();
    }

    @Override
    public OfferEntity findOffer(final Long offerId) {
        final List<OfferEntity> offers = entityManager.createNamedQuery("OfferEntity.findById", OfferEntity.class)
                .setParameter("id", offerId).getResultList();
        if (offers.size() != 1) {
            return null;
        }
        return offers.get(0);

    }

    @Override
    public List<OfferEntity> findOffers(final List<Long> offerIds) {
        final List<OfferEntity> offers = entityManager.createNamedQuery("OfferEntity.findByIds", OfferEntity.class)
                .setParameter("ids", offerIds).getResultList();
        return offers;
    }

    @Override
    public boolean delete(final Long offerId) {
        final OfferEntity offer = findOffer(offerId);
        if (offer == null) {
            return false;
        } else {
            entityManager.remove(offer);
            return true;
        }
    }

    @Override
    public Integer delete(final List<Long> offerIds) {
        return entityManager.createNamedQuery("OfferEntity.deleteByIds").setParameter("ids", offerIds).executeUpdate();
    }

}
