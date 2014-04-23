/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.exchange.PublishGroupTest
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

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.exchange.PublishingGroup;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.AdministrationClient;
import net.iaeste.iws.client.ExchangeClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class PublishingGroupTest extends AbstractTest {

    private final Administration administration = new AdministrationClient();
    private final Exchange exchange = new ExchangeClient();
    private AuthenticationToken austriaToken = null;
    private AuthenticationToken croatiaToken = null;

    @Override
    public void setup() {
        token = login("poland@iaeste.pl", "poland");
        austriaToken = login("austria@iaeste.at", "austria");
        croatiaToken = login("croatia@iaeste.hr", "croatia");
    }

    @Override
    public void tearDown() {
        logout(token);
        logout(austriaToken);
        logout(croatiaToken);
    }

    @Test
    public void testProcessPublishGroup() {
        final List<Group> groups = new ArrayList<>(2);
        groups.add(findNationalGroup(austriaToken));
        groups.add(findNationalGroup(croatiaToken));

        final String listName = "My Sharing List";

        final PublishingGroup publishingGroupList = new PublishingGroup(listName, groups);

        final ProcessPublishingGroupRequest processPublishingGroupRequest = new ProcessPublishingGroupRequest();
        processPublishingGroupRequest.setPublishingGroup(publishingGroupList);
        final Fallible processPublishingGroupResponse = exchange.processPublishGroup(token, processPublishingGroupRequest);

        assertThat(processPublishingGroupResponse.isOk(), is(true));
    }

    @Test
    public void testFetchPublishGroup() {
        final List<Group> groups = new ArrayList<>(2);
        groups.add(findNationalGroup(austriaToken));
        groups.add(findNationalGroup(croatiaToken));

        final String listName = "My Sharing List";
        String listId = null;

        createPublishGroupList(listName, groups, listId);

        final FetchPublishGroupsRequest fetchRequest = new FetchPublishGroupsRequest();
        final FetchPublishingGroupResponse fetchResponse = exchange.fetchPublishGroups(token, fetchRequest);

        assertThat(fetchResponse.isOk(), is(true));
        final PublishingGroup fetchedList = findApplicationFromResponse(listName, fetchResponse);
        assertThat(fetchedList.getGroups().size(), is(groups.size()));
    }

    @Test
    public void testUpdatePublishGroup() {
        final List<Group> groups = new ArrayList<>(2);
        groups.add(findNationalGroup(austriaToken));
        groups.add(findNationalGroup(croatiaToken));

        final String listName = "My Sharing List";
        String listId = null;

        createPublishGroupList(listName, groups, listId);

        FetchPublishGroupsRequest fetchRequest = new FetchPublishGroupsRequest();
        FetchPublishingGroupResponse fetchResponse = exchange.fetchPublishGroups(token, fetchRequest);

        assertThat(fetchResponse.isOk(), is(true));
        PublishingGroup fetchedList = findApplicationFromResponse(listName, fetchResponse);
        assertThat(fetchedList.getGroups().size(), is(groups.size()));

        groups.clear();
        groups.add(findNationalGroup(austriaToken));

        final String newListName = "Another list name";
        listId = fetchedList.getPublishingGroupId();
        createPublishGroupList(newListName, groups, listId);

        fetchRequest = new FetchPublishGroupsRequest();
        fetchResponse = exchange.fetchPublishGroups(token, fetchRequest);

        assertThat(fetchResponse.isOk(), is(true));
        fetchedList = findApplicationFromResponse(listName, fetchResponse);
        assertThat("Looking for old list name has to return null", fetchedList, is(nullValue()));

        fetchedList = findApplicationFromResponse(newListName, fetchResponse);
        assertThat(fetchedList.getGroups().size(), is(groups.size()));
    }

    private void createPublishGroupList(final String name, final List<Group> groups, final String id) {
        final PublishingGroup publishGroupList = new PublishingGroup(name, groups);
        publishGroupList.setPublishingGroupId(id);

        final ProcessPublishingGroupRequest processPublishingGroupRequest = new ProcessPublishingGroupRequest();
        processPublishingGroupRequest.setPublishingGroup(publishGroupList);
        final Fallible processPublishGroupResponse = exchange.processPublishGroup(token, processPublishingGroupRequest);

        assertThat(processPublishGroupResponse.isOk(), is(true));
    }

    private static PublishingGroup findApplicationFromResponse(final String listName, final FetchPublishingGroupResponse response) {
        for (final PublishingGroup found : response.getPublishingGroups()) {
            if (found.getName().equals(listName)) {
                return found;
            }
        }
        return null;
    }
}
