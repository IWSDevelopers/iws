package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * Created with IntelliJ IDEA.
 * User: martin
 * Date: 5/12/13
 * Time: 5:56 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ProcessCountries extends AbstractFixture<Fallible> {

    private final Administration administration = new AdministrationCaller();
    private CountryRequest request = new CountryRequest();
    private Country country = new Country();

    public void setCountryId(final String countryId) {
        country.setCountryId(countryId);
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
        country.setCurrency(currency);
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

    public void setListName(final String listName) {
        country.setListName(listName);
    }
    public void setNsFirstname(final String nsFirstname) {
        country.setNsFirstname(nsFirstname);
    }

    public void setNsLastname(final String nsLastname) {
        country.setNsLastname(nsLastname);
    }

    public void processCountries() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        request.setCountry(country);
        setResponse(administration.processCountries(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
