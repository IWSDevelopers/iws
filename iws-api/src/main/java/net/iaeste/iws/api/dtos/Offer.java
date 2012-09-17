/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - Offer
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

import static net.iaeste.iws.api.utils.CheckVerification.addEmptyErrorToCollection;
import static net.iaeste.iws.api.utils.CheckVerification.addNullErrorToCollection;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSExchangeConstants;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.LanguageOperator;
import net.iaeste.iws.api.enums.PaymentFrequency;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.enums.TypeOfWork;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.Verifiable;
import net.iaeste.iws.api.utils.Copier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Standard IAESTE Offer.
 *
 * @author Michael Pickelbauer / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection CastToConcreteClass, OverlyLongMethod, OverlyComplexMethod
 * @since 1.7
 */
public final class Offer implements Verifiable {

    /**
     * {@link IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private static final String refNoFormat = "(%s)-\\d{4}-\\d{4}(-[A-Z0-9]{2})?"; // %s - country codes

    /**
     * Empty Constructor, required for some communication frameworks.
     */
    public Offer() {
    }

    /**
     * Copy constructor.
     * <p/>
     * Fields are copied one by one. Correct "cloning" for muttable members is provided by setters.
     *
     * @param offer Offer to copy
     */
    public Offer(final Offer offer) {
        if (offer != null) {
            this.id = offer.id;
            this.refNo = offer.refNo;
            this.employerName = offer.employerName;
            this.employerAddress = offer.employerAddress;
            this.employerAddress2 = offer.employerAddress2;
            this.employerBusiness = offer.employerBusiness;
            this.employerEmployeesCount = offer.employerEmployeesCount;
            this.employerWebsite = offer.employerWebsite;
            this.prevTrainingRequired = offer.prevTrainingRequired;
            this.otherRequirements = offer.otherRequirements;
            this.language1 = offer.language1;
            this.language1Level = offer.language1Level;
            this.language1Operator = offer.language1Operator;
            this.language2 = offer.language2;
            this.language2Level = offer.language2Level;
            this.language2Operator = offer.language2Operator;
            this.language3 = offer.language3;
            this.language3Level = offer.language3Level;
            this.workDescription = offer.workDescription;
            this.minimumWeeks = offer.minimumWeeks;
            this.maximumWeeks = offer.maximumWeeks;
            this.workingPlace = offer.workingPlace;
            this.nearestAirport = offer.nearestAirport;
            this.nearestPubTransport = offer.nearestPubTransport;
            this.weeklyHours = offer.weeklyHours;
            this.dailyHours = offer.dailyHours;
            this.payment = offer.payment;
            this.currency = offer.currency;
            this.paymentFrequency = offer.paymentFrequency;
            this.deduction = offer.deduction;
            this.lodgingBy = offer.lodgingBy;
            this.lodgingCost = offer.lodgingCost;
            this.lodgingCostFrequency = offer.lodgingCostFrequency;
            this.livingCost = offer.livingCost;
            this.livingCostFrequency = offer.livingCostFrequency;
            this.canteen = offer.canteen;

            this.nominationDeadline = Copier.copy(offer.nominationDeadline);
            this.fromDate = Copier.copy(offer.fromDate);
            this.toDate = Copier.copy(offer.toDate);
            this.fromDate2 = Copier.copy(offer.fromDate2);
            this.toDate2 = Copier.copy(offer.toDate2);
            this.unavailableFrom = Copier.copy(offer.unavailableFrom);
            this.unavailableTo = Copier.copy(offer.unavailableTo);

            this.typeOfWork = offer.typeOfWork;
            this.fieldOfStudies = Copier.copy(offer.fieldOfStudies);
            this.specializations = Copier.copy(offer.specializations);
            this.studyLevels = Copier.copy(offer.studyLevels);
        }
    }

    private Long id;
    /**
     * not null
     */
    private String refNo;
    private Date nominationDeadline;

    // Employer information
    /**
     * not null
     */
    private String employerName;
    private String employerAddress;
    private String employerAddress2;
    private String employerBusiness;
    private Integer employerEmployeesCount;
    private String employerWebsite;

