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

import net.iaeste.iws.api.constants.IWSErrors;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class IsAliveTest {

    @Test
    public void testCall() {
        final IsAlive isAlive = new IsAlive();

        // First we reset the object, and then we invoke the call
        isAlive.reset();
        isAlive.execute();

        // Verify that the result is the expected
        assertThat(isAlive.isRequestOk(), is(false));
        assertThat(isAlive.errorCode(), is(IWSErrors.VERIFICATION_ERROR.getError()));
        assertThat(isAlive.errorMessage(), is("Username and Password must be defined, i.e. non-null."));
    }
}
