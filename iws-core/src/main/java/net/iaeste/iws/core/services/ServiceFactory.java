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

import net.iaeste.iws.core.notofications.NotificationCenter;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.OfferJpaDao;
import net.iaeste.iws.persistence.jpa.UserNotificationJpaDao;

import javax.persistence.EntityManager;

/**
 * The ServiceFactory class is here to ensure that we follow the Law of Demeter
 * (Principle of Least Knowledge). Since the this class is injected into this
 * module as the external dependency, we hereby have a way of upholding
 * this.<br />
 *   The factory will ensure that each service is prepared with the required
 * dependencies, in a way to uphold this principle.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class ServiceFactory {

    private final EntityManager entityManager;
    private final AccessDao accessDao;
    private final NotificationCenter nc;

    public ServiceFactory(final EntityManager entityManager) {
        this.entityManager = entityManager;

        accessDao = new AccessJpaDao(entityManager);
        nc = new NotificationCenter(new UserNotificationJpaDao(entityManager));
    }

    public AdministrationService prepareAdministrationService() {
        return new AdministrationService(entityManager);
    }

    public AccessService prepareAuthenticationService() {
        final AccessDao dao = new AccessJpaDao(entityManager);

        return new AccessService(dao);
    }

    public FacultyService prepareFacultyService() {
        return new FacultyService(entityManager);
    }

    public ExchangeService prepareOfferService() {
        final OfferDao dao = new OfferJpaDao(entityManager);

        return new ExchangeService(dao, nc);
    }

    public StudentService prepareStudentService() {
        return new StudentService(entityManager);
    }

    public AccessDao getAccessDao() {
        return accessDao;
    }
}
