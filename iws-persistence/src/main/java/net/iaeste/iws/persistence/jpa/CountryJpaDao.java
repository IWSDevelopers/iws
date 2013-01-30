/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.CountryJpaDao
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.persistence.CountryDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.views.CountryView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class CountryJpaDao extends BasicJpaDao implements CountryDao {

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public CountryJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryEntity findCountry(final String countryId) {
        final Query query = entityManager.createNamedQuery("country.findByCountryId");
        query.setParameter("cid", countryId.toLowerCase(IWSConstants.DEFAULT_LOCALE));
        final List<CountryEntity> list = query.getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryEntity findCountryByName(final String countryName) {
        final Query query = entityManager.createNamedQuery("country.findByName");
        query.setParameter("name", countryName);
        final List<CountryEntity> list = query.getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CountryView> getCountries(final List<String> countryIds, final Paginatable page) {
        final Query query = entityManager.createNamedQuery("view.findCountriesByCountryIds");
        query.setParameter("ids", countryIds);

        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CountryView> getCountries(final Membership membership, final Paginatable page) {
        final Query query = entityManager.createNamedQuery("view.findCountriesByMembership");
        query.setParameter("type", membership);

        return fetchList(query, page);
    }
}
