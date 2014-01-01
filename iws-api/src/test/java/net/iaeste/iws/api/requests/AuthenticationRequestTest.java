/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.AuthenticationRequestTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import net.iaeste.iws.api.exceptions.VerificationException;
import org.junit.Test;

/**
 * Test for the AuthenticationRequest Object.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class AuthenticationRequestTest {

    @Test
    public void testClassFlow() {
        // Test preconditions
        final String username = "username";
        final String password = "password";

        // Test Objects
        final AuthenticationRequest result = new AuthenticationRequest(username, password);
        final AuthenticationRequest same = new AuthenticationRequest();
        final AuthenticationRequest diff = new AuthenticationRequest(null, null);
        same.setUsername(username);
        same.setPassword(password);

        // Assertion Checks
        result.verify();
        assertThat(result, is(same));
        assertThat(result, is(not(diff)));
        assertThat(result.getUsername(), is(username));
        assertThat(result.getPassword(), is(password));
        assertThat(result.toString(), is("AuthenticationRequest[username=username,password=password]"));
        assertThat(result.toString(), is(same.toString()));
        assertThat(result.toString(), is(not(diff.toString())));
        assertThat(result.hashCode(), is(1569819734));
        assertThat(result.hashCode(), is(same.hashCode()));
        assertThat(result.hashCode(), is(not(diff.hashCode())));
        //noinspection ResultOfObjectAllocationIgnored
        new EqualsTester(result, same, diff, null);
    }

    @Test(expected = VerificationException.class)
    public void testVerificationEmptyPassword() {
        final AuthenticationRequest request = new AuthenticationRequest("username", "");
        request.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerificationNullPassword() {
        final AuthenticationRequest request = new AuthenticationRequest("username", null);
        request.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerificationEmptyUsername() {
        final AuthenticationRequest request = new AuthenticationRequest("", "password");
        request.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerificationNullUserName() {
        final AuthenticationRequest request = new AuthenticationRequest(null, "");
        request.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerificationEmpty() {
        final AuthenticationRequest request = new AuthenticationRequest("", "");
        request.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerificationnull() {
        final AuthenticationRequest request = new AuthenticationRequest(null, null);
        request.verify();
    }
}
