/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.exceptions.ExchangeException
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.common.exceptions;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class ExchangeException extends IWSException {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Default Constructor, for the case where a general error in the Exchange
     * module has arisen, and the only information available is the message
     * describing the error.
     *
     * @param message  Specific message, regarding the problem
     */
    public ExchangeException(final String message) {
        super(IWSErrors.GENERAL_EXCHANGE_ERROR, message);
    }

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * and the only information available is the type of error, and a message
     * describing the error.
     *
     * @param error    IWS Error
     * @param message  Specific message, regarding the problem
     */
    public ExchangeException(final IWSError error, final String message) {
        super(error, message);
    }

    /**
     * Default Constructor, for the case where a general error in the Exchange
     * module has arisen, and the only information available is the causing
     * error.
     *
     * @param cause    The specific cause of the problem
     */
    public ExchangeException(final Throwable cause) {
        super(IWSErrors.GENERAL_EXCHANGE_ERROR, cause);
    }
}
