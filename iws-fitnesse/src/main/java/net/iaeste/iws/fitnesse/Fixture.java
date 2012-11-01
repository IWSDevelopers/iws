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

import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.Fallible;

/**
 * Fixture Functionality, that all Fixtures must implement. The methods dealing
 * with error information cannot be extracted into an Abstract class, since the
 * Fixture implementation is using reflection, with some restrictions setup.
 *
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
interface Fixture {

    /**
     * To better be able to identify the test case, this id is not used.
     */
    void testId(String str);

    /**
     * To give test cases a name that can describe them - The name is not used.
     */
    void testCase(String str);

    /**
     * Display method to display if the request went well or not. All responses
     * implements the {@code Fallible} Interface, defining the status if the
     * request.
     *
     * @return True if request was successful, otherwise false
     */
    boolean isRequestOk();

    /**
     * Display method to display the actual error code of the response object
     * resulting from the request. If the request was successful, then the value
     * will be {@code net.iaeste.iws.api.constants.IWSErrors#SUCCESS}.
     *
     * @return IWS Error Code
     */
    Integer errorCode();

    /**
     * Display method to display the actual error message of the response object
     * resulting from the request. If the request was sucessful, then the value
     * will be {@code net.iaeste.iws.api.constants.IWSConstants#SUCCESS}.
     *
     * @return Status message
     */
    String errorMessage();

    /**
     * Fixture uses this method once all setters has been invoked, to make the
     * call to the System Under Test (SUT). Once completed, it will then call
     * the requested getters to display information.<br /> The method will make
     * the call to the Zobel bean, and set the result objects accordingly
     * (result & setFallible()). If an error occurred, then an exception of type
     * {@code StopTestException} will be thrown.
     *
     * @throws net.iaeste.iws.fitnesse.exceptions.StopTestException if a problem occurrred
     * @see <a href="http://fitnesse.org/Fixture.UserGuide.SliM.DecisionTable">Fixture.org</a>
     */
    void execute();

    /**
     * Fixture invokes this method between each iteration, i.e. after all the
     * output methods has been invoked and before the next set is invoked.<br />
     *   Please note, that the reset itself will not deprecate the current
     * Session. This is meant to avoid that a constant set of login/logout is
     * required.
     *
     * @see <a href="http://fitnesse.org/Fixture.UserGuide.SliM.DecisionTable">Fixture.org</a>
     */
    void reset();
}
