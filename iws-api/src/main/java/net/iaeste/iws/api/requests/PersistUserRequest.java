/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.UserRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.utils.Check;
import net.iaeste.iws.api.utils.Copier;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection RedundantNoArgConstructor
 */
public final class PersistUserRequest extends AbstractRequest {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private List<User> usersForPersisting = null;
    private List<User> usersToDelete = null;

    public void setUsersForPersisting(final List<User> usersForPersisting) {
        this.usersForPersisting = Copier.copy(usersForPersisting);
    }

    public List<User> getUsersForPersisting() {
        return Copier.copy(usersForPersisting);
    }

    public void setUsersToDelete(final List<User> usersToDelete) {
        this.usersToDelete = Copier.copy(usersToDelete);
    }

    public List<User> getUsersToDelete() {
        return Copier.copy(usersToDelete);
    }

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public PersistUserRequest() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
        if (usersForPersisting != null) {
            Check.valid(usersForPersisting, "usersForPersisting");
        }

        if (usersToDelete != null) {
            Check.valid(usersToDelete, "usersForDeleting");
        }
    }
}
