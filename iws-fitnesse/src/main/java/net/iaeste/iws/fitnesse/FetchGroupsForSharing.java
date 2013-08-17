/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.FetchGroupsForSharing
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
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchGroupsForSharing extends AbstractFixture<FetchGroupsForSharingResponse> {

    private final Exchange exchange = new ExchangeCaller();

    public void fetchGroupsForSharing() {
        execute();
    }

    public int numberOfGroups() {
        return getResponse() == null ? -1 : getResponse().getGroups().size();
    }

    public String printGroup(final int groupIndex) {
        final String retVal;

        if (getResponse() == null) {
            retVal = "no response";
        } else if ((groupIndex < 1) || (groupIndex > numberOfGroups())) {
            retVal = "no group for given index";
        } else {
            retVal = getResponse().getGroups().get(groupIndex - 1).toString();
        }

        return retVal;
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(exchange.fetchGroupsForSharing(getToken()));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();
    }
}
