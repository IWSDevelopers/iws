/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.util.BeanManagerProvider
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.util;

import net.iaeste.iws.fe.exceptions.BeanManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Helper class for getting an instance of {@link BeanManager}
 * through JNDI lookup.
 * <p/>
 * The <code>BeanManager</code> can be used to manually request
 * an instance of a CDI Bean, if it cannot be resolved through
 * dependency injection.
 * <p/>
 * In some servlet containers, dependency injection through
 * Annotations is not possible into Servlets, Filters and Interceptor.
 * The BeanManager provides a way to get those resources programatically.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class BeanManagerProvider {

    private static final Logger LOG = LoggerFactory.getLogger(BeanManagerProvider.class);

    private static final String SERVLET_CONTAINER_JNDI_NAME = "java:comp/env/BeanManager";
    private static final String SERVER_JNDI_NAME = "java:comp/BeanManager";

    /**
     * Get the current BeanManager through JNDI lookup.
     *
     * @return the current BeanManager instance
     * @throws
     */
    public static BeanManager getBeanManager() {
        LOG.debug("Looking up BeanManager");
        BeanManager manager;

        // servlet containers usually use a different name than servers

        try {
            // try servlet container like Jetty or Tomcat first
            LOG.debug("Lookin up BeanManager for JNDI name {}", SERVLET_CONTAINER_JNDI_NAME);
            manager = (BeanManager) new InitialContext().lookup(SERVLET_CONTAINER_JNDI_NAME);
        } catch (NamingException e) {
            LOG.debug("No BeanManager found in servlet container. Not a servlet container?", e);
            // try server now
            try {
                manager = (BeanManager) new InitialContext().lookup(SERVER_JNDI_NAME);
            } catch (NamingException ex) {
                // now we have a problem
                LOG.error("No BeanManager found through JNDI lookup. Does injection with @Inject work?", ex);
                throw new BeanManagerException("No BeanManager found for names [" + SERVLET_CONTAINER_JNDI_NAME + "," + SERVER_JNDI_NAME + "] ");
            }
        }

        LOG.debug("BeanManager found");
        return manager;
    }
}
