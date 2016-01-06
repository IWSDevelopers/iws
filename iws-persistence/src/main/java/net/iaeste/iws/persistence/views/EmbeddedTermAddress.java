/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.EmbeddedTermAddress
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

import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Membership;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * This is the second Embedded Address Object, which is used by various Views. The
 * intentions of this Object, is to have a unified way of dealing with the
 * read-only Address, so only a single DTO mapping instance is required.
 *   If any one view require more information, then all views must be extended
 * with this. All Address information must be prefixed with "address2_" in the
 * view.
 *
 * It's 1:1 copy of EmbeddedAddress.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Embeddable
public final class EmbeddedTermAddress {

    @Column(name = "term_address_street1", insertable = false, updatable = false)
    private String street1 = null;

    @Column(name = "term_address_street2", insertable = false, updatable = false)
    private String street2 = null;

    @Column(name = "term_address_pobox", insertable = false, updatable = false)
    private String pobox = null;

    @Column(name = "term_address_postal_code", insertable = false, updatable = false)
    private String postalCode = null;

    @Column(name = "term_address_city", insertable = false, updatable = false)
    private String city = null;

    @Column(name = "term_address_state", insertable = false, updatable = false)
    private String state = null;

    @Column(name = "term_country_id", insertable = false, updatable = false)
    private Long countryId = null;

    @Column(name = "term_country_code", insertable = false, updatable = false)
    private String countryCode = null;

    @Column(name = "term_country_name", insertable = false, updatable = false)
    private String countryName = null;

    @Column(name = "term_country_nationality", insertable = false, updatable = false)
    private String nationality = null;

    @Column(name = "term_country_phonecode", insertable = false, updatable = false)
    private String phonecode = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "term_country_currency", insertable = false, updatable = false)
    private Currency currency = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "term_country_membership", insertable = false, updatable = false)
    private Membership membership = null;

    @Column(name = "term_country_member_since", insertable = false, updatable = false)
    private Integer memberSince = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "term_address_modified", insertable = false, updatable = false)
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "term_address_created", insertable = false, updatable = false)
    private Date created = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

    public void setStreet1(final String street1) {
        this.street1 = street1;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet2(final String street2) {
        this.street2 = street2;
    }

    public String getStreet2() {
        return street2;
    }

    public void setPobox(final String pobox) {
        this.pobox = pobox;
    }

    public String getPobox() {
        return pobox;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setCountryId(final Long countryId) {
        this.countryId = countryId;
    }

    public Long getCountryId() {
        return countryId;
    }

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

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
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

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }
}
