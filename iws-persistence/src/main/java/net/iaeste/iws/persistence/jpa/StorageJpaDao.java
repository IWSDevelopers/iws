/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.StorageJpaDao
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


import net.iaeste.iws.persistence.StorageDao;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class StorageJpaDao extends BasicJpaDao implements StorageDao {

    public StorageJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileEntity findFileById(final Long id) {
        final Query query = entityManager.createNamedQuery("file.findById");
        query.setParameter("id", id);

        return findSingleResult(query, "File");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileEntity findFileByUserAndExternalId(final UserEntity user, final String externalId) {
        final Query query = entityManager.createNamedQuery("file.findByUserAndExternalId");
        query.setParameter("uid", user.getId());
        query.setParameter("efid", externalId);

        return findSingleResult(query, "File");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileEntity findFileByUserGroupAndExternalId(final UserEntity user, final GroupEntity group, final String externalId) {
        final Query query = entityManager.createNamedQuery("file.findByUserGroupAndExternalId");
        query.setParameter("uid", user.getId());
        query.setParameter("gid", group.getId());
        query.setParameter("efid", externalId);

        return findSingleResult(query, "file");
    }
}
