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
package net.iaeste.iws.api;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationsRequest;
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
     * Deprecated - please use the processStudent instead.
     *
     * @param token   Authentication information about the user invoking the
     *                request
     * @param request Request data, must contain username, password as well as
     *                first and last name
     * @return Standard Error Object
     */
    CreateUserResponse createStudent(AuthenticationToken token, CreateUserRequest request);

    /**
     * <p>Processes a Student Object. Meaning, either creating, updating an existing
     * Student, deleting an existing Student, or making an existing User a
     * Student.</p>
     *
     * <p>The new Student Account will have Status
     * {@link net.iaeste.iws.api.enums.UserStatus#NEW}, and an e-mail is send
     * to the user via the provided username. The e-mail will contain an
     * Activation Link, which is then used to activate the account.</p>
     *
     * <p>Note, the account cannot be used before it is activated. If the
     * Account is been deleted before Activation is completed, then all
     * information is removed from the system. If the Account is deleted after
     * activation, the User Account Object will remain in the system, though
     * all data will be removed.</p>
     *
     * <p>Note, the StudentAccount flag in the Request Object is ignored for
     * this request, meaning that the method will always create a Student
     * Account, if the requesting user is permitted to do so.</p>
     *
     * @param token   {@link AuthenticationToken}
     * @param request {@link StudentRequest}
     * @return {@link StudentResponse}
     */
    StudentResponse processStudent(AuthenticationToken token, StudentRequest request);

    /**
     * <p>Retrieves a list of Students, matching the criteria's from the Request
     * Object.</p>
     *
     * @param token   {@link AuthenticationToken}
     * @param request {@link FetchStudentsRequest}
     * @return {@link FetchStudentsResponse}
     */
    FetchStudentsResponse fetchStudents(AuthenticationToken token, FetchStudentsRequest request);

    /**
     * <p>Create or update a student application.</p>
     *
     * @param token   {@link AuthenticationToken}
     * @param request {@link StudentApplicationsRequest}
     * @return {@link StudentApplicationResponse}
     */
    StudentApplicationResponse processStudentApplication(AuthenticationToken token, StudentApplicationsRequest request);

    /**
     * <p>Fetch student applications.</p>
     *
     * @param token   {@link AuthenticationToken}
     * @param request {@link FetchStudentApplicationsRequest}
     * @return {@link FetchStudentApplicationsResponse}
     */
    FetchStudentApplicationsResponse fetchStudentApplications(AuthenticationToken token, FetchStudentApplicationsRequest request);

    /**
     * <p>Change the status of an application as well as additional status
     * fields.</p>
     *
     * @param token   {@link AuthenticationToken}
     * @param request {@link}
     * @return {@link StudentApplicationResponse}
     */
    StudentApplicationResponse processApplicationStatus(AuthenticationToken token, StudentApplicationRequest request);
}
