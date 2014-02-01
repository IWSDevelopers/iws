/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.persistence.entities.mailing_list.MailingAliasEntity;
import net.iaeste.iws.persistence.entities.mailing_list.MailingListEntity;
import net.iaeste.iws.persistence.entities.mailing_list.MailingListMembershipEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUserSubscriptionEmail(final String newEmailAddress, final String oldEmailAddress) {
        final Query query = entityManager.createNamedQuery("mailing_list.updateUserSubscriptionEmail");
        query.setParameter("newUserAddress", newEmailAddress);
        query.setParameter("oldUserAddress", oldEmailAddress);

        query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailingAliasEntity findMailingAliasByUsername(final String username) {
        final Query query = entityManager.createNamedQuery("mailing_list.findAliasByUsername");
        query.setParameter("username", username);

        return findSingleResult(query, "MailingAlias");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailingAliasEntity findMailingAliasByAlias(final String userAlias) {
        final Query query = entityManager.createNamedQuery("mailing_list.findAliasByUserAlias");
        query.setParameter("alias", userAlias);

        return findSingleResult(query, "MailingAlias");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUsernameInMailingAlias(final String newEmailAddress, final String oldEmailAddress) {
        final Query query = entityManager.createNamedQuery("mailing_list.updateUsernameinMailingAlias");
        query.setParameter("newUserAddress", newEmailAddress);
        query.setParameter("oldUserAddress", oldEmailAddress);

        query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void wipeOutMailingListMembers(final Long id) {
        final Query query = entityManager.createNamedQuery("mailing_list.deleteAllSubscriptions");
        query.setParameter("id", id);

        query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailingListEntity findNcsList(final String ncsList) {
        final Query query = entityManager.createNamedQuery("mailing_list.findByMailingListAddress");
        query.setParameter("address", ncsList);

        return findSingleResult(query, "NCs List");
    }
}
