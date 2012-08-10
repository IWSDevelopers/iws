/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.AuthenticationResultTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses;

import com.gargoylesoftware.base.testing.EqualsTester;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.data.AuthenticationToken;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Test for the AuthenticationResponse Object.
 * 
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection SuppressionAnnotation
 */
public class AuthenticationResultTest {

    @Test
    public void testClassFlow() {
        // Test preconditions
        final AuthenticationToken token = new AuthenticationToken("KEY");
        final IWSError error = IWSErrors.VERIFICATION_ERROR;
        final String message = "ERROR";

        // Test Objects
        final AuthenticationResponse result = new AuthenticationResponse(token);
        final AuthenticationResponse same = new AuthenticationResponse();
        final AuthenticationResponse diff = new AuthenticationResponse(error, message);
        same.setToken(token);

        // Assertion Checks
        assertThat(result, is(same));
        assertThat(result, is(not(diff)));
        assertThat(result, is(notNullValue()));
        assertThat(result.isOk(), is(true));
        assertThat(result.getToken(), is(token));
        assertThat(result.getError(), is(IWSErrors.SUCCESS));
        assertThat(result.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(result.toString(), is("AuthenticationResponse[token=AuthenticationToken[token=KEY],error=IWSError[error=0,description=SUCCESS],message=OK]"));
        assertThat(result.hashCode(), is(-545849611));
        assertThat(result.hashCode(), is(same.hashCode()));
        assertThat(result.hashCode(), is(not(diff.hashCode())));
        assertThat(diff.getError(), is(error));
        assertThat(diff.getMessage(), is(message));
        //noinspection ResultOfObjectAllocationIgnored
        new EqualsTester(result, same, diff, null);
    }
}