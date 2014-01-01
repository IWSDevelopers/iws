/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.ProcessPublishGroup
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.requests.exchange.PublishGroupRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ProcessPublishGroup extends AbstractFixture<Fallible> {

    private final Exchange exchange = new ExchangeCaller();
    private PublishGroupRequest request = new PublishGroupRequest();

    public void processPublishGroup() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(exchange.processPublishGroup(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
