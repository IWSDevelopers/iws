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

import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.FetchStudentsRequest;
import net.iaeste.iws.api.requests.StudentRequest;
import net.iaeste.iws.api.responses.StudentResponse;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.StudentDao;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StudentService {

    private final StudentDao studentDao;

    public StudentService(final StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void processStudents(final Authentication authentication, final StudentRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public StudentResponse fetchStudents(final Authentication authenticationn, final FetchStudentsRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }
}
