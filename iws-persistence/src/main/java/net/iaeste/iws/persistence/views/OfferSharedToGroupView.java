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

import net.iaeste.iws.api.enums.exchange.OfferState;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Entity
@NamedQueries(@NamedQuery(name = "view.findSharedToForOwnerYearAndOffers",
        query = "select s from OfferSharedToGroupView s " +
                "where s.offerOwner = :pid" +
                "  and s.exchangeYear = :year" +
                "  and s.offerExternalId in (:externalOfferIds)" +
                "  and s.status not in ('CLOSED', 'REJECTED')"))
@Table(name = "find_shared_to_groups")
public final class OfferSharedToGroupView {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private Long id = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", insertable = false, updatable = false)
    private OfferState status = null;

    @Column(name = "offer_owner", insertable = false, updatable = false)
    private Long offerOwner = null;

    @Column(name = "offer_id", insertable = false, updatable = false)
    private Long offerId = null;

    @Column(name = "offer_external_id", insertable = false, updatable = false)
    private String offerExternalId = null;

    @Column(name = "offer_ref_no", insertable = false, updatable = false)
    private String offerRefNo = null;

    @Column(name = "exchange_year", insertable = false, updatable = false)
    private Integer exchangeYear = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "offer_nomination_deadline", insertable = false, updatable = false)
    private Date nominationDeadline = null;

    @Column(name = "group_id", insertable = false, updatable = false)
    private Long sharedToGroupId = null;

    @Embedded
    private EmbeddedGroup group = null;

    @Embedded
    private EmbeddedCountry country = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStatus(final OfferState status) {
        this.status = status;
    }

    public OfferState getStatus() {
        return status;
    }

    public void setOfferOwner(final Long offerOwner) {
        this.offerOwner = offerOwner;
    }

    public Long getOfferOwner() {
        return offerOwner;
    }

    public void setOfferId(final Long offerId) {
        this.offerId = offerId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferExternalId(final String offerExternalId) {
        this.offerExternalId = offerExternalId;
    }

    public String getOfferExternalId() {
        return offerExternalId;
    }

    public void setOfferRefNo(final String offerRefNo) {
        this.offerRefNo = offerRefNo;
    }

    public String getOfferRefNo() {
        return offerRefNo;
    }

    public void setExchangeYear(final Integer exchangeYear) {
        this.exchangeYear = exchangeYear;
    }

    public Integer getExchangeYear() {
        return exchangeYear;
    }

    public void setNominationDeadline(final Date nominationDeadline) {
        this.nominationDeadline = nominationDeadline;
    }

    public Date getNominationDeadline() {
        return nominationDeadline;
    }

    public void setSharedToGroupId(final Long sharedToGroupId) {
        this.sharedToGroupId = sharedToGroupId;
    }

    public Long getSharedToGroupId() {
        return sharedToGroupId;
    }

    public void setGroup(final EmbeddedGroup group) {
        this.group = group;
    }

    public EmbeddedGroup getGroup() {
        return group;
    }

    public void setCountry(final EmbeddedCountry country) {
        this.country = country;
    }

    public EmbeddedCountry getCountry() {
        return country;
    }
}
