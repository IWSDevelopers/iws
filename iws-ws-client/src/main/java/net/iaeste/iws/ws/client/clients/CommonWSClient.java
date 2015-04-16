/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ws.AbstractClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ws.client.clients;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
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
    protected static final TLSClientParameters tlsClientParameters = new TLSClientParameters();
    protected final HTTPClientPolicy policy = new HTTPClientPolicy();
    private static volatile boolean isSSLInitialized = false;
    private static final Lock sslLock = new ReentrantLock();

    protected CommonWSClient(final URL wsdlLocation, final QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HTTPClientPolicy getPolicy() {
        return policy;
    }

    /**
     * This method will complete the initialization of the SSL Client Parameter
     * Object. The Object is part of the HTTP Conduit which is shared among all
     * CXF threads during the current session.
     */
    protected static void initializeSSL() {
        // Note, we're locking outside of the try-finally block, since an error
        // with acquiring the lock would otherwise lead to an unlock attemt,
        // which will throw an IllegalMonitorState Exception. So to avoid this,
        // we simply keep the lock outside :-)
        sslLock.lock();

        try {
            // If we already have initialized the TLS, no need to repeat it.
            // However, both the check and the initialization must be done
            // within this synchronized block to avoid problems. CXF is
            // generally thread-safe, however the HTTP Conduit is shared across
            // the session, which is why we initialize it like so.
            if (!isSSLInitialized) {
                isSSLInitialized = true;

                // Disabling the check regarding the Common Name (CN) on the
                // Server's Certificate, it may work well for the production
                // servers, as the IWS us running is using a star certificate.
                // But not for local development.
                tlsClientParameters.setDisableCNCheck(true);

                // Append a KeyStore to the Parameters, of a Client Certificate
                // to Authenticate the requesting client system. For the moment
                // commented out, it will hopefully be supported later.
                //appendKeyStore(tlsClientParameters, "truststore.jks", "myPassword");

                // Enable the SSL protocol.
                tlsClientParameters.setSecureSocketProtocol("SSL");

                // Finally, set the Cipher Filters which we support.
                tlsClientParameters.setCipherSuitesFilter(prepareFilters());
            }
        } finally {
            sslLock.unlock();
        }
    }

    ///**
    // * Prepares and appends the KeyStore for SSL based Authentication. Only
    // * required, if a Client Certificate is stored in a Local KeyStore which is
    // * needed to authenticate with. Although this is not needed until later,
    // * we're providing a sample for how to do it here.
    // *
    // * @param parameters TLS Client Parameters to append the Truststore to
    // * @param truststore The Trustore (file with extension .jks) to use
    // * @param password   Password for the Truststore
    // */
    //private static void appendKeyStore(final TLSClientParameters parameters, final String truststore, final String password) {
    //    try {
    //        // First, we need to prepate the KeyStore instance, which we can
    //        // load the Truststore into
    //        final KeyStore keyStore = KeyStore.getInstance("JKS");
    //
    //        // Prepare loading the Truststore from the given Filename into our
    //        // KeyStore. The Truststore requires the given password
    //        final File truststoreContainer = new File(truststore);
    //        keyStore.load(new FileInputStream(truststoreContainer), password.toCharArray());
    //
    //        // Now we can initialize a Trust Manager Factory with the KeyStore
    //        // and add this to the TLS Client Parameters
    //        final TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    //        trustFactory.init(keyStore);
    //        parameters.setTrustManagers(trustFactory.getTrustManagers());
    //
    //        // Finally, initialize the Key Manager Factory with the KeyStore and
    //        // add this to the TLS Client Parameters. This will authenticate the
    //        // local SSLSocket to ist peer.
    //        KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    //        keyFactory.init(keyStore, password.toCharArray());
    //        parameters.setKeyManagers(keyFactory.getKeyManagers());
    //    } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException e) {
    //        throw new WebServiceException(e);
    //    }
    //}

    /**
     * Set all the needed include & exclude cipher filters.
     *
     * @return Filters
     */
    private static FiltersType prepareFilters() {
        final FiltersType filter = new FiltersType();

        filter.getInclude().add(".*_EXPORT_.*");
        filter.getInclude().add(".*_EXPORT1024_.*");
        filter.getInclude().add(".*_WITH_DES_.*");
        filter.getInclude().add(".*_WITH_NULL_.*");
        filter.getExclude().add(".*_DH_anon_.*");

        return filter;
    }
}
