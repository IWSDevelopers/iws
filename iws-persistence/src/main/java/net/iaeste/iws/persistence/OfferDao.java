/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.persistence.entities.OfferEntity;
import net.iaeste.iws.persistence.exceptions.PersistenceException;

import java.util.List;

/**
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
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
    OfferEntity findOffer(final Long offerId);

    /**
     * Finds the entity in the database.
     *
     * @param refNo unique offer reference number
     * @return OfferEntity for given id, if no entity exists, then a null value is returned.
     * @throws PersistenceException
     */
    OfferEntity findOffer(final String refNo);

    /**
     * @param offerIds
     * @return
     */
    List<OfferEntity> findOffers(final List<Long> offerIds);

    /**
     * Deletes an offer from database.
     *
     * @param offerId id of the offer to delete
     * @return true if the offer has been deleted, otherwise false
     */
    boolean delete(final Long offerId);

    /**
     * Deletes an offer from database.
     *
     * @param offerIds ids of offers to delete
     * @return number of deleted objects
     */
    Integer delete(final List<Long> offerIds);
}
