/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.ProcessOffersTest
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
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.constants.IWSErrors;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ProcessOffersTest {
    private ProcessOffers cut;
    private static final String TOKEN = "12345678901234567890123456789012";

    @Before
    public void setUp() {
        cut = new ProcessOffers();
        // First we reset the object, and then we invoke the call
        cut.reset();
    }

    @Test
    public void testNoToken() {
        cut.setOffer("minimal");
        cut.setToken(null);

        cut.execute();

        // Verify that the result is the expected
        assertThat(cut.isRequestOk(), is(false));
        assertThat(cut.errorCode(), is(IWSErrors.VERIFICATION_ERROR.getError()));
        assertThat(cut.errorMessage(), is("Invalid Authentication Token provided."));
    }

    @Test
    public void testShortToken() {
        cut.setOffer("minimal");
        cut.setToken(TOKEN.substring(0, 10));

        cut.execute();

        // Verify that the result is the expected
        assertThat(cut.isRequestOk(), is(false));
        assertThat(cut.errorCode(), is(IWSErrors.VERIFICATION_ERROR.getError()));
        assertThat(cut.errorMessage(), is("The Token is invalid, the content is an unsupported or unallowed cryptographical hash value."));
    }

    @Test
    public void test() {
        cut.setOffer("minimal");
        cut.setToken(TOKEN);

        cut.execute();

        // Verify that the result is the expected
        assertThat(cut.isRequestOk(), is(true));
        assertThat(cut.errorCode(), is(IWSErrors.SUCCESS.getError()));
        assertThat(cut.errorMessage(), is("OK"));
    }
}
