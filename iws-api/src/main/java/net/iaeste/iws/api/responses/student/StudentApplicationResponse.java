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
package net.iaeste.iws.api.responses.student;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.responses.Response;

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
@XmlType(name = "studentApplicationResponse", propOrder = "studentApplication")
public final class StudentApplicationResponse extends Response {

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
