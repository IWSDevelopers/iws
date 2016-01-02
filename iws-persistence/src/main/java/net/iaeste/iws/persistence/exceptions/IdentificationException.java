/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.exceptions.IdentificationException
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.exceptions;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;

/**
 * Special variant of the Persistence Exception, to deal with Identification
 * problems.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class IdentificationException extends PersistenceException {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * and the only information available is the type of error, and a message
     * describing the error.
     *
     * @param message  Specific message, regarding the problem
     */
    public IdentificationException(final String message) {
        super(IWSErrors.OBJECT_IDENTIFICATION_ERROR, message);
    }

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * where JPA threw either an exception as there were either non or multiple
     * results, where a single result was expected.
     *
     * @param message  Specific message, regarding the problem
     * @param cause    The specific cause of the problem
     */
    public IdentificationException(final String message, final Throwable cause) {
        super(IWSErrors.OBJECT_IDENTIFICATION_ERROR, message, cause);
    }
}
