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

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.exceptions.VerificationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ProcessOfferRequestTest {

    private Offer validUpdateOffer;
    private Offer validCreateOffer;

    @Before
    public void setUp() {
        this.validUpdateOffer = OfferTestUtility.getMinimalOffer();
        this.validUpdateOffer.setId(null);
        this.validCreateOffer = OfferTestUtility.getMinimalOffer();
        this.validUpdateOffer.setId(1L);
    }

    @Test(expected = VerificationException.class)
    public void testVerifyEmptyRequest() {
        final ProcessOfferRequest requestWithInvalidOffer = new ProcessOfferRequest(null);
        requestWithInvalidOffer.verify();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testVerifyValidUpdateRequest() {
        final ProcessOfferRequest request = new ProcessOfferRequest(validUpdateOffer);
        request.verify();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testVerifyValidCreateRequest() {
        final ProcessOfferRequest requestWithInvalidOffer = new ProcessOfferRequest(validCreateOffer);
        requestWithInvalidOffer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerifyEditRequestWithInvalidOffer() {
        validUpdateOffer.setRefNo(null); // dto is not valid
        final ProcessOfferRequest requestWithInvalidOffer = new ProcessOfferRequest(validUpdateOffer);
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
        ProcessOfferRequest request = new ProcessOfferRequest(setNullOffer);

        Assert.assertThat(request, is(not(nullValue())));
        Assert.assertThat(setNullOffer, is(nullValue()));
        Assert.assertThat(request.getOffer(), is(not(nullValue())));

        request = new ProcessOfferRequest();
        request.setOffer(setNullOffer);

        Assert.assertThat(request, is(not(nullValue())));
        Assert.assertThat(setNullOffer, is(nullValue()));
        Assert.assertThat(request.getOffer(), is(not(nullValue())));
    }
}
