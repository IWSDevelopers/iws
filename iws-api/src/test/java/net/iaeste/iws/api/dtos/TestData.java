/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.exchange.ExchangeType;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferType;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.Specialization;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class TestData {

    // =========================================================================
    // Default Test data values
    // =========================================================================

    // Default data for filling in our test Address Object, only thing missing
    // is the Country Code, which must be provided
    private static final String ADDRESS_STREET1 = "MyStreet 1";
    private static final String ADDRESS_STREET2 = "MyStreet 2";
    private static final String ADDRESS_ZIP = "123456789012";
    private static final String ADDRESS_CITY = "MyCity";
    private static final String ADDRESS_STATE = "MyState";

    // Default data for filling in our test Employer Object. Lacking, is the
    // Name of the Employer, which must be provided
    private static final String EMPLOYER_DEPARTMENT = "Employer Department";
    private static final String EMPLOYER_BUSINESS = "Employer Business";
    private static final String EMPLOYER_EMPLOYEES_COUNT = "Ca. 10";
    private static final String EMPLOYER_WEBSITE = "www.iaeste.org";
    private static final String EMPLOYER_WORKING_PLACE = "Employer Working Place";
    private static final Boolean EMPLOYER_CANTEEN = Boolean.TRUE;
    private static final String EMPLOYER_NEAREAST_AIRPORT = "";
    private static final String EMPLOYER_NEAREST_PUBLIC_TRANSPORT = "";

    // Default data for filling our test Offer Object. Lacking is the refNo,
    // which must be provided
    private static final Date INITIAL_DATE = new Date().plusDays(90);
    public static final OfferType OFFER_TYPE = OfferType.OPEN;
    public static final ExchangeType OFFER_EXCHANGE_TYPE = ExchangeType.COBE;
    private static final String OFFER_WORK_DESCRIPTION = "Work Description";
    private static final Float OFFER_WEEKLY_HOURS = 37.5f;
    private static final Float OFFER_DAILY_HOURS = 7.5f;
    private static final Float OFFER_WEEKLY_WORK_DAYS = 5.0f;
    private static final TypeOfWork OFFER_TYPE_OF_WORK = TypeOfWork.R;
    private static final Set<StudyLevel> OFFER_STUDY_LEVELS = Collections.unmodifiableSet(EnumSet.of(StudyLevel.E, StudyLevel.M));
    private static final Set<FieldOfStudy> OFFER_FIELD_OF_STUDY = Collections.unmodifiableSet(EnumSet.of(FieldOfStudy.IT, FieldOfStudy.AGRICULTURE));
    private static final Set<String> OFFER_SPECIALIZATIONS;
    private static final Boolean OFFER_PREVIOUS_TRAINING_REQURED = Boolean.FALSE;
    private static final String OFFER_OTHER_REQUIREMENTS = "Other Requirements";
    private static final Integer OFFER_MINIMUM_WEEKS = 4;
    private static final Integer OFFER_MAXIMUM_WEEKS = 13;
    private static final DatePeriod OFFER_PERIOD1 = new DatePeriod(INITIAL_DATE, INITIAL_DATE.plusDays(90));
    private static final DatePeriod OFFER_PERIOD2 = new DatePeriod(INITIAL_DATE.plusDays(180), INITIAL_DATE.plusDays(270));
    private static final DatePeriod OFFER_UNAVAILABLE = new DatePeriod(INITIAL_DATE.plusDays(90), INITIAL_DATE.plusDays(180));
    private static final Language OFFER_LANGUAGE1 = Language.ENGLISH;
    private static final LanguageLevel OFFER_LANGUAGE1_LEVEL = LanguageLevel.E;
    private static final BigDecimal OFFER_PAYMENT = new BigDecimal(700);
    private static final PaymentFrequency OFFER_PAYMENT_FREQUENCY = PaymentFrequency.MONTHLY;
    private static final String OFFER_DEDUCTION = "20%";
    private static final BigDecimal OFFER_LIVING_COST = new BigDecimal(300);
    private static final PaymentFrequency OFFER_LIVING_COST_FREQUENCY = PaymentFrequency.DAILY;
    private static final String OFFER_LODGING_BY = "IAESTE";
    private static final BigDecimal OFFER_LODGING_COST = new BigDecimal(250);
    private static final PaymentFrequency OFFER_LODGING_COST_FREQUENCY = PaymentFrequency.WEEKLY;
    private static final Date OFFER_NOMINATION_DEADLINE = INITIAL_DATE.plusDays(-30);
    private static final String OFFER_ADDITIONAL_INFORMATION = "Offer Additional Information";

    static {
        final Set<String> specializations = new HashSet<>(2);
        specializations.add(Specialization.INFORMATION_TECHNOLOGY.toString());
        specializations.add("some custom specialization");
        OFFER_SPECIALIZATIONS = Collections.unmodifiableSet(specializations);
    }

    /**
     * Private Constructor, this is a Utility Class.
     */
    private TestData() {
    }

    // =========================================================================
    // Public methods to fill in test data Object
    // =========================================================================

    public static Offer prepareMinimalOffer(final String refNo, final String employerName) {
        final String countryCode = refNo.substring(0, 2).toUpperCase(IWSConstants.DEFAULT_LOCALE);
        return prepareMinimalOffer(refNo, employerName, countryCode);
    }

    public static Offer prepareMinimalOffer(final String refNo, final String employerName, final String countryCode) {
        final Offer offer = new Offer();

        offer.setRefNo(refNo);
        offer.setOfferType(OFFER_TYPE);
        offer.setExchangeType(OFFER_EXCHANGE_TYPE);
        offer.setEmployer(prepareEmployer(employerName, countryCode));
        offer.setWeeklyHours(OFFER_WEEKLY_HOURS);
        offer.setWorkDescription(OFFER_WORK_DESCRIPTION);
        offer.setTypeOfWork(OFFER_TYPE_OF_WORK);
        offer.setStudyLevels(OFFER_STUDY_LEVELS);
        offer.setFieldOfStudies(OFFER_FIELD_OF_STUDY);
        offer.setMinimumWeeks(OFFER_MINIMUM_WEEKS);
        offer.setMaximumWeeks(OFFER_MAXIMUM_WEEKS);
        offer.setPeriod1(OFFER_PERIOD1);
        offer.setLanguage1(OFFER_LANGUAGE1);
        offer.setLanguage1Level(OFFER_LANGUAGE1_LEVEL);

        return offer;
    }

    public static Offer prepareFullOffer(final String refNo, final String employerName) {
        final Offer offer = prepareMinimalOffer(refNo, employerName);

        offer.setTypeOfWork(OFFER_TYPE_OF_WORK);
        offer.setWeeklyHours(OFFER_WEEKLY_HOURS);
        offer.setDailyHours(OFFER_DAILY_HOURS);
        offer.setWeeklyWorkDays(OFFER_WEEKLY_WORK_DAYS);
        offer.setSpecializations(OFFER_SPECIALIZATIONS);
        offer.setPreviousTrainingRequired(OFFER_PREVIOUS_TRAINING_REQURED);
        offer.setOtherRequirements(OFFER_OTHER_REQUIREMENTS);
        offer.setPeriod2(OFFER_PERIOD2);
        offer.setUnavailable(OFFER_UNAVAILABLE);
        offer.setLanguage1Operator(LanguageOperator.A);
        offer.setLanguage2(Language.FRENCH);
        offer.setLanguage2Level(LanguageLevel.E);
        offer.setLanguage2Operator(LanguageOperator.O);
        offer.setLanguage3(Language.GERMAN);
        offer.setLanguage3Level(LanguageLevel.E);
        offer.setPayment(OFFER_PAYMENT);
        offer.setPaymentFrequency(OFFER_PAYMENT_FREQUENCY);
        offer.setCurrency(offer.getEmployer().getAddress().getCountry().getCurrency());
        offer.setDeduction(OFFER_DEDUCTION);
        offer.setLivingCost(OFFER_LIVING_COST);
        offer.setLivingCostFrequency(OFFER_LIVING_COST_FREQUENCY);
        offer.setLodgingBy(OFFER_LODGING_BY);
        offer.setLodgingCost(OFFER_LODGING_COST);
        offer.setLodgingCostFrequency(OFFER_LODGING_COST_FREQUENCY);
        offer.setNominationDeadline(OFFER_NOMINATION_DEADLINE);
        offer.setAdditionalInformation(OFFER_ADDITIONAL_INFORMATION);

        return offer;
    }

    public static Employer prepareEmployer(final String employerName, final String countryCode) {
        final Employer employer = new Employer();

        employer.setName(employerName);
        employer.setDepartment(EMPLOYER_DEPARTMENT);
        employer.setBusiness(EMPLOYER_BUSINESS);
        employer.setAddress(prepareAddress(countryCode));
        employer.setEmployeesCount(EMPLOYER_EMPLOYEES_COUNT);
        employer.setWebsite(EMPLOYER_WEBSITE);
        employer.setWorkingPlace(EMPLOYER_WORKING_PLACE);
        employer.setCanteen(EMPLOYER_CANTEEN);
        employer.setNearestAirport(EMPLOYER_NEAREAST_AIRPORT);
        employer.setNearestPublicTransport(EMPLOYER_NEAREST_PUBLIC_TRANSPORT);
        employer.setGroup(prepareGroup(countryCode));

        return employer;
    }

    private static Group prepareGroup(final String countryCode) {
        final Group group = new Group();

        group.setGroupType(GroupType.NATIONAL);
        group.setCountry(prepareCountry(countryCode));
        group.setGroupName(group.getCountry().getCountryName());

        return group;
    }

    public static Address prepareAddress(final String countryCode) {
        final Address address = new Address();

        address.setStreet1(ADDRESS_STREET1);
        address.setStreet2(ADDRESS_STREET2);
        address.setPostalCode(ADDRESS_ZIP);
        address.setCity(ADDRESS_CITY);
        address.setState(ADDRESS_STATE);
        address.setCountry(prepareCountry(countryCode));

        return address;
    }

    public static Country prepareCountry(final String countryCode) {
        final String countryId = countryCode.toUpperCase(IWSConstants.DEFAULT_LOCALE);
        final Country country = new Country();
        country.setCountryCode(countryId);

        switch (countryId) {
            case "AT":
                updateCountry(country, Membership.FULL_MEMBER, "Austria", Currency.EUR, 1949);
                break;
            case "DE":
                updateCountry(country, Membership.FULL_MEMBER, "Germany", Currency.EUR, 1950);
                break;
            case "DK":
                updateCountry(country, Membership.FULL_MEMBER, "Denmark", Currency.DKK, 1948);
                break;
            case "GB":
                updateCountry(country, Membership.FULL_MEMBER, "United Kingdom", Currency.GBP, 1948);
                break;
            case "PL":
                updateCountry(country, Membership.FULL_MEMBER, "Poland", Currency.PLN, 1959);
                break;
            case "VN":
                updateCountry(country, Membership.FULL_MEMBER, "Vietnam", Currency.VND, 2016);
                break;
            default:
                throw new IWSException(IWSErrors.ERROR, "Unknown Country.");
        }

        return country;
    }

    private static void updateCountry(final Country country, final Membership membership, final String name, final Currency currency, final Integer memberSince) {
        country.setMembership(membership);
        country.setCountryName(name);
        country.setCurrency(currency);
        country.setMemberSince(memberSince);
    }
}
