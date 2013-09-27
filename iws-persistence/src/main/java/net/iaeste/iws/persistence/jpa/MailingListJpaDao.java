/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.MailingListJpaDao
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

import net.iaeste.iws.persistence.MailingListDao;
import net.iaeste.iws.persistence.entities.mailing_list.MailingListEntity;
import net.iaeste.iws.persistence.entities.mailing_list.MailingListMembershipEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class MailingListJpaDao extends BasicJpaDao implements MailingListDao {

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public MailingListJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailingListEntity findPublicMailingList(final String externalId) {
        final Query query = entityManager.createNamedQuery("mailing_list.findPublicListByExternalId");
        query.setParameter("eid", externalId);

        return findSingleResult(query, "mailinglist");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailingListEntity findPrivateMailingList(final String externalId) {
        final Query query = entityManager.createNamedQuery("mailing_list.findPrivateListByExternalId");
        query.setParameter("eid", externalId);

        return findSingleResult(query, "mailinglist");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailingListMembershipEntity findMailingListSubscription(final Long listId, final String emailAddress) {
        final Query query = entityManager.createNamedQuery("mailing_list.findListSubsciptionByUserAddressAndListId");
        query.setParameter("lid", listId);
        query.setParameter("userAddress", emailAddress);

        return findSingleResult(query, "mailinglist");
    }
}
