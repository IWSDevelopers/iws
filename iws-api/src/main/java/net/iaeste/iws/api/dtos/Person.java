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
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.api.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * The Person Object contains the non-system specific information or private
 * information for a person. Although the Person is a core part of the User
 * Object, it is meant for all-round purposes.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "person", propOrder = { "nationality", "address", "alternateEmail", "phone", "mobile", "fax", "birthday", "gender", "understoodPrivacySettings", "acceptNewsletters" })
public final class Person extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private Country nationality = null;
    @XmlElement(required = true, nillable = true) private Address address = null;
    @XmlElement(required = true, nillable = true) private String alternateEmail = null;
    @XmlElement(required = true, nillable = true) private String phone = null;
    @XmlElement(required = true, nillable = true) private String mobile = null;
    @XmlElement(required = true, nillable = true) private String fax = null;
    @XmlElement(required = true, nillable = true) private Date birthday = null;
    @XmlElement(required = true, nillable = true) private Gender gender = null;
    @XmlElement(required = true, nillable = true) private Boolean understoodPrivacySettings = Boolean.FALSE;
    @XmlElement(required = true, nillable = true) private Boolean acceptNewsletters = Boolean.TRUE;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Person() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Copy Constructor.
     *
     * @param person Person Object to copy
     */
    public Person(final Person person) {
        if (person != null) {
            nationality = new Country(person.nationality);
            address = new Address(person.address);
            alternateEmail = person.alternateEmail;
            phone = person.phone;
            mobile = person.mobile;
            fax = person.fax;
            birthday = person.birthday;
            gender = person.gender;
            understoodPrivacySettings = person.understoodPrivacySettings;
            acceptNewsletters = person.acceptNewsletters;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Nationality of the user, the Nationality is an optional
     * field, though for certain internal processes, it is a required field.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the value
     * is not a verifiable Object.</p>
     *
     * @param nationality User Nationality
     * @throws IllegalArgumentException if not verifiable
     */
    public void setNationality(final Country nationality) {
        ensureVerifiable("nationality", nationality);
        this.nationality = nationality;
    }

    public Country getNationality() {
        return nationality;
    }

    /**
     * Sets the Address of a Person. The Address is optional, but if set, then
     * it must be a valid Object. If not, then the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param address Address for the Person
     * @throws IllegalArgumentException of not verifiable
     */
    public void setAddress(final Address address) {
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
    public void setAlternateEmail(final String alternateEmail) {
        ensureValidEmail("alternateEmail", alternateEmail);
        this.alternateEmail = alternateEmail;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    /**
     * Sets the Person's land line PhoneNumber. The number is optional, but if
     * set, then the length cannot exceed 25 characters. If the phone number
     * exceeds the maximum allowed number of characters, then the method will
     * throw an {@code IllegalArgumentException}.
     *
     * @param phone Person's Land line PhoneNumber
     * @throws IllegalArgumentException if the PhoneNumber exceeds 25 characters
     */
    public void setPhone(final String phone) {
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
     * @param mobile Person's Mobile PhoneNumber
     * @throws IllegalArgumentException if the mobile number exceeds 25 characters
     */
    public void setMobile(final String mobile) {
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
    public void setFax(final String fax) {
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

    /**
     * <p>Changing the Privacy settings for Files or Folders has the consequence
     * that these Object is generally accessible or viewable. This consequence
     * must be known and understood by the User, if not - then a pop-up should
     * appear explaining the consequences, and only by accepting this - will
     * the flag be set.</p>
     *
     * <p>The value must be set to either True or False, if it is set to Null,
     * then an {@code IllegalArgumentException} will be thrown.</p>
     *
     * @param understoodPrivacySettings Has the User understood the Privacy Settings
     * @throws IllegalArgumentException if set to null
     */
    public void setUnderstoodPrivacySettings(final Boolean understoodPrivacySettings) {
        ensureNotNull("understoodPrivacySettings", understoodPrivacySettings);
        this.understoodPrivacySettings = understoodPrivacySettings;
    }

    public Boolean getUnderstoodPrivacySettings() {
        return understoodPrivacySettings;
    }

    /**
     * <p>The Announce mailing list will go to all Active Members of the IAESTE,
     * except Students. The Announce list consists of announcements from the
     * Board or the IDT Administrators - but can also be a general newsletter
     * from the Staff. The announcements is not optional, but people may not
     * wish to receive newsletters, and by setting this flag, they won't. By
     * default, this flag is set to true, making this an opt-out.</p>
     *
     * <p>The value must be set to either True or False, if it is set to Null,
     * then an {@code IllegalArgumentException} will be thrown.</p>
     *
     * @param acceptNewsletters If the User accepts to receive Newsletters
     */
    public void setAcceptNewsletters(final Boolean acceptNewsletters) {
        ensureNotNull("acceptNewsletters", acceptNewsletters);
        this.acceptNewsletters = acceptNewsletters;
    }

    public Boolean getAcceptNewsletters() {
        return acceptNewsletters;
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
}
