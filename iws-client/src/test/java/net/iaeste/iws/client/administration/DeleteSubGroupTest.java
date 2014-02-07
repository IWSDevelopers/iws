/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.administration.DeleteSubGroupTest
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.api.util.Fallible;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class DeleteSubGroupTest extends AbstractAdministration {

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
    // Positive Tests, when deleting a subgroup
    // =========================================================================

    @Test
    public void testDeleteLocalCommittee() {
        final ProcessGroupResponse createResponse = createGroup(token, GroupType.MEMBER, GroupType.LOCAL, "new Local Committee");

        final Group memberGroup = findMemberGroup(token);
        token.setGroupId(memberGroup.getGroupId());
        final GroupRequest request = new GroupRequest(createResponse.getGroup());
        final Fallible deleteResponse = client.deleteSubGroup(token, request);
        assertThat(deleteResponse, is(not(nullValue())));
        assertThat(deleteResponse.isOk(), is(true));
        assertThat(deleteResponse.getError(), is(IWSErrors.SUCCESS));
        assertThat(deleteResponse.getMessage(), is(IWSConstants.SUCCESS));
    }

    @Test
    public void testDeleteNationalWorkGroupFromMembers() {
        final ProcessGroupResponse createResponse = createGroup(token, GroupType.NATIONAL, GroupType.WORKGROUP, "new National WorkGroup");

        final Group memberGroup = findMemberGroup(token);
        token.setGroupId(memberGroup.getGroupId());
        final GroupRequest request = new GroupRequest(createResponse.getGroup());
        final Fallible deleteResponse = client.deleteSubGroup(token, request);
        assertThat(deleteResponse, is(not(nullValue())));
        assertThat(deleteResponse.isOk(), is(false));
        assertThat(deleteResponse.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(deleteResponse.getMessage(), is("The Group is not associated with the requesting Group."));
    }

    @Test
    public void testDeleteLocalCommitteeWithWorkGroup() {
        final ProcessGroupResponse createResponse = createGroup(token, GroupType.MEMBER, GroupType.LOCAL, "Local Committee With Workgroup");

        // Create a Subgroup to our Local Committee
        final Group group = new Group();
        group.setGroupName("Our Little WorkGroup");
        group.setGroupType(GroupType.WORKGROUP);
        token.setGroupId(createResponse.getGroup().getGroupId());
        final GroupRequest subGrouprequest = new GroupRequest(group);
        final ProcessGroupResponse subGroupResponse = client.processGroup(token, subGrouprequest);
        assertThat(subGroupResponse, is(not(nullValue())));
        assertThat(subGroupResponse.isOk(), is(true));

        token.setGroupId(findMemberGroup(token).getGroupId());
        final GroupRequest request = new GroupRequest(createResponse.getGroup());
        final Fallible response = client.deleteSubGroup(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(response.getMessage(), is("The Group being deleted contains SubGroups."));
    }

    @Test
    public void testDeleteNationalGroup() {
        final Group nationalGroup = findNationalGroup(token);
        final Group memberGroup = findMemberGroup(token);

        token.setGroupId(memberGroup.getGroupId());
        final GroupRequest request = new GroupRequest(nationalGroup);
        final Fallible response = client.deleteSubGroup(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.NOT_PERMITTED));
        assertThat(response.getMessage(), is("It is not allowed to remove groups of type NATIONAL with this request."));
    }
}
