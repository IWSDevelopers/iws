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
package net.iaeste.iws.api.enums;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public enum CountryType implements Descriptable<CountryType> {

    /**
     * <p>Default Type for requesting a list of Countries. The Listing is based
     * on the current list of members. Members are here defined as Full,
     * Associate, Co-operating Institutions and former members where information
     * still exists in the system.</p>
     *
     * <p>When this type is invoked, the list of National Secretaries and the
     * mailing list for Committees are also added.</p>
     */
    COMMITTEES("IAESTE Committees"),

    /**
     * <p>This listing means that the retrieval will not focus on the IAESTE
     * Members, but rather on all countries. The listing should follow the UN
     * list of countries, but it may differ since it is not automatically
     * updated whenever changes occur to the UN listing.</p>
     *
     * <p>When this type is invoked, then the result will deliberately not
     * contain any information that is IAESTE related, other than the current
     * membership type.</p>
     */
    COUNTRIES("Country List");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    CountryType(final String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }
}
