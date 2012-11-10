/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.CommitteeService
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

import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.RegionalGroupRequest;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.persistence.Authentication;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class CommitteeService {

    public Fallible createCommittee(final Authentication authentication, final CommitteeRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public Fallible manageCommittee(final Authentication authentication, final CommitteeRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public Fallible upgradeCommittee(final Authentication authentication, final CommitteeRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public Fallible manageInternationalGroup(final Authentication authentication, final InternationalGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public Fallible createRegionalGroup(final Authentication authentication, final RegionalGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public Fallible manageRegionalGroup(final Authentication authentication, final RegionalGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }
}
