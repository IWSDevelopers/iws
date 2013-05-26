package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * Created with IntelliJ IDEA.
 * User: martin
 * Date: 5/12/13
 * Time: 5:54 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ControlUserAccount extends AbstractFixture<Fallible> {

    private final Administration administration = new AdministrationCaller();
    private UserRequest request = new UserRequest();
    private User user = new User();

    public void setUserId(final String userId) {
        user.setUserId(userId);
    }

    public void setUsername(final String username) {
        user.setUsername(username);
    }

    public void setAlias(final String alias) {
        user.setAlias(alias);
    }

    public void setFirstname(final String firstname) {
        user.setFirstname(firstname);
    }

    public void setLastname(final String lastname) {
        user.setLastname(lastname);
    }

    public void setStatus(final String status) {
        user.setStatus(UserStatus.valueOf(status));
    }

    public void setPrivacy(final String privacy) {
        user.setPrivacy(Privacy.valueOf(privacy));
    }

    public void setMemberCountryId(final String memberCountryId) {
        user.setMemberCountryId(memberCountryId);
    }

    public void setAlternateEmail(final String alternateEmail) {
        Person person = new Person();
        person.setAlternateEmail(alternateEmail);
        user.setPerson(person);
    }

    public void ControlUserAccount() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        request.setUser(user);
        setResponse(administration.controlUserAccount(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
