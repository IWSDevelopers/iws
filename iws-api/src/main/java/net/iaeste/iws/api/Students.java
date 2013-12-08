/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.Student
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationRequest;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.responses.student.StudentResponse;

import javax.ejb.Remote;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Remote
public interface Students {

    /**
     * Processes a Student Object. Meaning, either updating an existing Student,
     * deleting an existing Student, or making an existing User a Student.
     *
     * @param token   {@link AuthenticationToken}
     * @param request {@link StudentRequest}
     * @return {@link StudentResponse}
     */
    StudentResponse processStudent(AuthenticationToken token, StudentRequest request);

    /**
     * Retrieves a list of Students, matching the criterias from the Request
     * Object.
     *
     * @param token   {@link AuthenticationToken}
     * @param request {@link FetchStudentsRequest}
     * @return {@link FetchStudentsResponse}
     */
    FetchStudentsResponse fetchStudents(AuthenticationToken token, FetchStudentsRequest request);

    /**
     * Create or update a student application.
     *
     * @param token   {@link AuthenticationToken}
     * @param request {@link ProcessStudentApplicationsRequest}
     * @return {@link StudentApplicationResponse}
     */
    StudentApplicationResponse processStudentApplication(AuthenticationToken token, ProcessStudentApplicationsRequest request);

    /**
     * Fetch student applications.
     *
     * @param token   {@link AuthenticationToken}
     * @param request {@link FetchStudentApplicationsRequest}
     * @return {@link FetchStudentApplicationsResponse}
     */
    FetchStudentApplicationsResponse fetchStudentApplications(AuthenticationToken token, FetchStudentApplicationsRequest request);

    /**
     * Change the status of an application as well as additional status fields.
     *
     * @param token   {@link AuthenticationToken}
     * @param request {@link}
     * @return {@link StudentApplicationResponse}
     */
    StudentApplicationResponse processApplicationStatus(AuthenticationToken token, StudentApplicationRequest request);
}
