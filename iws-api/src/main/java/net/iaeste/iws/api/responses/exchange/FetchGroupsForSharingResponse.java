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
package net.iaeste.iws.api.responses.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.util.AbstractFallible;
import net.iaeste.iws.api.util.Copier;

import java.util.List;

/**
 * Returns a list of national groups. The list is ordered by name.
 *
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchGroupsForSharingResponse extends AbstractFallible {

    /** {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private List<Group> groups = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchGroupsForSharingResponse() {
    }

    public FetchGroupsForSharingResponse(final List<Group> groups) {
        this.groups = Copier.copy(groups);
    }

    /**
     * Error Constructor.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    public FetchGroupsForSharingResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroups(final List<Group> groups) {
        this.groups = Copier.copy(groups);
    }

    public List<Group> getGroups() {
        return Copier.copy(groups);
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
        if (!(obj instanceof FetchGroupsForSharingResponse)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        final FetchGroupsForSharingResponse that = (FetchGroupsForSharingResponse) obj;
        return !(groups != null ? !groups.equals(that.groups) : that.groups != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 31 * result + (groups != null ? groups.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FetchGroupResponse{" +
                "groups=" + groups +
                '}';
    }
}
