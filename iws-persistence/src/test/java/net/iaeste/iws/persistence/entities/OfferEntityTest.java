/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.OfferEntityTest
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
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.PaymentFrequency;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.jpa.OfferJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
import org.junit.After;
import org.junit.Assert;
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
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Contains tests for OfferEntity and OfferJpaDao
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { SpringConfig.class })
//@TransactionConfiguration(defaultRollback = false)
public class OfferEntityTest {

    private static final String REF_NO = "AT-2012-1234-AB";
    private static final Date NOMINATION_DEADLINE = new Date();
    private static final String EMPLOYER_NAME = "Test_Employer_1";
    private static final String WORK_DESCRIPTION = "nothing";
    private static final Integer MAXIMUM_WEEKS = 12;
    private static final Integer MINIMUM_WEEKS = 12;
    private static final Float WEEKLY_HOURS = 40.0f;
    private static final Float DAILY_HOURS = 8.0f;
    private static final Date FROM_DATE = new Date();
    private static final Date TO_DATE = new Date(new Date().getTime() + 3600 * 24 * 90);
    private static final BigDecimal PAYMENT = new BigDecimal(3000);
    private static final BigDecimal LODGING_COST = new BigDecimal(1000);
    private static final BigDecimal LIVING_COST = new BigDecimal(2000);

    private OfferDao dao;
    @PersistenceContext
    private EntityManager entityManager;

    private OfferEntity offer;

    @Before
    public void before() {
        dao = new OfferJpaDao(entityManager);
        offer = getMinimalOffer();
    }

