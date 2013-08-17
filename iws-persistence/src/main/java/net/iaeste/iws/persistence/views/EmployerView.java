/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.EmployerView
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.views;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CompareToUsesNonFinalVariable
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findEmployerByGroup",
                query = "select e from EmployerView e " +
                        "where e.groupId = :gid "),
        @NamedQuery(name = "view.findEmployerByGroupAndId",
                query = "select e from EmployerView e " +
                        "where e.groupId = :gid" +
                        "  and e.externalId = :id"),
        @NamedQuery(name = "view.findEmployerByGroupAndPartialName",
                query = "select e from EmployerView e " +
                        "where e.groupId = :gid" +
                        "  and lower(e.name) like :name")
})
@Table(name = "employer_view")
public class EmployerView extends AbstractView<EmployerView> {

    /** {@see IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private Long id = null;

    @Column(name = "external_id", insertable = false, updatable = false)
    private String externalId = null;

    @Column(name = "name", insertable = false, updatable = false)
    private String name = null;

    @Column(name = "department", insertable = false, updatable = false)
    private String department = null;

    @Column(name = "business", insertable = false, updatable = false)
    private String business = null;

    @Column(name = "number_of_employees", insertable = false, updatable = false)
    private Integer numberOfEmployees = null;

    @Column(name = "website", insertable = false, updatable = false)
    private String website = null;

    @Column(name = "working_place", insertable = false, updatable = false)
    private String workingPlace = null;

    @Column(name = "canteen", insertable = false, updatable = false)
    private Boolean canteen = null;

    @Column(name = "nearest_airport", insertable = false, updatable = false)
    private String nearestAirport = null;

    @Column(name = "nearest_pub_transport", insertable = false, updatable = false)
    private String nearestPublicTransport = null;

    @Column(name = "weekly_hours", insertable = false, updatable = false)
    private Float weeklyHours = null;

    @Column(name = "daily_hours", insertable = false, updatable = false)
    private Float dailyHours = null;

    @Column(name = "group_id", insertable = false, updatable = false)
    private Long groupId = null;

    @Column(name = "group_external_id", insertable = false, updatable = false)
    private String groupExternalId = null;

    @Column(name = "grouptype", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private GroupType groupType = null;

    @Column(name = "groupname", insertable = false, updatable = false)
    private String groupName = null;

    @Column(name = "address_id", insertable = false, updatable = false)
    private Long addressId = null;

    @Column(name = "address_external_id", insertable = false, updatable = false)
    private String addressExternalId = null;

    @Column(name = "street1", insertable = false, updatable = false)
    private String street1 = null;

    @Column(name = "street2", insertable = false, updatable = false)
    private String street2 = null;

    @Column(name = "zip", insertable = false, updatable = false)
    private String zip = null;

    @Column(name = "city", insertable = false, updatable = false)
    private String city = null;

    @Column(name = "state", insertable = false, updatable = false)
    private String state = null;

    @Column(name = "modified", insertable = false, updatable = false)
    private Date modified = null;

    @Column(name = "created", insertable = false, updatable = false)
    private Date created = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setBusiness(final String business) {
        this.business = business;
    }

    public String getBusiness() {
        return business;
    }

    public void setNumberOfEmployees(final Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setWorkingPlace(final String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setCanteen(final Boolean canteen) {
        this.canteen = canteen;
    }

    public Boolean getCanteen() {
        return canteen;
    }

    public void setNearestAirport(final String nearestAirport) {
        this.nearestAirport = nearestAirport;
    }

    public String getNearestAirport() {
        return nearestAirport;
    }

    public void setNearestPublicTransport(final String nearestPublicTransport) {
        this.nearestPublicTransport = nearestPublicTransport;
    }

    public String getNearestPublicTransport() {
        return nearestPublicTransport;
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

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupExternalId(final String groupExternalId) {
        this.groupExternalId = groupExternalId;
    }

    public String getGroupExternalId() {
        return groupExternalId;
    }

    public void setGroupType(final GroupType groupType) {
        this.groupType = groupType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setAddressId(final Long addressId) {
        this.addressId = addressId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressExternalId(final String addressExternalId) {
        this.addressExternalId = addressExternalId;
    }

    public String getAddressExternalId() {
        return addressExternalId;
    }

    public void setStreet1(final String street1) {
        this.street1 = street1;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet2(final String street2) {
        this.street2 = street2;
    }

    public String getStreet2() {
        return street2;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    // =========================================================================
    // Standard View Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof EmployerView)) {
            return false;
        }

        // As the view is reading from the current data model, and the Id is
        // always unique. It is sufficient to compare against this field.
        final EmployerView that = (EmployerView) obj;
        return !(id != null ? !id.equals(that.id) : that.id != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final EmployerView o) {
        final int result = id.compareTo(o.id);

        return sortAscending ? result : -result;
    }
}
