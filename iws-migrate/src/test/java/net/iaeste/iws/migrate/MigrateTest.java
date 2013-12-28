/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.MigrateTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.migrate.daos.IW3Dao;
import net.iaeste.iws.migrate.daos.IW3JpaDao;
import net.iaeste.iws.migrate.entities.IW3CountriesEntity;
import net.iaeste.iws.migrate.entities.IW3GroupsEntity;
import net.iaeste.iws.migrate.entities.IW3OffersEntity;
import net.iaeste.iws.migrate.entities.IW3ProfilesEntity;
import net.iaeste.iws.migrate.entities.IW3User2GroupEntity;
import net.iaeste.iws.migrate.migrators.CountryMigrator;
import net.iaeste.iws.migrate.migrators.GroupMigrator;
import net.iaeste.iws.migrate.migrators.MigrationResult;
import net.iaeste.iws.migrate.migrators.OfferMigrator;
import net.iaeste.iws.migrate.migrators.UserGroupMigrator;
import net.iaeste.iws.migrate.migrators.UserMigrator;
import net.iaeste.iws.migrate.spring.Config;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { Config.class })
@TransactionConfiguration(defaultRollback = false)
public class MigrateTest {

    private static final Logger log = LoggerFactory.getLogger(MigrateTest.class);

    @PersistenceContext(unitName = "IW3PersistenceUnit")
    private EntityManager iw3EntityManager;

    @PersistenceContext(unitName = "IWSPersistenceUnit")
    private EntityManager iwsEntityManager;

    private ExchangeDao exchangeDao = null;
    private AccessDao accessDao = null;
    private IW3Dao iw3Dao = null;

    @Before
    public void before() {
        exchangeDao = new ExchangeJpaDao(iwsEntityManager);
        accessDao = new AccessJpaDao(iwsEntityManager);
        iw3Dao = new IW3JpaDao(iw3EntityManager);
    }

    // =========================================================================
    // Migration is done using the following test methods, in order
    // =========================================================================

    @Test
    @Transactional("transactionManagerIWS")
    public void test1ReadingWritingCountries() {
        final CountryMigrator migrator = new CountryMigrator(accessDao);
        final List<IW3CountriesEntity> countries = iw3Dao.findAllCountries();
        log.info("Found {} Countries to migrate.", countries.size());

        final MigrationResult result = migrator.migrate(countries);
        final int persisted = result.getPersisted();
        final int skipped = result.getSkipped();

        // We should have all minus the invalid Chile & Training Session Country
        assertThat(persisted + skipped, is(countries.size()));
        log.info("Completed Migratring Countries; Persisted {} & Skipped {}.", persisted, skipped);
    }

    @Test
    @Transactional("transactionManagerIWS")
    public void test2ReadingWritingGroups() {
        final GroupMigrator migrator = new GroupMigrator(accessDao);
        final List<IW3GroupsEntity> groups = iw3Dao.findAllGroups();
        log.info("Found {} Groups to migrate.", groups.size());

        final MigrationResult result = migrator.migrate(groups);
        final int persisted = result.getPersisted();
        final int skipped = result.getSkipped();

        assertThat(persisted + skipped, is(groups.size()));
        log.info("Completed Migrating Groups; Persisted {} & Skipped {}.", persisted, skipped);
    }

    @Test
    @Transactional("transactionManagerIWS")
    public void test3ReadingWritingUsers() {
        final UserMigrator migrator = new UserMigrator(accessDao);
        final List<IW3ProfilesEntity> profiles = iw3Dao.findAllProfiles();
        log.info("Found {} Users to migrate.", profiles.size());

        final MigrationResult result = migrator.migrate(profiles);
        final int persisted = result.getPersisted();

        assertThat(persisted, is(profiles.size()));
        log.info("Completed Migrating Users; Persisted {}.", persisted);
    }

    @Test
    @Transactional("transactionManagerIWS")
    public void test4ReadingWritingUserGroups() {
        final UserGroupMigrator migrator = new UserGroupMigrator(accessDao);
        final List<IW3User2GroupEntity> userGroups = iw3Dao.findAllUserGroups();
        log.info("Found {} UserGroups to migrate.", userGroups.size());

        final MigrationResult result = migrator.migrate(userGroups);
        final int persisted = result.getPersisted();
        final int skipped = result.getSkipped();
        final int updated = result.getUpdated();

        assertThat(persisted + skipped + updated, is(userGroups.size()));
        log.info("Completed Migrating UserGroups; Persisted {}, Updated {} & Skipped {}.", persisted, updated, skipped);
    }

    @Test
    @Transactional("transactionManagerIWS")
    public void test5ReadingWritingOffers() {
        final OfferMigrator migrator = new OfferMigrator(accessDao, exchangeDao, iwsEntityManager);
        final List<IW3OffersEntity> offers = iw3Dao.findAllOffers(0, 100000);
        log.info("Found {} Offers to migrate.", offers.size());

        final MigrationResult result = migrator.migrate(offers);
        final int persisted = result.getPersisted();
        final int skipped = result.getSkipped();

        assertThat(persisted + skipped, is(offers.size()));
        log.info("Completed Migrating UserGroups; Persisted {} & Skipped {}.", persisted, skipped);
    }
}