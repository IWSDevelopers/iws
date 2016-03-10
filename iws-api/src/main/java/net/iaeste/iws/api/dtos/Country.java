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
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>The Country Object contains the information about a Country, i.e. the
 * name, two letter ISO code, IAESTE membership, etc.</p>
 *
 * <p>The Object is used for both fetching and updating/creating new Countries.
 * However, not all fields are updateable, the NS &amp; ListNames are controlled
 * by other mechanisms, but is listed here when fetching the information. For
 * Existing countries, only part of the information is allowed to be
 * updated.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "country", propOrder = { "countryCode", "countryName", "countryNameFull", "countryNameNative", "nationality", "citizens", "phonecode", "currency", "languages", "membership", "memberSince" })
public final class Country extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private String countryCode = null;
    @XmlElement(required = true, nillable = false) private String countryName = null;
    @XmlElement(required = true, nillable = true)  private String countryNameFull = null;
    @XmlElement(required = true, nillable = true)  private String countryNameNative = null;
    @XmlElement(required = true, nillable = true)  private String nationality = null;
    @XmlElement(required = true, nillable = true)  private String citizens = null;
    @XmlElement(required = true, nillable = true)  private String phonecode = null;
    @XmlElement(required = true, nillable = false) private Currency currency = null;
    @XmlElement(required = true, nillable = true)  private String languages = null;
    @XmlElement(required = true, nillable = true)  private Membership membership = null;
    @XmlElement(required = true, nillable = true)  private Integer memberSince = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Country() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Copy Constructor.
     *
     * @param country Country Object to copy
     */
    public Country(final Country country) {
        if (country != null) {
            countryCode = country.countryCode;
            countryName = country.countryName;
            countryNameFull = country.countryNameFull;
            countryNameNative = country.countryNameNative;
            nationality = country.nationality;
            citizens = country.citizens;
            phonecode = country.phonecode;
            currency = country.currency;
            languages = country.languages;
            membership = country.membership;
            memberSince = country.memberSince;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Country Code, i.e. the two letter "commonly used" code, which
     * follows the ISO-3166-1 alpha 2, with an Exception for the United Kingdom
     * or Great Britain, where the code used is UK.</p>
     *
     * <p>This method throws an {@code IllegalArgumentException} if the given
     * value is not a valid Country Code, i.e. it must not be null and the value
     * has to be two characters.</p>
     *
     * @param countryCode Country Code
     * @throws IllegalArgumentException If either null or not two characters long
     * @see <a href="https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2">ISO-3166-1 alpha-2</a>
     */
    public void setCountryCode(final String countryCode) {
        ensureNotNullAndExactLength("countryCode", countryCode, 2);
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    /**
     * <p>Sets the Country Name. This is the English variant of the Country
     * Name, and it cannot neither be null nor too long. The max length is set
     * to 100 characters.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the name
     * is invalid, i.e. null or too long.</p>
     *
     * @param countryName English name of the Country
     * @throws IllegalArgumentException if either null or too long
     */
    public void setCountryName(final String countryName) {
        ensureNotNullOrTooLong("countryName", countryName, 100);
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    /**
     * <p>Sets the Full Country Name. This is the complete name of the Country,
     * and it cannot neither be null nor too long. The max length is set to 100
     * characters.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the name
     * is invalid, i.e. null or too long.</p>
     *
     * @param countryNameFull Full Country Name
     * @throws IllegalArgumentException if either null or too long
     */
    public void setCountryNameFull(final String countryNameFull) {
        ensureNotTooLong("countryNameFull", countryNameFull, 100);
        this.countryNameFull = countryNameFull;
    }

    public String getCountryNameFull() {
        return countryNameFull;
    }

    /**
     * <p>Sets the Native Country Name. This is the name that the Country uses
     * for itself. It may be null, but if set, it cannot be longer than 100
     * characters.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the name
     * is too long.</p>
     *
     * @param countryNameNative Native Country Name
     * @throws IllegalArgumentException if the native name is too long
     */
    public void setCountryNameNative(final String countryNameNative) {
        ensureNotTooLong("countryNameNative", countryNameNative, 100);
        this.countryNameNative = countryNameNative;
    }

    public String getCountryNameNative() {
        return countryNameNative;
    }

    /**
     * <p>Sets the Nationality of the citizens of the Country. The given value
     * may maximum be 100 characters long. For example, the Nationality of
     * Germany is 'German'.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * value is null.</p>
     *
     * @param nationality The Nationality of the Citizens
     * @throws IllegalArgumentException if the given value is too long
     */
    public void setNationality(final String nationality) {
        ensureNotTooLong("nationality", nationality, 100);
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    /**
     * <p>Sets the name of the Citizens of the Country. The given value may
     * maximum be 100 characters long. For example, the Citizens of Germany are
     * 'Germans'.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * value is null.</p>
     *
     * @param citizens The name of the Citizens of the Country
     * @throws IllegalArgumentException if the given value is too long
     */
    public void setCitizens(final String citizens) {
        ensureNotTooLong("citizens", citizens, 100);
        this.citizens = citizens;
    }

    public String getCitizens() {
        return citizens;
    }

    /**
     * <p>Sets the PhoneCode of the Country. The value may be not be longer than
     * five (5) characters long.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * value is too long.</p>
     *
     * @param phonecode The official phone code of the country (+xxx)
     * @throws IllegalArgumentException if the given value is null
     */
    public void setPhonecode(final String phonecode) {
        ensureNotTooLong("phonecode", phonecode, 5);
        this.phonecode = phonecode;
    }

    public String getPhonecode() {
        return phonecode;
    }

    /**
     * <p>Sets the Currency of the Country. The value may not be null, as it is
     * used internally.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * value is null.</p>
     *
     * @param currency Official Currency of the Country
     * @throws IllegalArgumentException if the value is null
     */
    public void setCurrency(final Currency currency) {
        ensureNotNull("currency", currency);
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    /**
     * <p>Sets the official language(s) of the Country. The given value may
     * contain several Languages, but the maximum length may only be 100
     * characters.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * value is null.</p>
     *
     * @param languages Officially spoken language(s) of the Country
     * @throws IllegalArgumentException if the given value is null
     */
    public void setLanguages(final String languages) {
        ensureNotTooLong("languages", languages, 100);
        this.languages = languages;
    }

    public String getLanguages() {
        return languages;
    }

    /**
     * <p>Sets the type of Membership of the Country, if it is an IAESTE Member,
     * then the value can be either full member, associate member, Co-operating
     * Institution or former member. If neither of these values are applicable,
     * the default value will be a non-member.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * value is null.</p>
     *
     * @param membership Type of IAESTE Membership
     * @throws IllegalArgumentException if the given value is null
     */
    public void setMembership(final Membership membership) {
        ensureNotNull("membership", membership);
        this.membership = membership;
    }

    public Membership getMembership() {
        return membership;
    }

    /**
     * <p>Sets the Country's IAESTE Membership date, i.e. the year when the
     * country joined IAESTE. The year must be a valid IAESTE year, meaning
     * that it must be set to something between the founding year of IAESTE
     * (1948), and the present year.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * year is invalid, i.e. not within the limits (1948 - now).</p>
     *
     * @param memberSince IAESTE Membership Year
     * @throws IllegalArgumentException if the value is invalid
     * @see IWSConstants#FOUNDING_YEAR
     */
    public void setMemberSince(final Integer memberSince) {
        final int currentYear = new Date().getCurrentYear();
        ensureWithinLimits("memberSince", memberSince, IWSConstants.FOUNDING_YEAR, currentYear);
        this.memberSince = memberSince;
    }

    public Integer getMemberSince() {
        return memberSince;
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "countryCode", countryCode);
        isNotNull(validation, "countryName", countryName);
        isNotNull(validation, "currency", currency);

        return validation;
    }
}
