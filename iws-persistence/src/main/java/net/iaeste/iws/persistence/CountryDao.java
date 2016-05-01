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
package net.iaeste.iws.persistence;

import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.util.Page;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.views.CountryView;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface CountryDao extends BasicDao {

    /**
     * Finds a Country based on the given name, the lookup is made
     * case-insensitive, as the name must be unique.
     *
     * @param countryName The name of the Country too lookup
     * @return Found Country or null
     */
    CountryEntity findCountryByName(String countryName);

    List<CountryEntity> getCountries(List<String> countryCodes, Page page);

    List<CountryView> getMemberCountries(List<String> countryCodes, Page page);

    List<CountryView> getCountries(Membership membership, Page page);

    List<CountryView> getAllCountries(Page page);
}
