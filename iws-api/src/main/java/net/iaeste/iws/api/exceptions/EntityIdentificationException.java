/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.exceptions.FakeOfferException
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
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class EntityIdentificationException extends IWSException {
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Default Constructor.
     *
     * @param message Specific message, regarding the problem
     * @see IWSException
     * @see net.iaeste.iws.api.constants.IWSErrors#VERIFICATION_ERROR
     */
    public EntityIdentificationException(final String message) {
        super(IWSErrors.ID_ERROR, message);
    }
}
