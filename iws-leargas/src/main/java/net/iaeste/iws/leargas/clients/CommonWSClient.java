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
