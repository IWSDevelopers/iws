/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.ApplicationResponse
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
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.util.AbstractFallible;
import net.iaeste.iws.api.util.Copier;

import java.util.List;

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class StudentApplicationResponse extends AbstractFallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private final StudentApplication studentApplication;
    private final List<String> errors;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public StudentApplicationResponse() {
        super(IWSErrors.SUCCESS, IWSConstants.SUCCESS);
        studentApplication = null;
        errors = null;
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public StudentApplicationResponse(final IWSError error, final String message) {
        super(error, message);
        studentApplication = null;
        errors = null;
    }

    /**
     * Response is created when processing the studentApplication failed.
     * <p/>
     * Incorrect Applications should never be passed to this constructor. Instead use
     * constructor without list of errors parameter.
     *
     * @param failedStudentApplication list of applications for which something went wrong
     */
    public StudentApplicationResponse(final StudentApplication failedStudentApplication, final List<String> errors) {
        super(IWSErrors.PROCESSING_FAILURE, "processing of the StudentApplication failed");

        studentApplication = new StudentApplication(failedStudentApplication);
        this.errors = Copier.copy(errors);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public StudentApplication getStudentApplication() {
        return studentApplication;
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

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final StudentApplicationResponse that = (StudentApplicationResponse) obj;

        if (errors != null ? !errors.equals(that.errors) : that.errors != null) {
            return false;
        }

        return !(studentApplication != null ? !studentApplication.equals(that.studentApplication) : that.studentApplication != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (studentApplication != null ? studentApplication.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (errors != null ? errors.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "StudentApplicationResponse{" +
                "studentApplication=" + studentApplication +
                ", errors=" + errors +
                '}';
    }
}
