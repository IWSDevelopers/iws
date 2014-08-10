/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.EmbeddedEmployer
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

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * This is the Embedded Employer Object, which is used by various Views. The
 * intentions of this Object, is to have a unified way of dealing with the
 * read-only Group, so only a single DTO mapping instance is required.
 *   If any one view require more information, then all views must be extended
 * with this. All Employer information must be prefixed with "employer_" in the
 * view.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Embeddable
public class EmbeddedEmployer {

    @Column(name = "employer_external_id", insertable = false, updatable = false)
    private String externalId = null;

    @Column(name = "employer_name", insertable = false, updatable = false)
    private String name = null;

    @Column(name = "employer_department", insertable = false, updatable = false)
    private String department = null;

    @Column(name = "employer_business", insertable = false, updatable = false)
    private String business = null;

    @Column(name = "employer_number_of_employees", insertable = false, updatable = false)
    private String numberOfEmployees = null;

    @Column(name = "employer_website", insertable = false, updatable = false)
    private String website = null;

    @Column(name = "employer_working_place", insertable = false, updatable = false)
    private String workingPlace = null;

    @Column(name = "employer_canteen", insertable = false, updatable = false)
    private Boolean canteen = null;

    @Column(name = "employer_nearest_airport", insertable = false, updatable = false)
    private String nearestAirport = null;

    @Column(name = "employer_nearest_public_transport", insertable = false, updatable = false)
    private String nearestPublicTransport = null;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "employer_modified", insertable = false, updatable = false)
//    private Date modified = null;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "employer_created", insertable = false, updatable = false)
//    private Date created = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

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

    public void setNumberOfEmployees(final String numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getNumberOfEmployees() {
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

//    public void setModified(final Date modified) {
//        this.modified = modified;
//    }
//
//    public Date getModified() {
//        return modified;
//    }
//
//    public void setCreated(final Date created) {
//        this.created = created;
//    }
//
//    public Date getCreated() {
//        return created;
//    }
}
