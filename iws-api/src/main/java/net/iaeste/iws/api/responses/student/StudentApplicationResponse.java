/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.student.StudentApplicationResponse
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses.student;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.responses.FallibleResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studentApplicationResponse", propOrder = { "studentApplication" })
public final class StudentApplicationResponse extends FallibleResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true)
    private StudentApplication studentApplication;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public StudentApplicationResponse() {
        super(IWSErrors.SUCCESS, IWSConstants.SUCCESS);
        studentApplication = null;
    }

    /**
     * Constructor is used when succeed on creating or updating an application.
     *
     * @param studentApplication application which was saved
     */
    public StudentApplicationResponse(final StudentApplication studentApplication) {
        this.studentApplication = new StudentApplication(studentApplication);
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
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setStudentApplication(final StudentApplication studentApplication) {
        this.studentApplication = studentApplication;
    }

    public StudentApplication getStudentApplication() {
        return studentApplication;
    }
}
