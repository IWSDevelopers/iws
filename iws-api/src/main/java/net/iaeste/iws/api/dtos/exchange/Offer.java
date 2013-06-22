/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Copier;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DateTime;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// ToDo Kim; Review if all fields are present - the Address seems to be missing information, there is no City, Zip or Country added.
// ToDo Kim; Ensure that the tests for this class covers all cases, i.e. that all Strings are checked for length

/**
 * Standard IAESTE Offer.
 *
 * @author Michael Pickelbauer / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection OverlyComplexClass, OverlyLongMethod, CastToConcreteClass, ConstantConditions, BooleanMethodNameMustStartWithQuestion, OverlyComplexBooleanExpression, OverlyComplexMethod, ClassWithTooManyFields
 * @since 1.7
 */
public final class Offer extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The Id of this Offer. If an Offer object is to be updated, then this
     * value must be provided and both the Id and the RefNo must match up,
     * otherwise it is an error.
     */
    private String offerId = null;

    /**
     * Format of reference number is: {@code [country code]-[exchange year]-[identification number]-[additional code (optional)]} <br/>
     * {@code country code} should follow the {@link <a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2">ISO_3166-1 Alpha 2 specification</a>}
     * <p/>
     * Example valid reference numbers: {@code GB-2011-0001-01}, {@code IN-2011-0001-KU}
     * <p/>
     * validations:
     * <ul>
     * <li>required, {@link #validateNotNullableFields}</li>
     * <li>has to match the regular expression: {@code (CC)-\\d{4}-\\d{4}(-[A-Z0-9]{2})?} (where {@code CC} is one of the country codes)</li>
     * </ul>
     */
    private String refNo = null;

    /**
     * validations:
     * <ul>
     * </ul>
     */
    private Date nominationDeadline = null;

    // EmployerInformation information
    /**
     * validations:
     * <ul><li>required, {@link #validateNotNullableFields}</li></ul>
     */
    private String employerName = null;
    private String employerAddress = null;
    private String employerAddress2 = null;
    private String employerBusiness = null;
    private Integer employerEmployeesCount = null;
    private String employerWebsite = null;

    //Student Information
    /**
     * validations:
     * <ul>
     * <li>required, {@link #validateNotNullableFields}</li>
     * <li>from 1 up to net.iaeste.iws.api.constants.exchange.IWSExchangeConstants#MAX_OFFER_FIELDS_OF_STUDY values</li>
     * </ul>
     */
    private Set<FieldOfStudy> fieldOfStudies = EnumSet.noneOf(FieldOfStudy.class);

    /**
     * Most of specializations will be String values of {@code Specialization}
     * enumeration but has to be defined as a Set of Strings because the user
     * should be able to add custom specializations in addition to the
     * predefined ones.
     */
    private Set<String> specializations = new HashSet<>(1);

    /**
     * validations:
     * <ul>
     * <li>required, {@link #validateNotNullableFields}</li>
     * <li>from 1 up to net.iaeste.iws.api.constants.exchange.IWSExchangeConstants#MAX_OFFER_STUDY_LEVELS values</li>
     * </ul>
     */
    private Set<StudyLevel> studyLevels = EnumSet.noneOf(StudyLevel.class);
    private Boolean prevTrainingRequired = null;
    private String otherRequirements = null;

    /**
     * validations:
     * <ul><li>required, {@link #validateNotNullableFields}</li></ul>
     */
    private Language language1 = null;

    /**
     * validations:
     * <ul><li>required, {@link #validateNotNullableFields}</li></ul>
     */
    private LanguageLevel language1Level = null;

    /**
     * #language1Operator and #language2Operator define
     * if all languages are required
     * or if some of them are optional.
     * <p/>
     * To evaluate if student fulfill the requirements
     * we have to check the logical expression:
     * {@code #language1 #language1Operator (#language2 #language2Operator #language3)}.
     * <p/>
     * Priority of the operators doesn't matter because
     * {@code #language2 op #language3} are placed inside braces.
     */
    private LanguageOperator language1Operator = null;
    private Language language2 = null;
    private LanguageLevel language2Level = null;
    private LanguageOperator language2Operator = null;
    private Language language3 = null;
    private LanguageLevel language3Level = null;

    // Work offered
    /**
     * validations:
     * <ul>
     * <li>required, {@link #validateNotNullableFields}</li>
     * <li>length between IWSExchangeConstants#MIN_OFFER_WORK_DESCRIPTION_SIZE
     * up to IWSExchangeConstants#MAX_OFFER_WORK_DESCRIPTION_SIZE</li>
     * </ul>
     */
    private String workDescription = null;
    private TypeOfWork typeOfWork = null;

    /**
     * validations:
     * <ul>
     * <li>required, {@link #validateNotNullableFields}</li>
     * <li>has to be less or equal than #maximumWeeks</li>
     * </ul>
     */
    private Integer minimumWeeks = null;

    /**
     * validations:
     * <ul>
     * <li>required, {@link #validateNotNullableFields}</li>
     * <li>has to be greater or equal than #minimumWeeks</li>
     * </ul>
     */
    private Integer maximumWeeks = null;

    /**
     * validations:
     * <ul>
     * <li>required, {@link #validateNotNullableFields(Map)}</li>
     * <li>{@link #validateDates(Map)}</li>
     * </ul>
     */
    private Date fromDate = null;
    /**
     * validations:
     * <ul>
     * <li>required, {@link #validateNotNullableFields}</li>
     * <li>{@link #validateDates(Map)}</li>
     * </ul>
     */
    private Date toDate = null;
    private Date fromDate2 = null;
    private Date toDate2 = null;

    /**
     * validations:
     * <ul><li>{@link #validateUnavailableDatesOrder(Map)}</li></ul>
     */
    private Date unavailableFrom = null;
    /**
     * validations:
     * <ul><li>{@link #validateUnavailableDatesOrder(Map)} )}</li></ul>
     */
    private Date unavailableTo = null;
    private String workingPlace = null;
    private String nearestAirport = null;
    private String nearestPubTransport = null;

    /**
     * validations:
     * <ul><li>required, {@link #validateNotNullableFields(Map)}</li></ul>
     */
    private Float weeklyHours = null;
    private Float dailyHours = null;
    /* need big numbers, e.g. 1 EUR = 26.435,00 VND */
    private BigDecimal payment = null;
    private Currency currency = null;
    private PaymentFrequency paymentFrequency = null;
    private String deduction = null;

    // Accommodation
    private String lodgingBy = null;
    private BigDecimal lodgingCost = null;
    private PaymentFrequency lodgingCostFrequency = null;
    private BigDecimal livingCost = null;
    private PaymentFrequency livingCostFrequency = null;
    private Boolean canteen = null;

    /**
     * Number of Hard Copies.
     */
    private Integer numberOfHardCopies = null;
    private OfferState status = null;

    /**
     * Date of last modification of the Offer in the database.
     * <p/>
     * Field is read only. All changes made to that field will be discarded on persisting.
     */
    private DateTime modified = null;

    /**
     * Date of creation of the Offer in the database.
     * <p/>
     * Field is read only. All changes made to that field will be discarded on persisting.
     */
    private DateTime created = null;

    /**
     * Empty Constructor, required for some communication frameworks.
     */
    public Offer() {
    }

    /**
     * Copy constructor.
     * <p/>
     * Fields are copied one by one. Correct "cloning" for muttable members is
     * provided by setters.
     *
     * @param offer Offer to copy
     */
    public Offer(final Offer offer) {
        if (offer != null) {
            // No id exist as refNo is unique
            offerId = offer.offerId;
            refNo = offer.refNo;
            employerName = offer.employerName;
            employerAddress = offer.employerAddress;
            employerAddress2 = offer.employerAddress2;
            employerBusiness = offer.employerBusiness;
            employerEmployeesCount = offer.employerEmployeesCount;
            employerWebsite = offer.employerWebsite;
            prevTrainingRequired = offer.prevTrainingRequired;
            otherRequirements = offer.otherRequirements;
            language1 = offer.language1;
            language1Level = offer.language1Level;
            language1Operator = offer.language1Operator;
            language2 = offer.language2;
            language2Level = offer.language2Level;
            language2Operator = offer.language2Operator;
            language3 = offer.language3;
            language3Level = offer.language3Level;
            workDescription = offer.workDescription;
            minimumWeeks = offer.minimumWeeks;
            maximumWeeks = offer.maximumWeeks;
            workingPlace = offer.workingPlace;
            nearestAirport = offer.nearestAirport;
            nearestPubTransport = offer.nearestPubTransport;
            weeklyHours = offer.weeklyHours;
            dailyHours = offer.dailyHours;
            payment = offer.payment;
            currency = offer.currency;
            paymentFrequency = offer.paymentFrequency;
            deduction = offer.deduction;
            lodgingBy = offer.lodgingBy;
            lodgingCost = offer.lodgingCost;
            lodgingCostFrequency = offer.lodgingCostFrequency;
            livingCost = offer.livingCost;
            livingCostFrequency = offer.livingCostFrequency;
            canteen = offer.canteen;
            numberOfHardCopies = offer.numberOfHardCopies;

            nominationDeadline = Copier.copy(offer.nominationDeadline);
            fromDate = Copier.copy(offer.fromDate);
            toDate = Copier.copy(offer.toDate);
            fromDate2 = Copier.copy(offer.fromDate2);
            toDate2 = Copier.copy(offer.toDate2);
            unavailableFrom = Copier.copy(offer.unavailableFrom);
            unavailableTo = Copier.copy(offer.unavailableTo);

            typeOfWork = offer.typeOfWork;
            fieldOfStudies = Copier.copy(offer.fieldOfStudies);
            specializations = Copier.copy(offer.specializations);
            studyLevels = Copier.copy(offer.studyLevels);
            status = offer.status;

            modified = Copier.copy(offer.modified);
            created = Copier.copy(offer.created);
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setofferId(final String offerId) throws IllegalArgumentException {
        if (offerId != null && offerId.length() != 36) {
            throw new IllegalArgumentException("Illegal value for the Id.");
        }

        this.offerId = offerId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setCanteen(final Boolean canteen) {
        this.canteen = canteen;
    }

    public Boolean getCanteen() {
        return canteen;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setDailyHours(final Float dailyHours) {
        this.dailyHours = dailyHours;
    }

    public Float getDailyHours() {
        return dailyHours;
    }

    public void setDeduction(final String deduction) {
        this.deduction = deduction;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setEmployerAddress2(final String employerAddress2) {
        this.employerAddress2 = employerAddress2;
    }

    public String getEmployerAddress2() {
        return employerAddress2;
    }

    public void setEmployerAddress(final String employerAddress) {
        this.employerAddress = employerAddress;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    public void setEmployerBusiness(final String employerBusiness) {
        this.employerBusiness = employerBusiness;
    }

    public String getEmployerBusiness() {
        return employerBusiness;
    }

    public void setEmployerEmployeesCount(final Integer employerEmployeesCount) {
        this.employerEmployeesCount = employerEmployeesCount;
    }

    public Integer getEmployerEmployeesCount() {
        return employerEmployeesCount;
    }

    public void setEmployerName(final String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerWebsite(final String employerWebsite) {
        this.employerWebsite = employerWebsite;
    }

    public String getEmployerWebsite() {
        return employerWebsite;
    }

    public void setFieldOfStudies(final Set<FieldOfStudy> fieldOfStudies) {
        this.fieldOfStudies = Copier.copy(fieldOfStudies);
    }

    public Set<FieldOfStudy> getFieldOfStudies() {
        return Copier.copy(fieldOfStudies);
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate2(final Date fromDate2) {
        this.fromDate2 = fromDate2;
    }

    public Date getFromDate2() {
        return fromDate2;
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

    public void setMaximumWeeks(final Integer maximumWeeks) {
        this.maximumWeeks = maximumWeeks;
    }

    public Integer getMaximumWeeks() {
        return maximumWeeks;
    }

    public void setMinimumWeeks(final Integer minimumWeeks) {
        this.minimumWeeks = minimumWeeks;
    }

    public Integer getMinimumWeeks() {
        return minimumWeeks;
    }

    public void setNearestAirport(final String nearestAirport) {
        this.nearestAirport = nearestAirport;
    }

    public String getNearestAirport() {
        return nearestAirport;
    }

    public void setNearestPubTransport(final String nearestPubTransport) {
        this.nearestPubTransport = nearestPubTransport;
    }

    public String getNearestPubTransport() {
        return nearestPubTransport;
    }

    public void setNominationDeadline(final Date nominationDeadline) {
        this.nominationDeadline = nominationDeadline;
    }

    public Date getNominationDeadline() {
        return nominationDeadline;
    }

    public void setOtherRequirements(final String otherRequirements) {
        this.otherRequirements = otherRequirements;
    }

    public String getOtherRequirements() {
        return otherRequirements;
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

    public void setPrevTrainingRequired(final Boolean prevTrainingRequired) {
        this.prevTrainingRequired = prevTrainingRequired;
    }

    public Boolean getPrevTrainingRequired() {
        return prevTrainingRequired;
    }

    public void setRefNo(final String refNo) {
        if (refNo != null) {
            this.refNo = refNo.toUpperCase(IWSConstants.DEFAULT_LOCALE);
        } else {
            // Some tests are failing, if we don't force a null value!
            this.refNo = null;
        }
    }

    public String getRefNo() {
        return refNo;
    }

    public void setSpecializations(final Set<String> specializations) {
        this.specializations = Copier.copy(specializations);
    }

    public Set<String> getSpecializations() {
        return Copier.copy(specializations);
    }

    public void setStudyLevels(final Set<StudyLevel> studyLevels) {
        this.studyLevels = Copier.copy(studyLevels);
    }

    public Set<StudyLevel> getStudyLevels() {
        return Copier.copy(studyLevels);
    }

    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate2(final Date toDate2) {
        this.toDate2 = toDate2;
    }

    public Date getToDate2() {
        return toDate2;
    }

    public void setTypeOfWork(final TypeOfWork typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public TypeOfWork getTypeOfWork() {
        return typeOfWork;
    }

    public void setWeeklyHours(final Float weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Float getWeeklyHours() {
        return weeklyHours;
    }

    public void setWorkDescription(final String workDescription) {
        this.workDescription = workDescription;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkingPlace(final String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setNumberOfHardCopies(final Integer numberOfHardCopies) {
        this.numberOfHardCopies = numberOfHardCopies;
    }

    public Integer getNumberOfHardCopies() {
        return numberOfHardCopies;
    }

    public void setStatus(final OfferState status) {
        this.status = status;
    }

    public OfferState getStatus() {
        return status;
    }

    /**
     * For internal use only.
     *
     * @param modified DateTime of last modification of the Offer
     */
    public void setModified(final DateTime modified) {
        this.modified = modified;
    }

    public DateTime getModified() {
        return modified;
    }

    /**
     * For internal use only.
     *
     * @param created DateTime of the creation of the Offer
     */
    public void setCreated(final DateTime created) {
        this.created = created;
    }

    public DateTime getCreated() {
        return created;
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        // Notes, considerations about simplifying this Object was made, but
        // turned down since it should be possible to check if two unpersisted
        // Objects are equal
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Offer)) {
            return false;
        }

        final Offer offer = (Offer) obj;

        if (offerId != null ? !offerId.equals(offer.offerId) : offer.offerId != null) {
            return false;
        }
        if (canteen != null ? !canteen.equals(offer.canteen) : offer.canteen != null) {
            return false;
        }
        if (numberOfHardCopies != null ? !numberOfHardCopies.equals(offer.numberOfHardCopies) : offer.numberOfHardCopies != null) {
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
        if (employerAddress != null ? !employerAddress.equals(offer.employerAddress) : offer.employerAddress != null) {
            return false;
        }
        if (employerAddress2 != null ? !employerAddress2.equals(offer.employerAddress2) : offer.employerAddress2 != null) {
            return false;
        }
        if (employerBusiness != null ? !employerBusiness.equals(offer.employerBusiness) : offer.employerBusiness != null) {
            return false;
        }
        if (employerEmployeesCount != null ? !employerEmployeesCount.equals(offer.employerEmployeesCount) : offer.employerEmployeesCount != null) {
            return false;
        }
        if (employerName != null ? !employerName.equals(offer.employerName) : offer.employerName != null) {
            return false;
        }
        if (employerWebsite != null ? !employerWebsite.equals(offer.employerWebsite) : offer.employerWebsite != null) {
            return false;
        }
        if (fieldOfStudies != null ? !fieldOfStudies.equals(offer.fieldOfStudies) : offer.fieldOfStudies != null) {
            return false;
        }
        if (fromDate != null ? !fromDate.equals(offer.fromDate) : offer.fromDate != null) {
            return false;
        }
        if (fromDate2 != null ? !fromDate2.equals(offer.fromDate2) : offer.fromDate2 != null) {
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
        if (nearestAirport != null ? !nearestAirport.equals(offer.nearestAirport) : offer.nearestAirport != null) {
            return false;
        }
        if (nearestPubTransport != null ? !nearestPubTransport.equals(offer.nearestPubTransport) : offer.nearestPubTransport != null) {
            return false;
        }
        if (nominationDeadline != null ? !nominationDeadline.equals(offer.nominationDeadline) : offer.nominationDeadline != null) {
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
        if (prevTrainingRequired != null ? !prevTrainingRequired.equals(offer.prevTrainingRequired) : offer.prevTrainingRequired != null) {
            return false;
        }
        if (refNo != null ? !refNo.equals(offer.refNo) : offer.refNo != null) {
            return false;
        }
        if (specializations != null ? !specializations.equals(offer.specializations) : offer.specializations != null) {
            return false;
        }
        if (studyLevels != null ? !studyLevels.equals(offer.studyLevels) : offer.studyLevels != null) {
            return false;
        }
        if (toDate != null ? !toDate.equals(offer.toDate) : offer.toDate != null) {
            return false;
        }
        if (toDate2 != null ? !toDate2.equals(offer.toDate2) : offer.toDate2 != null) {
            return false;
        }
        if (typeOfWork != offer.typeOfWork) {
            return false;
        }
        if (unavailableFrom != null ? !unavailableFrom.equals(offer.unavailableFrom) : offer.unavailableFrom != null) {
            return false;
        }
        if (unavailableTo != null ? !unavailableTo.equals(offer.unavailableTo) : offer.unavailableTo != null) {
            return false;
        }
        if (weeklyHours != null ? !weeklyHours.equals(offer.weeklyHours) : offer.weeklyHours != null) {
            return false;
        }
        if (workDescription != null ? !workDescription.equals(offer.workDescription) : offer.workDescription != null) {
            return false;
        }
        if (status != offer.status) {
            return false;
        }
        // #modified and #created are not relevant for the equality of the offers.

        return !(workingPlace != null ? !workingPlace.equals(offer.workingPlace) : offer.workingPlace != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (offerId != null ? offerId.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (refNo != null ? refNo.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (nominationDeadline != null ? nominationDeadline.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (employerName != null ? employerName.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (employerAddress != null ? employerAddress.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (employerAddress2 != null ? employerAddress2.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (employerBusiness != null ? employerBusiness.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (employerEmployeesCount != null ? employerEmployeesCount.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (employerWebsite != null ? employerWebsite.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (fieldOfStudies != null ? fieldOfStudies.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (specializations != null ? specializations.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (studyLevels != null ? studyLevels.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (prevTrainingRequired != null ? prevTrainingRequired.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (otherRequirements != null ? otherRequirements.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language1 != null ? language1.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language1Level != null ? language1Level.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language1Operator != null ? language1Operator.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language2 != null ? language2.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language2Level != null ? language2Level.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language2Operator != null ? language2Operator.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language3 != null ? language3.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language3Level != null ? language3Level.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (workDescription != null ? workDescription.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (typeOfWork != null ? typeOfWork.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (minimumWeeks != null ? minimumWeeks.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (maximumWeeks != null ? maximumWeeks.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (fromDate != null ? fromDate.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (toDate != null ? toDate.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (fromDate2 != null ? fromDate2.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (toDate2 != null ? toDate2.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (unavailableFrom != null ? unavailableFrom.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (unavailableTo != null ? unavailableTo.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (workingPlace != null ? workingPlace.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (nearestAirport != null ? nearestAirport.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (nearestPubTransport != null ? nearestPubTransport.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (weeklyHours != null ? weeklyHours.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (dailyHours != null ? dailyHours.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (payment != null ? payment.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (currency != null ? currency.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (paymentFrequency != null ? paymentFrequency.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (deduction != null ? deduction.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (lodgingBy != null ? lodgingBy.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (lodgingCost != null ? lodgingCost.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (lodgingCostFrequency != null ? lodgingCostFrequency.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (livingCost != null ? livingCost.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (livingCostFrequency != null ? livingCostFrequency.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (canteen != null ? canteen.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (numberOfHardCopies != null ? numberOfHardCopies.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (status != null ? status.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // Id is left out of the offer toString value
        return "Offer{" +
                "refNo='" + refNo + '\'' +
                ", nominationDeadline=" + nominationDeadline +
                ", employerName='" + employerName + '\'' +
                ", employerAddress='" + employerAddress + '\'' +
                ", employerAddress2='" + employerAddress2 + '\'' +
                ", employerBusiness='" + employerBusiness + '\'' +
                ", employerEmployeesCount=" + employerEmployeesCount +
                ", employerWebsite='" + employerWebsite + '\'' +
                ", fieldOfStudies=" + fieldOfStudies +
                ", specializations=" + specializations +
                ", studyLevels=" + studyLevels +
                ", prevTrainingRequired=" + prevTrainingRequired +
                ", otherRequirements='" + otherRequirements + '\'' +
                ", language1=" + language1 +
                ", language1Level=" + language1Level +
                ", language1Operator=" + language1Operator +
                ", language2=" + language2 +
                ", language2Level=" + language2Level +
                ", language2Operator=" + language2Operator +
                ", language3=" + language3 +
                ", language3Level=" + language3Level +
                ", workDescription='" + workDescription + '\'' +
                ", typeOfWork=" + typeOfWork +
                ", minimumWeeks=" + minimumWeeks +
                ", maximumWeeks=" + maximumWeeks +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", fromDate2=" + fromDate2 +
                ", toDate2=" + toDate2 +
                ", unavailableFrom=" + unavailableFrom +
                ", unavailableTo=" + unavailableTo +
                ", workingPlace='" + workingPlace + '\'' +
                ", nearestAirport='" + nearestAirport + '\'' +
                ", nearestPubTransport='" + nearestPubTransport + '\'' +
                ", weeklyHours=" + weeklyHours +
                ", dailyHours=" + dailyHours +
                ", payment=" + payment +
                ", currency=" + currency +
                ", paymentFrequency=" + paymentFrequency +
                ", deduction=" + deduction +
                ", lodgingBy='" + lodgingBy + '\'' +
                ", lodgingCost=" + lodgingCost +
                ", lodgingCostFrequency=" + lodgingCostFrequency +
                ", livingCost=" + livingCost +
                ", livingCostFrequency=" + livingCostFrequency +
                ", canteen=" + canteen +
                ", numberOfHardCopies=" + numberOfHardCopies +
                ", status=" + status +
                ", modified=" + modified +
                ", created=" + created +
                '}';
    }

    /**
     * validations:
     * <ul>
     * <li>required fields, {@link #validateNotNullableFields(Map)}</li>
     * <li>refNo format, {@link #validateRefNo(Map)}</li>
     * <li>order and presence of dates, {@link #validateDates(Map)}</li>
     * <li>internship period, {@link #validateNumberOfWeeks(Map)}</li>
     * <li>number of selected Fields of Studies, {@link #validateSizeOfFieldOfStudies(Map)}</li>
     * <li>number of selected Specializations, {@link #validateSizeOfSpecializations(Map)}</li>
     * <li>length of work description, {@link #validateLengthOfWorkDescription(Map)}</li>
     * <li>dependencies: fields required only if other fields are provided, {@link #validateFieldDependencies(Map)}</li>
     * </ul>
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        validateNotNullableFields(validation);
        validateRefNo(validation);
        validateDates(validation);
        validateNumberOfWeeks(validation);
        validateSizeOfFieldOfStudies(validation);
        validateSizeOfSpecializations(validation);
        validateLengthOfWorkDescription(validation);
        validateLengthOfOtherRequirements(validation);
        validateFieldDependencies(validation);
        validateStringLength(validation);

        return validation;
    }

    /**
     * validations:
     * <ul><li>number of selected specialization should be below limit, {@link IWSExchangeConstants#MAX_OFFER_SPECIALIZATIONS}</li></ul>
     *
     * @param validation Map with Error information
     * @return true if size of specializations is valid
     */
    private boolean validateSizeOfSpecializations(final Map<String, String> validation) {
        return isWithinLimits(validation, "specializations", specializations, IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS);
    }

    /**
     * validations:
     * <ul><li>number of selected specialization should be below limit, {@link IWSExchangeConstants#MAX_OFFER_FIELDS_OF_STUDY}</li></ul>
     *
     * @param validation Map with Error information
     * @return true if size of field of studies is valid
     */
    private boolean validateSizeOfFieldOfStudies(final Map<String, String> validation) {
        return isWithinLimits(validation, "fieldOfStudies", fieldOfStudies, IWSExchangeConstants.MAX_OFFER_FIELDS_OF_STUDY);
    }

    /**
     * Checks if minimum and maximum weeks values are correct.
     * <ul>
     * <li>{@link #minimumWeeks} should be greater or equal to {@link IWSExchangeConstants#MIN_OFFER_MINIMUM_WEEKS}</li>
     * <li>{@link #maximumWeeks} should be greater or equal to {@link IWSExchangeConstants#MIN_OFFER_MINIMUM_WEEKS}</li>
     * <li>{@link #maximumWeeks} should be greater or equal to {@link #minimumWeeks}</li>
     * </ul>
     *
     * @param validation Map with Error information
     * @return true if {@code minimumWeeks} and {@code maximumWeeks} are valid
     */
    private boolean validateNumberOfWeeks(final Map<String, String> validation) {
        final boolean check;

        if (maximumWeeks != null) {
            if (maximumWeeks < IWSExchangeConstants.MIN_OFFER_MINIMUM_WEEKS) {
                addError(validation, "maximumWeeks", format("may be greater or equal to %s", IWSExchangeConstants.MIN_OFFER_MINIMUM_WEEKS));
                check = false;
            } else {
                check = isWithinLimits(validation, "minimumWeeks", minimumWeeks, IWSExchangeConstants.MIN_OFFER_MINIMUM_WEEKS, maximumWeeks);
            }
        } else {
            check = false;
        }

        return check;
    }

    /**
     * Checks if length of work description is correct.
     * <ul>
     * <li>length of {@link #workDescription} should be between
     * {@link IWSExchangeConstants#MIN_OFFER_WORK_DESCRIPTION_SIZE}
     * and {@link IWSExchangeConstants#MAX_OFFER_WORK_DESCRIPTION_SIZE}</li>
     * </ul>
     *
     * @param validation Map with Error information
     * @return true if {@link #workDescription} is valid
     */
    private boolean validateLengthOfWorkDescription(final Map<String, String> validation) {
        return isWithinLimits(validation, "workDescription", workDescription, IWSExchangeConstants.MIN_OFFER_WORK_DESCRIPTION_SIZE,
                IWSExchangeConstants.MAX_OFFER_WORK_DESCRIPTION_SIZE);
    }

    /**
     * Checks if length of other requirements (if set) is correct.
     * <ul>
     * <li>length of {@link #otherRequirements} should be between
     * {@link IWSExchangeConstants#MIN_OFFER_OTHER_REQUIREMENTS_SIZE}
     * and {@link IWSExchangeConstants#MAX_OFFER_OTHER_REQUIREMENTS_SIZE}</li>
     * </ul>
     *
     * @param validation Map with Error information
     * @return true if {@link #otherRequirements} is valid
     */
    private boolean validateLengthOfOtherRequirements(final Map<String, String> validation) {
        return otherRequirements == null || isWithinLimits(validation, "otherRequirements", otherRequirements,
                IWSExchangeConstants.MIN_OFFER_OTHER_REQUIREMENTS_SIZE,
                IWSExchangeConstants.MAX_OFFER_OTHER_REQUIREMENTS_SIZE);
    }

    /**
     * Check for the fiels, where there are limitations imposed by the size in
     * the database.
     *
     * @param validation Map with Error information
     */
    private void validateStringLength(final Map<String, String> validation) {
        isNotEmptyOrTooLong(validation, "deduction", deduction, 20);
    }

    /**
     * Checks if field dependencies are fulfilled.
     * If
     * {@code livingCost}, {@code payment} or {@code lodgingCost}
     * is present then the corresponding frequency period
     * ({@code livingCostFrequency}, {@code paymentFrequency}, {@code lodgingCostFrequency})
     * should be present.
     *
     * @param validation Map with Error information
     * @return true if field dependencies are valid
     */
    private boolean validateFieldDependencies(final Map<String, String> validation) {
        boolean check = true;

        if (livingCost != null && livingCostFrequency == null) {
            addError(validation, "livingCostFrequency", "'livingCostFrequency' is required if 'livingCost' is not null");
            check = false;
        }

        if (payment != null && paymentFrequency == null) {
            addError(validation, "paymentFrequency", "'paymentFrequency' is required if 'payment' is not null");
            check = false;
        }

        if (lodgingCost != null && lodgingCostFrequency == null) {
            addError(validation, "lodgingCostFrequency", "'lodgingCostFrequency' is required if 'lodgingCost' is not null");
            check = false;
        }

        return check;
    }

    /**
     * Checks for nulls, empty string and collections in required fields.
     * Required fields are:
     * <ul>
     * <li>refNo</li>
     * <li>employerName</li>
     * <li>weeklyhours</li>
     * <li>fieldOfStudies</li>
     * <li>studyLevels</li>
     * <li>language1</li>
     * <li>language1Level</li>
     * <li>workDescription</li>
     * <li>minimumWeeks</li>
     * <li>maximumWeeks</li>
     * <li>fromDate</li>
     * <li>toDate</li>
     * </ul>
     *
     * @param validation Map with Error information
     * @return true if all required fields are present
     */
    private boolean validateNotNullableFields(final Map<String, String> validation) {
        boolean check = true;

        check &= isNotNullOrEmpty(validation, "refNo", refNo);
        check &= isNotNullOrEmpty(validation, "employerName", employerName);
        check &= isNotNull(validation, "weeklyhours", weeklyHours);
        check &= isNotNullOrEmpty(validation, "fieldOfStudies", fieldOfStudies);
        check &= isNotNullOrEmpty(validation, "studyLevels", studyLevels);
        check &= isNotNull(validation, "language1", language1);
        check &= isNotNull(validation, "language1Level", language1Level);
        check &= isNotNullOrEmpty(validation, "workDescription", workDescription);
        check &= isNotNull(validation, "minimumWeeks", minimumWeeks);
        check &= isNotNull(validation, "maximumWeeks", maximumWeeks);
        check &= isNotNull(validation, "fromDate", fromDate);
        check &= isNotNull(validation, "toDate", toDate);

        return check;
    }

    /**
     * Checks if reference number is valid with #REFNO_FORMAT.
     *
     * @param validation Map with Error information
     * @return true if reference number is valid
     * @see #refNo
     */
    private boolean validateRefNo(final Map<String, String> validation) {
        boolean check = true;

        if ((refNo != null) && !IWSExchangeConstants.REFNO_PATTERN.matcher(refNo).matches()) {
            addError(validation, "refNo", "reference number has incorrect format");
            check = false;
        }

        return check;
    }

    /**
     * verifies dates
     * <p/>
     * <ul>
     * <li>{@code nominationDeadline} must be before {@code fromDate} and {@code fromDate2}</li>
     * <li>{@code fromDate} < {@code toDate}</li>
     * <li>{@code fromDate} and {@code toDate} are required</li>
     * <li>{@code fromDate} has to have an adequate {@code toDate}</li>
     * <li>dates from different groups (1,2) cannot interwise</li>
     * <li>unavailable dates must be inside date group</li>
     * </ul>
     *
     * @param validation Map with Error information
     * @return true if all dates are valid
     */
    private boolean validateDates(final Map<String, String> validation) {
        boolean check = true;

        check &= validateDatesPresence(validation);
        check &= validateDatesOrder(validation);
        check &= validateDatesGroupsOrder(validation);
        check &= validateUnavailableDatesOrder(validation);

        return check;
    }

    /**
     * Validates date fields presence and dependencies.
     *
     * @param validation Map with Error information
     * @return true if all required date fields are provided.
     */
    private boolean validateDatesPresence(final Map<String, String> validation) {
        boolean check = true;

        // no checks for 'from' and 'to' as it is already checkd in #validateNotNullableFields()
        // if 'from2' is present then 'to2' is needed to
        if (fromDate2 != null && toDate2 == null) {
            addError(validation, "toDate2", "is not present");
            check = false;
        }
        if (fromDate2 == null && toDate2 != null) {
            addError(validation, "fromDate2", "is not present");
            check = false;
        }
        // if 'unavailableFrom' is present then 'unavailableTo' is needed to
        if (unavailableFrom != null && unavailableTo == null) {
            addError(validation, "unavailableTo", "is not present");
            check = false;
        }
        if (unavailableFrom == null && unavailableTo != null) {
            addError(validation, "unavailableFrom", "is not present");
            check = false;
        }

        return check;
    }

    /**
     * Validates order of related dates
     * <p/>
     * validations:
     * <ul>
     * <li>{@code fromDate <= toDate}</li>
     * <li>{@code fromDate2 <= toDate2}</li>
     * <li>{@code unavailableFrom <= unavalilableTo}</li>
     * </ul>
     *
     * @param validation Map with Error information
     * @return true if dates order is valid.
     */
    private boolean validateDatesOrder(final Map<String, String> validation) {
        boolean check = true;

        // 'from' date can't be after 'to' date
        if (isAfter(fromDate, toDate)) {
            addError(validation, "fromDate", "should be before toDate");
            check = false;
        }
        if (isAfter(fromDate2, toDate2)) {
            addError(validation, "fromDate2", "should be before toDate2");
            check = false;
        }
        if (isAfter(unavailableFrom, unavailableTo)) {
            addError(validation, "unavailableFrom", "should be before unavailableTo");
            check = false;
        }

        return check;
    }

    private static Boolean isAfter(final Date first, final Date second) {
        return (first != null) && (second != null) && first.isAfter(second);
    }

    /**
     * Checks if date from one group is not inside another group.
     *
     * @param validation Map with Error information
     * @return true if order of date groups is valid.
     */
    private boolean validateDatesGroupsOrder(final Map<String, String> validation) {
        boolean check = true;

        // dates from groups 1 and 2 cannot intertwine
        if (fromDate != null && fromDate2 != null) {
            // to != null and to2 != null is already verified but checking for nulls for safety
            if (toDate == null || toDate2 == null) {
                check = false;
            } else {
                // from < to and from2 < to is already checked
                // check if group 2 is outside group 1
                if (!(fromDate.isAfter(toDate2) || fromDate2.isAfter(toDate))) {
                    addError(validation, "fromDate", "fromDate-toDate and fromDate2-toDate2 periods overlap on each other");
                    addError(validation, "fromDate2", "fromDate-toDate and fromDate2-toDate2 periods overlap on each other");
                    check = false;
                }
            }
        }

        return check;
    }

    /**
     * Unavailable period must be inside one of internship date ranges
     * or between two ranges.
     *
     * @param validation Map with Error information
     * @return true if unavailable dates order is valid.
     */
    private boolean validateUnavailableDatesOrder(final Map<String, String> validation) {
        boolean check = true;
        if (unavailableFrom != null && unavailableTo != null) {
            // unavailable "from" and "to" date must be inside "from" and "to" or "from2" and "to2" dates
            //      or between "to" and "from2" or "to2" and "from" (see #84 for requirements)
            if (unavailableFrom.isBefore(fromDate) && unavailableTo.isAfter(fromDate)) {
                check = false;
            }
            if (unavailableFrom.isAfter(toDate) && unavailableTo.isBefore(toDate)) {
                check = false;
            }
            if (unavailableFrom.isAfter(fromDate) && unavailableFrom.isBefore(toDate) && unavailableTo.isAfter(toDate)) {
                check = false;
            }
            if (fromDate2 != null) {
                if (unavailableFrom.isBefore(fromDate2) && unavailableTo.isAfter(fromDate2)) {
                    check = false;
                }
                if (unavailableFrom.isAfter(toDate2) && unavailableTo.isBefore(toDate2)) {
                    check = false;
                }
                if (unavailableFrom.isAfter(fromDate2) && unavailableFrom.isBefore(toDate2) && unavailableTo.isAfter(toDate2)) {
                    check = false;
                }
            }
            if (!check) {
                addError(validation, "unavailableFrom", "incorrect unavailable period");
                addError(validation, "unavailableTo", "incorrect unavailable period");
            }
        }

        return check;
    }
}
