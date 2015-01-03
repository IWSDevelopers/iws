/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.FetchInternationalGroupResponse
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.UserGroup;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class FetchInternationalGroupResponse extends FallibleResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private List<UserGroup> groups = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchInternationalGroupResponse() {
    }

    public FetchInternationalGroupResponse(final List<UserGroup> groups) {
        this.groups = groups;
    }

    /**
     * Error Constructor.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    public FetchInternationalGroupResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroups(final List<UserGroup> groups) {
        this.groups = groups;
    }

    public List<UserGroup> getGroups() {
        return groups;
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
        if (!(obj instanceof FetchInternationalGroupResponse)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        final FetchInternationalGroupResponse that = (FetchInternationalGroupResponse) obj;
        return !((groups != null) ? !groups.equals(that.groups) : (that.groups != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((groups != null) ? groups.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FetchInternationalGroupResponse{" +
                "groups=" + groups +
                '}';
    }
}
