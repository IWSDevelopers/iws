/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.AdminDao
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

import net.iaeste.iws.persistence.entities.CountryEntity;

import java.util.List;

/**
 * Data Access Object with the functionality to perform the most basic
 * operations on all Users, Groups and Countries.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface AdminDao extends BasicDao {

    // =========================================================================
    // County specific DAO functionality
    // =========================================================================

    /**
     * Finds the given Country by the name, if no such country exists, then the
     * method will return a null value, otherwise the found Country.
     *
     * @param countryName  The Name of the Country to find
     * @return Found {@code CountryEntity} or null
     */
    CountryEntity findCountryByName(String countryName);

    /**
     * Returns a list of all known Countries in the database.
     *
     * @return List of Countries
     */
    List<CountryEntity> findAllCountries();
}
