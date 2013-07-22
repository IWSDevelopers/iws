/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.Employer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * Default Employer Object, which is used as part of an Offer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyComplexMethod
 */
public final class Employer extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * All fields of type String in this Object, are allowed to be as big as
     * this number.
     */
    private static final int FIELD_LENGTH = 255;

    private String id = null;
    private Group group = null;
    private String name = null;
    private String department = null;
    private String business = null;
    private Address address = null;
    private Integer employeesCount = null;
    private String website = null;
    private String workingPlace = null;
    private Boolean canteen = null;
    private String nearestAirport = null;
    private String nearestPubTransport = null;
    private Float weeklyHours = null;
    private Float dailyHours = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Employer() {
    }

    /**
     * Copy Constructor.
     *
     * @param employer Employer Object to copy
     */
    public Employer(final Employer employer) {
        if (employer != null) {
            id = employer.id;
            group = new Group(employer.group);
            name = employer.name;
            business = employer.business;
            address = new Address(employer.address);
            employeesCount = employer.employeesCount;
            website = employer.website;
            workingPlace = employer.workingPlace;
            canteen = employer.canteen;
            nearestAirport = employer.nearestAirport;
            nearestPubTransport = employer.nearestPubTransport;
            weeklyHours = employer.weeklyHours;
            dailyHours = employer.dailyHours;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setId(final String id) throws IllegalArgumentException {
        ensureValidId("id", id);

        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setGroup(final Group group) {
        this.group = new Group(group);
    }

    public Group getGroup() {
        return new Group(group);
    }

    public void setName(final String name) throws IllegalArgumentException {
        ensureNotNullOrEmptyOrTooLong("name", name, FIELD_LENGTH);

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBusiness(final String business) throws IllegalArgumentException {
        ensureNotTooLong("business", business, FIELD_LENGTH);

        this.business = business;
    }

    public String getBusiness() {
        return business;
    }

    public void setDepartment(final String department) throws IllegalArgumentException {
        ensureNotTooLong("department", department, FIELD_LENGTH);

        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setAddress(final Address address) {
        this.address = new Address(address);
    }

    public Address getAddress() {
        return new Address(address);
    }

    public void setEmployeesCount(final Integer employeesCount) {
        this.employeesCount = employeesCount;
    }

    public Integer getEmployeesCount() {
        return employeesCount;
    }

    public void setWebsite(final String website) throws IllegalArgumentException {
        ensureNotTooLong("website", website, FIELD_LENGTH);

        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setWorkingPlace(final String workingPlace) throws IllegalArgumentException {
        ensureNotTooLong("workingPlace", workingPlace, FIELD_LENGTH);

        this.workingPlace = workingPlace;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setCanteen(final Boolean canteen) {
        ensureNotNull("canteen", canteen);

        this.canteen = canteen;
    }

    public Boolean getCanteen() {
        return canteen;
    }

    public void setNearestAirport(final String nearestAirport) throws IllegalArgumentException {
        ensureNotTooLong("nearestAirport", nearestAirport, FIELD_LENGTH);

        this.nearestAirport = nearestAirport;
    }

    public String getNearestAirport() {
        return nearestAirport;
    }

    public void setNearestPubTransport(final String nearestPubTransport) throws IllegalArgumentException {
        ensureNotTooLong("nearestPubTransport", nearestPubTransport, FIELD_LENGTH);

        this.nearestPubTransport = nearestPubTransport;
    }

    public String getNearestPubTransport() {
        return nearestPubTransport;
    }

    public void setWeeklyHours(final Float weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Float getWeeklyHours() {
        return weeklyHours;
    }

    public void setDailyHours(final Float dailyHours) {
        this.dailyHours = dailyHours;
    }

    public Float getDailyHours() {
        return dailyHours;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "name", name);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Employer)) {
            return false;
        }

        final Employer employer = (Employer) obj;

        if (id != null ? !id.equals(employer.id) : employer.id != null) {
            return false;
        }
        if (group != null ? !group.equals(employer.group) : employer.group != null) {
            return false;
        }
        if (name != null ? !name.equals(employer.name) : employer.name != null) {
            return false;
        }
        if (department != null ? !department.equals(employer.department) : employer.department != null) {
            return false;
        }
        if (business != null ? !business.equals(employer.business) : employer.business != null) {
            return false;
        }
        if (address != null ? !address.equals(employer.address) : employer.address != null) {
            return false;
        }
        if (employeesCount != null ? !employeesCount.equals(employer.employeesCount) : employer.employeesCount != null) {
            return false;
        }
        if (website != null ? !website.equals(employer.website) : employer.website != null) {
            return false;
        }
        if (workingPlace != null ? !workingPlace.equals(employer.workingPlace) : employer.workingPlace != null) {
            return false;
        }
        if (canteen != null ? !canteen.equals(employer.canteen) : employer.canteen != null) {
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

        return !(dailyHours != null ? !dailyHours.equals(employer.dailyHours) : employer.dailyHours != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (id != null ? id.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (group != null ? group.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (name != null ? name.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (department != null ? department.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (business != null ? business.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (address != null ? address.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (employeesCount != null ? employeesCount.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (website != null ? website.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (workingPlace != null ? workingPlace.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (canteen != null ? canteen.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (nearestAirport != null ? nearestAirport.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (nearestPubTransport != null ? nearestPubTransport.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (weeklyHours != null ? weeklyHours.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (dailyHours != null ? dailyHours.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Employer{" +
                "id='" + id + '\'' +
                ", group='" + group + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", business='" + business + '\'' +
                ", address=" + address +
                ", employeesCount=" + employeesCount +
                ", website='" + website + '\'' +
                ", workingPlace='" + workingPlace + '\'' +
                ", canteen=" + canteen +
                ", nearestAirport='" + nearestAirport + '\'' +
                ", nearestPubTransport='" + nearestPubTransport + '\'' +
                ", weeklyHours=" + weeklyHours +
                ", dailyHours=" + dailyHours +
                '}';
    }
}
