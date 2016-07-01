/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.core;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.responses.VersionResponse;
import net.iaeste.iws.core.services.AccessService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

    private static final Logger LOG = LoggerFactory.getLogger(AccessController.class);

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
    public VersionResponse version() {
        VersionResponse response;

        try {
            // Please note, that the information presented here is deliberately
            // not including any DB calls, since a DOS attack against this
            // request can be rejected by the Application Server, but if it also
            // includes DB invocations, it may influence performance as well.
            response = new VersionResponse();
            response.setHostname(InetAddress.getLocalHost().getHostName());
            response.setAddress(InetAddress.getLocalHost().getHostAddress());
            response.setVersion(IWSConstants.IWS_VERSION);
        } catch (UnknownHostException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.warn(e.getMessage(), e);
            response = new VersionResponse(IWSErrors.FATAL, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        AuthenticationResponse response;

        try {
            verify(request, AUTHENTICATION_REQUEST_ERROR);

            final AccessService service = factory.prepareAuthenticationService();
            response = service.generateSession(request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new AuthenticationResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse requestResettingSession(final AuthenticationRequest request) {
        FallibleResponse response;

        try {
            verify(request, AUTHENTICATION_REQUEST_ERROR);

            final AccessService service = factory.prepareAuthenticationService();
            service.requestResettingSession(request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new AuthenticationResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse resetSession(final String resetSessionToken) {
        AuthenticationResponse response;

        try {
            verifyCode(resetSessionToken, "The ResetSessionToken is invalid.");
            final AccessService service = factory.prepareAuthenticationService();
            final AuthenticationToken token = service.resetSession(resetSessionToken);
            response = new AuthenticationResponse(token);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new AuthenticationResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> FallibleResponse saveSessionData(final AuthenticationToken token, final SessionDataRequest<T> request) {
        FallibleResponse response;

        try {
            verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            service.saveSessionData(token, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> SessionDataResponse<T> readSessionData(final AuthenticationToken token) {
        SessionDataResponse<T> response;

        try {
            verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            response = service.fetchSessionData(token);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new SessionDataResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse verifySession(final AuthenticationToken token) {
        FallibleResponse response;

        try {
            verifyPrivateAccess(token);
            response = new FallibleResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse deprecateSession(final AuthenticationToken token) {
        FallibleResponse response;

        try {
            verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            service.deprecateSession(token);
            response = new FallibleResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse forgotPassword(final String username) {
        FallibleResponse response;

        try {
            verifyEmail(username);
            final AccessService service = factory.prepareAuthenticationService();
            service.forgotPassword(username);
            response = new FallibleResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse resetPassword(final Password password) {
        FallibleResponse response;

        try {
            verify(password);

            final AccessService service = factory.prepareAuthenticationService();
            service.resetPassword(password);
            response = new FallibleResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FallibleResponse updatePassword(final AuthenticationToken token, final Password password) {
        FallibleResponse response;

        try {
            verify(password);
            final Authentication authentication = verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            service.updatePassword(authentication, password);
            response = new FallibleResponse();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPermissionResponse fetchPermissions(final AuthenticationToken token) {
        FetchPermissionResponse response;

        try {
            final Authentication authentication = verifyPrivateAccess(token);

            final AccessService service = factory.prepareAuthenticationService();
            response = service.findPermissions(authentication, token.getGroupId());
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchPermissionResponse(e.getError(), e.getMessage());
        }

        return response;
    }
}
