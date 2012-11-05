package net.iaeste.iws.it;

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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

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

    @Before
    public void before() {
        if (token == null) {
            final String username = "austria";
            final String password = "austria";

            final AuthenticationRequest request = new AuthenticationRequest(username, password);
            final AuthenticationResponse response = access.generateSession(request);

            token = response.getToken();
        }

        // Clear all messages from the Notification Queue
        spy.clear();
    }

    @After
    public void after() {
        if (token != null) {
            access.deprecateSession(token);
            token = null;
        }
    }

    @Test
    public void testCreateAccount() {
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("alpha");
        createUserRequest.setPassword("beta");
        createUserRequest.setFirstname("Alpha");
        createUserRequest.setLastname("Beta");

        final Fallible result = administration.createUser(token, createUserRequest);
        assertThat(result.isOk(), is(true));
        assertThat(spy.size(), is(1));
        assertThat(spy.getNext().generateNotificationMessage(), containsString("Activation Code"));
    }
}
