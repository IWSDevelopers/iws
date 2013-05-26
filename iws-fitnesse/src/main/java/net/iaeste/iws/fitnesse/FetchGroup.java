package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * Created with IntelliJ IDEA.
 * User: martin
 * Date: 5/12/13
 * Time: 5:56 PM
 * To change this template use File | Settings | File Templates.
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
        return getResponse() == null ? -1 : getResponse().getUsers().size();
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
            retVal = getResponse().getUsers().get(userIndex - 1).toString();
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
