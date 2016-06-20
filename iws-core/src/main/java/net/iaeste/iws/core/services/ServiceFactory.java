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
import net.iaeste.iws.persistence.StorageDao;
import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.AdminJpaDao;
import net.iaeste.iws.persistence.jpa.CommitteeJpaDao;
import net.iaeste.iws.persistence.jpa.CountryJpaDao;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import net.iaeste.iws.persistence.jpa.StorageJpaDao;
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
    // requirement, that we instead should have setters for the DAOs.
    private final EntityManager entityManager;
    private final AccessDao accessDao;
    private final CountryDao countryDao;
    private final ExchangeDao exchangeDao;
    private final StudentDao studentDao;
    private final Notifications notifications;
    private final Settings settings;

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager Instance to use for the DAOs
     * @param notifications Notification System
     * @param settings      IWS Settings
     */
    public ServiceFactory(final EntityManager entityManager, final Notifications notifications, final Settings settings) {
        this.entityManager = entityManager;
        this.notifications = notifications;
        this.settings = settings;

        accessDao = new AccessJpaDao(entityManager, settings);
        countryDao = new CountryJpaDao(entityManager, settings);
        exchangeDao = new ExchangeJpaDao(entityManager, settings);
        studentDao = new StudentJpaDao(entityManager, settings);
    }

    // =========================================================================
    // Service Handlers
    // =========================================================================

    /**
     * Prepares the Account Service.
     *
     * @return Prepared Account Service
     */
    public AccountService prepareAccountService() {
        return new AccountService(settings, accessDao, notifications);
    }

    /**
     * Prepares the Group Service.
     *
     * @return Prepared Group Service
     */
    public GroupService prepareGroupService() {
        return new GroupService(accessDao, notifications);
    }

    /**
     * Prepares the Committee Service.
     *
     * @return Prepared Committee Service
     */
    public CommitteeService prepareCommitteeService() {
        final CommitteeDao committeeDao = new CommitteeJpaDao(entityManager, settings);
        return new CommitteeService(settings, committeeDao, notifications);
    }

    /**
     * Prepares the Country Service.
     *
     * @return Prepared Country Service
     */
    public CountryService prepareCountryService() {
        return new CountryService(countryDao);
    }

    /**
     * Prepares the Storage Service.
     *
     * @return Prepared Storage Service
     */
    public StorageService prepareStorageService() {
        final StorageDao dao = new StorageJpaDao(entityManager, settings);
        return new StorageService(settings, dao);
    }

    /**
     * Prepares the Authentication Service.
     *
     * @return Prepared Authentication Service
     */
    public AccessService prepareAuthenticationService() {
        return new AccessService(settings, accessDao, notifications);
    }

    /**
     * Prepares the Exchange Service.
     *
     * @return Prepared Exchange Service
     */
    public ExchangeService prepareExchangeService() {
        final ExchangeDao dao = new ExchangeJpaDao(entityManager, settings);

        return new ExchangeService(settings, dao, accessDao, notifications);
    }

    /**
     * Prepares the Exchange Fetching Service.
     *
     * @return Prepared Exchange Fetching Service
     */
    public ExchangeFetchService prepareExchangeFetchService() {
        final ExchangeDao dao = new ExchangeJpaDao(entityManager, settings);
        final ViewsDao viewsDao = new ViewsJpaDao(entityManager, settings);

        return new ExchangeFetchService(settings, dao, viewsDao, accessDao);
    }

    /**
     * Prepares the Exchange CSV Service.
     *
     * @return Prepared Exchange CSV Service
     */
    public ExchangeCSVService prepareExchangeCSVService() {
        final ExchangeDao dao = new ExchangeJpaDao(entityManager, settings);

        return new ExchangeCSVService(settings, dao, accessDao);
    }

    /**
     * Prepares the Exchange CSV Fetching Service.
     *
     * @return Prepared Exchange CSV Fetching Service
     */
    public ExchangeCSVFetchService prepareExchangeCSVFetchService() {
        final ExchangeDao dao = new ExchangeJpaDao(entityManager, settings);
        final ViewsDao viewsDao = new ViewsJpaDao(entityManager, settings);

        return new ExchangeCSVFetchService(settings, dao, viewsDao);
    }

    /**
     * Prepares the Student Service.
     *
     * @return Prepared Student Service
     */
    public StudentService prepareStudentService() {
        final ViewsDao viewsDao = new ViewsJpaDao(entityManager, settings);

        return new StudentService(settings, accessDao, exchangeDao, studentDao, viewsDao);
    }

    /**
     * Prepares the Contacts Service.
     *
     * @return Prepared Contacts Service
     */
    public ContactsService prepareContacsService() {
        final AdminDao adminDao = new AdminJpaDao(entityManager, settings);

        return new ContactsService(adminDao);
    }

    public AccessDao getAccessDao() {
        return accessDao;
    }

    public ActiveSessions getActiveSessions() {
        return ActiveSessions.getInstance(settings);
    }
}
