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
    private Access access;

    @Produces @IWSBean
    @EJB(beanInterface = Administration.class, lookup = "java:global/iws/iws-ejb/AdministrationBean!net.iaeste.iws.api.Administration")
    private Administration administration;

    @Produces @IWSBean
    @EJB(beanInterface = Committees.class, lookup = "java:global/iws/iws-ejb/CommitteeBean!net.iaeste.iws.api.Committees")
    private Committees committees;

    @Produces @IWSBean
    @EJB(beanInterface = Exchange.class, lookup = "java:global/iws/iws-ejb/ExchangeBean!net.iaeste.iws.api.Exchange")
    private Exchange exchange;

    @Produces @IWSBean
    @EJB(beanInterface = Storage.class, lookup = "java:global/iws/iws-ejb/StorageBean!net.iaeste.iws.api.Storage")
    private Storage storage;

    @Produces @IWSBean
    @EJB(beanInterface = Students.class, lookup = "java:global/iws/iws-ejb/StudentBean!net.iaeste.iws.api.Students")
    private Students students;
}
