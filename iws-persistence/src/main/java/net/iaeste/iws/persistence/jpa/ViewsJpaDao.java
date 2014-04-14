/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.ViewsJpaDao
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
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.views.AttachedFileView;
import net.iaeste.iws.persistence.views.EmployerView;
import net.iaeste.iws.persistence.views.OfferSharedToGroupView;
import net.iaeste.iws.persistence.views.OfferView;
import net.iaeste.iws.persistence.views.SharedOfferView;
import net.iaeste.iws.persistence.views.StudentView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ViewsJpaDao extends BasicJpaDao implements ViewsDao {

    public ViewsJpaDao(final EntityManager entityManager) {
        super(entityManager);
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
        final EmployerView result;

        if (found.isEmpty()) {
            result = null;
        } else if (found.size() > 1) {
            throw new IdentificationException("Found multiple Employer Records.");
        } else {
            result = found.get(0);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmployerView> findEmployers(final Long groupId, final Paginatable page) {
        final Query query = entityManager.createNamedQuery("view.findEmployerByGroup");
        query.setParameter("gid", groupId);

        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmployerView> findEmployers(final Long groupId, final Paginatable page, final String partialName) {
        final Query query = entityManager.createNamedQuery("view.findEmployerByGroupAndPartialName");
        query.setParameter("gid", groupId);
        query.setParameter("name", '%' + partialName.toLowerCase(IWSConstants.DEFAULT_LOCALE) + '%');

        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferView> findDomesticOffers(final Authentication authentication, final Integer exchangeYear, final Paginatable page) {
        final Query query = entityManager.createNamedQuery("view.findDomesticOffersByGroupAndYear");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("year", exchangeYear);

        // Todo 2014-01-23 by Kim; When Trac Task #719 is finished, then we'll use the pagination
        //return fetchList(query, page);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SharedOfferView> findSharedOffers(Authentication authentication, Integer exchangeYear, final Paginatable page) {
        final Query query = entityManager.createNamedQuery("view.findSharedOffersByGroupAndYear");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("year", exchangeYear);

        // Todo 2014-01-23 by Kim; When Trac Task #719 is finished, then we'll use the pagination
        //return fetchList(query, page);
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
    public List<StudentView> findStudentsForMemberGroup(final Long groupId, final Paginatable page) {
        final Query query = entityManager.createNamedQuery("view.findStudentsForMemberGroup");
        query.setParameter("parentId", groupId);

        return fetchList(query, page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AttachedFileView> findAttachments(final String table, final Long id) {
        final Query query = entityManager.createNamedQuery("view.findAttachments");
        query.setParameter("table", table);
        query.setParameter("recordId", id);

        return query.getResultList();
    }
}
