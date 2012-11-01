/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.UserGroupAssignmentRequest
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
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection RedundantNoArgConstructor
 */
public final class UserGroupAssignmentRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The user that ill be added to a group. **/
    private User user = null;

    /** The group to which the user will be added.**/
    private Group group = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public UserGroupAssignmentRequest(final User user, final Group group) {
        this.user = new User(user);
        this.group = group;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUser(final User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setGroup(final Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);
        return validation;
    }
}
