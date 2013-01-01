/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.IsAliveTest
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class IsAliveTest {

    @Test
    public void testInvalidCall() {
        final IsAlive cut = new IsAlive();

        // First we reset the object, and then we invoke the call
        cut.reset();
        cut.execute();

        // Verify that the result is the expected
        assertThat(cut.isRequestOk(), is(false));
        assertThat(cut.errorCode(), is(IWSErrors.VERIFICATION_ERROR.getError()));
        assertThat(cut.errorMessage(), is("Validation failed: {User Credentials=Missing or invalid value.}"));
    }

    @Test
    public void testCallMissingUsername() {
        final IsAlive cut = new IsAlive();
        cut.reset();
        cut.setPassword("austria");

        // Execute the test
        cut.execute();

        // Verify that the result is the expected
        assertThat(cut.isRequestOk(), is(false));
        assertThat(cut.errorCode(), is(IWSErrors.VERIFICATION_ERROR.getError()));
        assertThat(cut.errorMessage(), is("Validation failed: {User Credentials=Missing or invalid value.}"));
    }

    @Test
    public void testCallMissingPassword() {
        final IsAlive cut = new IsAlive();
        cut.reset();
        cut.setUsername("austria");

        // Execute the test
        cut.execute();

        // Verify that the result is the expected
        assertThat(cut.isRequestOk(), is(false));
        assertThat(cut.errorCode(), is(IWSErrors.VERIFICATION_ERROR.getError()));
        assertThat(cut.errorMessage(), is("Validation failed: {User Credentials=Missing or invalid value.}"));
    }

    @Test
    public void testInvalidCredentials() {
        final IsAlive cut = new IsAlive();
        cut.reset();
        cut.setUsername("hungary");
        cut.setPassword("hungary");

        // Execute the test
        cut.execute();

        // Verify that the result is the expected
        assertThat(cut.isRequestOk(), is(false));
        assertThat(cut.errorCode(), is(IWSErrors.AUTHORIZATION_ERROR.getError()));
        assertThat(cut.errorMessage(), is("No account for the user 'hungary' was found."));
    }

    @Test
    public void testValidCredentials() {
        final IsAlive cut = new IsAlive();
        cut.reset();
        cut.setUsername("austria");
        cut.setPassword("austria");

        // Execute the test
        cut.execute();

        assertThat(cut.isRequestOk(), is(true));
        assertThat(cut.errorCode(), is(IWSErrors.SUCCESS.getError()));
        assertThat(cut.errorMessage(), is(IWSConstants.SUCCESS));
        assertThat(cut.token(), is(not(nullValue())));
        assertThat(cut.token().length(), is(64));
        cut.deprecateSession();
    }
}
