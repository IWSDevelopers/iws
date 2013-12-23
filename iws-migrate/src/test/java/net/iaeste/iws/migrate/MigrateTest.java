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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.core.transformers.CommonTransformer;
import net.iaeste.iws.core.transformers.ExchangeTransformer;
import net.iaeste.iws.migrate.converters.CommonConverter;
import net.iaeste.iws.migrate.converters.CountryConverter;
import net.iaeste.iws.migrate.converters.GroupConverter;
import net.iaeste.iws.migrate.converters.OfferConverter;
import net.iaeste.iws.migrate.converters.UserConverter;
import net.iaeste.iws.migrate.daos.IW3Dao;
import net.iaeste.iws.migrate.daos.IW3JpaDao;
import net.iaeste.iws.migrate.entities.IW3CountriesEntity;
import net.iaeste.iws.migrate.entities.IW3GroupsEntity;
import net.iaeste.iws.migrate.entities.IW3OffersEntity;
import net.iaeste.iws.migrate.entities.IW3ProfilesEntity;
import net.iaeste.iws.migrate.entities.IW3User2GroupEntity;
import net.iaeste.iws.migrate.spring.Config;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
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
import java.util.Date;
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
        final CountryConverter converter = new CountryConverter();
        final List<IW3CountriesEntity> countries = iw3Dao.findAllCountries();
        log.info("Found {} Countries to migrate.", countries.size());
        int persisted = 0;
        int skipped = 0;

        for (final IW3CountriesEntity oldCountry : countries) {
            // Skip incorrect Chile Country record
            if (!"c".equals(oldCountry.getCountryid())) {
                final CountryEntity entity = converter.convert(oldCountry);
                try {
                    final Country country = CommonTransformer.transform(entity);
                    country.verify();
                    accessDao.persist(entity);
                    persisted++;
                } catch (IllegalArgumentException | VerificationException e) {
                    log.error("Cannot process Country id:{}, name = {} => {}", oldCountry.getCountryid(), oldCountry.getCountryname(), e.getMessage());
                    skipped++;
                }
            } else {
                skipped++;
            }
        }

        // We should have all minus the invalid Chile & Training Session Country
        assertThat(persisted + skipped, is(countries.size()));
        log.info("Completed Migratring Countries; Persisted {} & Skipped {}.", persisted, skipped);
    }

    @Test
    @Transactional("transactionManagerIWS")
    public void test2ReadingWritingGroups() {
        final GroupConverter converter = new GroupConverter();
        final List<IW3GroupsEntity> groups = iw3Dao.findAllGroups();
        log.info("Found {} Groups to migrate.", groups.size());
        int persisted = 0;
        int dropped = 0;

        for (final IW3GroupsEntity oldGroup : groups) {
            final GroupEntity converted = converter.convert(oldGroup);
            GroupEntity toPersist = null;

            // Handling standard Groups, these already exists, so we need to
            // properly map the old information into the new Groups - hence
            // we're dealing with them in a special way
            if (converted.getOldId() < 10) {
                final GroupEntity existing = accessDao.findGroupByIW3Id(converted.getOldId());
                if (existing != null) {
                    existing.merge(converted);
                    toPersist = existing;
                } else {
                    log.info("The standard group {} (id {}), is dropped.", converted.getGroupName(), converted.getOldId());
                    dropped++;
                }
            } else {
                final CountryEntity country = findExistingCountry(findCorrectCountry(oldGroup.getCountryid(), oldGroup.getRealcountryid()));
                final GroupEntity parent = accessDao.findGroupByIW3Id(oldGroup.getParentid());
                if (parent == null) {
                    // For Holland, we have the problem that Group 629 exists, but 628 (the parent) doesn't.
                    log.info("Couldn't find a parent for {} with id {}", oldGroup.getGroupname(), oldGroup.getGroupid());
                    converted.setParentId(0L);
                } else {
                    converted.setParentId(parent.getId());
                }
                final GroupTypeEntity groupType = accessDao.findGroupType(GroupConverter.convertGroupType(oldGroup.getGrouptype().getGrouptype()));
                converted.setCountry(country);
                converted.setGroupType(groupType);
                toPersist = converted;
            }

            // Done with the preparations, now we're going to verify the entity
            // and then persist it
            if (toPersist != null) {
                Group group = null;
                try {
                    group = CommonTransformer.transform(toPersist);
                    group.verify();
                    accessDao.persist(toPersist);

                    persisted++;
                } catch (IllegalArgumentException | VerificationException e) {
                    log.error("Cannot process Group {} => {}", group, e.getMessage());
                } catch (RuntimeException e) {
                    log.error("Unknown problem while migrating Group {} => {}", group, e.getMessage());
                }
            }
        }

        assertThat(persisted + dropped, is(groups.size()));
        log.info("Completed Migrating Groups; Persisted {} & Dropped {}.", persisted, dropped);
    }

    @Test
    @Transactional("transactionManagerIWS")
    public void test3ReadingWritingUsers() {
        final UserConverter converter = new UserConverter();
        final List<IW3ProfilesEntity> profiles = iw3Dao.findAllProfiles();
        log.info("Found {} Users to migrate.", profiles.size());
        int persisted = 0;

        for (final IW3ProfilesEntity profile : profiles) {
            final CountryEntity country = findExistingCountry(profile.getUser().getCountry());
            final CountryEntity nationality = findExistingCountry(profile.getUser().getNationality());
            final GroupTypeEntity groupType = accessDao.findGroupType(GroupType.PRIVATE);
            final RoleEntity role = accessDao.findRoleById(IWSConstants.ROLE_OWNER);

            final UserEntity userEntity = converter.convert(profile);
            userEntity.getPerson().setNationality(nationality);
            userEntity.getPerson().getAddress().setCountry(country);
            final UserGroupEntity entity = converter.preparePrivateGroup(userEntity, groupType, role);

            // Done with preparations, let's verify and persists the User
            User user = null;
            try {
                user = AdministrationTransformer.transform(userEntity);
                user.verify();
                accessDao.persist(userEntity.getPerson().getAddress());
                accessDao.persist(userEntity.getPerson());
                accessDao.persist(userEntity);
                accessDao.persist(entity.getGroup());
                accessDao.persist(entity);
                persisted++;
            } catch (IllegalArgumentException | VerificationException e) {
                log.error("Cannot process User {} => {}", user, e.getMessage());
            } catch (RuntimeException e) {
                log.error("Unknown problem while migrating User {} => {}", user, e.getMessage());
            }
        }

        assertThat(persisted, is(profiles.size()));
        log.info("Completed Migrating Users; Persisted {}.", persisted);
    }

    @Test
    @Transactional("transactionManagerIWS")
    public void test4ReadingWritingUserGroups() {
        final List<IW3User2GroupEntity> userGroups = iw3Dao.findAllUserGroups();
        log.info("Found {} UserGroups to migrate.", userGroups.size());
        int persisted = 0;
        int skipped = 0;
        int updated = 0;

        for (final IW3User2GroupEntity oldUserGroupEntity : userGroups) {
            final UserGroupEntity userGroup = accessDao.findIw3UserGroup(oldUserGroupEntity.getUser().getUserid(), oldUserGroupEntity.getGroup().getGroupid());
            UserGroupEntity toPersist = null;

            if (userGroup == null) {
                final UserEntity user = accessDao.findUserByIW3Id(oldUserGroupEntity.getUser().getUserid());
                final GroupEntity group = accessDao.findGroupByIW3Id(oldUserGroupEntity.getGroup().getGroupid());
                final RoleEntity role = accessDao.findRoleById(0L + oldUserGroupEntity.getRole().getRoleid());

                final UserGroupEntity entity = new UserGroupEntity(user, group, role);
                entity.setTitle(CommonConverter.convert(oldUserGroupEntity.getUsertitle()));
                entity.setOnPublicList(oldUserGroupEntity.getOnmailinglist());
                entity.setOnPrivateList(oldUserGroupEntity.getOnmailinglist());
                entity.setModified(CommonConverter.convert(oldUserGroupEntity.getModified()));
                entity.setCreated(CommonConverter.convert(oldUserGroupEntity.getCreated(), oldUserGroupEntity.getModified()));

                if (user != null) {
                    toPersist = entity;
                    persisted++;
                } else {
                    skipped++;
                    log.info("Skipping UserGroup Entity where the user no longer exists (userid={}).", oldUserGroupEntity.getUser().getUserid());
                }
            } else {
                log.info("Duplicate UserGroup Entity found, ");
                final Date modified = oldUserGroupEntity.getModified();

                if ((modified != null) && modified.after(userGroup.getModified())) {
                    log.info("changes are more recent than existing record - Merging.");
                    userGroup.setTitle(oldUserGroupEntity.getUsertitle());
                    userGroup.setOnPublicList(oldUserGroupEntity.getOnmailinglist());
                    userGroup.setOnPrivateList(oldUserGroupEntity.getOnmailinglist());
                    userGroup.setModified(CommonConverter.convert(oldUserGroupEntity.getModified()));
                    toPersist = userGroup;
                    updated++;
                } else {
                    skipped++;
                    log.info("Changes are older than the existing record - Skipping.");
                }
            }

            if (toPersist != null) {
                UserGroup dto = null;
                try {
                    dto = AdministrationTransformer.transform(toPersist);
                    dto.verify();
                    accessDao.persist(toPersist);
                } catch (IllegalArgumentException | VerificationException e) {
                    log.error("Cannot process UserGroup {} => {}", dto, e.getMessage());
                } catch (RuntimeException e) {
                    log.error("Unknown problem while migrating UserGroup {} => {}", dto, e.getMessage());
                }
            }
        }

        assertThat(persisted + updated + skipped, is(userGroups.size()));
        log.info("Completed Migrating UserGroups; Persisted {}, Updated {} & Skipped {}.", persisted, updated, skipped);
    }

    @Test
    @Transactional("transactionManagerIWS")
    public void test5ReadingWritingOffers() {
        final OfferConverter converter = new OfferConverter(iwsEntityManager);
        final List<IW3OffersEntity> offers = iw3Dao.findAllOffers(0, 1000);
        log.info("Found {} Offers to migrate.", offers.size());
        int persisted = 0;
        int skipped = 0;

        for (final IW3OffersEntity oldEntity : offers) {
            final OfferEntity offerEntity = converter.convert(oldEntity);
            final GroupEntity groupEntity = accessDao.findGroupByIW3Id(oldEntity.getGroupid());
            EmployerEntity employerEntity = exchangeDao.findUniqueEmployer(groupEntity, offerEntity.getEmployer());

            try {
                if (employerEntity == null) {
                    employerEntity = offerEntity.getEmployer();
                    final CountryEntity countryEntity = accessDao.findCountryByCode(oldEntity.getCountryid());
                    employerEntity.getAddress().setCountry(countryEntity);
                    employerEntity.setGroup(groupEntity);
                    accessDao.persist(employerEntity.getAddress());
                    accessDao.persist(employerEntity);
                }
                offerEntity.setEmployer(employerEntity);
                final Offer offer = ExchangeTransformer.transform(offerEntity);
                offer.verify();
                accessDao.persist(offerEntity);
                persisted++;
            } catch (IllegalArgumentException | VerificationException e) {
                log.error("Cannot process Offer with refno:{} => {}", offerEntity.getRefNo(), e.getMessage());
                skipped++;
            }
        }

        assertThat(persisted + + skipped, is(offers.size()));
        log.info("Completed Migrating UserGroups; Persisted {} & Skipped {}.", persisted, skipped);
    }

    // =========================================================================
    // Internal Helper Methods
    // =========================================================================

    private CountryEntity findExistingCountry(final String countrycode) {
        CountryEntity entity = null;

        try {
            if ((countrycode != null) && (countrycode.length() == 2) && !"$$".equals(countrycode)) {
                entity = accessDao.findCountry(countrycode.toUpperCase(IWSConstants.DEFAULT_LOCALE));
            }
        } catch (IWSException e) {
            System.out.println("Couldn't find Entity for country " + countrycode);
            entity = null;
        }

        return entity;
    }

    private static String findCorrectCountry(final String countryid, final String realcountryid) {
        final String result;

        if (countryid.equals(realcountryid)) {
            result = countryid;
        } else if (!"$$".equals(realcountryid)) {
            result = realcountryid;
        } else {
            result = countryid;
        }

        return result;
    }
}
