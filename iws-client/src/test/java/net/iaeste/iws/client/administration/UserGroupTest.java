/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.administration.UserGroupTest
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

import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchRoleRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.util.Fallible;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class UserGroupTest extends AbstractAdministration {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
        token = login("denmark@iaeste.dk", "denmark");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tearDown() {
        logout(token);
    }

    @Test
    public void testAddingUserToNationalGroup() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("user@iaeste.dk");
        createUserRequest.setFirstname("Firstname");
        createUserRequest.setLastname("Lastname");
        final CreateUserResponse createUserResponse = client.createUser(token, createUserRequest);
        assertThat(createUserResponse.isOk(), is(true));

        final Group nationalGroup = findNationalGroup(token);
        final FetchRoleRequest fetchRoleRequest = new FetchRoleRequest(nationalGroup.getId());
        final FetchRoleResponse fetchRoleResponse = client.fetchRoles(token, fetchRoleRequest);

        // Add the user to the National Group
        final UserGroup userGroup = new UserGroup();
        userGroup.setGroup(nationalGroup);
        userGroup.setUser(createUserResponse.getUser());
        userGroup.setRole(fetchRoleResponse.getRoles().get(1));
        UserGroupAssignmentRequest request = new UserGroupAssignmentRequest(userGroup);
        final Fallible response = client.processUserGroupAssignment(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));

        // Delete the user again
        request.setDeleteUser(true);
        final Fallible deleteResponse = client.processUserGroupAssignment(token, request);
        assertThat(deleteResponse, is(not(nullValue())));
        assertThat(deleteResponse.isOk(), is(true));
    }
}
