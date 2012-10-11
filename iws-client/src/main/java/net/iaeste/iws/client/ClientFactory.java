/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ConnectionFactory
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.client.spring.Beans;
import net.iaeste.iws.client.spring.SpringAccessClient;
import net.iaeste.iws.client.spring.SpringAdministrationclient;
import net.iaeste.iws.client.spring.SpringExchangeClient;
import net.iaeste.iws.core.services.ServiceFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManagerFactory;

/**
 * <p>The ClientFactory will use the provided Properties, to determine which
 * instance or implementation of IWS to use for external testing.</p>
 *
 * <p>Class is made package private, since it is only suppose to be used by the
 * actual Client Classes in this package.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection StaticNonFinalField
 */
public final class ClientFactory {

    private static final Boolean USE_XML_CONFIG = true;
    private static final Object LOCK = new Object();
    private static ClientFactory instance = null;
    private final ServiceFactory serviceFactory;

    // =========================================================================
    // Factory Instantiation Methods
    // =========================================================================

    /**
     * Default Private Constructor for the class, as it is a Singleton. The
     * Constructor will set the Application Context to the ones from the Spring
     * based BeanConfiguration class, that emulates a primitive AppServer.
     */
    private ClientFactory() {
        final ConfigurableApplicationContext context;

        if (USE_XML_CONFIG) {
            context = new ClassPathXmlApplicationContext("/net/iaeste/iws/client/spring/beans.xml");
        } else {
            context = new AnnotationConfigApplicationContext(Beans.class);
        }

        serviceFactory = new ServiceFactory(context.getBean(EntityManagerFactory.class).createEntityManager());
    }

    /**
     * Singleton class instantiator.
     *
     * @return Client Settings instance
     */
    public static ClientFactory getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ClientFactory();
            }

            return instance;
        }
    }

    // =========================================================================
    // IWS API Implementations
    // =========================================================================

    public Access getAccessImplementation() {
        return new SpringAccessClient(serviceFactory);
    }

    public Administration getAdministrationImplementation() {
        return new SpringAdministrationclient(serviceFactory);
    }

    public Exchange getExchangeImplementation() {
        return new SpringExchangeClient(serviceFactory);
    }
}
