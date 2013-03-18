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
import net.iaeste.iws.api.requests.*;
import net.iaeste.iws.api.responses.CountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.util.Fallible;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class AdministrationClient implements Administration {

    private final Administration administration;

    /**
     * Default Constructor.
     */
    public AdministrationClient() {
        administration = ClientFactory.getInstance().getAdministrationImplementation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible createUser(final AuthenticationToken token, final CreateUserRequest request) {
        return administration.createUser(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible activateUser(final String activationString) {
        return administration.activateUser(activationString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible controlUserAccount(final AuthenticationToken token, final UserRequest request) {
        return administration.controlUserAccount(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchUserResponse fetchUser(final AuthenticationToken token, final FetchUserRequest request) {
        return administration.fetchUser(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processGroup(final AuthenticationToken token, final GroupRequest request) {
        return administration.processGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupResponse fetchGroup(final AuthenticationToken token, final FetchGroupRequest request) {
        return administration.fetchGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processCountries(final AuthenticationToken token, final CountryRequest request) {
        return administration.processCountries(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        return administration.fetchCountries(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupsForSharingResponse fetchGroupsForSharing(final AuthenticationToken token, final FetchGroupsForSharingRequest request) {
        return administration.fetchGroupsForSharing(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        return administration.processUserGroupAssignment(token, request);
    }
}
