/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.Fallible
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.constants.IWSError;

import java.io.Serializable;

/**
 * All Result Object must implement this functionality, since it contains the
 * error information.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface Fallible extends Serializable {

    /**
     * Returns true of the result was successfully completed, otherwise if an
     * error occurred, a false is returned.
     *
     * @return True if success, otherwise false
     */
    boolean isOk();

    /**
     * Returns the current {@code IWSError} object, if an error occurred,
     * otherwise it will return a null.
     *
     * @return Error object or null.
     */
    IWSError getError();

    /**
     * Returns the more detailed message for the current error, if an error
     * occurred, otherwise it will return a null.
     *
     * @return Error message or null.
     */
    String getMessage();
}
