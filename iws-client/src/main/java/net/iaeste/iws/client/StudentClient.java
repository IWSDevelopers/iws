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
package net.iaeste.iws.client;

import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.student.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
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
public final class StudentClient implements Students {

    private final Students client;

    /**
     * Default Constructor.
     */
    public StudentClient() {
        client = ClientFactory.getInstance().getStudentImplementation();
    }

    // =========================================================================
    // Implementation of methods from Student in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateUserResponse createStudent(final AuthenticationToken token, final CreateUserRequest request) {
        return client.createStudent(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentResponse processStudent(final AuthenticationToken token, final StudentRequest request) {
        return client.processStudent(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchStudentsResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        return client.fetchStudents(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentApplicationResponse processStudentApplication(final AuthenticationToken token, final ProcessStudentApplicationsRequest request) {
        return client.processStudentApplication(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchStudentApplicationsResponse fetchStudentApplications(final AuthenticationToken token, final FetchStudentApplicationsRequest request) {
        return client.fetchStudentApplications(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentApplicationResponse processApplicationStatus(final AuthenticationToken token, final StudentApplicationRequest request) {
        return client.processApplicationStatus(token, request);
    }
}
