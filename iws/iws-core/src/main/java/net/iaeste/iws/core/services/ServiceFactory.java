/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.ServiceFactory
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import javax.persistence.EntityManager;

/**
 * Service Factory, to prepare the different Service instances, before being
 * used. Since the individual services may have different pre-requisites, then
 * the purpose of this class is to abstract the usage of the individual
 * services.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class ServiceFactory {

    private final EntityManager entityManager;

    public ServiceFactory(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public AdministrationService prepareAdministrationService() {
        return new AdministrationService(entityManager);
    }

    public AccessService prepareAuthenticationService() {
        return new AccessService(entityManager);
    }

    public FacultyService prepareFacultyService() {
        return new FacultyService(entityManager);
    }

    public ExchangeService prepareOfferService() {
        return new ExchangeService(entityManager);
    }

    public StudentService prepareStudentService() {
        return new StudentService(entityManager);
    }
}
