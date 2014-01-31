/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.spring.ContextProvider
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.spring;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * This Context Provider, will ensure that it is possible for any Class to load
 * the Spring Context. If it is started in a Spring framework (which this code
 * should be), then it can be used directly without any problems. If it is used
 * in a Context without Spring, then the Application Context must be set.<br />
 *   If the system is running without Spring, then Spring can be loaded and all
 * Beans can be instantiated by simply setting the the context as follows:
 * {@code ContextProvider.getInstance().setApplicationContext(new AnnotationConfigApplicationContext(Config.class));}
 *
 * @author  Kim Jensen / last $Author: $
 * @version $Revision:$ / $Date: $
 * @since   1.7
 */
public final class ContextProvider {

    private static final Object LOCK = new Object();
    private static ContextProvider instance = null;
    private final Object contextLock = new Object();
    private ConfigurableApplicationContext applicationContext = null;

    // =========================================================================
    // Factory Instantiation Methods
    // =========================================================================

    /**
     * Default Private Constructor for the class, as it is a Singleton. If we
     * allow that multiple instances of this class exists, then we'll get
     * problems with the Spring database configuration.
     */
    private ContextProvider() {
        applicationContext = new AnnotationConfigApplicationContext(Config.class);
    }

    /**
     * Singleton class instantiator. It will not create a new instance of the
     * {@code ClientFactory}, rather it will load the spring Context, and ask
     * Spring to create a new "Bean", that will then be set as our instance.
     *
     * @return Client Settings instance
     */
    public static ContextProvider getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ContextProvider();
            }

            return instance;
        }
    }

    /**
     * Return the bean instance that uniquely matches the given object type, if
     * any.<br />
     *   This method goes into {@link org.springframework.beans.factory.ListableBeanFactory}
     * by-name lookup based on the given name.
     *
     * @param name The name of the bean. {@code null} is disallowed.
     * @return an instance of the single bean matching the required name
     * @throws IWSException if the bean could not be obtained
     */
    public <T> T getBean(final String name) {
        synchronized (contextLock) {
            if (applicationContext != null) {
                try {
                    final Object obj = applicationContext.getBean(name);
                    return (T) obj;
                } catch (BeansException e) {
                    throw new IWSException(IWSErrors.FATAL, e);
                }
            } else {
                throw new IWSException(IWSErrors.FATAL, "Spring Context not loaded.");
            }
        }
    }
}
