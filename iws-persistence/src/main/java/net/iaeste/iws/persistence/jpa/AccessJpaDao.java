/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.AccessJpaDao
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

import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.common.exceptions.AuthenticationException;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.views.UserPermissionView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class AccessJpaDao implements AccessDao {

    private final EntityManager entityManager;

    /**
     * Default Constructor.
     *
     * @param entityManager  Entity Manager instance to use
     */
    public AccessJpaDao(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(final Object entity) {
        entityManager.persist(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findActiveSession(final AuthenticationToken token) {
        final Query query = entityManager.createNamedQuery("findActiveSession");
        query.setParameter("key", token.getToken());
        final List<SessionEntity> entities = query.getResultList();
        if (entities.isEmpty()) {
            throw new AuthenticationException("No AuthenticationToken found.");
        }
        if (entities.size() > 1) {
            throw new AuthenticationException("Multiple AuthenticationToken found, please consult the DBA's.");
        }

        return entities.get(0).getUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserPermissionView> findPermissions(final Integer userId) {
        final Query query = entityManager.createNamedQuery("findAllUserPermissions");
        query.setParameter("uid", userId);
        final List<UserPermissionView> permissions = query.getResultList();

        return permissions;
    }
}
