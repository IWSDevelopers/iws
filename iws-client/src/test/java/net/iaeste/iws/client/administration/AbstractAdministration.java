/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.AdministrationClient;

/**
 * Common functionality for our Administration Tests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public abstract class AbstractAdministration extends AbstractTest {

    protected static final Administration client = new AdministrationClient();

    protected static ProcessGroupResponse createGroup(final AuthenticationToken token, final GroupType parent, final GroupType subgroup, final String groupName) {
        final FetchGroupRequest fetchRequest = new FetchGroupRequest(parent);
        fetchRequest.setFetchUsers(true);
        fetchRequest.setFetchSubGroups(true);
        final FetchGroupResponse fetchResponse = client.fetchGroup(token, fetchRequest);

        final Group group = new Group();
        group.setGroupName(groupName);
        group.setGroupType(subgroup);
        token.setGroupId(fetchResponse.getGroup().getId());
        final GroupRequest request = new GroupRequest(group);

        return client.processGroup(token, request);
    }
}
