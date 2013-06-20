/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.AccessClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.util.Fallible;

import java.io.Serializable;

/**
 * Simple Client to work with the IWS Acess functionality. The access to
 * be used is controlled via the Factory, which loads the settings from a
 * properties file.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class AccessClient implements Access {

    private final Access client;

    /**
     * Default Constructor.
     */
    public AccessClient() {
        client = ClientFactory.getInstance().getAccessImplementation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        return client.generateSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible requestResettingSession(final AuthenticationRequest request) {
        return client.requestResettingSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse resetSession(final String resetSessionToken) {
        return client.resetSession(resetSessionToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> Fallible saveSessionData(final AuthenticationToken token, final SessionDataRequest<T> request) {
        return client.saveSessionData(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> SessionDataResponse<T> readSessionData(final AuthenticationToken token) {
        return client.readSessionData(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deprecateSession(final AuthenticationToken token) {
        return client.deprecateSession(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible forgotPassword() {
        return client.forgotPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible resetPassword(final String resetPasswordToken, final String newPassword) {
        return client.resetPassword(resetPasswordToken, newPassword);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible updatePassword(final AuthenticationToken token, final String newPassword) {
        return client.updatePassword(token, newPassword);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPermissionResponse fetchPermissions(final AuthenticationToken token) {
        return client.fetchPermissions(token);
    }
}
