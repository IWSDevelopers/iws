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
package net.iaeste.iws.client.exchange;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.ExchangeClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * This test class, goes over the Employer logic, meaning creating, reading,
 * updating Employers.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class EmployerTest extends AbstractTest {

    private static final String USERNAME = "vietnam@iaeste.vn";
    private static final String PASSWORD = "vietnam";

    @Before
    @Override
    public void setup() {
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest(USERNAME, PASSWORD);
        token = access.generateSession(authenticationRequest).getToken();
        final String groupId = findNationalGroup(token).getGroupId();
        token.setGroupId(groupId);
    }

    @After
    @Override
    public void tearDown() {
        access.deprecateSession(token);
    }

    @Test
    public void testFetchingEmployer() {
        final Exchange client = new ExchangeClient();

        final Employer employer1 = createEmployer(token, "MyFirstEmployer");
        final Employer employer2 = createEmployer(token, "MySecondEmployer");
        final Employer employer3 = createEmployer(token, "MySecondEmployerAgain");

        // Save our new Employers
        final EmployerResponse save1 = client.processEmployer(token, new ProcessEmployerRequest(employer1));
        assertThat(save1.isOk(), is(true));
        final EmployerResponse save2 = client.processEmployer(token, new ProcessEmployerRequest(employer2));
        assertThat(save2.isOk(), is(true));
        final EmployerResponse save3 = client.processEmployer(token, new ProcessEmployerRequest(employer3));
        assertThat(save3.isOk(), is(true));

        // Now, we'll start the test. We have three different types if lookups
        // that can be made. By default, we're fetching all
        final FetchEmployerRequest employerRequest = new FetchEmployerRequest();
        employerRequest.setFetchAll();
        final FetchEmployerResponse fetchResponse1 = client.fetchEmployers(token, employerRequest);
        assertThat(fetchResponse1.isOk(), is(true));

        // Find Employer by Id
        employerRequest.setFetchById(save1.getEmployer().getEmployerId());
        final FetchEmployerResponse fetchResponse2 = client.fetchEmployers(token, employerRequest);
        assertThat(fetchResponse2.isOk(), is(true));

        // Find Employer by partial name
        employerRequest.setFetchByPartialName("mysecond");
        final FetchEmployerResponse fetchResponse3 = client.fetchEmployers(token, employerRequest);
        assertThat(fetchResponse3.isOk(), is(true));
    }

    @Test
    public void testSaveEmployer() {
        // The Class under test
        final Exchange client = new ExchangeClient();
        final Employer employer = TestData.prepareEmployer("MyEmployer", "DE");

        // Invoke IWS to persist the Employer
        final EmployerResponse response1 = client.processEmployer(token, new ProcessEmployerRequest(employer));
        assertThat(response1.isOk(), is(true));

        // Update the Employer, let's just set a few fields
        final Employer persisted = response1.getEmployer();
        persisted.setBusiness("MyBusiness");
        // Since we're using defensive copying, we need to read out the Address
        // Object, update it and add it again
        final Address address = persisted.getAddress();
        address.setStreet1("MyStreet");
        address.setCity("MyCity");
        persisted.setAddress(address);

        // Now, let's update the existing Employer
        final EmployerResponse response2 = client.processEmployer(token, new ProcessEmployerRequest(persisted));
        assertThat(response2.isOk(), is(true));

        // And verify that everything is working
        final Employer updated = response2.getEmployer();
        assertThat(updated.getBusiness(), is("MyBusiness"));
        assertThat(updated.getAddress().getStreet1(), is("MyStreet"));
        assertThat(updated.getAddress().getCity(), is("MyCity"));
    }

    @Test
    public void testProcessingEmployer() {
        final Exchange client = new ExchangeClient();
        final String name = "New Employer Name";
        final String department = "New Department";
        String business = "New Business";
        String employeesCount = "Updated Employee Count";
        String website = "New Website";
        String workingPlace = "New Working Place";
        Boolean canteen = false;
        String nearestAirport = "New Airport";
        String nearestPublicTransport = "New Public Transportation";

        final EmployerResponse create = client.processEmployer(token, new ProcessEmployerRequest(createEmployer(token, "The Employer")));
        assertThat(create.isOk(), is(true));
        final Employer employer = create.getEmployer();

        // First, verify that all the fields we wish to change has a different value
        assertThat(employer.getName(), is(not(name)));
        assertThat(employer.getDepartment(), is(not(department)));
        assertThat(employer.getBusiness(), is(not(business)));
        assertThat(employer.getEmployeesCount(), is(not(employeesCount)));
        assertThat(employer.getWebsite(), is(not(website)));
        assertThat(employer.getWorkingPlace(), is(not(workingPlace)));
        assertThat(employer.getCanteen(), is(not(canteen)));
        assertThat(employer.getNearestAirport(), is(not(nearestAirport)));
        assertThat(employer.getNearestPublicTransport(), is(not(nearestPublicTransport)));

        // Now, update the Employer values and save the updated Employer
        employer.setName(name);
        employer.setDepartment(department);
        employer.setBusiness(business);
        employer.setEmployeesCount(employeesCount);
        employer.setWebsite(website);
        employer.setWorkingPlace(workingPlace);
        employer.setCanteen(canteen);
        employer.setNearestAirport(nearestAirport);
        employer.setNearestPublicTransport(nearestPublicTransport);
        final EmployerResponse update = client.processEmployer(token, new ProcessEmployerRequest(employer));

        // Now verify that the request went through, and that our updated
        // Employer object contain the new values
        assertThat(update.isOk(), is(true));
        final Employer updated = update.getEmployer();
        assertThat(updated.getName(), is(name));
        assertThat(updated.getDepartment(), is(department));
        assertThat(updated.getBusiness(), is(business));
        assertThat(updated.getEmployeesCount(), is(employeesCount));
        assertThat(updated.getWebsite(), is(website));
        assertThat(updated.getWorkingPlace(), is(workingPlace));
        assertThat(updated.getCanteen(), is(canteen));
        assertThat(updated.getNearestAirport(), is(nearestAirport));
        assertThat(updated.getNearestPublicTransport(), is(nearestPublicTransport));
    }

    /**
     * IAESTE Switzerland have reported a problem, where the duplicate Offer
     * functionality in IW4 lead to a Unique Constraint Violation in the IWS.
     * The error was because the Employer used in the duplication was somehow
     * changed, so the values were correct, but the Id was that from the newest
     * "similar" Employer.<br />
     *   The IWS should be able to catch this case and correctly return an error
     * rather than throwing SQL Exceptions.
     */
    @Test
    public void testProcessingAlmostIdenticalEmployers() {
        final Exchange client = new ExchangeClient();

        // First, we create & retrieve two new Employers
        final EmployerResponse save1 = client.processEmployer(token, new ProcessEmployerRequest(createEmployer(token, "The Employer")));
        assertThat(save1.isOk(), is(true));
        final Employer employer1 = save1.getEmployer();
        final EmployerResponse save2 = client.processEmployer(token, new ProcessEmployerRequest(createEmployer(token, "The Same Employer")));
        assertThat(save2.isOk(), is(true));
        final Employer employer2 = save2.getEmployer();

        // Now, we swap the Id's and try to save the Employer.
        employer1.setEmployerId(employer2.getEmployerId());
        final ProcessEmployerRequest request = new ProcessEmployerRequest(employer1);
        final EmployerResponse response = client.processEmployer(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.OBJECT_IDENTIFICATION_ERROR));
        assertThat(response.getMessage(), is("Processing failed, as this Employer already exist with a different Id than provided."));
    }

    @Test
    public void testReadingOfferRefNo() {
        final Exchange exchange = new ExchangeClient();
        final String employer = "Vietnam Inc.";
        final String refno = "VN-" + Verifications.calculateExchangeYear() + "-554331";
        final Offer offer = TestData.prepareFullOffer(refno, employer);
        final ProcessOfferRequest offerRequest = new ProcessOfferRequest();
        offerRequest.setOffer(offer);
        final OfferResponse offerResponse = exchange.processOffer(token, offerRequest);
        assertThat(offerResponse.getMessage(), is(IWSConstants.SUCCESS));

        final FetchEmployerRequest request = new FetchEmployerRequest();
        request.setFetchById(offerResponse.getOffer().getEmployer().getEmployerId());
        request.setFetchOfferReferenceNumbers(true);
        final FetchEmployerResponse response = exchange.fetchEmployers(token, request);
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(response.getEmployers().size(), is(greaterThanOrEqualTo(1)));
        assertThat(response.getOfferRefNos().size(), is(1));
        assertThat(response.getOfferRefNos().get(0), is(refno));
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private static Employer createEmployer(final AuthenticationToken token, final String name) {
        final Employer employer = TestData.prepareEmployer(name, "DE");
        final Address address = employer.getAddress();
        address.setCountry(fetchCountry(token, "DE"));

        return employer;
    }

    private static Country fetchCountry(final AuthenticationToken token, final String countryCode) {
        final FetchCountryRequest request = new FetchCountryRequest();
        final List<String> countryIds = new ArrayList<>(1);
        countryIds.add(countryCode);
        request.setCountryIds(countryIds);

        final FetchCountryResponse response = administration.fetchCountries(token, request);
        return response.getCountries().get(0);
    }
}
