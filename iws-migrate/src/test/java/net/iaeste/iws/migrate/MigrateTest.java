/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import net.iaeste.iws.migrate.migrators.MigrationResult;
import net.iaeste.iws.migrate.migrators.Migrator;
import net.iaeste.iws.migrate.spring.ContextProvider;
import org.joda.time.Period;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MigrateTest {

    private static final Logger log = LoggerFactory.getLogger(MigrateTest.class);
    private static final ContextProvider context = ContextProvider.getInstance();

    //private Migrator countryMigrator;
    //private Migrator groupMigrator;
    //private Migrator userMigrator;
    //private Migrator userGroupMigrator;
    private Migrator mailMigrator;
    //private Migrator offerMigrator;
    //private Migrator offerGroupMigrator;
    //private IW3Dao iw3Dao;

    private static Long start = null;

    @BeforeClass
    public static void beforeClass() {
        start = new Date().getTime();
    }

    @AfterClass
    public static void afterClass() {
        final Period duration = new Period(new Date().getTime() - start);
        log.info("Migration of the IW3 Database to IWS has been completed in {}:{}:{}.",
                duration.getHours(),
                duration.getMinutes(),
                duration.getSeconds());
    }

    @Before
    public void before() {
        //iw3Dao = context.getBean("iw3Dao");
        //countryMigrator = context.getBean("countryMigrator");
        //groupMigrator = context.getBean("groupMigrator");
        //userMigrator = context.getBean("userMigrator");
        //userGroupMigrator = context.getBean("userGroupMigrator");
        mailMigrator = context.getBean("mailMigrator");
        //offerMigrator = context.getBean("offerMigrator");
        //offerGroupMigrator = context.getBean("offerGroupMigrator");
    }

    // =========================================================================
    // Migration is done using the following test methods, in order
    // =========================================================================

    // Following is commented out, since the initial migration is over. The code
    // is only present to provide examples for future migrations, once other
    // features are mapped over
    //@Test
    //public void test1ReadingWritingCountries() {
    //    final List<IW3CountriesEntity> countries = iw3Dao.findAllCountries();
    //    log.info("Found {} Countries to migrate.", countries.size());
    //
    //    final MigrationResult result = countryMigrator.migrate(countries);
    //    final long persisted = result.getPersisted();
    //    final long skipped = result.getSkipped();
    //
    //    // We should have all minus the invalid Chile & Training Session Country
    //    assertThat((int) (persisted + skipped), is(countries.size()));
    //    log.info("Completed Migrating Countries; Persisted {} & Skipped {}.", persisted, skipped);
    //}

    // Following is commented out, since the initial migration is over. The code
    // is only present to provide examples for future migrations, once other
    // features are mapped over
    //@Test
    //public void test2ReadingWritingGroups() {
    //    final List<IW3GroupsEntity> groups = iw3Dao.findAllGroups();
    //    log.info("Found {} Groups to migrate.", groups.size());
    //
    //    final MigrationResult result = groupMigrator.migrate(groups);
    //    final long persisted = result.getPersisted();
    //    final long skipped = result.getSkipped();
    //
    //    assertThat((int) (persisted + skipped), is(groups.size()));
    //    log.info("Completed Migrating Groups; Persisted {} & Skipped {}.", persisted, skipped);
    //}

    // Following is commented out, since the initial migration is over. The code
    // is only present to provide examples for future migrations, once other
    // features are mapped over
    //@Test
    //public void test3ReadingWritingUsers() {
    //    final Long count = iw3Dao.countProfiles();
    //    final long blocks = (count / Migrator.BLOCK_SIZE) + 1;
    //    log.info("Found {} Users to migrate.", count);
    //    MigrationResult result = new MigrationResult();
    //
    //    for (int page = 0; page < blocks; page++) {
    //        log.debug("Migrating Users block {} of {}.", page + 1, blocks);
    //        final List<IW3ProfilesEntity> profiles = iw3Dao.findProfiles(page);
    //        result = result.merge(userMigrator.migrate(profiles));
    //    }
    //
    //    assertThat(result.getPersisted(), is(count));
    //    log.info("Completed Migrating Users; Persisted {}.", result.getPersisted());
    //}

    // Following is commented out, since the initial migration is over. The code
    // is only present to provide examples for future migrations, once other
    // features are mapped over
    //@Test
    //public void test4ReadingWritingUserGroups() {
    //    final Long count = iw3Dao.countUserGroups();
    //    final long blocks = (count / Migrator.BLOCK_SIZE) + 1;
    //    log.info("Found {} UserGroups to migrate.", count);
    //    MigrationResult result = new MigrationResult();
    //
    //    for (int page = 0; page < blocks; page ++) {
    //        log.debug("Migrating UserGroups block {} of {}.", page + 1, blocks);
    //        final List<IW3User2GroupEntity> userGroups = iw3Dao.findUserGroups(page);
    //        result = result.merge(userGroupMigrator.migrate(userGroups));
    //    }
    //
    //    assertThat(result.getPersisted() + result.getSkipped(), is(count));
    //    log.info("Completed Migrating UserGroups; Persisted {} & Skipped {}.", result.getPersisted(), result.getSkipped());
    //}

    @Test
    @Transactional("transactionManagerMail")
    public void test5ReadingWritingMail() {
        log.info("Starting migration of Mail settings.");

        final MigrationResult result = mailMigrator.migrate();
        final long persisted = result.getPersisted();

        assertThat(persisted > 0, is(true));
        log.info("Completed Migrating Mail.");
    }

    // Following is commented out, since the initial migration is over. The code
    // is only present to provide examples for future migrations, once other
    // features are mapped over
    //@Test
    //public void test6ReadingWritingOffers() {
    //    final Long count = iw3Dao.countOffers();
    //    final long blocks = (count / Migrator.BLOCK_SIZE) + 1;
    //    log.info("Found {} Offers to migrate.", count);
    //    MigrationResult result = new MigrationResult();
    //
    //    for (int page = 0; page < blocks; page++) {
    //        log.debug("Migrating Offers block {} of {}.", page + 1, blocks);
    //        final List<IW3OffersEntity> offers = iw3Dao.findOffers(page);
    //        result = result.merge(offerMigrator.migrate(offers));
    //    }
    //
    //    assertThat(result.getPersisted() + result.getSkipped(), is(count));
    //    log.info("Completed Migrating Offers; Persisted {} & Skipped {}.", result.getPersisted(), result.getSkipped());
    //}

    // Following is commented out, since the initial migration is over. The code
    // is only present to provide examples for future migrations, once other
    // features are mapped over
    //@Test
    //public void test7ReadingWritingOfferGroups() {
    //    final Long count = iw3Dao.countOfferGroups();
    //    final long blocks = (count / Migrator.BLOCK_SIZE) + 1;
    //    log.info("Found {} OfferGroups to migrate.", count);
    //    MigrationResult result = new MigrationResult();
    //
    //    for (int page = 0; page < blocks; page++) {
    //        log.debug("Migrating OfferGroups block {} of {}.", page + 1, blocks);
    //        final List<IW3Offer2GroupEntity> offerGroups = iw3Dao.findOfferGroups(page);
    //        result = result.merge(offerGroupMigrator.migrate(offerGroups));
    //    }
    //
    //    assertThat(result.getPersisted() + result.getSkipped(), is(count));
    //    log.info("Completed Migrating OfferGroups; Persisted {} & Skipped {}.", result.getPersisted(), result.getSkipped());
    //}
}
