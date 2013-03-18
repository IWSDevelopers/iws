/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.callers.AccessCaller
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse.callers;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.api.responses.PermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.client.AccessClient;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

import java.io.Serializable;

/**
 * The IWS FitNesse implementation of the API logic. The Class will attempt to
 * invoke the IWS Client module, and wrap all calls with an Exception check that
 * will throw a new {@code StopTestException} if an error occured - this is the
 * expected behaviour for the FitNesse tests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class AccessCaller implements Access {

    // The Client handles the IWS for us, we use use it
    private final Access caller = new AccessClient();

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse generateSession(final AuthenticationRequest request) {
        try {
            return caller.generateSession(request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible requestResettingSession(final AuthenticationRequest request) {
        try {
            return caller.requestResettingSession(request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse resetSession(final String resetSessionString) {
        try {
            return caller.resetSession(resetSessionString);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> Fallible saveSessionData(final AuthenticationToken token, final SessionDataRequest<T> request) {
        try {
            return caller.saveSessionData(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Serializable> SessionDataResponse<T> fetchSessionData(final AuthenticationToken token) {
        try {
            return caller.fetchSessionData(token);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deprecateSession(final AuthenticationToken token) {
        try {
            return caller.deprecateSession(token);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionResponse fetchPermissions(final AuthenticationToken token) {
        try {
            return caller.fetchPermissions(token);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }
}
