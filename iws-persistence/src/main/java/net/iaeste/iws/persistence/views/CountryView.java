/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.CountryView
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Membership;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Maps the CountryDetails View from the database. The View primarily lists the
 * information from the countries table, but links in the current National
 * Secretary, and general information about the National Committee.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CompareToUsesNonFinalVariable
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findCountriesByMembership",
                query = "select v from CountryView v " +
                        "where v.membership = :type "),
        @NamedQuery(name = "view.findCountriesByCountryCode",
                query = "select v from CountryView v " +
                        "where lower(v.countryCode) in :codes")
})
@Table(name = "country_details")
public class CountryView extends AbstractView<CountryView> {

    /** {@see IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "country_code")
    private String countryCode = null;

    @Column(name = "country_name")
    private String countryName = null;

    @Column(name = "country_name_full")
    private String countryNameFull = null;

    @Column(name = "country_name_native")
    private String countryNameNative = null;

    @Column(name = "nationality")
    private String nationality = null;

    @Column(name = "citizens")
    private String citizens = null;

    @Column(name = "phonecode")
    private String phonecode = null;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency = null;

    @Column(name = "languages")
    private String languages = null;

    @Column(name = "membership")
    @Enumerated(EnumType.STRING)
    private Membership membership = null;

    @Column(name = "member_since")
    private Integer memberSince = null;

    @Column(name = "list_name")
    private String listName = null;

    @Column(name = "ns_firstname")
    private String nsFirstname = null;

    @Column(name = "ns_lastname")
    private String nsLastname = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryNameFull(final String countryNameFull) {
        this.countryNameFull = countryNameFull;
    }

    public String getCountryNameFull() {
        return countryNameFull;
    }

    public void setCountryNameNative(final String countryNameNative) {
        this.countryNameNative = countryNameNative;
    }

    public String getCountryNameNative() {
        return countryNameNative;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setCitizens(final String citizens) {
        this.citizens = citizens;
    }

    public String getCitizens() {
        return citizens;
    }

    public void setPhonecode(final String phonecode) {
        this.phonecode = phonecode;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setLanguages(final String languages) {
        this.languages = languages;
    }

    public String getLanguages() {
        return languages;
    }

    public void setMembership(final Membership membership) {
        this.membership = membership;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMemberSince(final Integer memberSince) {
        this.memberSince = memberSince;
    }

    public Integer getMemberSince() {
        return memberSince;
    }

    public void setListName(final String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setNsFirstname(final String nsFirstname) {
        this.nsFirstname = nsFirstname;
    }

    public String getNsFirstname() {
        return nsFirstname;
    }

    public void setNsLastname(final String nsLastname) {
        this.nsLastname = nsLastname;
    }

    public String getNsLastname() {
        return nsLastname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final CountryView o) {
        final int result;

        switch (sortField) {
            case NAME:
                result = countryName.compareTo(o.countryName);
                break;
            default:
                result = countryCode.compareTo(o.countryCode);
        }

        return sortAscending ? result : -result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        // As the View is reading unique records from the database, it is
        // enough to simply look at their unique Id
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CountryView)) {
            return false;
        }

        final CountryView view = (CountryView) obj;
        return !(countryCode != null ? !countryCode.equals(view.countryCode) : view.countryCode != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        // As the View is reading unique records from the database, it is
        // enough to simply look at their unique Id
        return countryCode.hashCode();
    }
}
