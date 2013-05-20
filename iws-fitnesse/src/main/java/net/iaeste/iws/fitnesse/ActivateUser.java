/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.ActivateUser
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

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ActivateUser extends AbstractFixture<Fallible> {

    private final Administration administration = new AdministrationCaller();
    private String activationString = null;

    public void setActivationString(final String activationString) {
        this.activationString = activationString;
    }

    public void activateUser() {
        execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(administration.activateUser(activationString));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        activationString = null;
    }
}
