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
import net.iaeste.iws.client.spring.EntityManagerProvider;
import net.iaeste.iws.client.spring.SpringAccessClient;
import net.iaeste.iws.client.spring.SpringAdministrationclient;
import net.iaeste.iws.client.spring.SpringExchangeClient;

import javax.persistence.EntityManager;

/**
 * The ConnectionFactory will use the provided Properties, to determine which
 * instance or implementation of IWS to use for external testing.<br />
 * Class is made package private, since it is only suppose to be used by the
 * actual Client Classes in this package.
 *
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
final class ConnectionFactory {

    private EntityManager em = EntityManagerProvider.getInstance();

    public Access getAccessImplementation() {
        return new SpringAccessClient(em);
    }

    public Administration getAdministrationImplementation() {
        return new SpringAdministrationclient(em);
    }

    public Exchange getExchangeImplementation() {
        return new SpringExchangeClient(em);
    }
}
