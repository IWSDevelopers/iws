/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.ProcessOfferRequestTest
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

import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.exceptions.VerificationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection JUnitTestMethodWithNoAssertions
 */
public final class ProcessOfferRequestTest {

    private Offer validOffer = null;

    @Before
    public void setUp() {
        validOffer = OfferTestUtility.getMinimalOffer();
    }

    @Test(expected = VerificationException.class)
    public void testVerifyEmptyRequest() {
        final ProcessOfferRequest requestWithInvalidOffer = new ProcessOfferRequest(null);
        requestWithInvalidOffer.verify();
    }

    @Test
    public void testVerifyValidUpdateRequest() {
        final ProcessOfferRequest request = new ProcessOfferRequest(validOffer);
        request.verify();
    }

    @Test
    public void testVerifyValidCreateRequest() {
        final ProcessOfferRequest requestWithInvalidOffer = new ProcessOfferRequest(validOffer);
        requestWithInvalidOffer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerifyEditRequestWithInvalidOffer() {
        validOffer.setRefNo(null); // dto is not valid
        final ProcessOfferRequest requestWithInvalidOffer = new ProcessOfferRequest(validOffer);
        requestWithInvalidOffer.verify();
    }

    @Test
    public void testGetters() {
        final Offer initialUpdateOffer = OfferTestUtility.getMinimalOffer();
        final ProcessOfferRequest request = new ProcessOfferRequest(initialUpdateOffer);

        final Offer editOffer = request.getOffer();
        editOffer.setRefNo("CZ-2013-0666");

        Assert.assertThat(editOffer, is(not(nullValue())));
        Assert.assertThat(request, is(not(nullValue())));
        Assert.assertThat(request.getOffer(), is(not(nullValue())));
        Assert.assertThat(request.getOffer(), is(not(editOffer)));
        Assert.assertThat(request.getOffer(), is(initialUpdateOffer));
    }

    @Test
    public void testGettersNullOffer() {
        final Offer setNullOffer = null;
        final ProcessOfferRequest nullOfferRequest = new ProcessOfferRequest(setNullOffer);

        Assert.assertThat(nullOfferRequest, is(not(nullValue())));
        Assert.assertThat(setNullOffer, is(nullValue()));
        Assert.assertThat(nullOfferRequest.getOffer(), is(not(nullValue())));

        final ProcessOfferRequest emptyRequest = new ProcessOfferRequest();
        emptyRequest.setOffer(setNullOffer);

        Assert.assertThat(emptyRequest, is(not(nullValue())));
        Assert.assertThat(setNullOffer, is(nullValue()));
        Assert.assertThat(emptyRequest.getOffer(), is(not(nullValue())));
    }
}
