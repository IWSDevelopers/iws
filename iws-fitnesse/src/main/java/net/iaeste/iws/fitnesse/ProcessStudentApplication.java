/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.ProcessStudentApplication
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Student;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.requests.exchange.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.fitnesse.callers.StudentCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ProcessStudentApplication extends AbstractFixture<StudentApplicationResponse> {

    private final Student student = new StudentCaller();
    private ProcessStudentApplicationsRequest request = new ProcessStudentApplicationsRequest();
    private StudentApplication studentApplication = new StudentApplication();

    public void setOfferId(final String offerId) {
        studentApplication.setOfferId(offerId);
    }

    public void setStudentId(final String studentId) {
        studentApplication.setStudentId(studentId);
    }

    public void setStatus(final String status) {
        studentApplication.setStatus(ApplicationStatus.valueOf(status));
    }

    public String printStudentApplication()
    {
        return getResponse().getStudentApplication().toString();
    }

    public void processStudentApplication() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        request.setStudentApplication(studentApplication);
        setResponse(student.processStudentApplication(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
