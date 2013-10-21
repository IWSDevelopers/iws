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

import static net.iaeste.iws.common.utils.HashcodeGenerator.generateHash;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Authorization;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.core.exceptions.SessionException;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.singletons.ActiveSessions;
import net.iaeste.iws.core.singletons.LoginRetries;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.views.UserPermissionView;
import org.apache.log4j.Logger;

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
 */
public final class AccessService extends CommonService<AccessDao> {

    private static final Logger log = Logger.getLogger(AccessService.class);
    private final Notifications notifications;
    private final ActiveSessions activeSessions;
    private final LoginRetries loginRetries;

    /**
     * Default Constructor. This Service only requires an AccessDao instance,
     * which is used for all database operations.
     *
     * @param dao           AccessDAO instance
     * @param notifications Notification Object
     */
    public AccessService(final AccessDao dao, final Notifications notifications, final ActiveSessions activeSessions, final LoginRetries loginRetries) {
        super(dao);

        this.notifications = notifications;
        this.activeSessions = activeSessions;
        this.loginRetries = loginRetries;
    }

    /**
     * Generates a new Session for the User, using the provided (verified)
     * credentials. Returns a new Token, if no session exists. If an active
     * session exists, then the method will thrown an SessionExists Exception.
     *
     * @param request Request Object with User Credentials
     * @return New AuthenticationToken
     * @throws SessionException if an Active Session already exists
     */
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        removeDeprecatedSessions();
        loginRetries.registerUser(request.getUsername());
        final UserEntity user = findUserFromCredentials(request);
        final SessionEntity activeSession = dao.findActiveSession(user);

