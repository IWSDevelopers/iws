/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.PaymentFrequencyTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
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
