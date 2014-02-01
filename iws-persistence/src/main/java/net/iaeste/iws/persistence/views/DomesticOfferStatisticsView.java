/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.DomesticOfferStatistics
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
@NamedQueries(@NamedQuery(name = "view.findStatisticsForDomesticOffersForGroupAndYear",
        query = "select s from DomesticOfferStatisticsView s " +
                "where s.id.groupId = :gid" +
                "  and s.exchangeYear = :year"))
@Table(name = "domestic_offer_statistics")
public class DomesticOfferStatisticsView {

    @EmbeddedId
    private DomesticOfferStatisticsId id = new DomesticOfferStatisticsId();

    @Column(name = "records")
    private Integer records = null;

    @Column(name = "exchange_year", length = 4)
    private Integer exchangeYear = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final DomesticOfferStatisticsId id) {
        this.id = id;
    }

    public DomesticOfferStatisticsId getId() {
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
