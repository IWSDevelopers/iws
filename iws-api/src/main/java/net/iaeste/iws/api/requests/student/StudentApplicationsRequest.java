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
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.requests.Actions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Request to create or update student application
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studentApplicationsRequest", propOrder = "studentApplication")
public class StudentApplicationsRequest extends Actions {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The StudentApplication Object to process. */
    @XmlElement(required = true)  private StudentApplication studentApplication = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Default Constructor.
     */
    public StudentApplicationsRequest() {
        super(EnumSet.of(Action.PROCESS), Action.PROCESS);
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
        ensureNotNullAndVerifiable("studentApplication", studentApplication);

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

        isNotNull(validation, FIELD_ACTION, action);
        isNotNull(validation, "studentApplication", studentApplication);

        return validation;
    }
}
