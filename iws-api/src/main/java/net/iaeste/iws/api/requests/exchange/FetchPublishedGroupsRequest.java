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
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.util.AbstractPaginatable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Request Object for fetching sharing info for posted list of offer Id's.
 * The offers have to be owned by the group to which the user is
 * currently logged in otherwise the exception is thrown.</p>
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchPublishedGroupsRequest", propOrder = { "identifiers", "exchangeYear" })
public final class FetchPublishedGroupsRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private List<String> identifiers = null;
    @XmlElement(required = true, nillable = false) private Integer exchangeYear;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public FetchPublishedGroupsRequest() {
        this.exchangeYear = calculateExchangeYear();
    }

    /**
     * <p>Default Constructor.</p>
     *
     * @param identifiers identifiers of the offer for which the sharing info is to be fetched
     */
    public FetchPublishedGroupsRequest(final List<String> identifiers) {
        setIdentifiers(identifiers);
        this.exchangeYear = calculateExchangeYear();
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets a list of Identifiers, meaning either the Id of the Offers or
     * their Reference Number, which both can be used to uniquely identify an
     * Offer.</p>
     *
     * <p>The method will thrown an {@code IllegalArgumentException} if the given
     * value is null.</p>
     *
     * @param identifiers List of OfferId's or Reference Numbers to be fetched, may be empty
     * @throws IllegalArgumentException if the parameter is null
     */
    public void setIdentifiers(final List<String> identifiers) throws IllegalArgumentException {
        ensureNotNullAndValidIdentifiers("identifiers", identifiers);
        this.identifiers = identifiers;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    /**
     * <p>Sets the ExchangeYear to read which Groups the provided Offers were
     * shared with, by default, it is the current Exchange Year. The value must
     * be defined, i.e. no null and also within the IAESTE Years, i.e. founding
     * year to the current Exchange Year.</p>
     *
     * <p>If attempted to set to an invalid year, then the method will throw an
     * {@code IllegalArgumentException}.</p>
     *
     * @param exchangeYear Exchange YeAr
     * @throws IllegalArgumentException if the given argument is invalid
     */
    public void setExchangeYear(final Integer exchangeYear) throws IllegalArgumentException {
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

        isNotNull(validation, "identifiers", identifiers);
        isNotNull(validation, "exchangeYear", exchangeYear);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSortBy(final SortingField sortBy) {
        ensureNotNull("sortBy", sortBy);

        switch (sortBy) {
            case NAME:
                page.setSortBy(sortBy);
                break;
            default:
                // If unsupported, we're going to revert to the default
                page.setSortBy(SortingField.CREATED);
        }
    }
}
