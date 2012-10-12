/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.SpringAdministrationclient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.spring;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CountryRequest;
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
import net.iaeste.iws.core.AdministrationController;
import net.iaeste.iws.core.services.ServiceFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Spring based Administration Client, which wraps the Administration Controller
 * from the IWS core module within a transactional layer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Transactional
@Repository("springAdministrationClient")
public final class SpringAdministrationclient implements Administration {

    @PersistenceContext
    private EntityManager entityManager;

    private final Object lock = new Object();
    private Administration administration = null;

    // =========================================================================
    // IWS API Administration Functionality
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processUser(final AuthenticationToken token, final ProcessUserRequest request) {
        return getAdministration().processUser(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserResponse fetchUsers(final AuthenticationToken token, final FetchUserRequest request) {
        return getAdministration().fetchUsers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processGroups(final AuthenticationToken token, final GroupRequest request) {
        return getAdministration().processGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupResponse fetchGroups(final AuthenticationToken token, final FetchGroupRequest request) {
        return getAdministration().fetchGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processCountries(final AuthenticationToken token, final CountryRequest request) {
        return getAdministration().processCountries(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        return getAdministration().fetchCountries(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        return getAdministration().processUserGroupAssignment(token, request);
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * Since Spring only performs the injection of resources after class
     * instantiation, we need a second place to actually create the
     * Administration Controller instance that we wish to use for our
     * communication with the IWS. This is required to have a proper
     * Transactional mechanism surrounding the calls, so we don't have to worry
     * about the current state.<br />
     *   The method uses synchronization to create the instance, to ensure that
     * the creation of a new Instance is thread safe.
     *
     * @return Administration Instance with Transactions
     */
    private Administration getAdministration() {
        synchronized (lock) {
            if (administration == null) {
                final ServiceFactory factory = new ServiceFactory(entityManager);
                administration = new AdministrationController(factory);
            }

            return administration;
        }
    }
}
