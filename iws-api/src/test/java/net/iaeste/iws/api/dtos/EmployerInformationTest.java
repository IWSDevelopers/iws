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

import net.iaeste.iws.api.exceptions.VerificationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//import static net.iaeste.iws.api.dtos.OfferTestUtility.*;
import static net.iaeste.iws.api.dtos.EmployerTestUtility.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class EmployerInformationTest {
    private EmployerInformation employer = getMinimalEmployer();
    static final String ERRMSG_NOT_NULL = " field cannot be null";

    @Before
    public void before() {
        employer = getMinimalEmployer();
    }

    @Test
    public void testCopyConstructor() {
        final EmployerInformation minimalEmployer = getMinimalEmployer();
        final EmployerInformation copy = new EmployerInformation(minimalEmployer);
        Assert.assertThat(minimalEmployer, is(not(nullValue())));
        Assert.assertThat(copy, is(not(nullValue())));
        Assert.assertThat(minimalEmployer, is(copy));
    }

    @Test
    public void testMinimalEmployer() {
        Assert.assertNotNull("reference not null", employer);
        Assert.assertThat("Name", NAME, is(employer.getName()));
        Assert.assertThat("WeeklyHours", WEEKLY_HOURS, is(employer.getWeeklyHours()));
    }

    @Test
    public void testFullEmployer() {
        employer = getFullEmployer();
        Assert.assertNotNull("reference not null", employer);
        Assert.assertThat("Name", NAME, is(employer.getName()));
        Assert.assertThat("Address", ADDRESS, is(employer.getAddress()));
        Assert.assertThat("Address2", ADDRESS2, is(employer.getAddress2()));
        Assert.assertThat("Business", BUSINESS, is(employer.getBusiness()));
        Assert.assertThat("EmployeesCount", EMPLOYEES_COUNT, is(employer.getEmployeesCount()));
        Assert.assertThat("Website", WEBSITE, is(employer.getWebsite()));
        Assert.assertThat("WorkingPlace", WORKING_PLACE, is(employer.getWorkingPlace()));
        Assert.assertThat("NearestAirport", NEAREST_AIRPORT, is(employer.getNearestAirport()));
        Assert.assertThat("NearestPublicTransport", NEAREST_PUBLIC_TRANSPORT, is(employer.getNearestPubTransport()));
        Assert.assertThat("WeeklyHours", WEEKLY_HOURS, is(employer.getWeeklyHours()));
        Assert.assertThat("DailyHours", DAILY_HOURS, is(employer.getDailyHours()));
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testMinimalOfferShouldBeValid() {
        employer = getMinimalEmployer();
        // "valid employer from helper should be valid"
        employer.verify();
    }

    @Test
    public void testNotNullableName() {
        employer = getMinimalEmployer();
        employer.setName(null);
        Assert.assertThat(String.format("Name%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testNotNullableWeeklyHours() {
        employer = getMinimalEmployer();
        employer.setWeeklyHours(null);
        Assert.assertThat(String.format("weeklyHours%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    public boolean isVerificationExceptionThrown() {
        boolean result = false;

        try {
            employer.verify();
        } catch (VerificationException ignore) {
            result = true;
        }

        return result;
    }
}
