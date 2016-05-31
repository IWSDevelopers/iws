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
package net.iaeste.iws.api.requests.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.Verifications;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "offerStatisticsRequest", propOrder = "exchangeYear")
public final class OfferStatisticsRequest extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true)
    private Integer exchangeYear = calculateExchangeYear();

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public OfferStatisticsRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * <p>Default Constructor, for setting the ExchangeYear where statistics
     * should be fetched. Note, that the Exchange year starts in September and
     * ends in August, meaning that all offers created from September will
     * belong to the following year.</p>
     *
     * @param exchangeYear IAESTE Exchange Year (September -&gt; August)
     */
    public OfferStatisticsRequest(final Integer exchangeYear) {
        setExchangeYear(exchangeYear);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Exchange Year for which the statistics should be gathered for
     * the country. The value may not be null, and is by default set to the
     * current Exchange Year, which changes on September 1st, to the following
     * year, i.e. the IAESTE Exchange Year goes from September to August.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the value
     * is illegal, i.e. null.</p>
     *
     * @param exchangeYear IAESTE Exchange Year
     * @throws IllegalArgumentException if null or not a valid IAESTE Year
     */
    public void setExchangeYear(final Integer exchangeYear) {
        ensureNotNullAndWithinLimits("exchangeYear", exchangeYear, IWSConstants.FOUNDING_YEAR, calculateExchangeYear());
        this.exchangeYear = exchangeYear;
    }

    public Integer getExchangeYear() {
        return exchangeYear;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "exchangeYear", exchangeYear);

        return validation;
    }
}
