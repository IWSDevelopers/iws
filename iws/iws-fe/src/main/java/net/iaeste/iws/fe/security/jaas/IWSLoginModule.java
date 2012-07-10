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
import net.iaeste.iws.fe.security.SecurityContextImpl;
import net.iaeste.iws.fe.util.BeanManagerProvider;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

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
 * See <code>web.xml</code> for the configuration.
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

    private String login;
    private String credentials;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.handler = callbackHandler;
        this.subject = subject;
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

            String name = ((NameCallback) callbacks[0]).getName();
            String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());

            try {
                getSecurityContext().authenticate(name, password);
            } catch (AuthenticationException e) {
                throw new LoginException("Authentication failed");
            }

            login = name;
            credentials = password;

            return true;

        } catch (IOException e) {
            throw new LoginException(e.getMessage());
        } catch (UnsupportedCallbackException e) {
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
            getSecurityContext().logout();

            IWSUser user = new IWSUser(login);
            IWSRole role = new IWSRole(DEFAULT_ROLE);

            subject.getPrincipals().remove(user);
            subject.getPrincipals().remove(role);

            return true;
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }

    /**
     * Get the current instance of the {@link net.iaeste.iws.fe.security.SecurityContext}
     * We cannot use injection through annotations here so we have to lookup the bean
     * manually.
     *
     * @return
     */
    private SecurityContext getSecurityContext() {
        BeanManager bm = BeanManagerProvider.getBeanManager();
        Set<Bean<?>> beans = bm.getBeans(SecurityContextImpl.class);

        if (beans.size() == 0) {
            throw new RuntimeException("no beans of type " + SecurityContextImpl.class.getName() + " found!");
        }

        if (beans.size() > 1) {
            throw new RuntimeException("multiple beans of type " + SecurityContextImpl.class.getName() + " found!");
        }

        Bean<SecurityContext> bean = (Bean<SecurityContext>) beans.iterator().next();
        CreationalContext<SecurityContext> ctx = bm.createCreationalContext(bean);

        return (SecurityContext) bm.getReference(bean, SecurityContext.class, ctx);
    }


}
