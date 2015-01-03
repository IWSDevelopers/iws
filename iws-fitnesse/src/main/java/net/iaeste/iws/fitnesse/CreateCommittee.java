/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.CreateCommittee
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

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.CommitteeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CreateCommittee  extends AbstractFixture<Fallible> {

    private final Committees committees = new CommitteeCaller();
    private CommitteeRequest request = new CommitteeRequest();

    public void createComittee() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(committees.processCommittee(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
