/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
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
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studentApplicationRequest", propOrder = { "applicationId", "status", "rejectByEmployerReason", "rejectDescription", "rejectInternalComment" })
public final class StudentApplicationRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private String applicationId = null;
    @XmlElement(required = true, nillable = false) private ApplicationStatus status = null;
    @XmlElement(required = true, nillable = true) private String rejectByEmployerReason = null;
    @XmlElement(required = true, nillable = true) private String rejectDescription = null;
    @XmlElement(required = true, nillable = true) private String rejectInternalComment = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public StudentApplicationRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    public StudentApplicationRequest(final String applicationId, final ApplicationStatus status) {
        this.applicationId = applicationId;
        this.status = status;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Student Application Id. The Id must be valid, if not then the
     * method will throw an {@code IllegalArgumentException}.
     *
     * @param applicationId Student Application Id
     * @throws IllegalArgumentException if not a valid Id
     */
    public void setApplicationId(final String applicationId) throws IllegalArgumentException {
        ensureNotNullAndValidId("applicationId", applicationId);
        this.applicationId = applicationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    /**
     * Sets the new Status for the Student Application. The Status may not be
     * null. If invalid, then the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param status Student Application Status
     * @throws IllegalArgumentException if set to null
     */
    public void setStatus(final ApplicationStatus status) throws IllegalArgumentException {
        ensureNotNull("status", status);
        this.status = status;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setRejectByEmployerReason(final String rejectByEmployerReason) {
        this.rejectByEmployerReason = rejectByEmployerReason;
    }

    public String getRejectByEmployerReason() {
        return rejectByEmployerReason;
    }

    public void setRejectDescription(final String rejectDescription) {
        this.rejectDescription = rejectDescription;
    }

    public String getRejectDescription() {
        return rejectDescription;
    }

    public void setRejectInternalComment(final String rejectInternalComment) {
        this.rejectInternalComment = rejectInternalComment;
    }

    public String getRejectInternalComment() {
        return rejectInternalComment;
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

        isNotNull(validation, "applicationId", applicationId);
        isNotNull(validation, "status", status);

        return validation;
    }
}
