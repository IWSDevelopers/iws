/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.exchange.PaymentFrequency
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums.exchange;

/**
 * PaymentFrequency Object for the Exchange Module.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public enum PaymentFrequency {

    /**
     * When the Payment frequency is daily.
     */
    DAILY("Daily"),

    /**
     * When the Payment frequency is weekly.
     */
    WEEKLY("Weekly"),

    /**
     * When the Payment frequency is monthly.
     */
    MONTHLY("Monthly"),

    /**
     * When the Payment frequency is yearly.
     */
    YEARLY("Yearly");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    PaymentFrequency(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