        if ((activeSession == null) && (activeSessions.getNumberOfActiveTokens() < IWSConstants.MAX_ACTIVE_TOKENS)) {
            log.info("Creating a new Session for the user " + user.toString());
            final String key = generateAndPersistSessionKey(user);
            activeSessions.registerToken(key);
            loginRetries.removeAuthenticatedUser(request.getUsername());

            final AuthenticationToken token = new AuthenticationToken(key);
            return new AuthenticationResponse(token);
        } else {
            final String msg = "An Active Session for user %s %s already exists.";
            throw new SessionException(format(msg, user.getFirstname(), user.getLastname()));
        }
    }

    /**
     * Handles the first part of resetting the current session for a user. It
     * simply adds a Temporary Code to the User, and ships it as an immediate
     * notification to the user. The user can then use this Code to invoke the
     * second part of the resetting mechanism, which handles the actual
     * resetting.<br />
     *   The method requires that we can uniquely identify the user, thus the
     * same information as for generating a new Session is required.<br />
     *   If no active sessions exists, then an Exception is thrown.
     *
     * @param request Authentication Request Object, with user credentials
     * @throws SessionException if no active session exists
     */
    public void requestResettingSession(final AuthenticationRequest request) {
        removeDeprecatedSessions();
        final UserEntity user = findUserFromCredentials(request);
        final SessionEntity activeSession = dao.findActiveSession(user);

        if (activeSession != null) {
            user.setCode(generateHash(UUID.randomUUID().toString()));
            dao.persist(user);
            final Authentication authentication = new Authentication(user, UUID.randomUUID().toString());
            notifications.notify(authentication, user, NotificationType.RESET_SESSION);
        } else {
            throw new SessionException("No Session exists for this user.");
        }
    }

    /**
     * Reads the current user & session data from the system. If unable to find
     * a user for the given reset String, or if no active Session Objects
     * exists, then an exception is thrown, otherwise the current Sessions are
     * deprecated and a new Session is created.
     *
     * @param resetSessionString The Token for resetting the users Session
     * @return New AuthenticationToken
     */
    public AuthenticationToken resetSession(final String resetSessionString) {
        removeDeprecatedSessions();
        final UserEntity user = dao.findUserByCodeAndStatus(resetSessionString, UserStatus.ACTIVE);
        final SessionEntity deadSession = dao.findActiveSession(user);

        if (deadSession != null) {
            dao.deprecateSession(user);
            activeSessions.removeToken(deadSession.getSessionKey());
            return new AuthenticationToken(generateAndPersistSessionKey(user));
        } else {
            throw new SessionException("No Session exists to reset.");
        }
    }

    /**
     * Clients may store some temporary data together with the Session. This
     * data is set in the DTO Object in the API module, and there converted to
     * a Byte Array. The reason for converting the Byte Array already in the
     * API module, is to avoid needing knowledge about the Object later.<br />
     * The data is simply added (updated) to the currently active session,
     * and saved. The data is then added to the response from a readSessionData
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

    /**
     * Retrieves the data that a client stored temporarily with the Session.
     * The data is read out as a serializable Object, presuming that the
     * invoking method is using the correct Object type for reading as was used
     * for writing.
     *
     * @param token User Token
     * @return SessionData Response
     */
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
        removeDeprecatedSessions();
        final SessionEntity session = dao.findActiveSession(token);
        dao.deprecateSession(session.getUser());
        activeSessions.removeToken(token.getToken());
        log.info("Deprecated session for user: " + session.getUser().toString());
    }

    /**
     * Finds the user account for the user who forgot the password in the
     * database, based on the provided username. If the username does not exist
     * in the database, then the request is ignored and no error message is
     * returned. This is to ensure that a caller cannot use this functionality
     * to learn about possible accounts in the system.
     *
     * @param username Username to the users account
     */
    public void forgotPassword(final String username) {
        final UserEntity user = dao.findActiveUserByUsername(username);

        if (user != null) {
            user.setCode(generateHash(UUID.randomUUID().toString(), user.getSalt()));
            dao.persist(user);
            notifications.notify(user);
        }
    }

    /**
     * Resets the usersession, by finding the Account via the token. Only
     * accounts that are currently Active can have their passwords reset.
     *
     * @param resetPasswordToken Reset Password Token, from the notification
     * @param password           New Password for the user
     */
    public void resetPassword(final String resetPasswordToken, final Password password) {
        final UserEntity user = dao.findUserByCodeAndStatus(resetPasswordToken, UserStatus.ACTIVE);

        if (user != null) {
            final String pwd = password.getNewPassword().toLowerCase(IWSConstants.DEFAULT_LOCALE);
            final String salt = UUID.randomUUID().toString();

            user.setPassword(generateHash(pwd, salt));
            user.setSalt(salt);
            user.setCode(null);
            user.setModified(new Date());

            dao.persist(user);
        } else {
            throw new IWSException(IWSErrors.AUTHENTICATION_ERROR, "No account for this user was found.");
        }
    }

    /**
     * Updates a users Password (and Salt). The method checks the users current
     * Password against the one provided, and only if there is a match, will
     * the syste update the new Password. If the old Password is invalid, then
     * the check should catch it.<br />
     *   If the old Password is invalid, then an Invalid
     *
     * @param authentication Authentication Object, with User & optinal Group
     * @param password       New Password for the user
     */
    public void updatePassword(final Authentication authentication, final Password password) {
        final String newPassword = password.getNewPassword().toLowerCase(IWSConstants.DEFAULT_LOCALE);
        final String salt = UUID.randomUUID().toString();
        final UserEntity user = authentication.getUser();

        if (isOldPasswordValid(user, password)) {
            user.setPassword(generateHash(newPassword, salt));
            user.setSalt(salt);
            user.setModified(new Date());

            dao.persist(user);
        } else {
            throw new IWSException(IWSErrors.CANNOT_UPDATE_PASSWORD, "The system cannot update Password.");
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
    public FetchPermissionResponse findPermissions(final Authentication authentication, final String externalGroupId) {
        // List will always contain at least 1 entry, otherwise an exception is thrown
        final List<UserPermissionView> found = dao.findPermissions(authentication, externalGroupId);
        final Map<String, Set<Permission>> permissions = new HashMap<>(16);
        final Map<String, UserGroup> userGroups = new HashMap<>(16);

        for (final UserPermissionView view : found) {
            final UserGroup userGroup = readUserGroup(view);
            if (!userGroups.containsKey(userGroup.getUserGroupId())) {
                userGroups.put(userGroup.getUserGroupId(), userGroup);
                permissions.put(userGroup.getUserGroupId(), EnumSet.noneOf(Permission.class));
            }
            permissions.get(userGroup.getUserGroupId()).add(view.getPermission());
        }

        final List<Authorization> list = convertPermissionMap(userGroups, permissions);
        final String userId = found.get(0).getExternalUserId();
        return new FetchPermissionResponse(userId, list);
    }

    // =========================================================================
    // Internal helper methods
    // =========================================================================

    /**
     * Removes all deprecated Sessions from the in-memory listing.
     */
    private void removeDeprecatedSessions() {
        for (final String token : activeSessions.findAndRemoveExpiredTokens()) {
            final SessionEntity session = dao.findActiveSession(token);
            final UserEntity user = session.getUser();
            dao.deprecateSession(user);
            log.info("Deprecated inactive session for user " + user.toString());
        }
    }

    private String generateAndPersistSessionKey(final UserEntity user) {
        // Generate new Hashcode from the User Credentials, and some other entropy
        final String entropy = UUID.randomUUID().toString() + user.getPassword();
        final String sessionKey = generateHash(entropy);

        // Generate the new Session, and persist it
        final SessionEntity entity = new SessionEntity(user, sessionKey);
        dao.persist(entity);

        // Now, let's return the newly generated SessionKey
        return sessionKey;
    }

    /**
     * Finding a user based on the credentials requires that we first lookup the
     * user, based on the username. The method then checks if the password
     * matches the one stored for the user.<br />
     *   The method makes no distinction between the existing of an Entity, or
     * of the password was incorrect, this is to avoid that someone attempts to
     * run a check against the system for known user accounts, which can then be
     * further tested.<br />
     *   If the user was found (Entity exists matching the credentials), then
     * this Entity is returned, otherwise a {@code IWSException} is thrown.
     *
     * @param request Authentication Requst Object with username and password
     * @return Found UserEntity
     * @throws IWSException if no user account exists or matches the credentials
     */
    private UserEntity findUserFromCredentials(final AuthenticationRequest request) throws IWSException {
        // First, find an Entity exists for the given (lowercased) username
        final String username = request.getUsername().toLowerCase(IWSConstants.DEFAULT_LOCALE);
        final UserEntity user = dao.findActiveUserByUsername(username);

        if (user != null) {
            if (user.getStatus() == UserStatus.ACTIVE) {
                // Okay, an Active  UserEntity Object was found, now to match if
                // the Password is correct, we first have to read it out of the
                // Request Object, lowercase and generate a salted
                // cryptographical hashvalue for it, which we can then match
                // directly with the stored value from the UserEntity
                final String password = request.getPassword().toLowerCase(IWSConstants.DEFAULT_LOCALE);
                final String hashcode = generateHash(password, user.getSalt());

                if (!hashcode.equals(user.getPassword())) {
                    // Password mismatch, throw generic error
                    throw new IWSException(IWSErrors.AUTHENTICATION_ERROR, "No account for the user '" + username + "' was found.");
                }

                // All good, return found UserEntity
                return user;
            } else {
                // No active account exist! Error condition
                throw new IWSException(IWSErrors.NO_USER_ACCOUNT_FOUND, "The account is not active, and can therfore not be used.");
            }
        } else {
            // No account for this User, throw generic error
            throw new IWSException(IWSErrors.AUTHENTICATION_ERROR, "No account for the user '" + username + "' was found.");
        }
    }

    private static boolean isOldPasswordValid(final UserEntity user, final Password password) {
        final String oldPassword = password.getOldPassword();
        final boolean result;

        if (oldPassword != null) {
            final String pwd = generateHash(oldPassword.toLowerCase(IWSConstants.DEFAULT_LOCALE), user.getSalt());
            result = user.getPassword().equals(pwd);
        } else {
            result = false;
        }

        return result;
    }

    private static UserGroup readUserGroup(final UserPermissionView view) {
        final UserGroup userGroup = new UserGroup();

        userGroup.setUserGroupId(view.getExternalUserGroupId());
        userGroup.setGroup(readGroup(view));
        userGroup.setRole(readRole(view));
        userGroup.setTitle(view.getTitle());

        return userGroup;
    }

    private static Group readGroup(final UserPermissionView view) {
        final Group group = new Group();

        group.setGroupId(view.getExternalGroupId());
        group.setGroupType(view.getGroupType());
        group.setGroupName(view.getGroupName());
        group.setCountry(readCountry(view));
        group.setDescription(view.getGroupDescription());

        return group;
    }

    private static Role readRole(final UserPermissionView view) {
        final Role role = new Role();

        role.setRoleId(view.getExternalRoleId());
        role.setRoleName(view.getRole());

        return role;
    }

    private static Country readCountry(final UserPermissionView view) {
        final Country country = new Country();

        country.setCountryCode(view.getCountryCode());

        return country;
    }

    private static List<Authorization> convertPermissionMap(final Map<String, UserGroup> userGroupMap, final Map<String, Set<Permission>> permissionMap) {
        final List<Authorization> result = new ArrayList<>(userGroupMap.size());

        for (final Map.Entry<String, Set<Permission>> permissionSet : permissionMap.entrySet()) {
            final String key = permissionSet.getKey();
            result.add(readFromSet(userGroupMap.get(key), permissionSet));
        }

        return result;
    }

    private static Authorization readFromSet(final UserGroup userGroup, final Map.Entry<String, Set<Permission>> set) {
        userGroup.getRole().setPermissions(set.getValue());

        return new Authorization(userGroup);
    }
}
