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
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.CountryType;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.util.Paginatable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>For listing of countries, this request Object is used. It can be generated
 * with a number of variants. If the specific countryId is known, it can be
 * given, otherwise a list of CountryIds can be given or a Membership type. It
 * is not possible to mix the list of CountryIds with Membership type, since
 * this allows for mutually exclusive combinations, i.e. a combination of a
 * CountryId where the country in question has one type of Membership, and the
 * given Membership is of a different type, meaning that the result would be
 * rather strange in nature, since it would be "yes, we know the country, but
 * you asked for it in a different context".</p>
 *
 * <p>The provided constructors and setters, is written, so it is not possible
 * to set both values.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchCountryRequest", propOrder = { "countryIds", "membership", "countryType" })
public final class FetchCountryRequest extends Paginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true)  private List<String> countryIds;
    @XmlElement(required = true, nillable = true)  private Membership membership;
    @XmlElement(required = true, nillable = false) private CountryType countryType = CountryType.COMMITTEES;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public FetchCountryRequest() {
        countryIds = null;
        membership = null;
    }

    /**
     * <p>Default Constructor, for the case where a list of countries, matching
     * the given Ids, should be fetched.</p>
     *
     * @param countryIds List of Countries to fetch
     */
    public FetchCountryRequest(final List<String> countryIds) {
        ensureNotNullOrEmpty("countryIds", countryIds);

        this.countryIds = countryIds;
        this.membership = null;
    }

    /**
     * <p>Default Constructor, for the case where a list of countries, with a
     * specific membership type, should be fetched.</p>
     *
     * @param membership Membership Type
     */
    public FetchCountryRequest(final Membership membership) {
        ensureNotNull("membership", membership);

        this.membership = membership;
        this.countryIds = null;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the list of CountryIds to fetch, it will also erase the
     * Membership type, if it is defined. The setter will throw an
     * {@code IllegalArgumentException}, if the given list of CountryIds is
     * null or empty.</p>
     *
     * @param countryIds List of Countries to fetch
     * @throws IllegalArgumentException if the CountryIds is null or empty
     */
    public void setCountryIds(final List<String> countryIds) throws IllegalArgumentException {
        ensureNotNullOrEmpty("countryIds", countryIds);

        this.countryIds = countryIds;
        membership = null;
    }

    /**
     * <p>Retrieves the list of CountryIds or null.</p>
     *
     * @return List of CountryIds to fetch
     */
    public List<String> getCountryIds() {
        return countryIds;
    }

    /**
     * <p>Sets the Membership type to fetch Countries for, it will also erase
     * the CountryIds, if they are defined. The setter will throw an
     * {@code IllegalArgumentException}, if the given membership value is
     * null.</p>
     *
     * @param membership Membership Type
     * @throws IllegalArgumentException if the membership value is null
     */
    public void setMembership(final Membership membership) throws IllegalArgumentException {
        ensureNotNull("membership", membership);

        this.membership = membership;
        countryIds = null;
    }

    /**
     * <p>Retrieves the Membership type or null.</p>
     *
     * @return Membership type or null
     */
    public Membership getMembership() {
        return membership;
    }

    /**
     * <p>Sets the Type of Countries to retrieve. Type is here defined as a list
     * of countries which may either be IAESTE specific (CountryType#COMMITTEES)
     * or of a more general type (CountryType#COUNTRIES).</p>
     *
     * <p>By default, all Country based requests are made with the Committee
     * type, this means that the lookup will also try to fill in information
     * about the current National Secretaries and mailing lists. If a different
     * type is chosen, then the result will purely focus on retrieving country
     * information and nothing else. This type is optimal for country
     * administration, i.e. adding new members to the organization, and when a
     * list of nationalities or country of residence is needed.</p>
     *
     * <p>The method will thrown an {@code IllegalArgumentException} if the value
     * is set to null.</p>
     *
     * @param countryType The current type of listing to retrieve
     * @throws IllegalArgumentException if the countryType is null
     */
    public void setCountryType(final CountryType countryType) throws IllegalArgumentException {
        ensureNotNull("countryType", countryType);
        this.countryType = countryType;
    }

    public CountryType getCountryType() {
        return countryType;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>();

        isNotNull(validation, "countryType", countryType);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSortBy(final SortingField sortBy) {
        ensureNotNull("sortBy", sortBy);

        switch (sortBy) {
            //case CREATED:
            case NAME:
                page.setSortBy(sortBy);
                break;
            default:
                // If unsupported, we're going to revert to the default
                page.setSortBy(SortingField.CREATED);
        }
    }
}
