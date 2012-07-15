/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.security.AuthenticationFilter
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fe.security;

import net.iaeste.iws.fe.security.SecurityContext;
import net.iaeste.iws.fe.util.CdiHelper;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * If an already authenticated user tries to access the login page,
 * the filter will redirect to the welcome page.
 * <p/>
 * Logging in again while a user is already logged in with the
 * same or a different username causes the {@link net.iaeste.iws.fe.security.jaas.IWSLoginModule}
 * to login the new user and logout the old user in this particular order.
 * <p/>
 * This causes problems for the {@link SecurityContext} because of
 * the following workflow:
 * <ol>
 * <li>the user logs in the first time</li>
 * <li>the SecurityContext request a new session</li>
 * <li>the user logs in a second time</li>
 * <li>the SecurityContext request a new session without deprecating the previous one</li>
 * <li>the SecurityContext immediately deprecates the new session because
 * {@link net.iaeste.iws.fe.security.jaas.IWSLoginModule#logout()} is called</li>
 * </ol>
 *
 * This filter prevents this by not allowing an authenticated user to
 * access the login page.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = "/pages/login.xhtml")
public class AuthenticationFilter implements Filter {

    @Inject
    private SecurityContext securityContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nothing to do here
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (req instanceof HttpServletRequest && resp instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;

            if (userAlreadyLoggedIn()) {
                response.sendRedirect(request.getContextPath());
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    private boolean userAlreadyLoggedIn() {
        if (securityContext == null) {
            CdiHelper.programmaticInjection(this);
        }
        return securityContext.isAuthenticated();
    }

    @Override
    public void destroy() {
        // nothing to destroy
    }
}
