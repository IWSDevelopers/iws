/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.ContactsService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.ContactsRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.NCsResponse;
import net.iaeste.iws.persistence.Authentication;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ContactsService {

    public NCsResponse fetchNCsList(final Authentication authentication) {
        throw new IWSException(IWSErrors.NOT_IMPLEMENTED, "Functionality is pending implementation.");
    }

    public ContactsResponse fetchContacts(final Authentication authentication, final ContactsRequest request) {
        throw new IWSException(IWSErrors.NOT_IMPLEMENTED, "Functionality is pending implementation.");
    }
}
