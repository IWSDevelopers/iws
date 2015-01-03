/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.callers.AdministrationCaller
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse.callers;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AccountNameRequest;
import net.iaeste.iws.api.requests.ContactsRequest;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchRoleRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.OwnerRequest;
import net.iaeste.iws.api.requests.SearchUserRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.api.responses.ProcessUserGroupResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.AdministrationClient;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * The IWS FitNesse implementation of the API logic. The Class will attempt to
 * invoke the IWS Client module, and wrap all calls with an Exception check that
 * will throw a new {@code StopTestException} if an error occured - this is the
 * expected behaviour for the FitNesse tests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AdministrationCaller implements Administration {

    // The Client handles the IWS for us, we use use it
    private final Administration caller = new AdministrationClient();

    // =========================================================================
    // Implementation of methods from Administration in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateUserResponse createUser(final AuthenticationToken token, final CreateUserRequest request) {
        try {
            return caller.createUser(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible activateUser(final String activationString) {
        try {
            return caller.activateUser(activationString);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible updateUsername(final String updateCode) {
        try {
            return caller.updateUsername(updateCode);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible controlUserAccount(final AuthenticationToken token, final UserRequest request) {
        try {
            return caller.controlUserAccount(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible changeAccountName(final AuthenticationToken token, final AccountNameRequest request) {
        try {
            return caller.changeAccountName(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchUserResponse fetchUser(final AuthenticationToken token, final FetchUserRequest request) {
        try {
            return caller.fetchUser(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchRoleResponse fetchRoles(final AuthenticationToken token, final FetchRoleRequest request) {
        try {
            return caller.fetchRoles(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessGroupResponse processGroup(final AuthenticationToken token, final GroupRequest request) {
        try {
            return caller.processGroup(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deleteSubGroup(final AuthenticationToken token, final GroupRequest request) {
        try {
            return caller.deleteSubGroup(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupResponse fetchGroup(final AuthenticationToken token, final FetchGroupRequest request) {
        try {
            return caller.fetchGroup(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible changeGroupOwner(final AuthenticationToken token, final OwnerRequest request) {
        try {
            return caller.changeGroupOwner(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessUserGroupResponse processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        try {
            return caller.processUserGroupAssignment(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchUserResponse searchUsers(final AuthenticationToken token, final SearchUserRequest request) {
        try {
            return caller.searchUsers(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processCountry(final AuthenticationToken token, final CountryRequest request) {
        try {
            return caller.processCountry(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        try {
            return caller.fetchCountries(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmergencyListResponse fetchEmergencyList(final AuthenticationToken token) {
        try {
            return caller.fetchEmergencyList(token);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContactsResponse fetchContacts(final AuthenticationToken token, final ContactsRequest request) {
        try {
            return caller.fetchContacts(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }
}
