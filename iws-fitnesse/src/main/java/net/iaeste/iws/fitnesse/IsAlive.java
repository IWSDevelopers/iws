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
import net.iaeste.iws.client.AccessClient;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class IsAlive extends AbstractFixture<AuthenticationResponse> {

    private final Access access = new AccessClient();
    private String username = null;
    private String password = null;

    // =========================================================================
    // Fixture Setters
    // =========================================================================

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    // =========================================================================
    // Fixture Display methods
    // =========================================================================

    public String token() {
        final String token;

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

    /** {@inheritDoc} */
    @Override
    public void execute() {
        final AuthenticationRequest request = buildRequest();

        response = access.generateSession(request);
    }

    /** {@inheritDoc} */
    @Override
    public void reset() {
        super.reset();

        username = null;
        password = null;
    }

    // =========================================================================
    // Internal methods
    // =========================================================================

    private AuthenticationRequest buildRequest() {
        final AuthenticationRequest request = new AuthenticationRequest();

        request.setUsername(username);
        request.setPassword(password);

        return request;
    }
}
