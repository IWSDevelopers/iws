/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.AccessController
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Authorization;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.PermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.core.services.AccessService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Primary implementation of the IWS Access functionality. The class is the
 * default implementation, and will handle all issues regarding testing request
 * data and building result Objects.<br />
 *   Since some communication forms (eg. HTML based), doesn't allow for
 * immutable Objects, the methods will first create a copy of the request
 * Object, and then verify the correctness of this before invoking the actual
 * Bussiness Logic on the Object.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class AccessController extends CommonController implements Access {

    private static final String AUTHENTICATION_REQUEST_ERROR = "The Authentication Request Object is undefined.";
    private static final Logger LOG = LoggerFactory.getLogger(AccessController.class);
    private final ServiceFactory factory;

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory  The ServiceFactory
     */
    public AccessController(final ServiceFactory factory) {
        super(factory.getAccessDao());

        this.factory = factory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        LOG.trace("Starting generateSession()");
        AuthenticationResponse response;

        try {
            verify(request, AUTHENTICATION_REQUEST_ERROR);

            final AccessService service = factory.prepareAuthenticationService();
            final AuthenticationToken token = service.generateSession(request);
            response = new AuthenticationResponse(token);
        } catch (IWSException e) {
            response = new AuthenticationResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished generateSession()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible saveSessionData(final AuthenticationToken token, final SessionDataRequest request) {
        LOG.trace("Starting saveSessionData()");
        Fallible response;

        try {
            verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            service.saveSessionData(token, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished saveSessionData()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionDataResponse fetchSessionData(final AuthenticationToken token) {
        LOG.trace("Starting fetchSessionData()");
        SessionDataResponse response;

        try {
            verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            response = service.verifySession(token);
        } catch (IWSException e) {
            response = new SessionDataResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchSessionData()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deprecateSession(final AuthenticationToken token) {
        LOG.trace("Starting deprecateSession()");
        Fallible response;

        try {
            verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            service.deprecateSession(token);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished deprecateSession()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionResponse fetchPermissions(final AuthenticationToken token) {
        LOG.trace("Starting fetchPermissions()");
        PermissionResponse response;

        try {
            final Authentication authentication = verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            final List<Authorization> authorizations = service.findPermissions(authentication, token.getGroupId());
            response = new PermissionResponse(authorizations);
        } catch (IWSException e) {
            response = new PermissionResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchPermissions()");
        return response;
    }
}
