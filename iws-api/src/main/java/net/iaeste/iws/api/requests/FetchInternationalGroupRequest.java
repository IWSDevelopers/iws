/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchInternationalGroupRequest
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
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class FetchInternationalGroupRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private static final Set<GroupStatus> ALLOWED = EnumSet.of(GroupStatus.ACTIVE, GroupStatus.SUSPENDED);
    private Set<GroupStatus> statuses = ALLOWED;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchInternationalGroupRequest() {
    }

    /**
     * Default Constructor.
     *
     * @param groupId Id of the Group Object to fetch Roles for
     */
    public FetchInternationalGroupRequest(final Set<GroupStatus> statuses) {
        this.statuses = statuses;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Statuses to be used in the International Group lookup. If the
     * value is null or empty, then the setter will throw an IllegalArgument
     * Exception.
     *
     * @param statuses Set of Status values to include in the lookup
     * @throws IllegalArgumentException if the statuses is null
     */
    public void setStatuses(final Set<GroupStatus> statuses) throws IllegalArgumentException {
        ensureNotNullAndContains("statuses", statuses, ALLOWED);

        this.statuses = statuses;
    }

    public Set<GroupStatus> getStatuses() {
        return statuses;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(1);

        isNotNullAndContains(validation, "statuses", statuses, ALLOWED);

        return validation;
    }
}
