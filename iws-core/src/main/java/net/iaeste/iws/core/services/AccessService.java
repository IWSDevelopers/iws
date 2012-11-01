/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.AccessService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Authorization;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.SessionResponse;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.common.exceptions.AuthorizationException;
import net.iaeste.iws.common.utils.HashcodeGenerator;
import net.iaeste.iws.core.exceptions.SessionExistsException;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.views.UserPermissionView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection ObjectAllocationInLoop
 */
public final class AccessService {

    private final AccessDao dao;

    /**
     * Default Constructor. This Service only requires an AccessDao instance,
     * which is used for all database operations.
     *
     * @param dao AccessDAO instance
     */
    public AccessService(final AccessDao dao) {
        this.dao = dao;
    }

    /**
     * Generates a new Session for the User, using the provided (verified)
     * credentials. Returns a new Token, if no session exists. If an active
     * session exists, then the method will thrown an SessionExists Exception.
     *
     * @param request  Request Object with User Credentials
     * @return New AuthenticationToken
     * @throws SessionExistsException if an Active Session already exists
     */
    public AuthenticationToken generateSession(final AuthenticationRequest request) {
        final UserEntity user = findUserFromCredentials(request);
        final SessionEntity activeSession = dao.findActiveSession(user);

        if (activeSession == null) {
            return new AuthenticationToken(generateAndPersistSessionKey(user));
        } else {
            throw new SessionExistsException("An Active Session for user " + user.getFirstname() + ' ' + user.getLastname() + " already exists.");
        }
    }

    public SessionResponse verifySession(final AuthenticationToken token) {
        final SessionEntity entity = dao.findActiveSession(token);
        final byte[] data = entity.getSessionData();
        final DateTime created = new DateTime(entity.getCreated());
        final DateTime modified = new DateTime(entity.getModified());

        final SessionResponse response = new SessionResponse();
        response.setSessionData(data);
        response.setCreated(created);
        response.setModified(modified);

        return response;
    }

    public Fallible saveSessionData(final AuthenticationToken token, final SessionDataRequest request) {
        final SessionEntity entity = dao.findActiveSession(token);
        entity.setSessionData(request.getSessionData());
        entity.setModified(new Date());
        dao.persist(entity);

        return new SessionResponse();
    }

    public void deprecateSession(final AuthenticationToken token) {
        final SessionEntity session = dao.findActiveSession(token);
        final Integer updated = dao.deprecateSession(session.getUser());

        // If zero records were updated, then the session was already
        // deprecated. If one record was updated, then the currently
        // active session has been successfully deprecated.
        if (updated > 1) {
            throw new AuthorizationException("Multiple Active Sessions was found.");
        }
    }

    public List<Authorization> findPermissions(final AuthenticationToken token) {
        final List<UserPermissionView> permissions = dao.findPermissions(1);
        final List<Authorization> result = new ArrayList<>(permissions.size());

        for (final UserPermissionView view : permissions) {
            final String permission = view.getPermission();
            final String groupType = view.getGroupType();
            final Authorization authorization = new Authorization(permission, groupType);

            result.add(authorization);
        }

        return result;
    }

    private String generateAndPersistSessionKey(final UserEntity user) {
        // Generate new Hashcode from the User Credentials, and some other entropy
        final String entropy = UUID.randomUUID().toString() + user.getPassword();
        final String sessionKey = HashcodeGenerator.generateSHA256(entropy);

        // Generate the new Session, and persist it
        final SessionEntity entity = new SessionEntity(user, sessionKey);
        dao.persist(entity);

        // Now, let's return the newly generated SessionKey
        return sessionKey;
    }

    private UserEntity findUserFromCredentials(final AuthenticationRequest request) {
        // First, let's read the Password in clear-text (lower cased), and
        // generate the Hashcode value for it.
        final String password = request.getPassword().toLowerCase(IWSConstants.DEFAULT_LOCALE);
        final String hashcode = HashcodeGenerator.generateSHA256(password);

        // Now, let's find the user, based on the credentials
        return dao.findUserByCredentials(request.getUsername(), hashcode);
    }
}
