/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.client.spring.Beans;
import net.iaeste.iws.ws.client.clients.AccessWSClient;
import net.iaeste.iws.ws.client.clients.AdministrationWSClient;
import net.iaeste.iws.ws.client.clients.CommitteeWSClient;
import net.iaeste.iws.ws.client.clients.ExchangeWSClient;
import net.iaeste.iws.ws.client.clients.StorageWSClient;
import net.iaeste.iws.ws.client.clients.StudentWSClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.MalformedURLException;

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
 * @since   IWS 1.0
 */
public final class ClientFactory {

    private static final Object LOCK = new Object();
    private static ClientFactory instance = null;
    private final ConfigurableApplicationContext context;
    private static final boolean useWebService = false;
    private static final String wsHost = "localhost";
    private static final String wsPort = "9080";

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
        final Access access;

        if (useWebService) {
            try {
                access = new AccessWSClient("http://" + wsHost + ':' + wsPort + "/iws-ws/accessWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, "Cannot connect to the IWS WebServices: " + e.getMessage(), e);
            }
        } else {
            access = (Access) context.getBean("accessSpringClient");
        }

        return access;
    }

    Administration getAdministrationImplementation() {
        final Administration administration;

        if (useWebService) {
            try {
                administration = new AdministrationWSClient("http://" + wsHost + ':' + wsPort + "/iws-ws/administrationWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, "Cannot connect to the IWS WebServices: " + e.getMessage(), e);
            }
        } else {
            administration = (Administration) context.getBean("administrationSpringClient");
        }

        return administration;
    }

    Storage getStorageImplementation() {
        final Storage storage;

        if (useWebService) {
            try {
                storage = new StorageWSClient("http://" + wsHost + ':' + wsPort + "/iws-ws/storageWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, "Cannot connect to the IWS WebServices: " + e.getMessage(), e);
            }
        } else {
            storage = (Storage) context.getBean("storageSpringClient");
        }

        return storage;
    }

    Committees getCommitteeImplementation() {
        final Committees committees;

        if (useWebService) {
            try {
                committees = new CommitteeWSClient("http://" + wsHost + ':' + wsPort + "/iws-ws/committeeWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, "Cannot connect to the IWS WebServices: " + e.getMessage(), e);
            }
        } else {
            committees = (Committees) context.getBean("committeeSpringClient");
        }

        return committees;
    }

    Exchange getExchangeImplementation() {
        final Exchange exchange;

        if (useWebService) {
            try {
                exchange = new ExchangeWSClient("http://" + wsHost + ':' + wsPort + "/iws-ws/exchangeWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, "Cannot connect to the IWS WebServices: " + e.getMessage(), e);
            }
        } else {
            exchange = (Exchange) context.getBean("exchangeSpringClient");
        }

        return exchange;
    }

    Students getStudentImplementation() {
        final Students students;

        if (useWebService) {
            try {
                students = new StudentWSClient("http://" + wsHost + ':' + wsPort + "/iws-ws/studentWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, "Cannot connect to the IWS WebServices: " + e.getMessage(), e);
            }
        } else {
            students = (Students) context.getBean("studentSpringClient");
        }

        return students;
    }
}
