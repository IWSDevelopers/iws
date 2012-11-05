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

import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.LanguageOperator;
import net.iaeste.iws.api.enums.PaymentFrequency;
import net.iaeste.iws.api.enums.Specialization;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.enums.TypeOfWork;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.OfferJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
import org.junit.After;
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
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Contains tests for OfferEntity and OfferJpaDao
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@SuppressWarnings("ClassWithTooManyFields")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { SpringConfig.class })
public class OfferEntityTest {

    private static final String REF_NO = "AT-2012-1234-AB";
    private static final String REF_NO_2 = "AT-2012-5678-AB";
    private static final Date NOMINATION_DEADLINE = new Date();
    private static final String EMPLOYER_NAME = "Test_Employer_1";
    private static final String EMPLOYER_NAME_LIKE = EMPLOYER_NAME.substring(3, 3);
    private static final String EMPLOYER_NAME_LIKE_NONEXISTING = "XxXxX";
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
    private static final String TYPE_OF_WORK = TypeOfWork.R.toString();
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

    @PersistenceContext
    private EntityManager entityManager;

    private OfferDao offerDao = null;

    private OfferEntity offer = null;
    private Authentication authentication = null;

    @Before
    public void before() {
        offerDao = new OfferJpaDao(entityManager);
        final AccessDao accessDao = new AccessJpaDao(entityManager);

        offer = getMinimalOffer();
        final UserEntity user = accessDao.findUserByUsername("austria");
        final GroupEntity group = accessDao.findGroup(user, GroupType.NATIONAL);
        offer.setGroup(group);
        authentication = new Authentication(user, group);
    }

    private static OfferEntity getMinimalOffer() {
        final OfferEntity offer = new OfferEntity();
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
        offerDao.persist(authentication, offer);
        assertThat(offer.getId(), is(notNullValue()));

        offer = entityManager.find(OfferEntity.class, offer.getId());
        assertThat(offer.getRefNo(), is(REF_NO));
        assertThat(offer.getEmployerName(), is(EMPLOYER_NAME));
        assertThat(offer.getStudyLevels(), is(STUDY_LEVELS));
        assertThat(offer.getFieldOfStudies(), is(FIELDS_OF_STUDY));
        assertThat(offer.getLanguage1(), is(Language.ENGLISH));
        assertThat(offer.getLanguage1Level(), is(LanguageLevel.E));
        assertThat(offer.getWorkDescription(), is(WORK_DESCRIPTION));
        assertThat(offer.getMaximumWeeks(), is(MAXIMUM_WEEKS));
        assertThat(offer.getMinimumWeeks(), is(MINIMUM_WEEKS));
        assertThat(offer.getWeeklyHours(), is(WEEKLY_HOURS));
        assertThat(offer.getFromDate(), is(FROM_DATE));
        assertThat(offer.getToDate(), is(TO_DATE));

        final OfferEntity persisted = offerDao.findOffer(offer.getId());
        assertThat(offer, is(persisted));
    }

