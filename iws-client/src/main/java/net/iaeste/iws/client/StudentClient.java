/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.StudentClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client;

import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.student.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationRequest;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.responses.student.StudentResponse;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
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
