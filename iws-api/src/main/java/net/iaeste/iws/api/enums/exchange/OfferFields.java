/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.exchange.OfferFields
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums.exchange;

import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;

import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Set;

/**
 * This enumerated type holds the different fields present in the CSV files,
 * which is either generated by the IWS or can be uploaded to the IWS. There is
 * a couple of fields which are not present in both files.<br />
 *   The published CSV guideline, will contain description of all fields.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlType(name = "offerFields")
public enum OfferFields {

    REF_NO("Ref.No", "setRefNo", true, true, String.class),
    OFFER_TYPE("OfferType", "setOfferType", true, false, OfferType.class),
    EXCHANGE_TYPE("ExchangeType", "setExchangeType", true, false, ExchangeType.class),
    DEADLINE("Deadline", "setNominationDeadline", true, true, Date.class),
    COMMENT("Comment", "setPrivateComment", true, true, String.class),
    EMPLOYER("Employer", "setName", true, true, String.class),
    DEPARTMENT("Department", "setDepartment", true, true, String.class), // Currently unused, should be added
    STREET1("Street1", "setStreet1", true, true, String.class),
    STREET2("Street2", "setStreet2", true, true, String.class),
    POSTBOX("PostBox", "setPobox", true, true, String.class),
    POSTAL_CODE("PostalCode", "setPostalCode", true, true, String.class),
    CITY("City", "setCity", true, true, String.class),
    STATE("State", "setState", true, true, String.class),
    COUNTRY("Country", null, true, true, String.class),
    WEBSITE("Website", "setWebsite", true, true, String.class),
    WORKPLACE("Workplace", "setWorkingPlace", true, true, String.class),
    BUSINESS("Business", "setBusiness", true, true, String.class),
    RESPONSIBLE("Responsible", null, true, true, String.class),
    AIRPORT("Airport", "setNearestAirport", true, true, String.class),
    TRANSPORT("Transport", "setNearestPublicTransport", true, true, String.class),
    EMPLOYEES("Employees", "setEmployeesCount", true, true, String.class),
    HOURS_WEEKLY("HoursWeekly", "setWeeklyHours", true, true, Float.class),
    HOURS_DAILY("HoursDaily", "setDailyHours", true, true, Float.class),
    CANTEEN("Canteen", "setCanteen", true, true, Boolean.class),
    FACULTY("Faculty", "setFieldOfStudies", true, true, Set.class),
    SPECIALIZATION("Specialization", "setSpecializations", true, true, Set.class),
    TRAINING_REQUIRED("TrainingRequired", "setPreviousTrainingRequired", true, true, Boolean.class),
    OTHER_REQUIREMENTS("OtherRequirements", "setOtherRequirements", true, true, String.class),
    WORK_KIND("Workkind", "setWorkDescription", true, true, String.class), // Mapped to Work Description
    WEEKS_MIN("WeeksMin", "setMinimumWeeks", true, true, Integer.class),
    WEEKS_MAX("WeeksMax", "setMaximumWeeks", true, true, Integer.class),
    FROM("From", "setPeriod1", true, true, DatePeriod.class),
    TO("To", "setPeriod1", true, true, DatePeriod.class),
    STUDY_COMPLETED("StudyCompleted", "setStudyLevels", true, true, Set.class),
    STUDY_COMPLETED_BEGINNING("StudyCompleted_Beginning", "setStudyLevels", true, true, Set.class),
    STUDY_COMPLETED_MIDDLE("StudyCompleted_Middle", "setStudyLevels", true, true, Set.class),
    STUDY_COMPLETED_END("StudyCompleted_End", "setStudyLevels", true, true, Set.class),
    WORK_TYPE("WorkType", "setTypeOfWork", true, true, TypeOfWork.class),
    WORK_TYPE_P("WorkType_P", "setTypeOfWork", true, true, TypeOfWork.class),
    WORK_TYPE_R("WorkType_R", "setTypeOfWork", true, true, TypeOfWork.class),
    WORK_TYPE_W("WorkType_W", "setTypeOfWork", true, true, TypeOfWork.class),
    WORK_TYPE_N("WorkType_N", "setTypeOfWork", true, true, TypeOfWork.class),
    LANGUAGE_1("Language1", "setLanguage1", true, true, Language.class),
    LANGUAGE_1_LEVEL("Language1Level", "setLanguage1Level", true, true, LanguageLevel.class),
    LANGUAGE_1_OR("Language1Or", "setLanguage1Operator", true, true, LanguageOperator.class),
    LANGUAGE_2("Language2", "setLanguage2", true, true, Language.class),
    LANGUAGE_2_LEVEL("Language2Level", "setLanguage2Level", true, true, LanguageLevel.class),
    LANGUAGE_2_OR("Language2Or", "setLanguage2Operator", true, true, LanguageOperator.class),
    LANGUAGE_3("Language3", "setLanguage3", true, true, Language.class),
    LANGUAGE_3_LEVEL("Language3Level", "setLanguage3Level", true, true, LanguageLevel.class),
    CURRENCY("Currency", "setCurrency", true, true, Currency.class),
    PAYMENT("Payment", "setPayment", true, true, BigDecimal.class),
    PAYMENT_FREQUENCY("PaymentFrequency", "setPaymentFrequency", true, true, PaymentFrequency.class),
    DEDUCTION("Deduction", "setDeduction", true, true, String.class),
    LODGING("Lodging", "setLodgingBy", true, true, String.class),
    LODGING_COST("LodgingCost", "setLodgingCost", true, true, BigDecimal.class),
    LODGING_COST_FREQUENCY("LodgingCostFrequency", "setLodgingCostFrequency", true, true, PaymentFrequency.class),
    LIVING_COST("LivingCost", "setLivingCost", true, true, BigDecimal.class),
    LIVING_COST_FREQUENCY("LivingCostFrequency", "setLivingCostFrequency", true, true, PaymentFrequency.class),
    NO_HARD_COPIES("NoHardCopies", "setNumberOfHardCopies", true, true, Integer.class),
    STATUS("Status", null, true, true), // Not supported to set this via CSV
    PERIOD_2_FROM("Period2_From", "setPeriod2", true, true, Date.class),
    PERIOD_2_TO("Period2_To", "setPeriod2", true, true, Date.class),
    HOLIDAYS_FROM("Holidays_From", "setUnavailable", true, true, Date.class),
    HOLIDAYS_TO("Holidays_To", "setUnavailable", true, true, Date.class),
    ADDITIONAL_INFO("Additional_Info", "setAdditionalInformation", true, true, String.class),
    SHARED("Shared", null, true, true), // Not supported to set this via CSV
    LAST_MODIFIED("Last modified", null, false, true), // Not supported to set this via CSV
    NS_FIRST_NAME("NS First Name", null, false, true), // Not supported to set this via CSV
    NS_LAST_NAME("NS Last Name", null, false, true); // Not supported to set this via CSV

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String field;
    private final String method;
    private final boolean forDomesticCSVOffer;
    private final boolean forForeignCSVOffer;
    private final Class<?>[] classes;

    OfferFields(final String field, final String method, final boolean forDomesticCSVOffer, final boolean forForeignCSVOffer, final Class<?>... classes) {
        this.field = field;
        this.method = method;
        this.forDomesticCSVOffer = forDomesticCSVOffer;
        this.forForeignCSVOffer = forForeignCSVOffer;
        this.classes = classes;
    }

    public String getField() {
        return field;
    }

    public boolean isForDomesticCSVOffer() {
        return forDomesticCSVOffer;
    }

    public boolean isForForeignCSVOffer() {
        return forForeignCSVOffer;
    }

    public String getMethod() {
        return method;
    }

    public Class<?>[] getArgumentClasses() {
        return classes;
    }
}
