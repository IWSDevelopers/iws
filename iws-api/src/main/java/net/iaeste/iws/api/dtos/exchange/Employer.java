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
package net.iaeste.iws.api.dtos.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default Employer Object, which is used as part of an Offer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "employer", propOrder = { "employerId", "group", "name", "department", "business", "address", "employeesCount", "website", "workingPlace", "canteen", "nearestAirport", "nearestPublicTransport", "offerReferenceNumbers", "modified", "created" })
public final class Employer extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The maximum length for Employer related Strings.
     */
    public static final int FIELD_LENGTH = 255;

    @XmlElement(required = true, nillable = true)  private String employerId = null;
    @XmlElement(required = true, nillable = false) private Group group = null;
    @XmlElement(required = true, nillable = false) private String name = null;
    @XmlElement(required = true, nillable = false) private String department = "";
    @XmlElement(required = true, nillable = true)  private String business = null;
    @XmlElement(required = true, nillable = false) private Address address = null;
    @XmlElement(required = true, nillable = true)  private String employeesCount = null;
    @XmlElement(required = true, nillable = true)  private String website = null;
    @XmlElement(required = true, nillable = false) private String workingPlace = null;
    @XmlElement(required = true, nillable = true)  private Boolean canteen = null;
    @XmlElement(required = true, nillable = true)  private String nearestAirport = null;
    @XmlElement(required = true, nillable = true)  private String nearestPublicTransport = null;
    // Following fields is reporting fields, and not part of the actual
    // contract, hence they are not part of the equals, hashCode or toString
    // methods
    @XmlElement(required = false, nillable = true) private List<String> offerReferenceNumbers = new ArrayList<>();
    @XmlElement(required = true, nillable = true)  private DateTime modified = null;
    @XmlElement(required = true, nillable = true)  private DateTime created = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Employer() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Copy Constructor.
     *
     * @param employer Employer Object to copy
     */
    public Employer(final Employer employer) {
        if (employer != null) {
            setEmployerId(employer.employerId);
            group = (employer.group != null) ? new Group(employer.group) : null;
            setName(employer.name);
            setDepartment(employer.department);
            setBusiness(employer.business);
            address = (employer.address != null) ? new Address(employer.address) : null;
            setEmployeesCount(employer.employeesCount);
            setWebsite(employer.website);
            setWorkingPlace(employer.workingPlace);
            this.canteen = employer.canteen;
            setNearestAirport(employer.nearestAirport);
            setNearestPublicTransport(employer.nearestPublicTransport);
            setOfferReferenceNumbers(employer.offerReferenceNumbers);
            setModified(employer.modified);
            setCreated(employer.created);
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Employer Id, which is the internally generated key for this
     * Object. Note, that the presence of the value will determine if the IWS
     * should process this record as if it exist or not. If the Id is set, but
     * no record exists, then the system will reply with an error. Likewise, if
     * no Id is provided, but the record exists, the system will reply with an
     * error.</p>
     *
     * <p>The value must be a valid Id, otherwise the method will throw an
     * {@code IllegalArgumentException}.</p>
     *
     * @param employerId Employer Id
     * @throws IllegalArgumentException if the Id is set but invalid
     * @see AbstractVerification#UUID_FORMAT
     */
    public void setEmployerId(final String employerId) {
        ensureValidId("employerId", employerId);
        this.employerId = employerId;
    }

    public String getEmployerId() {
        return employerId;
    }

    /**
     * <p>Sets the Employer Group. The Group is automatically set by the IWS
     * upon initial persisting of the Employer.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the Group
     * is not valid, i.e. if the Group is either null or not verifiable.</p>
     *
     * @param group National Group, which this Employer belongs to
     * @throws IllegalArgumentException if null or not valid
     */
    public void setGroup(final Group group) {
        ensureNotNullAndVerifiable("group", group);
        this.group = new Group(group);
    }

    public Group getGroup() {
        return new Group(group);
    }

    /**
     * <p>Sets the Employer Name. The name should be unique for the Employer, as
     * it is used in the IW4 for listing existing employers to avoid having to
     * tip in all details again.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the Name
     * is not valid.</p>
     *
     * @param name Employer Name
     * @throws IllegalArgumentException if not valid, i.e. either null or too long
     * @see #FIELD_LENGTH
     */
    public void setName(final String name) {
        ensureNotNullOrEmptyOrTooLong("name", name, FIELD_LENGTH);
        this.name = sanitize(name);
    }

    public String getName() {
        return name;
    }

    /**
     * <p>Sets the Employer Business. There are no rules applies to the Employer
     * Business, meaning that it any value, as long as it doesn't exceed the
     * maximum length.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * Business is not valid.</p>
     *
     * @param business Employer Business
     * @throws IllegalArgumentException if not valid, i.e. too long
     * @see #FIELD_LENGTH
     */
    public void setBusiness(final String business) {
        ensureNotTooLong("business", business, FIELD_LENGTH);
        this.business = sanitize(business);
    }

    public String getBusiness() {
        return business;
    }

    /**
     * <p>Sets the Employer Department. The Department is part of the uniqueness
     * criteria for the Employer, it may be empty but cannot be null or too
     * long.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * Department is not valid, i.e. null or too long.</p>
     *
     * @param department Employer Department
     * @throws IllegalArgumentException if not valid, i.e. null or too long
     * @see #FIELD_LENGTH
     */
    public void setDepartment(final String department) {
        ensureNotNullOrTooLong("department", department, FIELD_LENGTH);
        this.department = sanitize(department);
    }

    public String getDepartment() {
        return department;
    }

    /**
     * <p>Sets the Employer Address. The Address is an optional information
     * related to the Employer.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * Address is not valid.</p>
     *
     * @param address Employer Address
     * @throws IllegalArgumentException if not valid
     */
    public void setAddress(final Address address) {
        ensureVerifiable("address", address);
        this.address = new Address(address);
    }

    public Address getAddress() {
        return new Address(address);
    }

    /**
     * <p>Sets the Employer Employees Count. There are not rules applied to the
     * values of this field, as long as it does not exceed 25 characters.</p>
     *
     * <p>The method will thrown an {@code IllegalArgumentException} if the
     * employees Count value is too long.</p>
     *
     * @param employeesCount Employer Employees Count
     * @throws IllegalArgumentException if the field is longer than 25 characters
     */
    public void setEmployeesCount(final String employeesCount) {
        ensureNotTooLong("employeesCount", employeesCount, 25);
        this.employeesCount = sanitize(employeesCount);
    }

    public String getEmployeesCount() {
        return employeesCount;
    }

    /**
     * <p>Sets the Employer Website. There are no rules applies to the Employer
     * Website, meaning that it any value, as long as it doesn't exceed the
     * maximum length.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * Website is not valid.</p>
     *
     * @param website Employer Website
     * @throws IllegalArgumentException if not valid, i.e. too long
     * @see #FIELD_LENGTH
     */
    public void setWebsite(final String website) {
        ensureNotTooLong("website", website, FIELD_LENGTH);
        this.website = sanitize(website);
    }

    public String getWebsite() {
        return website;
    }

    /**
     * <p>Sets the Employer Working Place. The Working Place is part of the
     * uniqueness criteria for the Employer, it may be empty but cannot be null
     * or too long.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * Working Place is not valid, i.e. null or too long.</p>
     *
     * @param workingPlace Employer Working Place
     * @throws IllegalArgumentException if not valid, i.e. null or too long
     * @see #FIELD_LENGTH
     */
    public void setWorkingPlace(final String workingPlace) {
        ensureNotNullOrTooLong("workingPlace", workingPlace, FIELD_LENGTH);
        this.workingPlace = sanitize(workingPlace);
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    /**
     * Sets the Employer Canteen value. If set to true, then a Canteen exists,
     * otherwise no Canteen exists.
     *
     * @param canteen Employer Canteen available
     */
    public void setCanteen(final Boolean canteen) {
        this.canteen = canteen;
    }

    public Boolean getCanteen() {
        return canteen;
    }

    /**
     * <p>Sets the Employer Nearest Airport. There are no rules applies to the
     * Employer Nearest Airport, meaning that it any value, as long as it
     * doesn't exceed the maximum length.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * Nearest Airport is not valid.</p>
     *
     * @param nearestAirport Employer Nearest Airport
     * @throws IllegalArgumentException if not valid, i.e. too long
     * @see #FIELD_LENGTH
     */
    public void setNearestAirport(final String nearestAirport) {
        ensureNotTooLong("nearestAirport", nearestAirport, FIELD_LENGTH);
        this.nearestAirport = sanitize(nearestAirport);
    }

    public String getNearestAirport() {
        return nearestAirport;
    }

    /**
     * <p>Sets the Employer Nearest Public Transport. There are no rules applies
     * to the Employer Nearest Public Transport, meaning that it any value, as
     * long as it doesn't exceed the maximum length.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * Nearest Public Transport is not valid.</p>
     *
     * @param nearestPublicTransport Employer Nearest Public Transport
     * @throws IllegalArgumentException if not valid, i.e. too long
     * @see #FIELD_LENGTH
     */
    public void setNearestPublicTransport(final String nearestPublicTransport) {
        ensureNotTooLong("nearestPublicTransport", nearestPublicTransport, FIELD_LENGTH);
        this.nearestPublicTransport = sanitize(nearestPublicTransport);
    }

    public String getNearestPublicTransport() {
        return nearestPublicTransport;
    }

    /**
     * <p>If requested, then this will be a list of all the Offer's listed by
     * their Reference number's, which is registered with Employer in the
     * IWS.</p>
     *
     * <p>Note; this is a reporting field, which means that it is ignored by the
     * IWS.</p>
     *
     * @param offerReferenceNumbers List of Offer Reference Numbers
     */
    public void setOfferReferenceNumbers(final List<String> offerReferenceNumbers) {
        this.offerReferenceNumbers = immutableList(offerReferenceNumbers);
    }

    public List<String> getOfferReferenceNumbers() {
        return immutableList(offerReferenceNumbers);
    }

    /**
     * Sets the Employer latest modification DateTime. Note, this field is
     * controlled by the IWS, and cannot be altered by users.
     *
     * @param modified DateTime of latest modification
     */
    public void setModified(final DateTime modified) {
        this.modified = new DateTime(modified);
    }

    public DateTime getModified() {
        return modified;
    }

    /**
     * Sets the Employer Creation DateTime. Note, this field is controlled by
     * the IWS, and cannot be altered by users.
     *
     * @param created Employer Creation DateTime
     */
    public void setCreated(final DateTime created) {
        this.created = new DateTime(created);
    }

    public DateTime getCreated() {
        return created;
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

        isVerifiable(validation, "group", group);
        isNotNull(validation, "name", name);
        isNotNull(validation, "department", department);
        isVerifiable(validation, "address", address);
        isNotNull(validation, "workingPlace", workingPlace);

        return validation;
    }
}
