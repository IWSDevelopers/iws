/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Employer
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
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.Verifiable;
import net.iaeste.iws.api.responses.AbstractResponse;

import java.util.*;

/**
 * The Employer Object contains all information about the employer.
 *
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class Employer extends AbstractResponse implements Verifiable {

    /**
     * {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Empty Constructor, required for some communication frameworks.
     */
    public Employer() {
    }

    /**
     * Copy constructor.
     *
     * @param employer Employer to copy
     */
    public Employer(final Employer employer) {
        if (employer != null) {
            this.setName(employer.getName());
            this.setAddress(employer.getAddress());
            this.setAddress2(employer.getAddress2());
            this.setBusiness(employer.getBusiness());
            this.setEmployeesCount(employer.getEmployeesCount());
            this.setWebsite(employer.getWebsite());
            this.setWorkingPlace(employer.getWorkingPlace());
            this.setNearestAirport(employer.getNearestAirport());
            this.setNearestPubTransport(employer.getNearestPubTransport());
            this.setWeeklyHours(employer.getWeeklyHours());
            this.setDailyHours(employer.getDailyHours());
        }
    }

    private String name;
    private String address;
    private String address2;
    private String business;
    private Integer employeesCount;
    private String website;
    private String workingPlace;
    private String nearestAirport;
    private String nearestPubTransport;
    private Float weeklyHours;
    private Float dailyHours;

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

    /**
     * {@inheritDoc}
     *
     * @param o
     * @return
     */
    @SuppressWarnings("OverlyLongMethod")
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Employer employer = (Employer) o;

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
        if (dailyHours != null ? !dailyHours.equals(employer.dailyHours) : employer.dailyHours != null) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("OverlyLongMethod")
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Employer{" +
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
        final Collection<String> errors = new ArrayList<>();

        errors.addAll(verifyNotNullableFields());
        if (!errors.isEmpty()) {
            throw new VerificationException(errors.toString());
        }
    }

    /**
     * Checks for nulls in required fields.
     *
     * @return collection of errors. If all required fields are provided, method returns empty collection.
     */
    private Collection<String> verifyNotNullableFields() {
        final Collection<String> errors = new ArrayList<>();
        if (weeklyHours == null) {
            errors.add("'weeklyHours' is missing");
        }
        if (name == null || name.length() == 0) {
            errors.add("'name' is missing");
        }
        return errors;
    }

}