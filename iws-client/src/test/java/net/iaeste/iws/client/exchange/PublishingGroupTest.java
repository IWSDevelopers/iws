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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.exchange.PublishingGroup;
import net.iaeste.iws.api.requests.exchange.DeletePublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.ExchangeClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class PublishingGroupTest extends AbstractTest {

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
        final Fallible processPublishingGroupResponse = exchange.processPublishingGroup(token, processPublishingGroupRequest);

        assertThat(processPublishingGroupResponse.isOk(), is(true));
    }

    @Test
    public void testProcessInvalidPublishGroup() {
        final List<Group> groups = new ArrayList<>(2);
        groups.add(findNationalGroup(austriaToken));
        groups.add(findNationalGroup(croatiaToken));

        final String listName = "My Sharing List";

        final PublishingGroup publishingGroup = new PublishingGroup(listName, groups);
        publishingGroup.setPublishingGroupId(UUID.randomUUID().toString());

        final ProcessPublishingGroupRequest request = new ProcessPublishingGroupRequest();
        request.setPublishingGroup(publishingGroup);
        final Fallible response = exchange.processPublishingGroup(token, request);

        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.OBJECT_IDENTIFICATION_ERROR));
        assertThat(response.getMessage(), containsString("No Sharing List could be found with the Id"));
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
        final FetchPublishingGroupResponse fetchResponse = exchange.fetchPublishingGroups(token, fetchRequest);

        assertThat(fetchResponse.isOk(), is(true));
        final PublishingGroup fetchedList = findPublishingGroupFromResponse(listName, fetchResponse);
        assertThat(fetchedList.getGroups().size(), is(groups.size()));
    }

    @Test
    public void testDeprecatedDeletePublishGroup() {
        final List<Group> groups = new ArrayList<>(2);
        groups.add(findNationalGroup(austriaToken));
        groups.add(findNationalGroup(croatiaToken));

        final String listName = "My Sharing List To Be Deleted";
        String listId = null;

        createPublishGroupList(listName, groups, listId);

        final FetchPublishGroupsRequest fetchRequest = new FetchPublishGroupsRequest();
        FetchPublishingGroupResponse fetchResponse = exchange.fetchPublishingGroups(token, fetchRequest);

        assertThat(fetchResponse.isOk(), is(true));
        PublishingGroup fetchedList = findPublishingGroupFromResponse(listName, fetchResponse);
        assertThat(fetchedList.getGroups().size(), is(groups.size()));

        final DeletePublishingGroupRequest deleteRequest = new DeletePublishingGroupRequest();
        deleteRequest.setPublishingGroupId(fetchedList.getPublishingGroupId());
        final Fallible deleteResponse = exchange.deletePublishingGroup(token, deleteRequest);
        assertThat(deleteResponse.isOk(), is(true));
        fetchResponse = exchange.fetchPublishingGroups(token, fetchRequest);
        fetchedList = findPublishingGroupFromResponse(listName, fetchResponse);
        assertThat(fetchedList, is(nullValue()));
    }

    @Test
    public void testDeletePublishGroup() {
        final List<Group> groups = new ArrayList<>(2);
        groups.add(findNationalGroup(austriaToken));
        groups.add(findNationalGroup(croatiaToken));

        final String listName = "My Sharing List To Be Deleted";
        String listId = null;

        createPublishGroupList(listName, groups, listId);

        final FetchPublishGroupsRequest fetchRequest = new FetchPublishGroupsRequest();
        FetchPublishingGroupResponse fetchResponse = exchange.fetchPublishingGroups(token, fetchRequest);

        assertThat(fetchResponse.isOk(), is(true));
        PublishingGroup fetchedList = findPublishingGroupFromResponse(listName, fetchResponse);
        assertThat(fetchedList.getGroups().size(), is(groups.size()));

        final ProcessPublishingGroupRequest request = new ProcessPublishingGroupRequest();
        request.setPublishingGroup(fetchedList);
        request.setDeletePublishingGroup(true);
        final Fallible deleteResponse = exchange.processPublishingGroup(token, request);
        assertThat(deleteResponse.isOk(), is(true));
        fetchResponse = exchange.fetchPublishingGroups(token, fetchRequest);
        fetchedList = findPublishingGroupFromResponse(listName, fetchResponse);
        assertThat(fetchedList, is(nullValue()));
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
        FetchPublishingGroupResponse fetchResponse = exchange.fetchPublishingGroups(token, fetchRequest);

        assertThat(fetchResponse.isOk(), is(true));
        PublishingGroup fetchedList = findPublishingGroupFromResponse(listName, fetchResponse);
        assertThat(fetchedList.getGroups().size(), is(groups.size()));

        groups.clear();
        groups.add(findNationalGroup(austriaToken));

        final String newListName = "Another list name";
        listId = fetchedList.getPublishingGroupId();
        createPublishGroupList(newListName, groups, listId);

        fetchRequest = new FetchPublishGroupsRequest();
        fetchResponse = exchange.fetchPublishingGroups(token, fetchRequest);

        assertThat(fetchResponse.isOk(), is(true));
        fetchedList = findPublishingGroupFromResponse(listName, fetchResponse);
        assertThat("Looking for old list name has to return null", fetchedList, is(nullValue()));

        fetchedList = findPublishingGroupFromResponse(newListName, fetchResponse);
        assertThat(fetchedList.getGroups().size(), is(groups.size()));
    }

    private void createPublishGroupList(final String name, final List<Group> groups, final String id) {
        final PublishingGroup publishGroupList = new PublishingGroup(name, groups);
        publishGroupList.setPublishingGroupId(id);

        final ProcessPublishingGroupRequest processPublishingGroupRequest = new ProcessPublishingGroupRequest();
        processPublishingGroupRequest.setPublishingGroup(publishGroupList);
        final Fallible processPublishGroupResponse = exchange.processPublishingGroup(token, processPublishingGroupRequest);

        assertThat(processPublishGroupResponse.isOk(), is(true));
    }

    private static PublishingGroup findPublishingGroupFromResponse(final String listName, final FetchPublishingGroupResponse response) {
        for (final PublishingGroup found : response.getPublishingGroups()) {
            if (found.getName().equals(listName)) {
                return found;
            }
        }
        return null;
    }
}
