/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.singletons.ActiveSessions;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.AdminDao;
import net.iaeste.iws.persistence.CountryDao;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.AdminJpaDao;
import net.iaeste.iws.persistence.jpa.CountryJpaDao;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import net.iaeste.iws.persistence.jpa.StudentJpaDao;
import net.iaeste.iws.persistence.jpa.ViewsJpaDao;

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
 * @since   IWS 1.0
 */
public final class ServiceFactory {

    // Note, for now the Constructor sets the EntityManager, it is a long-term
    // requirement, that we instead should have setters for the DAO's.
    private final EntityManager entityManager;
    private final Notifications notifications;
    private final CountryDao countryDao;
    private final AccessDao accessDao;
    private final ExchangeDao exchangeDao;
    private final StudentDao studentDao;
    private final Settings settings;

    public ServiceFactory(final EntityManager entityManager, final Notifications notifications, final Settings settings) {
        this.entityManager = entityManager;
        this.notifications = notifications;
        this.settings = settings;

        accessDao = new AccessJpaDao(entityManager);
        countryDao = new CountryJpaDao(entityManager);
        exchangeDao = new ExchangeJpaDao(entityManager);
        studentDao = new StudentJpaDao(entityManager);
    }

    // =========================================================================
    // Service Handlers
    // =========================================================================

    public AccountService prepareAccountService() {
        return new AccountService(settings, accessDao, notifications);
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

    public StorageService prepareStorageService() {
        return new StorageService(settings, accessDao);
    }

    public AccessService prepareAuthenticationService() {
        return new AccessService(settings, accessDao, notifications);
    }

    public ExchangeService prepareExchangeService() {
        final ExchangeDao dao = new ExchangeJpaDao(entityManager);

        return new ExchangeService(settings, dao, accessDao, studentDao, notifications);
    }

    public ExchangeFetchService prepareExchangeFetchService() {
        final ExchangeDao dao = new ExchangeJpaDao(entityManager);
        final ViewsDao viewsDao = new ViewsJpaDao(entityManager);

        return new ExchangeFetchService(settings, dao, viewsDao, accessDao);
    }

    public StudentService prepareStudentService() {
        final ViewsDao viewsDao = new ViewsJpaDao(entityManager);

        return new StudentService(settings, accessDao, exchangeDao, studentDao, viewsDao);
    }

    public ContactsService prepareContacsService() {
        final AdminDao adminDao = new AdminJpaDao(entityManager);

        return new ContactsService(adminDao);
    }

    public AccessDao getAccessDao() {
        return accessDao;
    }

    public ActiveSessions getActiveSessions() {
        return ActiveSessions.getInstance(settings);
    }
}
