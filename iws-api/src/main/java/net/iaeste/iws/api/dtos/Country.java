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

    private String countryId = null;
    private String countryName = null;
    private String countryNameFull = null;
    private String countryNameNative = null;
    private String nationality = null;
    private String citizens = null;
    private String phonecode = null;
    private String currency = null;
    private String languages = null;
    private Membership membership = null;
    private Integer memberSince = null;
    private String listName = null;
    private String nsFirstname = null;
    private String nsLastname = null;

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
            countryId = country.countryId;
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

    public void setCountryId(final String countryId) {
        ensureNotNullAndExactLength("countryId", countryId, 2);

        this.countryId = countryId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryName(final String countryName) {
        ensureNotNullOrTooLong("countryName", countryName, 100);

        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryNameFull(final String countryNameFull) {
        ensureNotNullOrTooLong("countryNameFull", countryNameFull, 100);

        this.countryNameFull = countryNameFull;
    }

    public String getCountryNameFull() {
        return countryNameFull;
    }

    public void setCountryNameNative(final String countryNameNative) {
        ensureNotNullOrTooLong("countryNameNative", countryNameNative, 100);

        this.countryNameNative = countryNameNative;
    }

    public String getCountryNameNative() {
        return countryNameNative;
    }

    public void setNationality(final String nationality) {
        ensureNotNullOrTooLong("nationality", nationality, 100);

        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setCitizens(final String citizens) {
        ensureNotNullOrTooLong("citizens", citizens, 100);

        this.citizens = citizens;
    }

    public String getCitizens() {
        return citizens;
    }

    public void setPhonecode(final String phonecode) {
        ensureNotNullOrTooLong("phonecode", phonecode, 5);

        this.phonecode = phonecode;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setCurrency(final String currency) {
        ensureNotNullAndExactLength("currency", currency, 3);

        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setLanguages(final String languages) {
        ensureNotNullOrTooLong("languages", languages, 100);

        this.languages = languages;
    }

    public String getLanguages() {
        return languages;
    }

    public void setMembership(final Membership membership) {
        ensureNotNull("membership", membership);

        this.membership = membership;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMemberSince(final Integer memberSince) {
        final int currentYear = new Date().getCurrentYear();
        ensureNotNullAndWithinLimits("memberSince", memberSince, IWSConstants.FOUNDING_YEAR, currentYear);

        this.memberSince = memberSince;
    }

    public Integer getMemberSince() {
        return memberSince;
    }

    public void setListName(final String listName) {
        // No checks for this field, since it is only used when reading data
        // from the IWS, not for writing
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setNsFirstname(final String nsFirstname) {
        // No checks for this field, since it is only used when reading data
        // from the IWS, not for writing
        this.nsFirstname = nsFirstname;
    }

    public String getNsFirstname() {
        return nsFirstname;
    }

    public void setNsLastname(final String nsLastname) {
        // No checks for this field, since it is only used when reading data
        // from the IWS, not for writing
        this.nsLastname = nsLastname;
    }

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
        final int currentYear = new Date().getCurrentYear();

        hasExactSize(validation, "country", countryId, 2);
        isWithinLimits(validation, "countryName", countryName, 0, 100);
        isWithinLimits(validation, "countryNameFull", countryNameFull, 0, 100);
        isWithinLimits(validation, "countryNameNative", countryNameNative, 0, 100);
        isWithinLimits(validation, "nationality", nationality, 0, 100);
        isWithinLimits(validation, "citizens", citizens, 0, 100);
        isWithinLimits(validation, "phonecode", phonecode, 0, 5);
        hasExactSize(validation, "currency", currency, 3);
        isWithinLimits(validation, "languages", languages, 0, 100);
        isNotNull(validation, "membership", membership);
        isWithinLimits(validation, "memberSince", memberSince, IWSConstants.FOUNDING_YEAR, currentYear);

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

        if (countryId != null ? !countryId.equals(country.countryId) : country.countryId != null) {
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
        if (currency != null ? !currency.equals(country.currency) : country.currency != null) {
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

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (countryId != null ? countryId.hashCode() : 0);
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
                "countryId='" + countryId + '\'' +
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
