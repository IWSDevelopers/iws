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
import net.iaeste.iws.core.AdministrationController;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.persistence.notification.Notifications;
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

    private Administration administration = null;

    /**
     * Injects the {@code EntityManager} instance required to invoke our
     * transactional daos. The EntityManager instance can only be injected into
     * the beans, we cannot create a bean for the Administration Controller
     * otherwise.
     *
     * @param entityManager Spring controlled EntityManager instance
     */
    @PersistenceContext
    public void init(final EntityManager entityManager) {
        final Notifications notitications = new NotificationLogger();
        final ServiceFactory factory = new ServiceFactory(entityManager, notitications);
        administration = new AdministrationController(factory);
    }

    // =========================================================================
    // IWS API Administration Functionality
    // =========================================================================

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
    public Fallible processUser(final AuthenticationToken token, final ProcessUserRequest request) {
        return administration.processUser(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserResponse fetchUsers(final AuthenticationToken token, final FetchUserRequest request) {
        return administration.fetchUsers(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processGroups(final AuthenticationToken token, final GroupRequest request) {
        return administration.processGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupResponse fetchGroups(final AuthenticationToken token, final FetchGroupRequest request) {
        return administration.fetchGroups(token, request);
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
    public Fallible processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        return administration.processUserGroupAssignment(token, request);
    }
}
