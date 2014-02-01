/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import org.junit.Test;

import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 * @noinspection ResultOfObjectAllocationIgnored
 */
public final class DeleteOfferRequestTest {

    @Test
    public void testClassflow() {
        final String offerId = UUID.randomUUID().toString();

        // Create a few Objects to test
        final DeleteOfferRequest request1 = new DeleteOfferRequest(offerId);
        final DeleteOfferRequest request2 = new DeleteOfferRequest();
        final DeleteOfferRequest request3 = new DeleteOfferRequest();

        // Test that Objects have different instances, but same content
        request2.setOfferId(offerId);
        assertThat(request1, is(not(sameInstance(request2))));
        assertThat(request1, is(request2));
        assertThat(request1, is(not(request3)));
        assertThat(request1.getOfferId(), is(offerId));
        assertThat(request3.getOfferId(), is(nullValue()));

        // Compare the results of the validation
        assertThat(request1.validate().isEmpty(), is(true));
        assertThat(request3.validate().isEmpty(), is(false));
        assertThat(request3.validate().containsKey("offerId"), is(true));
        assertThat(request3.validate().get("offerId"), is("The field may not be null."));

        // Compare results of the hashCode & toString methods
        assertThat(request1.hashCode(), is(request2.hashCode()));
        assertThat(request1.hashCode(), is(not(request3.hashCode())));
        assertThat(request1.toString(), is(request2.toString()));
        assertThat(request1.toString(), is(not(request3.toString())));

        new EqualsTester(request1, request2, request3, null);
    }
}
