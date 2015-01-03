/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.daos.IW3JpaDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.daos;

import net.iaeste.iws.migrate.entities.IW3CountriesEntity;
import net.iaeste.iws.migrate.entities.IW3GroupsEntity;
import net.iaeste.iws.migrate.entities.IW3Offer2GroupEntity;
import net.iaeste.iws.migrate.entities.IW3OffersEntity;
import net.iaeste.iws.migrate.entities.IW3ProfilesEntity;
import net.iaeste.iws.migrate.entities.IW3User2GroupEntity;
import net.iaeste.iws.migrate.migrators.Migrator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Repository("iw3Dao")
@Transactional(value = "transactionManagerIW3", readOnly = true)
public class IW3JpaDao implements IW3Dao {

    private EntityManager entityManager;

    @PersistenceContext(unitName = "IW3PersistenceUnit")
    public void setIw3EntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // =========================================================================
    // Implementation of the IW3Dao functionality
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IW3CountriesEntity> findAllCountries() {
        final Query query = entityManager.createNamedQuery("countries.findAll");
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IW3GroupsEntity> findAllGroups() {
        final Query query = entityManager.createNamedQuery("groups.findAll");
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countProfiles() {
        return (Long) entityManager.createNamedQuery("profiles.countAll").getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IW3ProfilesEntity> findProfiles(final int page) {
        final Query query = entityManager.createNamedQuery("profiles.findAll");
        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countUserGroups() {
        return (Long) entityManager.createNamedQuery("usergroup.countAll").getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IW3User2GroupEntity> findUserGroups(final int page) {
        final Query query = entityManager.createNamedQuery("usergroup.findAll");
        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countOffers() {
        return (Long) entityManager.createNamedQuery("offers.countAll").getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IW3OffersEntity> findOffers(final int page) {
        final Query query = entityManager.createNamedQuery("offers.findAll");
        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countOfferGroups() {
        return (Long) entityManager.createNamedQuery("offergroup.countAll").getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IW3Offer2GroupEntity> findOfferGroups(final int page) {
        final Query query = entityManager.createNamedQuery("offergroup.findAll");
        return fetchList(query, page);
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private static <T> List<T> fetchList(final Query query, final int page) {
        query.setFirstResult(page * Migrator.BLOCK_SIZE);
        query.setMaxResults(Migrator.BLOCK_SIZE);

        return query.getResultList();
    }
}
