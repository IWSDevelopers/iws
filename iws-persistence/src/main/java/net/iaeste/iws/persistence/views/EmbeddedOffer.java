/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.EmbeddedOffer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.views;

import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.ExchangeType;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.OfferType;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Embeddable
public class EmbeddedOffer {

    @Column(name = "offer_external_id", insertable = false, updatable = false)
    private String externalId = null;

    @Column(name = "offer_ref_no", insertable = false, updatable = false)
    private String refNo = null;

    @Column(name = "offer_old_ref_no", insertable = false, updatable = false)
    private String oldRefNo = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_offer_type", insertable = false, updatable = false)
    private OfferType offerType = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_exchange_type", insertable = false, updatable = false)
    private ExchangeType exchangeType = null;

    @Column(name = "offer_work_description", insertable = false, updatable = false)
    private String workDescription = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_work_type", insertable = false, updatable = false)
    private TypeOfWork typeOfWork = null;

    @Column(name = "offer_weekly_hours", insertable = false, updatable = false)
    private Float weeklyHours = null;

    @Column(name = "offer_daily_hours", insertable = false, updatable = false)
    private Float dailyHours = null;

    @Column(name = "offer_work_days_per_week", insertable = false, updatable = false)
    private Float weeklyWorkDays = null;

    @Column(name = "offer_study_levels", insertable = false, updatable = false)
    private String studyLevels = null;

    @Column(name = "offer_study_fields", insertable = false, updatable = false)
    private String fieldOfStudies = null;

    @Column(name = "offer_specializations", insertable = false, updatable = false)
    private String specializations = null;

    @Column(name = "offer_prev_training_req", insertable = false, updatable = false)
    private Boolean prevTrainingRequired = null;

    @Column(name = "offer_other_requirements", insertable = false, updatable = false)
    private String otherRequirements = null;

    @Column(name = "offer_min_weeks", insertable = false, updatable = false)
    private Integer minimumWeeks = null;

