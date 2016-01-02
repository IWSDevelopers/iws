/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-leargas) - net.iaeste.iws.leargas.State
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.leargas;

/**
 * For keeping track of how many Offers were Created, Updated or Deleted, we're
 * using this simple State Object.
 *
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public final class State {

    private int createdOffers = 0;
    private int updatedOffers = 0;
    private int deletedOffers = 0;

    public void incCreatedOffers() {
        createdOffers++;
    }

    public void incUpdatedOffers() {
        updatedOffers++;
    }

    public void incDeletedOffers() {
        deletedOffers++;
    }

    public String toString() {
        return "created " + createdOffers +
               ", updated " + updatedOffers +
               ", deleted " + deletedOffers +
               " of " + createdOffers + updatedOffers + deletedOffers +
               " Offers";
    }
}
