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
    public UserEntity findUser(final AuthenticationToken token) {
        final Query query = entityManager.createNamedQuery("session.findUser");
        query.setParameter("key", token.getToken());
        final List<UserEntity> found = query.getResultList();

        // Error handling, unless we find a single active session, then this is
        // an error
        if (found.isEmpty()) {
            throw new AuthenticationException("No AuthenticationToken found.");
        }
        if (found.size() > 1) {
            throw new AuthenticationException("Multiple AuthenticationToken found, please consult the DBA's.");
        }

        return found.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findActiveSession(final AuthenticationToken token) {
        final Query query = entityManager.createNamedQuery("session.findActive");
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
}
