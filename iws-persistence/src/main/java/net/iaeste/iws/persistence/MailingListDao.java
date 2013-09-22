/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.MailingListDao
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

import net.iaeste.iws.persistence.entities.mailing_list.MailingListEntity;

import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface MailingListDao extends BasicDao {
    /**
     * Finds a public Mailing List based on the given external id
     *
     * @param externalId The external id of the Group the mailing list belongs to
     * @return Found MailignList or null
     */
    MailingListEntity findPublicMailingList(String externalId);

    /**
     * Finds a private Mailing List based on the given external id
     *
     * @param externalId The external id of the Group the mailing list belongs to
     * @return Found MailignList or null
     */
    MailingListEntity findPrivateMailingList(String externalId);

    /**
     * Clear subscription to public Mailing List based on the given external id
     *
     * @param externalId The external id of the Group the mailing list belongs to
     * @return number of removed subscriptions
     */
    Integer clearPublicSubsription(String externalId);

    /**
     * Clear subscription to private Mailing List based on the given external id
     *
     * @param externalId The external id of the Group the mailing list belongs to
     * @return number of removed subscriptions
     */
    Integer clearPrivateSubsription(String externalId);
}
