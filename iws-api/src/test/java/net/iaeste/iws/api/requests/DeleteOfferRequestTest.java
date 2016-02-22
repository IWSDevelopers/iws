/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
