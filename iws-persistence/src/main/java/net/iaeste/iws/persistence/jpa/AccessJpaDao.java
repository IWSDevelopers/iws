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

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.exceptions.AuthenticationException;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.IWSEntity;
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
 * @noinspection ReturnOfNull
 */
public class AccessJpaDao extends BasicJpaDao implements AccessDao {

    /**
     * Default Constructor.
     *
     * @param entityManager  Entity Manager instance to use
     */
    public AccessJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findUserByCredentials(final String username, final String passwordHashcode) {
        final Query query = entityManager.createNamedQuery("user.loginCredentials");
        query.setParameter("username", username);
        query.setParameter("password", passwordHashcode);
        final List<UserEntity> result = query.getResultList();

        if (result.size() != 1) {
            throw new IWSException(IWSErrors.AUTHORIZATION_ERROR, "No account for the user '" + username + "' was found.");
        }

        return result.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionEntity findActiveSession(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("session.findByUser");
        query.setParameter("id", user.getId());
        final List<SessionEntity> found = query.getResultList();

        if (found.size() > 1) {
            throw new AuthenticationException("Multiple Active sessions exists.");
        }

        return found.isEmpty() ? null : found.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionEntity findActiveSession(final AuthenticationToken token) {
        final Query query = entityManager.createNamedQuery("session.findByToken");
        query.setParameter("key", token.getToken());

        return findUniqueResult(query, "AuthenticationToken");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer deprecateSession(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("session.deprecate");
        query.setParameter("id", user.getId());

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserPermissionView> findPermissions(final Integer userId) {
        final Query query = entityManager.createNamedQuery("view.findAllUserPermissions");
        query.setParameter("uid", userId);
        final List<UserPermissionView> permissions = query.getResultList();

        return permissions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findGroup(final AuthenticationToken token, final Permission permission) {
        return null;
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private <T extends IWSEntity> T findUniqueResult(final Query query, final String name) {
        final List<T> found = query.getResultList();

        if (found.isEmpty()) {
            throw new AuthenticationException("No " + name + " was found.");
        }

        if (found.size() > 1) {
            throw new AuthenticationException("Multiple " + name + "s were found.");
        }

        return found.get(0);
    }
}
