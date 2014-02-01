/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.FetchOffersTest
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.constants.IWSErrors;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class FetchOffersTest {
    private FetchOffers cut;

    @Test
    public void testInvalidCall() {
        cut = new FetchOffers();
        cut.reset();

        // First we login
        cut.setUsernameAndPassword("austria", "incorrect");
        assertThat(cut.login(), is(false));

        cut.execute();

        assertThat(cut.isRequestOk(), is(false));
        assertThat(cut.errorCode(), is(IWSErrors.VERIFICATION_ERROR.getError()));
        assertThat(cut.errorMessage(), startsWith("Invalid Authentication Token provided."));
    }

    @Test
    @Ignore
    public void testFetchCall() {
        cut = new FetchOffers();
        cut.reset();

        // First we login
        cut.setUsernameAndPassword("austria@iaeste.at", "austria");
        assertThat(cut.login(), is(true));

        // Set fetch request type
        cut.setRequestType("ALL");

        // Try to fetch offers
        cut.execute();
        assertThat(cut.isRequestOk(), is(true));
        assertThat(cut.errorCode(), is(IWSErrors.SUCCESS.getError()));
        assertThat(cut.numberOfFetchedOffers(), is(4));
    }

    @After
    public void logout() {
        cut.deprecateSession();
        cut = null;
    }
}
