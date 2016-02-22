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

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findDomesticOffersByGroupAndYears",
            query = "select o from OfferView o " +
                    "where o.groupId = :gid" +
                    "  and o.exchangeYear in :years" +
                    "  and o.offer.status in :states"),
        @NamedQuery(name = "view.findDomesticOffersByGroupAndYearAndOfferExternalId",
            query = "select o from OfferView o " +
                    "where o.groupId = :gid" +
                    "  and o.exchangeYear = :year" +
                    "  and o.offer.externalId in (:eids)")
})
@Table(name = "offer_view")
public final class OfferView extends AbstractView<OfferView> {

    @Id
    @Column(name = "offer_id", insertable = false, updatable = false)
    private Long id = null;

    @Column(name = "offer_exchange_year", insertable = false, updatable = false)
    private Integer exchangeYear = null;

    @Column(name = "group_id", insertable = false, updatable = false)
    private Long groupId = null;

    @Column(name = "ns_firstname", insertable = false, updatable = false)
    private String nsFirstname = null;

    @Column(name = "ns_lastname", insertable = false, updatable = false)
    private String nsLastname = null;

    @Embedded
    private EmbeddedOffer offer = null;

    @Embedded
    private EmbeddedEmployer employer = null;

    @Embedded
    private EmbeddedAddress address = null;

    @Embedded
    private EmbeddedCountry country = null;

    @Embedded
    private EmbeddedGroup group = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final Long offerId) {
        this.id = offerId;
    }

    public Long getId() {
        return id;
    }

    public void setExchangeYear(final Integer exchangeYear) {
        this.exchangeYear = exchangeYear;
    }

    public Integer getExchangeYear() {
        return exchangeYear;
    }

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
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

    public void setOffer(final EmbeddedOffer offer) {
        this.offer = offer;
    }

    public EmbeddedOffer getOffer() {
        return offer;
    }

    public void setEmployer(final EmbeddedEmployer employer) {
        this.employer = employer;
    }

    public EmbeddedEmployer getEmployer() {
        return employer;
    }

    public void setAddress(final EmbeddedAddress address) {
        this.address = address;
    }

    public EmbeddedAddress getAddress() {
        return address;
    }

    public void setCountry(final EmbeddedCountry country) {
        this.country = country;
    }

    public EmbeddedCountry getCountry() {
        return country;
    }

    public void setGroup(final EmbeddedGroup group) {
        this.group = group;
    }

    public EmbeddedGroup getGroup() {
        return group;
    }

    // =========================================================================
    // Standard View Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OfferView)) {
            return false;
        }

        // As the view is reading from the current data model, and the Id is
        // always unique. It is sufficient to compare against this field.
        final OfferView that = (OfferView) obj;
        return (id != null) ? id.equals(that.id) : (that.id == null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final OfferView o) {
        final int result = id.compareTo(o.id);

        return sortAscending ? result : -result;
    }
}
