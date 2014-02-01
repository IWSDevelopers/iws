/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.daos.MailJpaDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.daos;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Repository("mailDao")
@Transactional("transactionManagerMail")
public class MailJpaDao implements MailDao {

    private EntityManager entityManager;

    @PersistenceContext(unitName = "MailPersistenceUnit")
    public void setMailEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MailJpaDao() {
    }

    public MailJpaDao(final EntityManager mailEntityManager) {
        this.entityManager = mailEntityManager;
    }

    // =========================================================================
    // Implementation of the MailDao functionality
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(final Object entity) {
        entityManager.persist(entity);
    }
}
