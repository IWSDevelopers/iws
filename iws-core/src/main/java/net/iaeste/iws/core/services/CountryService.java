/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static net.iaeste.iws.common.utils.StringUtils.toUpper;

import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.CountryDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.views.CountryView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CountryService {

    private final CountryDao dao;

    public CountryService(final CountryDao dao) {
        this.dao = dao;
    }

    /**
     * Handles the processing of Country Objects, i.e. either saving new or
     * updating existing countries. The method makes two lookups initially, to
     * verify that the Country is not going to conflict with existing Countries,
     * example of conflicts, is that someone is trying to rename an existing
     * country to something that already exists.
     *
     * @param authentication User & Group information
     * @param request        Process Country Request information
     */
    public void processCountries(final Authentication authentication, final CountryRequest request) {
        final CountryEntity newEntity = transform(request.getCountry());
        final CountryEntity existingWithId = dao.findCountry(newEntity.getCountryCode());
        final CountryEntity existingWithName = dao.findCountryByName(newEntity.getCountryName());

        if (existingWithId == null) {
            if (existingWithName == null) {
                dao.persist(authentication, newEntity);
            } else {
                throw new IdentificationException("Cannot save Country, the name and id's are conflicting with existing records.");
            }
        } else {
            if ((existingWithName == null) || existingWithName.getCountryCode().equals(existingWithId.getCountryCode())) {
                dao.persist(authentication, existingWithId, newEntity);
            } else {
                throw new IdentificationException("Cannot save Country, the name and id's are conflicting with existing records.");
            }
        }
    }

    /**
     * Retrieves a list of Countries, matching the criterias from the Request
     * Object, and returns it.
     *
     * @param request Fetch Country Request information
     * @return Response Object with found Countries
     */
    public FetchCountryResponse fetchCountries(final FetchCountryRequest request) {
        final FetchCountryResponse response;
        final List<CountryView> entities;

        final Paginatable page = request.getPagingInformation();
        final List<String> countryCodes = request.getCountryIds();
        final Membership membership = request.getMembership();

        if (membership != null) {
            entities = dao.getCountries(membership, page);
        } else if ((countryCodes != null) && !countryCodes.isEmpty()) {
            entities = dao.getCountries(countryCodes, page);
        } else {
            entities = dao.getAllCountries(page);
        }

        final List<Country> countries = transform(entities);
        response = new FetchCountryResponse(countries);

        return response;
    }

    // =========================================================================
    // Transformers
    // =========================================================================

    /**
     * Returns a new list of Country Objects, matching the found Country View
     * Objects.
     *
     * @param views List of Country View Objects
     * @return List of Country Objects
     */
    private static List<Country> transform(final List<CountryView> views) {
        final List<Country> countries = new ArrayList<>(views.size());

        for (final CountryView view : views) {
            final Country country = transform(view);
            countries.add(country);
        }

        return countries;
    }

    /**
     * Transforms the Country View to a Country Object. The View contains all
     * the information from the Country Object, plus some additional information
     * related to the Staff of the Country.
     *
     * @param view Country View
     * @return Country Object
     */
    private static Country transform(final CountryView view) {
        final Country country = new Country();

        // Note, ignored lines are either not mapped in (additional details), or
        // is mapped in via different views (NS & Listname) since they are not
        // uniquely distinguishable as Cooperating Institutions will have more
        // than one NS & list
        country.setCountryCode(view.getCountry().getCountryCode());
        country.setCountryName(view.getCountry().getCountryName());
        //country.setCountryNameFull(view.getCountry().getCountryNameFull());
        //country.setCountryNameNative(view.getCountry().getCountryNameNative());
        country.setNationality(view.getCountry().getNationality());
        //country.setCitizens(view.getCountry().getCitizens());
        country.setPhonecode(view.getCountry().getPhonecode());
        country.setCurrency(view.getCountry().getCurrency());
        //country.setLanguages(view.getCountry().getLanguages());
        country.setMembership(view.getCountry().getMembership());
        country.setMemberSince(view.getCountry().getMemberSince());

        return country;
    }

    /**
     * Transforms a Country Object to the corresponsing Entity. Not all
     * information from the Country Object is mapped into the Entity, this
     * includes the National Secretary and Listnamee - these are managed via
     * the Group functionaity.
     *
     * @param country Country Object
     * @return Country Entity
     */
    private static CountryEntity transform(final Country country) {
        final CountryEntity entity = new CountryEntity();

        entity.setCountryCode(toUpper(country.getCountryCode()));
        entity.setCountryName(country.getCountryName());
        entity.setCountryNameFull(country.getCountryNameFull());
        entity.setCountryNameNative(country.getCountryNameNative());
        entity.setNationality(country.getNationality());
        entity.setCitizens(country.getCitizens());
        entity.setPhonecode(country.getPhonecode());
        entity.setCurrency(country.getCurrency());
        entity.setLanguages(country.getLanguages());
        entity.setMembership(country.getMembership());
        entity.setMemberSince(country.getMemberSince());

        return entity;
    }
}
