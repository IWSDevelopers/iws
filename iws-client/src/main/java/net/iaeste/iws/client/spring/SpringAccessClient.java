/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.SpringAccessClient
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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.PermissionResponse;
import net.iaeste.iws.core.AccessController;
import net.iaeste.iws.core.services.ServiceFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Spring based Access Client, which wraps the Access Controller from the
 * IWS core module within a transactional layer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Transactional
@Repository("springAccessClient")
public final class SpringAccessClient implements Access {

    @PersistenceContext
    private EntityManager entityManager;

    private final Object lock = new Object();
    private Access access = null;

    // =========================================================================
    // IWS API Access Functionality
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        return getAccess().generateSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deprecateSession(final AuthenticationToken token) {
        return getAccess().deprecateSession(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionResponse fetchPermissions(final AuthenticationToken token) {
        return getAccess().fetchPermissions(token);
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * Since Spring only performs the injection of resources after class
     * instantiation, we need a second place to actually create the Access
     * Controller instance that we wish to use for our communication with the
     * IWS. This is required to have a proper Transactional mechanism
     * surrounding the calls, so we don't have to worry about the current
     * state.<br />
     *   The method uses synchronization to create the instance, to ensure that
     * the creation of a new Instance is thread safe.
     *
     * @return Access Instance with Transactions
     */
    private Access getAccess() {
        synchronized (lock) {
            if (access == null) {
                final ServiceFactory factory = new ServiceFactory(entityManager);
                access = new AccessController(factory);
            }

            return access;
        }
    }
}
