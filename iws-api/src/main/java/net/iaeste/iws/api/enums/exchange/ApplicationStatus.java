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
package net.iaeste.iws.api.enums.exchange;

import javax.xml.bind.annotation.XmlType;

/**
 * Describes the status of a student application for an offer
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "applicationStatus")
public enum ApplicationStatus {

    /**
     * A student applies for an offer in his local/national committee.
     * The receiving country does not see the application yet.
     */
    APPLIED,

    /**
     * The sending national committee selected the student to forward
     * to the receiving country.
     * Both countries can see the application.
     */
    NOMINATED,

    /**
     * The receiving country forwarded the application to the employer.
     * Both countries can see the application.
     */
    FORWARDED_TO_EMPLOYER,

    /**
     * The employer accepted the student.
     * Both countries can see the application.
     */
    ACCEPTED,

    /**
     * The employer rejected the student.
     * Both countries can see the application.
     */
    REJECTED,

    /**
     * Sending country rejected the student
     */
    REJECTED_BY_SENDING_COUNTRY,

    /**
     * The student does no longer wish to take the internship.
     * Both countries can see the application.
     */
    CANCELLED
}
