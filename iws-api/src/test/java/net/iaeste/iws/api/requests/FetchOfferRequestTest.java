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

import java.util.EnumSet;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class FetchOfferRequestTest {
    @Test
    public void testConstructor() {
        final Iterable<FetchOffersRequest.FetchType> fetchTypes = EnumSet.allOf(FetchOffersRequest.FetchType.class);
        for (final FetchOffersRequest.FetchType fetchType : fetchTypes) {
            final FetchOffersRequest request = new FetchOffersRequest(fetchType);
            Assert.assertThat(request, is(not(nullValue())));
            Assert.assertThat(request.getFetchType(), is(not(nullValue())));
            Assert.assertThat(request.getFetchType(), is(fetchType));
        }
    }
}
