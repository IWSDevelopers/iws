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
package net.iaeste.iws.api.dtos.exchange;

import static net.iaeste.iws.api.util.AbstractVerification.immutableMap;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.exchange.OfferState;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "offerStatistics", propOrder = { "statistics", "exchangeYear" })
public final class OfferStatistics implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private Map<OfferState, Integer> statistics = new EnumMap<>(OfferState.class);
    @XmlElement(required = true, nillable = true) private Integer exchangeYear = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
    * Empty Constructor, to use if the setters are invoked. This is required
    * for WebServices to work properly.
    */
    public OfferStatistics() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor.
     *
     * @param statistics   Offer Statistics
     * @param exchangeYear Exchange Year
     */
    public OfferStatistics(final Map<OfferState, Integer> statistics, final Integer exchangeYear) {
        setStatistics(statistics);
        this.exchangeYear = exchangeYear;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setStatistics(final Map<OfferState, Integer> statistics) {
        this.statistics = immutableMap(statistics);
    }

    public Map<OfferState, Integer> getStatistics() {
        return immutableMap(statistics);
    }

    public void setExchangeYear(final Integer exchangeYear) {
        this.exchangeYear = exchangeYear;
    }

    public Integer getExchangeYear() {
        return exchangeYear;
    }
}
