/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.EmployerTestUtility
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

/**
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class EmployerTestUtility {

    public static final String NAME = "Test_Employer_1";
    public static final String ADDRESS = "test address 30";
    public static final String ADDRESS2 = "test address 31";
    public static final String BUSINESS = "test business";
    public static final Integer EMPLOYEES_COUNT = 10;
    public static final String WEBSITE = "www.website.at";
    public static final String WORKING_PLACE = "Vienna";
    public static final String NEAREST_AIRPORT = "VIE";
    public static final String NEAREST_PUBLIC_TRANSPORT = "U4";
    public static final Float WEEKLY_HOURS = 40.0f;
    public static final Float DAILY_HOURS = WEEKLY_HOURS / 5;

    private EmployerTestUtility() {
    }

    public static EmployerInformation getEmptyEmployer() {
        return new EmployerInformation();
    }

    public static EmployerInformation getMinimalEmployer() {
        final EmployerInformation minimalEmployer = new EmployerInformation();
        minimalEmployer.setName(NAME);
        minimalEmployer.setWeeklyHours(WEEKLY_HOURS);
        return minimalEmployer;
    }

    public static EmployerInformation getFullEmployer() {
        final EmployerInformation employer = getMinimalEmployer();
        employer.setAddress(ADDRESS);
        employer.setAddress2(ADDRESS2);
        employer.setBusiness(BUSINESS);
        employer.setEmployeesCount(EMPLOYEES_COUNT);
        employer.setWebsite(WEBSITE);
        employer.setWorkingPlace(WORKING_PLACE);
        employer.setNearestAirport(NEAREST_AIRPORT);
        employer.setNearestPubTransport(NEAREST_PUBLIC_TRANSPORT);
        employer.setDailyHours(DAILY_HOURS);
        return employer;
    }
}
