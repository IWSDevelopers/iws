/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.administration.AbstractAdministration
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;

/**
 * Common functionality for our Administration Tests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public abstract class AbstractAdministration extends AbstractTest {

    protected static User createAndActiveUser(final AuthenticationToken token, final String username, final String firstname, final String lastname) {
        final NotificationSpy spy = NotificationSpy.getInstance();
        final User user = createUser(token, username, firstname, lastname);

        final String activationCode = spy.getNext(NotificationType.ACTIVATE_USER).getFields().get(NotificationField.CODE);
        administration.activateUser(activationCode);

        return user;
    }

    protected static User createUser(final AuthenticationToken token, final String username, final String firstname, final String lastname) {
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(username);
        createUserRequest.setPassword(lastname);
        createUserRequest.setFirstname(firstname);
        createUserRequest.setLastname(lastname);

        final CreateUserResponse response = administration.createUser(token, createUserRequest);
        assertThat(response.isOk(), is(true));
        assertThat(response.getUser(), is(not(nullValue())));
        assertThat(response.getUser().getUserId(), is(not(nullValue())));

        return response.getUser();
    }

    protected static Group createGroup(final AuthenticationToken token, final GroupType subgroup, final String groupName) {
        return createGroup(token, GroupType.MEMBER, subgroup, groupName).getGroup();
    }

    protected static ProcessGroupResponse createGroup(final AuthenticationToken token, final GroupType parent, final GroupType subgroup, final String groupName) {
        final FetchGroupRequest fetchRequest = new FetchGroupRequest(parent);
        fetchRequest.setUsersToFetch(FetchGroupRequest.FetchType.ACTIVE);
        fetchRequest.setFetchSubGroups(true);
        final FetchGroupResponse fetchResponse = administration.fetchGroup(token, fetchRequest);

        final Group group = new Group();
        group.setGroupName(groupName);
        group.setGroupType(subgroup);
        token.setGroupId(fetchResponse.getGroup().getGroupId());
        final GroupRequest request = new GroupRequest(group);

        return administration.processGroup(token, request);
    }
}
