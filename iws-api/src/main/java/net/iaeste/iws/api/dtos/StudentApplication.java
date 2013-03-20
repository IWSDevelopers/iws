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
 * The studentApplication contains the student data for the time
 * when he/she applied, therefore student information
 * are duplicated for each studentApplication.
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StudentApplication extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** Id of the {@link Offer} that the {@link Student} is applying for */
    private String offerId = null;

    /** Id of the {@link Student} */
    private String studentId = null;

    /** Status of the {@link StudentApplication} */
    private ApplicationStatus status = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public StudentApplication() {
    }

    /**
     * Copy Constructor.
     *
     * @param studentApplication StudentApplication Object to copy
     */
    public StudentApplication(final StudentApplication studentApplication) {
        if (studentApplication != null) {
            offerId = studentApplication.offerId;
            studentId = studentApplication.studentId;
            status = studentApplication.status;
        }
    }

    // TODO add a copy of student data

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setOfferId(final String offerId) {
        this.offerId = offerId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setStudentId(final String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStatus(final ApplicationStatus status) {
        this.status = status;
    }

    public ApplicationStatus getStatus() {
        return status;
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

        isNotNull(validation, "offerId", offerId);
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

        if (!(obj instanceof StudentApplication)) {
            return false;
        }

        final StudentApplication studentApplication = (StudentApplication) obj;

        if (offerId != null ? !offerId.equals(studentApplication.offerId) : studentApplication.offerId != null) {
            return false;
        }

        if (studentId != null ? !studentId.equals(studentApplication.studentId) : studentApplication.studentId != null) {
            return false;
        }

        return !(status != null ? status != studentApplication.status : studentApplication.status != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (offerId != null ? offerId.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (studentId != null ? studentId.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (status != null ? status.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "StudentApplication{" +
                "offerId='" + offerId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", status=" + status +
                '}';
    }
}
