/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.OfferGroupEntityTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Contains tests for OfferEntity and ExchangeJpaDao
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { SpringConfig.class })
public class OfferGroupEntityTest {

    private static final String REF_NO = "AT-2012-1234-AB";
    private static final String EMPLOYER_NAME = "Test_Employer_1";
    private static final String WORK_DESCRIPTION = "nothing";
    private static final Integer MAXIMUM_WEEKS = 12;
    private static final Integer MINIMUM_WEEKS = 12;
    private static final Float WEEKLY_HOURS = 40.0f;
    private static final Date FROM_DATE = new Date();
    private static final Date TO_DATE = new Date(FROM_DATE.getTime() + 3600 * 24 * 90);
    private static final String FIELDS_OF_STUDY = String.format("%s|%s", FieldOfStudy.IT, FieldOfStudy.CHEMISTRY);
    private static final String STUDY_LEVELS = String.format("%s|%s", StudyLevel.E, StudyLevel.M);
    private static final String GROUP_EXTERNAL_ID = "adc8dfd4-bc3a-4b27-897b-87d3950db503";
    private static final String GROUP_EXTERNAL_ID_2 = "3adbeb2b-05c0-456e-8809-1d1e4743f2c1";
    private static final String GROUP_NAME = "Croatia";

    @PersistenceContext
    private EntityManager entityManager;

    private ExchangeDao offerDao = null;

    private OfferEntity offer = null;
    private Authentication authentication = null;

    @Before
    public void before() {
        offerDao = new ExchangeJpaDao(entityManager);
        final AccessDao accessDao = new AccessJpaDao(entityManager);

        offer = getMinimalOffer();
        final AuthenticationToken token = new AuthenticationToken();
        final UserEntity user = accessDao.findActiveUserByUsername("austria@iaeste.at");
        final GroupEntity group = accessDao.findNationalGroup(user);
        offer.setGroup(group);
        authentication = new Authentication(token, user, group, UUID.randomUUID().toString());
    }

    @After
    public void cleanUp() {
    }

    @Test
    public void testDefaultOfferGroupStatus() {
        final OfferGroupEntity offerGroupEntity = new OfferGroupEntity();

        assertThat(offerGroupEntity.getStatus(), is(OfferState.NEW));
    }

    @Test
    @Transactional
    @Ignore("Ignored 2013-10-15 by Kim - Reason: The test is using hardcoded Id's, which is bad.")
    public void testFindGroupsForSharedOffer() {
        assertThat(offerDao.findAllOffers(authentication).size(), is(0));
        offerDao.persist(authentication, offer);

        assertThat(offerDao.findAllOffers(authentication).size(), is(1));
        final List<String> externalIds = new ArrayList(1);
        externalIds.add(GROUP_EXTERNAL_ID);
        externalIds.add(GROUP_EXTERNAL_ID_2);

        assertThat(offerDao.findInfoForSharedOffer(offer.getId()).size(), is(0));
        assertThat(offerDao.findInfoForSharedOffer(offer.getExternalId()).size(), is(0));

        final List<GroupEntity> groups = offerDao.findGroupByExternalIds(externalIds);
        assertThat(groups.size(), is(2));

        offerDao.persist(new OfferGroupEntity(offer, groups.get(0)));
        offerDao.persist(new OfferGroupEntity(offer, groups.get(1)));

        assertThat(offerDao.findInfoForSharedOffer(offer.getId()).size(), is(2));
        assertThat(offerDao.findInfoForSharedOffer(offer.getExternalId()).size(), is(2));

        offerDao.unshareFromAllGroups(offer.getId());
        assertThat(offerDao.findInfoForSharedOffer(offer.getId()).size(), is(0));

        offerDao.persist(new OfferGroupEntity(offer, groups.get(0)));
        offerDao.persist(new OfferGroupEntity(offer, groups.get(1)));
        assertThat(offerDao.findInfoForSharedOffer(offer.getId()).size(), is(2));

        final List<Long> groupIdsToUnshare = new ArrayList<>(1);
        groupIdsToUnshare.add(groups.get(0).getId());
        offerDao.unshareFromGroups(offer.getId(), groupIdsToUnshare);
        assertThat(offerDao.findInfoForSharedOffer(offer.getId()).size(), is(1));
    }

    private static OfferEntity getMinimalOffer() {
        final EmployerEntity employer = new EmployerEntity();
        employer.setName(EMPLOYER_NAME);
        employer.setWeeklyHours(WEEKLY_HOURS);

        final OfferEntity offer = new OfferEntity();
        offer.setRefNo(REF_NO);
        offer.setEmployer(employer);
        offer.setStudyLevels(STUDY_LEVELS);
        offer.setFieldOfStudies(FIELDS_OF_STUDY);
        offer.setLanguage1(Language.ENGLISH);
        offer.setLanguage1Level(LanguageLevel.E);
        offer.setWorkDescription(WORK_DESCRIPTION);
        offer.setMaximumWeeks(MAXIMUM_WEEKS);
        offer.setMinimumWeeks(MINIMUM_WEEKS);
        offer.setFromDate(FROM_DATE);
        offer.setToDate(TO_DATE);

        return offer;
    }
}
