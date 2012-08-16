/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IWS (iws-fitnesse)
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.fitnesse.callers.AccessCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class IsAlive extends AbstractFitNesse<AuthenticationResponse> {

    private final Access access = new AccessCaller();

    // ========================================================================
    // Standard FitNesse methods
    // ========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws StopTestException {
        final AuthenticationRequest request = new AuthenticationRequest();
        response = access.generateSession(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        response = null;
    }
}