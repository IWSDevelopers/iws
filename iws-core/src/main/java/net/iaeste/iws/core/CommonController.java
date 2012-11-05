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
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.exceptions.PersistenceException;

/**
 * Common Controller, handles the default checks.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
class CommonController {

    private final AccessDao dao;

    CommonController(final AccessDao dao) {
        this.dao = dao;
    }

    /**
     * Checks the given Token to see if the user is known (authenticated) and
     * allowed (authorized) to invoke the desired request. Please note, that
     * although a user is allowed to perform a request, it doesn't mean that
     * the user is allowed to process the desired data. This check must be made
     * additionally.<br />
     *   If the user is allowed to make the request, then the method will create
     * a new {@code Authentication} Object, that will be returned, this Object
     * will contain the User & Group Entities for the action.
     *
     * @param token      Authentication Token
     * @param permission Permission to be checked
     * @return Authentication Object
     * @throws VerificationException if neither authenticated nor authorized
     */
    Authentication verifyAccess(final AuthenticationToken token, final Permission permission) {
        verify(token, "Invalid Authentication Token provided.");

        try {
            // Authentication Check; Expect Exception if unable to find a User
            final UserEntity user = dao.findActiveSession(token).getUser();
            // Authorization Check; Expect Exception if unable to match a Group
            final GroupEntity group = dao.findGroup(user, token.getGroupId(), permission);

            // So far so good, return the information
            return new Authentication(user, group);
        } catch (PersistenceException e) {
            throw new VerificationException(e);
        }
    }

    /**
     * Internal method to test verifiable objects. If the object is undefined,
     * i.e. null a VerificationException with the provided message is thrown,
     * otherwise the verify method is called on the verifiable object.
     *
     * @param verifiable Object to verify
     * @param message    Error message, if the verifiable object is null
     * @throws VerificationException if the verification failed
     * @see Verifiable#verify()
     */
    void verify(final Verifiable verifiable, final String message) {
        if (verifiable == null) {
            throw new VerificationException(message);
        }

        verifiable.verify();
    }
}
