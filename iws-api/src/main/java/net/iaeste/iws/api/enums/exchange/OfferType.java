/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.exchange.OfferType
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

import java.util.EnumSet;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public enum OfferType {

    OPEN("Open", "", EnumSet.of(ExchangeType.AC, ExchangeType.IW, ExchangeType.COBE)),
    LIMITED("Limited", "-L", EnumSet.of(ExchangeType.AC, ExchangeType.IW, ExchangeType.COBE)),
    RESERVED("Reserved", "-R", EnumSet.of(ExchangeType.AC, ExchangeType.IW));

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;
    private final String type;
    private final Set<ExchangeType> exchangeTypes;

    OfferType(final String description, final String type, final Set<ExchangeType> exchangeTypes) {
        this.description = description;
        this.type = type;
        this.exchangeTypes = exchangeTypes;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Set<ExchangeType> getExchangeTypes() {
        return exchangeTypes;
    }
}
