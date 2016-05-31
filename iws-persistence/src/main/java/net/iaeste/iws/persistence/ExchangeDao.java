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
package net.iaeste.iws.persistence;

import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.PublishingGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.exceptions.PersistenceException;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface ExchangeDao extends BasicDao {

    EmployerEntity findEmployer(Authentication authentication, String externalId);

    /**
     * Finds a Unique Employer in the IntraWeb. The Uniqueness of an Employer is
     * characterized by the employer name, department and workplace as well as
     * the owning Group.<br />
     *   Note; There is a bug in the current implementation which prevents the
     * department from being used in the unique identification of the Employer.
     *
     * @param authentication User Authentication information with the Group Id
     * @param employer       The Employer to find
     * @return Found Employer Entity or null
     * @throws IdentificationException if more Employer Entities were found
     */
    EmployerEntity findUniqueEmployer(Authentication authentication, Employer employer);

    /**
     * Get all offers from the database.
     *
     * @return list of {@code OfferEntity}
     */
    List<OfferEntity> findAllOffers(Authentication authentication);

    /**
     * Finds the entity in the database.
     *
     * @param externalId The External Id of the Offer
     * @return OfferEntity for given id, if no entity exists, then a null value is returned.
     * @throws PersistenceException
     */
    OfferEntity findOfferByOwnerAndExternalId(Authentication authentication, String externalId);

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
     * @param refNo      The unique Offer Reference Number
     * @return Found Offer Entity or null
     */
    OfferEntity findOfferByExternalIdAndRefNo(Authentication authentication, String externalId, String refNo);

    /**
     * Finds information about sharing of the offer
     *
     * @param  offerId id of the offer to get sharing info for
     * @return list of {@link OfferGroupEntity} which are shared
     */
    List<OfferGroupEntity> findInfoForSharedOffer(Long offerId);

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
     * Finds information about sharing of the offer
     *
     * @param  group group the offer is shared to
     * @param  offerIds reference numbers of the offers to get sharing info for
     * @return list of {@link OfferGroupEntity} which are shared
     */
    List<OfferGroupEntity> findInfoForSharedOffers(GroupEntity group, Set<String> offerIds);

    /**
     * Unshare's the offer from all groups
     *
     * @param offerId id of the offer to get sharing info for
     * @return number of groups from which the offer was unshared
     */
    Integer unshareFromAllGroups(Long offerId);

    /**
     * Unshare's the offer from groups
     *
     * @param  offerId id of the offer to get sharing info for
     * @param  groups list of groups from which the offer is unshared
     * @return number of groups from which the offer was unshared
     */
    Integer unshareFromGroups(Long offerId, List<Long> groups);

    /**
     * Finds all groups for given external ids
     *
     * @param externalIds list of external ids
     * @return list of {@link GroupEntity}
     */
    List<GroupEntity> findGroupByExternalIds(List<String> externalIds);

    List<GroupEntity> findGroupsForSharing(GroupEntity group);

    /**
     * Finds a list of Offers which has expired, meaning that the nomination's
     * deadline has been exceeded. Please see Trac ticket #1020 for more
     * information.
     *
     * @param currentDate Current Date
     * @return List of Expired Offers
     */
    List<OfferEntity> findExpiredOffers(Date currentDate);

    /**
     * Sets given state for given list of offer IDs
     *
     * @param ids List of offer IDs
     * @param state New offer state
     */
    void updateOfferState(List<Long> ids, OfferState state);

    /**
     * Sets the hidden flag in OfferGroup for given list of OfferGroup IDs
     *
     * @param ids List of OfferGroups IDs
     */
    void hideOfferGroups(List<Long> ids);

    PublishingGroupEntity getSharingListByExternalIdAndOwnerId(String externalId, Long groupId);

    List<PublishingGroupEntity> getSharingListForOwner(Long id);

    List<GroupEntity> findNationalGroupsById(List<String> groupIds);

    List<GroupEntity> findNationalGroupsByIdForSharing(GroupEntity owner, List<String> groupIds);

    OfferEntity findOfferByOwnerAndIdentifier(Authentication authentication, String identifier);
}
