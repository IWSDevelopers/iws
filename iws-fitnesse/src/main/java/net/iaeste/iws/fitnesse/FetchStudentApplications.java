/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.FetchStudentApplications
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
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.fitnesse.callers.StudentCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchStudentApplications extends AbstractFixture<FetchStudentApplicationsResponse> {

    private final Student student = new StudentCaller();
    private FetchStudentApplicationsRequest request = new FetchStudentApplicationsRequest();

    public void setOfferId(final String offerId) {
        request.setOfferId(offerId);
    }

    public int numberOfStudentApplications() {
        return getResponse() == null ? -1 : getResponse().getStudentApplications().size();
    }

    public String printStudentApplications(final int applicationIndex) {
        final String retVal;

        if (getResponse() == null) {
            retVal = "no response";
        } else if ((applicationIndex < 1) || (applicationIndex > numberOfStudentApplications())) {
            retVal = "no application for given index";
        } else {
            retVal = getResponse().getStudentApplications().get(applicationIndex - 1).toString();
        }

        return retVal;
    }

    public void fetchStudentApplications() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(student.fetchStudentApplications(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
