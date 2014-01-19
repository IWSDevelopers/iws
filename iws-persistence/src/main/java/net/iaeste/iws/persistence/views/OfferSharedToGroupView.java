/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.OfferSharedToGroupView
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

import net.iaeste.iws.api.enums.exchange.OfferState;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
 * @since   1.7
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findSharedToForOwnerYearAndOffers",
                query = "select s from OfferSharedToGroupView s " +
                        "where s.offerOwner = :pid" +
                        "  and s.exchangeYear = :year" +
                        "  and nominationDeadline >= :date" +
                        "  and offerExternalId in (:externalOfferIds)" +
                        "  and s.status <> 'CLOSED'")
})
@Table(name = "find_shared_to_groups")
public class OfferSharedToGroupView {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private Long id = null;

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
