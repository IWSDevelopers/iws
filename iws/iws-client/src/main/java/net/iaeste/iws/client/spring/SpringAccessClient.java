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
import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.PermissionResponse;
import net.iaeste.iws.core.AccessController;
import net.iaeste.iws.core.services.ServiceFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The spring based implementation uses the "Test" setup for Spring, to provide
 * a working IWS Library instance. As we're using JPA for our persistence layer,
 * it is important that all invocations is made transactional, hence the need
 * for the "@Transactional" annotation.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {Config.class})
public class SpringAccessClient implements Access {

    @PersistenceContext
    private EntityManager entityManager;
    private final Access access;

    /**
     * Default Constructor, initializes the Core Service Factory with the Spring
     * based EntityManager instance.
     */
    public SpringAccessClient() {
        final ServiceFactory factory = new ServiceFactory(entityManager);
        access = new AccessController(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        return access.generateSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Fallible deprecateSession(final AuthenticationToken token) {
        return access.deprecateSession(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public PermissionResponse findPermissions(final AuthenticationToken token) {
        return access.findPermissions(token);
    }
}
