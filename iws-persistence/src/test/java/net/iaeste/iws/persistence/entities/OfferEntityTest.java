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

import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.LanguageOperator;
import net.iaeste.iws.api.enums.PaymentFrequency;
import net.iaeste.iws.api.enums.Specialization;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.enums.TypeOfWork;
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
    private static final Float WEEKLY_HOURS = 40.0f;
    private static final Float DAILY_HOURS = 8.0f;
    private static final Date FROM_DATE = new Date();
    private static final Date TO_DATE = new Date(FROM_DATE.getTime() + 3600 * 24 * 90);
    private static final Date FROM_DATE2 = new Date(TO_DATE.getTime() + 3600 * 24 * 90);
    private static final Date TO_DATE2 = new Date(FROM_DATE2.getTime() + 3600 * 24 * 90);
    private static final Date UNAVAIABLE_FROM = new Date(TO_DATE.getTime());
    private static final Date UNAVAIABLE_TO = new Date(FROM_DATE2.getTime());
    private static final BigDecimal PAYMENT = new BigDecimal(3000);
    private static final BigDecimal LODGING_COST = new BigDecimal(1000);
    private static final BigDecimal LIVING_COST = new BigDecimal(2000);
    private static final String FIELDS_OF_STUDY = String.format("%s|%s", FieldOfStudy.IT, FieldOfStudy.CHEMISTRY);
    private static final String SPECIALIZATIONS = String.format("%s|%s", Specialization.INFORMATION_TECHNOLOGY, "Custom");
    private static final String STUDY_LEVELS = String.format("%s|%s", StudyLevel.E, StudyLevel.M);
    private static final String TYPE_OF_WORK = String.format("|", TypeOfWork.N, TypeOfWork.P);

    private static final String EMPLOYER_ADDRESS = "test address 30";
    private static final String EMPLOYER_ADDRESS2 = "test address 31";
    private static final String EMPLOYER_BUSINESS = "test business";
    private static final Integer EMPLOYER_EMPLOYEES_COUNT = 10;
    private static final String EMPLOYER_WEBSITE = "www.website.at";
    private static final String OTHER_REQUIREMENTS = "cooking";
    private static final String WORKING_PLACE = "Vienna";
    private static final String NEAREST_AIRPORT = "VIE";
    private static final String NEAREST_PUBLIC_TRANSPORT = "U4";
    private static final Currency CURRENCY = Currency.EUR;
    private static final PaymentFrequency PAYMENT_FREQUENCY = PaymentFrequency.W;
    private static final Integer DEDUCTION = 20;
    private static final String LODGING_BY = "IAESTE";
    private static final PaymentFrequency LODGING_COST_FREQUENCY = PaymentFrequency.M;
    private static final PaymentFrequency LIVING_COST_FREQUENCY = PaymentFrequency.M;
    private static final Boolean CANTEEN = true;

    private OfferDao dao;
    @PersistenceContext
    private EntityManager entityManager;

    private OfferEntity offer;

    @Before
    public void before() {
        dao = new OfferJpaDao(entityManager);
        offer = getMinimalOffer();
    }

    private static OfferEntity getMinimalOffer() {
        final OfferEntity offer = new OfferEntity();
        offer.setRefNo(REF_NO);
        offer.setEmployerName(EMPLOYER_NAME);
        offer.setStudyLevels(STUDY_LEVELS);
        offer.setFieldOfStudies(FIELDS_OF_STUDY);
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


    public static OfferEntity getFullOffer() {
        final OfferEntity offer = getMinimalOffer();
        offer.setNominationDeadline(NOMINATION_DEADLINE);
        offer.setEmployerAddress(EMPLOYER_ADDRESS);
        offer.setEmployerAddress2(EMPLOYER_ADDRESS2);
        offer.setEmployerBusiness(EMPLOYER_BUSINESS);
        offer.setEmployerEmployeesCount(EMPLOYER_EMPLOYEES_COUNT);
        offer.setEmployerWebsite(EMPLOYER_WEBSITE);
        offer.setPrevTrainingRequired(true);
        offer.setOtherRequirements(OTHER_REQUIREMENTS);
        offer.setLanguage1Operator(LanguageOperator.A);
        offer.setLanguage2(Language.FRENCH);
        offer.setLanguage2Level(LanguageLevel.E);
        offer.setLanguage2Operator(LanguageOperator.O);
        offer.setLanguage3(Language.GERMAN);
        offer.setLanguage3Level(LanguageLevel.E);
        offer.setTypeOfWork(TYPE_OF_WORK);
        offer.setFromDate2(FROM_DATE2);
        offer.setToDate2(TO_DATE2);
        offer.setUnavailableFrom(UNAVAIABLE_FROM);
        offer.setUnavailableTo(UNAVAIABLE_TO);
        offer.setWorkingPlace(WORKING_PLACE);
        offer.setNearestAirport(NEAREST_AIRPORT);
        offer.setNearestPubTransport(NEAREST_PUBLIC_TRANSPORT);
        offer.setDailyHours(DAILY_HOURS);
        offer.setCurrency(CURRENCY);
        offer.setPaymentFrequency(PAYMENT_FREQUENCY);
        offer.setDeduction(DEDUCTION);
        offer.setLodgingBy(LODGING_BY);
        offer.setLodgingCost(LODGING_COST);
        offer.setLodgingCostFrequency(LODGING_COST_FREQUENCY);
        offer.setLivingCost(LIVING_COST);
        offer.setLivingCostFrequency(LIVING_COST_FREQUENCY);
        offer.setCanteen(CANTEEN);
        offer.setSpecializations(SPECIALIZATIONS);
        return offer;
    }

    @Test
    @Transactional
    public void testMinimalOffer() {
        dao.persist(offer);
        Assert.assertThat(offer.getId(), is(notNullValue()));

        offer = entityManager.find(OfferEntity.class, offer.getId());
        Assert.assertThat(offer.getRefNo(), is(REF_NO));
        Assert.assertThat(offer.getEmployerName(), is(EMPLOYER_NAME));
        Assert.assertThat(offer.getStudyLevels(), is(STUDY_LEVELS));
        Assert.assertThat(offer.getFieldOfStudies(), is(FIELDS_OF_STUDY));
        Assert.assertThat(offer.getGender(), is(Gender.E));
        Assert.assertThat(offer.getLanguage1(), is(Language.ENGLISH));
        Assert.assertThat(offer.getLanguage1Level(), is(LanguageLevel.E));
        Assert.assertThat(offer.getWorkDescription(), is(WORK_DESCRIPTION));
        Assert.assertThat(offer.getMaximumWeeks(), is(MAXIMUM_WEEKS));
        Assert.assertThat(offer.getMinimumWeeks(), is(MINIMUM_WEEKS));
        Assert.assertThat(offer.getWeeklyHours(), is(WEEKLY_HOURS));
        Assert.assertThat(offer.getFromDate(), is(FROM_DATE));
        Assert.assertThat(offer.getToDate(), is(TO_DATE));

        final OfferEntity persisted = dao.findOffer(offer.getId());
        Assert.assertThat(offer, is(persisted));
    }

    @Test
    @Transactional
    public void testFullOffer() {
        offer = getFullOffer();
        dao.persist(offer);
        Assert.assertThat(offer.getId(), is(notNullValue()));

        offer = entityManager.find(OfferEntity.class, offer.getId());
        Assert.assertThat(offer.getRefNo(), is(REF_NO));
        Assert.assertThat(offer.getEmployerName(), is(EMPLOYER_NAME));
        Assert.assertThat(offer.getStudyLevels(), is(STUDY_LEVELS));
        Assert.assertThat(offer.getFieldOfStudies(), is(FIELDS_OF_STUDY));
        Assert.assertThat(offer.getGender(), is(Gender.E));
        Assert.assertThat(offer.getLanguage1(), is(Language.ENGLISH));
        Assert.assertThat(offer.getLanguage1Level(), is(LanguageLevel.E));
        Assert.assertThat(offer.getWorkDescription(), is(WORK_DESCRIPTION));
        Assert.assertThat(offer.getMaximumWeeks(), is(MAXIMUM_WEEKS));
        Assert.assertThat(offer.getMinimumWeeks(), is(MINIMUM_WEEKS));
        Assert.assertThat(offer.getWeeklyHours(), is(WEEKLY_HOURS));
        Assert.assertThat(offer.getFromDate(), is(FROM_DATE));
        Assert.assertThat(offer.getToDate(), is(TO_DATE));

        Assert.assertThat(offer.getNominationDeadline(), is(NOMINATION_DEADLINE));
        Assert.assertThat(offer.getEmployerAddress(), is(EMPLOYER_ADDRESS));
        Assert.assertThat(offer.getEmployerAddress2(), is(EMPLOYER_ADDRESS2));
        Assert.assertThat(offer.getEmployerBusiness(), is(EMPLOYER_BUSINESS));
        Assert.assertThat(offer.getEmployerEmployeesCount(), is(EMPLOYER_EMPLOYEES_COUNT));
        Assert.assertThat(offer.getEmployerWebsite(), is(EMPLOYER_WEBSITE));
        Assert.assertThat(offer.getPrevTrainingRequired(), is(true));
        Assert.assertThat(offer.getOtherRequirements(), is(OTHER_REQUIREMENTS));
        Assert.assertThat(offer.getLanguage1Operator(), is(LanguageOperator.A));
        Assert.assertThat(offer.getLanguage2(), is(Language.FRENCH));
        Assert.assertThat(offer.getLanguage2Level(), is(LanguageLevel.E));
        Assert.assertThat(offer.getLanguage2Operator(), is(LanguageOperator.O));
        Assert.assertThat(offer.getLanguage3(), is(Language.GERMAN));
        Assert.assertThat(offer.getLanguage3Level(), is(LanguageLevel.E));
        Assert.assertThat(offer.getTypeOfWork(), is(TYPE_OF_WORK));
        Assert.assertThat(offer.getFromDate2(), is(FROM_DATE2));
        Assert.assertThat(offer.getToDate2(), is(TO_DATE2));
        Assert.assertThat(offer.getUnavailableFrom(), is(UNAVAIABLE_FROM));
        Assert.assertThat(offer.getUnavailableTo(), is(UNAVAIABLE_TO));
        Assert.assertThat(offer.getWorkingPlace(), is(WORKING_PLACE));
        Assert.assertThat(offer.getNearestAirport(), is(NEAREST_AIRPORT));
        Assert.assertThat(offer.getNearestPubTransport(), is(NEAREST_PUBLIC_TRANSPORT));
        Assert.assertThat(offer.getDailyHours(), is(DAILY_HOURS));
        Assert.assertThat(offer.getCurrency(), is(CURRENCY));
        Assert.assertThat(offer.getPaymentFrequency(), is(PAYMENT_FREQUENCY));
        Assert.assertThat(offer.getDeduction(), is(DEDUCTION));
        Assert.assertThat(offer.getLodgingBy(), is(LODGING_BY));
        Assert.assertThat(offer.getLodgingCost(), is(LODGING_COST));
        Assert.assertThat(offer.getLodgingCostFrequency(), is(LODGING_COST_FREQUENCY));
        Assert.assertThat(offer.getLivingCost(), is(LIVING_COST));
        Assert.assertThat(offer.getLivingCostFrequency(), is(LIVING_COST_FREQUENCY));
        Assert.assertThat(offer.getCanteen(), is(CANTEEN));
        Assert.assertThat(offer.getSpecializations(), is(SPECIALIZATIONS));

        final OfferEntity persisted = dao.findOffer(offer.getId());
        Assert.assertThat(offer, is(persisted));
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
    public void testTypeOfWork() {
        offer.setTypeOfWork(TYPE_OF_WORK);

        offer.setId(null);
        dao.persist(offer);

        Assert.assertThat(offer, is(notNullValue()));
        Assert.assertThat(offer.getId(), is(notNullValue()));
        Assert.assertThat(offer.getTypeOfWork(), is(TYPE_OF_WORK));
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
    }
}
