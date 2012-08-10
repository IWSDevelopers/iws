/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.CommonController
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.Verifiable;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
class CommonController {

    protected void verifyAccess(final AuthenticationToken token, final Permission permission) {
        verify(token, "Invalid Authentication Token provided.");
        // ToDO; Load User Permission, and check the given permission against the list
    }

    /**
     * Internal method to test verifiable objects. If the object is undefined,
     * i.e. null a VerificationException with the provided message is thrown,
     * otherwise the verify method is called on the verifiable object.
     *
     * @param verifiable Object to verify
     * @param message    Error message, if the verifiable object is null
     * @see net.iaeste.iws.api.requests.Verifiable#verify()
     */
    protected void verify(final Verifiable verifiable, final String message) {
        if (verifiable == null) {
            throw new VerificationException(message);
        }
        verifiable.verify();
    }
}
