/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.OfferDao
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

import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.OfferEntity;
import net.iaeste.iws.persistence.entities.OfferGroupEntity;
import net.iaeste.iws.persistence.exceptions.PersistenceException;

import java.util.List;

/**
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 * @noinspection BooleanMethodNameMustStartWithQuestion
 */
public interface OfferDao extends BasicDao {

    /**
     * Get all offers from the database.
     *
     * @return list of {@code OfferEntity}
     */
    List<OfferEntity> findAll();

    /**
     * Finds the entity in the database.
     *
     * @param offerId primary key for offers
     * @return OfferEntity for given id, if no entity exists, then a null value is returned.
     * @throws PersistenceException
     */
    OfferEntity findOffer(Long offerId);

    /**
     * Finds the entity in the database.
     *
     * @param refNo unique offer reference number
     * @return OfferEntity for given id, if no entity exists, then a null value is returned.
     * @throws PersistenceException
     */
    OfferEntity findOffer(String refNo);

    /**
     * Finds an Offer in the database which has both the given ExternalId and
     * RefNo. If no offer matching both was found, then a null value is
     * returned, otherwise the found Entity is returned.
     *
     * @param externalId The External Id of the Offer
     * @param refNo      The unique Offer Refence Number
     * @return Found Offer Entity or null
     */
    OfferEntity findOffer(String externalId, String refNo);

    /**
     * @param offerIds list of primary keys for offers
     * @return list of {@code }OfferEntity}
     */
    List<OfferEntity> findOffers(List<Long> offerIds);

    /**
     * Finds the entity in the database.
     *
     * @param employerName employer name to search for
     * @param ownerId ownerId of a group owning offers
     * @return list of {@code }OfferEntity}
     * @throws PersistenceException
     */
    List<OfferEntity> findOffersByEmployerName(String employerName, Long ownerId);

    /**
     * Finds the entity in the database.
     *
     * @param employerName employer name to search for
     * @param ownerId ownerId of a group owning offers
     * @return list of {@code }OfferEntity}
     * @throws PersistenceException
     */
    List<OfferEntity> findOffersByLikeEmployerName(String employerName, Long ownerId);

    /**
     * Finds all offers which are owned by a group of given Id.
     *
     * @param   ownerId ownerId of a group owning offers
     * @return  list of {@link OfferEntity} which are owned by GroupEntity with {@code id = ownerId}
     */
    List<OfferEntity> findOffersByOwnerId(Long ownerId);

    /**
     * Finds all shared offers.
     *
     * @return list of {@link OfferEntity} which are shared
     */
    List<OfferEntity> findSharedOffers();

    /**
     * Finds information about sharing of the offer
     *
     * @param  offerId id of the offer to get sharing info for
     * @return list of {@link OfferGroupEntity} which are shared
     */
    List<OfferGroupEntity> findGroupsForSharedOffer(Long offerId);

    /**
     * Finds information about sharing of the offer
     *
     * @param  refNo reference number of the offer to get sharing info for
     * @return list of {@link OfferGroupEntity} which are shared
     */
    List<OfferGroupEntity> findGroupsForSharedOffer(String refNo);

    /**
     * Unshares the offer from all groups
     *
     * @param offerId id of the offer to get sharing info for
     * @return number of groups from which the offer was unshared
     */
    Integer unshareFromAllGroups(Long offerId);

    /**
     * Unshares the offer from all groups
     * TODO not working, see OfferGroupEntity
     *
     * @param  refNo reference number of the offer to get sharing info for
     * @return number of groups from which the offer was unshared
     */
    Integer unshareFromAllGroups(String refNo);

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
     * TODO not working, see OfferGroupEntity
     *
     * @param  refNo reference number of the offer to get sharing info for
     * @param  groups list of groups from which the offer is unshared
     * @return number of groups from which the offer was unshared
     */
    Integer unshareFromGroups(String refNo, List<Long> groups);

    /**
     * Deletes an offer from database.
     *
     * @param offerId id of the offer to delete
     */
    void delete(Long offerId);

    /**
     * Deletes an offer from database.
     *
     * @param offerIds ids of offers to delete
     * @return number of deleted objects
     */
    Integer delete(List<Long> offerIds);

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
}