    //Student Information
    /**
     * not null
     */
    private Set<FieldOfStudy> fieldOfStudies = EnumSet.noneOf(FieldOfStudy.class);
    /**
     * Has to be defined as a List of Strings because
     * the user should be able to add custom
     * specializations in addition to the predefined ones.
     */
    private Set<String> specializations = new HashSet<>();
    /**
     * not null
     */
    private Set<StudyLevel> studyLevels = EnumSet.noneOf(StudyLevel.class);
    private Boolean prevTrainingRequired;
    private String otherRequirements;
    /**
     * not null
     */
    private Language language1;
    /**
     * not null
     */
    private LanguageLevel language1Level;
    private LanguageOperator language1Operator;
    private Language language2;
    private LanguageLevel language2Level;
    private LanguageOperator language2Operator;
    private Language language3;
    private LanguageLevel language3Level;

    // Work offered
    /**
     * not null
     */
    private String workDescription;
    private TypeOfWork typeOfWork;
    /**
     * not null
     */
    private Integer minimumWeeks;
    /**
     * not null
     */
    private Integer maximumWeeks;
    /**
     * not null
     */
    private Date fromDate;
    private Date toDate;
    /**
     * not null
     */
    private Date fromDate2;
    private Date toDate2;
    private Date unavailableFrom;
    private Date unavailableTo;
    private String workingPlace;
    private String nearestAirport;
    private String nearestPubTransport;
    /**
     * not null
     */
    private Float weeklyHours;
    private Float dailyHours;
    /**
     * need big numbers, e.g. 1 EUR = 26.435,00 VND
     */
    private BigDecimal payment;
    private Currency currency;
    private PaymentFrequency paymentFrequency;
    private Integer deduction;

    // Accommodation
    private String lodgingBy;
    private BigDecimal lodgingCost;
    private PaymentFrequency lodgingCostFrequency;
    private BigDecimal livingCost;
    private PaymentFrequency livingCostFrequency;
    private Boolean canteen;

    public Boolean getCanteen() {
        return canteen;
    }

