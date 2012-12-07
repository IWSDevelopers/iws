/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.exceptions.StopTestException
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse.exceptions;

import net.iaeste.iws.api.constants.IWSConstants;

/**
 * Fixture will stop running, if an exception with the "StopTest" as part of
 * the name. Hence, this exception is called "StopTestException", and should
 * be thrown within the ZObEL Fixture environment, to ensure proper handling
 * of problems.<br />
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @see <a href="http://fitnesse.org/Fixture.UserGuide.SliM.ExceptionHandling">Fixture.org</a>
 */
public class StopTestException extends RuntimeException {

    /**
     * @see IWSConstants#SERIAL_VERSION_UID
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    public StopTestException(final String message) {
        super(message);
    }

    public StopTestException(final Throwable cause) {
        super(cause);
    }

    public StopTestException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
