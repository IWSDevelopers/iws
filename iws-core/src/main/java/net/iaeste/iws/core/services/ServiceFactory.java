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

import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.CountryDao;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.CountryJpaDao;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import net.iaeste.iws.persistence.jpa.StudentJpaDao;

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
    private final CountryDao countryDao;
    private final AccessDao accessDao;

    public ServiceFactory(final EntityManager entityManager, final Notifications notifications) {
        this.entityManager = entityManager;
        this.notifications = notifications;

        accessDao = new AccessJpaDao(entityManager);
        countryDao = new CountryJpaDao(entityManager);
    }

    public AccountService prepareAccountService() {
        return new AccountService(accessDao, notifications);
    }

    public GroupService prepareGroupService() {
        return new GroupService(accessDao, notifications);
    }

    public CommitteeService prepareCommitteeService() {
        return new CommitteeService();
    }

    public CountryService prepareCountryService() {
        return new CountryService(countryDao);
    }

    public AccessService prepareAuthenticationService() {
        return new AccessService(accessDao, notifications);
    }

    public ExchangeService prepareExchangeService() {
        final ExchangeDao dao = new ExchangeJpaDao(entityManager);

        return new ExchangeService(dao, notifications);
    }

    public ExchangeFetchService prepareExchangeFetchService() {
        final ExchangeDao dao = new ExchangeJpaDao(entityManager);
        return new ExchangeFetchService(dao);
    }

    public StudentService prepareStudentService() {
        final StudentDao dao = new StudentJpaDao(entityManager);
        return new StudentService(dao);
    }

    public AccessDao getAccessDao() {
        return accessDao;
    }
}
