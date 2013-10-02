/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.exchange.EmployerEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities.exchange;

import net.iaeste.iws.common.monitoring.Monitored;
import net.iaeste.iws.common.monitoring.MonitoringLevel;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.entities.AbstractUpdateable;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Employer Entity, contains the common fields for the Offers for a given
 * Employer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries({
        @NamedQuery(
                name = "employer.findAllForGroup",
                query = "select e from EmployerEntity e " +
                        "where e.group.id = :gid"),
        @NamedQuery(
                name = "employer.findByExternalId",
                query = "select e from EmployerEntity e " +
                        "where e.externalId = :eid"),
        @NamedQuery(
                name = "employer.findEmployerByValues",
                query = "select e from EmployerEntity e " +
                        "where e.group.id = :gid" +
                        "  and lower(e.name) = lower(:name)" +
                        // Why we cannot use a lowe on the Department is a mystery
                        //"  and e.department = :department" +
                        "  and lower(e.workingPlace) = lower(:workingPlace)")
})
@Entity
@Table(name = "employers")
@Monitored(name = "Employer", level = MonitoringLevel.DETAILED)
public class EmployerEntity extends AbstractUpdateable<EmployerEntity> implements Externable<EmployerEntity> {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "employer_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * The content of this Entity is exposed externally, however to avoid that
     * someone tries to spoof the system by second guessing our Sequence values,
     * An External Id is used, the External Id is a Uniqie UUID value, which in
     * all external references is referred to as the "Id". Although this can be
     * classified as StO (Security through Obscrutity), there is no need to
     * expose more information than necessary.
     */
    @Column(name = "external_id", length = 36, unique = true, nullable = false, updatable = false)
    private String externalId = null;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group = null;

    @Monitored(name="Employer name", level = MonitoringLevel.DETAILED)
    @Column(name = "name", length = 255, nullable = false)
    private String name = null;

    @Monitored(name="Employer department", level = MonitoringLevel.DETAILED)
    @Column(name = "department", length = 255)
    private String department = null;

    @Monitored(name="Employer business", level = MonitoringLevel.DETAILED)
    @Column(name = "business", length = 255)
    private String business = null;

    @Monitored(name="Employer working place", level = MonitoringLevel.DETAILED)
    @Column(name = "working_place")
    private String workingPlace = null;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address = null;

    @Monitored(name="Employer number of employees", level = MonitoringLevel.DETAILED)
    @Column(name = "number_of_employees")
    private Integer numberOfEmployees = null;

    @Monitored(name="Employer website", level = MonitoringLevel.DETAILED)
    @Column(name = "website", length = 255)
    private String website = null;

    @Monitored(name="Employer canteen", level = MonitoringLevel.DETAILED)
    @Column(name = "canteen", length = 255)
    private Boolean canteen = false;

    @Monitored(name="Employer nearest airport", level = MonitoringLevel.DETAILED)
    @Column(name = "nearest_airport", length = 255)
    private String nearestAirport = null;

    @Monitored(name="Employer nearest public transport", level = MonitoringLevel.DETAILED)
    @Column(name = "nearest_public_transport", length = 255)
    private String nearestPublicTransport = null;

    @Monitored(name="Employer weekly hours", level = MonitoringLevel.DETAILED)
    @Column(name = "weekly_hours", scale = 5, precision = 3)
    private Float weeklyHours = null;

    @Monitored(name="Employer daily hours", level = MonitoringLevel.DETAILED)
    @Column(name = "daily_hours", scale = 5, precision = 3)
    private Float dailyHours = null;

    /**
     * Last time the Entity was modified.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified = new Date();

    /**
     * Timestamp when the Entity was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    private Date created = new Date();

    // =========================================================================
    // Entity Constructors
    // =========================================================================

    /**
     * Empty Constructor, required by JPA.
     */
    public EmployerEntity() {
    }

    /**
     * Default Constructor for creating new Entities of this type.
     *
     * @param name  Name of the Employer, must be unique for the given Group
     * @param group National Group, which this Employer belongs to
     */
    public EmployerEntity(final String name, final GroupEntity group) {
        this.name = name;
        this.group = group;
    }

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExternalId() {
        return externalId;
    }

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
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

    public void setWorkingPlace(final String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setAddress(final AddressEntity address) {
        this.address = address;
    }

    public AddressEntity getAddress() {
        return address;
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

    public Float getDailyHours() {
        return dailyHours;
    }

    public void setDailyHours(final Float dailyHours) {
        this.dailyHours = dailyHours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModified(final Date modified) {
        this.modified = modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getModified() {
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getCreated() {
        return created;
    }

    // =========================================================================
    // Standard Entity Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final EmployerEntity obj) {
        int changes = 0;

        changes += different(name, obj.name);
        changes += different(department, obj.department);
        changes += different(business, obj.business);
        changes += different(workingPlace, obj.workingPlace);
        changes += different(numberOfEmployees, obj.numberOfEmployees);
        changes += different(website, obj.website);
        changes += different(canteen, obj.canteen);
        changes += different(nearestAirport, obj.nearestAirport);
        changes += different(nearestPublicTransport, obj.nearestPublicTransport);
        changes += different(weeklyHours, obj.weeklyHours);
        changes += different(dailyHours, obj.dailyHours);

        return changes == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final EmployerEntity obj) {
        // don't merge if objects are not the same entity
        if ((id != null) && (obj != null) && externalId.equals(obj.externalId)) {
            name = obj.name;
            department = obj.department;
            business = obj.business;
            workingPlace = obj.workingPlace;
            numberOfEmployees = obj.numberOfEmployees;
            website = obj.website;
            canteen = obj.canteen;
            nearestAirport = obj.nearestAirport;
            nearestPublicTransport = obj.nearestPublicTransport;
            weeklyHours = obj.weeklyHours;
            dailyHours = obj.dailyHours;
        }
    }
}
