/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchSurveyOfCountryRequest
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
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class FetchSurveyOfCountryRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String groupId = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Default Constructor.
     */
    public FetchSurveyOfCountryRequest() {
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Setting the Id of the National Committee to retrieve the Survey of
     * Countries for. The Id must be valid, otherwise an Illegal Argument
     * Exception is thrown.
     *
     * @param groupId National Group Id
     * @throws IllegalArgumentException if given group Id is invalid
     */
    public void setGroupId(final String groupId) throws IllegalArgumentException {
        ensureValidId("groupId", groupId);
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
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

        isNotNull(validation, "groupId", groupId);

        return validation;
    }
}