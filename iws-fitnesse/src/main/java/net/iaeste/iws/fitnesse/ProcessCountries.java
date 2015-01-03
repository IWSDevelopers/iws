/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.ProcessCountries
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ProcessCountries extends AbstractFixture<Fallible> {

    private final Administration administration = new AdministrationCaller();
    private CountryRequest request = new CountryRequest();
    private final Country country = new Country();

    public void setCountryId(final String countryId) {
        country.setCountryCode(countryId);
    }

    public void setCountryName(final String countryName) {
        country.setCountryName(countryName);
    }

    public void setCountryNameFull(final String countryNameFull) {
        country.setCountryNameFull(countryNameFull);
    }

    public void setCountryNameNative(final String countryNameNative) {
        country.setCountryNameNative(countryNameNative);
    }

    public void setNationality(final String nationality) {
        country.setNationality(nationality);
    }

    public void setCitizens(final String citizens) {
        country.setCitizens(citizens);
    }

    public void setPhonecode(final String phonecode) {
        country.setPhonecode(phonecode);
    }

    public void setCurrency(final String currency) {
        country.setCurrency(Currency.valueOf(currency));
    }

    public void setLanguages(final String languages) {
        country.setLanguages(languages);
    }

    public void setMembership(final String membership) {
        country.setMembership(Membership.valueOf(membership));
    }

    public void setMemberSince(final Integer memberSince) {
        country.setMemberSince(memberSince);
    }

    public void processCountries() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        request.setCountry(country);
        setResponse(administration.processCountry(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
