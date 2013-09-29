/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.FetchGroup
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchGroup extends AbstractFixture<FetchGroupResponse> {

    private final Administration administration = new AdministrationCaller();
    private FetchGroupRequest request = new FetchGroupRequest();

    public void setGroupId(final String groupId) {
        request.setGroupId(groupId);
    }

    public void setFetchUsers(final boolean fetchUsers) {
        request.setFetchUsers(fetchUsers);
    }

    public void setFetchSubGroups(final boolean fetchSubGroups) {
        request.setFetchSubGroups(fetchSubGroups);
    }

    public int numberOfUsersInGroup() {
        return getResponse() == null ? -1 : getResponse().getUserGroups().size();
    }

    public String printGroup() {
        final String retVal;

        if (getResponse() == null) {
            retVal = "no response";
        } else {
            retVal = getResponse().getGroup().toString();
        }

        return retVal;
    }

    public String printUser(final int userIndex) {
        final String retVal;

        if (getResponse() == null) {
            retVal = "no response";
        } else if ((userIndex < 1) || (userIndex > numberOfUsersInGroup())) {
            retVal = "no user for given index";
        } else {
            retVal = getResponse().getUserGroups().get(userIndex - 1).toString();
        }

        return retVal;
    }

    public void fetchGroup() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(administration.fetchGroup(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
