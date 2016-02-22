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
package net.iaeste.iws.api.enums;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class PaymentFrequencyTest {

    @Test
    public void testGetDescription() {
        // Daily
        assertThat(PaymentFrequency.DAILY.name(), is("DAILY"));
        assertThat(PaymentFrequency.DAILY.getDescription(), is("Daily"));

        // Weekly
        assertThat(PaymentFrequency.WEEKLY.name(), is("WEEKLY"));
        assertThat(PaymentFrequency.WEEKLY.getDescription(), is("Weekly"));

        // Monthly
        assertThat(PaymentFrequency.MONTHLY.name(), is("MONTHLY"));
        assertThat(PaymentFrequency.MONTHLY.getDescription(), is("Monthly"));

        // Yearly
        assertThat(PaymentFrequency.YEARLY.name(), is("YEARLY"));
        assertThat(PaymentFrequency.YEARLY.getDescription(), is("Yearly"));
    }
}
