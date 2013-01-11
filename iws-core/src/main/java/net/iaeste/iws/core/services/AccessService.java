/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.PermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.common.exceptions.AuthorizationException;
import net.iaeste.iws.common.utils.HashcodeGenerator;
import net.iaeste.iws.core.exceptions.SessionExistsException;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.views.UserPermissionView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection ObjectAllocationInLoop
 */
public final class AccessService extends CommonService {

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
     * @param request Request Object with User Credentials
     * @return New AuthenticationToken
     * @throws SessionExistsException if an Active Session already exists
     */
    public AuthenticationToken generateSession(final AuthenticationRequest request) {
        final UserEntity user = findUserFromCredentials(request);
        final SessionEntity activeSession = dao.findActiveSession(user);

        if (activeSession == null) {
            return new AuthenticationToken(generateAndPersistSessionKey(user));
        } else {
            final String msg = "An Active Session for user %s %s already exists.";
            throw new SessionExistsException(format(msg, user.getFirstname(), user.getLastname()));
        }
    }

    /**
     * Clients may store some temporary data together with the Session. This
     * data is set in the DTO Object in the API module, and there converted to
     * a Byte Array. The reason for converting the Byte Array already in the
     * API module, is to avoid needing knowledge about the Object later.<br />
     * The data is simply added (updated) to the currently active session,
     * and saved. The data is then added to the response from a fetchSessionData
     * request.
     *
     * @param token   User Token
     * @param request SessionData Request
     */
    public <T extends Serializable> void saveSessionData(final AuthenticationToken token, final SessionDataRequest<T> request) {
        final SessionEntity entity = dao.findActiveSession(token);
        entity.setSessionData(request.getSessionData());
        entity.setModified(new Date());
        dao.persist(entity);
    }

    public <T extends Serializable> SessionDataResponse<T> fetchSessionData(final AuthenticationToken token) {
        final SessionEntity entity = dao.findActiveSession(token);
        final byte[] data = entity.getSessionData();
        final DateTime created = new DateTime(entity.getCreated());
        final DateTime modified = new DateTime(entity.getModified());

        final SessionDataResponse<T> response = new SessionDataResponse<>();
        response.setSessionData(data);
        response.setCreated(created);
        response.setModified(modified);

        return response;
    }

    /**
     * Deprecates a Session, meaning that the current session associated with
     * the given token, is set to deprecated (invalid), so it can no longer be
     * used.
     *
     * @param token Token containing the session to deprecate
     */
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

    /**
     * Fetches a list of Permissions for a User. If the ExternalGroupId is not
     * set, then the result will be all the Groups and their respective
     * Permissions. If the ExternalGroupId is set, then the result will be for
     * the specific Group, unless the user is either not a member of the given
     * group, or the group is invalid, in which case, an Exception is thrown.
     *
     * @param authentication  Authentication Object, with User & optinal Group
     * @param externalGroupId If only the permissions for the given Groups
     *                        should be fetched
     * @return List of Authorization Objects
     */
    public PermissionResponse findPermissions(final Authentication authentication, final String externalGroupId) {
        // List will always contain at least 1 entry, otherwise an exception is thrown
        final List<UserPermissionView> found = dao.findPermissions(authentication, externalGroupId);
        final Map<Group, Set<Permission>> map = new HashMap<>(10);

        for (final UserPermissionView view : found) {
            final Group group = readGroup(view);
            if (!map.containsKey(group)) {
                map.put(group, EnumSet.noneOf(Permission.class));
            }
            map.get(group).add(view.getPermission());
        }

        final List<Authorization> list = convertPermissionMap(map);
        final String userId = found.get(0).getExternalUserId();
        return new PermissionResponse(userId, list);
    }

    // =========================================================================
    // Internal helper methods
    // =========================================================================

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
        final String username = request.getUsername().toLowerCase(IWSConstants.DEFAULT_LOCALE);
        final String password = request.getPassword().toLowerCase(IWSConstants.DEFAULT_LOCALE);
        final String hashcode = HashcodeGenerator.generateSHA256(password);

        // Now, let's find the user, based on the credentials
        return dao.findUserByCredentials(username, hashcode);
    }

    private static Group readGroup(final UserPermissionView view) {
        final Group group = new Group();

        group.setGroupId(view.getExternalGroupId());
        group.setGroupType(view.getGroupType());
        group.setGroupName(view.getGroupName());
        group.setCountryId(view.getCountryId());
        group.setDescription(view.getGroupDescription());

        return group;
    }

    private static List<Authorization> convertPermissionMap(final Map<Group, Set<Permission>> map) {
        final List<Authorization> result = new ArrayList<>(map.size());

        for (final Map.Entry<Group, Set<Permission>> set : map.entrySet()) {
            result.add(new Authorization(set.getKey(), set.getValue()));
        }

        return result;
    }
}
