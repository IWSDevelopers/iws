/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
            employerId = employer.employerId;
            group = employer.group != null ? new Group(employer.group) : null;
            name = employer.name;
            department = employer.department;
            business = employer.business;
            address = employer.address != null ? new Address(employer.address) : null;
            employeesCount = employer.employeesCount;
            website = employer.website;
            workingPlace = employer.workingPlace;
            canteen = employer.canteen;
            nearestAirport = employer.nearestAirport;
            nearestPublicTransport = employer.nearestPublicTransport;
            offerReferenceNumbers = employer.offerReferenceNumbers;
            modified = employer.modified;
            created = employer.created;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Employer Id, which is the internally generated key for this
     * Object. Note, that the presence of the value will determine if the IWS
     * should process this record as if it exist or not. If the Id is set, but
     * no record exists, then the system will reply with an error. Likewise, if
     * no Id is provided, but the record exists, the system will reply with an
     * error.<br />
     *   The value must be a valid Id, otherwise the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param employerId Employer Id
     * @throws IllegalArgumentException if the Id is set but invalid
     * @see AbstractVerification#UUID_FORMAT
     */
    public void setEmployerId(final String employerId) throws IllegalArgumentException {
        ensureValidId("employerId", employerId);
        this.employerId = employerId;
    }

    public String getEmployerId() {
        return employerId;
    }

    /**
     * Sets the Employer Group. The Group is automatically set by the IWS upon
     * initial persisting of the Employer.<br />
     *   The method will throw an {@code IllegalArgumentException} if the Group
     * is not valid, i.e. if the Group is either null or not verifiable.
     *
     * @param group National Group, which this Employer belongs to
     * @throws IllegalArgumentException if null or not valid
     */
    public void setGroup(final Group group) throws IllegalArgumentException {
        ensureNotNullAndVerifiable("group", group);
        this.group = new Group(group);
    }

    public Group getGroup() {
        return new Group(group);
    }

    /**
     * Sets the Employer Name. The name should be unique for the Employer, as
     * it is used in the IW4 for listing existing employers to avoid having to
     * tip in all details again.<br />
     *   The method will throw an {@code IllegalArgumentException} if the Name
     * is not valid.
     *
     * @param name Employer Name
     * @throws IllegalArgumentException if not valid, i.e. either null or too long
     * @see #FIELD_LENGTH
     */
    public void setName(final String name) throws IllegalArgumentException {
        ensureNotNullOrEmptyOrTooLong("name", name, FIELD_LENGTH);
        this.name = sanitize(name);
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the Employer Business. There are no rules applies to the Employer
     * Business, meaning that it any value, as long as it doesn't exceed the
     * maximum length.<br />
     *   The method will throw an {@code IllegalArgumentException} if the
     * Business is not valid.
     *
     * @param business Employer Business
     * @throws IllegalArgumentException if not valid, i.e. too long
     * @see #FIELD_LENGTH
     */
    public void setBusiness(final String business) throws IllegalArgumentException {
        ensureNotTooLong("business", business, FIELD_LENGTH);
        this.business = sanitize(business);
    }

    public String getBusiness() {
        return business;
    }

    /**
     * Sets the Employer Department. The Department is part of the uniqueness
     * criteria for the Employer, it may be empty but cannot be null or too
     * long.<br />
     *   The method will throw an {@code IllegalArgumentException} if the
     * Department is not valid, i.e. null or too long.
     *
     * @param department Employer Department
     * @throws IllegalArgumentException if not valid, i.e. null or too long
     * @see #FIELD_LENGTH
     */
    public void setDepartment(final String department) throws IllegalArgumentException {
        ensureNotNullOrTooLong("department", department, FIELD_LENGTH);
        this.department = sanitize(department);
    }

    public String getDepartment() {
        return department;
    }

    /**
     * Sets the Employer Address. The Address is an optional information related
     * to the Employer.<br />
     *   The method will throw an {@code IllegalArgumentException} if the
     * Address is not valid.
     *
     * @param address Employer Address
     * @throws IllegalArgumentException if not valid
     */
    public void setAddress(final Address address) throws IllegalArgumentException {
        ensureVerifiable("address", address);
        this.address = new Address(address);
    }

    public Address getAddress() {
        return new Address(address);
    }

    /**
     * Sets the Employer Employees Count. Ther eare not rules applied to the
     * values of this field, as long as it does not exceed 25 characters.<br />
     *   The method will thrown an {@code IllegalArgumentException} if the
     * employees Count value is too long.
     *
     * @param employeesCount Employer Employees Count
     * @throws IllegalArgumentException if the field is longer than 25 characters
     */
    public void setEmployeesCount(final String employeesCount) throws IllegalArgumentException {
        ensureNotTooLong("employeesCount", employeesCount, 25);
        this.employeesCount = sanitize(employeesCount);
    }

    public String getEmployeesCount() {
        return employeesCount;
    }

    /**
     * Sets the Employer Website. There are no rules applies to the Employer
     * Website, meaning that it any value, as long as it doesn't exceed the
     * maximum length.<br />
     *   The method will throw an {@code IllegalArgumentException} if the
     * Website is not valid.
     *
     * @param website Employer Website
     * @throws IllegalArgumentException if not valid, i.e. too long
     * @see #FIELD_LENGTH
     */
    public void setWebsite(final String website) throws IllegalArgumentException {
        ensureNotTooLong("website", website, FIELD_LENGTH);
        this.website = sanitize(website);
    }

    public String getWebsite() {
        return website;
    }

    /**
     * Sets the Employer Working Place. The Working Place is part of the
     * uniqueness criteria for the Employer, it may be empty but cannot be null
     * or too long.<br />
     *   The method will throw an {@code IllegalArgumentException} if the
     * Working Place is not valid, i.e. null or too long.
     *
     * @param workingPlace Employer Working Place
     * @throws IllegalArgumentException if not valid, i.e. null or too long
     * @see #FIELD_LENGTH
     */
    public void setWorkingPlace(final String workingPlace) throws IllegalArgumentException {
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
     * Sets the Employer Nearest Airport. There are no rules applies to the
     * Employer Nearest Airport, meaning that it any value, as long as it
     * doesn't exceed the maximum length.<br />
     *   The method will throw an {@code IllegalArgumentException} if the
     * Nearest Airport is not valid.
     *
     * @param nearestAirport Employer Nearest Airport
     * @throws IllegalArgumentException if not valid, i.e. too long
     * @see #FIELD_LENGTH
     */
    public void setNearestAirport(final String nearestAirport) throws IllegalArgumentException {
        ensureNotTooLong("nearestAirport", nearestAirport, FIELD_LENGTH);
        this.nearestAirport = sanitize(nearestAirport);
    }

    public String getNearestAirport() {
        return nearestAirport;
    }

    /**
     * Sets the Employer Nearest Public Transport. There are no rules applies to
     * the Employer Nearest Public Transport, meaning that it any value, as long
     * as it doesn't exceed the maximum length.<br />
     *   The method will throw an {@code IllegalArgumentException} if the
     * Nearest Public Transport is not valid.
     *
     * @param nearestPublicTransport Employer Nearest Public Transport
     * @throws IllegalArgumentException if not valid, i.e. too long
     * @see #FIELD_LENGTH
     */
    public void setNearestPublicTransport(final String nearestPublicTransport) throws IllegalArgumentException {
        ensureNotTooLong("nearestPublicTransport", nearestPublicTransport, FIELD_LENGTH);
        this.nearestPublicTransport = sanitize(nearestPublicTransport);
    }

    public String getNearestPublicTransport() {
        return nearestPublicTransport;
    }

    /**
     * If requested, then this will be a list of all the Offer's listed by their
     * Reference number's, which is registered with Employer in the IWS.<br />
     *   Note; this is a reporting field, which means that it is ignored by the
     * IWS.
     *
     * @param offerReferenceNumbers List of Offer Reference Numbers
     */
    public void setOfferReferenceNumbers(List<String> offerReferenceNumbers) {
        this.offerReferenceNumbers = offerReferenceNumbers;
    }

    public List<String> getOfferReferenceNumbers() {
        return offerReferenceNumbers;
    }

    /**
     * Sets the Employer latest modification DateTime. Note, this field is
     * controlled by the IWS, and cannot be altered by users.
     *
     * @param modified DateTime of latest modification
     */
    public void setModified(DateTime modified) {
        this.modified = modified;
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
    public void setCreated(DateTime created) {
        this.created = created;
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

        if ((employerId != null) ? !employerId.equals(employer.employerId) : (employer.employerId != null)) {
            return false;
        }
        if ((group != null) ? !group.equals(employer.group) : (employer.group != null)) {
            return false;
        }
        if ((name != null) ? !name.equals(employer.name) : (employer.name != null)) {
            return false;
        }
        if ((department != null) ? !department.equals(employer.department) : (employer.department != null)) {
            return false;
        }
        if ((business != null) ? !business.equals(employer.business) : (employer.business != null)) {
            return false;
        }
        if ((address != null) ? !address.equals(employer.address) : (employer.address != null)) {
            return false;
        }
        if ((employeesCount != null) ? !employeesCount.equals(employer.employeesCount) : (employer.employeesCount != null)) {
            return false;
        }
        if ((website != null) ? !website.equals(employer.website) : (employer.website != null)) {
            return false;
        }
        if ((workingPlace != null) ? !workingPlace.equals(employer.workingPlace) : (employer.workingPlace != null)) {
            return false;
        }
        if ((canteen != null) ? !canteen.equals(employer.canteen) : (employer.canteen != null)) {
            return false;
        }
        if ((nearestAirport != null) ? !nearestAirport.equals(employer.nearestAirport) : (employer.nearestAirport != null)) {
            return false;
        }

        return !((nearestPublicTransport != null) ? !nearestPublicTransport.equals(employer.nearestPublicTransport) : (employer.nearestPublicTransport != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((employerId != null) ? employerId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((group != null) ? group.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((name != null) ? name.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((department != null) ? department.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((business != null) ? business.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((address != null) ? address.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((employeesCount != null) ? employeesCount.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((website != null) ? website.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((workingPlace != null) ? workingPlace.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((canteen != null) ? canteen.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((nearestAirport != null) ? nearestAirport.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((nearestPublicTransport != null) ? nearestPublicTransport.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Employer{" +
                "employerId='" + employerId + '\'' +
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
                ", nearestPublicTransport='" + nearestPublicTransport + '\'' +
                '}';
    }
}
