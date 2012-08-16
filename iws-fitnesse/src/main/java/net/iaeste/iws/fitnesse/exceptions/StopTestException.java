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
package net.iaeste.iws.fitnesse.exceptions;

import net.iaeste.iws.api.constants.IWSConstants;

/**
 * FitNesse will stop running, if an exception with the "StopTest" as part of
 * the name. Hence, this exception is called "StopTestException", and should
 * be thrown within the ZObEL FitNesse environment, to ensure proper handling
 * of problems.<br />
 * @see <a href="http://fitnesse.org/FitNesse.UserGuide.SliM.ExceptionHandling">FitNesse.org</a>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class StopTestException extends RuntimeException {

    /** @see IWSConstants#SERIAL_VERSION_UID */
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