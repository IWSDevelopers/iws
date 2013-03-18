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

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
        final UserEntity user = accessDao.findUserByUsername("austria");
        final GroupEntity group = accessDao.findNationalGroup(user);
        offer.setGroup(group);
        authentication = new Authentication(token, user, group);
    }

    private static OfferEntity getMinimalOffer() {
        final OfferEntity offer = new OfferEntity();
        //FIXME: auto UUID generation should be done in persist method
        offer.setExternalId(UUID.randomUUID().toString());
        offer.setRefNo(REF_NO);
        offer.setEmployerName(EMPLOYER_NAME);
        offer.setStudyLevels(STUDY_LEVELS);
        offer.setFieldOfStudies(FIELDS_OF_STUDY);
        offer.setLanguage1(Language.ENGLISH);
        offer.setLanguage1Level(LanguageLevel.E);
        offer.setWorkDescription(WORK_DESCRIPTION);
        offer.setMaximumWeeks(MAXIMUM_WEEKS);
        offer.setMinimumWeeks(MINIMUM_WEEKS);
        offer.setWeeklyHours(WEEKLY_HOURS);
        offer.setFromDate(FROM_DATE);
        offer.setToDate(TO_DATE);
        return offer;
    }

    @Test
    @Transactional
    public void testFindGroup() {
        final List<String> externalIds = new ArrayList(1);
        externalIds.add(GROUP_EXTERNAL_ID);
        final List<GroupEntity> groups = offerDao.findGroupByExternalIds(externalIds);
        assertThat(groups.size(), is(1));
        final GroupEntity group = groups.get(0);
        assertThat(group.getExternalId(), is(GROUP_EXTERNAL_ID));
        assertThat(group.getGroupName(), is(GROUP_NAME));

        assertThat(offerDao.findGroupByExternalId(GROUP_EXTERNAL_ID), is(group));
    }

    @Test
    @Transactional
    @Ignore("Ignored 2013-03-18 by Kim - Reason: The Offer logic has been extended with group checks.")
    public void testFindGroupsForSharedOffer() {
        assertThat(offerDao.findAllOffers(authentication).size(), is(0));
        offerDao.persist(authentication, offer);

        assertThat(offerDao.findAllOffers(authentication).size(), is(1));
        final List<String> externalIds = new ArrayList(1);
        externalIds.add(GROUP_EXTERNAL_ID);
        externalIds.add(GROUP_EXTERNAL_ID_2);

        assertThat(offerDao.findGroupsForSharedOffer(offer.getId()).size(), is(0));
        assertThat(offerDao.findGroupsForSharedOffer(offer.getExternalId()).size(), is(0));

        final List<GroupEntity> groups = offerDao.findGroupByExternalIds(externalIds);
        assertThat(groups.size(), is(2));

        OfferGroupEntity og = new OfferGroupEntity(offer, groups.get(0));
        OfferGroupEntity og2 = new OfferGroupEntity(offer, groups.get(1));
        offerDao.persist(og);
        offerDao.persist(og2);

        assertThat(offerDao.findGroupsForSharedOffer(offer.getId()).size(), is(2));
        assertThat(offerDao.findGroupsForSharedOffer(offer.getExternalId()).size(), is(2));

        offerDao.unshareFromAllGroups(offer.getId());
        assertThat(offerDao.findGroupsForSharedOffer(offer.getId()).size(), is(0));

        og = new OfferGroupEntity(offer, groups.get(0));
        og2 = new OfferGroupEntity(offer, groups.get(1));
        offerDao.persist(og);
        offerDao.persist(og2);
        assertThat(offerDao.findGroupsForSharedOffer(offer.getId()).size(), is(2));

        final List<Long> groupIdsToUnshare = new ArrayList<>(1);
        groupIdsToUnshare.add(groups.get(0).getId());
        offerDao.unshareFromGroups(offer.getId(), groupIdsToUnshare);
        assertThat(offerDao.findGroupsForSharedOffer(offer.getId()).size(), is(1));
    }

    @After
    public void cleanUp() {
    }
}
