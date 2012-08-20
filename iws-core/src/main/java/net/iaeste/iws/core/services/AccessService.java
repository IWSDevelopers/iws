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
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.common.utils.HashcodeGenerator;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.views.UserPermissionView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection ObjectAllocationInLoop
 */
public class AccessService {

    private final AccessDao dao;

    public AccessService(final AccessDao dao) {
        this.dao = dao;
    }

    public AuthenticationToken generateSession(final AuthenticationRequest request) {
        final UserEntity user = findUserFromCredentials(request);
        final String sessionKey = generateAndPersistSessionKey(user);
        final AuthenticationToken token = new AuthenticationToken(sessionKey);

        return token;
    }

    private UserEntity findUserFromCredentials(final AuthenticationRequest request) {
        // First, let's read the Password in clear-text (lower cased), and
        // generate the Hashcode value for it.
        final String password = request.getPassword().toLowerCase(IWSConstants.DEFAULT_LOCALE);
        final String hashcode = HashcodeGenerator.generateSHA256(password);

        // Now, let's find the user, based on the credentials
        return dao.findUserByCredentials(request.getUsername(), hashcode);
    }

    private String generateAndPersistSessionKey(final UserEntity user) {
        // Generate new Hashcode from the User Credentials, and some other entropy
        final String entropy = UUID.randomUUID().toString() + user.getUserName();
        final String sessionKey = HashcodeGenerator.generateSHA256(entropy);

        // Generate the new Session, and persist it
        final SessionEntity entity = new SessionEntity(user, sessionKey);
        dao.persist(entity);

        // Now, let's return the newly generated SessionKey
        return sessionKey;
    }

    public void deprecateSession(final AuthenticationToken token) {
        throw new NotImplementedException("The 'deprecateSession' method is not yet implemented");
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
}
