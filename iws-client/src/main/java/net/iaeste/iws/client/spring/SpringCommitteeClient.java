/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.SpringCommitteeClient
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

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.RegionalGroupRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.core.CommitteeController;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.persistence.notification.Notifications;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Transactional
@Repository("springCommitteeClient")
public class SpringCommitteeClient implements Committees {

    private Committees committees = null;

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
        final Notifications notitications = NotificationSpy.getInstance();
        final ServiceFactory factory = new ServiceFactory(entityManager, notitications);
        committees = new CommitteeController(factory);
    }

    // =========================================================================
    // IWS API Committees Functionality
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible createCommittee(final AuthenticationToken token, final CommitteeRequest request) {
        return committees.createCommittee(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageCommittee(final AuthenticationToken token, final CommitteeRequest request) {
        return committees.manageCommittee(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible upgradeCommittee(final AuthenticationToken token, final CommitteeRequest request) {
        return committees.upgradeCommittee(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageInternationalGroup(final AuthenticationToken token, final InternationalGroupRequest request) {
        return committees.manageInternationalGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible createRegionalGroup(final AuthenticationToken token, final RegionalGroupRequest request) {
        return committees.createRegionalGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageRegionalGroup(final AuthenticationToken token, final RegionalGroupRequest request) {
        return committees.manageRegionalGroup(token, request);
    }
}
