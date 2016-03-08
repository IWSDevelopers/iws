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
package net.iaeste.iws.api.dtos.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.exchange.IWSExchangeConstants;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.ExchangeType;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.OfferType;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.api.util.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Standard IAESTE Offer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "offer", propOrder = { "offerId", "refNo", "offerType", "exchangeType", "oldRefNo", "employer", "workDescription", "typeOfWork", "weeklyHours", "dailyHours", "weeklyWorkDays", "studyLevels", "fieldOfStudies", "specializations", "previousTrainingRequired", "otherRequirements", "minimumWeeks", "maximumWeeks", "period1", "period2", "unavailable", "language1", "language1Level", "language1Operator", "language2", "language2Level", "language2Operator", "language3", "language3Level", "payment", "paymentFrequency", "currency", "deduction", "livingCost", "livingCostFrequency", "lodgingBy", "lodgingCost", "lodgingCostFrequency", "nominationDeadline", "numberOfHardCopies", "additionalInformation", "privateComment", "status", "modified", "created", "nsFirstname", "nsLastname", "shared", "hidden" })
public final class Offer extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private static final String FIELD_EMPLOYER = "employer";
    private static final String FIELD_OFFER_TYPE = "offerType";
    private static final String FIELD_EXCHANGE_TYPE = "exchangeType";
    private static final String FIELD_STUDY_LEVELS = "studyLevels";
    private static final String FIELD_FIELD_OF_STUDIES = "fieldOfStudies";

    @XmlElement(required = true, nillable = true)  private String offerId = null;
    @XmlElement(required = true, nillable = false) private String refNo = null;
    @XmlElement(required = true, nillable = false) private OfferType offerType = OfferType.OPEN;
    // Defaulting to IW, as COBE is causing problems for Reserved Offers
    @XmlElement(required = true, nillable = true)  private ExchangeType exchangeType = ExchangeType.IW;

    @XmlElement(required = true, nillable = true)  private String oldRefNo = null;

    // General Work Description
    @XmlElement(required = true, nillable = false) private Employer employer = null;
    @XmlElement(required = true, nillable = false) private String workDescription = null;
    @XmlElement(required = true, nillable = true)  private TypeOfWork typeOfWork = null;
    @XmlElement(required = true, nillable = false) private Float weeklyHours = null;
    @XmlElement(required = true, nillable = true)  private Float dailyHours = null;
    @XmlElement(required = true, nillable = true)  private Float weeklyWorkDays = null;
    @XmlElement(required = true, nillable = false) private Set<StudyLevel> studyLevels = EnumSet.noneOf(StudyLevel.class);
    @XmlElement(required = true, nillable = true)  private Set<FieldOfStudy> fieldOfStudies = EnumSet.noneOf(FieldOfStudy.class);
    @XmlElement(required = true, nillable = true)  private Set<String> specializations = new HashSet<>(1);
    @XmlElement(required = true, nillable = true)  private Boolean previousTrainingRequired = null;
    @XmlElement(required = true, nillable = true)  private String otherRequirements = null;

    // DatePeriod for the Offer
    @XmlElement(required = true, nillable = false) private Integer minimumWeeks = null;
    @XmlElement(required = true, nillable = false) private Integer maximumWeeks = null;
    @XmlElement(required = true, nillable = false) private DatePeriod period1 = null;
    @XmlElement(required = true, nillable = true)  private DatePeriod period2 = null;
    @XmlElement(required = true, nillable = true)  private DatePeriod unavailable = null;

    // Language restrictions
    @XmlElement(required = true, nillable = false) private Language language1 = null;
    @XmlElement(required = true, nillable = false) private LanguageLevel language1Level = null;
    @XmlElement(required = true, nillable = true)  private LanguageOperator language1Operator = null;
    @XmlElement(required = true, nillable = true)  private Language language2 = null;
    @XmlElement(required = true, nillable = true)  private LanguageLevel language2Level = null;
    @XmlElement(required = true, nillable = true)  private LanguageOperator language2Operator = null;
    @XmlElement(required = true, nillable = true)  private Language language3 = null;
    @XmlElement(required = true, nillable = true)  private LanguageLevel language3Level = null;

    // Payment & Cost information
    @XmlElement(required = true, nillable = true)  private BigDecimal payment = null;
    @XmlElement(required = true, nillable = true)  private PaymentFrequency paymentFrequency = null;
    /* need big numbers, e.g. 1 EUR = 26.435,00 VND */
    @XmlElement(required = true, nillable = true)  private Currency currency = null;
    @XmlElement(required = true, nillable = true)  private String deduction = null;
    @XmlElement(required = true, nillable = true)  private BigDecimal livingCost = null;
    @XmlElement(required = true, nillable = true)  private PaymentFrequency livingCostFrequency = null;
    @XmlElement(required = true, nillable = true)  private String lodgingBy = null;
    @XmlElement(required = true, nillable = true)  private BigDecimal lodgingCost = null;
    @XmlElement(required = true, nillable = true)  private PaymentFrequency lodgingCostFrequency = null;

    // Other things
    @XmlElement(required = true, nillable = true)  private Date nominationDeadline = null;
    @XmlElement(required = true, nillable = true)  private Integer numberOfHardCopies = null;
    @XmlElement(required = true, nillable = true)  private String additionalInformation = null;
    @XmlElement(required = true, nillable = true)  private String privateComment = null;
    @XmlElement(required = true, nillable = true)  private OfferState status = null;
    @XmlElement(required = true, nillable = true)  private DateTime modified = null;
    @XmlElement(required = true, nillable = true)  private DateTime created = null;

    // Additional information
    @XmlElement(required = true, nillable = true)  private String nsFirstname = null;
    @XmlElement(required = true, nillable = true)  private String nsLastname = null;

    @XmlElement(required = true, nillable = true)  private DateTime shared = null;

    // custom flag used by the FE to hide irrelevant offers
    @XmlElement(required = true, nillable = true)  private Boolean hidden = false;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Offer() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Copy constructor.
     *
     * @param offer Offer Object to copy
     */
    public Offer(final Offer offer) {
        if (offer != null) {
            offerId = offer.offerId;
            refNo = offer.refNo;
            offerType = offer.offerType;
            exchangeType = offer.exchangeType;
            employer = new Employer(offer.employer);
            workDescription = offer.workDescription;
            weeklyHours = offer.weeklyHours;
            dailyHours = offer.dailyHours;
            weeklyWorkDays = offer.weeklyWorkDays;
            typeOfWork = offer.typeOfWork;
            studyLevels = offer.studyLevels;
            fieldOfStudies = offer.fieldOfStudies;
            specializations = offer.specializations;
            previousTrainingRequired = offer.previousTrainingRequired;
            otherRequirements = offer.otherRequirements;
            minimumWeeks = offer.minimumWeeks;
            maximumWeeks = offer.maximumWeeks;
            period1 = new DatePeriod(offer.period1);
            period2 = new DatePeriod(offer.period2);
            unavailable = new DatePeriod(offer.unavailable);
            language1 = offer.language1;
            language1Level = offer.language1Level;
            language1Operator = offer.language1Operator;
            language2 = offer.language2;
            language2Level = offer.language2Level;
            language2Operator = offer.language2Operator;
            language3 = offer.language3;
            language3Level = offer.language3Level;
            payment = offer.payment;
            paymentFrequency = offer.paymentFrequency;
            currency = offer.currency;
            deduction = offer.deduction;
            livingCost = offer.livingCost;
            livingCostFrequency = offer.livingCostFrequency;
            lodgingBy = offer.lodgingBy;
            lodgingCost = offer.lodgingCost;
            lodgingCostFrequency = offer.lodgingCostFrequency;
            nominationDeadline = offer.nominationDeadline;
            numberOfHardCopies = offer.numberOfHardCopies;
            additionalInformation = offer.additionalInformation;
            privateComment = offer.privateComment;
            status = offer.status;
            modified = offer.modified;
            created = offer.created;
            nsFirstname = offer.nsFirstname;
            nsLastname = offer.nsLastname;
            shared = offer.shared;
            hidden = offer.hidden;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Id of the Offer. The Id is generated by the IWS, upon initial
     * persisting of the Offer.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the id is
     * not valid.</p>
     *
     * @param offerId Offer Id
     * @throws IllegalArgumentException if not valid
     * @see  AbstractVerification#UUID_FORMAT
     */
    public void setOfferId(final String offerId) {
        ensureValidId("offerId", offerId);
        this.offerId = offerId;
    }

    public String getOfferId() {
        return offerId;
    }

    /**
     * <p>Sets the Offer reference number. The number must be unique, and follow
     * a certain format.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * Reference Number is not valid, i.e. is either null or doesn't follow the
     * allowed format.</p>
     *
     * @param refNo Offer Reference Number
     * @throws IllegalArgumentException if not valid, i.e. null or not following the format
     * @see IWSExchangeConstants#REFNO_FORMAT
     */
    public void setRefNo(final String refNo) {
        ensureNotNullAndValidRefno("refNo", refNo);
        this.refNo = refNo;
    }

    public String getRefNo() {
        return refNo;
    }

    /**
     * Returns the Printable or Displayable version of the Reference Number.
     * This is the version of the Reference Number together with Type of Offer.
     *
     * @return Reference Number + Offer Type
     */
    public String printableRefNo() {
        return refNo + offerType.getType();
    }

    /**
     * Retrieves the Exchange Year for the given Offer. Note, this function
     * only works on persisted Offers, as non-persisted Offers have not yet
     * been verified.
     *
     * @return Exchange Year of the given, persisted, Offer.
     */
    public Integer getExchangeYear() {
        return Integer.valueOf(refNo.substring(3,7));
    }

    /**
     * Sets the Type of Offer, meaning if this Offer is either Reserved, Limited
     * or Open. The type is closely linked together with the Reference Number.
     *
     * @param offerType Type of Offer
     * @throws IllegalArgumentException in set to null
     */
    public void setOfferType(final OfferType offerType) {
        ensureNotNull(FIELD_OFFER_TYPE, offerType);
        this.offerType = offerType;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setExchangeType(final ExchangeType exchangeType) {
        // We cannot set the ExchangeType, if there is no OfferType!
        ensureNotNull(FIELD_OFFER_TYPE, offerType);
        ensureNotNullAndContains(FIELD_EXCHANGE_TYPE, exchangeType, offerType.getExchangeTypes());
        this.exchangeType = exchangeType;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    /**
     * Sets the IW3 Offer refNo.
     * The oldRefNo once set after migration cannot be changed,
     * any changes of this field made to DTO will be ignored during
     * persiting the entity.
     *
     * @param oldRefNo old Offer Reference Number
     */
    public void setOldRefNo(final String oldRefNo) {
        ensureNotTooLong("oldRefNo", oldRefNo, 50);
        this.oldRefNo = oldRefNo;
    }

    public String getOldRefNo() {
        return oldRefNo;
    }

    /**
     * <p>Sets the Offer Employer. The Employer must be defined, i.e. it cannot
     * be a null value.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * Employer is not valid, i.e. if the Object is either null or not a valid
     * Employer Object.</p>
     *
     * @param employer Offer Employer
     * @throws IllegalArgumentException if not valid, i.e. null or not verifiable
     */
    public void setEmployer(final Employer employer) {
        ensureNotNull(FIELD_EMPLOYER, employer);
        this.employer = new Employer(employer);
    }

    public Employer getEmployer() {
        return new Employer(employer);
    }

    /**
     * Sets the Offer Work Description. The field may be null, but if it is
     * defined, then the length may not exceed 3000 characters, if the field is
     * longer than 3.000 characters, then an {@code IllegalArgumentException}
     * will be thrown.
     *
     * @param workDescription Offer Work Description
     * @throws IllegalArgumentException if the length is too long
     * @see IWSExchangeConstants#MAX_OFFER_WORK_DESCRIPTION_SIZE
     */
    public void setWorkDescription(final String workDescription) {
        ensureNotTooLong("workDescription", workDescription, IWSExchangeConstants.MAX_OFFER_WORK_DESCRIPTION_SIZE);
        this.workDescription = sanitize(workDescription);
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

    /**
     * <p>Sets the Weekly Hours expected by the Employer.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * Weekly Hours is a null value.</p>
     *
     * @param weeklyHours Offer Weekly Hours
     */
    public void setWeeklyHours(final Float weeklyHours) {
        ensureNotNull("weeklyHours", weeklyHours);
        this.weeklyHours = weeklyHours;
    }

    public Float getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the Daily Hours expected by the Employer.
     *
     * @param dailyHours Offer Daily Hours
     */
    public void setDailyHours(final Float dailyHours) {
        this.dailyHours = dailyHours;
    }

    public Float getDailyHours() {
        return dailyHours;
    }

    /**
     * Sets the number of work days expected by the Employer.
     *
     * @param weeklyWorkDays Offer Weekly Work Days
     */
    public void setWeeklyWorkDays(final Float weeklyWorkDays) {
        this.weeklyWorkDays = weeklyWorkDays;
    }

    public Float getWeeklyWorkDays() {
        return weeklyWorkDays;
    }

    public void setStudyLevels(final Set<StudyLevel> studyLevels) {
        ensureNotNull(FIELD_STUDY_LEVELS, studyLevels);
        ensureNotTooLong(FIELD_STUDY_LEVELS, studyLevels.toString(), 25);
        ensureNotContaining(FIELD_STUDY_LEVELS, studyLevels, IWSExchangeConstants.SET_DELIMITER);
        this.studyLevels = immutableSet(studyLevels);
    }

    public Set<StudyLevel> getStudyLevels() {
        return immutableSet(studyLevels);
    }

    /**
     * <p>Sets the Offer Field of Study. The field is mandatory and may contain
     * between 1 (one) and {@link IWSExchangeConstants#MAX_OFFER_FIELDS_OF_STUDY}
     * Field of Study.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the Field
     * of Study is either null or too numerous.</p>
     *
     * @param fieldOfStudies Offer Field of Study
     * @throws IllegalArgumentException if value is invalid
     * @see IWSExchangeConstants#MAX_OFFER_FIELDS_OF_STUDY
     */
    public void setFieldOfStudies(final Set<FieldOfStudy> fieldOfStudies) {
        ensureNotNullOrTooLong(FIELD_FIELD_OF_STUDIES, fieldOfStudies, IWSExchangeConstants.MAX_OFFER_FIELDS_OF_STUDY);
        ensureNotContaining(FIELD_FIELD_OF_STUDIES, fieldOfStudies, IWSExchangeConstants.SET_DELIMITER);
        this.fieldOfStudies = immutableSet(fieldOfStudies);
    }

    public Set<FieldOfStudy> getFieldOfStudies() {
        return immutableSet(fieldOfStudies);
    }

    /**
     * <p>Sets the Offer Specializations. The field is mandatory and may contain
     * between 1 (one) and {@link IWSExchangeConstants#MAX_OFFER_SPECIALIZATIONS}
     * Specializations.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the
     * Specializations is either null or too numerous.</p>
     *
     * @param specializations Offer Specializations
     * @throws IllegalArgumentException if value is invalid
     * @see IWSExchangeConstants#MAX_OFFER_SPECIALIZATIONS
     */
    public void setSpecializations(final Set<String> specializations) {
        ensureNotNullOrTooLong("specializations", specializations, IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS);
        ensureNotContaining("specializations", specializations, IWSExchangeConstants.SET_DELIMITER);
        this.specializations = immutableSet(sanitize(specializations));
    }

    public Set<String> getSpecializations() {
        return immutableSet(specializations);
    }

    public void setPreviousTrainingRequired(final Boolean previousTrainingRequired) {
        this.previousTrainingRequired = previousTrainingRequired;
    }

    public Boolean getPreviousTrainingRequired() {
        return previousTrainingRequired;
    }

    public void setOtherRequirements(final String otherRequirements) {
        ensureNotTooLong("otherRequirements", otherRequirements, IWSExchangeConstants.MAX_OFFER_OTHER_REQUIREMENTS_SIZE);
        this.otherRequirements = sanitize(otherRequirements);
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public void setMinimumWeeks(final Integer minimumWeeks) {
        ensureNotNullAndMinimum("minimumWeeks", minimumWeeks, IWSExchangeConstants.MIN_OFFER_MINIMUM_WEEKS);
        this.minimumWeeks = minimumWeeks;
    }

    public Integer getMinimumWeeks() {
        return minimumWeeks;
    }

    public void setMaximumWeeks(final Integer maximumWeeks) {
        ensureNotNullAndMinimum("maximumWeeks", maximumWeeks, IWSExchangeConstants.MIN_OFFER_MINIMUM_WEEKS);
        this.maximumWeeks = maximumWeeks;
    }

    public Integer getMaximumWeeks() {
        return maximumWeeks;
    }

    /**
     * The primary period for the Offer must be defined, i.e. it cannot be null.
     * If set to null, then the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param period1 Primary Period for this Offer
     * @throws IllegalArgumentException if value is null
     */
    public void setPeriod1(final DatePeriod period1) {
        ensureNotNull("period1", period1);
        this.period1 = new DatePeriod(period1);
    }

    public DatePeriod getPeriod1() {
        return new DatePeriod(period1);
    }

    public void setPeriod2(final DatePeriod period2) {
        this.period2 = new DatePeriod(period2);
    }

    public DatePeriod getPeriod2() {
        return new DatePeriod(period2);
    }

    public void setUnavailable(final DatePeriod unavailable) {
        this.unavailable = new DatePeriod(unavailable);
    }

    public DatePeriod getUnavailable() {
        return new DatePeriod(unavailable);
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

    /**
     * Sets the Deduction allowed. The value may not exceed the max limit of 50
     * characters or an IllegalArgument Exception is thrown.
     *
     * @param deduction Deduction
     * @throws IllegalArgumentException if longer than 50 characters
     */
    public void setDeduction(final String deduction) {
        ensureNotTooLong("deduction", deduction, 50);
        this.deduction = sanitize(deduction);
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

    /**
     * Sets who organizes the Lodging. The value may not exceed the max limit of
     * 255 characters or an IllegalArgument Exception is thrown.
     *
     * @param lodgingBy Lodging is organized by
     * @throws IllegalArgumentException if longer than 255 characters
     */
    public void setLodgingBy(final String lodgingBy) {
        ensureNotTooLong("lodgingBy", lodgingBy, 255);
        this.lodgingBy = sanitize(lodgingBy);
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

    /**
     * Sets the Additional Information for an Offer. The value may nor exceed
     * 3000 characters, or an IllegalArgiment Exception is thrown.
     *
     * @param additionalInformation Additional Information
     * @throws IllegalArgumentException if field is longer than 3000 characters
     */
    public void setAdditionalInformation(final String additionalInformation) {
        ensureNotTooLong("additionalInformation", additionalInformation, 3000);
        this.additionalInformation = sanitize(additionalInformation);
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * Sets the Private Comment for an Offer. The value may nor exceed 1000
     * characters, or an IllegalArgiment Exception is thrown.
     *
     * @param privateComment Private Comment
     * @throws IllegalArgumentException if field is longer than 1000 characters
     */
    public void setPrivateComment(final String privateComment) {
        ensureNotTooLong("privateComment", privateComment, 1000);
        this.privateComment = sanitize(privateComment);
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

    /**
     * Sets the Offer latest modification DateTime. Note, this field is
     * controlled by the IWS, and cannot be altered by users.
     *
     * @param modified DateTime of latest modification
     */
    public void setModified(final DateTime modified) {
        this.modified = modified;
    }

    public DateTime getModified() {
        return modified;
    }

    /**
     * Sets the Offer Creation DateTime. Note, this field is controlled by the
     * IWS, and cannot be altered by users.
     *
     * @param created Offer Creation DateTime
     */
    public void setCreated(final DateTime created) {
        this.created = created;
    }

    public DateTime getCreated() {
        return created;
    }

    /**
     * Sets the National Secretary for this Offer (from the National Group).
     * Note, this field is controlled by the IWS and cannot be altered via this
     * Object.
     *
     * @param nsFirstname NS Firstname
     */
    public void setNsFirstname(final String nsFirstname) {
        this.nsFirstname = nsFirstname;
    }

    public String getNsFirstname() {
        return nsFirstname;
    }

    /**
     * Sets the National Secretary for this Offer (from the National Group).
     * Note, this field is controlled by the IWS and cannot be altered via this
     * Object.
     *
     * @param nsLastname NS Lastname
     */
    public void setNsLastname(final String nsLastname) {
        this.nsLastname = nsLastname;
    }

    public String getNsLastname() {
        return nsLastname;
    }

    /**
     * Sets the Offer Sharing DateTime. Note, this field is controlled by the
     * IWS, and cannot be altered by users.
     *
     * @param shared Offer Sharing DateTime
     */
    public void setShared(final DateTime shared) {
        this.shared = shared;
    }

    public DateTime getShared() {
        return shared;
    }

    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        // These checks match those from the Database, remaining are implicit
        // filled by the IWS Logic as part of the processing of the Offer
        isNotNull(validation, "refNo", refNo);
        isNotNull(validation, FIELD_OFFER_TYPE, offerType);
        // The Exchange Type cannot be null and we also need to verify the
        // content, however as it is dependent on the Offer Type, we must add
        // a null check here
        if (offerType != null) {
            isNotNullAndContains(validation, FIELD_EXCHANGE_TYPE, exchangeType, offerType.getExchangeTypes());
        } else {
            isNotNull(validation, FIELD_EXCHANGE_TYPE, exchangeType);
        }

        // We need to ensure that the Employer is verifiable also!
        isNotNullAndVerifiable(validation, FIELD_EMPLOYER, employer);
        isNotNull(validation, "workDescription", workDescription);
        isNotNull(validation, "weeklyHours", weeklyHours);
        isNotNull(validation, FIELD_STUDY_LEVELS, studyLevels);
        isNotNull(validation, FIELD_FIELD_OF_STUDIES, fieldOfStudies);
        isNotNull(validation, "minimumWeeks", minimumWeeks);
        isNotNull(validation, "maximumWeeks", maximumWeeks);
        isNotNull(validation, "period1", period1);
        isNotNull(validation, "language1", language1);
        isNotNull(validation, "language1Level", language1Level);

        return validation;
    }
}
