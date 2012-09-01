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
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

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
    public static final Date TO_DATE = new Date(FROM_DATE.getTime() + 3600 * 24 * 90);
    public static final Set<TypeOfWork> TYPE_OF_WORK = EnumSet.of(TypeOfWork.R, TypeOfWork.N);
    public static final Date FROM_DATE2 = new Date(TO_DATE.getTime() + 3600 * 24 * 90);
    public static final Date TO_DATE2 = new Date(FROM_DATE2.getTime() + 3600 * 24 * 90);
    public static final Date UNAVAIABLE_FROM = new Date(TO_DATE.getTime());
    public static final Date UNAVAIABLE_TO = new Date(FROM_DATE2.getTime());
    public static final BigDecimal PAYMENT = new BigDecimal(3000);
    public static final BigDecimal LODGING_COST = new BigDecimal(1000);
    public static final BigDecimal LIVING_COST = new BigDecimal(2000);
    public static final String EMPLOYER_ADDRESS = "test address 30";
    public static final String EMPLOYER_ADDRESS2 = "test address 31";
    public static final String EMPLOYER_BUSINESS = "test business";
    public static final Integer EMPLOYER_EMPLOYEES_COUNT = 10;
    public static final String EMPLOYER_WEBSITE = "www.website.at";
    public static final String OTHER_REQUIREMENTS = "cooking";
    public static final String WORKING_PLACE = "Vienna";
    public static final String NEAREST_AIRPORT = "VIE";
    public static final String NEAREST_PUBLIC_TRANSPORT = "U4";
    public static final Float DAILY_HOURS = WEEKLY_HOURS / 5;
    public static final Currency CURRENCY = Currency.EUR;
    public static final PaymentFrequency PAYMENT_FREQUENCY = PaymentFrequency.W;
    public static final Integer DEDUCTION = 20;
    public static final String LODGING_BY = "IAESTE";
    public static final PaymentFrequency LODGING_COST_FREQUENCY = PaymentFrequency.M;
    public static final PaymentFrequency LIVING_COST_FREQUENCY = PaymentFrequency.M;
    public static final Boolean CANTEEN = true;

    private OfferTestUtility() {
    }

    public static Offer getEmptyOffer() {
        return new Offer();
    }

    public static Offer getMinimalOffer() {
        final Offer minimalOffer = new Offer();
        minimalOffer.setRefNo(REF_NO);
        minimalOffer.setEmployerName(EMPLOYER_NAME);
        minimalOffer.setStudyLevels(EnumSet.of(StudyLevel.E));
        minimalOffer.setFieldOfStudies(EnumSet.of(FieldOfStudy.IT));
        minimalOffer.setGender(Gender.E);
        minimalOffer.setLanguage1(Language.ENGLISH);
        minimalOffer.setLanguage1Level(LanguageLevel.E);
        minimalOffer.setWorkDescription(WORK_DESCRIPTION);
        minimalOffer.setMaximumWeeks(MAXIMUM_WEEKS);
        minimalOffer.setMinimumWeeks(MINIMUM_WEEKS);
        minimalOffer.setWeeklyHours(WEEKLY_HOURS);
        minimalOffer.setFromDate(FROM_DATE);
        minimalOffer.setToDate(TO_DATE);
        minimalOffer.setPayment(null);
        return minimalOffer;
    }

    public static Offer getFullOffer() {
        final Offer offer = getMinimalOffer();
        offer.setRefNo("GB-2012-1234-AB");
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
        offer.setPayment(PAYMENT);
        offer.setPaymentFrequency(PAYMENT_FREQUENCY);
        offer.setDeduction(DEDUCTION);
        offer.setLodgingBy(LODGING_BY);
        offer.setLodgingCost(LODGING_COST);
        offer.setLodgingCostFrequency(LODGING_COST_FREQUENCY);
        offer.setLivingCost(LIVING_COST);
        offer.setLivingCostFrequency(LIVING_COST_FREQUENCY);
        offer.setCanteen(CANTEEN);
        {
            final Set<String> specializations = new HashSet<>(2);
            specializations.add(Specialization.INFORMATION_TECHNOLOGY.toString());
            specializations.add("some custom specialization");
            offer.setSpecializations(specializations);
        }
        return offer;
    }
}
