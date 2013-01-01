/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.jpa.StudentJpaDao;
import net.iaeste.iws.persistence.notification.Notifications;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.OfferJpaDao;

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
public final class ServiceFactory {

    // Note, for now the Constructor sets the EntityManager, it is a long-term
    // requirement, that we instead should have setters for the DAO's.
    private final EntityManager entityManager;
    private final Notifications notifications;
    private final AccessDao accessDao;

    public ServiceFactory(final EntityManager entityManager, final Notifications notifications) {
        this.entityManager = entityManager;
        this.notifications = notifications;

        accessDao = new AccessJpaDao(entityManager);
    }

    public AdministrationService prepareAdministrationService() {
        return new AdministrationService(accessDao, notifications);
    }

    public AccessService prepareAuthenticationService() {
        return new AccessService(accessDao);
    }

    public FacultyService prepareFacultyService() {
        return new FacultyService(entityManager);
    }

    public ExchangeService prepareOfferService() {
        final OfferDao dao = new OfferJpaDao(entityManager);
        return new ExchangeService(dao, notifications);
    }

    public StudentService prepareStudentService() {
        final StudentDao dao = new StudentJpaDao(entityManager);
        return new StudentService(dao);
    }

    public AccessDao getAccessDao() {
        return accessDao;
    }

    public CommitteeService prepareCommitteeService() {
        return new CommitteeService();
    }
}
