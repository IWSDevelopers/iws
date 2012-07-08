/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.security.SecurityContext
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fe.security;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.data.Authorization;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.PermissionResponse;
import net.iaeste.iws.common.exceptions.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for holding the security information of the
 * current user session and providing methods for user authentication and
 * authorization.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Named
@SessionScoped
public class SecurityContext implements Serializable {

    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private static final Logger LOG = LoggerFactory.getLogger(SecurityContext.class);
    private static final Integer DEFAULT_GROUP_ID = -1;

    @SuppressWarnings({"CdiUnproxyableBeanTypesInspection"})
    @Inject
    private Access accessController;

    private String username;
    private AuthenticationToken authentication;

    /**
     * Stores {@link Authorization} for a given user.
     * Default Authorizations are loaded during login, Authorizations
     * for certain groups are lazily loaded when requested.
     * <p/>
     * The groupId is used as key, default Authorizations
     * have the key DEFAULT_GROUP_ID.
     */
    private Map<Integer, List<Authorization>> authorizations = new HashMap<>();

    /**
     * Authenticates a user with the given credentials.
     * Initialized the default Permissions if the user is
     * authenticated successfully.
     *
     * @param username plaintext username
     * @param password plaintext password
     * @throws AuthenticationException if the response was not ok or permission could not be loaded
     */
    public void authenticate(String username, String password) throws AuthenticationException {
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        AuthenticationResponse response = accessController.generateSession(request);

        if (response.isOk()) {
            LOG.info("User {} successfully authenticated!", username);
            this.username = username;
            this.authentication = response.getToken();
            requestPermissions(null);
        } else {
            LOG.info("Could not authenticate user {}", username);
            throw new AuthenticationException(response.getMessage());
        }
    }

    /**
     * Request permissions for an authenticated user,optionally for a given group.
     * <p/>
     * Stores the permissions in the <code>authorizations</code> Map.
     * If no permissions could be found, stores an empty List in the Map.
     *
     * @param groupId a groupId if the permissions are requested
     *                for a certain group, otherwise null
     * @throws AuthenticationException if permissions could not be loaded
     */
    private void requestPermissions(Integer groupId) throws AuthenticationException {
        AuthenticationToken copyToken = new AuthenticationToken(authentication.getToken(), groupId);
        PermissionResponse response = accessController.findPermissions(copyToken);

        if (response.isOk()) {
            authorizations.put(groupId == null ? DEFAULT_GROUP_ID : groupId, response.getAuthorizations());
        } else {
            LOG.info("Could not request permissions for user {}, group {}", username, groupId);
            throw new AuthenticationException(response.getMessage());
        }
    }

    /**
     * @return the username of the current logged in user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return a copy of the current {@link AuthenticationToken}
     */
    public AuthenticationToken getAuthentication() {
        return new AuthenticationToken(authentication);
    }

    /**
     * Check whether the logged in user has the given permission
     *
     * @param permission requested Permission
     * @return true if the user has the permission, false otherwise
     */
    public boolean hasPermission(Permission permission) {
        return hasPermission(permission, DEFAULT_GROUP_ID);
    }

    /**
     * Check whether the logged in user has the given permission for the given group
     *
     * @param permission requested Permission
     * @param groupId    groupId for which the permission is valid
     * @return true if the user has the permission, false otherwise
     */
    public boolean hasPermission(Permission permission, Integer groupId) {
        if (groupId == null) {
            throw new VerificationException("GroupId cannot be null");
        }

        if (!authorizations.containsKey(groupId)) {
            try {
                requestPermissions(groupId);
            } catch (AuthenticationException e) {
                LOG.error("Reading permissions for groupId " + groupId + " failed. Error was: {}:{}", e.getError(), e.getMessage());
                authorizations.put(groupId, Collections.<Authorization>emptyList());
            }
        }

        for (Authorization a : authorizations.get(groupId)) {
            if (a.getPermission() == permission) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deprecate the session of the current user.Will be called automatically
     * before the bean is destroyed so we don't have open sessions after
     * users are logged out by session timeouts.
     */
    @PreDestroy
    public void logout() {
        accessController.deprecateSession(authentication);
    }
}
