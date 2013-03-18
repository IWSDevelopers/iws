/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.EmployerInformationTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import static net.iaeste.iws.api.dtos.EmployerTestUtility.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.exceptions.VerificationException;
import org.junit.Test;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class EmployerInformationTest {

    @Test
    public void testCopyConstructor() {
        final EmployerInformation minimalEmployer = getMinimalEmployer();
        final EmployerInformation copy = new EmployerInformation(minimalEmployer);

        assertThat(minimalEmployer, is(not(nullValue())));
        assertThat(copy, is(not(nullValue())));
        assertThat(minimalEmployer, is(copy));
    }

    @Test
    public void testMinimalEmployer() {
        final EmployerInformation employer = getMinimalEmployer();

        assertThat("reference not null", employer, is(not(nullValue())));
        assertThat("Name", NAME, is(employer.getName()));
        assertThat("WeeklyHours", WEEKLY_HOURS, is(employer.getWeeklyHours()));
    }

    @Test
    public void testFullEmployer() {
        final EmployerInformation  employer = getFullEmployer();

        assertThat("reference not null", employer, is(not(nullValue())));
        assertThat("Name", NAME, is(employer.getName()));
        assertThat("Address", ADDRESS, is(employer.getAddress()));
        assertThat("Address2", ADDRESS2, is(employer.getAddress2()));
        assertThat("Business", BUSINESS, is(employer.getBusiness()));
        assertThat("EmployeesCount", EMPLOYEES_COUNT, is(employer.getEmployeesCount()));
        assertThat("Website", WEBSITE, is(employer.getWebsite()));
        assertThat("WorkingPlace", WORKING_PLACE, is(employer.getWorkingPlace()));
        assertThat("NearestAirport", NEAREST_AIRPORT, is(employer.getNearestAirport()));
        assertThat("NearestPublicTransport", NEAREST_PUBLIC_TRANSPORT, is(employer.getNearestPubTransport()));
        assertThat("WeeklyHours", WEEKLY_HOURS, is(employer.getWeeklyHours()));
        assertThat("DailyHours", DAILY_HOURS, is(employer.getDailyHours()));
    }

    @Test(expected = VerificationException.class)
    public void testNotNullableName() {
        final EmployerInformation employer = getMinimalEmployer();
        employer.setName(null);

        employer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testNotNullableWeeklyHours() {
        final EmployerInformation employer = getMinimalEmployer();
        employer.setWeeklyHours(null);

        employer.verify();
    }
}
