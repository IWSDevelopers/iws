/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import net.iaeste.iws.ws.client.AccessWSClient;
import net.iaeste.iws.ws.client.AdministrationWSClient;
import net.iaeste.iws.ws.client.CommitteeWSClient;
import net.iaeste.iws.ws.client.ExchangeWSClient;
import net.iaeste.iws.ws.client.StorageWSClient;
import net.iaeste.iws.ws.client.StudentWSClient;
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
    private static final boolean USE_WEB_SERVICE = false;
    private static final String WS_HOST = "http://localhost";
    private static final String WS_PORT = "9080";
    private static final String STANDARD_ERROR = "Cannot connect to the IWS WebServices: ";

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

        if (USE_WEB_SERVICE) {
            try {
                access = new AccessWSClient(WS_HOST + ':' + WS_PORT + "/iws-ws/accessWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, STANDARD_ERROR + e.getMessage(), e);
            }
        } else {
            access = (Access) context.getBean("accessSpringClient");
        }

        return access;
    }

    Administration getAdministrationImplementation() {
        final Administration administration;

        if (USE_WEB_SERVICE) {
            try {
                administration = new AdministrationWSClient(WS_HOST + ':' + WS_PORT + "/iws-ws/administrationWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, STANDARD_ERROR + e.getMessage(), e);
            }
        } else {
            administration = (Administration) context.getBean("administrationSpringClient");
        }

        return administration;
    }

    Storage getStorageImplementation() {
        final Storage storage;

        if (USE_WEB_SERVICE) {
            try {
                storage = new StorageWSClient(WS_HOST + ':' + WS_PORT + "/iws-ws/storageWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, STANDARD_ERROR + e.getMessage(), e);
            }
        } else {
            storage = (Storage) context.getBean("storageSpringClient");
        }

        return storage;
    }

    Committees getCommitteeImplementation() {
        final Committees committees;

        if (USE_WEB_SERVICE) {
            try {
                committees = new CommitteeWSClient(WS_HOST + ':' + WS_PORT + "/iws-ws/committeeWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, STANDARD_ERROR + e.getMessage(), e);
            }
        } else {
            committees = (Committees) context.getBean("committeeSpringClient");
        }

        return committees;
    }

    Exchange getExchangeImplementation() {
        final Exchange exchange;

        if (USE_WEB_SERVICE) {
            try {
                exchange = new ExchangeWSClient(WS_HOST + ':' + WS_PORT + "/iws-ws/exchangeWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, STANDARD_ERROR + e.getMessage(), e);
            }
        } else {
            exchange = (Exchange) context.getBean("exchangeSpringClient");
        }

        return exchange;
    }

    Students getStudentImplementation() {
        final Students students;

        if (USE_WEB_SERVICE) {
            try {
                students = new StudentWSClient(WS_HOST + ':' + WS_PORT + "/iws-ws/studentWS?wsdl");
            } catch (MalformedURLException e) {
                throw new IWSException(IWSErrors.FATAL, STANDARD_ERROR + e.getMessage(), e);
            }
        } else {
            students = (Students) context.getBean("studentSpringClient");
        }

        return students;
    }
}
