/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.student.StudentApplicationRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests.student;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * This class describes the request object used to change the status
 * of a student application.
 *
 * It also contains additional fields that are required for certain states.
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StudentApplicationRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    // TODO complete implementation, add fields for rejection messages and comments (see exchange mockups)
    private ApplicationStatus status;

    private String applicationId;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public StudentApplicationRequest() {
    }

    public StudentApplicationRequest(final String applicationId, final ApplicationStatus status) {
        this.status = status;
        this.applicationId = applicationId;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getApplicationId() {
        return applicationId;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final HashMap<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "applicationId", applicationId);
        isNotNull(validation, "status", status);

        return validation;
    }
}
