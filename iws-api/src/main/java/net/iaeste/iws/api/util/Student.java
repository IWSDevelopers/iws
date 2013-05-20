/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.Students
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;

import javax.ejb.Remote;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Remote
public interface Student {

    Fallible processStudent(AuthenticationToken token, StudentRequest request);

    FetchStudentResponse fetchStudents(AuthenticationToken token, FetchStudentsRequest request);

    StudentApplicationResponse processStudentApplication(AuthenticationToken token, ProcessStudentApplicationsRequest request);

    FetchStudentApplicationsResponse fetchStudentApplications(AuthenticationToken token, FetchStudentApplicationsRequest request);
}
