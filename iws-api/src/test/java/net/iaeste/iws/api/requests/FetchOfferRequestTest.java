/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchOffersRequestTest
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class FetchOfferRequestTest {
    @Test
    public void testEmptyConstructor() {
        final FetchOffersRequest request = new FetchOffersRequest();
        Assert.assertThat(request, is(not(nullValue())));
        Assert.assertThat(request.getFetchType(), is(not(nullValue())));
        Assert.assertThat(request.getFetchType(), is(FetchOffersRequest.FetchType.ALL));
        Assert.assertThat(request.getOffers(), is(not(nullValue())));
        Assert.assertThat(request.getOffers().size(), is(0));
    }

    @Test
    public void testConstructorEmptyList() {
        final FetchOffersRequest request = new FetchOffersRequest(new ArrayList<Long>(0));
        Assert.assertThat(request, is(not(nullValue())));
        Assert.assertThat(request.getFetchType(), is(not(nullValue())));
        Assert.assertThat(request.getFetchType(), is(FetchOffersRequest.FetchType.BY_ID));
        Assert.assertThat(request.getOffers(), is(not(nullValue())));
        Assert.assertThat(request.getOffers().size(), is(0));
    }

    @Test
    public void testConstructorList() {
        final ArrayList<Long> offerIds = new ArrayList<>(0);
        offerIds.add(1L);
        offerIds.add(2L);
        final FetchOffersRequest request = new FetchOffersRequest(offerIds);
        Assert.assertThat(request, is(not(nullValue())));
        Assert.assertThat(request.getFetchType(), is(not(nullValue())));
        Assert.assertThat(request.getFetchType(), is(FetchOffersRequest.FetchType.BY_ID));
        Assert.assertThat(request.getOffers(), is(not(nullValue())));
        Assert.assertThat(request.getOffers().size(), is(offerIds.size()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testModifingOfferList() {
        final ArrayList<Long> offerIds = new ArrayList<>(0);
        offerIds.add(1L);
        offerIds.add(2L);
        final FetchOffersRequest request = new FetchOffersRequest(offerIds);
        request.getOffers().add(3L);
    }
}
