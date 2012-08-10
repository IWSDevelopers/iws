/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.exceptions.IWSException
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
import net.iaeste.iws.api.constants.IWSError;

/**
 * Basic Exception, which all other exceptions in the system should inherit
 * from. It may not be thrown directly.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class IWSException extends RuntimeException {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** Given base error. */
    private final IWSError error;

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * and the only information available is the type of error, and a message
     * describing the error.
     *
     * @param error    IW-Base Error
     * @param message  Specific message, regarding the problem
     */
    public IWSException(final IWSError error, final String message) {
        super(message);

        this.error = error;
    }

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * caused by an underlying Exception. In this case, this Exception serves
     * as a wrapper around the underlying Exception, to avoid that higher
     * layers has to deal with more specific problems.
     *
     * @param error    IWS Error
     * @param cause    The specific cause of the problem
     */
    public IWSException(final IWSError error, final Throwable cause) {
        super(cause);

        this.error = error;
    }

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * which is caused by an underlying Exception, but more information is
     * available to improve the error handling. In this case, this Exception
     * serves as a wrapper around the underlying Exception, to avoid that
     * higher layers has to deal with more specific problems.
     *
     * @param error    IWS Error
     * @param message  Specific message, regarding the problem
     * @param cause    The specific cause of the problem
     */
    public IWSException(final IWSError error, final String message, final Throwable cause) {
        super(message, cause);

        this.error = error;
    }

    /**
     * Returns the IWS Error.
     *
     * @return IWS Error
     */
    public IWSError getError() {
        return error;
    }
}