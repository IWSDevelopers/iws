/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.AdminJpaDao
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

import net.iaeste.iws.persistence.AdminDao;
import net.iaeste.iws.persistence.entities.CountryEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Default JPA implementation of the AdminDao, which contain the functionality
 * to work with users, groups and countries.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class AdminJpaDao extends BasicJpaDao implements AdminDao {

    /**
     * Default Constructor.
     *
     * @param entityManager  Entity Manager instance to use
     */
    public AdminJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryEntity findCountryByName(final String countryName) {
        final Query query = entityManager.createNamedQuery("country.findByName");
        query.setParameter("name", countryName);
        final List<CountryEntity> found = query.getResultList();

        return found.size() == 1 ? found.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CountryEntity> findAllCountries() {
        final Query query = entityManager.createNamedQuery("country.findAll");

        return query.getResultList();
    }
}
