/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchOfferRequestTest
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

import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import org.junit.Test;

import java.util.EnumSet;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection ObjectAllocationInLoop
 */
public final class FetchOfferRequestTest {

    @Test
    public void testConstructor() {
        final Iterable<FetchType> fetchTypes = EnumSet.allOf(FetchType.class);

        for (final FetchType fetchType : fetchTypes) {
            final FetchOffersRequest request = new FetchOffersRequest(fetchType);
            assertThat(request, is(not(nullValue())));
            assertThat(request.getFetchType(), is(not(nullValue())));
            assertThat(request.getFetchType(), is(fetchType));
        }
    }
}
