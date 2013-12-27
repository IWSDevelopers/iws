/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (${module.name}) - ${file.name}
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
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
public final class ContextProvider /*implements ApplicationContextAware*/ {

    private static final Logger log = LoggerFactory.getLogger(ContextProvider.class);

    private static final Object LOCK = new Object();
    private static ContextProvider instance = null;
    private final Object contextLock = new Object();
    private ApplicationContext applicationContext = null;

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

    ///**
    // * {@inheritDoc}
    // */
    //@Override
    //public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
    //    log.info("Setting of Application Context is prohibited.");
    //    //log.info("Setting context.");
    //    //synchronized (contextLock) {
    //    //    this.applicationContext = applicationContext;
    //    //}
    //}

    /**
     * Return the bean instance that uniquely matches the given object type, if
     * any.<br />
     *   This method goes into {@link org.springframework.beans.factory.ListableBeanFactory}
     * by-type lookup territory but may also be translated into a conventional
     * by-name lookup based on the name of the given type.
     *
     * @param requiredType type the bean must match; can be an interface or superclass.
     *                     {@code null} is disallowed.
     * @return an instance of the single bean matching the required type
     * @throws IWSException if the bean could not be obtained
     */
    public <T> T getBean(final Class<T> requiredType) {
        synchronized (contextLock) {
            final T bean;

            if (applicationContext != null) {
                try {
                    bean = applicationContext.getBean(requiredType);
                } catch (BeansException e) {
                    throw new IWSException(IWSErrors.FATAL, e);
                }
            } else {
                throw new IWSException(IWSErrors.FATAL, "Spring Context not loaded.");
            }

            return bean;
        }
    }

    ///**
    // * Return an instance, which may be shared or independent, of the specified
    // * bean.<br />
    // *   This method allows a Spring BeanFactory to be used as a replacement for
    // * the Singleton or Prototype design pattern. Callers may retain references
    // * to returned objects in the case of Singleton beans.<br />
    // *   Translates aliases back to the corresponding canonical bean name. Will
    // * ask the parent factory if the bean cannot be found in this factory
    // * instance.
    // *
    // * @param name the name of the bean to retrieve
    // * @return an instance of the bean
    // * @throws IWSException if the bean could not be obtained
    // */
    //public Object getBean(final String name) throws IWSException {
    //    synchronized (contextLock) {
    //        final Object bean;
    //
    //        if (applicationContext != null) {
    //            try {
    //                bean = applicationContext.getBean(name);
    //            } catch (BeansException e) {
    //                throw new IWSException(IWSErrors.FATAL, e);
    //            }
    //        } else {
    //            throw new IWSException(IWSErrors.FATAL, "Spring Context not loaded.");
    //        }
    //
    //        return bean;
    //    }
    //}
}
