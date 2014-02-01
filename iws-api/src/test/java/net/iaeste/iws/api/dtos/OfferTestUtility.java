/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.api.util.Date;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 * @deprecated please use TestData instead
 * @noinspection PublicStaticCollectionField
 */
@Deprecated
public final class OfferTestUtility {

    public static final String REF_NO = "AT-2012-123456";
    public static final Date NOMINATION_DEADLINE = TestData.OFFER_NOMINATION_DEADLINE;
    public static final String EMPLOYER_NAME = "Test_Employer_1";
    public static final String WORK_DESCRIPTION = TestData.OFFER_WORK_DESCRIPTION;
    public static final Integer MAXIMUM_WEEKS = TestData.OFFER_MAXIMUM_WEEKS;
    public static final Integer MINIMUM_WEEKS = TestData.OFFER_MINIMUM_WEEKS;
    public static final Float WEEKLY_HOURS = TestData.EMPLOYER_WEEKLY_HOURS;
    public static final Date FROM_DATE = TestData.OFFER_PERIOD1.getFromDate();
    public static final Date TO_DATE = TestData.OFFER_PERIOD1.getToDate();
    public static final TypeOfWork TYPE_OF_WORK = TestData.OFFER_TYPE_OF_WORK;
    public static final Date FROM_DATE2 = TestData.OFFER_PERIOD2.getFromDate();
    public static final Date TO_DATE2 = TestData.OFFER_PERIOD2.getToDate();
    public static final Date UNAVAIABLE_FROM = TestData.OFFER_UNAVAILABLE.getFromDate();
    public static final Date UNAVAIABLE_TO = TestData.OFFER_UNAVAILABLE.getToDate();
    public static final BigDecimal PAYMENT = TestData.OFFER_PAYMENT;
    public static final BigDecimal LODGING_COST = TestData.OFFER_LODGING_COST;
    public static final BigDecimal LIVING_COST = TestData.OFFER_LIVING_COST;
    public static final String EMPLOYER_ADDRESS = TestData.ADDRESS_STREET1;
    public static final String EMPLOYER_ADDRESS2 = TestData.ADDRESS_STREET2;
    public static final String EMPLOYER_BUSINESS = TestData.EMPLOYER_BUSINESS;
    public static final String EMPLOYER_EMPLOYEES_COUNT = TestData.EMPLOYER_EMPLOYEES_COUNT;
    public static final String EMPLOYER_WEBSITE = TestData.EMPLOYER_WEBSITE;
    public static final String OTHER_REQUIREMENTS = TestData.OFFER_OTHER_REQUIREMENTS;
    public static final String WORKING_PLACE = TestData.EMPLOYER_WORKING_PLACE;
    public static final String NEAREST_AIRPORT = TestData.EMPLOYER_NEAREAST_AIRPORT;
    public static final String NEAREST_PUBLIC_TRANSPORT = TestData.EMPLOYER_NEAREST_PUBLIC_TRANSPORT;
    public static final Float DAILY_HOURS = TestData.EMPLOYER_DAILY_HOURS;
    public static final Currency CURRENCY = Currency.EUR;
    public static final PaymentFrequency PAYMENT_FREQUENCY = TestData.OFFER_PAYMENT_FREQUENCY;
    public static final String DEDUCTION = TestData.OFFER_DEDUCTION;
    public static final String LODGING_BY = TestData.OFFER_LODGING_BY;
    public static final PaymentFrequency LODGING_COST_FREQUENCY = TestData.OFFER_LODGING_COST_FREQUENCY;
    public static final PaymentFrequency LIVING_COST_FREQUENCY = TestData.OFFER_LIVING_COST_FREQUENCY;
    public static final Boolean CANTEEN = TestData.EMPLOYER_CANTEEN;
    public static final Set<String> SPECIALIZATIONS = TestData.OFFER_SPECIALIZATIONS;
    public static final Set<FieldOfStudy> FIELD_OF_STUDIES = TestData.OFFER_FIELD_OF_STUDY;
    public static final Set<StudyLevel> STUDY_LEVELS = TestData.OFFER_STUDY_LEVELS;

    private OfferTestUtility() {
    }

    @Deprecated
    public static Offer getMinimalOffer() {
        return TestData.prepareMinimalOffer(REF_NO, EMPLOYER_NAME, "AT");
    }

    @Deprecated
    public static Offer getFullOffer() {
        return TestData.prepareFullOffer(REF_NO, EMPLOYER_NAME, "AT");
    }
}
