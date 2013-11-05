/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.StudentService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import static net.iaeste.iws.core.transformers.AdministrationTransformer.transform;

import net.iaeste.iws.api.dtos.exchange.Student;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.student.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationRequest;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentDao studentDao;
    private final AccessDao accessDao;

    public StudentService(final AccessDao accessDao, final StudentDao studentDao) {
        this.accessDao = accessDao;
        this.studentDao = studentDao;
    }

    public FetchStudentsResponse fetchStudents(final Authentication authentication, final FetchStudentsRequest request) {
        final GroupEntity group = accessDao.findMemberGroup(authentication.getUser());
        final List<UserEntity> found = studentDao.findAllStudents(group.getId());

        final List<Student> students = new ArrayList<>(found.size());
        for (final UserEntity entity : found) {
            students.add(convert(entity));
        }

        return new FetchStudentsResponse(students);
    }

    private static Student convert(final UserEntity user) {
        return new Student(transform(user));
    }

    public StudentApplicationResponse processStudentApplication(final Authentication authentication, final ProcessStudentApplicationsRequest request) {
        throw new NotImplementedException("Pending Implementation.");
    }

    public FetchStudentApplicationsResponse fetchStudentApplications(final Authentication authentication, final FetchStudentApplicationsRequest request) {
        throw new NotImplementedException("Pending Implementation.");
    }

    public StudentApplicationResponse processApplicationStatus(final Authentication authentication, final StudentApplicationRequest request) {
        throw new NotImplementedException("Pending Implementation.");
    }
}
