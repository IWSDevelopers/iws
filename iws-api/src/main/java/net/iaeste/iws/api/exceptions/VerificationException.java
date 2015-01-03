/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.exceptions.VerificationException
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.exceptions;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;

/**
 * Objects, which are used as input parameters, all implements the Verifiable
 * Interface, which throws this Exception, if there lacks important information
 * required for the processing.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class VerificationException extends IWSException {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * and the only information available is the type of error, and a message
     * describing the error.
     *
     * @param message  Specific message, regarding the problem
     * @see IWSException
     * @see IWSErrors#VERIFICATION_ERROR
     */
    public VerificationException(final String message) {
        super(IWSErrors.VERIFICATION_ERROR, message);
    }

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * caused by an underlying Exception. In this case, this Exception serves
     * as a wrapper around the underlying Exception, to avoid that higher
     * layers has to deal with more specific problems.
     *
     * @param cause    The specific cause of the problem
     */
    public VerificationException(final Throwable cause) {
        super(IWSErrors.VERIFICATION_ERROR, cause);
    }
}
