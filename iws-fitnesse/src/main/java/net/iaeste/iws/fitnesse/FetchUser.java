package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * Created with IntelliJ IDEA.
 * User: martin
 * Date: 5/12/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
public final class FetchUser extends AbstractFixture<FetchUserResponse> {

    private final Administration administration = new AdministrationCaller();
    private FetchUserRequest request = new FetchUserRequest();

    public void setUserId(final String userId) {
        request.setUserId(userId);
    }

    public String getUserId() {
        return getResponse().getUser().getUserId();
    }

    public String getUsername() {
        return getResponse().getUser().getUsername();
    }

    public String getAlias() {
        return getResponse().getUser().getAlias();
    }

    public String getFirstname() {
        return getResponse().getUser().getFirstname();
    }

    public String getLastname() {
        return getResponse().getUser().getFirstname();
    }

    public String getStatus() {
        return getResponse().getUser().getStatus().toString();
    }

    public String getPrivacy() {
        return getResponse().getUser().getPrivacy().toString();
    }

    public String getMemberCountryId() {
        return getResponse().getUser().getMemberCountryId();
    }

    public String getAlternateEmail() {
        return getResponse().getUser().getPerson().getAlternateEmail();
    }

    public String printUser() {
        final String retVal;

        if (getResponse() == null) {
            retVal = "no response";
        } else {
            retVal = getResponse().getUser().toString();
        }

        return retVal;
    }

    public void FetchUser() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(administration.fetchUser(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
