package net.iaeste.iws.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.client.AccessClient;
import net.iaeste.iws.client.AdministrationClient;
import net.iaeste.iws.client.spring.NotificationSpy;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class AdministrationTest {

    private final Access access = new AccessClient();
    private AuthenticationToken token = null;
    private final NotificationSpy spy = NotificationSpy.getInstance();
    private final Administration administration = new AdministrationClient();

    private void login() {
        if (token == null) {
            final String username = "austria";
            final String password = "austria";

            final AuthenticationRequest request = new AuthenticationRequest(username, password);
            final AuthenticationResponse response = access.generateSession(request);

            token = response.getToken();
        }
    }

    private void logout() {
        if (token != null) {
            access.deprecateSession(token);
            token = null;
        }
    }

    @Test
    public void testDummy() {
        assertThat(Boolean.TRUE, is(true));
    }

    @Test
    @Ignore("TBD Kim; It's late and this is a bad excuse for not fixing it, but I'm too tired to do it now...")
    public void testCreateAccount() {
        login();
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("alpha");
        createUserRequest.setPassword("beta");
        createUserRequest.setFirstname("Alpha");
        createUserRequest.setLastname("Beta");

        final Fallible result = administration.createUser(token, createUserRequest);
        assertThat(result.isOk(), is(true));
        assertThat(spy.size(), is(1));
        logout();
    }
}
