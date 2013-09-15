/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.constants.IWSErrorTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.constants;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection ResultOfObjectAllocationIgnored
 */
public class IWSErrorTest {

    @Test
    public void testClassFlow() {
        // Test preconditions
        final int code = 1;
        final String description = "Description";

        // Test Objects
        final IWSError result = new IWSError(code, description);
        final IWSError same = new IWSError(code, description);
        final IWSError diff = new IWSError(2, "Different Description");

        // Assertion Checks
        assertThat(result, is(same));
        assertThat(result, is(not(diff)));
        assertThat(result.getError(), is(code));
        assertThat(result.getDescription(), is(description));
        assertThat(result.toString(), is("IWSError[error=1,description=Description]"));
        assertThat(result.toString(), is(same.toString()));
        assertThat(result.toString(), is(not(diff.toString())));
        assertThat(result.hashCode(), is(-56661044));
        assertThat(result.hashCode(), is(same.hashCode()));
        assertThat(result.hashCode(), is(not(diff.hashCode())));
        new EqualsTester(result, same, diff, null);
    }

    @Test
    public void testIWBaseErrors() {
        final IWSError result = new IWSError(1, "Description");

        assertThat(result, is(not(IWSErrors.SUCCESS)));
        assertThat(result, is(not(IWSErrors.ERROR)));
        assertThat(result, is(not(IWSErrors.FATAL)));
        assertThat(result, is(not(IWSErrors.NOT_IMPLEMENTED)));
        assertThat(result, is(not(IWSErrors.DATABASE_UNREACHABLE)));
        assertThat(result, is(not(IWSErrors.VERIFICATION_ERROR)));
        assertThat(result, is(not(IWSErrors.AUTHENTICATION_ERROR)));
        assertThat(result, is(not(IWSErrors.AUTHORIZATION_ERROR)));
    }
}
