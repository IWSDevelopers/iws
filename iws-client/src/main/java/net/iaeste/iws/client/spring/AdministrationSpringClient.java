/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.AdministrationSpringClient
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
import net.iaeste.iws.api.requests.*;
import net.iaeste.iws.api.responses.CountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.ejb.AdministrationBean;
import net.iaeste.iws.ejb.NotificationManagerBean;
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
@Repository("administrationSpringClient")
public final class AdministrationSpringClient implements Administration {

    private Administration administration = null;

    /**
     * Injects the {@code EntityManager} instance required to invoke our
     * transactional daos. The EntityManager instance can only be injected into
     * the Spring Beans, we cannot create a Spring Bean for the Administration
     * EJB otherwise.
     *
     * @param entityManager Spring controlled EntityManager instance
     */
    @PersistenceContext
    public void init(final EntityManager entityManager) {
        // Create the Notification Spy, and inject it
        final Notifications notitications = NotificationSpy.getInstance();
        final NotificationManagerBean notificationBean = new NotificationManagerBean();
        notificationBean.setNotifications(notitications);

        // Create an Administration EJB, and inject the EntityManager & Notification Spy
        final AdministrationBean administrationBean = new AdministrationBean();
        administrationBean.setEntityManager(entityManager);
        administrationBean.setNotificationManager(notificationBean);
        administrationBean.postConstruct();

        // Set our Administration implementation to the Administration EJB,
        // running withing a "Spring Container".
        administration = administrationBean;
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

    @Override
    public FetchGroupsForSharingResponse fetchGroupsForSharing(AuthenticationToken token, FetchGroupsForSharingRequest request) {
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
