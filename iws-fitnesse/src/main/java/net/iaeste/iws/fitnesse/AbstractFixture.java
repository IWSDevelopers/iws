/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.AbstractFixture
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

import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
abstract class AbstractFixture<T extends Fallible> implements Fixture {

    protected T response = null;
    protected String testId = null;
    protected String testCase = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void testId(final String str) {
        testId = str;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void testCase(final String str) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequestOk() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.isOk();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer errorCode() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.getError().getError();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String errorMessage() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        response = null;
        testId = null;
        testCase = null;
    }
}