    @Test
    @Transactional
    public void testFullOffer() {
        final GroupEntity group = offer.getGroup();
        offer = getFullOffer();
        offer.setGroup(group);
        offerDao.persist(authentication, offer);
        assertThat(offer.getId(), is(notNullValue()));

        offer = entityManager.find(OfferEntity.class, offer.getId());
        assertThat(offer.getRefNo(), is(REF_NO));
        assertThat(offer.getEmployerName(), is(EMPLOYER_NAME));
        assertThat(offer.getStudyLevels(), is(STUDY_LEVELS));
        assertThat(offer.getFieldOfStudies(), is(FIELDS_OF_STUDY));
        assertThat(offer.getLanguage1(), is(Language.ENGLISH));
        assertThat(offer.getLanguage1Level(), is(LanguageLevel.E));
        assertThat(offer.getWorkDescription(), is(WORK_DESCRIPTION));
        assertThat(offer.getMaximumWeeks(), is(MAXIMUM_WEEKS));
        assertThat(offer.getMinimumWeeks(), is(MINIMUM_WEEKS));
        assertThat(offer.getWeeklyHours(), is(WEEKLY_HOURS));
        assertThat(offer.getFromDate(), is(FROM_DATE));
        assertThat(offer.getToDate(), is(TO_DATE));

        assertThat(offer.getNominationDeadline(), is(NOMINATION_DEADLINE));
        assertThat(offer.getEmployerAddress(), is(EMPLOYER_ADDRESS));
        assertThat(offer.getEmployerAddress2(), is(EMPLOYER_ADDRESS2));
        assertThat(offer.getEmployerBusiness(), is(EMPLOYER_BUSINESS));
        assertThat(offer.getEmployerEmployeesCount(), is(EMPLOYER_EMPLOYEES_COUNT));
        assertThat(offer.getEmployerWebsite(), is(EMPLOYER_WEBSITE));
        assertThat(offer.getPrevTrainingRequired(), is(true));
        assertThat(offer.getOtherRequirements(), is(OTHER_REQUIREMENTS));
        assertThat(offer.getLanguage1Operator(), is(LanguageOperator.A));
        assertThat(offer.getLanguage2(), is(Language.FRENCH));
        assertThat(offer.getLanguage2Level(), is(LanguageLevel.E));
        assertThat(offer.getLanguage2Operator(), is(LanguageOperator.O));
        assertThat(offer.getLanguage3(), is(Language.GERMAN));
        assertThat(offer.getLanguage3Level(), is(LanguageLevel.E));
        assertThat(offer.getTypeOfWork(), is(TYPE_OF_WORK));
        assertThat(offer.getFromDate2(), is(FROM_DATE2));
        assertThat(offer.getToDate2(), is(TO_DATE2));
        assertThat(offer.getUnavailableFrom(), is(UNAVAIABLE_FROM));
        assertThat(offer.getUnavailableTo(), is(UNAVAIABLE_TO));
        assertThat(offer.getWorkingPlace(), is(WORKING_PLACE));
        assertThat(offer.getNearestAirport(), is(NEAREST_AIRPORT));
        assertThat(offer.getNearestPubTransport(), is(NEAREST_PUBLIC_TRANSPORT));
        assertThat(offer.getDailyHours(), is(DAILY_HOURS));
        assertThat(offer.getCurrency(), is(CURRENCY));
        assertThat(offer.getPaymentFrequency(), is(PAYMENT_FREQUENCY));
        assertThat(offer.getDeduction(), is(DEDUCTION));
        assertThat(offer.getLodgingBy(), is(LODGING_BY));
        assertThat(offer.getLodgingCost(), is(LODGING_COST));
        assertThat(offer.getLodgingCostFrequency(), is(LODGING_COST_FREQUENCY));
        assertThat(offer.getLivingCost(), is(LIVING_COST));
        assertThat(offer.getLivingCostFrequency(), is(LIVING_COST_FREQUENCY));
        assertThat(offer.getCanteen(), is(CANTEEN));
        assertThat(offer.getSpecializations(), is(SPECIALIZATIONS));

        final OfferEntity persisted = offerDao.findOffer(offer.getId());
        assertThat(offer, is(persisted));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testUniqueRefNo() {
        final String refNo = "CZ-2012-1001";
        offer.setRefNo(refNo);
        offer.setId(null);
        offerDao.persist(authentication, offer);
        assertThat(offer.getId(), is(notNullValue()));

        offer = getMinimalOffer();
        offer.setRefNo(refNo);
        offer.setId(null);
        offerDao.persist(authentication, offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullRefNo() {
        offer.setRefNo(null);
        offerDao.persist(authentication, offer);
    }

    @Test
    @Transactional
    public void testNullNominationDeadline() {
        offer.setNominationDeadline(null);
        offerDao.persist(authentication, offer);

        assertThat(offer.getId(), is(notNullValue()));
        assertThat(offerDao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullEmployerName() {
        offer.setEmployerName(null);
        offerDao.persist(authentication, offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullLang1() {
        offer.setLanguage1(null);
        offerDao.persist(authentication, offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullLang1Level() {
        offer.setLanguage1Level(null);
        offerDao.persist(authentication, offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullWorkDescription() {
        offer.setWorkDescription(null);
        offerDao.persist(authentication, offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullMaxWeeks() {
        offer.setMaximumWeeks(null);
        offerDao.persist(authentication, offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullMinWeeks() {
        offer.setMinimumWeeks(null);
        offerDao.persist(authentication, offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullWeeklyHours() {
        offer.setWeeklyHours(null);
        offerDao.persist(authentication, offer);
    }

    @Test
    @Transactional
    public void testNullDailyHours() {
        offer.setDailyHours(null);
        offerDao.persist(authentication, offer);

        assertThat(offer.getId(), is(notNullValue()));
        final OfferEntity foundOffer = offerDao.findOffer(offer.getId());
        assertThat(foundOffer, is(offer));
    }

    @Test
    @Transactional
    public void testOtherRequirements() {
        final StringBuilder sb = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            sb.append('a');
        }

        offer.setOtherRequirements(sb.toString());
        offerDao.persist(authentication, offer);

        assertThat(offer.getId(), is(notNullValue()));
        assertThat(offerDao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testTooLongOtherRequirements() {
        final StringBuilder sb = new StringBuilder(501);
        for (int i = 0; i < 501; i++) {
            sb.append('a');
        }

        offer.setOtherRequirements(sb.toString());
        offerDao.persist(authentication, offer);
    }

    @Test
    @Transactional
    public void testWorkDescription() {
        final StringBuilder sb = new StringBuilder(1000);
        for (int i = 0; i < 1000; i++) {
            sb.append('a');
        }

        offer.setWorkDescription(sb.toString());
        offerDao.persist(authentication, offer);

        assertThat(offer.getId(), is(notNullValue()));
        assertThat(offerDao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testTooLongWorkDescription() {
        final StringBuilder sb = new StringBuilder(1001);
        for (int i = 0; i < 1001; i++) {
            sb.append('a');
        }

        offer.setWorkDescription(sb.toString());
        offerDao.persist(authentication, offer);
    }

    @Test
    @Transactional
    public void testWeeklyHoursPrecision() {
        offer.setWeeklyHours(0.999f);
        offerDao.persist(authentication, offer);
        offer = entityManager.find(OfferEntity.class, offer.getId());

        assertThat(offer.getWeeklyHours(), is(Float.valueOf("0.999")));
    }

    /* TODO for some reason the precision does not work with hsqldb
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testWeeklyHoursPrecision2() {
        offer.setWeeklyHours(10.9999f);
        offerDao.persist(authentication, offer);
        offer = entityManager.find(OfferEntity.class, offer.getId());
        Assert.assertEquals("10.9999", offer.getWeeklyHours().toString());
    }*/

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testWeeklyHoursPrecision3() {
        offer.setWeeklyHours(100.0f);
        offerDao.persist(authentication, offer);
    }

    @Test
    @Transactional
    public void testDailyHoursPrecision() {
        offer.setDailyHours(0.999f);
        offerDao.persist(authentication, offer);
        offer = entityManager.find(OfferEntity.class, offer.getId());
        assertThat(offer.getDailyHours(), is(Float.valueOf("0.999")));
    }

    /* TODO for some reason the precision does not work with hsqldb
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testDailyHoursPrecision2() {
        offer.setDailyHours(10.9999f);
        offerDao.persist(authentication, offer);
        offer = entityManager.find(OfferEntity.class, offer.getId());
        Assert.assertEquals("10.9999", offer.getDailyHours().toString());
    }*/

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testDailyHoursPrecision3() {
        offer.setDailyHours(100.0f);
        offerDao.persist(authentication, offer);
    }

    @Test
    @Transactional
    public void testPayment() {
        offer.setPayment(BigDecimal.valueOf(1234567890.12));
        offer.setPaymentFrequency(PaymentFrequency.M);
        offerDao.persist(authentication, offer);

        assertThat(offer.getId(), is(notNullValue()));
        assertThat(offerDao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testPayment2() {
        offer.setPayment(BigDecimal.valueOf(12345678901.0));
        offerDao.persist(authentication, offer);
    }

    /* TODO for some reason the precision does not work with hsqldb
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testPayment3() {
        offer.setPayment(BigDecimal.valueOf(1234567890.123));
        offerDao.persist(authentication, offer);
    }*/

    @Test
    @Transactional
    public void testDecuction() {
        offer.setDeduction(99);
        offerDao.persist(authentication, offer);

        assertThat(offer.getId(), is(notNullValue()));
        assertThat(offerDao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testDecuction2() {
        offer.setDeduction(100);
        offerDao.persist(authentication, offer);
    }

    @Test
    @Transactional
    public void testLodgingCost() {
        offer.setLodgingCost(BigDecimal.valueOf(1234567890.12));
        offer.setLodgingCostFrequency(PaymentFrequency.M);
        offerDao.persist(authentication, offer);

        assertThat(offer.getId(), is(notNullValue()));
        assertThat(offerDao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testLodgingCost2() {
        offer.setLodgingCost(BigDecimal.valueOf(12345678901.0));
        offerDao.persist(authentication, offer);
    }

    /* TODO for some reason the precision does not work with hsqldb
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testLodgingCost3() {
        offer.setLodgingCost(BigDecimal.valueOf(1234567890.123));
        offerDao.persist(authentication, offer);
    }*/

    @Test
    @Transactional
    public void testLivingCost() {
        offer.setLivingCost(BigDecimal.valueOf(1234567890.12));
        offer.setLivingCostFrequency(PaymentFrequency.M);
        offerDao.persist(authentication, offer);

        assertThat(offer.getId(), is(notNullValue()));
        assertThat(offerDao.findOffer(offer.getId()), is(offer));
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testLivingCost2() {
        offer.setLivingCost(BigDecimal.valueOf(12345678901.0));
        offerDao.persist(authentication, offer);
    }

    /* TODO for some reason the precision does not work with hsqldb
    @Test(expected = PersistenceException.class)
    @Transactional
    public void testLivingCost3() {
        offer.setLivingCost(BigDecimal.valueOf(1234567890.123));
        offerDao.persist(authentication, offer);
    }*/

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullFromDate() {
        offer.setFromDate(null);
        offerDao.persist(authentication, offer);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void testNullToDate() {
        offer.setToDate(null);
        offerDao.persist(authentication, offer);
    }

    @Test
    @Transactional
    public void testNullPaymentFrequency() {
        offer.setPayment(null);
        offer.setPaymentFrequency(null);
        offerDao.persist(authentication, offer);

        final OfferEntity persistedOffer = offerDao.findOffer(offer.getId());
        assertThat(persistedOffer, is(offer));
        assertThat(persistedOffer.getPayment(), is(nullValue()));
        assertThat(persistedOffer.getPaymentFrequency(), is(nullValue()));
    }

    @Transactional
    @Test
    public void testNullLodgingCostFrequency() {
        offer.setLodgingCostFrequency(null);
        offer.setLodgingCost(null);
        offerDao.persist(authentication, offer);

        final OfferEntity persistedOffer = offerDao.findOffer(offer.getId());
        assertThat(persistedOffer, is(offer));
        assertThat(persistedOffer.getLodgingCostFrequency(), is(nullValue()));
        assertThat(persistedOffer.getLodgingCost(), is(nullValue()));
    }

    @Test
    @Transactional
    public void testNullLivingCostFrequency() {
        offer.setLivingCostFrequency(null);
        offer.setLivingCost(null);
        offerDao.persist(authentication, offer);

        final OfferEntity persistedOffer = offerDao.findOffer(offer.getId());
        assertThat(persistedOffer, is(offer));
        assertThat(persistedOffer.getLivingCostFrequency(), is(nullValue()));
        assertThat(persistedOffer.getLivingCost(), is(nullValue()));
    }

    @Test
    @Transactional
    public void testTypeOfWork() {
        offer.setTypeOfWork(TYPE_OF_WORK);

        offer.setId(null);
        offerDao.persist(authentication, offer);

        assertThat(offer, is(notNullValue()));
        assertThat(offer.getId(), is(notNullValue()));
        assertThat(offer.getTypeOfWork(), is(TYPE_OF_WORK));
    }

    @Test
    @Transactional
    public void testFind() {
        assertThat(offerDao.findAll().size(), is(0));
        offerDao.persist(authentication, offer);
        final OfferEntity offerFoundByRefNo = offerDao.findOffer(offer.getRefNo());
        assertThat(offerFoundByRefNo, is(notNullValue()));
        assertThat(offerFoundByRefNo, is(offer));
        final OfferEntity offerFoundById = offerDao.findOffer(offer.getId());
        assertThat(offerFoundById, is(notNullValue()));
        assertThat(offerFoundById, is(offer));
        assertThat(offerDao.findOffersByEmployerName(EMPLOYER_NAME_LIKE_NONEXISTING).size(), is(0));
        final List<OfferEntity> offersFoundByEmployerName = offerDao.findOffersByEmployerName(offer.getEmployerName());
        if (offersFoundByEmployerName == null || offersFoundByEmployerName.isEmpty()) {
            fail("This should not happen!");
        }
        final OfferEntity offerFoundByEmployerName = offersFoundByEmployerName.get(0);
        assertThat(offerFoundByEmployerName, is(offer));
        final OfferEntity offer2 = getFullOffer();
        offer2.setRefNo(REF_NO_2);
        offer2.setGroup(offer.getGroup());
        offerDao.persist(authentication, offer2);
        assertThat(offerDao.findAll().size(), is(2));
        final List<OfferEntity> offersFoundByLikeEmployerName = offerDao.findOffersByLikeEmployerName(EMPLOYER_NAME_LIKE);
        if (offersFoundByLikeEmployerName == null || offersFoundByLikeEmployerName.isEmpty()) {
            fail("This should not happen!");
        }
        //we want to retrieve only one row for each employer
        assertThat(offersFoundByLikeEmployerName.size(), is(1));
        assertThat(offerDao.findOffersByLikeEmployerName(EMPLOYER_NAME_LIKE_NONEXISTING).size(), is(0));
    }

    @Test
    @Transactional
    public void testDeleteOffer() {
        final Long id = offer.getId();
        final String refNo = offer.getRefNo();
        assert id == null;
        offerDao.persist(authentication, offer);
        // make sure that offer was persisted
        final Long newId = offer.getId();
        assertThat(newId, is(notNullValue()));
        final OfferEntity found = offerDao.findOffer(newId);
        assertThat(found.getId(), is(newId));
        assertThat(found.getRefNo(), is(refNo));

        // try to delete offer
        offerDao.delete(found.getId());

        // make sure that offer was deleted
        final OfferEntity notFound = offerDao.findOffer(newId);
        assertThat(notFound, is(nullValue()));
    }

    @After
    public void cleanUp() {
    }
}
