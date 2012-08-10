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

import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.PaymentFrequency;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.jpa.OfferJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
import org.junit.Assert;
import org.junit.Before;
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
import java.util.Date;

/**
 * Contains tests for OfferEntity and OfferJpaDao
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { SpringConfig.class })
public class OfferEntityTest {

    private static final String REF_NO = "AT-2012-1234-AB";
    private static final Date NOMINATION_DEADLINE = new Date();
    private static final String EMPLOYER_NAME = "Test_Employer_1";
    private static final String WORK_DESCRIPTION = "nothing";
    private static final Integer MAXIMUM_WEEKS = 12;
    private static final Integer MINIMUM_WEEKS = 12;
    private static final Float WEEKLY_HOURS = 40f;
    private static final Float DAILY_HOURS = 8f;
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
        OfferEntity offer = new OfferEntity();
        offer.setRefNo(REF_NO);
        offer.setEmployerName(EMPLOYER_NAME);
        offer.getStudyLevels().add(StudyLevel.E);
        offer.getFieldOfStudies().add(FieldOfStudy.AERONAUTIC_ENGINEERING);
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
    @Transactional
    public void testMinimalOffer() {
        dao.persist(offer);
        Assert.assertNotNull(offer.getId());

        offer = entityManager.find(OfferEntity.class, offer.getId());
        Assert.assertEquals(REF_NO, offer.getRefNo());
        Assert.assertEquals(EMPLOYER_NAME, offer.getEmployerName());
        Assert.assertEquals(1, offer.getStudyLevels().size());
        Assert.assertEquals(StudyLevel.E, offer.getStudyLevels().get(0));
        Assert.assertEquals(1, offer.getFieldOfStudies().size());
        Assert.assertEquals(FieldOfStudy.AERONAUTIC_ENGINEERING, offer.getFieldOfStudies().get(0));
        Assert.assertEquals(Gender.E, offer.getGender());
        Assert.assertEquals(Language.ENGLISH, offer.getLanguage1());
        Assert.assertEquals(LanguageLevel.E, offer.getLanguage1Level());
        Assert.assertEquals(WORK_DESCRIPTION, offer.getWorkDescription());
        Assert.assertEquals(MAXIMUM_WEEKS, offer.getMaximumWeeks());
        Assert.assertEquals(MINIMUM_WEEKS, offer.getMinimumWeeks());
        Assert.assertEquals(WEEKLY_HOURS, offer.getWeeklyHours());
        Assert.assertEquals(FROM_DATE, offer.getFromDate());
        Assert.assertEquals(TO_DATE, offer.getToDate());
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
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullEmployerName() {
        offer.setEmployerName(null);
        dao.persist(offer);
    }

    /* TODO does not work for @ElementCollection without a separate table
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullStudyLevel() {
        offer.setStudyLevels(null);
        dao.persist(offer);
    }*/

    /* TODO does not work for @ElementCollection without a separate table
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testEmptyStudyLevel() {
        offer.setStudyLevels(Collections.<StudyLevel>emptyList());
        dao.persist(offer);
    }
    */
    /* TODO: at least one field of studies
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullFieldOfStudies() {

    }
    */

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
    }

    @Test
    @Transactional
    public void testOtherRequirements() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append("a");
        }

        offer.setOtherRequirements(sb.toString());
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testTooLongOtherRequirements() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 501; i++) {
            sb.append("a");
        }

        offer.setOtherRequirements(sb.toString());
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testWorkDescription() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("a");
        }

        offer.setWorkDescription(sb.toString());
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testTooLongWorkDescription() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1001; i++) {
            sb.append("a");
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
        Assert.assertEquals(Float.valueOf("0.999"), offer.getWeeklyHours());
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
        offer.setWeeklyHours(100f);
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testDailyHoursPrecision() {
        offer.setDailyHours(0.999f);
        dao.persist(offer);
        offer = entityManager.find(OfferEntity.class, offer.getId());
        Assert.assertEquals(Float.valueOf("0.999"), offer.getDailyHours());
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
        offer.setDailyHours(100f);
        dao.persist(offer);
    }

    @Test
    @Transactional
    public void testPayment() {
        offer.setPayment(BigDecimal.valueOf(1234567890.12));
        offer.setPaymentFrequency(PaymentFrequency.M);
        dao.persist(offer);
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

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    @Transactional
    public void testNullPaymentFrequency() {
        offer.setPayment(null);
        offer.setPaymentFrequency(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullPaymentFrequencyWhenPaymentNotNull() {
        offer.setPayment(PAYMENT);
        offer.setPaymentFrequency(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullPaymentFrequencyWhenPaymentNotNullOnUpdate() {
        dao.persist(offer);
        offer.setPayment(PAYMENT);
        offer.setPaymentFrequency(null);
        dao.persist(offer);
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    @Transactional
    public void testNullLodgingCostFrequency() {
        offer.setLodgingCostFrequency(null);
        offer.setLodgingCost(null);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullLodgingCostFrequencyWhenLodgingCostNotNull() {
        offer.setLodgingCostFrequency(null);
        offer.setLodgingCost(LODGING_COST);
        dao.persist(offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullLodgingCostFrequencyWhenLodgingCostNotNullOnUpdate() {
        dao.persist(offer);
        offer.setLodgingCostFrequency(null);
        offer.setLodgingCost(LODGING_COST);
        dao.persist(offer);
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    @Transactional
    public void testNullLivingCostFrequency() {
        offer.setLivingCostFrequency(null);
        offer.setLivingCost(null);
        dao.persist(offer);
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
    public void testNullLivingCostFrequencyWhenLivingCostNotNullOnUpdate() {
        dao.persist(offer);
        offer.setLivingCostFrequency(null);
        offer.setLivingCost(LIVING_COST);
        dao.persist(offer);
    }
}
