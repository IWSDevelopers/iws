/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.DeleteOfferRequestTest
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

import com.gargoylesoftware.base.testing.EqualsTester;
import net.iaeste.iws.api.exceptions.VerificationException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class DeleteOfferRequestTest {
    private static final String invalidOfferRefNo = "AT-2012-@#$1";
    private static final String offerRefNo1 = "AT-2012-0001";
    private static final String offerRefNo2 = "AT-2012-0002";

    @Test
    public void testClassFlow() {
        final DeleteOfferRequest request = new DeleteOfferRequest();
        request.setOfferRefNo(offerRefNo1);
        request.verify();

        assertThat(request.getOfferRefNo(), is(offerRefNo1));
    }

    @Test(expected = VerificationException.class)
    public void testVerificationNullId() {
        final DeleteOfferRequest request = new DeleteOfferRequest(null);
        request.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerificationInvalidId() {

        final DeleteOfferRequest request = new DeleteOfferRequest(invalidOfferRefNo);
        request.verify();
    }

    @Test
    public void testStandardMethods() {
        final DeleteOfferRequest request = new DeleteOfferRequest(offerRefNo1);
        final DeleteOfferRequest same = new DeleteOfferRequest(offerRefNo1);
        final DeleteOfferRequest diff = new DeleteOfferRequest(offerRefNo2);

        // Test the HashCode
        assertThat(request.hashCode(), is(offerRefNo1.hashCode()));
        assertThat(request.hashCode(), is(same.hashCode()));
        assertThat(request.hashCode(), is(not(diff.hashCode())));

        assertThat(request.toString(), is(String.format("DeleteOfferRequest{offer=%s}", offerRefNo1)));
        assertThat(request.toString(), is(same.toString()));
        assertThat(request.toString(), is(not(diff.toString())));

        // Perform the testing of the equals method
        new EqualsTester(request, same, diff, null);
    }
}
