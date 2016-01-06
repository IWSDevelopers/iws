/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.ForeignOfferStatisticsView
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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Entity
@NamedQueries(@NamedQuery(name = "view.findStatisticsForForeignOffersForGroupAndYears",
        query = "select s from ForeignOfferStatisticsView s " +
                "where s.id.groupId = :gid" +
                "  and s.exchangeYear in :years"))
@Table(name = "foreign_offer_statistics")
public final class ForeignOfferStatisticsView {

    @EmbeddedId
    private ForeignOfferStatisticsId id = new ForeignOfferStatisticsId();

    @Column(name = "records")
    private Integer records = null;

    @Column(name = "exchange_year", length = 4)
    private Integer exchangeYear = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final ForeignOfferStatisticsId id) {
        this.id = id;
    }

    public ForeignOfferStatisticsId getId() {
        return id;
    }

    public void setRecords(final Integer records) {
        this.records = records;
    }

    public Integer getRecords() {
        return records;
    }

    public void setExchangeYear(final Integer exchangeYear) {
        this.exchangeYear = exchangeYear;
    }

    public Integer getExchangeYear() {
        return exchangeYear;
    }
}
