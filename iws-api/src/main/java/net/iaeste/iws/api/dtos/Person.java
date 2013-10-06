/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Person
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
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;

import java.util.HashMap;
import java.util.Map;

/**
 * The Person Object contains the non-system specific information or private
 * information for a person. Although the Person is a core part of the User
 * Object, it is meant for all-round purposes.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyComplexMethod
 */
public final class Person extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Address address = null;
    private String alternateEmail = null;
    private String phone = null;
    private String mobile = null;
    private String fax = null;
    private Date birthday = null;
    private Gender gender = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Person() {
    }

    /**
     * Copy Constructor.
     *
     * @param person Person Object to copy
     */
    public Person(final Person person) {
        if (person != null) {
            address = person.address;
            alternateEmail = person.alternateEmail;
            phone = person.phone;
            mobile = person.mobile;
            fax = person.fax;
            birthday = person.birthday;
            gender = person.gender;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setAddress(final Address address) throws IllegalArgumentException {
        ensureVerifiable("address", address);
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAlternateEmail(final String alternateEmail) throws IllegalArgumentException {
        ensureValidEmail("alternateEmail", alternateEmail);
        this.alternateEmail = alternateEmail;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setPhone(final String phone) throws IllegalArgumentException {
        ensureNotTooLong("phone", phone, 25);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setMobile(final String mobile) throws IllegalArgumentException {
        ensureNotTooLong("mobile", mobile, 25);
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setFax(final String fax) throws IllegalArgumentException {
        ensureNotTooLong("fax", fax, 25);
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        // Since an Address is an optional Object, we're not going to make any
        // validity checks here
        return new HashMap<>(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Person)) {
            return false;
        }

        final Person person = (Person) obj;

        if (address != null ? !address.equals(person.address) : person.address != null) {
            return false;
        }
        if (alternateEmail != null ? !alternateEmail.equals(person.alternateEmail) : person.alternateEmail != null) {
            return false;
        }
        if (fax != null ? !fax.equals(person.fax) : person.fax != null) {
            return false;
        }
        if (mobile != null ? !mobile.equals(person.mobile) : person.mobile != null) {
            return false;
        }
        if (phone != null ? !phone.equals(person.phone) : person.phone != null) {
            return false;
        }
        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null) {
            return false;
        }

        return gender == person.gender;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (address != null ? address.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (alternateEmail != null ? alternateEmail.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (phone != null ? phone.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (mobile != null ? mobile.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (fax != null ? fax.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (birthday != null ? birthday.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (gender != null ? gender.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Person{" +
                "address=" + address +
                ", alternateEmail='" + alternateEmail + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", fax='" + fax + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                '}';
    }
}
