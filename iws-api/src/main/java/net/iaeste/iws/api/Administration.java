/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.Administration
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.ProcessUserRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.responses.CountryResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.GroupResponse;
import net.iaeste.iws.api.responses.UserResponse;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface Administration {

    Fallible createUser(AuthenticationToken token, CreateUserRequest request);

    /**
     * Users cannot access the IWS, until their account has been activated, this
     * happens via an e-mail that is sent to their e-mail address (username),
     * with an activation link.<br />
     *   Once activation link is activated, this method should be invoked, which
     * will handle the actual activation process. Meaning, that if an account is
     * found in status "new", and with the given activation code, then it is
     * being updated to status "active", the code is removed and the updates are
     * saved.
     *
     * @param activationString
     */
    Fallible activateUser(String activationString);
    Fallible processUser(AuthenticationToken token, ProcessUserRequest request);
    UserResponse fetchUsers(AuthenticationToken token, FetchUserRequest request);

    Fallible processGroups(AuthenticationToken token, GroupRequest request);
    GroupResponse fetchGroups(AuthenticationToken token, FetchGroupRequest request);

    Fallible processCountries(AuthenticationToken token, CountryRequest request);
    CountryResponse fetchCountries(AuthenticationToken token, FetchCountryRequest request);

    Fallible processUserGroupAssignment(AuthenticationToken token, UserGroupAssignmentRequest request);
}
