/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.FacultyService
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

import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.FacultyRequest;
import net.iaeste.iws.api.responses.FacultyResponse;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.6
 */
public class FacultyService {

    public void processFaculties(final AuthenticationToken token, final FacultyRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public FacultyResponse findFaculties(final AuthenticationToken token, final FacultyRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }
}
