/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.IsAlive
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

import net.iaeste.iws.api.responses.AuthenticationResponse;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class IsAlive extends AbstractFixture<AuthenticationResponse> {

    // =========================================================================
    // Fixture Setters
    // =========================================================================

    // =========================================================================
    // Fixture Display methods
    // =========================================================================

    public String token() {
        final String token;

        final AuthenticationResponse response = getResponse();
        if ((response != null) && (response.getToken() != null)) {
            token = response.getToken().getToken();
        } else {
            token = "";
        }

        return token;
    }

    // =========================================================================
    // Standard Fixture methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        setResponse(createSession());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        super.reset();
        deprecateSession();
    }
}
