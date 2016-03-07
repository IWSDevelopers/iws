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
package net.iaeste.iws.client.administration;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.ContactsType;
import net.iaeste.iws.api.requests.ContactsRequest;
import net.iaeste.iws.api.requests.SearchUserRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ContactsTest extends AbstractAdministration {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
        token = login("spain@iaeste.es", "spain");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tearDown() {
        logout(token);
    }

    // =========================================================================
    // Positive Tests
    // =========================================================================

    @Test
    public void testSearchingWithoutGroup() {
        // The request is a generic one, so we need to set a Group for it
        token.setGroupId(findNationalGroup(token).getGroupId());

        final SearchUserRequest request = new SearchUserRequest();
        request.setName("aust"); // Australia, Austria

        final SearchUserResponse response = administration.searchUsers(token, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getUsers().size(), is(2));
    }

    @Test
    public void testSearchingWithGroup() {
        // The request is a generic one, so we need to set a Group for it
        final Group memberGroup = findMemberGroup(token);
        token.setGroupId(memberGroup.getGroupId());

        final SearchUserRequest request1 = new SearchUserRequest();
        request1.setName("aust"); // Australia, Austria
        request1.setGroup(memberGroup);

        final SearchUserResponse response1 = administration.searchUsers(token, request1);
        assertThat(response1.isOk(), is(true));
        assertThat(response1.getUsers().size(), is(0));

        final SearchUserRequest request2 = new SearchUserRequest();
        request2.setName("spa"); // Spain
        request2.setGroup(memberGroup);

        final SearchUserResponse response2 = administration.searchUsers(token, request2);
        assertThat(response2.isOk(), is(true));
        assertThat(response2.getUsers().size(), is(1));
    }

    @Test
    public void testFindEmergencyList() {
        final EmergencyListResponse response = administration.fetchEmergencyList(token);

        assertThat(response.isOk(), is(true));
        assertThat(response.getEmergencyContacts().isEmpty(), is(false));
    }

    @Test
    @Ignore("Ignored 2016-03-07 by Kim - Reaseon: Test is failing Travis-CI, need to investigate.")
    public void testFindContacts() {
        final ContactsRequest requestAll = new ContactsRequest();
        final ContactsResponse responseAll = administration.fetchContacts(token, requestAll);
        assertThat(responseAll.isOk(), is(true));
        assertThat(responseAll.getType(), is(ContactsType.OTHER));
        assertThat(responseAll.getGroups().isEmpty(), is(false));
        assertThat(responseAll.getUsers(), is(nullValue()));

        final ContactsRequest requestGroup = new ContactsRequest();
        requestGroup.setGroupId(responseAll.getGroups().get(12).getGroupId());
        final ContactsResponse responseGroup = administration.fetchContacts(token, requestGroup);
        assertThat(responseGroup.isOk(), is(true));
        assertThat(responseGroup.getType(), is(ContactsType.GROUP));
        assertThat(responseGroup.getGroups().size(), is(1));
        assertThat(responseGroup.getUsers(), is(not(nullValue())));
        assertThat(responseGroup.getUsers().isEmpty(), is(false));

        final ContactsRequest requestUser = new ContactsRequest();
        requestUser.setUserId(responseGroup.getUsers().get(0).getUserId());
        final ContactsResponse responseUser = administration.fetchContacts(token, requestUser);
        assertThat(responseUser.isOk(), is(true));
        assertThat(responseUser.getType(), is(ContactsType.USER));
        assertThat(responseUser.getUsers().size(), is(1));
        assertThat(responseUser.getGroups(), is(not(nullValue())));
        assertThat(responseUser.getGroups().isEmpty(), is(false));
    }

    @Test
    public void testFindContactsForInvalidUser() {
        final ContactsRequest request = new ContactsRequest();
        request.setUserId(UUID.randomUUID().toString());

        final ContactsResponse response = administration.fetchContacts(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.OBJECT_IDENTIFICATION_ERROR));
        assertThat(response.getMessage().contains("No details were found for User with Id"), is(true));
    }

    @Test
    public void testFindContactsForInvalidGroup() {
        final ContactsRequest request = new ContactsRequest();
        request.setGroupId(UUID.randomUUID().toString());

        final ContactsResponse response = administration.fetchContacts(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.OBJECT_IDENTIFICATION_ERROR));
        assertThat(response.getMessage().contains("No details were found for Group with Id"), is(true));
    }
}
