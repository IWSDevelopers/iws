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

import static net.iaeste.iws.common.utils.StringUtils.toLower;
import static net.iaeste.iws.common.utils.StringUtils.toUpper;

import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.util.Page;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.persistence.CountryDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.views.CountryView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * @param settings       IWS System Settings
     */
    public CountryJpaDao(final EntityManager entityManager, final Settings settings) {
        super(entityManager, settings);
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
    public List<CountryEntity> getCountries(final List<String> countryCodes, final Page page) {
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
    public List<CountryView> getMemberCountries(final List<String> countryCodes, final Page page) {
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
    public List<CountryView> getCountries(final Membership membership, final Page page) {
        final Query query = entityManager.createNamedQuery("view.findCountriesByMembership");
        query.setParameter("type", membership);

        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CountryView> getAllCountries(final Page page) {
        final String jql = "select v from CountryView v";
        final Query query = entityManager.createQuery(extendWithSorting(CountryView.class, page, jql));

        return fetchList(query, page);
    }

    private static String extendWithSorting(final Class<?> entity, final Page page, final String jql) {
        final String sortBy = findSortBy(entity, page);
        final String order = toLower(page.sortOrder().name());

        return jql + " order by " + sortBy + ' ' + order;
    }

    private static String findSortBy(final Class<?> entity, final Page page) {
        final String sortBy;

        if (Objects.equals("CountryView", entity.getSimpleName())) {
            if (page.sortBy() == SortingField.NAME) {
                sortBy = "country_name";
            } else {
                sortBy = "country_created";
            }
        } else {
            sortBy = toLower(page.sortBy().name());
        }

        return sortBy;
    }
}
