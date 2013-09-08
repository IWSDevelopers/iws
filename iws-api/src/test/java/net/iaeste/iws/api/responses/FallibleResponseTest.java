/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.FallibleResponseTest
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.util.Fallible;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FallibleResponseTest {

    @Test
    public void testClassflow() {
        final Fallible response = new FallibleResponse();

        assertThat(response.isOk(), is(true));
        assertThat(response.getError(), is(IWSErrors.SUCCESS));
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
    }

    @Test
    public void testClassWithError() {
        final IWSError error = IWSErrors.AUTHORIZATION_ERROR;
        final String message = "Error message";
        final Fallible response = new FallibleResponse(error, message);

        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(error));
        assertThat(response.getMessage(), is(message));
    }

    @Test
    public void testStandardMethods() {
        final FallibleResponse obj = new FallibleResponse(IWSErrors.FATAL, "fatal");
        final FallibleResponse same = new FallibleResponse(IWSErrors.FATAL, "fatal");
        final FallibleResponse diff = new FallibleResponse();

        // Test Hashcodes
        assertThat(obj.hashCode(), is(-1592032781));
        assertThat(same.hashCode(), is(-1592032781));
        assertThat(diff.hashCode(), is(-1344310095));

        // Test ToString
        assertThat(obj.toString(), is(""));
        assertThat(same.toString(), is(""));
        assertThat(diff.toString(), is(""));

        // Test Equals
        new EqualsTester(obj, same, diff, null);
    }
}
