/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.Offer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
public final class Offer extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String offerId = null;
    private String refNo = null;
    private OfferType offerType = OfferType.OPEN;
    private ExchangeType exchangeType = ExchangeType.COBE;

    private String oldRefNo = null;

    // General Work Description
    private Employer employer = null;
    private String workDescription = null;
    private TypeOfWork typeOfWork = null;
    private Float weeklyHours = null;
    private Float dailyHours = null;
    private Float weeklyWorkDays = null;
    private Set<StudyLevel> studyLevels = EnumSet.noneOf(StudyLevel.class);
    private Set<FieldOfStudy> fieldOfStudies = EnumSet.noneOf(FieldOfStudy.class);
    private Set<String> specializations = new HashSet<>(1);
    private Boolean previousTrainingRequired = null;
    private String otherRequirements = null;

    // DatePeriod for the Offer
    private Integer minimumWeeks = null;
    private Integer maximumWeeks = null;
    private DatePeriod period1 = null;
    private DatePeriod period2 = null;
    private DatePeriod unavailable = null;

    // Language restrictions
    private Language language1 = null;
    private LanguageLevel language1Level = null;
    private LanguageOperator language1Operator = null;
    private Language language2 = null;
    private LanguageLevel language2Level = null;
    private LanguageOperator language2Operator = null;
    private Language language3 = null;
    private LanguageLevel language3Level = null;

    // Payment & Cost information
    private BigDecimal payment = null;
    private PaymentFrequency paymentFrequency = null;
    /* need big numbers, e.g. 1 EUR = 26.435,00 VND */
    private Currency currency = null;
    private String deduction = null;
    private BigDecimal livingCost = null;
    private PaymentFrequency livingCostFrequency = null;
    private String lodgingBy = null;
    private BigDecimal lodgingCost = null;
    private PaymentFrequency lodgingCostFrequency = null;

    // Other things
    private Date nominationDeadline = null;
    private Integer numberOfHardCopies = null;
    private String additionalInformation = null;
    private String privateComment = null;
    private OfferState status = null;
    private DateTime modified = null;
    private DateTime created = null;

    // Additional information
    private String nsFirstname = null;
    private String nsLastname = null;

    private DateTime shared = null;

    // custom flag used by the FE to hide irrelevant offers
    private Boolean hidden = false;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Offer() {
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
     * Sets the Id of the Offer. The Id is generated by the IWS, upon initial
     * persisting of the Offer.<br />
     *   The method will throw an {@code IllegalArgumentException} if the id is
     * not valid.
     *
     * @param offerId Offer Id
     * @throws IllegalArgumentException if not valid
     * @see  AbstractVerification#UUID_FORMAT
     */
    public void setOfferId(final String offerId) throws IllegalArgumentException {
        ensureValidId("offerId", offerId);
        this.offerId = offerId;
    }

    public String getOfferId() {
        return offerId;
    }

    /**
     * Sets the Offer reference number. The number must be unique, and follow
     * a certain format.<br />
     *   The method will throw an {@code IllegalArgumentException} if the
     * Reference Number is not valid, i.e. is either null or doesn't follow the
     * allowed format.
     *
     * @param refNo Offer Reference Number
     * @throws IllegalArgumentException if not valid, i.e. null or not following the format
     * @see IWSExchangeConstants#REFNO_FORMAT
     */
    public void setRefNo(final String refNo) throws IllegalArgumentException {
        ensureNotNullAndValidRefno("refNo", refNo);
        this.refNo = refNo;
    }

    public String getRefNo() {
        return refNo;
    }

    /**
     * Returns the Printable or Displayable version of the Reference Number. This
     * is the version of the Reference Number together with Type of Offer.
     *
     * @return Reference Number + Offer Type
     */
    public String printableRefNo() {
        return refNo + offerType.getType();
    }

    /**
     * Sets the Type of Offer, meaning if this Offer is either Reserved, Limited
     * or Open. The type is closely linked together with the Reference Number
     * @param offerType
     * @throws IllegalArgumentException
     */
    public void setOfferType(final OfferType offerType) throws IllegalArgumentException {
        ensureNotNull("offerType", offerType);
        this.offerType = offerType;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setExchangeType(final ExchangeType exchangeType) throws IllegalArgumentException {
        ensureNotNull("offerType", offerType);
        ensureNotNullAndContains("exchangeType", exchangeType, offerType.getExchangeTypes());
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
     * Sets the Offer Employer. The Employer must be defined, i.e. it cannot be
     * a null value.<br />
     *   The method will throw an {@code IllegalArgumentException} if the
     * Employer is not valid, i.e. if the Object is either null or not a valid
     * Employer Object.
     *
     * @param employer Offer Employer
     * @throws IllegalArgumentException if not valid, i.e. null or not verifiable
     */
    public void setEmployer(final Employer employer) throws IllegalArgumentException {
        //ensureNotNullAndVerifiable("employer", employer);
        ensureNotNull("employer", employer);
        this.employer = new Employer(employer);
    }

    public Employer getEmployer() {
        return new Employer(employer);
    }

    /**
     * Sets the Offer Work Description. The field may be null, but if it is
     * defined, then the length may not exceed 1000 characters.<br />
     *   The method will thrown an {@code IllegalArgumentException} if the value
     * is too long, the length may not exceed 3000 characters.
     *
     * @param workDescription Offer Work Description
     * @throws IllegalArgumentException if the length is too long
     */
    public void setWorkDescription(final String workDescription) throws IllegalArgumentException {
        ensureNotTooLong("workDescription", workDescription, 3000);
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

    /**
     * Sets the Weekly Hours expected by the Employer.<br />
     *   The method will throw an {@code IllegalArgumentException} if the Weekly
     * Hours is a null value.
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
     * @param dailyHours Offer Weekly Work Days
     */
    public void setWeeklyWorkDays(final Float weeklyWorkDays) {
        this.weeklyWorkDays = weeklyWorkDays;
    }

    public Float getWeeklyWorkDays() {
        return weeklyWorkDays;
    }

    public void setStudyLevels(final Set<StudyLevel> studyLevels) throws IllegalArgumentException {
        ensureNotNull("studyLevels", studyLevels);
        ensureNotTooLong("studyLevels", studyLevels.toString(), 25);
        this.studyLevels = studyLevels;
    }

    public Set<StudyLevel> getStudyLevels() {
        return studyLevels;
    }

    /**
     * Sets the Offer Field of Study. The field is mandatory and may contain
     * between 1 (one) and {@link IWSExchangeConstants#MAX_OFFER_FIELDS_OF_STUDY}
     * Field of Study.<br />
     *   The method will throw an {@code IllegalArgumentException} if the Field
     * of Study is either null or too numerous.
     *
     * @param fieldOfStudies Offer Field of Study
     * @throws IllegalArgumentException if value is invalid
     * @see IWSExchangeConstants#MAX_OFFER_FIELDS_OF_STUDY
     */
    public void setFieldOfStudies(final Set<FieldOfStudy> fieldOfStudies) throws IllegalArgumentException {
        ensureNotNullOrTooLong("fieldOfStudies", fieldOfStudies, IWSExchangeConstants.MAX_OFFER_FIELDS_OF_STUDY);
        this.fieldOfStudies = fieldOfStudies;
    }

    public Set<FieldOfStudy> getFieldOfStudies() {
        return fieldOfStudies;
    }

    /**
     * Sets the Offer Specializations. The field is mandatory and may contain
     * between 1 (one) and {@link IWSExchangeConstants#MAX_OFFER_SPECIALIZATIONS}
     * Specializations.<br />
     *   The method will throw an {@code IllegalArgumentException} if the
     * Specializations is either null or too numerous.
     *
     * @param specializations Offer Specializations
     * @throws IllegalArgumentException if value is invalid
     * @see IWSExchangeConstants#MAX_OFFER_SPECIALIZATIONS
     */
    public void setSpecializations(final Set<String> specializations) throws IllegalArgumentException {
        ensureNotNullOrTooLong("specializations", specializations, IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS);
        this.specializations = specializations;
    }

    public Set<String> getSpecializations() {
        return specializations;
    }

    public void setPreviousTrainingRequired(final Boolean previousTrainingRequired) {
        this.previousTrainingRequired = previousTrainingRequired;
    }

    public Boolean getPreviousTrainingRequired() {
        return previousTrainingRequired;
    }

    public void setOtherRequirements(final String otherRequirements) throws IllegalArgumentException {
        ensureNotTooLong("otherRequirements", otherRequirements, 4000);
        this.otherRequirements = otherRequirements;
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public void setMinimumWeeks(final Integer minimumWeeks) throws IllegalArgumentException {
        ensureNotNullAndMinimum("minimumWeeks", minimumWeeks, IWSExchangeConstants.MIN_OFFER_MINIMUM_WEEKS);
        this.minimumWeeks = minimumWeeks;
    }

    public Integer getMinimumWeeks() {
        return minimumWeeks;
    }

    public void setMaximumWeeks(final Integer maximumWeeks) throws IllegalArgumentException {
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
    public void setPeriod1(final DatePeriod period1) throws IllegalArgumentException {
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

    /**
     * Sets who organizes the Lodging. The value may not exceed the max limit of
     * 255 characters or an IllegalArgument Exception is thrown.
     *
     * @param lodgingBy Lodging is organized by
     * @throws IllegalArgumentException if longer than 255 characters
     */
    public void setLodgingBy(final String lodgingBy) {
        ensureNotTooLong("lodgingBy", lodgingBy, 255);
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

    /**
     * Sets the Additional Information for an Offer. The value may nor exceed
     * 3000 characters, or an IllegalArgiment Exception is thrown.
     *
     * @param additionalInformation Additional Information
     * @throws IllegalArgumentException if field is longer than 3000 characters
     */
    public void setAdditionalInformation(final String additionalInformation) throws IllegalArgumentException {
        ensureNotTooLong("additionalInformation", additionalInformation, 3000);
        this.additionalInformation = additionalInformation;
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
    public void setPrivateComment(final String privateComment) throws IllegalArgumentException {
        ensureNotTooLong("privateComment", privateComment, 1000);
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
    public void setShared(DateTime shared) {
        this.shared = shared;
    }

    public DateTime getShared() {
        return shared;
    }

    public void setHidden(boolean hidden) {
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
        isNotNull(validation, "offerType", offerType);
        // The Exchange Type cannot be null and we also need to verify the
        // content, however as it is dependent on the Offer Type, we must add
        // a null check here
        if (offerType != null) {
            isNotNullAndContains(validation, "exchangeType", exchangeType, offerType.getExchangeTypes());
        } else {
            isNotNull(validation, "exchangeType", exchangeType);
        }

        // We need to ensure that the Employer is verifiable also!
        isNotNullAndVerifiable(validation, "employer", employer);
        isNotNull(validation, "employer", employer);
        isNotNull(validation, "weeklyHours", weeklyHours);
        isNotNull(validation, "workDescription", workDescription);
        isNotNull(validation, "studyLevels", studyLevels);
        isNotNull(validation, "fieldOfStudies", fieldOfStudies);
        isNotNull(validation, "period1", period1);
        isNotNull(validation, "minimumWeeks", minimumWeeks);
        isNotNull(validation, "maximumWeeks", maximumWeeks);
        isNotNull(validation, "language1", language1);
        isNotNull(validation, "language1Level", language1Level);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Offer)) {
            return false;
        }

        final Offer offer = (Offer) obj;

        if (additionalInformation != null ? !additionalInformation.equals(offer.additionalInformation) : offer.additionalInformation != null) {
            return false;
        }
        if (created != null ? !created.equals(offer.created) : offer.created != null) {
            return false;
        }
        if (currency != offer.currency) {
            return false;
        }
        if (dailyHours != null ? !dailyHours.equals(offer.dailyHours) : offer.dailyHours != null) {
            return false;
        }
        if (deduction != null ? !deduction.equals(offer.deduction) : offer.deduction != null) {
            return false;
        }
        if (employer != null ? !employer.equals(offer.employer) : offer.employer != null) {
            return false;
        }
        if (exchangeType != offer.exchangeType) {
            return false;
        }
        if (fieldOfStudies != null ? !fieldOfStudies.equals(offer.fieldOfStudies) : offer.fieldOfStudies != null) {
            return false;
        }
        if (language1 != offer.language1) {
            return false;
        }
        if (language1Level != offer.language1Level) {
            return false;
        }
        if (language1Operator != offer.language1Operator) {
            return false;
        }
        if (language2 != offer.language2) {
            return false;
        }
        if (language2Level != offer.language2Level) {
            return false;
        }
        if (language2Operator != offer.language2Operator) {
            return false;
        }
        if (language3 != offer.language3) {
            return false;
        }
        if (language3Level != offer.language3Level) {
            return false;
        }
        if (livingCost != null ? !(livingCost.compareTo(offer.livingCost) == 0) : offer.livingCost != null) {
            return false;
        }
        if (livingCostFrequency != offer.livingCostFrequency) {
            return false;
        }
        if (lodgingBy != null ? !lodgingBy.equals(offer.lodgingBy) : offer.lodgingBy != null) {
            return false;
        }
        if (lodgingCost != null ? !(lodgingCost.compareTo(offer.lodgingCost) == 0) : offer.lodgingCost != null) {
            return false;
        }
        if (lodgingCostFrequency != offer.lodgingCostFrequency) {
            return false;
        }
        if (maximumWeeks != null ? !maximumWeeks.equals(offer.maximumWeeks) : offer.maximumWeeks != null) {
            return false;
        }
        if (minimumWeeks != null ? !minimumWeeks.equals(offer.minimumWeeks) : offer.minimumWeeks != null) {
            return false;
        }
        if (modified != null ? !modified.equals(offer.modified) : offer.modified != null) {
            return false;
        }
        if (nominationDeadline != null ? !nominationDeadline.equals(offer.nominationDeadline) : offer.nominationDeadline != null) {
            return false;
        }
        if (nsFirstname != null ? !nsFirstname.equals(offer.nsFirstname) : offer.nsFirstname != null) {
            return false;
        }
        if (nsLastname != null ? !nsLastname.equals(offer.nsLastname) : offer.nsLastname != null) {
            return false;
        }
        if (numberOfHardCopies != null ? !numberOfHardCopies.equals(offer.numberOfHardCopies) : offer.numberOfHardCopies != null) {
            return false;
        }
        if (offerId != null ? !offerId.equals(offer.offerId) : offer.offerId != null) {
            return false;
        }
        if (offerType != offer.offerType) {
            return false;
        }
        if (oldRefNo != null ? !oldRefNo.equals(offer.oldRefNo) : offer.oldRefNo != null) {
            return false;
        }
        if (otherRequirements != null ? !otherRequirements.equals(offer.otherRequirements) : offer.otherRequirements != null) {
            return false;
        }
        if (payment != null ? !(payment.compareTo(offer.payment) == 0) : offer.payment != null) {
            return false;
        }
        if (paymentFrequency != offer.paymentFrequency) {
            return false;
        }
        if (period1 != null ? !period1.equals(offer.period1) : offer.period1 != null) {
            return false;
        }
        if (period2 != null ? !period2.equals(offer.period2) : offer.period2 != null) {
            return false;
        }
        if (previousTrainingRequired != null ? !previousTrainingRequired.equals(offer.previousTrainingRequired) : offer.previousTrainingRequired != null) {
            return false;
        }
        if (privateComment != null ? !privateComment.equals(offer.privateComment) : offer.privateComment != null) {
            return false;
        }
        if (refNo != null ? !refNo.equals(offer.refNo) : offer.refNo != null) {
            return false;
        }
        if (shared != null ? !shared.equals(offer.shared) : offer.shared != null) {
            return false;
        }
        if (specializations != null ? !specializations.equals(offer.specializations) : offer.specializations != null) {
            return false;
        }
        if (status != offer.status) {
            return false;
        }
        if (studyLevels != null ? !studyLevels.equals(offer.studyLevels) : offer.studyLevels != null) {
            return false;
        }
        if (typeOfWork != offer.typeOfWork) {
            return false;
        }
        if (unavailable != null ? !unavailable.equals(offer.unavailable) : offer.unavailable != null) {
            return false;
        }
        if (weeklyHours != null ? !weeklyHours.equals(offer.weeklyHours) : offer.weeklyHours != null) {
            return false;
        }
        if (weeklyWorkDays != null ? !weeklyWorkDays.equals(offer.weeklyWorkDays) : offer.weeklyWorkDays != null) {
            return false;
        }
        return !(workDescription != null ? !workDescription.equals(offer.workDescription) : offer.workDescription != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((offerId != null) ? offerId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (refNo != null ? refNo.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (offerType != null ? offerType.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (exchangeType != null ? exchangeType.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (oldRefNo != null ? oldRefNo.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (employer != null ? employer.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (workDescription != null ? workDescription.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (typeOfWork != null ? typeOfWork.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (weeklyHours != null ? weeklyHours.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (dailyHours != null ? dailyHours.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (weeklyWorkDays != null ? weeklyWorkDays.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (studyLevels != null ? studyLevels.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (fieldOfStudies != null ? fieldOfStudies.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (specializations != null ? specializations.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (previousTrainingRequired != null ? previousTrainingRequired.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (otherRequirements != null ? otherRequirements.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (minimumWeeks != null ? minimumWeeks.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (maximumWeeks != null ? maximumWeeks.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (period1 != null ? period1.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (period2 != null ? period2.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (unavailable != null ? unavailable.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language1 != null ? language1.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language1Level != null ? language1Level.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language1Operator != null ? language1Operator.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language2 != null ? language2.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language2Level != null ? language2Level.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language2Operator != null ? language2Operator.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language3 != null ? language3.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language3Level != null ? language3Level.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (payment != null ? payment.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (paymentFrequency != null ? paymentFrequency.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (currency != null ? currency.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (deduction != null ? deduction.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (livingCost != null ? livingCost.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (livingCostFrequency != null ? livingCostFrequency.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (lodgingBy != null ? lodgingBy.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (lodgingCost != null ? lodgingCost.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (lodgingCostFrequency != null ? lodgingCostFrequency.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (nominationDeadline != null ? nominationDeadline.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (numberOfHardCopies != null ? numberOfHardCopies.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (additionalInformation != null ? additionalInformation.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (privateComment != null ? privateComment.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (status != null ? status.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (modified != null ? modified.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (created != null ? created.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (nsFirstname != null ? nsFirstname.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (nsLastname != null ? nsLastname.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (shared != null ? shared.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (hidden != null ? hidden.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Offer{" +
                "offerId='" + offerId + '\'' +
                ", refNo='" + refNo + '\'' +
                ", offerType='" + offerType + '\'' +
                ", exchangeType='" + exchangeType + '\'' +
                ", oldRefNo='" + oldRefNo + '\'' +
                ", employer=" + employer +
                ", workDescription='" + workDescription + '\'' +
                ", typeOfWork=" + typeOfWork +
                ", weeklyHours=" + weeklyHours +
                ", dailyHours=" + dailyHours +
                ", weeklyWorkDays=" + weeklyWorkDays +
                ", studyLevels=" + studyLevels +
                ", fieldOfStudies=" + fieldOfStudies +
                ", specializations=" + specializations +
                ", previousTrainingRequired=" + previousTrainingRequired +
                ", otherRequirements='" + otherRequirements + '\'' +
                ", minimumWeeks=" + minimumWeeks +
                ", maximumWeeks=" + maximumWeeks +
                ", period1=" + period1 +
                ", period2=" + period2 +
                ", unavailable=" + unavailable +
                ", language1=" + language1 +
                ", language1Level=" + language1Level +
                ", language1Operator=" + language1Operator +
                ", language2=" + language2 +
                ", language2Level=" + language2Level +
                ", language2Operator=" + language2Operator +
                ", language3=" + language3 +
                ", language3Level=" + language3Level +
                ", payment=" + payment +
                ", paymentFrequency=" + paymentFrequency +
                ", currency=" + currency +
                ", deduction='" + deduction + '\'' +
                ", livingCost=" + livingCost +
                ", livingCostFrequency=" + livingCostFrequency +
                ", lodgingBy='" + lodgingBy + '\'' +
                ", lodgingCost=" + lodgingCost +
                ", lodgingCostFrequency=" + lodgingCostFrequency +
                ", nominationDeadline=" + nominationDeadline +
                ", numberOfHardCopies=" + numberOfHardCopies +
                ", additionalInformation='" + additionalInformation + '\'' +
                ", privateComment='" + privateComment + '\'' +
                ", status=" + status +
                ", modified=" + modified +
                ", created=" + created +
                ", nsFirstname='" + nsFirstname + '\'' +
                ", nsLastname='" + nsLastname + '\'' +
                ", shared=" + shared +
                ", hidden=" + hidden +
                '}';
    }
}
