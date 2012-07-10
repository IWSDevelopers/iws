/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.security.jaas.IWRole
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.security.jaas;

import java.security.Principal;

/**
 * Custom implementation of {@link Principal}
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @see IWSLoginModule
 * @since 1.7
 */
public class IWSRole implements Principal {

    String roleName;

    public IWSRole(String defaultRole) {
        this.roleName = defaultRole;
    }

    @Override
    public String getName() {
        return roleName;
    }
}
