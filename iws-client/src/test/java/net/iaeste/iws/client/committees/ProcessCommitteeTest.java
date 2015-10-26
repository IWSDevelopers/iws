/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.committees.ProcessCommitteeTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.committees;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.CountryType;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.AdministrationClient;
import net.iaeste.iws.client.CommitteeClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class ProcessCommitteeTest extends AbstractTest {

    private final Administration administration = new AdministrationClient();
    private final Committees committees = new CommitteeClient();

    /**
     * {@inheritDoc}
     */
    @Before
    @Override
    public void setup() {
        // We have configured Australia as a member of the Board
        token = login("australia@iaeste.au", "australia");
    }

    /**
     * {@inheritDoc}
     */
    @After
    @Override
    public void tearDown() {
        logout(token);
    }

    // =========================================================================
    // Testing Committee Processing
    // =========================================================================

    /**
     * Following test is rather massive - reason is that it avoid too many
     * lookup's as the test is made as a sequence of actions against a
     * Committee. The tests are all primarily positive tests, as the negative
     * tests serve the purpose of testing the error handling.
     */
    @Test
    public void testCreateCommittee() {
        final Country country = retrieveRandomCountry(CountryType.COUNTRIES);

        // First create a new Committee and verify it was done correctly
        final Fallible createResponse = createCommittee(country.getCountryCode(), "Donald", "Duck", "DD");
        assertThat(createResponse.isOk(), is(true));

        // Now, we're repeating the same request, expecting an error!
        final Fallible failedCreateResponse = createCommittee(country.getCountryCode(), "Donald", "Duck", "DD");
        assertThat(failedCreateResponse.isOk(), is(false));
        assertThat(failedCreateResponse.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(failedCreateResponse.getMessage(), is("A Committee with the name " + country.getCountryName() + ", DD already exist."));

        // Okay, we forgot to give the correct name
        final Fallible newCreateResponse = createCommittee(country.getCountryCode(), "Daisy", "Duck", "DaD");
        assertThat(newCreateResponse.isOk(), is(true));

//        final List<String> countryIds = new ArrayList<>();
//        countryIds.add("AA");
//        final FetchCommitteeResponse committeeResponse = committees.fetchCommittees(token, new FetchCommitteeRequest(countryIds));
//        assertThat(committeeResponse.isOk(), is(false));
//        assertThat(committeeResponse.getMessage(), is(""));
//        final UserGroup userGroup = committeeResponse.getCommittees().get(0);
//        final CommitteeRequest request = new CommitteeRequest();
//        request.setAction(Action.ACTIVATE);
//        request.setNationalCommittee(userGroup.getGroup());
//        final Fallible activateResponse = committees.processCommittee(token, request);
//        assertThat(activateResponse.isOk(), is(false));
    }

    // =========================================================================
    // Negative Testing of Committee Processing
    // =========================================================================

    @Test
    public void testCreateCommitteeInvalidCountry() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CREATE);
        request.setCountryCode("XX");
        request.setFirstname("Donald");
        request.setLastname("Duck");
        request.setUsername("Donald@duck.com");
        request.setInstitutionName("Donald Duck");
        request.setInstitutionAbbreviation("DD");

        final Fallible response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.OBJECT_IDENTIFICATION_ERROR));
        assertThat(response.getMessage(), is("No country was found."));
    }

    @Test
    public void testCreateCommitteeForAssociateMember() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CREATE);
        request.setCountryCode("AZ");
        request.setFirstname("Donald");
        request.setLastname("Duck");
        request.setUsername("Donald@duck.com");
        request.setInstitutionName("Donald Duck");
        request.setInstitutionAbbreviation("DD");

        final Fallible response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response.getMessage(), is("Cannot create a new Cooperating Institution for a Member Country."));
    }

    @Test
    public void testCreateCommitteeForFullMember() {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CREATE);
        request.setCountryCode("DE");
        request.setFirstname("Donald");
        request.setLastname("Duck");
        request.setUsername("Donald@duck.com");
        request.setInstitutionName("Donald Duck");
        request.setInstitutionAbbreviation("DD");

        final Fallible response = committees.processCommittee(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.ILLEGAL_ACTION));
        assertThat(response.getMessage(), is("Cannot create a new Cooperating Institution for a Member Country."));
    }

    // =========================================================================
    // Testing Update Committee
    // =========================================================================

    // =========================================================================
    // Testing Merge Committee
    // =========================================================================

    // =========================================================================
    // Testing Upgrade Committee
    // =========================================================================

    @Test
    @Ignore("Test case not yet implemented.")
    public void testUpgradeInactiveCommittee() {
        fail("Not yet implemented.");
    }

    @Test
    @Ignore("Test case not yet implemented.")
    public void testUpgradeCooperatingInstitutionToAssociateMember() {
        fail("Not yet implemented.");
    }

    @Test
    @Ignore("Test case not yet implemented.")
    public void testUpgradeCountryWithMultipleCooperatingInstitutions() {
        fail("Not yet implemented.");
    }

    @Test
    @Ignore("Test case not yet implemented.")
    public void testUpgradeAssociateMemberToFullMember() {
        fail("Not yet implemented.");
    }

    @Test
    @Ignore("Test case not yet implemented.")
    public void testUpgradeFullMember() {
        fail("Not yet implemented.");
    }

    // =========================================================================
    // Testing Activate Committee
    // =========================================================================

    // =========================================================================
    // Testing Suspend Committee
    // =========================================================================

    // =========================================================================
    // Testing Delete Committee
    // =========================================================================

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private Country retrieveRandomCountry(final CountryType type) {
        final FetchCountryRequest request = new FetchCountryRequest();
        request.setPageNumber(1);
        request.setPageSize(100);
        request.setCountryType(type);

        final FetchCountryResponse response = administration.fetchCountries(token, request);

        return response.getCountries().get(0);
    }

    private Fallible createCommittee(final String countryCode, final String firstname, final String lastname, final String abbreviation) {
        final CommitteeRequest request = new CommitteeRequest();
        request.setAction(Action.CREATE);
        request.setCountryCode(countryCode);
        request.setFirstname(firstname);
        request.setLastname(lastname);
        request.setUsername(firstname + '@' + lastname + ".com");
        request.setInstitutionName(firstname + ' ' + lastname);
        request.setInstitutionAbbreviation(abbreviation);

        return committees.processCommittee(token, request);
    }
}
