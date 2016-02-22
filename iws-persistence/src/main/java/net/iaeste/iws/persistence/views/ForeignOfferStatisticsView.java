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