    public void setCanteen(final Boolean canteen) {
        this.canteen = canteen;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public Float getDailyHours() {
        return dailyHours;
    }

    public void setDailyHours(final Float dailyHours) {
        this.dailyHours = dailyHours;
    }

    public Integer getDeduction() {
        return deduction;
    }

    public void setDeduction(final Integer deduction) {
        this.deduction = deduction;
    }

    public String getEmployerAddress2() {
        return employerAddress2;
    }

    public void setEmployerAddress2(final String employerAddress2) {
        this.employerAddress2 = employerAddress2;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    public void setEmployerAddress(final String employerAddress) {
        this.employerAddress = employerAddress;
    }

    public String getEmployerBusiness() {
        return employerBusiness;
    }

    public void setEmployerBusiness(final String employerBusiness) {
        this.employerBusiness = employerBusiness;
    }

    public Integer getEmployerEmployeesCount() {
        return employerEmployeesCount;
    }

    public void setEmployerEmployeesCount(final Integer employerEmployeesCount) {
        this.employerEmployeesCount = employerEmployeesCount;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(final String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerWebsite() {
        return employerWebsite;
    }

    public void setEmployerWebsite(final String employerWebsite) {
        this.employerWebsite = employerWebsite;
    }

    public Set<FieldOfStudy> getFieldOfStudies() {
        return Copier.copy(fieldOfStudies);
    }

    public void setFieldOfStudies(final Set<FieldOfStudy> fieldOfStudies) {
        this.fieldOfStudies = Copier.copy(fieldOfStudies);
    }

    public Date getFromDate2() {
        return Copier.copy(fromDate2);
    }

    public void setFromDate2(final Date fromDate) {
        this.fromDate2 = Copier.copy(fromDate);
        this.fromDate2 = Copier.copy(fromDate);
    }

    public Date getFromDate() {
        return Copier.copy(fromDate);
    }

    public void setFromDate(final Date fromDate2) {
        this.fromDate = Copier.copy(fromDate2);
    }

    public Date getUnavailableFrom() {
        return Copier.copy(unavailableFrom);
    }

    public void setUnavailableFrom(final Date unavailableFrom) {
        this.unavailableFrom = Copier.copy(unavailableFrom);
    }

    public Date getUnavailableTo() {
        return Copier.copy(unavailableTo);
    }

    public void setUnavailableTo(final Date unavailableTo) {
        this.unavailableTo = Copier.copy(unavailableTo);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Language getLanguage1() {
        return language1;
    }

    public void setLanguage1(final Language language1) {
        this.language1 = language1;
    }

    public LanguageLevel getLanguage1Level() {
        return language1Level;
    }

    public void setLanguage1Level(final LanguageLevel language1Level) {
        this.language1Level = language1Level;
    }

    public LanguageOperator getLanguage1Operator() {
        return language1Operator;
    }

    public void setLanguage1Operator(final LanguageOperator language1Operator) {
        this.language1Operator = language1Operator;
    }

    public Language getLanguage2() {
        return language2;
    }

    public void setLanguage2(final Language language2) {
        this.language2 = language2;
    }

    public LanguageLevel getLanguage2Level() {
        return language2Level;
    }

    public void setLanguage2Level(final LanguageLevel language2Level) {
        this.language2Level = language2Level;
    }

    public LanguageOperator getLanguage2Operator() {
        return language2Operator;
    }

    public void setLanguage2Operator(final LanguageOperator language2Operator) {
        this.language2Operator = language2Operator;
    }

    public Language getLanguage3() {
        return language3;
    }

    public void setLanguage3(final Language language3) {
        this.language3 = language3;
    }

    public LanguageLevel getLanguage3Level() {
        return language3Level;
    }

    public void setLanguage3Level(final LanguageLevel language3Level) {
        this.language3Level = language3Level;
    }

    public BigDecimal getLivingCost() {
        return livingCost;
    }

    public void setLivingCost(final BigDecimal livingCost) {
        this.livingCost = livingCost;
    }

    public PaymentFrequency getLivingCostFrequency() {
        return livingCostFrequency;
    }

    public void setLivingCostFrequency(final PaymentFrequency livingCostFrequency) {
        this.livingCostFrequency = livingCostFrequency;
    }

    public String getLodgingBy() {
        return lodgingBy;
    }

    public void setLodgingBy(final String lodgingBy) {
        this.lodgingBy = lodgingBy;
    }

    public BigDecimal getLodgingCost() {
        return lodgingCost;
    }

    public void setLodgingCost(final BigDecimal lodgingCost) {
        this.lodgingCost = lodgingCost;
    }

    public PaymentFrequency getLodgingCostFrequency() {
        return lodgingCostFrequency;
    }

    public void setLodgingCostFrequency(final PaymentFrequency lodgingCostFrequency) {
        this.lodgingCostFrequency = lodgingCostFrequency;
    }

    public Integer getMaximumWeeks() {
        return maximumWeeks;
    }

    public void setMaximumWeeks(final Integer maximumWeeks) {
        this.maximumWeeks = maximumWeeks;
    }

    public Integer getMinimumWeeks() {
        return minimumWeeks;
    }

    public void setMinimumWeeks(final Integer minimumWeeks) {
        this.minimumWeeks = minimumWeeks;
    }

    public String getNearestAirport() {
        return nearestAirport;
    }

    public void setNearestAirport(final String nearestAirport) {
        this.nearestAirport = nearestAirport;
    }

    public String getNearestPubTransport() {
        return nearestPubTransport;
    }

    public void setNearestPubTransport(final String nearestPubTransport) {
        this.nearestPubTransport = nearestPubTransport;
    }

    public Date getNominationDeadline() {
        return Copier.copy(nominationDeadline);
    }

    public void setNominationDeadline(final Date nominationDeadline) {
        this.nominationDeadline = Copier.copy(nominationDeadline);
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public void setOtherRequirements(final String otherRequirements) {
        this.otherRequirements = otherRequirements;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(final BigDecimal payment) {
        this.payment = payment;
    }

    public PaymentFrequency getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(final PaymentFrequency paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public Boolean getPrevTrainingRequired() {
        return prevTrainingRequired;
    }

    public void setPrevTrainingRequired(final Boolean prevTrainingRequired) {
        this.prevTrainingRequired = prevTrainingRequired;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(final String refNo) {
        this.refNo = refNo;
    }

    public Set<String> getSpecializations() {
        return Copier.copy(specializations);
    }

    public void setSpecializations(final Set<String> specializations) {
        this.specializations = specializations;
    }

    public Set<StudyLevel> getStudyLevels() {
        return Copier.copy(studyLevels);
    }

    public void setStudyLevels(final Set<StudyLevel> studyLevels) {
        this.studyLevels = Copier.copy(studyLevels);
    }

    public Date getToDate2() {
        return Copier.copy(toDate2);
    }

    public void setToDate2(final Date toDate2) {
        this.toDate2 = Copier.copy(toDate2);
    }

    public Date getToDate() {
        return Copier.copy(toDate);
    }

    public void setToDate(final Date toDate) {
        this.toDate = Copier.copy(toDate);
    }

    public TypeOfWork getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(final TypeOfWork typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public Float getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(final Float weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(final String workDescription) {
        this.workDescription = workDescription;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(final String workingPlace) {
        this.workingPlace = workingPlace;
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    /**
     * first thought was that id should be sufficient, but what if two
     * NOT PRESISTED offers want to be compared, then there is no ID
     * <p/>
     * Even persisted offers can be updated differently, so still all
     * fields need to be taken into conscideration.
     * <p/>
     * {@inheritDoc}
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Offer offer = (Offer) obj;
        if (id != null ? !id.equals(offer.id) : offer.id != null) {
            return false;
        }
        if (refNo != null ? !refNo.equals(offer.refNo) : offer.refNo != null) {
            return false;
        }
        if (livingCostFrequency != offer.livingCostFrequency) {
            return false;
        }
        if (canteen != null ? !canteen.equals(offer.canteen) : offer.canteen != null) {
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
        if (fromDate != null ? !fromDate.equals(offer.fromDate) : offer.fromDate != null) {
            return false;
        }
        if (fromDate2 != null ? !fromDate2.equals(offer.fromDate2) : offer.fromDate2 != null) {
            return false;
        }
        if (unavailableFrom != null ? !unavailableFrom.equals(offer.unavailableFrom) : offer.unavailableFrom != null) {
            return false;
        }
        if (unavailableTo != null ? !unavailableTo.equals(offer.unavailableTo) : offer.unavailableTo != null) {
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
        if (livingCost != null ? !livingCost.equals(offer.livingCost) : offer.livingCost != null) {
            return false;
        }
        if (lodgingBy != null ? !lodgingBy.equals(offer.lodgingBy) : offer.lodgingBy != null) {
            return false;
        }
        if (lodgingCost != null ? !lodgingCost.equals(offer.lodgingCost) : offer.lodgingCost != null) {
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
        if (payment != null ? !payment.equals(offer.payment) : offer.payment != null) {
            return false;
        }
        if (paymentFrequency != offer.paymentFrequency) {
            return false;
        }
        if (prevTrainingRequired != null ? !prevTrainingRequired.equals(offer.prevTrainingRequired) : offer.prevTrainingRequired != null) {
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
        if (weeklyHours != null ? !weeklyHours.equals(offer.weeklyHours) : offer.weeklyHours != null) {
            return false;
        }
        if (workDescription != null ? !workDescription.equals(offer.workDescription) : offer.workDescription != null) {
            return false;
        }
        if (workingPlace != null ? !workingPlace.equals(offer.workingPlace) : offer.workingPlace != null) {
            return false;
        }
        if (typeOfWork != null ? !typeOfWork.equals(offer.typeOfWork) : offer.typeOfWork != null) {
            return false;
        }
        if (!(fieldOfStudies == null ? EnumSet.noneOf(FieldOfStudy.class) : fieldOfStudies).equals(
                offer.fieldOfStudies == null ? EnumSet.noneOf(FieldOfStudy.class) : offer.fieldOfStudies)) {
            return false;
        }
        if (!(studyLevels == null ? EnumSet.noneOf(StudyLevel.class) : studyLevels).equals(
                offer.studyLevels == null ? EnumSet.noneOf(StudyLevel.class) : offer.studyLevels)) {
            return false;
        }
        if (!(specializations == null ? Collections.emptySet() : specializations).equals(
                offer.specializations == null ? Collections.emptySet() : offer.specializations)) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + refNo.hashCode();
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

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", refNo='" + refNo + '\'' +
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
                '}';
    }

    /**
     * @throws {@code VerificationException} if object is not valid
     */
    @Override
    public void verify() throws VerificationException {
        final Collection<String> errors = new ArrayList<>(0);

        errors.addAll(verifyNotNullableFields());
        if (!verifyRefNo()) {
            errors.add("refNo: reference number has incorrect format");
        }
        if (!verifyDates()) {
            errors.add("dates are not set correctly");
        }
        if (!verifyNumberOfWeeks()) {
            errors.add("weeks are not set correctly");
        }
        if (!verifySizeOfFieldsOfStudy()) {
            errors.add(String.format("cannot have more than %s Fields of Study", IWSExchangeConstants.MAX_OFFER_FIELDS_OF_STUDY));
        }
        if (!verifySizeOfSpecializations()) {
            errors.add(String.format("cannot have more than %s Specializations", IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS));
        }
        errors.addAll(verifyFieldDependencies());
        if (!errors.isEmpty()) {
            throw new VerificationException(errors.toString());
        }
    }

    private boolean verifySizeOfSpecializations() {
        return specializations == null || specializations.size() <= IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS;
    }

    private boolean verifySizeOfFieldsOfStudy() {
        return fieldOfStudies == null || fieldOfStudies.size() <= IWSExchangeConstants.MAX_OFFER_FIELDS_OF_STUDY;
    }

    /**
     * Checks if field dependencies are fulfilled.
     *
     * @return collection of errors. If dependencies are valid, method returns empty collection.
     */
    private Collection<String> verifyFieldDependencies() {
        final Collection<String> errors = new ArrayList<>(0);

        if (livingCost != null && livingCostFrequency == null) {
            errors.add("'livingCostFrequency' is required if 'livingCost' is not null");
        }

        if (payment != null && paymentFrequency == null) {
            errors.add("'paymentFrequency' is required if 'payment' is not null");
        }

        if (lodgingCost != null && lodgingCostFrequency == null) {
            errors.add("'lodgingCostFrequency' is required if 'lodgingCost' is not null");
        }

        return errors;
    }

    /**
     * Checks for nulls, empty string and collections in required fields.
     *
     * @return collection of errors. If all required fields are provided, method returns empty collection.
     */
    private Collection<String> verifyNotNullableFields() {
        final Collection<String> errors = new ArrayList<>(0);

        addNullErrorToCollection(errors, "refno", refNo);
        addNullErrorToCollection(errors, "weeklyhours", weeklyHours);
        addEmptyErrorToCollection(errors, "employerName", employerName);
        addNullErrorToCollection(errors, "fromDate", fromDate);
        addNullErrorToCollection(errors, "toDate", toDate);
        addNullErrorToCollection(errors, "language1", language1);
        addNullErrorToCollection(errors, "language1Level", language1Level);
        addNullErrorToCollection(errors, "maximumWeeks", maximumWeeks);
        addNullErrorToCollection(errors, "minimumWeeks", minimumWeeks);
        addEmptyErrorToCollection(errors, "workDescription", workDescription);
        addEmptyErrorToCollection(errors, "fieldOfStudies", fieldOfStudies);
        addEmptyErrorToCollection(errors, "studyLevels", studyLevels);

        return errors;
    }

    /**
     * Checks if minimum and maximum weeks values are correct.
     *
     * @return true if {@code minimumWeeks} and {@code maximumWeeks} are valid.
     */
    boolean verifyNumberOfWeeks() {
        if (minimumWeeks == null || maximumWeeks == null) {
            return false;
        }
        return minimumWeeks > 0 && maximumWeeks >= minimumWeeks;
    }

    /**
     * verifies reference number format
     * [ISO_3166-2 country code]-[exchange year]-[identification number]-[additional code (optional)]
     *
     * @return true if {@code refNo} is valid
     */
    boolean verifyRefNo() {
        if (refNo == null) {
            return false;
        }
        final String[] codes = Locale.getISOCountries();
        // for each country code 3 bytes are needed: "CC|"
        final StringBuilder countryCodes = new StringBuilder(3 * codes.length - 1);
        for (final String code : codes) {
            countryCodes.append(code);
            countryCodes.append('|');
        }
        countryCodes.delete(countryCodes.length() - 1, countryCodes.length());

        final Pattern refNoPattern = Pattern.compile(String.format(refNoFormat, countryCodes.toString().toUpperCase()));
        final Matcher matcher = refNoPattern.matcher(refNo);
        return matcher.matches();
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
     */
    boolean verifyDates() {
        // the order of invoking verifyDates*** methods is important!
        return verifyDatesPresence() && verifyDatesOrder() && verifyDatesNominationDeadline()
                && verifyDatesGroupsOrder() && verifyUnavailableDatesOrder();
    }

    /**
     * Checks for presence of date fields.
     *
     * @return true if all required date fields are provided.
     */
    @SuppressWarnings("OverlyComplexBooleanExpression")
    private boolean verifyDatesPresence() {
        // either 'from' date either 'from2' date must be present
        if (fromDate == null || toDate == null) {
            return false;
        }
        // if 'from' is present then 'to' is needed to
        if (fromDate2 != null && toDate2 == null || fromDate2 == null && toDate2 != null) {
            return false;
        }
        // if 'unavailableFrom' is present then 'unavailableTo' is needed to
        if (unavailableFrom != null && unavailableTo == null || unavailableFrom == null && unavailableTo != null) {
            return false;
        }
        return true;
    }

    /**
     * Verifies order of related dates ({@code fromDate} < {@code toDate}, {@code fromDate2} < {@code toDate2},
     * {@code unavailableFrom} < {@code unavalilableTo}).
     *
     * @return true if order of related dates is valid.
     */
    private boolean verifyDatesOrder() {
        // 'from' date can't be after 'to' date
        if (fromDate != null && fromDate.compareTo(toDate) > 0) {
            return false;
        }
        if (fromDate2 != null && fromDate2.compareTo(toDate2) > 0) {
            return false;
        }
        if (unavailableFrom != null && unavailableFrom.compareTo(unavailableTo) > 0) {
            return false;
        }
        return true;
    }

    /**
     * Checks if {@code nominationDeadline} if before {@code fromDate} and {@code fromDate2}
     *
     * @return true if {@code nominationDeadline} is valid.
     */
    private boolean verifyDatesNominationDeadline() {
        if (nominationDeadline != null) {
            // "nominationDeadline" must be before start of an internship
            if ((fromDate != null && nominationDeadline.after(fromDate))
                    || (fromDate2 != null && nominationDeadline.after(fromDate2))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if date from one group is not inside another group.
     *
     * @return true if order of date groups is valid.
     */
    private boolean verifyDatesGroupsOrder() {
        // dates from groups 1 and 2 cannot intertwine
        if (fromDate != null && fromDate2 != null) {
            // to != null and to2 != null is already verified but checking for nulls for safety
            if (toDate == null || toDate2 == null) {
                return false;
            }
            // from < to and from2 < to is already checked
            if (!(fromDate.after(toDate2) || fromDate2.after(toDate))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true if unavailable dates order is valid.
     */
    private boolean verifyUnavailableDatesOrder() {
        if (unavailableFrom != null) {
            // holidays "from" and "to" date must be inside "from" and "to" or "from2" and "to2" dates
            //      or between "to" and "from2" or "to2" and "from" (see #84 for requirements)
            if (unavailableFrom.before(fromDate) && unavailableTo.after(fromDate)) {
                return false;
            }
            if (unavailableFrom.after(toDate) && unavailableTo.before(toDate)) {
                return false;
            }
            if (unavailableFrom.after(fromDate) && unavailableFrom.before(toDate) && unavailableTo.after(toDate)) {
                return false;
            }
            if (fromDate2 != null) {
                if (unavailableFrom.before(fromDate2) && unavailableTo.after(fromDate2)) {
                    return false;
                }
                if (unavailableFrom.after(toDate2) && unavailableTo.before(toDate2)) {
                    return false;
                }
                if (unavailableFrom.after(fromDate2) && unavailableFrom.before(toDate2) && unavailableTo.after(toDate2)) {
                    return false;
                }
            }
        }
        return true;
    }

}