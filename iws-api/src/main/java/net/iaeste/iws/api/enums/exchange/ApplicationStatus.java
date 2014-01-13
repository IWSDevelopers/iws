/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.exchange.ApplicationStatus
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums.exchange;

/**
 * Describes the status of a student application for an offer
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public enum ApplicationStatus {
    // TODO the following states are just preliminary

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
