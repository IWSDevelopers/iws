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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.CountryDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.views.CountryView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
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
        final CountryEntity existingWithId = dao.findCountry(newEntity.getCountryId());
        final CountryEntity existingWithName = dao.findCountryByName(newEntity.getCountryName());

        if (existingWithId == null) {
            if (existingWithName == null) {
                dao.persist(authentication, newEntity);
            } else {
                throw new IdentificationException("Cannot save Country, the name and id's are conflicting with existing records.");
            }
        } else {
            if ((existingWithName == null) || existingWithName.getCountryId().equals(existingWithId.getCountryId())) {
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

        if (request.getMembership() != null) {
            final Membership membership = request.getMembership();
            entities = dao.getCountries(membership, request.getPagingInformation());
        } else {
            final List<String> countryIds = request.getCountryIds();
            entities = dao.getCountries(countryIds, request.getPagingInformation());
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

        country.setCountryId(view.getCountryId());
        country.setCountryName(view.getCountryName());
        country.setCountryNameFull(view.getCountryNameFull());
        country.setCountryNameNative(view.getCountryNameNative());
        country.setNationality(view.getNationality());
        country.setCitizens(view.getCitizens());
        country.setPhonecode(view.getPhonecode());
        country.setCurrency(view.getCurrency());
        country.setLanguages(view.getLanguages());
        country.setMembership(view.getMembership());
        country.setMemberSince(view.getMemberSince());
        country.setListName(view.getListName());
        country.setNsFirstname(view.getNsFirstname());
        country.setNsLastname(view.getNsLastname());

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

        entity.setCountryId(country.getCountryId().toUpperCase(IWSConstants.DEFAULT_LOCALE));
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
