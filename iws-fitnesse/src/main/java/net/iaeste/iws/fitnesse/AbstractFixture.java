/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.AbstractFixture
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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.notifications.NotificationMessage;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.fitnesse.callers.AccessCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

import java.util.regex.Pattern;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 * @noinspection StaticNonFinalField, SynchronizationOnStaticField, BooleanMethodNameMustStartWithQuestion
 */
abstract class AbstractFixture<T extends Fallible> implements Fixture {

    private static final Access ACCESS = new AccessCaller();
    private static final NotificationSpy NOTIFICATION = NotificationSpy.getInstance();
    private static final Pattern STRING_PATTERN = Pattern.compile("'");
    private T response = null;
    private String testId = null;
    private String testCase = null;

    // To ensure that the Session will work regardless of the request being
    // made, the data is made static and all access is being synchronized, to
    // avoid that the system enters into an unknown state.
    private static final Object LOCK = new Object();
    private static AuthenticationToken token = null;
    private static String username = null;
    private static String password = null;

    protected void setResponse(final T response) {
        this.response = response;
    }

    protected T getResponse() {
        return response;
    }

    public void setAuthenticationToken(final String tokenString) {
        final String token = readToken(tokenString);
        final String groupId = readGroupId(tokenString);

        if ("null".equalsIgnoreCase(groupId)) {
            this.token = new AuthenticationToken(token);
        } else {
            this.token = new AuthenticationToken(token, groupId);
        }
    }

    public String getAuthenticationToken() {
        return token.toString();
    }

    private static String readToken(final String token) {
        final String[] array = STRING_PATTERN.split(token);

        return array[1].trim();
    }

    private static String readGroupId(final String token) {
        final String[] array = STRING_PATTERN.split(token);

        return array[3].trim();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void testId(final String testId) {
        this.testId = testId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void testCase(final String testCase) {
        this.testCase = testCase;
    }

    /**
     * FitNesse does not allow invocation of Static methods. However, we need
     * some of the methods to be static, to ensure that we can access the
     * Static members of the Class. This is needed to ensure that our Session
     * Object is working across Fixtures being invoked.
     */
    public void setUsername(final String username) {
        doSetUsername(username);
    }

    /**
     * FitNesse does not allow invocation of Static methods. However, we need
     * some of the methods to be static, to ensure that we can access the
     * Static members of the Class. This is needed to ensure that our Session
     * Object is working across Fixtures being invoked.
     */
    public void setPassword(final String password) {
        doSetPassword(password);
    }

    /**
     * FitNesse does not allow invocation of Static methods. However, we need
     * some of the methods to be static, to ensure that we can access the
     * Static members of the Class. This is needed to ensure that our Session
     * Object is working across Fixtures being invoked.
     *
     * @return Response Obect from IWS
     */
    protected AuthenticationResponse createSession() {
        return doCreateSession();
    }

    /**
     * FitNesse does not allow invocation of Static methods. However, we need
     * some of the methods to be static, to ensure that we can access the
     * Static members of the Class. This is needed to ensure that our Session
     * Object is working across Fixtures being invoked.
     *
     * @return IWS Error information
     */
    public Fallible deprecateSession() {
        return doDeprecateSession();
    }

    /**
     * Sets the username part of the login credentials. All requests to the IWS
     * requires an active Session.
     *
     * @param str username
     */
    private static void doSetUsername(final String str) {
        synchronized (LOCK) {
            username = str;
        }
    }

    /**
     * Sets the password part of the login credentials. All requests to the IWS
     * requires an active Session.
     *
     * @param str Password
     */
    private static void doSetPassword(final String str) {
        synchronized (LOCK) {
            password = str;
        }
    }

    /**
     * Creates a new Session to be used for the subsequent requests.
     */
    private static AuthenticationResponse doCreateSession() {
        synchronized (LOCK) {
            final AuthenticationResponse result;

            if (token == null) {
                final AuthenticationRequest request = new AuthenticationRequest(username, password);
                result = ACCESS.generateSession(request);
                token = result.getToken();
            } else {
                result = null;
            }

            return result;
        }
    }

    /**
     * Deprecates the current Session, i.e. equivalent to the user performing a
     * 'log out' operation.
     */
    private static Fallible doDeprecateSession() {
        synchronized (LOCK) {
            final Fallible result = ACCESS.deprecateSession(token);

            // Set the token to null, to signal that it is revoked
            token = null;

            // Return the result of the deprecation
            return result;
        }
    }

    protected static AuthenticationToken getToken() {
        synchronized (LOCK) {
            return token == null ? null : new AuthenticationToken(token.getToken());
        }
    }

    /**
     * Clear the current Notifications.
     */
    public void clearNotifications() {
        NOTIFICATION.clear();
    }

    /**
     * Retrieve the number of pending notifications.
     *
     * @return Number of Notifications pending
     */
    public int getNotificationCount() {
        return NOTIFICATION.size();
    }

    /**
     * Read & remove the next Notification from the Notification Queue.
     *
     * @return First notification from the Queue or null if none exists
     */
    public NotificationMessage getNextNotification() {
        return NOTIFICATION.getNext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequestOk() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.isOk();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer errorCode() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.getError().getError();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String errorMessage() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        response = null;
        testId = null;
        testCase = null;
        resetCredentials();
    }

    private static void resetCredentials() {
        synchronized (LOCK) {
            username = null;
            password = null;
        }
    }

    /**
     * Method indicates if user was already logged in or can log in with current credentials.
     *
     * @return true if user is logged in
     */
    public boolean login() {
        createSession();
        return token != null;
    }

    /**
     * Method deprecates session.
     * <p/>
     * Deprecating session always finishes with success but the method can return false if user was not logged in.
     *
     * @return true if user was logged out after calling the method
     */
    public boolean logout() {
        return deprecateSession().isOk();
    }
}
