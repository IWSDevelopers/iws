/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.api.requests.student;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.util.Verifications;

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
public final class StudentApplicationRequest extends Verifications {

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
