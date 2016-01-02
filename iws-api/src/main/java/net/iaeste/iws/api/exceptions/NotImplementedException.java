/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.exceptions.NotImplementedException
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
 * Exception to be thrown, in case the implementation is missing. The usage of
 * this exception should not become the norm, rather the exception for features
 * which have been added to the external API, but haven't been implemented
 * yet.<br />
 *   Class is made deprecated by default, since it helps to outline the areas
 * where we have not completed work.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 * @deprecated Please implement the logic
 */
@Deprecated
public final class NotImplementedException extends IWSException {

    /** @see IWSConstants#SERIAL_VERSION_UID */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Default Constructor.
     *
     * @param message  Specific message, regarding the problem
     * @deprecated Please implement the logic
     * @see IWSException
     * @see IWSErrors#VERIFICATION_ERROR
     */
    @Deprecated
    public NotImplementedException(final String message) {
        super(IWSErrors.NOT_IMPLEMENTED, message);
    }
}
