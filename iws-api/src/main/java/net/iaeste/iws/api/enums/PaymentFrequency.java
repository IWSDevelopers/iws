/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.PaymentFrequency
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

/**
 * PaymentFrequency Object for the Exchange Module.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection EnumeratedConstantNamingConvention
 */
public enum PaymentFrequency {

    /**
     * When the Payment frequency is daily.
     */
    D("Daily"),

    /**
     * When the Payment frequency is weekly.
     */
    W("Weekly"),

    /**
     * When the Payment frequency is monthly.
     */
    M("Monthly"),

    /**
     * When the Payment frequency is yearly.
     */
    Y("Yearly");

    private final String description;

    PaymentFrequency(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
