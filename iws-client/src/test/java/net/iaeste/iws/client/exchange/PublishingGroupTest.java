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

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.exchange.PublishingGroup;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.PublishingGroupRequest;
import net.iaeste.iws.api.responses.Response;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
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
    public void setUp() {
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

        final PublishingGroupRequest publishingGroupRequest = new PublishingGroupRequest();
        publishingGroupRequest.setPublishingGroup(publishingGroupList);
        final Response processPublishingGroupResponse = exchange.processPublishingGroup(token, publishingGroupRequest);

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

        final PublishingGroupRequest request = new PublishingGroupRequest();
        request.setPublishingGroup(publishingGroup);
        final Response response = exchange.processPublishingGroup(token, request);

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
        final String listId = null;

        createPublishGroupList(listName, groups, listId);

        final FetchPublishGroupsRequest fetchRequest = new FetchPublishGroupsRequest();
        final FetchPublishingGroupResponse fetchResponse = exchange.fetchPublishingGroups(token, fetchRequest);

        assertThat(fetchResponse.isOk(), is(true));
        final PublishingGroup fetchedList = findPublishingGroupFromResponse(listName, fetchResponse);
        assertThat(fetchedList, is(not(nullValue())));
        assertThat(fetchedList.getGroups().size(), is(groups.size()));
    }

    @Test
    public void testDeprecatedDeletePublishGroup() {
        final List<Group> groups = new ArrayList<>(2);
        groups.add(findNationalGroup(austriaToken));
        groups.add(findNationalGroup(croatiaToken));

        final String listName = "My Sharing List To Be Deleted";
        final String listId = null;

        createPublishGroupList(listName, groups, listId);

        final FetchPublishGroupsRequest fetchRequest = new FetchPublishGroupsRequest();
        final FetchPublishingGroupResponse fetchResponse = exchange.fetchPublishingGroups(token, fetchRequest);

        assertThat(fetchResponse.isOk(), is(true));
        final PublishingGroup fetchedList = findPublishingGroupFromResponse(listName, fetchResponse);
        assertThat(fetchedList.getGroups().size(), is(groups.size()));

        final PublishingGroupRequest deleteRequest = new PublishingGroupRequest();
        deleteRequest.setAction(Action.DELETE);
        deleteRequest.setPublishingGroupId(fetchedList.getPublishingGroupId());
        final Response deleteResponse = exchange.processPublishingGroup(token, deleteRequest);
        assertThat(deleteResponse.isOk(), is(true));
        final FetchPublishingGroupResponse fetchResponse2 = exchange.fetchPublishingGroups(token, fetchRequest);
        final PublishingGroup fetchedList2 = findPublishingGroupFromResponse(listName, fetchResponse2);
        assertThat(fetchedList2, is(nullValue()));
    }

    @Test
    public void testDeletePublishGroup() {
        final List<Group> groups = new ArrayList<>(2);
        groups.add(findNationalGroup(austriaToken));
        groups.add(findNationalGroup(croatiaToken));

        final String listName = "My Sharing List To Be Deleted";
        final String listId = null;

        createPublishGroupList(listName, groups, listId);

        final FetchPublishGroupsRequest fetchRequest = new FetchPublishGroupsRequest();
        final FetchPublishingGroupResponse fetchResponse = exchange.fetchPublishingGroups(token, fetchRequest);

        assertThat(fetchResponse.isOk(), is(true));
        final PublishingGroup fetchedList = findPublishingGroupFromResponse(listName, fetchResponse);
        assertThat(fetchedList.getGroups().size(), is(groups.size()));

        final PublishingGroupRequest request = new PublishingGroupRequest();
        request.setAction(Action.DELETE);
        request.setPublishingGroupId(fetchedList.getPublishingGroupId());
        final Response deleteResponse = exchange.processPublishingGroup(token, request);
        assertThat(deleteResponse.isOk(), is(true));
        final FetchPublishingGroupResponse fetchResponse2 = exchange.fetchPublishingGroups(token, fetchRequest);
        final PublishingGroup fetchedList2 = findPublishingGroupFromResponse(listName, fetchResponse2);
        assertThat(fetchedList2, is(nullValue()));
    }

    @Test
    public void testUpdatePublishGroup() {
        final List<Group> groups = new ArrayList<>(2);
        groups.add(findNationalGroup(austriaToken));
        groups.add(findNationalGroup(croatiaToken));

        final String listName = "My Sharing List";
        final String listId1 = null;

        createPublishGroupList(listName, groups, listId1);

        final FetchPublishGroupsRequest fetchRequest1 = new FetchPublishGroupsRequest();
        final FetchPublishingGroupResponse fetchResponse1 = exchange.fetchPublishingGroups(token, fetchRequest1);

        assertThat(fetchResponse1.isOk(), is(true));
        final PublishingGroup fetchedList1 = findPublishingGroupFromResponse(listName, fetchResponse1);
        assertThat(fetchedList1.getGroups().size(), is(groups.size()));

        groups.clear();
        groups.add(findNationalGroup(austriaToken));

        final String newListName = "Another list name";
        final String listId2 = fetchedList1.getPublishingGroupId();
        createPublishGroupList(newListName, groups, listId2);

        final FetchPublishGroupsRequest fetchRequest2 = new FetchPublishGroupsRequest();
        final FetchPublishingGroupResponse fetchResponse2 = exchange.fetchPublishingGroups(token, fetchRequest2);

        assertThat(fetchResponse2.isOk(), is(true));
        final PublishingGroup fetchedList2 = findPublishingGroupFromResponse(listName, fetchResponse2);
        assertThat("Looking for old list name has to return null", fetchedList2, is(nullValue()));

        final PublishingGroup fetchedList3 = findPublishingGroupFromResponse(newListName, fetchResponse2);
        assertThat(fetchedList3.getGroups().size(), is(groups.size()));
    }

    private void createPublishGroupList(final String name, final List<Group> groups, final String id) {
        final PublishingGroup publishGroupList = new PublishingGroup(name, groups);
        publishGroupList.setPublishingGroupId(id);

        final PublishingGroupRequest publishingGroupRequest = new PublishingGroupRequest();
        publishingGroupRequest.setPublishingGroup(publishGroupList);
        final Response processPublishGroupResponse = exchange.processPublishingGroup(token, publishingGroupRequest);

        assertThat(processPublishGroupResponse.isOk(), is(true));
    }

    private static PublishingGroup findPublishingGroupFromResponse(final String listName, final FetchPublishingGroupResponse response) {
        PublishingGroup result = null;

        for (final PublishingGroup found : response.getPublishingGroups()) {
            if (found.getName().equals(listName)) {
                result = found;
                break;
            }
        }

        return result;
    }
}
