/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws) - net.iaeste.iws.ws.Producer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ws;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.Students;
import net.iaeste.iws.ejb.cdi.IWSBean;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;

/**
 * Producer for the IWS EJBs, required for injection of the Bean instances into
 * the IWS WebServices.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class Producer {

    @Produces @IWSBean
    @EJB(beanInterface = Access.class, lookup = "java:global/iws/iws-ejb/AccessBean!net.iaeste.iws.api.Access")
    private Access access = null;

    @Produces @IWSBean
    @EJB(beanInterface = Administration.class, lookup = "java:global/iws/iws-ejb/AdministrationBean!net.iaeste.iws.api.Administration")
    private Administration administration = null;

    @Produces @IWSBean
    @EJB(beanInterface = Committees.class, lookup = "java:global/iws/iws-ejb/CommitteeBean!net.iaeste.iws.api.Committees")
    private Committees committees = null;

    @Produces @IWSBean
    @EJB(beanInterface = Exchange.class, lookup = "java:global/iws/iws-ejb/ExchangeBean!net.iaeste.iws.api.Exchange")
    private Exchange exchange = null;

    @Produces @IWSBean
    @EJB(beanInterface = Storage.class, lookup = "java:global/iws/iws-ejb/StorageBean!net.iaeste.iws.api.Storage")
    private Storage storage = null;

    @Produces @IWSBean
    @EJB(beanInterface = Students.class, lookup = "java:global/iws/iws-ejb/StudentBean!net.iaeste.iws.api.Students")
    private Students students = null;
}
