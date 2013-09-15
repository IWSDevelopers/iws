/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.AdministrationClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchRoleRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.api.util.Fallible;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class AdministrationClient implements Administration {

    private final Administration client;

    /**
     * Default Constructor.
     */
    public AdministrationClient() {
        client = ClientFactory.getInstance().getAdministrationImplementation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateUserResponse createUser(final AuthenticationToken token, final CreateUserRequest request) {
        return client.createUser(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible activateUser(final String activationString) {
        return client.activateUser(activationString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible updateUsername(final String updateCode) {
        return client.updateUsername(updateCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible controlUserAccount(final AuthenticationToken token, final UserRequest request) {
        return client.controlUserAccount(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchUserResponse fetchUser(final AuthenticationToken token, final FetchUserRequest request) {
        return client.fetchUser(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchRoleResponse fetchRoles(final AuthenticationToken token, final FetchRoleRequest request) {
        return client.fetchRoles(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessGroupResponse processGroup(final AuthenticationToken token, final GroupRequest request) {
        return client.processGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deleteSubGroup(final AuthenticationToken token, final GroupRequest request) {
        return client.deleteSubGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupResponse fetchGroup(final AuthenticationToken token, final FetchGroupRequest request) {
        return client.fetchGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processCountries(final AuthenticationToken token, final CountryRequest request) {
        return client.processCountries(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        return client.fetchCountries(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        return client.processUserGroupAssignment(token, request);
    }
}
