/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.OfferTestUtility
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.api.dtos;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class OfferTestUtility {
    public static final String REF_NO = "AT-2012-1234-AB";
    public static final Date NOMINATION_DEADLINE = new Date();
    public static final String EMPLOYER_NAME = "Test_Employer_1";
    public static final String WORK_DESCRIPTION = "nothing";
    public static final Integer MAXIMUM_WEEKS = 12;
    public static final Integer MINIMUM_WEEKS = 12;
    public static final Float WEEKLY_HOURS = 40.0f;
    public static final Date FROM_DATE = new Date();
    public static final Date TO_DATE = new Date(new Date().getTime() + 3600 * 24 * 90);
    public static final BigDecimal PAYMENT = new BigDecimal(3000);
    public static final BigDecimal LODGING_COST = new BigDecimal(1000);
    public static final BigDecimal LIVING_COST = new BigDecimal(2000);
    private static final String EMPLOYER_ADDRESS = "test address 30";
    private static final String EMPLOYER_ADDRESS2 = "test address 31";
    private static final String EMPLOYER_BUSINESS = "test business";
    private static final Integer EMPLOYER_EMPLOYEES_COUNT = 10;
    private static final String EMPLOYER_WEBSITE = "www.website.at";
    private static final String OTHER_REQUIREMENTS = "cooking";
    private static final String WORKING_PLACE = "Vienna";
    private static final String NEAREST_AIRPORT = "VIE";
    private static final String NEAREST_PUBLIC_TRANSPORT = "U4";
    private static final Float DAILY_HOURS = WEEKLY_HOURS / 5;
    private static final Currency CURRENCY = Currency.EUR;
    private static final PaymentFrequency PAYMENT_FREQUENCY = PaymentFrequency.W;
    private static final Integer DEDUCTION = 20;
    private static final String LODGING_BY = "IAESTE";
    private static final PaymentFrequency LODGING_COST_FREQUENCY = PaymentFrequency.M;
    private static final PaymentFrequency LIVING_COST_FREQUENCY = PaymentFrequency.M;
    private static final Boolean CANTEEN = true;

    private OfferTestUtility() {
    }

    public static Offer getEmptyOffer() {
        return new Offer();
    }

    public static Offer getMinimalOffer() {
        final Offer minimalOffer = new Offer();
        minimalOffer.setRefNo(REF_NO);
        minimalOffer.setEmployerName(EMPLOYER_NAME);
        {
            final List<StudyLevel> list = new ArrayList<StudyLevel>(1);
            list.add(StudyLevel.E);
            minimalOffer.setStudyLevels(list);
        }
        {
            final List<FieldOfStudy> fieldOfStudies = new ArrayList<FieldOfStudy>();
            fieldOfStudies.add(FieldOfStudy.IT);
            minimalOffer.setFieldOfStudies(fieldOfStudies);
        }
        minimalOffer.setGender(Gender.E);
        minimalOffer.setLanguage1(Language.ENGLISH);
        minimalOffer.setLanguage1Level(LanguageLevel.E);
        minimalOffer.setWorkDescription(WORK_DESCRIPTION);
        minimalOffer.setMaximumWeeks(MAXIMUM_WEEKS);
        minimalOffer.setMinimumWeeks(MINIMUM_WEEKS);
        minimalOffer.setWeeklyHours(WEEKLY_HOURS);
        minimalOffer.setFromDate(FROM_DATE);
        minimalOffer.setToDate(TO_DATE);
        return minimalOffer;
    }

    public static Offer getFullOffer() {
        final Offer offer = getMinimalOffer();
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
        {
            List<TypeOfWork> typesOfWork = new ArrayList<>(2);
            typesOfWork.add(TypeOfWork.R);
            typesOfWork.add(TypeOfWork.N);
            offer.setTypeOfWork(typesOfWork);
        }
        offer.setFromDate2(FROM_DATE);
        offer.setToDate2(TO_DATE);
        offer.setUnavailableFrom(FROM_DATE);
        offer.setUnavailableTo(TO_DATE);
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
        {
            final List<String> list = new ArrayList<>(2);
            list.add(Specialization.INFORMATION_TECHNOLOGY.toString());
            list.add("some specialization");
            offer.setSpecializations(list);
        }
        return offer;
    }
}
