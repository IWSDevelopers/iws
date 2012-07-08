/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.mock.MockAccessController
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.mock;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.PermissionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

/**
 * Mock implementation of {@link Access}
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Mock
@ApplicationScoped
public class MockAccessController implements Access, Serializable {

    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private static final Logger LOG = LoggerFactory.getLogger(MockAccessController.class);

    @Override
    public AuthenticationResponse generateSession(AuthenticationRequest request) {
        throw new NotImplementedException("TBD");
    }

    @Override
    public Fallible deprecateSession(AuthenticationToken token) {
        throw new NotImplementedException("TBD");
    }

    @Override
    public PermissionResponse findPermissions(AuthenticationToken token) {
        throw new NotImplementedException("TBD");
    }
}
