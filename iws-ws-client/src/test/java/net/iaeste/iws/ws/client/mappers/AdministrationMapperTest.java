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
package net.iaeste.iws.ws.client.mappers;

import static net.iaeste.iws.ws.client.mappers.AdministrationMapper.map;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareCountry;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareIwsError;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.CountryType;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.enums.SortingOrder;
import net.iaeste.iws.api.requests.AccountNameRequest;
import net.iaeste.iws.api.requests.ContactsRequest;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchRoleRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.OwnerRequest;
import net.iaeste.iws.api.requests.RoleRequest;
import net.iaeste.iws.api.requests.SearchUserRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.api.responses.ProcessRoleResponse;
import net.iaeste.iws.api.responses.ProcessUserGroupResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;
import net.iaeste.iws.api.util.Page;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class AdministrationMapperTest {

    /**
     * <p>Private methods should never be tested, as they are part of an
     * internal workflow. Classes should always be tested via their contract,
     * i.e. public methods.</p>
     *
     * <p>However, for Utility Classes, with a Private Constructor, the contract
     * disallows instantiation, so the constructor is thus not testable via
     * normal means. This little Test method will just do that.</p>
     */
    @Test
    public void testPrivateConstructor() {
        try {
            final Constructor<AdministrationMapper> constructor = AdministrationMapper.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            final AdministrationMapper mapper = constructor.newInstance();
            assertThat(mapper, is(not(nullValue())));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            fail("Could not invoke Private Constructor: " + e.getMessage());
        }
    }

    @Test
    public void testNullFallibleResponse() {
        final net.iaeste.iws.ws.FallibleResponse response = null;
        final FallibleResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testFallibleResponse() {
        final IWSError error = IWSErrors.AUTHENTICATION_ERROR;
        final String message = "The Error Message";

        final net.iaeste.iws.ws.FallibleResponse response = new net.iaeste.iws.ws.FallibleResponse();
        response.setError(prepareIwsError(error));
        response.setMessage(message);

        final FallibleResponse mapped = map(response);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getError(), is(error));
        assertThat(mapped.getMessage(), is(message));
    }

    @Test
    public void testNullCountryRequest() {
        final CountryRequest request = null;
        final net.iaeste.iws.ws.CountryRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testCountryRequest() {
        final Country country = prepareCountry();
        final Action action = Action.PROCESS;

        final CountryRequest request = new CountryRequest();
        request.setCountry(country);
        request.setAction(action);

        final net.iaeste.iws.ws.CountryRequest mapped = map(request);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getCountry().getCountryName(), is(country.getCountryName()));
        assertThat(mapped.getAction().name(), is(action.name()));
    }

    @Test
    public void testNullFetchCountryRequest() {
        final FetchCountryRequest request = null;
        final net.iaeste.iws.ws.FetchCountryRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testFetchCountryRequest() {
        final List<String> countryIds = new ArrayList<>(3);
        countryIds.add("AA");
        countryIds.add("BB");
        countryIds.add("CC");
        final Membership membership = Membership.UNLISTED;
        final CountryType type = CountryType.COUNTRIES;
        final SortingOrder order = SortingOrder.ASC;
        final SortingField field = SortingField.CREATED;
        final int pageSize = 34;
        final int pageNumber = 3;

        final FetchCountryRequest request = new FetchCountryRequest();
        request.setCountryIds(countryIds);
        request.setMembership(membership);
        request.setCountryType(type);
        final Page page = request.getPage();
        page.setSortOrder(order);
        page.setSortBy(field);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);

        final net.iaeste.iws.ws.FetchCountryRequest mapped = map(request);
        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getCountryIds().size(), is(3));
        assertThat(mapped.getCountryIds().get(0), is("AA"));
        assertThat(mapped.getMembership().name(), is(membership.name()));
        assertThat(mapped.getCountryType().name(), is(type.name()));
        assertThat(mapped.getPage().getSortOrder().name(), is(order.name()));
        assertThat(mapped.getPage().getSortBy().name(), is(field.name()));
        assertThat(mapped.getPage().getPageNumber(), is(pageNumber));
        assertThat(mapped.getPage().getPageSize(), is(pageSize));
    }

    @Test
    public void testNullFetchCountryResponse() {
        final net.iaeste.iws.ws.FetchCountryResponse response = null;
        final FetchCountryResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testFetchCountryResponse() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullCreateUserRequest() {
        final CreateUserRequest request = null;
        final net.iaeste.iws.ws.CreateUserRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testCreateUserRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullCreateUserResponse() {
        final net.iaeste.iws.ws.CreateUserResponse response = null;
        final CreateUserResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testCreateUserResponse() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullUserRequest() {
        final UserRequest request = null;
        final net.iaeste.iws.ws.UserRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testUserRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullAccountNameRequest() {
        final AccountNameRequest request = null;
        final net.iaeste.iws.ws.AccountNameRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testAccountNameRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullFetchUserRequest() {
        final FetchUserRequest request = null;
        final net.iaeste.iws.ws.FetchUserRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testFetchUserRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullFetchUserResponse() {
        final net.iaeste.iws.ws.FetchUserResponse response = null;
        final FetchUserResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testFetchUserResponse() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullRoleRequest() {
        final RoleRequest request = null;
        final net.iaeste.iws.ws.RoleRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testRoleRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullProcessRoleResponse() {
        final net.iaeste.iws.ws.ProcessRoleResponse response = null;
        final ProcessRoleResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testProcessRoleResponse() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullFetchRoleRequest() {
        final FetchRoleRequest request = null;
        final net.iaeste.iws.ws.FetchRoleRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testFetchRoleRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullFetchRoleResponse() {
        final net.iaeste.iws.ws.FetchRoleResponse response = null;
        final FetchRoleResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testFetchRoleResponse() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullGroupRequest() {
        final GroupRequest request = null;
        final net.iaeste.iws.ws.GroupRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testGroupRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullProcessGroupResponse() {
        final net.iaeste.iws.ws.ProcessGroupResponse response = null;
        final ProcessGroupResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testProcessGroupResponse() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullFetchGroupRequest() {
        final FetchGroupRequest request = null;
        final net.iaeste.iws.ws.FetchGroupRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testFetchGroupRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullFetchGroupResponse() {
        final net.iaeste.iws.ws.FetchGroupResponse response = null;
        final FetchGroupResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testFetchGroupResponse() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullOwnerRequest() {
        final OwnerRequest request = null;
        final net.iaeste.iws.ws.OwnerRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testOwnerRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullUserGroupAssignmentRequest() {
        final UserGroupAssignmentRequest request = null;
        final net.iaeste.iws.ws.UserGroupAssignmentRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testUserGroupAssignmentRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullProcessUserGroupResponse() {
        final net.iaeste.iws.ws.ProcessUserGroupResponse response = null;
        final ProcessUserGroupResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testProcessUserGroupResponse() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullSearchUserRequest() {
        final SearchUserRequest request = null;
        final net.iaeste.iws.ws.SearchUserRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testSearchUserRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullSearchUserResponse() {
        final net.iaeste.iws.ws.SearchUserResponse response = null;
        final SearchUserResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testSearchUserResponse() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullEmergencyListResponse() {
        final net.iaeste.iws.ws.EmergencyListResponse response = null;
        final EmergencyListResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testEmergencyListResponse() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullContactsRequest() {
        final ContactsRequest request = null;
        final net.iaeste.iws.ws.ContactsRequest mapped = map(request);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testContactsRequest() {
        fail("not yet implemented.");
    }

    @Test
    public void testNullContactsResponse() {
        final net.iaeste.iws.ws.ContactsResponse response = null;
        final ContactsResponse mapped = map(response);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Not yet implemented")
    public void testContactsResponse() {
        fail("not yet implemented.");
    }
}
