/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-leargas) - net.iaeste.iws.leargas.clients.CommonWSClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.leargas.clients;

import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * Common and shared settings for all IWS WebService Clients.
 *
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public class CommonWSClient extends Service {

    protected static final String ENDPOINT_ADDRESS = BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

    // Although lazy-initialization can be a good thing, it can also come with
    // some consequences. We wish to expose the Client Parameters, so we can use
    // them without locking - as we would otherwise end up in a race-condition.
    // Solution is simple, pre-initialize it with default settings, and then use
    // a Lock & Flag to complete the process. This way the parameters can be
    // used regardlessly, provided we're checking and initializing it in all
    // Client Constructors.
    protected final HTTPClientPolicy policy = new HTTPClientPolicy();

    protected CommonWSClient(final URL wsdlLocation, final QName serviceName) {
        super(wsdlLocation, serviceName);
    }
}
