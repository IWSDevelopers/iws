/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.OfferTestUtility
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
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class EmployerInformationTestUtility {
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

    private EmployerInformationTestUtility() {
    }

    public static EmployerInformation getEmptyEmployerInformation() {
        return new EmployerInformation();
    }

    public static EmployerInformation getMinimalEmployerInformation() {
        final EmployerInformation minimalEmployerInformation = new EmployerInformation();
        minimalEmployerInformation.setName(NAME);
        minimalEmployerInformation.setWeeklyHours(WEEKLY_HOURS);
        return minimalEmployerInformation;
    }

    public static EmployerInformation getFullEmployerInformation() {
        final EmployerInformation employerInformation = getMinimalEmployerInformation();
        employerInformation.setAddress(ADDRESS);
        employerInformation.setAddress2(ADDRESS2);
        employerInformation.setBusiness(BUSINESS);
        employerInformation.setEmployeesCount(EMPLOYEES_COUNT);
        employerInformation.setWebsite(WEBSITE);
        employerInformation.setWorkingPlace(WORKING_PLACE);
        employerInformation.setNearestAirport(NEAREST_AIRPORT);
        employerInformation.setNearestPubTransport(NEAREST_PUBLIC_TRANSPORT);
        employerInformation.setDailyHours(DAILY_HOURS);
        return employerInformation;
    }
}
