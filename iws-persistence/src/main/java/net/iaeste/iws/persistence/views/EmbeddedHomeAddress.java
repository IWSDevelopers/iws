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
 * This is the Embedded Address Home Object, which is used by the Application
 * View.
 *   If any one view require more information, then all views must be extended
 * with this. All Address information must be prefixed with "address2_" in the
 * view.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Embeddable
public final class EmbeddedHomeAddress {

    @Column(name = "home_address_street1", insertable = false, updatable = false)
    private String street1 = null;

    @Column(name = "home_address_street2", insertable = false, updatable = false)
    private String street2 = null;

    @Column(name = "home_address_pobox", insertable = false, updatable = false)
    private String pobox = null;

    @Column(name = "home_address_postal_code", insertable = false, updatable = false)
    private String postalCode = null;

    @Column(name = "home_address_city", insertable = false, updatable = false)
    private String city = null;

    @Column(name = "home_address_state", insertable = false, updatable = false)
    private String state = null;

    @Column(name = "home_country_id", insertable = false, updatable = false)
    private Long countryId = null;

    @Column(name = "home_country_code", insertable = false, updatable = false)
    private String countryCode = null;

    @Column(name = "home_country_name", insertable = false, updatable = false)
    private String countryName = null;

    @Column(name = "home_country_nationality", insertable = false, updatable = false)
    private String nationality = null;

    @Column(name = "home_country_phonecode", insertable = false, updatable = false)
    private String phonecode = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "home_country_currency", insertable = false, updatable = false)
    private Currency currency = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "home_country_membership", insertable = false, updatable = false)
    private Membership membership = null;

    @Column(name = "home_country_member_since", insertable = false, updatable = false)
    private Integer memberSince = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "home_address_modified", insertable = false, updatable = false)
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "home_address_created", insertable = false, updatable = false)
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
