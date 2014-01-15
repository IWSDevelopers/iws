/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.administration.GroupProcessingTest
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

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.api.util.Fallible;
import org.junit.Test;

/**
 * This test will attempt to verify the creation and updating of our normally
 * allowed Groups, WorkGroups & Local Committees. Other types of Groups are
 * controlled via other mechanisms.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class GroupProcessingTest extends AbstractAdministration {

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

    // =========================================================================
    // Positive Tests, when creating a subgroup
    // =========================================================================

    @Test
    public void testCreatingLocalAsSubGroupToMembers() {
        final ProcessGroupResponse result = createGroup(token, GroupType.MEMBER, GroupType.LOCAL, "My Local Committee");
        assertThat(result.isOk(), is(true));
        assertThat(result.getGroup(), is(not(nullValue())));

        // Okay, created Group - let's try to modify it
        final Group group = result.getGroup();
        // Changes to GroupType should be ignored
        group.setGroupType(GroupType.ADMINISTRATION);
        group.setDescription("My Description");

        final GroupRequest request = new GroupRequest(group);
        final ProcessGroupResponse response = client.processGroup(token, request);

        // Now, check that the changes are in
        assertThat(response.isOk(), is(true));
        assertThat(response.getGroup(), is(not(nullValue())));
        assertThat(response.getGroup().getGroupId(), is(group.getGroupId()));
        assertThat(response.getGroup().getDescription(), is("My Description"));
        assertThat(response.getGroup().getGroupType(), is(GroupType.LOCAL));
    }

    @Test
    public void testCreatingWorkGroupAsSubGroupToMembers() {
        final ProcessGroupResponse result = createGroup(token, GroupType.MEMBER, GroupType.WORKGROUP, "My Work Group");
        assertThat(result.isOk(), is(true));
        assertThat(result.getGroup(), is(not(nullValue())));

        // Okay, created Group - let's try to modify it
        final Group group = result.getGroup();
        // Changes to GroupType should be ignored
        group.setGroupType(GroupType.ADMINISTRATION);
        group.setDescription("My Description");

        final GroupRequest request = new GroupRequest(group);
        final ProcessGroupResponse response = client.processGroup(token, request);

        // Now, check that the changes are in
        assertThat(response.isOk(), is(true));
        assertThat(response.getGroup(), is(not(nullValue())));
        assertThat(response.getGroup().getGroupId(), is(group.getGroupId()));
        assertThat(response.getGroup().getDescription(), is("My Description"));
        assertThat(response.getGroup().getGroupType(), is(GroupType.WORKGROUP));
    }

    @Test
    public void testCreatingWorkGroupAsSubGroupToNational() {
        final ProcessGroupResponse result = createGroup(token, GroupType.NATIONAL, GroupType.WORKGROUP, "My Work Group");
        assertThat(result.isOk(), is(true));
        assertThat(result.getGroup(), is(not(nullValue())));

        // Okay, created Group - let's try to modify it
        final Group group = result.getGroup();
        // Changes to GroupType should be ignored
        group.setGroupType(GroupType.ADMINISTRATION);
        group.setDescription("My Description");

        final GroupRequest request = new GroupRequest(group);
        final ProcessGroupResponse response = client.processGroup(token, request);

        // Now, check that the changes are in
        assertThat(response.isOk(), is(true));
        assertThat(response.getGroup(), is(not(nullValue())));
        assertThat(response.getGroup().getGroupId(), is(group.getGroupId()));
        assertThat(response.getGroup().getDescription(), is("My Description"));
        assertThat(response.getGroup().getGroupType(), is(GroupType.WORKGROUP));
    }

    // =========================================================================
    // Negative tests, when creating new subgroups
    // =========================================================================

    @Test
    public void testUpdatingIllegalGroup() {
        final Group group = findNationalGroup(token);
        group.setGroupName("New Name");
        token.setGroupId(group.getGroupId());
        final GroupRequest request = new GroupRequest(group);

        final ProcessGroupResponse response = client.processGroup(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(response.getMessage(), is("It is not permitted to update Groups of type NATIONAL with this request."));
    }

    @Test
    public void testUpdatingInvalidGroup() {
        final Group group = new Group();

        // Invalid Id
        group.setGroupId("c7b32f81-4f83-48e8-9ffb-9e73255f5e5e");
        group.setGroupName("New Name");
        group.setGroupType(GroupType.LOCAL);
        final GroupRequest request = new GroupRequest(group);

        token.setGroupId(findMemberGroup(token).getGroupId());
        final ProcessGroupResponse response = client.processGroup(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.AUTHORIZATION_ERROR));
        assertThat(response.getMessage(), is("User is not permitted to perform actions of type: PROCESS_GROUP"));
    }

    @Test
    public void testCreatingAdministrationAsSubGroupToMembers() {
        final Fallible result = createGroup(token, GroupType.MEMBER, GroupType.ADMINISTRATION, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.ADMINISTRATION.getDescription() + "'."));
    }

    @Test
    public void testCreatingPrivateAsSubGroupToMembers() {
        final Fallible result = createGroup(token, GroupType.MEMBER, GroupType.PRIVATE, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.PRIVATE.getDescription() + "'."));
    }

    @Test
    public void testCreatingMemberAsSubGroupToMembers() {
        final Fallible result = createGroup(token, GroupType.MEMBER, GroupType.MEMBER, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.MEMBER.getDescription() + "'."));
    }

    @Test
    public void testCreatingInternationalAsSubGroupToMembers() {
        final Fallible result = createGroup(token, GroupType.MEMBER, GroupType.INTERNATIONAL, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.INTERNATIONAL.getDescription() + "'."));
    }

    @Test
    public void testCreatingRegionalAsSubGroupToMembers() {
        final Fallible result = createGroup(token, GroupType.MEMBER, GroupType.REGIONAL, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.REGIONAL.getDescription() + "'."));
    }

    @Test
    public void testCreatingNationalAsSubGroupToMembers() {
        final Fallible result = createGroup(token, GroupType.MEMBER, GroupType.NATIONAL, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.NATIONAL.getDescription() + "'."));
    }

    @Test
    public void testCreatingStudentsAsSubGroupToMembers() {
        final Fallible result = createGroup(token, GroupType.MEMBER, GroupType.STUDENT, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.STUDENT.getDescription() + "'."));
    }

    @Test
    public void testCreatingAdministrationAsSubGroupToNational() {
        final Fallible result = createGroup(token, GroupType.NATIONAL, GroupType.ADMINISTRATION, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.ADMINISTRATION.getDescription() + "'."));
    }

    @Test
    public void testCreatingPrivateAsSubGroupToNational() {
        final Fallible result = createGroup(token, GroupType.NATIONAL, GroupType.PRIVATE, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.PRIVATE.getDescription() + "'."));
    }

    @Test
    public void testCreatingMemberAsSubGroupToNational() {
        final Fallible result = createGroup(token, GroupType.NATIONAL, GroupType.MEMBER, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.MEMBER.getDescription() + "'."));
    }

    @Test
    public void testCreatingInternationalAsSubGroupToNational() {
        final Fallible result = createGroup(token, GroupType.NATIONAL, GroupType.INTERNATIONAL, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.INTERNATIONAL.getDescription() + "'."));
    }

    @Test
    public void testCreatingRegionalAsSubGroupToNational() {
        final Fallible result = createGroup(token, GroupType.NATIONAL, GroupType.REGIONAL, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.REGIONAL.getDescription() + "'."));
    }

    @Test
    public void testCreatingNationalAsSubGroupToNational() {
        final Fallible result = createGroup(token, GroupType.NATIONAL, GroupType.NATIONAL, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.NATIONAL.getDescription() + "'."));
    }

    @Test
    public void testCreatingLocalAsSubGroupToNational() {
        final Fallible result = createGroup(token, GroupType.NATIONAL, GroupType.LOCAL, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.LOCAL.getDescription() + "'."));
    }

    @Test
    public void testCreatingStudentsAsSubGroupToNational() {
        final Fallible result = createGroup(token, GroupType.NATIONAL, GroupType.STUDENT, "My Group");
        assertThat(result.isOk(), is(false));
        assertThat(result.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(result.getMessage(), is("Not allowed to create a sub-group of type '" + GroupType.STUDENT.getDescription() + "'."));
    }

    @Test
    public void testCreatingDuplicateGroup() {
        final String duplicateName = "Duplicate Name";
        final ProcessGroupResponse result = createGroup(token, GroupType.MEMBER, GroupType.WORKGROUP, duplicateName);
        assertThat(result.isOk(), is(true));

        final ProcessGroupResponse failed = createGroup(token, GroupType.MEMBER, GroupType.WORKGROUP, duplicateName);
        assertThat(failed.isOk(), is(false));
    }
}
