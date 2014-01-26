/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.exchange.EmployerTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.exchange;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.AccessClient;
import net.iaeste.iws.client.AdministrationClient;
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
 * @since   1.7
 */
public final class EmployerTest extends AbstractTest {

    private static final String USERNAME = "vietnam@iaeste.vn";
    private static final String PASSWORD = "vietnam";
    private final Access access = new AccessClient();

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

        final Access access = new AccessClient();
        final FetchPermissionResponse result = access.fetchPermissions(token);
        final Administration administration = new AdministrationClient();
        final FetchCountryResponse response = administration.fetchCountries(token, request);
        return response.getCountries().get(0);
    }
}
