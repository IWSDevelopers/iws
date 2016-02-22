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
package net.iaeste.iws.core.services;

import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.monitors.ActiveSessions;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.AdminDao;
import net.iaeste.iws.persistence.CommitteeDao;
import net.iaeste.iws.persistence.CountryDao;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.AdminJpaDao;
import net.iaeste.iws.persistence.jpa.CommitteeJpaDao;
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
    private final AccessDao accessDao;
    private final CountryDao countryDao;
    private final ExchangeDao exchangeDao;
    private final StudentDao studentDao;
    private final Notifications notifications;
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
        final CommitteeDao committeeDao = new CommitteeJpaDao(entityManager);
        return new CommitteeService(settings, committeeDao, notifications);
    }

    public CountryService prepareCountryService() {
        return new CountryService(countryDao);
    }

    public StorageService prepareStorageService() {
        return new StorageService(settings, accessDao, entityManager);
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

    public ExchangeCSVService prepareExchangeCSVService() {
        final ExchangeDao dao = new ExchangeJpaDao(entityManager);
        final ViewsDao viewsDao = new ViewsJpaDao(entityManager);

        return new ExchangeCSVService(settings, dao, accessDao, viewsDao);
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
