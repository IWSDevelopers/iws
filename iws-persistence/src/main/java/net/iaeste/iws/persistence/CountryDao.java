/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.CountryDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence;

import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.util.Paginatable;
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

    List<CountryView> getCountries(List<String> countryCodes, Paginatable page);

    List<CountryView> getCountries(Membership membership, Paginatable page);

    List<CountryView> getAllCountries(Paginatable page);
}
