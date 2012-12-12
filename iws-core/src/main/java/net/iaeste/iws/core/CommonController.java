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
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.util.Verifiable;
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

    private static final String NULL_REQUEST = " Object may not be null.";
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
     * @param token      Authentication Token with GroupId
     * @param permission Permission to be checked
     * @return Authentication Object
     * @throws VerificationException if neither authenticated nor authorized
     */
    Authentication verifyAccess(final AuthenticationToken token, final Permission permission) {
        verify(token, "Invalid Authentication Token provided.");

        return verifyAccess(token, permission, token.getGroupId());
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
     * @param group      Group to use instead of the one from the Token
     * @return Authentication Object
     * @throws VerificationException if neither authenticated nor authorized
     */
    Authentication verifyAccess(final AuthenticationToken token, final Permission permission, final Group group) {
        verify(token, "Invalid Authentication Token provided.");
        verify(group);

        return verifyAccess(token, permission, group.getGroupId());
    }

    private Authentication verifyAccess(final AuthenticationToken token, final Permission permission, final String externalGroupId) {
        try {
            // Authentication Check; Expect Exception if unable to find a User
            final UserEntity user = dao.findActiveSession(token).getUser();
            // Authorization Check; Expect Exception if unable to match a Group
            final GroupEntity group = dao.findGroupByPermission(user, externalGroupId, permission);

            // So far so good, return the information
            return new Authentication(token, user, group);
        } catch (PersistenceException e) {
            throw new VerificationException(e);
        }
    }

    /**
     * For those requests, which does not require a Permission, we still need to
     * verify the provided Token, fetch an {@code Authentication} Object, which
     * we can use internally.
     *
     * @param token Authentication Token
     * @return Authentication Object
     * @throws VerificationException if neither authenticated nor authorized
     */
    Authentication verifyPrivateAccess(final AuthenticationToken token) {
        verify(token, "Invalid Authentication Token provided.");

        try {
            // Authentication Check; Expect Exception if unable to find a User
            final UserEntity user = dao.findActiveSession(token).getUser();

            // For most requests, we are not using the Group, so for our Private
            // usage, we're ignoring this. If data has to be added, and thus
            // requires the Private Group, then this must be fetched. The
            // decision to do it so, was made to avoid loosing too much
            // performance on operations that are rarely required
            return new Authentication(token, user);
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
     * @param message    The Error message to display
     * @throws VerificationException if the verification failed
     * @see Verifiable#verify()
     */
    void verify(final Verifiable verifiable, final String... message) {
        if (verifiable == null) {
            final String text;
            if (message != null && message.length == 1) {
                text = message[0];
            } else {
                text = "Cannot process a Null Request Object.";
            }

            throw new VerificationException(text + NULL_REQUEST);
        }

        verifiable.verify();
    }
}
