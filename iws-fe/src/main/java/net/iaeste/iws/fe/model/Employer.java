/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.model.Employer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that serves as a dummy database to test the functionality
 * of the autocomplete component for a selected employer and filling out the form
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */

public class Employer {

    private String name;
    private String address;
    private String businessOrProducts;
    private Integer numberOfEmployees;
    private String website;
    private String workingPlace;
    private String nearestInternationalAirport;
    private String nearestPublicTransport;
    private static Float weeklyWorkingHours;
    private static Float dailyWorkingHours;
    private String address2;

    public static List<Employer> getDummyEmployers() {
        List<Employer> list = new ArrayList<>();
     for (int i=0; i< 10; i++) {
         Employer e = new Employer();
         e.setName("Employer" + i);
         e.setAddress("Street " + i);
         e.setAddress2("Town " + i);
         e.setBusinessOrProducts("businessOrProducts " + i);
         e.setNumberOfEmployees(i);
         e.setWebsite("website " + i);
         e.setWorkingPlace("workingPlace " + i);
         e.setNearestInternationalAirport("nearestInternationalAirport " + i);
         e.setNearestPublicTransport("nearestPublicTransport " + i);
         e.setWeeklyWorkingHours(i % 2 == 0 ? 55f : 47f);
         e.setDailyWorkingHours(i % 2 == 0 ? 8f : 6f);
         list.add(e);
     }

        return list;
    }

    public boolean nameMatches(String str) {
        return this.name.toLowerCase().startsWith(str.toLowerCase());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessOrProducts() {
        return businessOrProducts;
    }

    public void setBusinessOrProducts(String businessOrProducts) {
        this.businessOrProducts = businessOrProducts;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getNearestInternationalAirport() {
        return nearestInternationalAirport;
    }

    public void setNearestInternationalAirport(String nearestInternationalAirport) {
        this.nearestInternationalAirport = nearestInternationalAirport;
    }

    public String getNearestPublicTransport() {
        return nearestPublicTransport;
    }

    public void setNearestPublicTransport(String nearestPublicTransport) {
        this.nearestPublicTransport = nearestPublicTransport;
    }

    public Float getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }

    public void setWeeklyWorkingHours(Float weeklyWorkingHours) {
        this.weeklyWorkingHours = weeklyWorkingHours;
    }

    public Float getDailyWorkingHours() {
        return dailyWorkingHours;
    }

    public void setDailyWorkingHours(Float dailyWorkingHours) {
        this.dailyWorkingHours = dailyWorkingHours;
    }

    public String getAddress2() {
        return address2;
    }
}
