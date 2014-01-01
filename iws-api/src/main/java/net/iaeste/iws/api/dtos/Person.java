/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

    /**
     * Sets the Address of a Person. The Address is optional, but if set, then
     * it must be a valid Object. If not, then the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param address Address for the Person
     * @throws IllegalArgumentException
     */
    public void setAddress(final Address address) throws IllegalArgumentException {
        ensureVerifiable("address", address);
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Sets an alternate e-mail address, which can be used if the person is
     * unreachable via the normal e-mail address. The alternate e-mal address
     * is optional, but if set - then it must be a valid address. The system
     * will not verify if the address works, only that it is a correct e-mail
     * address. If not, then the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param alternateEmail Alternate E-mail address for the Person
     * @throws IllegalArgumentException if the e-mail is incorrect
     */
    public void setAlternateEmail(final String alternateEmail) throws IllegalArgumentException {
        ensureValidEmail("alternateEmail", alternateEmail);
        this.alternateEmail = alternateEmail;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    /**
     * Sets the Person's landline phone number. The number is optional, but if
     * set, then the length cannot exceed 25 characters. If the phone number
     * exceeds the maximum allowed number of characters, then the method will
     * throw an {@code IllegalArgumentException}.
     *
     * @param phone Person's Landline Phonenumber
     * @throws IllegalArgumentException if the phonenumber exceeds 25 characters
     */
    public void setPhone(final String phone) throws IllegalArgumentException {
        ensureNotTooLong("phone", phone, 25);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * Sets the Person's Mobile phone number. The number is optional, but if
     * set, then the length cannot exceed 25 characters. If the mobile number
     * exceeds the maximum allowed number of characters, then the method will
     * throw an {@code IllegalArgumentException}.
     *
     * @param mobile Person's Mobile Phonenumber
     * @throws IllegalArgumentException if the mobile number exceeds 25 characters
     */
    public void setMobile(final String mobile) throws IllegalArgumentException {
        ensureNotTooLong("mobile", mobile, 25);
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    /**
     * Sets the Person's Fax number. The number is optional, but if set, then
     * the maximum allowed length cannot exceed 25 characters. If the fax number
     * exceeds the maximum allowed number of characters, then the method will
     * throw an {@code IllegalArgumentException}.
     *
     * @param fax Person's Fax Number
     * @throws IllegalArgumentException if the fax number exceeds 25 characters
     */
    public void setFax(final String fax) throws IllegalArgumentException {
        ensureNotTooLong("fax", fax, 25);
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    /**
     * Sets the Person's Date of Birth. The information is optional, and there
     * is not made any checks for the validity of this value.
     *
     * @param birthday Person's Date of Birth
     */
    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    /**
     * Sets the Person's Gender. The information is optional.
     *
     * @param gender Person's Gender (Male or Female)
     */
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
