/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Country
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
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;

import java.util.HashMap;
import java.util.Map;

/**
 * The Country Object contains the information about a Country, i.e. the name,
 * two letter ISO code, IAESTE membership, etc.<br />
 *   The Object is used for both fetching and updating/creating new Countries.
 * However, not all fields are updateable, the NS & Listnames are controlled by
 * other mechanisms, but is listed here when fetching the information. For
 * Existing countries, only part of the information is allowed to be updated.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyComplexMethod
 */
public final class Country extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String countryCode = null;
    private String countryName = null;
    private String countryNameFull = null;
    private String countryNameNative = null;
    private String nationality = null;
    private String citizens = null;
    private String phonecode = null;
    private Currency currency = null;
    private String languages = null;
    private Membership membership = null;
    private Integer memberSince = null;
    private String listName = null;
    private String nsFirstname = null;
    private String nsLastname = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Country() {
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
            listName = country.listName;
            nsFirstname = country.nsFirstname;
            nsLastname = country.nsLastname;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Country Code, the two letter Country Code, which is specified by
     * the UN.<br />
     *   This method throws an {@code IllegalArgumentException} if the given
     * value is not a valid Country Code, i.e. it must not be null and the value
     * has to be two characters.
     *
     * @param countryCode UN Country Code
     * @throws IllegalArgumentException If either null or not two characters long
     */
    public void setCountryCode(final String countryCode) throws IllegalArgumentException {
        ensureNotNullAndExactLength("countryCode", countryCode, 2);
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the Country Name. This is the English variant of the Country Name,
     * and it cannot neither be null nor too long. The max length is set to 100
     * characters.<br />
     *   The method will throw an {@code IllegalArgumentException} if the name
     * is invalid, i.e. null or too long.
     *
     * @param countryName English name of the Country
     * @throws IllegalArgumentException if either null or too long
     */
    public void setCountryName(final String countryName) throws IllegalArgumentException {
        ensureNotNullOrTooLong("countryName", countryName, 100);
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the Full Country Name. This is the complete name of the Country, and
     * it cannot neither be null nor too long. The max length is set to 100
     * characters.<br />
     *   The method will throw an {@code IllegalArgumentException} if the name
     * is invalid, i.e. null or too long.
     *
     * @param countryNameFull Full Country Name
     * @throws IllegalArgumentException if either null or too long
     */
    public void setCountryNameFull(final String countryNameFull) throws IllegalArgumentException {
        ensureNotTooLong("countryNameFull", countryNameFull, 100);
        this.countryNameFull = countryNameFull;
    }

    public String getCountryNameFull() {
        return countryNameFull;
    }

    /**
     * Sets the Native Country Name. This is the name that the Country uses for
     * itself. It may be null, but if set, it cannot be longer than 100
     * characters.<br />
     *   The method will throw an {@code IllegalArgumentException} if the name
     * is too long.
     *
     * @param countryNameNative Native Country Name
     * @throws IllegalArgumentException if the native name is too long
     */
    public void setCountryNameNative(final String countryNameNative) throws IllegalArgumentException {
        ensureNotTooLong("countryNameNative", countryNameNative, 100);
        this.countryNameNative = countryNameNative;
    }

    public String getCountryNameNative() {
        return countryNameNative;
    }

    /**
     * Sets the Nationality of the citizens of the Country. The given value may
     * maximum be 100 characters long. For example, the Nationality of Germany
     * is 'German'.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * value is null.
     *
     * @param nationality The Nationality of the Citizens
     * @throws IllegalArgumentException if the given value is too long
     */
    public void setNationality(final String nationality) throws IllegalArgumentException {
        ensureNotTooLong("nationality", nationality, 100);
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the name of the Citizens of the Country. The given value may
     * maximum be 100 characters long. For example, the Citizens of Germany are
     * 'Germans'.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * value is null.
     *
     * @param citizens The name of the Citizens of the Country
     * @throws IllegalArgumentException if the given value is too long
     */
    public void setCitizens(final String citizens) throws IllegalArgumentException {
        ensureNotTooLong("citizens", citizens, 100);
        this.citizens = citizens;
    }

    public String getCitizens() {
        return citizens;
    }

    /**
     * Sets the Phonecode of the Country. The value may be not be longer than
     * five (5) characters long.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * value is too long.
     *
     * @param phonecode The official phone code of the country (+xxx)
     * @throws IllegalArgumentException if the given value is null
     */
    public void setPhonecode(final String phonecode) throws IllegalArgumentException {
        ensureNotTooLong("phonecode", phonecode, 5);
        this.phonecode = phonecode;
    }

    public String getPhonecode() {
        return phonecode;
    }

    /**
     * Sets the Currency of the Country. The value may not be null, as it is
     * used internally.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * value is null.
     *
     * @param currency Official Currency of the Country
     * @throws IllegalArgumentException if the value is null
     */
    public void setCurrency(final Currency currency) throws IllegalArgumentException {
        ensureNotNull("currency", currency);
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    /**
     * Sets the official language(s) of the Country. The given value may contain
     * several Languages, but the maximum length may only be 100
     * characters.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * value is null.
     *
     * @param languages Officially spoken language(s) of the Country
     * @throws IllegalArgumentException if the given value is null
     */
    public void setLanguages(final String languages) throws IllegalArgumentException {
        ensureNotTooLong("languages", languages, 100);
        this.languages = languages;
    }

    public String getLanguages() {
        return languages;
    }

    /**
     * Sets the type of Membership of the Country, if it is an IAESTE Member,
     * then the value can be either full member, associate member, Co-operating
     * Institution or former member. If neither of these values are applicable,
     * the default value will be a non-member.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * value is null.
     *
     * @param membership Type of IAESTE Membership
     * @throws IllegalArgumentException if the given value is null
     */
    public void setMembership(final Membership membership) throws IllegalArgumentException {
        ensureNotNull("membership", membership);
        this.membership = membership;
    }

    public Membership getMembership() {
        return membership;
    }

    /**
     * Sets the Country's IAESTE Membership date, i.e. the year when the country
     * joined IAESTE. The year must be a valid IAESTE year, meaning that it must
     * be set to something between the founding year of IAESTE (1948), and the
     * present year.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * year is invalid, i.e. not within the limits (1948 - now).
     *
     * @param memberSince IAESTE Membership Year
     * @throws IllegalArgumentException if the value is invalid
     * @see IWSConstants#FOUNDING_YEAR
     */
    public void setMemberSince(final Integer memberSince) throws IllegalArgumentException {
        final int currentYear = new Date().getCurrentYear();
        ensureNotNullAndWithinLimits("memberSince", memberSince, IWSConstants.FOUNDING_YEAR, currentYear);
        this.memberSince = memberSince;
    }

    public Integer getMemberSince() {
        return memberSince;
    }

    /**
     * The name of the mailing list for this Country, the public variant
     * (@iaeste.org) and the private variant (@iaeste.net). The mailinglist is
     * not updatable via the Country Object.
     *
     * @param listName Mailinglist name
     */
    public void setListName(final String listName) {
        this.listName = listName;
    }

    /**
     * Retrieves the National mailinglist, however as it is not unique
     * (Cooperating Institutions share the same Country, but have individual NS
     * & Mailinglists), it has been deprecated.
     *
     * @return Listname
     * @deprecated please use the Group instead.
     */
    @Deprecated
    public String getListName() {
        return listName;
    }

    /**
     * The name of the National Secretary is set by the System, and cannot be
     * updated via the Country Object.
     *
     * @param nsFirstname Firstname of the National Secretary
     */
    public void setNsFirstname(final String nsFirstname) {
        this.nsFirstname = nsFirstname;
    }

    /**
     * Retrieves the NS Firstname, however as it is not unique (Cooperating
     * Institutions share the same Country, but have individual NS &
     * Mailinglists), it has been deprecated.
     *
     * @return NS Firstname
     * @deprecated please use the Group instead.
     */
    @Deprecated
    public String getNsFirstname() {
        return nsFirstname;
    }

    /**
     * The name of the National Secretary is set by the System, and cannot be
     * updated via the Country Object.
     *
     * @param nsLastname Lastname of the National Secretary
     */
    public void setNsLastname(final String nsLastname) {
        this.nsLastname = nsLastname;
    }

    /**
     * Retrieves the NS Lastname, however as it is not unique (Cooperating
     * Institutions share the same Country, but have individual NS &
     * Mailinglists), it has been deprecated.
     *
     * @return NS Lastname
     * @deprecated please use the Group instead.
     */
    @Deprecated
    public String getNsLastname() {
        return nsLastname;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Country)) {
            return false;
        }

        final Country country = (Country) obj;

        if (countryCode != null ? !countryCode.equals(country.countryCode) : country.countryCode != null) {
            return false;
        }
        if (countryName != null ? !countryName.equals(country.countryName) : country.countryName != null) {
            return false;
        }
        if (countryNameFull != null ? !countryNameFull.equals(country.countryNameFull) : country.countryNameFull != null) {
            return false;
        }
        if (countryNameNative != null ? !countryNameNative.equals(country.countryNameNative) : country.countryNameNative != null) {
            return false;
        }
        if (nationality != null ? !nationality.equals(country.nationality) : country.nationality != null) {
            return false;
        }
        if (citizens != null ? !citizens.equals(country.citizens) : country.citizens != null) {
            return false;
        }
        if (phonecode != null ? !phonecode.equals(country.phonecode) : country.phonecode != null) {
            return false;
        }
        if (currency != null ? currency != country.currency : country.currency != null) {
            return false;
        }
        if (languages != null ? !languages.equals(country.languages) : country.languages != null) {
            return false;
        }
        if (membership != country.membership) {
            return false;
        }
        if (memberSince != null ? !memberSince.equals(country.memberSince) : country.memberSince != null) {
            return false;
        }
        if (listName != null ? !listName.equals(country.listName) : country.listName != null) {
            return false;
        }
        if (nsFirstname != null ? !nsFirstname.equals(country.nsFirstname) : country.nsFirstname != null) {
            return false;
        }

        return !(nsLastname != null ? !nsLastname.equals(country.nsLastname) : country.nsLastname != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (countryName != null ? countryName.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (countryNameFull != null ? countryNameFull.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (countryNameNative != null ? countryNameNative.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (nationality != null ? nationality.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (citizens != null ? citizens.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (phonecode != null ? phonecode.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (currency != null ? currency.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (languages != null ? languages.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (membership != null ? membership.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (memberSince != null ? memberSince.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (listName != null ? listName.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (nsFirstname != null ? nsFirstname.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (nsLastname != null ? nsLastname.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Country{" +
                "countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryNameFull='" + countryNameFull + '\'' +
                ", countryNameNative='" + countryNameNative + '\'' +
                ", nationality='" + nationality + '\'' +
                ", citizens='" + citizens + '\'' +
                ", phonecode='" + phonecode + '\'' +
                ", currency='" + currency + '\'' +
                ", languages='" + languages + '\'' +
                ", membership=" + membership +
                ", memberSince=" + memberSince +
                ", listName='" + listName + '\'' +
                ", nsFirstname='" + nsFirstname + '\'' +
                ", nsLastname='" + nsLastname + '\'' +
                '}';
    }
}
