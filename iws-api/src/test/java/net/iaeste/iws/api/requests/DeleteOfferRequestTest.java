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
 * @noinspection ResultOfObjectAllocationIgnored
 */
public final class DeleteOfferRequestTest {

    @Test
    public void testClassFlow() {
        final Long offerId = 1L;

        final DeleteOfferRequest request = new DeleteOfferRequest();
        request.setOfferId(offerId);
        request.verify();

        assertThat(request.getOfferId(), is(offerId));
    }

    @Test(expected = VerificationException.class)
    public void testVerificationNullId() {
        final DeleteOfferRequest request = new DeleteOfferRequest(null);
        request.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerificationInvalidId() {
        final DeleteOfferRequest request = new DeleteOfferRequest(-1L);
        request.verify();
    }

    @Test
    public void testStandardMethods() {
        final DeleteOfferRequest request = new DeleteOfferRequest(1L);
        final DeleteOfferRequest same = new DeleteOfferRequest(1L);
        final DeleteOfferRequest diff = new DeleteOfferRequest(2L);

        // Test the HashCode
        assertThat(request.hashCode(), is(1));
        assertThat(request.hashCode(), is(same.hashCode()));
        assertThat(request.hashCode(), is(not(diff.hashCode())));

        assertThat(request.toString(), is("DeleteOfferRequest{offer=1}"));
        assertThat(request.toString(), is(same.toString()));
        assertThat(request.toString(), is(not(diff.toString())));

        // Perform the testing of the equals method
        new EqualsTester(request, same, diff, null);
    }
}