    private OfferEntity getMinimalOffer() {
        final OfferEntity offer = new OfferEntity();
        offer.setRefNo(REF_NO);
        offer.setEmployerName(EMPLOYER_NAME);
        final List<StudyLevel> studyLevels = new ArrayList<>();
        studyLevels.add(StudyLevel.E);
        offer.setStudyLevels(studyLevels);
        final List<FieldOfStudy> fieldOfStudies = new ArrayList<>();
        fieldOfStudies.add(FieldOfStudy.AERONAUTIC_ENGINEERING);
        offer.setFieldOfStudies(fieldOfStudies);
        offer.setGender(Gender.E);
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
    @Transactional //(noRollbackFor = Exception.class)
    public void testMinimalOffer() {
        dao.persist(offer);
        Assert.assertThat(offer.getId(), is(notNullValue()));

        offer = entityManager.find(OfferEntity.class, offer.getId());
        Assert.assertThat(offer.getRefNo(), is(REF_NO));
        Assert.assertThat(offer.getEmployerName(), is(EMPLOYER_NAME));
        Assert.assertThat(offer.getStudyLevels().size(), is(1));
        Assert.assertThat(offer.getStudyLevels().get(0), is(StudyLevel.E));
        Assert.assertThat(offer.getFieldOfStudies().size(), is(1));
        Assert.assertThat(offer.getFieldOfStudies().get(0), is(FieldOfStudy.AERONAUTIC_ENGINEERING));
        Assert.assertThat(offer.getGender(), is(Gender.E));
        Assert.assertThat(offer.getLanguage1(), is(Language.ENGLISH));
        Assert.assertThat(offer.getLanguage1Level(), is(LanguageLevel.E));
        Assert.assertThat(offer.getWorkDescription(), is(WORK_DESCRIPTION));
        Assert.assertThat(offer.getMaximumWeeks(), is(MAXIMUM_WEEKS));
        Assert.assertThat(offer.getMinimumWeeks(), is(MINIMUM_WEEKS));
        Assert.assertThat(offer.getWeeklyHours(), is(WEEKLY_HOURS));
        Assert.assertThat(offer.getFromDate(), is(FROM_DATE));
        Assert.assertThat(offer.getToDate(), is(TO_DATE));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testUniqueRefNo() {
        final String refNo = "CZ-2012-1001";
        offer.setRefNo(refNo);
        offer.setId(null);
        dao.persist(offer);
        Assert.assertThat(offer.getId(), is(notNullValue()));

        offer = getMinimalOffer();
        offer.setRefNo(refNo);
        offer.setId(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullRefNo() {
        offer.setRefNo(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testDuplicateRefNo() {
        dao.persist(offer);
        final OfferEntity o2 = getMinimalOffer();
        dao.persist(o2);
    }

    @Test
    @Transactional
    public void testNullNominationDeadline() {
        offer.setNominationDeadline(null);
        dao.persist(offer);

        Assert.assertThat(offer.getId(), is(notNullValue()));
        Assert.assertThat(dao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullEmployerName() {
        offer.setEmployerName(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Ignore("TODO does not work for @ElementCollection without a separate table")
    @Transactional
    public void testNullStudyLevel() {
        offer.setStudyLevels(null);
        dao.persist(offer);
    }


    @Test(expected = PersistenceException.class)
    @Ignore("TODO does not work for @ElementCollection without a separate table")
    @Transactional
    public void testEmptyStudyLevel() {
        offer.setStudyLevels(Collections.<StudyLevel>emptyList());
        dao.persist(offer);
    }


    @Test(expected = PersistenceException.class)
    @Ignore("TODO: at least one field of studies")
    @Transactional
    public void testNullFieldOfStudies() {
        offer.setFieldOfStudies(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Ignore("TODO: at least one field of studies")
    @Transactional
    public void testEmptyFieldOfStudies() {
        offer.setFieldOfStudies(Collections.<FieldOfStudy>emptyList());
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullGender() {
        offer.setGender(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullLang1() {
        offer.setLanguage1(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullLang1Level() {
        offer.setLanguage1Level(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullWorkDescription() {
        offer.setWorkDescription(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullMaxWeeks() {
        offer.setMaximumWeeks(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullMinWeeks() {
        offer.setMinimumWeeks(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullWeeklyHours() {
        offer.setWeeklyHours(null);
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testNullDailyHours() {
        offer.setDailyHours(null);
        dao.persist(offer);

        Assert.assertThat(offer.getId(), is(notNullValue()));
        final OfferEntity foundOffer = dao.findOffer(offer.getId());
        Assert.assertThat(foundOffer, is(offer));
    }

    @Test
    @Transactional
    public void testOtherRequirements() {
        final StringBuilder sb = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            sb.append('a');
        }

        offer.setOtherRequirements(sb.toString());
        dao.persist(offer);

        Assert.assertThat(offer.getId(), is(notNullValue()));
        Assert.assertThat(dao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testTooLongOtherRequirements() {
        final StringBuilder sb = new StringBuilder(501);
        for (int i = 0; i < 501; i++) {
            sb.append('a');
        }

        offer.setOtherRequirements(sb.toString());
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testWorkDescription() {
        final StringBuilder sb = new StringBuilder(1000);
        for (int i = 0; i < 1000; i++) {
            sb.append('a');
        }

        offer.setWorkDescription(sb.toString());
        dao.persist(offer);

        Assert.assertThat(offer.getId(), is(notNullValue()));
        Assert.assertThat(dao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testTooLongWorkDescription() {
        final StringBuilder sb = new StringBuilder(1001);
        for (int i = 0; i < 1001; i++) {
            sb.append('a');
        }

        offer.setWorkDescription(sb.toString());
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testWeeklyHoursPrecision() {
        offer.setWeeklyHours(0.999f);
        dao.persist(offer);
        offer = entityManager.find(OfferEntity.class, offer.getId());

        Assert.assertThat(offer.getWeeklyHours(), is(Float.valueOf("0.999")));
    }

    /* TODO for some reason the precision does not work with hsqldb
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testWeeklyHoursPrecision2() {
        offer.setWeeklyHours(10.9999f);
        dao.persist(offer);
        offer = entityManager.find(OfferEntity.class, offer.getId());
        Assert.assertEquals("10.9999", offer.getWeeklyHours().toString());
    }*/

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testWeeklyHoursPrecision3() {
        offer.setWeeklyHours(100.0f);
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testDailyHoursPrecision() {
        offer.setDailyHours(0.999f);
        dao.persist(offer);
        offer = entityManager.find(OfferEntity.class, offer.getId());
        Assert.assertThat(offer.getDailyHours(), is(Float.valueOf("0.999")));
    }

    /* TODO for some reason the precision does not work with hsqldb
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testDailyHoursPrecision2() {
        offer.setDailyHours(10.9999f);
        dao.persist(offer);
        offer = entityManager.find(OfferEntity.class, offer.getId());
        Assert.assertEquals("10.9999", offer.getDailyHours().toString());
    }*/

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testDailyHoursPrecision3() {
        offer.setDailyHours(100.0f);
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testPayment() {
        offer.setPayment(BigDecimal.valueOf(1234567890.12));
        offer.setPaymentFrequency(PaymentFrequency.M);
        dao.persist(offer);

        Assert.assertThat(offer.getId(), is(notNullValue()));
        Assert.assertThat(dao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testPayment2() {
        offer.setPayment(BigDecimal.valueOf(12345678901.0));
        dao.persist(offer);
    }

    /* TODO for some reason the precision does not work with hsqldb
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testPayment3() {
        offer.setPayment(BigDecimal.valueOf(1234567890.123));
        dao.persist(offer);
    }*/

    @Test
    @Transactional
    public void testDecuction() {
        offer.setDeduction(99);
        dao.persist(offer);

        Assert.assertThat(offer.getId(), is(notNullValue()));
        Assert.assertThat(dao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testDecuction2() {
        offer.setDeduction(100);
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testLodgingCost() {
        offer.setLodgingCost(BigDecimal.valueOf(1234567890.12));
        offer.setLodgingCostFrequency(PaymentFrequency.M);
        dao.persist(offer);

        Assert.assertThat(offer.getId(), is(notNullValue()));
        Assert.assertThat(dao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testLodgingCost2() {
        offer.setLodgingCost(BigDecimal.valueOf(12345678901.0));
        dao.persist(offer);
    }

    /* TODO for some reason the precision does not work with hsqldb
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testLodgingCost3() {
        offer.setLodgingCost(BigDecimal.valueOf(1234567890.123));
        dao.persist(offer);
    }*/

    @Test
    @Transactional
    public void testLivingCost() {
        offer.setLivingCost(BigDecimal.valueOf(1234567890.12));
        offer.setLivingCostFrequency(PaymentFrequency.M);
        dao.persist(offer);

        Assert.assertThat(offer.getId(), is(notNullValue()));
        Assert.assertThat(dao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testLivingCost2() {
        offer.setLivingCost(BigDecimal.valueOf(12345678901.0));
        dao.persist(offer);
    }

    /* TODO for some reason the precision does not work with hsqldb
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testLivingCost3() {
        offer.setLivingCost(BigDecimal.valueOf(1234567890.123));
        dao.persist(offer);
    }*/

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullFromDate() {
        offer.setFromDate(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullToDate() {
        offer.setToDate(null);
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testNullPaymentFrequency() {
        offer.setPayment(null);
        offer.setPaymentFrequency(null);
        dao.persist(offer);

        final OfferEntity persistedOffer = dao.findOffer(offer.getId());
        Assert.assertThat(persistedOffer, is(offer));
        Assert.assertThat(persistedOffer.getPayment(), is(nullValue()));
        Assert.assertThat(persistedOffer.getPaymentFrequency(), is(nullValue()));
    }

    @Transactional
    @Test(expected = PersistenceException.class)
    public void testNullPaymentFrequencyWhenPaymentNotNull() {
        offer.setPayment(PAYMENT);
        offer.setPaymentFrequency(null);
        dao.persist(offer);
    }

    @Transactional
    @Test(expected = PersistenceException.class)
    @Ignore("TODO: test is fine, fix the db schema")
    public void testNullPaymentFrequencyWhenPaymentNotNullOnUpdate() {
        dao.persist(offer);
        offer.setPayment(PAYMENT);
        offer.setPaymentFrequency(null);
        dao.persist(offer);
    }

    @Transactional
    @Test
    public void testNullLodgingCostFrequency() {
        offer.setLodgingCostFrequency(null);
        offer.setLodgingCost(null);
        dao.persist(offer);

        final OfferEntity persistedOffer = dao.findOffer(offer.getId());
        Assert.assertThat(persistedOffer, is(offer));
        Assert.assertThat(persistedOffer.getLodgingCostFrequency(), is(nullValue()));
        Assert.assertThat(persistedOffer.getLodgingCost(), is(nullValue()));
    }

    @Transactional
    @Test(expected = PersistenceException.class)
    public void testNullLodgingCostFrequencyWhenLodgingCostNotNull() {
        offer.setLodgingCostFrequency(null);
        offer.setLodgingCost(LODGING_COST);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    @Ignore("TODO: test is fine, fix the db schema")
    public void testNullLodgingCostFrequencyWhenLodgingCostNotNullOnUpdate() {
        dao.persist(offer);
        offer.setLodgingCostFrequency(null);
        offer.setLodgingCost(LODGING_COST);
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testNullLivingCostFrequency() {
        offer.setLivingCostFrequency(null);
        offer.setLivingCost(null);
        dao.persist(offer);

        final OfferEntity persistedOffer = dao.findOffer(offer.getId());
        Assert.assertThat(persistedOffer, is(offer));
        Assert.assertThat(persistedOffer.getLivingCostFrequency(), is(nullValue()));
        Assert.assertThat(persistedOffer.getLivingCost(), is(nullValue()));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullLivingCostFrequencyWhenLivingCostNotNull() {
        offer.setLivingCostFrequency(null);
        offer.setLivingCost(LIVING_COST);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    @Ignore("TODO: test is fine, fix the db schema")
    public void testNullLivingCostFrequencyWhenLivingCostNotNullOnUpdate() {
        dao.persist(offer);
        offer.setLivingCostFrequency(null);
        offer.setLivingCost(LIVING_COST);
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testFind() {
        Assert.assertThat(dao.findAll().size(), is(0));
        dao.persist(offer);
        final OfferEntity offerFoundByRefNo = dao.findOffer(offer.getRefNo());
        Assert.assertThat(offerFoundByRefNo, is(notNullValue()));
        Assert.assertThat(offerFoundByRefNo, is(offer));
        final OfferEntity offerFoundById = dao.findOffer(offer.getId());
        Assert.assertThat(offerFoundById, is(notNullValue()));
        Assert.assertThat(offerFoundById, is(offer));
    }

    @After
    public void cleanUp() {
//        transaction.commit();
    }
}
