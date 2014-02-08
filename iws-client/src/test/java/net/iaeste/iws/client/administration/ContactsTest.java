/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.enums.ContactsType;
import net.iaeste.iws.api.requests.ContactsRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
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
    public void testFindEmergencyList() {
        final EmergencyListResponse response = client.fetchEmergencyList(token);

        assertThat(response.isOk(), is(true));
        assertThat(response.getEmergencyContacts().isEmpty(), is(false));
    }

    @Test
    public void testFindContacts() {
        final ContactsRequest requestAll = new ContactsRequest();
        final ContactsResponse responseAll = client.fetchContacts(token, requestAll);
        assertThat(responseAll.isOk(), is(true));
        assertThat(responseAll.getType(), is(ContactsType.OTHER));
        assertThat(responseAll.getGroups().isEmpty(), is(false));
        assertThat(responseAll.getUsers(), is(nullValue()));

        final ContactsRequest requestGroup = new ContactsRequest();
        requestGroup.setGroupId(responseAll.getGroups().get(10).getGroupId());
        final ContactsResponse responseGroup = client.fetchContacts(token, requestGroup);
        assertThat(responseGroup.isOk(), is(true));
        assertThat(responseGroup.getType(), is(ContactsType.GROUP));
        assertThat(responseGroup.getGroups().size(), is(1));
        assertThat(responseGroup.getUsers(), is(not(nullValue())));
        assertThat(responseGroup.getUsers().isEmpty(), is(false));

        final ContactsRequest requestUser = new ContactsRequest();
        requestUser.setUserId(responseGroup.getUsers().get(0).getUserId());
        final ContactsResponse responseUser = client.fetchContacts(token, requestUser);
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

        final ContactsResponse response = client.fetchContacts(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.OBJECT_IDENTIFICATION_ERROR));
        assertThat(response.getMessage().contains("No details were found for User with Id"), is(true));
    }

    @Test
    public void testFindContactsForInvalidGroup() {
        final ContactsRequest request = new ContactsRequest();
        request.setGroupId(UUID.randomUUID().toString());

        final ContactsResponse response = client.fetchContacts(token, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.OBJECT_IDENTIFICATION_ERROR));
        assertThat(response.getMessage().contains("No details were found for Group with Id"), is(true));
    }
}