    @Column(name = "offer_max_weeks", insertable = false, updatable = false)
    private Integer maximumWeeks = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "offer_from_date", insertable = false, updatable = false)
    private Date fromDate = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "offer_to_date", insertable = false, updatable = false)
    private Date toDate = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "offer_from_date_2", insertable = false, updatable = false)
    private Date fromDate2 = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "offer_to_date_2", insertable = false, updatable = false)
    private Date toDate2 = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "offer_unavailable_from", insertable = false, updatable = false)
    private Date unavailableFrom = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "offer_unavailable_to", insertable = false, updatable = false)
    private Date unavailableTo = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_language_1", insertable = false, updatable = false)
    private Language language1 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_language_1_level", insertable = false, updatable = false)
    private LanguageLevel language1Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_language_1_op", insertable = false, updatable = false)
    private LanguageOperator language1Operator = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_language_2", insertable = false, updatable = false)
    private Language language2 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_language_2_level", insertable = false, updatable = false)
    private LanguageLevel language2Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_language_2_op", insertable = false, updatable = false)
    private LanguageOperator language2Operator = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_language_3", insertable = false, updatable = false)
    private Language language3 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_language_3_level", insertable = false, updatable = false)
    private LanguageLevel language3Level = null;

    @Column(name = "offer_payment", insertable = false, updatable = false)
    private BigDecimal payment = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_payment_frequency", insertable = false, updatable = false)
    private PaymentFrequency paymentFrequency = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_currency", insertable = false, updatable = false)
    private Currency currency = null;

    @Column(name = "offer_deduction", insertable = false, updatable = false)
    private String deduction = null;

    @Column(name = "offer_living_cost", insertable = false, updatable = false)
    private BigDecimal livingCost = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_living_cost_frequency", insertable = false, updatable = false)
    private PaymentFrequency livingCostFrequency = null;

    @Column(name = "offer_lodging_by", insertable = false, updatable = false)
    private String lodgingBy = null;

    @Column(name = "offer_lodging_cost", insertable = false, updatable = false)
    private BigDecimal lodgingCost = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_lodging_cost_frequency", insertable = false, updatable = false)
    private PaymentFrequency lodgingCostFrequency = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "offer_nomination_deadline", insertable = false, updatable = false)
    private Date nominationDeadline = null;

    @Column(name = "offer_number_of_hard_copies", insertable = false, updatable = false)
    private Integer numberOfHardCopies = null;

    @Column(name = "offer_additional_information", insertable = false, updatable = false)
    private String additionalInformation = null;

    @Column(name = "offer_private_comment", insertable = false, updatable = false)
    private String privateComment = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_status", insertable = false, updatable = false)
    private OfferState status = OfferState.NEW;

    @Column(name = "offer_modified", insertable = false, updatable = false)
    private Date modified = null;

    @Column(name = "offer_created", insertable = false, updatable = false)
    private Date created = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setRefNo(final String refNo) {
        this.refNo = refNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setOfferType(final OfferType offerType) {
        this.offerType = offerType;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setExchangeType(final ExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public void setOldRefNo(final String oldRefNo) {
        this.oldRefNo = oldRefNo;
    }

    public String getOldRefNo() {
        return oldRefNo;
    }

    public void setWorkDescription(final String workDescription) {
        this.workDescription = workDescription;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setTypeOfWork(final TypeOfWork typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public TypeOfWork getTypeOfWork() {
        return typeOfWork;
    }

    public void setStudyLevels(final String studyLevels) {
        this.studyLevels = studyLevels;
    }

    public void setWeeklyHours(final Float weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Float getWeeklyHours() {
        return weeklyHours;
    }

    public void setDailyHours(final Float dailyHours) {
        this.dailyHours = dailyHours;
    }

    public Float getDailyHours() {
        return dailyHours;
    }

    public void setWeeklyWorkDays(final Float weeklyWorkDays) {
        this.weeklyWorkDays = weeklyWorkDays;
    }

    public Float getWeeklyWorkDays() {
        return weeklyWorkDays;
    }

    public String getStudyLevels() {
        return studyLevels;
    }

    public void setFieldOfStudies(final String fieldOfStudies) {
        this.fieldOfStudies = fieldOfStudies;
    }

    public String getFieldOfStudies() {
        return fieldOfStudies;
    }

    public void setSpecializations(final String specializations) {
        this.specializations = specializations;
    }

    public String getSpecializations() {
        return specializations;
    }

    public void setPrevTrainingRequired(final Boolean prevTrainingRequired) {
        this.prevTrainingRequired = prevTrainingRequired;
    }

    public Boolean getPrevTrainingRequired() {
        return prevTrainingRequired;
    }

    public void setOtherRequirements(final String otherRequirements) {
        this.otherRequirements = otherRequirements;
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public void setMinimumWeeks(final Integer minimumWeeks) {
        this.minimumWeeks = minimumWeeks;
    }

    public Integer getMinimumWeeks() {
        return minimumWeeks;
    }

    public void setMaximumWeeks(final Integer maximumWeeks) {
        this.maximumWeeks = maximumWeeks;
    }

    public Integer getMaximumWeeks() {
        return maximumWeeks;
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setFromDate2(final Date fromDate2) {
        this.fromDate2 = fromDate2;
    }

    public Date getFromDate2() {
        return fromDate2;
    }

    public void setToDate2(final Date toDate2) {
        this.toDate2 = toDate2;
    }

    public Date getToDate2() {
        return toDate2;
    }

    public void setUnavailableFrom(final Date unavailableFrom) {
        this.unavailableFrom = unavailableFrom;
    }

    public Date getUnavailableFrom() {
        return unavailableFrom;
    }

    public void setUnavailableTo(final Date unavailableTo) {
        this.unavailableTo = unavailableTo;
    }

    public Date getUnavailableTo() {
        return unavailableTo;
    }

    public void setLanguage1(final Language language1) {
        this.language1 = language1;
    }

    public Language getLanguage1() {
        return language1;
    }

    public void setLanguage1Level(final LanguageLevel language1Level) {
        this.language1Level = language1Level;
    }

    public LanguageLevel getLanguage1Level() {
        return language1Level;
    }

    public void setLanguage1Operator(final LanguageOperator language1Operator) {
        this.language1Operator = language1Operator;
    }

    public LanguageOperator getLanguage1Operator() {
        return language1Operator;
    }

    public void setLanguage2(final Language language2) {
        this.language2 = language2;
    }

    public Language getLanguage2() {
        return language2;
    }

    public void setLanguage2Level(final LanguageLevel language2Level) {
        this.language2Level = language2Level;
    }

    public LanguageLevel getLanguage2Level() {
        return language2Level;
    }

    public void setLanguage2Operator(final LanguageOperator language2Operator) {
        this.language2Operator = language2Operator;
    }

    public LanguageOperator getLanguage2Operator() {
        return language2Operator;
    }

    public void setLanguage3(final Language language3) {
        this.language3 = language3;
    }

    public Language getLanguage3() {
        return language3;
    }

    public void setLanguage3Level(final LanguageLevel language3Level) {
        this.language3Level = language3Level;
    }

    public LanguageLevel getLanguage3Level() {
        return language3Level;
    }

    public void setPayment(final BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPaymentFrequency(final PaymentFrequency paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public PaymentFrequency getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setDeduction(final String deduction) {
        this.deduction = deduction;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setLivingCost(final BigDecimal livingCost) {
        this.livingCost = livingCost;
    }

    public BigDecimal getLivingCost() {
        return livingCost;
    }

    public void setLivingCostFrequency(final PaymentFrequency livingCostFrequency) {
        this.livingCostFrequency = livingCostFrequency;
    }

    public PaymentFrequency getLivingCostFrequency() {
        return livingCostFrequency;
    }

    public void setLodgingBy(final String lodgingBy) {
        this.lodgingBy = lodgingBy;
    }

    public String getLodgingBy() {
        return lodgingBy;
    }

    public void setLodgingCost(final BigDecimal lodgingCost) {
        this.lodgingCost = lodgingCost;
    }

    public BigDecimal getLodgingCost() {
        return lodgingCost;
    }

    public void setLodgingCostFrequency(final PaymentFrequency lodgingCostFrequency) {
        this.lodgingCostFrequency = lodgingCostFrequency;
    }

    public PaymentFrequency getLodgingCostFrequency() {
        return lodgingCostFrequency;
    }

    public void setNominationDeadline(final Date nominationDeadline) {
        this.nominationDeadline = nominationDeadline;
    }

    public Date getNominationDeadline() {
        return nominationDeadline;
    }

    public void setNumberOfHardCopies(final Integer numberOfHardCopies) {
        this.numberOfHardCopies = numberOfHardCopies;
    }

    public Integer getNumberOfHardCopies() {
        return numberOfHardCopies;
    }

    public void setAdditionalInformation(final String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setPrivateComment(final String privateComment) {
        this.privateComment = privateComment;
    }

    public String getPrivateComment() {
        return privateComment;
    }

    public void setStatus(final OfferState status) {
        this.status = status;
    }

    public OfferState getStatus() {
        return status;
    }

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }
}
