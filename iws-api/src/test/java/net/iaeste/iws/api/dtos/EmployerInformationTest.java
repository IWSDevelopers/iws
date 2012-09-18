/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
import static net.iaeste.iws.api.dtos.EmployerInformationTestUtility.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class EmployerInformationTest {
    private EmployerInformation employerInformation = getMinimalEmployerInformation();
    static final String ERRMSG_NOT_NULL = " field cannot be null";

    @Before
    public void before() {
        employerInformation = getMinimalEmployerInformation();
    }

    @Test
    public void testCopyConstructor() {
        final EmployerInformation employerInformation = getMinimalEmployerInformation();
        final EmployerInformation copy = new EmployerInformation(employerInformation);
        Assert.assertThat(employerInformation, is(not(nullValue())));
        Assert.assertThat(copy, is(not(nullValue())));
        Assert.assertThat(employerInformation, is(copy));
    }

    @Test
    public void testMinimalEmployerInformation() {
        Assert.assertNotNull("reference not null", employerInformation);
        Assert.assertThat("Name", NAME, is(employerInformation.getName()));
        Assert.assertThat("WeeklyHours", WEEKLY_HOURS, is(employerInformation.getWeeklyHours()));
    }

    @Test
    public void testFullEmployerInformation() {
        employerInformation = getFullEmployerInformation();
        Assert.assertNotNull("reference not null", employerInformation);
        Assert.assertThat("Name", NAME, is(employerInformation.getName()));
        Assert.assertThat("Address", ADDRESS, is(employerInformation.getAddress()));
        Assert.assertThat("Address2", ADDRESS2, is(employerInformation.getAddress2()));
        Assert.assertThat("Business", BUSINESS, is(employerInformation.getBusiness()));
        Assert.assertThat("EmployeesCount", EMPLOYEES_COUNT, is(employerInformation.getEmployeesCount()));
        Assert.assertThat("Website", WEBSITE, is(employerInformation.getWebsite()));
        Assert.assertThat("WorkingPlace", WORKING_PLACE, is(employerInformation.getWorkingPlace()));
        Assert.assertThat("NearestAirport", NEAREST_AIRPORT, is(employerInformation.getNearestAirport()));
        Assert.assertThat("NearestPublicTransport", NEAREST_PUBLIC_TRANSPORT, is(employerInformation.getNearestPubTransport()));
        Assert.assertThat("WeeklyHours", WEEKLY_HOURS, is(employerInformation.getWeeklyHours()));
        Assert.assertThat("DailyHours", DAILY_HOURS, is(employerInformation.getDailyHours()));
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testMinimalOfferShouldBeValid() {
        employerInformation = getMinimalEmployerInformation();
        // "valid employerInformation from helper should be valid"
        employerInformation.verify();
    }

    @Test
    public void testNotNullableName() {
        employerInformation = getMinimalEmployerInformation();
        employerInformation.setName(null);
        Assert.assertThat(String.format("Name%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testNotNullableWeeklyHours() {
        employerInformation = getMinimalEmployerInformation();
        employerInformation.setWeeklyHours(null);
        Assert.assertThat(String.format("weeklyHours%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    public boolean isVerificationExceptionThrown() {
        try {
            employerInformation.verify();
            return false;
        } catch (VerificationException ignore) {
            return true;
        }
    }
}
