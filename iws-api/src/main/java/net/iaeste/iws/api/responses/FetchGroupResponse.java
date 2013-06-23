/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.FetchGroupResponse
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses;

import static net.iaeste.iws.api.util.Copier.copy;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.util.AbstractFallible;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchGroupResponse extends AbstractFallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Group group = null;
    private List<User> users = null;
    private List<Group> subGroups = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchGroupResponse() {
    }

    /**
     * Default Constructor, for creating this Object with a Group and all known
     * Users and Subgroups.
     *
     * @param group     Requested Group
     * @param users     List of Users belonging to the Group
     * @param subGroups List of SubGroups belonging to the Group
     */
    public FetchGroupResponse(final Group group, final List<User> users, final List<Group> subGroups) {
        setGroup(group);
        setUsers(users);
        setSubGroups(subGroups);
    }

    /**
     * Error Constructor.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    public FetchGroupResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroup(final Group group) {
        this.group = new Group(group);
    }

    public Group getGroup() {
        return new Group(group);
    }

    public void setUsers(final List<User> users) {
        this.users = copy(users);
    }

    public List<User> getUsers() {
        return copy(users);
    }

    public void setSubGroups(final List<Group> subGroups) {
        this.subGroups = copy(subGroups);
    }

    public List<Group> getSubGroups() {
        return copy(subGroups);
    }

    // =========================================================================
    // Standard Response Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FetchGroupResponse)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        final FetchGroupResponse that = (FetchGroupResponse) obj;

        if (group != null ? !group.equals(that.group) : that.group != null) {
            return false;
        }
        if (subGroups != null ? !subGroups.equals(that.subGroups) : that.subGroups != null) {
            return false;
        }

        return !(users != null ? !users.equals(that.users) : that.users != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (group != null ? group.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (users != null ? users.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (subGroups != null ? subGroups.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FetchGroupResponse{" +
                "group=" + group +
                ", users=" + users +
                ", subGroups=" + subGroups +
                '}';
    }
}
