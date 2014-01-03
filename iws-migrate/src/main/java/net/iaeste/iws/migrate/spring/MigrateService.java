/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.spring.MigrateService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.spring;

import net.iaeste.iws.migrate.daos.IW3Dao;
import net.iaeste.iws.migrate.daos.IW3JpaDao;
import net.iaeste.iws.migrate.entities.IW3CountriesEntity;
import net.iaeste.iws.migrate.entities.IW3GroupsEntity;
import net.iaeste.iws.migrate.entities.IW3Offer2GroupEntity;
import net.iaeste.iws.migrate.entities.IW3OffersEntity;
import net.iaeste.iws.migrate.entities.IW3ProfilesEntity;
import net.iaeste.iws.migrate.entities.IW3User2GroupEntity;
import net.iaeste.iws.migrate.migrators.CountryMigrator;
import net.iaeste.iws.migrate.migrators.GroupMigrator;
import net.iaeste.iws.migrate.migrators.MigrationResult;
import net.iaeste.iws.migrate.migrators.OfferGroupMigrator;
import net.iaeste.iws.migrate.migrators.OfferMigrator;
import net.iaeste.iws.migrate.migrators.UserGroupMigrator;
import net.iaeste.iws.migrate.migrators.UserMigrator;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.MailingListDao;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import net.iaeste.iws.persistence.jpa.MailingListJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
//@Transactional("transactionManagerIWS")
//@Repository("migrateService")
public class MigrateService implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(MigrateService.class);

    @PersistenceContext(unitName = "IW3PersistenceUnit")
    private EntityManager iw3EntityManager;

    @PersistenceContext(unitName = "IWSPersistenceUnit")
    private EntityManager iwsEntityManager;

    @PersistenceContext(unitName = "MailPersistenceUnit")
    private EntityManager mailEntityManager;

    private MailingListDao mailingListDao = null;
    private ExchangeDao exchangeDao = null;
    private AccessDao accessDao = null;
    private IW3Dao iw3Dao = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        mailingListDao = new MailingListJpaDao(mailEntityManager);
        exchangeDao = new ExchangeJpaDao(iwsEntityManager);
        accessDao = new AccessJpaDao(iwsEntityManager);
        iw3Dao = new IW3JpaDao(iw3EntityManager);
    }

    // =========================================================================
    // Actual Migrators
    // =========================================================================

    /**
     * Countries Migration Service.
     */
    @Transactional("transactionManagerIWS")
    public void migrateCountries() {
        // Our Migrator
        final CountryMigrator migrator = new CountryMigrator(accessDao);

        // Fetch the List of Country Entities from IW3 to migrate
        final List<IW3CountriesEntity> countries = iw3Dao.findAllCountries();
        log.info("Found {} Countries to migrate.", countries.size());

        // Now, run the migration
        final MigrationResult result = migrator.migrate(countries);
        final int persisted = result.getPersisted();
        final int skipped = result.getSkipped();

        // And log the result
        log.info("Completed Migratring Countries; Persisted {} & Skipped {}.", persisted, skipped);
    }

    @Transactional("transactionManagerIWS")
    public void migrateGroups() {
        // Our Migrator
        final GroupMigrator migrator = new GroupMigrator(accessDao, mailingListDao);

        // Fetch the List of Group Entities from IW3 to migrate
        final List<IW3GroupsEntity> groups = iw3Dao.findAllGroups();
        log.info("Found {} Groups to migrate.", groups.size());

        // Now, run the migration
        final MigrationResult result = migrator.migrate(groups);
        final int persisted = result.getPersisted();
        final int skipped = result.getSkipped();

        // And log the result
        log.info("Completed Migrating Groups; Persisted {} & Skipped {}.", persisted, skipped);
    }

    @Transactional("transactionManagerIWS")
    public void migrateUsers() {
        // Our Migrator
        final UserMigrator migrator = new UserMigrator(accessDao, mailingListDao);

        // Fetch the List of Profile (User) Entities from IW3 to migrate
        final List<IW3ProfilesEntity> profiles = iw3Dao.findAllProfiles();
        log.info("Found {} Users to migrate.", profiles.size());

        // Now, run the migration
        final MigrationResult result = migrator.migrate(profiles);
        final int persisted = result.getPersisted();

        // And log the result
        log.info("Completed Migrating Users; Persisted {}.", persisted);
    }

    @Transactional("transactionManagerIWS")
    public void migrateUserGroups() {
        // Our Migrator
        final UserGroupMigrator migrator = new UserGroupMigrator(accessDao, mailingListDao);

        // Fetch the List of User2Group Entities from IW3 to migrate
        final List<IW3User2GroupEntity> userGroups = iw3Dao.findAllUserGroups();
        log.info("Found {} UserGroups to migrate.", userGroups.size());

        // Now, run the migration
        final MigrationResult result = migrator.migrate(userGroups);
        final int persisted = result.getPersisted();
        final int skipped = result.getSkipped();

        // And log the result
        log.info("Completed Migrating UserGroups; Persisted {} & Skipped {}.", persisted, skipped);
    }

    @Transactional("transactionManagerIWS")
    public void migrateOffers() {
        // Our Migrator
        final OfferMigrator migrator = new OfferMigrator(accessDao, exchangeDao, iwsEntityManager);

        // Fetch the List of Offer Entities from IW3 to migrate
        final List<IW3OffersEntity> offers = iw3Dao.findAllOffers(0, 100000);
        log.info("Found {} Offers to migrate.", offers.size());

        // Now, run the migration
        final MigrationResult result = migrator.migrate(offers);
        final int persisted = result.getPersisted();
        final int skipped = result.getSkipped();

        // And log the result
        log.info("Completed Migrating OfferGroups; Persisted {} & Skipped {}.", persisted, skipped);
    }

    @Transactional("transactionManagerIWS")
    public void migrateOfferGroups() {
        // Our Migrator
        final OfferGroupMigrator migrator = new OfferGroupMigrator(accessDao, exchangeDao);

        // Fetch the List of Offer Entities from IW3 to migrate
        final List<IW3Offer2GroupEntity> offerGroups = iw3Dao.findAllOfferGroups(0, 250000);
        log.info("Found {} OfferGroups to migrate.", offerGroups.size());

        // Now, run the migration
        final MigrationResult result = migrator.migrate(offerGroups);
        final int persisted = result.getPersisted();
        final int skipped = result.getSkipped();

        // And log the result
        log.info("Completed Migrating OfferGroups; Persisted {} & Skipped {}.", persisted, skipped);
    }
}
