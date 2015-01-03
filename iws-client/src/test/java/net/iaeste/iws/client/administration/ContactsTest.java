/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.administration.ContactsTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
    public void testFindContacts() {
        final ContactsRequest requestAll = new ContactsRequest();
        final ContactsResponse responseAll = administration.fetchContacts(token, requestAll);
        assertThat(responseAll.isOk(), is(true));
        assertThat(responseAll.getType(), is(ContactsType.OTHER));
        assertThat(responseAll.getGroups().isEmpty(), is(false));
        assertThat(responseAll.getUsers(), is(nullValue()));

        final ContactsRequest requestGroup = new ContactsRequest();
        requestGroup.setGroupId(responseAll.getGroups().get(10).getGroupId());
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
