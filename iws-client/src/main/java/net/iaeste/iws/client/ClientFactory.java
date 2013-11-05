/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ClientFactory
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
import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.Students;
import net.iaeste.iws.client.spring.Beans;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * The ClientFactory will use the provided Properties, to determine which
 * instance or implementation of IWS to use for external testing.<br />
 *   Class is made package private, since it is only suppose to be used by the
 * actual Client Classes in this package.<br />
 *   Note, rather than public, the methods are package private, since this class
 * is only supposed to be used by other Classes in this package.
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

    // =========================================================================
    // Factory Instantiation Methods
    // =========================================================================

    /**
     * Default Private Constructor for the class, as it is a Singleton. If we
     * allow that multiple instances of this class exists, then we'll get
     * problems with the Spring database configuration.
     */
    private ClientFactory() {
        context = new AnnotationConfigApplicationContext(Beans.class);
    }

    /**
     * Singleton class instantiator. It will not create a new instance of the
     * {@code ClientFactory}, rather it will load the spring Context, and ask
     * Spring to create a new "Bean", that will then be set as our instance.
     *
     * @return Client Settings instance
     */
    static ClientFactory getInstance() {
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

    Access getAccessImplementation() {
        return (Access) context.getBean("accessSpringClient");
    }

    Administration getAdministrationImplementation() {
        return (Administration) context.getBean("administrationSpringClient");
    }

    Committees getCommitteeImplementation() {
        return (Committees) context.getBean("committeeSpringClient");
    }

    Exchange getExchangeImplementation() {
        return (Exchange) context.getBean("exchangeSpringClient");
    }

    Students getStudentImplementation() {
        return (Students) context.getBean("studentSpringClient");
    }
}
