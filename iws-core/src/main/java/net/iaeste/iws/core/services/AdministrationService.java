/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.AdministrationService
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

import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.CountryResponse;
import net.iaeste.iws.api.responses.GroupResponse;
import net.iaeste.iws.api.responses.UserResponse;

import javax.persistence.EntityManager;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.6
 */
public class AdministrationService {

    private final EntityManager entityManager;

    public AdministrationService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void processUsers(final AuthenticationToken token, final UserRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public UserResponse fetchUsers(final AuthenticationToken token, final FetchUserRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processGroups(final AuthenticationToken token, final GroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public GroupResponse fetchGroups(final AuthenticationToken token, final FetchGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processCountries(final AuthenticationToken token, final CountryRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public CountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }
}
