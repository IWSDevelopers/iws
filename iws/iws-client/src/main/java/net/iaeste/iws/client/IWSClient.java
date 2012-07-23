/*
 * =====================================================================================================================
 * Copyright (c) 2010-2012, secunet Security Networks AG, Germany
 * ---------------------------------------------------------------------------------------------------------------------
 * $Id: //BayLfSt/zobel/develop/main/zobel-admin-client/src/main/java/de/elster/zobel/ZobelAdminClient.java#5 $
 * ---------------------------------------------------------------------------------------------------------------------
 * Project: ZObEL (zobel-admin-client)
 * =====================================================================================================================
 */
package net.iaeste.iws.client;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.PermissionResponse;
import net.iaeste.iws.client.spring.Config;
import net.iaeste.iws.core.AccessController;
import net.iaeste.iws.core.services.ServiceFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The Client is the class used for external testing of the IWS. It should be
 * configurable (which it isn't at the moment), so it is possible to speicify
 * via a Properties file, if the IWS should be loaded as an in-memory library
 * that uses either an in-memory database (HSQL), or a real database
 * (PostgreSQL). Or alternatively, if it should use a deployed IWS, via an
 * AppServer (Glassfish).
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {Config.class})
public class IWSClient implements Access {

    @PersistenceContext
    private EntityManager entityManager;

    private final Access controller;

    public IWSClient() {
        final ServiceFactory factory = new ServiceFactory(entityManager);

        controller = new AccessController(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        return controller.generateSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deprecateSession(final AuthenticationToken token) {
        return controller.deprecateSession(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionResponse findPermissions(final AuthenticationToken token) {
        return controller.findPermissions(token);
    }
}
