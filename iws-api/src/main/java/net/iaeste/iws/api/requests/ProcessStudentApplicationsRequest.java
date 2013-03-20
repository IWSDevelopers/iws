/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.ProcessApplicationsRequest
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
import net.iaeste.iws.api.dtos.StudentApplication;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class ProcessStudentApplicationsRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The StudentApplication Object to process.
     */
    private StudentApplication studentApplication = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public ProcessStudentApplicationsRequest() {
    }

    /**
     * Default Constructor, sets the StudentApplication to be processed. If the StudentApplication exists,
     * it will be updated otherwise a new StudentApplication will be created.
     *
     * @param studentApplication object to create or update
     */
    public ProcessStudentApplicationsRequest(final StudentApplication studentApplication) {
        this.studentApplication = new StudentApplication(studentApplication);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Student Application for this request, if the value is null, then
     * an IllegalArgument exception is thrown.
     *
     * @param studentApplication Student Application
     */
    public void setStudentApplication(final StudentApplication studentApplication) {
        if (studentApplication == null) {
            throw new IllegalArgumentException("The StudentApplication value may not be null.");
        }

        this.studentApplication = new StudentApplication(studentApplication);
    }

    public StudentApplication getStudentApplication() {
        return studentApplication;
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

        isVerifiable(validation, "studentApplication", studentApplication);

        return validation;
    }
}
