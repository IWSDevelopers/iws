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
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.PermissionResponse;
import net.iaeste.iws.common.exceptions.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Implementation of the {@link SecurityContext}
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Named("securityContext")
@SessionScoped
public class SecurityContextImpl implements SecurityContext {

    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private static final Logger LOG = LoggerFactory.getLogger(SecurityContextImpl.class);
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
     * {@inheritDoc}
     */
    @Override
    public void authenticate(String username, String password) throws AuthenticationException {
        authenticateUser(username, password);
        List<Authorization> permissions = requestPermissions(null);
        authorizations.put(DEFAULT_GROUP_ID, permissions);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isAuthenticated() {
        return authentication != null;
    }

    /**
     * @see SecurityContextImpl#authenticate(String, String)
     */
    private void authenticateUser(String username, String password) {
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        AuthenticationResponse response = accessController.generateSession(request);

        if (response.isOk()) {
            this.username = username;
            this.authentication = response.getToken();
            LOG.info("User {} successfully authenticated!", username);
        } else {
            LOG.info("Could not authenticate user {}", username);
            throw new AuthenticationException(response.getMessage());
        }
    }

    /**
     * Request permissions for an authenticated user, optionally for a given group.
     * <p/>
     * Stores the permissions in the <code>authorizations</code> Map.
     * If no permissions could be found, stores an empty List in the Map.
     *
     * @param groupId a groupId if the permissions are requested
     *                for a certain group, null otherwise
     * @throws AuthenticationException if permissions could not be loaded
     */
    private List<Authorization> requestPermissions(Integer groupId) throws AuthenticationException {
        AuthenticationToken copyToken = new AuthenticationToken(authentication.getToken(), groupId);
        PermissionResponse response = accessController.findPermissions(copyToken);

        if (response.isOk()) {
            return response.getAuthorizations();
        } else {
            LOG.info("Could not request permissions for user {}, group {}", username, groupId);
            throw new AuthenticationException(response.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationToken getAuthentication() {
        return new AuthenticationToken(authentication);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(Permission permission) {
        return hasPermission(permission, DEFAULT_GROUP_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(Permission permission, Integer groupId) {
        if (groupId == null) {
            throw new VerificationException("GroupId cannot be null");
        }

        if (!authorizations.containsKey(groupId)) {
            try {
                List<Authorization> permissions = requestPermissions(groupId);
                authorizations.put(groupId, permissions);
            } catch (AuthenticationException e) {
                LOG.error("Reading permissions for groupId " + groupId + " failed!", e);
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
     * {@inheritDoc}
     */
    @Override
    public void logout() {
        Fallible response = accessController.deprecateSession(authentication);

        if (response.isOk()) {
            LOG.info("User {} successfully logged out!", username);
        } else {
            LOG.warn("Error while logging out user {}", username);
            LOG.error(response.getMessage());
        }
        this.username = null;
        this.authentication = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Locale getUserLocale() {
        // TODO change to the logged in users locale
        return Locale.GERMAN;
    }
}
