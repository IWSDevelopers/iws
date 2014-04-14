/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.ExchangeJpaDao
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
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.AttachmentEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.views.DomesticOfferStatisticsView;
import net.iaeste.iws.persistence.views.EmployerView;
import net.iaeste.iws.persistence.views.ForeignOfferStatisticsView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ExchangeJpaDao extends BasicJpaDao implements ExchangeDao {

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public ExchangeJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignOfferStatisticsView> findForeignOfferStatistics(final GroupEntity group, final Integer year) {
        final Query query = entityManager.createNamedQuery("view.findStatisticsForForeignOffersForGroupAndYear");
        query.setParameter("gid", group.getId());
        query.setParameter("year", year);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DomesticOfferStatisticsView> findDomesticOfferStatistics(final GroupEntity group, final Integer year) {
        final Query query = entityManager.createNamedQuery("view.findStatisticsForDomesticOffersForGroupAndYear");
        query.setParameter("gid", group.getId());
        query.setParameter("year", year);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerEntity findEmployer(final String externalId) {
        final Query query = entityManager.createNamedQuery("employer.findByExternalId");
        query.setParameter("eid", externalId);

        return findUniqueResult(query, "Employer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerEntity findUniqueEmployer(final Authentication authentication, final Employer employer) {
        final Query query = entityManager.createNamedQuery("employer.findEmployerByValues");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("name", employer.getName());
        // Note by Kim; If someone can explain why PostgreSQL throws an
        // exception with function 'lower(bytea) unknown', when we're trying to
        // make a lowercase comparison of the Department field, then let me know
        //query.setParameter("department", employer.getDepartment());
        query.setParameter("workingPlace", employer.getWorkingPlace());

        return findSingleResult(query, "Employer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerEntity findUniqueEmployer(final GroupEntity group, final EmployerEntity employer) {
        final Query query = entityManager.createNamedQuery("employer.findEmployerByValues");
        query.setParameter("gid", group.getId());
        query.setParameter("name", employer.getName());
        // Note by Kim; If someone can explain why PostgreSQL throws an
        // exception with function 'lower(bytea) unknown', when we're trying to
        // make a lowercase comparison of the Department field, then let me know
        //query.setParameter("department", employer.getDepartment());
        query.setParameter("workingPlace", employer.getWorkingPlace());

        return findSingleResult(query, "Employer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferEntity findOfferByOldOfferId(final Integer oldOfferId) {
        final Query query = entityManager.createNamedQuery("offer.findByOldOfferId");
        query.setParameter("ooid", oldOfferId);

        return findSingleResult(query, "Offer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findAllOffers(final Authentication authentication) {
        final Query query = entityManager.createNamedQuery("offer.findAllForGroup");
        query.setParameter("gid", authentication.getGroup().getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferEntity findOffer(final Authentication authentication, final Long offerId) {
        OfferEntity entity = null;

        if (offerId != null) {
            final Query query = entityManager.createNamedQuery("offer.findByGroupAndId");
            query.setParameter("gid", authentication.getGroup().getId());
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
    public OfferEntity findOfferByExternalId(final Authentication authentication, final String externalId) {
        final Query query = entityManager.createNamedQuery("offer.findByGroupAndExternalId");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("eoid", externalId);

        return findSingleResult(query, "Offer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferEntity findOfferByRefNo(final Authentication authentication, final String refNo) {
        OfferEntity entity = null;

        if (refNo != null) {
            final Query query = entityManager.createNamedQuery("offer.findByGroupAndRefNo");
            query.setParameter("gid", authentication.getGroup().getId());
            query.setParameter("refNo", refNo);

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
    public OfferEntity findOfferByExternalIdAndRefNo(final Authentication authentication, final String externalId, final String refNo) {
        final Query query = entityManager.createNamedQuery("offer.findByGroupAndExternalIdAndRefNo");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("eoid", externalId);
        query.setParameter("refno", refNo);
        final List<OfferEntity> found = query.getResultList();

        final OfferEntity entity;
        if (found.isEmpty()) {
            entity = null;
        } else {
            entity = found.get(0);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findOffers(final Authentication authentication, final List<Long> offerIds) {
        final Query query = entityManager.createNamedQuery("offer.findByGroupAndIds");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("ids", offerIds);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findOffersByExternalId(final Authentication authentication, final Set<String> externalIds) {
        final Query query = entityManager.createNamedQuery("offer.findByGroupAndExternalIds");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("eoids", externalIds);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findOffersByEmployerName(final Authentication authentication, final String employerName) {
        final Query query = entityManager.createNamedQuery("offer.findByGroupAndEmployerName");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("employer", employerName);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmployerView> findOffersByLikeEmployerName(final Authentication authentication, final String employerName) {
        final Query query = entityManager.createNamedQuery("offer.findByGroupAndLikeEmployerName");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("employer", '%' + employerName.toLowerCase(IWSConstants.DEFAULT_LOCALE) + '%');

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findSharedOffers(final Authentication authentication, final Integer exchangeYear) {
        final Query query = entityManager.createNamedQuery("offerGroup.findSharedToGroup");
        query.setParameter("gid", authentication.getGroup().getId());
        query.setParameter("year", exchangeYear);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferGroupEntity> findInfoForSharedOffer(final Long offerId) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByOffer");
        query.setParameter("oid", offerId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferGroupEntity findInfoForSharedOfferAndGroup(final Long offerId, final Long groupId) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByOfferAndGroup");
        query.setParameter("oid", offerId);
        query.setParameter("gid", groupId);

        return findSingleResult(query, "OfferGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferGroupEntity> findInfoForSharedOfferAndGroup(final Long offerId, final List<Long> groupIds) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByOfferAndGroupList");
        query.setParameter("oid", offerId);
        query.setParameter("gids", groupIds);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferGroupEntity> findInfoForSharedOffer(final String externalOfferId) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByExternalOfferId");
        query.setParameter("eoid", externalOfferId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferGroupEntity findInfoForSharedOffer(final GroupEntity group, final String offerId) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByGroupAndExternalId");
        query.setParameter("eoid", offerId);
        query.setParameter("gid", group.getId());

        return findSingleResult(query, "OfferGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferGroupEntity> findInfoForSharedOffers(final GroupEntity group, final Set<String> offerIds) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByGroupAndExternalIds");
        query.setParameter("eoids", offerIds);
        query.setParameter("gid", group.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferGroupEntity> findInfoForUnexpiredSharedOffer(final String externalOfferId, final Date currentDate) {
        final Query query = entityManager.createNamedQuery("offerGroup.findUnexpiredByExternalOfferId");
        query.setParameter("eoid", externalOfferId);
        query.setParameter("date", currentDate);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer unshareFromAllGroups(final Long offerId) {
        final Query query = entityManager.createNamedQuery("offerGroup.deleteByOffer");
        query.setParameter("oid", offerId);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer unshareFromAllGroups(final String externalId) {
        final Query query = entityManager.createNamedQuery("offerGroup.deleteByExternalOfferId");
        query.setParameter("eoid", externalId);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer unshareFromGroups(final Long offerId, final List<Long> groups) {
        //TODO passing empty list fails, correct?
        final Query query = entityManager.createNamedQuery("offerGroup.deleteByOfferIdAndGroups");
        query.setParameter("oid", offerId);
        query.setParameter("gids", groups);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer unshareFromGroups(final String externalId, final List<Long> groups) {
        //TODO passing empty list fails, correct?
        final Query query = entityManager.createNamedQuery("offerGroup.deleteByOfferExternalIdAndGroups");
        query.setParameter("eoid", externalId);
        query.setParameter("gids", groups);

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findGroupByExternalIds(final List<String> externalIds) {
        final Query query = entityManager.createNamedQuery("group.findByExternalGroupIds");
        query.setParameter("egids", externalIds);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findGroupByExternalId(final String externalId) {
        final List<String> externalIds = new ArrayList<>(1);
        externalIds.add(externalId);
        final List<GroupEntity> groups = findGroupByExternalIds(externalIds);

        GroupEntity group = null;

        if (groups.size() == 1) {
            group = groups.get(0);
        }

        return group;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findGroupsForSharing(final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("group.findGroupsForSharing");
        query.setParameter("gid", group.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AttachmentEntity> findAttachments(final String table, final Long id) {
        final Query query = entityManager.createNamedQuery("attachments.findForRecord");
        query.setParameter("table", table);
        query.setParameter("recordid", id);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferEntity> findExpiredOffers(final Date currentDate, final Integer exchangeYear) {
        final Query query = entityManager.createNamedQuery("offer.findExpired");
        query.setParameter("date", currentDate);
        query.setParameter("exchangeYear", exchangeYear);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOfferState(final List<Long> ids, final OfferState state) {
        final Query query = entityManager.createNamedQuery("offer.updateStateByIds");

        //error when passing empty list
        //QuerySyntaxException: unexpected end of subtree [delete from net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity og where og.id in ()]
        if (ids.isEmpty()) {
            ids.add(-1L);
        }

        query.setParameter("ids", ids);
        query.setParameter("status", state);

        query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOfferGroupState(final List<Long> ids, final OfferState state) {
        final Query query = entityManager.createNamedQuery("offerGroup.updateStateByIds");

        //error when passing empty list
        //QuerySyntaxException: unexpected end of subtree [delete from net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity og where og.id in ()]
        if (ids.isEmpty()) {
            ids.add(-1L);
        }

        query.setParameter("ids", ids);
        query.setParameter("status", state);

        query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOfferGroup(final List<Long> ids) {
        final Query query = entityManager.createNamedQuery("offerGroup.deleteByIds");

        //error when passing empty list
        //QuerySyntaxException: unexpected end of subtree [delete from net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity og where og.id in ()]
        if (ids.isEmpty()) {
            ids.add(-1L);
        }

        query.setParameter("ids", ids);

        query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfferGroupEntity> findInfoForSharedOffers(/*final*/ List<Long> offerIds) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByOffers");
        //TODO @Kim: is it possible to pass empty list? It was a bug but I don't know which version we are using now
        //TODO see https://hibernate.atlassian.net/browse/HHH-6876
        //TODO workaround to "unexpected end of subtree" exception when empty list is passed
        if (offerIds.isEmpty()) {
            offerIds = new ArrayList<>(1);
            offerIds.add(-1L);
        }

        query.setParameter("oids", offerIds);

        return query.getResultList();
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void hideOfferGroups(List<Long> ids) {
        final Query query = entityManager.createNamedQuery("offerGroup.hideByIds");
        //TODO @Kim: is it possible to pass empty list? It was a bug but I don't know which version we are using now
        //TODO see https://hibernate.atlassian.net/browse/HHH-6876
        //TODO workaround to "unexpected end of subtree" exception when empty list is passed
        if (ids.isEmpty()) {
            ids = new ArrayList<>(1);
            ids.add(-1L);
        }

        query.setParameter("ids", ids);

        query.executeUpdate();
    }
}
