/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.data.Offer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.data;

import net.iaeste.iws.api.constants.IWSConstants;
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
import net.iaeste.iws.api.exceptions.EntityIdentificationException;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.utils.Copier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Pickelbauer / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class Offer extends AbstractDto {

    /**
     * {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Empty Constructor, required for some communication frameworks.
     */
    public Offer() {
        this.refNo = null;
        this.nominationDeadline = null;
        this.employerName = null;
        //this.studyLevels is already initialized
        this.gender = null;
        this.language1 = null;
        this.language1Level = null;
        this.workDescription = null;
        this.maximumWeeks = null;
        this.minimumWeeks = null;
        this.weeklyHours = null;
        this.dailyHours = null;
        this.fromDate = null;
        this.toDate = null;
    }

    /**
     * Default Constructor.
     *
     * @param refNo              Reference Number
     * @param nominationDeadline nomination deadline
     * @param employerName       employer name
     * @param studyLevels        list of study levels
     * @param gender             Gender
     * @param language1          Language 1
     * @param language1Level     Language 1 level
     * @param workDescription    work description
     * @param minimumWeeks       minimum weeks
     * @param maximumWeeks       maximum weeks
     * @param weeklyHours        weekly hours
     * @param dailyHours         daily hours
     */
    public Offer(final String refNo,
                 final Date nominationDeadline,
                 final String employerName,
                 final List<StudyLevel> studyLevels,
                 final Gender gender,
                 final Language language1,
                 final LanguageLevel language1Level,
                 final String workDescription,
                 final Integer minimumWeeks,
                 final Integer maximumWeeks,
                 final Float weeklyHours,
                 final Float dailyHours,
                 final Date fromDate,
                 final Date toDate) {

        //set required fields
        setRefNo(refNo);
        setNominationDeadline(nominationDeadline);
        setEmployerName(employerName);
        setStudyLevels(studyLevels);
        setGender(gender);
        setLanguage1(language1);
        setLanguage1Level(language1Level);
        setWorkDescription(workDescription);
        setMaximumWeeks(maximumWeeks);
        setMinimumWeeks(minimumWeeks);
        setWeeklyHours(weeklyHours);
        setDailyHours(dailyHours);
        setFromDate(fromDate);
        setToDate(toDate);
    }

    /**
     * Copy constructor.
     * <p/>
     * Fields are copied one by one. Correct "cloning" for muttable members is provided by setters.
     *
     * @param offer Offer to copy
     */
    public Offer(final Offer offer) {
        copyFields(offer, this);
    }

    public Offer(final Offer offer, final EntityIdentificationException e) {
        super(e.getError(), e.getMessage());
        copyFields(offer, this);
    }

    private Long id;
    private String refNo;
    private Date nominationDeadline;

    // Employer information
    private String employerName;
    private String employerAddress;
    private String employerAddress2;
    private String employerBusiness;
    private Integer employerEmployeesCount;
    private String employerWebsite;

    //Student Information
    private List<FieldOfStudy> fieldOfStudies = new ArrayList<FieldOfStudy>();
    /**
     * Has to be defined as a List of Strings because
     * the user should be able to add custom
     * specializations in addition to the predefined ones.
     */
    private List<Specialization> specializations = new ArrayList<>();
    private List<StudyLevel> studyLevels = new ArrayList<>();
    private Boolean prevTrainingRequired;
    private String otherRequirements;
    private Gender gender;
    private Language language1;
    private LanguageLevel language1Level;
    private LanguageOperator language1Operator;
    private Language language2;
    private LanguageLevel language2Level;
    private LanguageOperator language2Operator;
    private Language language3;
    private LanguageLevel language3Level;

    // Work offered
    private String workDescription;
    private TypeOfWork typeOfWork;
    private Integer minimumWeeks;
    private Integer maximumWeeks;
    private Date fromDate;
    private Date toDate;
    private Date fromDate2;
    private Date toDate2;
    private Date holidaysFrom;
    private Date holidaysTo;
    private String workingPlace;
    private String nearestAirport;
    private String nearestPubTransport;
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
    private PaymentFrequency lodgingPaymentFrequency;
    private BigDecimal livingCost;
    private int livingPaymentFrequency;
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

    public List<FieldOfStudy> getFieldOfStudies() {
        return Collections.unmodifiableList(fieldOfStudies);
    }

    public void setFieldOfStudies(final List<FieldOfStudy> fieldOfStudies) {
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Date getHolidaysFrom() {
        return Copier.copy(holidaysFrom);
    }

    public void setHolidaysFrom(final Date holidaysFrom) {
        this.holidaysFrom = Copier.copy(holidaysFrom);
    }

    public Date getHolidaysTo() {
        return Copier.copy(holidaysTo);
    }

    public void setHolidaysTo(final Date holidaysTo) {
        this.holidaysTo = Copier.copy(holidaysTo);
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

    public int getLivingPaymentFrequency() {
        return livingPaymentFrequency;
    }

    public void setLivingPaymentFrequency(final int livingPaymentFrequency) {
        this.livingPaymentFrequency = livingPaymentFrequency;
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

    public PaymentFrequency getLodgingPaymentFrequency() {
        return lodgingPaymentFrequency;
    }

    public void setLodgingPaymentFrequency(final PaymentFrequency lodgingPaymentFrequency) {
        this.lodgingPaymentFrequency = lodgingPaymentFrequency;
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

    public List<Specialization> getSpecializations() {
        return Collections.unmodifiableList(specializations);
    }

    public void setSpecializations(final List<Specialization> specializations) {
        this.specializations = specializations;
    }

    public List<StudyLevel> getStudyLevels() {
        return Collections.unmodifiableList(studyLevels);
    }

    public void setStudyLevels(final List<StudyLevel> studyLevels) {
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

    /**
     * first thought was that id should be sufficient, but what if two
     * NOT PRESISTED offers want to be compared, then there is no ID
     * <p/>
     * Even persisted offers can be updated differently, so still all
     * fields need to be taken into conscideration.
     * <p/>
     * {@inheritDoc}
     *
     * @param o
     * @return
     */
    @SuppressWarnings("OverlyLongMethod")
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Offer offer = (Offer) o;

        if (!id.equals(offer.id)) {
            return false;
        }
        if (livingPaymentFrequency != offer.livingPaymentFrequency) {
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
        if (fieldOfStudies != null ? !fieldOfStudies.equals(offer.fieldOfStudies) : offer.fieldOfStudies != null) {
            return false;
        }
        if (fromDate != null ? !fromDate.equals(offer.fromDate) : offer.fromDate != null) {
            return false;
        }
        if (fromDate2 != null ? !fromDate2.equals(offer.fromDate2) : offer.fromDate2 != null) {
            return false;
        }
        if (gender != offer.gender) {
            return false;
        }
        if (holidaysFrom != null ? !holidaysFrom.equals(offer.holidaysFrom) : offer.holidaysFrom != null) {
            return false;
        }
        if (holidaysTo != null ? !holidaysTo.equals(offer.holidaysTo) : offer.holidaysTo != null) {
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
        if (lodgingPaymentFrequency != offer.lodgingPaymentFrequency) {
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
        if (!refNo.equals(offer.refNo)) {
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
        if (weeklyHours != null ? !weeklyHours.equals(offer.weeklyHours) : offer.weeklyHours != null) {
            return false;
        }
        if (workDescription != null ? !workDescription.equals(offer.workDescription) : offer.workDescription != null) {
            return false;
        }
        if (workingPlace != null ? !workingPlace.equals(offer.workingPlace) : offer.workingPlace != null) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("OverlyLongMethod")
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
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (gender != null ? gender.hashCode() : 0);
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
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (holidaysFrom != null ? holidaysFrom.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (holidaysTo != null ? holidaysTo.hashCode() : 0);
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
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (lodgingPaymentFrequency != null ? lodgingPaymentFrequency.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (livingCost != null ? livingCost.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + livingPaymentFrequency;
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
                ", gender=" + gender +
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
                ", holidaysFrom=" + holidaysFrom +
                ", holidaysTo=" + holidaysTo +
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
                ", lodgingPaymentFrequency=" + lodgingPaymentFrequency +
                ", livingCost=" + livingCost +
                ", livingPaymentFrequency=" + livingPaymentFrequency +
                ", canteen=" + canteen +
                '}';
    }

    /**
     * @throws VerificationException if object is not valid
     */
    @Override
    public void verify() throws VerificationException {
        final Collection<String> errors = new ArrayList<>();
        verifyNotNullableFields(errors);
        if (!verifyRefNo()) {
            errors.add("refNo: reference number has incorrect format");
        }
        if (!verifyDates()) {
            errors.add("dates are not set correctly");
        }
        if (!verifyNumberOfWeeks()) {
            errors.add("weeks are not set correctly");
        }
        if (!errors.isEmpty()) {
            throw new VerificationException(errors.toString());
        }
    }

    private void verifyNotNullableFields(Collection<String> errors) {
        if (fieldOfStudies == null || fieldOfStudies.isEmpty()) {
            errors.add("'fieldOfStudies' is missing");
        }
        if (studyLevels == null || studyLevels.isEmpty()) {
            errors.add("'studyLevels' is missing");
        }
        if (workDescription == null) {
            errors.add("'workDescription' is missing");
        }
        if (language1 == null) {
            errors.add("'language1' is missing");
        }
        if (language1Level == null) {
            errors.add("'language1Level' is missing");
        }
        if (employerName == null) {
            errors.add("'employerName' is missing");
        }
        if (gender == null) {
            errors.add("'gender' is missing");
        }
        if (weeklyHours == null) {
            errors.add("'weeklyHours' is missing");
        }
        if (refNo == null) {
            errors.add("'refNo' is missing");
        }
        if (fromDate == null) {
            errors.add("'fromDate' is missing");
        }
        if (toDate == null) {
            errors.add("'toDate' is missing");
        }
        if (minimumWeeks == null) {
            errors.add("'minimumWeeks' is missing");
        }
        if (maximumWeeks == null) {
            errors.add("'minimumWeeks' is missing");
        }
    }

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
     * @return true if refNo is correct
     *         TODO Michal: check in the spec if "identification number" should  be exactly 4 characters long
     *         E.g: UK-2011-0001-01, IN-2011-0001-KU
     */
    boolean verifyRefNo() {
        if (refNo == null) {
            return false;
        }
        final String[] codes = Locale.getISOCountries();
        // for each country code 3 bytes are needed: "CC|"
        final StringBuilder countryCodes = new StringBuilder(codes.length * 3 - 1);
        for (final String code : codes) {
            countryCodes.append(code);
            countryCodes.append('|');
        }
        countryCodes.delete(countryCodes.length() - 1, countryCodes.length());
        final Pattern refNoPattern = Pattern.compile("(" + countryCodes.toString().toUpperCase() + ")-\\d{4}-\\d{4}(-[A-Z0-9]{2})?");
        final Matcher matcher = refNoPattern.matcher(refNo);
        return matcher.matches();
    }

    /**
     * verifies dates
     * <p/>
     * <ul>
     * <li>nominationDeadline must be before from and from2</li>
     * <li>dateTo > dateFrom</li>
     * <li>either 'from' either 'to' have to be present</li>
     * <li>'from' has to have an adequate 'to'</li>
     * <li>dates from different groups (1,2) cannot interwise</li>
     * <li>holidays must be inside date group</li>
     * </ul>
     */
    boolean verifyDates() {
        // the order of invoking verifyDates*** methods is important!
        return verifyDatesPresence() && verifyDatesOrder() && verifyDatesNominationDeadline() && verifyDatesGroupsOrder() && verifyDatesHolidaysOrder();
    }

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
        // if 'holidaysFrom' is present then 'holidaysTo' is needed to
        if (holidaysFrom != null && holidaysTo == null || holidaysFrom == null && holidaysTo != null) {
            return false;
        }
        return true;
    }

    private boolean verifyDatesOrder() {
        // 'from' date can't be after 'to' date
        if (fromDate != null && fromDate.compareTo(toDate) > 0) {
            return false;
        }
        if (fromDate2 != null && fromDate2.compareTo(toDate2) > 0) {
            return false;
        }
        if (holidaysFrom != null && holidaysFrom.compareTo(holidaysTo) > 0) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("OverlyComplexBooleanExpression")
    private boolean verifyDatesNominationDeadline() {
        if (nominationDeadline != null) {
            // "nominationDeadline" must be before start of an internship
            if ((fromDate != null && nominationDeadline.compareTo(fromDate) > 0)
                    || (fromDate2 != null && nominationDeadline.compareTo(fromDate2) > 0)) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyDatesGroupsOrder() {
        // dates from groups 1 and 2 cannot intertwine
        if (fromDate != null && fromDate2 != null) {
            // from < to and from2 < to is already checked
            // to != null and to2 != null is already checked
            if (!(fromDate.compareTo(toDate2) > 0 || fromDate2.compareTo(toDate) > 0)) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyDatesHolidaysOrder() {
        if (holidaysFrom != null) {
            // holidays "from" and "to" date must be inside "from" and "to" or "from2" and "to2" dates
            //      (otherwise the period of the internship can be shown unambiguously by changing "from" and "to" dates
            if (fromDate != null) {
                if (!(holidaysFrom.compareTo(fromDate) > 0 && holidaysFrom.compareTo(toDate) < 0
                        && holidaysTo.compareTo(fromDate) > 0 && holidaysTo.compareTo(toDate) < 0)) {
                    return false;
                }
            }
            if (fromDate2 != null) {
                if (!(holidaysFrom.compareTo(fromDate2) > 0 && holidaysFrom.compareTo(toDate2) < 0
                        && holidaysTo.compareTo(fromDate2) > 0 && holidaysTo.compareTo(toDate2) < 0)) {
                    return false;
                }
            }

        }
        return true;
    }

    @SuppressWarnings("OverlyLongMethod")
    private static void copyFields(final Offer from, final Offer to) {
        to.setId(from.getId());
        to.setRefNo(from.getRefNo());
        to.setNominationDeadline(from.getNominationDeadline());
        to.setEmployerName(from.getEmployerName());
        to.setEmployerAddress(from.getEmployerAddress());
        to.setEmployerAddress2(from.getEmployerAddress2());
        to.setEmployerBusiness(from.getEmployerBusiness());
        to.setEmployerEmployeesCount(from.getEmployerEmployeesCount());
        to.setEmployerWebsite(from.getEmployerWebsite());
        to.setPrevTrainingRequired(from.getPrevTrainingRequired());
        to.setOtherRequirements(from.getOtherRequirements());
        to.setGender(from.getGender());
        to.setLanguage1(from.getLanguage1());
        to.setLanguage1Level(from.getLanguage1Level());
        to.setLanguage1Operator(from.getLanguage1Operator());
        to.setLanguage2(from.getLanguage2());
        to.setLanguage2Level(from.getLanguage2Level());
        to.setLanguage2Operator(from.getLanguage2Operator());
        to.setLanguage3(from.getLanguage3());
        to.setLanguage3Level(from.getLanguage3Level());
        to.setWorkDescription(from.getWorkDescription());
        to.setTypeOfWork(from.getTypeOfWork());
        to.setMinimumWeeks(from.getMinimumWeeks());
        to.setMaximumWeeks(from.getMaximumWeeks());
        to.setFromDate(from.getFromDate());
        to.setToDate(from.getToDate());
        to.setFromDate2(from.getFromDate2());
        to.setToDate2(from.getToDate2());
        to.setHolidaysFrom(from.getHolidaysFrom());
        to.setHolidaysTo(from.getHolidaysTo());
        to.setWorkingPlace(from.getWorkingPlace());
        to.setNearestAirport(from.getNearestAirport());
        to.setNearestPubTransport(from.getNearestPubTransport());
        to.setWeeklyHours(from.getWeeklyHours());
        to.setDailyHours(from.getDailyHours());
        to.setPayment(from.getPayment());
        to.setCurrency(from.getCurrency());
        to.setPaymentFrequency(from.getPaymentFrequency());
        to.setDeduction(from.getDeduction());
        to.setLodgingBy(from.getLodgingBy());
        to.setLodgingCost(from.getLodgingCost());
        to.setLodgingPaymentFrequency(from.getLodgingPaymentFrequency());
        to.setLivingCost(from.getLivingCost());
        to.setLivingPaymentFrequency(from.getLivingPaymentFrequency());
        to.setCanteen(from.getCanteen());
    }

}