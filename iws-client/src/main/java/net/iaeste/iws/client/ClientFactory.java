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
import net.iaeste.iws.client.spring.SpringAdministrationclient;
import net.iaeste.iws.client.spring.SpringExchangeClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The ClientFactory will use the provided Properties, to determine which
 * instance or implementation of IWS to use for external testing.<br />
 *   Class is made package private, since it is only suppose to be used by the
 * actual Client Classes in this package.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection StaticNonFinalField
 */
public final class ClientFactory {

    private static final Object LOCK = new Object();
    private static ClientFactory instance = null;
    private final ConfigurableApplicationContext context;

    private Access access = null;
    private SpringAdministrationclient administration = null;
    private SpringExchangeClient exchange = null;

    // =========================================================================
    // Factory Instantiation Methods
    // =========================================================================

    /**
     * Default Private Constructor for the class, as it is a Singleton. If we
     * allow that multiple instances of this class exists, then we'll get
     * problems with the Spring database configuration.
     */
    private ClientFactory() {
        context = new ClassPathXmlApplicationContext("classpath:net/iaeste/iws/client/spring/beans.xml");
    }

    /**
     * Singleton class instantiator. It will not create a new instance of the
     * {@code ClientFactory}, rather it will load the spring Context, and ask
     * Spring to create a new "Bean", that will then be set as our instance.
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

    /**
     *
     * @return
     */
    public Access getAccessImplementation() {
        synchronized (LOCK) {
            if (access == null) {
                access = (Access) context.getBean("springAccessClient");
                //access = context.getBean(SpringAccessClient.class);
            }

            return access;
        }
    }

    public SpringAdministrationclient getAdministrationImplementation() {
        synchronized (LOCK) {
            if (administration == null) {
                administration = context.getBean(SpringAdministrationclient.class);
            }

            return administration;
        }
    }

    public SpringExchangeClient getExchangeImplementation() {
        synchronized (LOCK) {
            if (exchange == null) {
                exchange = context.getBean(SpringExchangeClient.class);
            }

            return exchange;
        }
    }
}
