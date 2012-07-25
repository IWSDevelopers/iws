/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.AccessService
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
import net.iaeste.iws.api.data.Authorization;
import net.iaeste.iws.api.exceptions.NotImplementedException;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class AccessService {

    private final EntityManager entityManager;

    public AccessService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public AuthenticationToken generateSession(final String username, final String password) {
        throw new NotImplementedException("The 'generateSession' method is not yet implemented");
    }

    public void deprecateSession(final AuthenticationToken token) {
        throw new NotImplementedException("The 'deprecateSession' method is not yet implemented");
    }

    public List<Authorization> findPermissions(final AuthenticationToken token) {
        throw new NotImplementedException("The 'findPermissions' method is not yet implemented");
    }
}
