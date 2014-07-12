/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.Students
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
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationRequest;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.responses.student.StudentResponse;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Students {

    /**
     * Creates a new Student Account, with the data from the Request Object. The
     * new Student Account will have Status {@link UserStatus#NEW}, and an
     * e-mail is send to the user via the provided username. The e-mail will
     * contain an Activation Link, which is then used to activate the
     * account.<br />
     *   Note, the account cannot be used before it is activated. If the Account
     * is been deleted before Activation is completed, then all information is
     * removed from the system. If the Account is deleted after activation, the
     * User Account Object will remain in the system, though all data will be
     * removed.<br />
     *   Note, the StudentAccount flag in the Request Object is ignored for this
     * request, meaning that the method will always create a Student Account, if
     * the requesting user is permitted to do so.
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Request data, must contain username, password as well as
     *                first and last name
     * @return Standard Error Object
     */
    CreateUserResponse createStudent(AuthenticationToken token, CreateUserRequest request);

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
