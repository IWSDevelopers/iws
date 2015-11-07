/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static net.iaeste.iws.common.utils.StringUtils.toUpper;

import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.persistence.CountryDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.views.CountryView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
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
    public List<CountryEntity> getCountries(final List<String> countryCodes, final Paginatable page) {
        // First, let's ensure that the list of Country Codes are all Upper Case
        final List<String> codes = new ArrayList<>(countryCodes.size());
        for (final String code : countryCodes) {
            codes.add(toUpper(code));
        }

        final Query query = entityManager.createNamedQuery("country.findByCountryCodes");
        query.setFirstResult((page.pageNumber() - 1) * page.pageSize());
        query.setMaxResults(page.pageSize());
        query.setParameter("codes", codes);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CountryView> getMemberCountries(List<String> countryCodes, Paginatable page) {
        final List<String> codes = new ArrayList<>(countryCodes.size());
        for (final String code : countryCodes) {
            codes.add(toUpper(code));
        }

        final Query query = entityManager.createNamedQuery("view.findCountriesByCountryCode");
        query.setParameter("codes", codes);

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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CountryView> getAllCountries(final Paginatable page) {
        final Query query = entityManager.createNamedQuery("view.findAllCountries");

        return fetchList(query, page);
    }
}
