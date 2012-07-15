/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.security.jaas.IWSLoginModule
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.security.jaas;

import net.iaeste.iws.common.exceptions.AuthenticationException;
import net.iaeste.iws.fe.security.SecurityContext;
import net.iaeste.iws.fe.util.CdiHelper;

import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;

/**
 * Custom implementation of the {@link LoginModule}.
 * <p/>
 * The module needs to be configured for the servers or servlet containers
 * that we use.
 * <p/>
 * This module ensures that, even though we use custom authentication and
 * authorization internally, all the communication between the Frontend
 * and the users goes though the standardized JAAS security framework.
 * <p/>
 * Every user that is successfully logged in is assigned the default role
 * <code>IS_AUTHENTICATED</code>. Without this role, the user does not have
 * access to any part of the Frontend, except for the login and  password
 * forgotten pages.
 * <p/>
 * See <code>WEB-INF/web.xml</code> for the configuration.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class IWSLoginModule implements LoginModule {

    private static final String DEFAULT_ROLE = "IS_AUTHENTICATED";

    /* Callback handler to store between initialization and authentication. */
    private CallbackHandler handler;

    /* Subject to store between initialization and authentication. */
    private Subject subject;

    /* The security context for the current http session */
    @Inject
    private SecurityContext securityContext;

    private String login;
    private String credentials;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        initSecurityContext();
        this.handler = callbackHandler;
        this.subject = subject;
    }

    /**
     * Initialize resources if they haven't been injected yet.
     * Injection via @Inject does not usually work at least for
     * servlet containers so we have to do it programatically.
     */
    private void initSecurityContext() {
        if (securityContext == null) {
            CdiHelper.programmaticInjection(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", true);

        try {
            handler.handle(callbacks);

            String name = ((NameCallback) callbacks[0]).getName().trim();
            String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword()).trim();

            try {
                securityContext.authenticate(name, password);
            } catch (AuthenticationException e) {
                throw new LoginException("Authentication failed");
            }

            login = name;
            credentials = password;

            return true;

        } catch (IOException | UnsupportedCallbackException e) {
            throw new LoginException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean commit() throws LoginException {
        try {
            IWSUser user = new IWSUser(login);
            IWSRole role = new IWSRole(DEFAULT_ROLE);

            subject.getPrincipals().add(user);
            subject.getPrivateCredentials().add(credentials);
            subject.getPrincipals().add(role);

            return true;

        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean logout() throws LoginException {
        try {
            securityContext.logout();

            IWSUser user = new IWSUser(login);
            IWSRole role = new IWSRole(DEFAULT_ROLE);

            subject.getPrincipals().remove(user);
            subject.getPrincipals().remove(role);

            return true;

        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }

}
