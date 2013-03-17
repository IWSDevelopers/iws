/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Application
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.ApplicationStatus;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains information about a Student applying for an Offer
 * <p/>
 * The application contains the student data for the time
 * when he/she applied, therefore student information
 * are duplicated for each application.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class Application extends AbstractVerification {

    /**
     * {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Application() {
    }

    /**
     * Copy Constructor.
     *
     * @param application Application Object to copy
     */
    public Application(final Application application) {
        if (application != null) {
            externalOfferId = application.getExternalOfferId();
            studentId = application.getStudentId();
            status = application.getStatus();
        }
    }

    /**
     * External ID of the {@link Offer} that the {@link Student} is applying for
     */
    private String externalOfferId;

    /**
     * ID of the {@link Student}
     */
    private String studentId;

    /**
     * Status of the {@link Application}
     */
    private ApplicationStatus status;

    // TODO add a copy of student data

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================


    public String getExternalOfferId() {
        return externalOfferId;
    }

    public void setExternalOfferId(String externalOfferId) {
        this.externalOfferId = externalOfferId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "externalOfferId", externalOfferId);
        isNotNull(validation, "studentId", studentId);

        return validation;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Application)) {
            return false;
        }

        final Application application = (Application) obj;

        if (externalOfferId != null ? !externalOfferId.equals(application.externalOfferId) : application.externalOfferId != null) {
            return false;
        }

        if (studentId != null ? !studentId.equals(application.studentId) : application.studentId != null) {
            return false;
        }

        return !(status != null ? !status.equals(application.status) : application.status != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (externalOfferId != null ? externalOfferId.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (studentId != null ? studentId.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (status != null ? status.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Application{" +
                "externalOfferId='" + externalOfferId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", status=" + status +
                '}';
    }
}