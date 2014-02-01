/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static net.iaeste.iws.core.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.core.services.AccessService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

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
 * @since   IWS 1.0
 */
public final class AccessController extends CommonController implements Access {

    private static final Logger log = LoggerFactory.getLogger(AccessController.class);

    private static final String AUTHENTICATION_REQUEST_ERROR = "The Authentication Request Object is undefined.";

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory  The ServiceFactory
     */
    public AccessController(final ServiceFactory factory) {
        super(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        if (log.isTraceEnabled()) {
            log.trace("Starting generateSession()");
        }
        AuthenticationResponse response;

        try {
            verify(request, AUTHENTICATION_REQUEST_ERROR);

            final AccessService service = factory.prepareAuthenticationService();
            response = service.generateSession(request);
        } catch (IWSException e) {
            response = new AuthenticationResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace("Finished generateSession()");
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible requestResettingSession(final AuthenticationRequest request) {
        if (log.isTraceEnabled()) {
            log.trace("Starting requestResettingSession()");
        }
        Fallible response;

        try {
            verify(request, AUTHENTICATION_REQUEST_ERROR);

            final AccessService service = factory.prepareAuthenticationService();
            service.requestResettingSession(request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new AuthenticationResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace("Finished requestResettingSession()");
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse resetSession(final String resetSessionToken) {
        if (log.isTraceEnabled()) {
            log.trace("Starting resetSession()");
        }
        AuthenticationResponse response;

        try {
            verifyCode(resetSessionToken, "The ResetSessionToken is invalid.");
            final AccessService service = factory.prepareAuthenticationService();
            final AuthenticationToken token = service.resetSession(resetSessionToken);
            response = new AuthenticationResponse(token);
        } catch (IWSException e) {
            response = new AuthenticationResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace("Finished resetSession()");
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> Fallible saveSessionData(final AuthenticationToken token, final SessionDataRequest<T> request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting saveSessionData()"));
        }
        Fallible response;

        try {
            verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            service.saveSessionData(token, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        log.trace(formatLogMessage(token, "Finished saveSessionData()"));
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> SessionDataResponse<T> readSessionData(final AuthenticationToken token) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting readSessionData()"));
        }
        SessionDataResponse<T> response;

        try {
            verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            response = service.fetchSessionData(token);
        } catch (IWSException e) {
            response = new SessionDataResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished readSessionData()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deprecateSession(final AuthenticationToken token) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting deprecateSession()"));
        }
        Fallible response;

        try {
            verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            service.deprecateSession(token);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished deprecateSession()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible forgotPassword(final String username) {
        if (log.isTraceEnabled()) {
            log.trace("Starting forgotPassword()");
        }
        Fallible response;

        try {
            final AccessService service = factory.prepareAuthenticationService();
            service.forgotPassword(username);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace("Finished forgotPassword()");
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible resetPassword(final String resetPasswordToken, final Password password) {
        if (log.isTraceEnabled()) {
            log.trace("Starting resetPassword()");
        }
        Fallible response;

        try {
            verifyCode(resetPasswordToken, "The ResetPassword Token is invalid.");
            verify(password);

            final AccessService service = factory.prepareAuthenticationService();
            service.resetPassword(resetPasswordToken, password);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace("Finished resetPassword()");
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible updatePassword(final AuthenticationToken token, final Password password) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting updatePassword()"));
        }
        Fallible response;

        try {
            verify(password);
            final Authentication authentication = verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            service.updatePassword(authentication, password);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished updatePassword()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPermissionResponse fetchPermissions(final AuthenticationToken token) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchPermissions()"));
        }
        FetchPermissionResponse response;

        try {
            final Authentication authentication = verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            response = service.findPermissions(authentication, token.getGroupId());
        } catch (IWSException e) {
            response = new FetchPermissionResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchPermissions()"));
        }
        return response;
    }
}
