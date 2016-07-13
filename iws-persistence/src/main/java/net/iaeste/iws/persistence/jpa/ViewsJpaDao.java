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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.Page;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.views.DomesticOfferStatisticsView;
import net.iaeste.iws.persistence.views.EmployerView;
import net.iaeste.iws.persistence.views.ForeignOfferStatisticsView;
import net.iaeste.iws.persistence.views.OfferSharedToGroupView;
import net.iaeste.iws.persistence.views.OfferView;
import net.iaeste.iws.persistence.views.SharedOfferView;
import net.iaeste.iws.persistence.views.StudentView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ViewsJpaDao extends BasicJpaDao implements ViewsDao {

    public ViewsJpaDao(final EntityManager entityManager, final Settings settings) {
        super(entityManager, settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignOfferStatisticsView> findForeignOfferStatistics(final GroupEntity group, final Integer year) {
        final Query query = entityManager.createNamedQuery("view.findStatisticsForForeignOffersForGroupAndYears");
        query.setParameter("gid", group.getId());
        query.setParameter("years", prepareExchangeYears(year, false));

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DomesticOfferStatisticsView> findDomesticOfferStatistics(final GroupEntity group, final Integer year) {
        final Query query = entityManager.createNamedQuery("view.findStatisticsForDomesticOffersForGroupAndYears");
        query.setParameter("gid", group.getId());
        query.setParameter("years", prepareExchangeYears(year, false));

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerView findEmployer(final Long groupId, final String externalId) {
        final Query query = entityManager.createNamedQuery("view.findEmployerByGroupAndId");
        query.setParameter("gid", groupId);
        query.setParameter("id", externalId);

        final List<EmployerView> found = query.getResultList();
        EmployerView result = null;

        if (found.size() == 1) {
            result = found.get(0);
        } else if (found.size() > 1) {
            throw new IdentificationException("Found multiple Employer Records.");
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmployerView> findEmployers(final Long groupId, final Page page) {
        final Query query = entityManager.createNamedQuery("view.findEmployerByGroup");
        query.setParameter("gid", groupId);

        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmployerView> findEmployers(final Long groupId, final Page page, final String partialName) {
        final Query query = entityManager.createNamedQuery("view.findEmployerByGroupAndPartialName");
        query.setParameter("gid", groupId);
        query.setParameter("name", '%' + partialName.toLowerCase(IWSConstants.DEFAULT_LOCALE) + '%');

        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferView> findDomesticOffers(final Authentication authentication, final Integer exchangeYear, final Set<OfferState> states, final Boolean retrieveCurrentAndNextExchangeYear, final Page page) {
        final Query query = entityManager.createNamedQuery("view.findDomesticOffersByGroupAndYears");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("years", prepareExchangeYears(exchangeYear, retrieveCurrentAndNextExchangeYear));
        query.setParameter("states", states);

        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferView> findDomesticOffersByOfferIds(final Authentication authentication, final Integer exchangeYear, final List<String> offerIds) {
        final Query query = entityManager.createNamedQuery("view.findDomesticOffersByGroupAndYearAndOfferExternalId");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("year", exchangeYear);
        query.setParameter("eids", offerIds);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SharedOfferView> findSharedOffers(final Authentication authentication, final Integer exchangeYear, final Set<OfferState> states, final Boolean retrieveCurrentAndNextExchangeYear, final Page page) {
        final Query query = entityManager.createNamedQuery("view.findSharedOffersByGroupAndYears");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("years", prepareExchangeYears(exchangeYear, retrieveCurrentAndNextExchangeYear));
        query.setParameter("states", states);

        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SharedOfferView> findSharedOffersByOfferIds(final Authentication authentication, final Integer exchangeYear, final List<String> offerIds) {
        final Query query = entityManager.createNamedQuery("view.findSharedOffersByGroupAndYearAndOfferExternalId");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("year", exchangeYear);
        query.setParameter("eids", offerIds);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferSharedToGroupView> findSharedToGroup(final Long parentId, final Integer exchangeYear, final List<String> externalOfferIds) {
        final Query query = entityManager.createNamedQuery("view.findSharedToForOwnerYearAndOffers");
        query.setParameter("pid", parentId);
        query.setParameter("year", exchangeYear);
        query.setParameter("externalOfferIds", externalOfferIds);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentView> findStudentsForMemberGroup(final Long groupId, final Page page) {
        final Query query = entityManager.createNamedQuery("view.findStudentsForMemberGroup");
        query.setParameter("parentId", groupId);

        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> findOfferRefNoForEmployers(final List<Employer> employers) {
        final List<String> externalEmployerIds = new ArrayList<>(employers.size());
        for (final Employer employer : employers) {
            externalEmployerIds.add(employer.getEmployerId());
        }

        final String jql =
                "select o.refNo " +
                "from OfferEntity o " +
                "where o.employer.externalId in (:eoids)";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("eoids", externalEmployerIds);

        return query.getResultList();
    }

    // =========================================================================
    // Internal methods
    // =========================================================================

    /**
     * As part of Trac Ticket #1022, it was decided to expand on the current
     * fetching logic for Offer information, so retrieval of Offers will always
     * include information from the ongoing Calendar Year. Thus trying to fix a
     * Usability problem for Users, when the Exchange Year changes in
     * September.<br />
     *   Note, following a User complaint, the list of years is made optional,
     * and can be controlled via a flag. If the flag is set (default false),
     * then information from both years is retrieved, otherwise only for the
     * given year.<br />
     *   Basically, what will happen is that if the provided Exchange Year is
     * either the current Calendar Year or the next Exchange Year, then both
     * will be returned, of an earlier year was given, then only that will be
     * returned.
     *
     * @param exchangeYear                       Exchange Year to base list on
     * @param retrieveCurrentAndNextExchangeYear Combine if true, otherwise not
     * @return List with either given Exchange Year or current + next
     */
    private static List<Integer> prepareExchangeYears(final Integer exchangeYear, final boolean retrieveCurrentAndNextExchangeYear) {
        final int currentYear = new Date().getCurrentYear();
        final List<Integer> years = new ArrayList<>(2);

        if (retrieveCurrentAndNextExchangeYear && (exchangeYear >= currentYear)) {
            years.add(currentYear);
            years.add(currentYear + 1);
        } else {
            years.add(exchangeYear);
        }

        return years;
    }
}
