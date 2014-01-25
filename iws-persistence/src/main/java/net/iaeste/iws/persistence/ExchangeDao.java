/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.ExchangeDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence;

import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.persistence.entities.AttachmentEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.exceptions.PersistenceException;
import net.iaeste.iws.persistence.views.DomesticOfferStatisticsView;
import net.iaeste.iws.persistence.views.EmployerView;
import net.iaeste.iws.persistence.views.ForeignOfferStatisticsView;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection BooleanMethodNameMustStartWithQuestion
 */
public interface ExchangeDao extends BasicDao {

    List<ForeignOfferStatisticsView> findForeignOfferStatistics(GroupEntity group, Integer year);
    List<DomesticOfferStatisticsView> findDomesticOfferStatistics(GroupEntity group, Integer year);

    EmployerEntity findEmployer(String externalId);

    EmployerEntity findUniqueEmployer(Authentication authentication, Employer employer);
    EmployerEntity findUniqueEmployer(GroupEntity group, EmployerEntity employer);

    OfferEntity findOfferByOldOfferId(Integer oldOfferId);

    /**
     * Get all offers from the database.
     *
     * @return list of {@code OfferEntity}
     */
    List<OfferEntity> findAllOffers(Authentication authentication);

    /**
     * Finds the entity in the database.
     *
     * @param offerId primary key for offers
     * @return OfferEntity for given id, if no entity exists, then a null value is returned.
     * @throws PersistenceException
     */
    OfferEntity findOffer(Authentication authentication, Long offerId);

    /**
     * Finds the entity in the database.
     *
     * @param externalId The External Id of the Offer
     * @return OfferEntity for given id, if no entity exists, then a null value is returned.
     * @throws PersistenceException
     */
    OfferEntity findOfferByExternalId(Authentication authentication, String externalId);

    /**
     * Finds the entity in the database.
     *
     * @param refNo unique offer reference number
     * @return OfferEntity for given id, if no entity exists, then a null value is returned.
     * @throws PersistenceException
     */
    OfferEntity findOfferByRefNo(Authentication authentication, String refNo);

    /**
     * Finds an Offer in the database which has both the given ExternalId and
     * RefNo. If no offer matching both was found, then a null value is
     * returned, otherwise the found Entity is returned.
     *
     * @param externalId The External Id of the Offer
     * @param refNo      The unique Offer Refence Number
     * @return Found Offer Entity or null
     */
    OfferEntity findOfferByExternalIdAndRefNo(Authentication authentication, String externalId, String refNo);

    /**
     * @param offerIds list of primary keys for offers
     * @return list of {@code OfferEntity}
     */
    List<OfferEntity> findOffers(Authentication authentication, List<Long> offerIds);

    /**
     * @param externalIds list of external IDs for fetching
     * @return list of {@code OfferEntity}
     */
    List<OfferEntity> findOffersByExternalId(Authentication authentication, Set<String> externalIds);

    /**
     * Finds the entity in the database.
     *
     * @param employerName employer name to search for
     * @return list of {@code OfferEntity}
     * @throws PersistenceException
     */
    List<OfferEntity> findOffersByEmployerName(Authentication authentication, String employerName);

    /**
     * Finds the entity in the database.
     *
     * @param employerName employer name to search for
     * @return list of {@code OfferEntity}
     * @throws PersistenceException
     */
    List<EmployerView> findOffersByLikeEmployerName(Authentication authentication, String employerName);

    /**
     * Finds all shared offers.
     *
     * @return list of {@link OfferEntity} which are shared
     */
    List<OfferEntity> findSharedOffers(Authentication authentication, Integer exchangeYear);

    /**
     * Finds information about sharing of the offer
     *
     * @param  offerId id of the offer to get sharing info for
     * @return list of {@link OfferGroupEntity} which are shared
     */
    List<OfferGroupEntity> findInfoForSharedOffer(Long offerId);

    /**
     * Finds information about sharing of the offers
     *
     * @param  offerIds list of ids of the offers to get sharing info for
     * @return list of {@link OfferGroupEntity} which are shared
     */
    List<OfferGroupEntity> findInfoForSharedOffers(List<Long> offerIds);

    /**
     * Finds information about sharing of the offer for specified group
     *
     * @param  offerId id of the offer to get sharing info for
     * @param  groupId id of the group to get sharing info for
     * @return {@link OfferGroupEntity}
     */
    OfferGroupEntity findInfoForSharedOfferAndGroup(Long offerId, Long groupId);

    /**
     * Finds information about sharing of the offer for specified list of groups
     *
     * @param  offerId id of the offer to get sharing info for
     * @param  groupIds list of id of the groups to get sharing info for
     * @return list of {@link OfferGroupEntity} which are shared
     */
    List<OfferGroupEntity> findInfoForSharedOfferAndGroup(Long offerId, List<Long> groupIds);

    /**
     * Finds information about sharing of the offer
     *
     * @param  externalOfferId reference number of the offer to get sharing info for
     * @return list of {@link OfferGroupEntity} which are shared
     */
    List<OfferGroupEntity> findInfoForSharedOffer(String externalOfferId);

    /**
     * Finds information about sharing of the offer
     *
     * @param  offerId reference number of the offer to get sharing info for
     * @return list of {@link OfferGroupEntity} which are shared
     */
    OfferGroupEntity findInfoForSharedOffer(GroupEntity group, String offerId);

    /**
     * Finds information about sharing of the offer only if offer did not expire because of deadline
     *
     * @param  externalOfferId reference number of the offer to get sharing info for
     * @param currentDate current date for comparing with nomination deadlines
     * @return list of {@link OfferGroupEntity} which are shared
     */
    List<OfferGroupEntity> findInfoForUnexpiredSharedOffer(String externalOfferId, Date currentDate);

    /**
     * Unshares the offer from all groups
     *
     * @param offerId id of the offer to get sharing info for
     * @return number of groups from which the offer was unshared
     */
    Integer unshareFromAllGroups(Long offerId);

    /**
     * Unshares the offer from all groups
     *
     * @param  externalId reference number of the offer to get sharing info for
     * @return number of groups from which the offer was unshared
     */
    Integer unshareFromAllGroups(String externalId);

    /**
     * Unshares the offer from groups
     *
     * @param  offerId id of the offer to get sharing info for
     * @param  groups list of groups from which the offer is unshared
     * @return number of groups from which the offer was unshared
     */
    Integer unshareFromGroups(Long offerId, List<Long> groups);

    /**
     * Unshares the offer from groups
     *
     * @param  externalId The External Id for the Offer
     * @param  groups     List of groups from which the offer is unshared
     * @return number of groups from which the offer was unshared
     */
    Integer unshareFromGroups(String externalId, List<Long> groups);

    /**
     * Finds all groups for given external ids
     *
     * @param externalIds list of external ids
     * @return list of {@link GroupEntity}
     */
    List<GroupEntity> findGroupByExternalIds(List<String> externalIds);

    /**
     * Finds group for given external id
     *
     * @param externalId group's external id
     * @return {@link GroupEntity}
     */
    GroupEntity findGroupByExternalId(String externalId);

    List<GroupEntity> findGroupsForSharing(GroupEntity group);

    List<AttachmentEntity> findAttachments(String table, Long id);

    List<OfferEntity> findExpiredOffers(Date currentDate);
}
