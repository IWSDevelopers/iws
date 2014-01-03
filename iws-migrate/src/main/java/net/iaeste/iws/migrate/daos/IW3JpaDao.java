/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.migrate.entities.IW3FacultiesEntity;
import net.iaeste.iws.migrate.entities.IW3GroupsEntity;
import net.iaeste.iws.migrate.entities.IW3Offer2GroupEntity;
import net.iaeste.iws.migrate.entities.IW3OffersEntity;
import net.iaeste.iws.migrate.entities.IW3ProfilesEntity;
import net.iaeste.iws.migrate.entities.IW3User2GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Transactional("transactionManagerIW3")
public class IW3JpaDao implements IW3Dao {

    @Autowired(required = true)
    private EntityManager entityManager = null;

    public IW3JpaDao(final EntityManager entityManager) {
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
    public IW3FacultiesEntity findFaculty(final String name) {
        final Query query = entityManager.createNamedQuery("faculty.findByName");
        query.setParameter("name", name);
        final List<IW3FacultiesEntity> found = query.getResultList();

        return found.isEmpty() ? null : found.get(0);
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
    public List<IW3ProfilesEntity> findAllProfiles() {
        final Query query = entityManager.createNamedQuery("profiles.findAll");
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IW3User2GroupEntity> findAllUserGroups() {
        final Query query = entityManager.createNamedQuery("usergroup.findAll");
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IW3OffersEntity> findAllOffers(final int page, final int size) {
        final Query query = entityManager.createNamedQuery("offers.findAll");
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IW3Offer2GroupEntity> findAllOfferGroups(final int page, final int size) {
        final Query query = entityManager.createNamedQuery("offergroup.findAll");
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return query.getResultList();
    }
}
