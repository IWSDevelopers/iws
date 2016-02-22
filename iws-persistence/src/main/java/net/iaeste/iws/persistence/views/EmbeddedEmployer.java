/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public final class EmbeddedEmployer {

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
