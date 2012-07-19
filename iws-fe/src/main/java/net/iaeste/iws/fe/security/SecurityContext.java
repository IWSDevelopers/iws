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

import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.common.exceptions.AuthenticationException;

import java.io.Serializable;
import java.util.Locale;

/**
 * The SecurityContext is responsible for holding the security information of the
 * current user session and providing methods for user authentication and
 * authorization.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public interface SecurityContext extends Serializable {

    /**
     * Authenticates a user with the given credentials.
     * Initializes the default Permissions and settings
     * if the user is authenticated successfully.
     *
     * @param username plaintext username
     * @param password plaintext password
     * @throws AuthenticationException if the user could not be authenticated
     *                                 or permissions could not be loaded
     */
    public void authenticate(String username, String password) throws AuthenticationException;

    /**
     * @return the username of the current logged in user
     */
    public String getUsername();

    /**
     * @return a copy of the current {@link net.iaeste.iws.api.data.AuthenticationToken}
     */
    public AuthenticationToken getAuthentication();

    /**
     * Check whether the logged in user has the given permission
     *
     * @param permission requested Permission
     * @return true if the user has the permission, false otherwise
     */
    public boolean hasPermission(Permission permission);

    /**
     * Check whether the logged in user has the given permission for the given group
     *
     * @param permission requested Permission
     * @param groupId    groupId for which the permission is valid
     * @return true if the user has the permission, false otherwise
     */
    public boolean hasPermission(Permission permission, Integer groupId);

    /**
     * Deprecates the current user session and logs the current user out.
     * User credentials are removed event if deprecating the session fails.
     */
    public void logout();

    /**
     * @return true if a user is already authenticated
     */
    public boolean isAuthenticated();

    /**
     * @return the {@link} Locale of the current logged in user
     */
    public Locale getUserLocale();
}
