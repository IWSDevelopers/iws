/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IWS (iws-fitnesse)
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
 * Abstract FitNesse Methods.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public abstract class AbstractFitNesse<T extends Fallible> {

    // The Response Object with Fallible (Error) information
    protected T response = null;

    /**
     * Returns a true if Zobel could successfully complete the processing of the
     * request.
     *
     * @return True if successful, otherwise false
     */
    public boolean isRequestOk() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.isOk();
    }

    /**
     * Returns the internal Error Code for the given response.
     *
     * @return IWS Error Code
     */
    public Integer errorCode() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.getError().getError();
    }

    /**
     * Returns the message relating to the error from the Zobel processing
     * result. If an error occurred, then this contain the defails related to
     * the error.
     *
     * @return Status message
     */
    public String errorMessage() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.getMessage();
    }

    // ========================================================================
    // Abstract methods, which all sub-classes must implement
    // ========================================================================

    /**
     * FitNesse uses this method once all setters has been invoked, to make the
     * call to the System Under Test (SUT). Once completed, it will then call
     * the requested getters to display information.<br /> The method will make
     * the call to the Zobel bean, and set the result objects accordingly
     * (result & setFallible()). If an error occurred, then an exception of type
     * {@code StopTestException} will be thrown.
     *
     * @throws net.iaeste.iws.fitnesse.exceptions.StopTestException if EJB problems occurrred
     * @see <a href="http://fitnesse.org/FitNesse.UserGuide.SliM.DecisionTable">FitNesse.org</a>
     */
    abstract void execute() throws StopTestException;

    /**
     * FitNesse invokes this method between each iteration, i.e. after all the
     * output methods has been invoked and before the next set is invoked.
     *
     * @see <a href="http://fitnesse.org/FitNesse.UserGuide.SliM.DecisionTable">FitNesse.org</a>
     */
    abstract void reset();
}
