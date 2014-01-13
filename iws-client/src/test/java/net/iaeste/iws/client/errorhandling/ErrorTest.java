/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.errorhandling.ErrorTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.errorhandling;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.client.AccessClient;
import org.junit.Test;

/**
 * The IWSError Object is causing a problem due to the fact that it is both an
 * Object and a Constant. Meaning, that comparison using either "==" or
 * ".equals()" are working. The reference comparison ("=="), is incorrect, since
 * it only checks that the pointer references are the same. For this reason, the
 * IWS Exception is using deep copying to return a true copy, which will make
 * the reference comparison fail.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ErrorTest {

    private final Access access = new AccessClient();

    @Test
    public void testErrorCodeComparison() {
        final AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("alfa@centaury.space");
        request.setPassword("Galaxy Quest");
        final AuthenticationResponse response = access.generateSession(request);

        // Now run the assert checks
        assertThat("Test should fail, comparing addresses is not good when checking if Objects are identical", response.getError() == IWSErrors.AUTHENTICATION_ERROR, is(false));
        assertThat(response.getError().equals(IWSErrors.AUTHENTICATION_ERROR), is(true));
    }
}
