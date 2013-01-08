/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.CountryService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.responses.CountryResponse;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.CountryDao;
import net.iaeste.iws.persistence.views.CountryView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class CountryService {

    private static final Logger LOG = LoggerFactory.getLogger(AdministrationService.class);
    private final CountryDao dao;

    public CountryService(final CountryDao dao) {
        this.dao = dao;
    }

    public void processCountries(final Authentication authentication, final CountryRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public CountryResponse fetchCountries(final FetchCountryRequest request) {
        final CountryResponse response;
        final List<CountryView> entities;

        if (request.getMembership() != null) {
            final Membership membership = request.getMembership();
            entities = dao.getCountries(membership);
        } else {
            final List<String> countryIds = request.getCountryIds();
            entities = dao.getCountries(countryIds);
        }

        final List<Country> countries = transform(entities);
        response = new CountryResponse(countries);

        return response;
    }

    // =========================================================================
    // Transformers
    // =========================================================================

    private static List<Country> transform(final List<CountryView> entities) {
        final List<Country> countries = new ArrayList<>(entities.size());

        for (final CountryView entity : entities) {
            final Country country = transform(entity);
            countries.add(country);
        }

        return countries;
    }

    private static Country transform(final CountryView entity) {
        final Country country = new Country();
        return country;
    }
}
