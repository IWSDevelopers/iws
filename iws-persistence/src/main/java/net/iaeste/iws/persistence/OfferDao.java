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
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface OfferDao {

    /**
     * Persist the given {@code OfferEntity} into the database.
     *
     * @param offer {@code OfferEntity} instance to persist
     */
    void persist(OfferEntity offer);

    /**
     * Get all offers from the database.
     * @return list of {@code OfferEntity}
     */
    List<OfferEntity> findAll();

    /**
     * Finds the entity in the database.
     *
     * @param offerId
     * @return OfferEntity for given id, if no entity exists, then a null value is returned.
     * @throws PersistenceException
     */
    OfferEntity findOffer(final Integer offerId);

    /**
     * @param offerIds
     * @return
     */
    List<OfferEntity> findOffers(final List<Integer> offerIds);
}
