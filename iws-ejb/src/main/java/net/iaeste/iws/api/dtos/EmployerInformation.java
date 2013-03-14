/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.EmployerInformation
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * The EmployerInformation Object contains all information about the employer.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyComplexMethod
 */
public final class EmployerInformation extends AbstractVerification {

    /** {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String name = null;
    private String address = null;
    private String address2 = null;
    private String business = null;
    private Integer employeesCount = null;
    private String website = null;
    private String workingPlace = null;
    private String nearestAirport = null;
    private String nearestPubTransport = null;
    private Float weeklyHours = null;
    private Float dailyHours = null;

    /** Empty Constructor, required for some communication frameworks. */
    public EmployerInformation() {
    }

    /**
     * Copy constructor.
     *
     * @param employer EmployerInformation to copy
     */
    public EmployerInformation(final EmployerInformation employer) {
        if (employer != null) {
            name = employer.name;
            address = employer.address;
            address2 = employer.address2;
            business = employer.business;
            employeesCount = employer.employeesCount;
            website = employer.website;
            workingPlace = employer.workingPlace;
            nearestAirport = employer.nearestAirport;
            nearestPubTransport = employer.nearestPubTransport;
            weeklyHours = employer.weeklyHours;
            dailyHours = employer.dailyHours;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(final String business) {
        this.business = business;
    }

    public Integer getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(final Integer employeesCount) {
        this.employeesCount = employeesCount;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(final String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getNearestAirport() {
        return nearestAirport;
    }

    public void setNearestAirport(final String nearestAirport) {
        this.nearestAirport = nearestAirport;
    }

    public String getNearestPubTransport() {
        return nearestPubTransport;
    }

    public void setNearestPubTransport(final String nearestPubTransport) {
        this.nearestPubTransport = nearestPubTransport;
    }

    public Float getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(final Float weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Float getDailyHours() {
        return dailyHours;
    }

    public void setDailyHours(final Float dailyHours) {
        this.dailyHours = dailyHours;
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    /** {@inheritDoc} */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> errors = new HashMap<>(0);

        isNotNull(errors, "weeklyHours", weeklyHours);
        isNotNullOrEmpty(errors, "name", name);

        return errors;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final EmployerInformation employer = (EmployerInformation) obj;

        if (name != null ? !name.equals(employer.name) : employer.name != null) {
            return false;
        }
        if (address != null ? !address.equals(employer.address) : employer.address != null) {
            return false;
        }
        if (address2 != null ? !address2.equals(employer.address2) : employer.address2 != null) {
            return false;
        }
        if (business != null ? !business.equals(employer.business) : employer.business != null) {
            return false;
        }
        if (employeesCount != null ? !employeesCount.equals(employer.employeesCount) : employer.employeesCount != null) {
            return false;
        }
        if (website != null ? !website.equals(employer.website) : employer.website != null) {
            return false;
        }
        if (nearestAirport != null ? !nearestAirport.equals(employer.nearestAirport) : employer.nearestAirport != null) {
            return false;
        }
        if (nearestPubTransport != null ? !nearestPubTransport.equals(employer.nearestPubTransport) : employer.nearestPubTransport != null) {
            return false;
        }
        if (weeklyHours != null ? !weeklyHours.equals(employer.weeklyHours) : employer.weeklyHours != null) {
            return false;
        }
        if (workingPlace != null ? !workingPlace.equals(employer.workingPlace) : employer.workingPlace != null) {
            return false;
        }

        return !(dailyHours != null ? !dailyHours.equals(employer.dailyHours) : employer.dailyHours != null);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (name != null ? name.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (address != null ? address.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (address2 != null ? address2.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (business != null ? business.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (employeesCount != null ? employeesCount.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (website != null ? website.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (workingPlace != null ? workingPlace.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (nearestAirport != null ? nearestAirport.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (nearestPubTransport != null ? nearestPubTransport.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (weeklyHours != null ? weeklyHours.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (dailyHours != null ? dailyHours.hashCode() : 0);

        return hash;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "EmployerInformation{" +
                "name=" + name +
                ", address='" + address + '\'' +
                ", address2='" + address2 + '\'' +
                ", business='" + business + '\'' +
                ", employeesCount=" + employeesCount +
                ", website='" + website + '\'' +
                ", workingPlace='" + workingPlace + '\'' +
                ", nearestAirport='" + nearestAirport + '\'' +
                ", nearestPubTransport='" + nearestPubTransport + '\'' +
                ", weeklyHours=" + weeklyHours +
                ", dailyHours=" + dailyHours +
                '}';
    }
}
