/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.OfferEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities;

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

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection AssignmentToDateFieldFromParameter
 * @since 1.7
 */
@Table(name = "offers")
@Entity
@NamedQueries({
        @NamedQuery(name = "OfferEntity.findAll", query = "SELECT o FROM Offer o"),
        @NamedQuery(name = "OfferEntity.findById", query = "SELECT o FROM Offer o WHERE o.id = :id"),
        @NamedQuery(name = "OfferEntity.findByIds", query = "SELECT o FROM Offer o WHERE o.id IN :ids"),
        @NamedQuery(name = "OfferEntity.findByRefNo", query = "SELECT o FROM Offer o WHERE o.refNo= :refNo"),
        @NamedQuery(name = "OfferEntity.findByEmployerName", query = "SELECT o FROM Offer o WHERE o.employerName= :employerName")
})
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ref_no", nullable = false, unique = true)
    private String refNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "nomination_deadline", nullable = false)
    private Date nominationDeadline;

    /**
     * Employer information should be duplicated in each offer for several reasons:
     * <ul>
     * <li>multiple locations for big companies</li>
     * <li>working hours can change from offer to offer</li>
     * <li>adds unnecessary complexity</li>
     * </ul>
     */

    // Employer information
    @Column(name = "employer_name", nullable = false)
    private String employerName;

    @Column(name = "employer_address")
    private String employerAddress;

    @Column(name = "employer_address_2")
    private String employerAddress2;

    @Column(name = "employer_business")
    private String employerBusiness;

    @Column(name = "employer_employees_cnt")
    private Integer employerEmployeesCount;

    @Column(name = "employer_website")
    private String employerWebsite;

    //Student Information
    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    @Column(name = "field_of_studies")
    private List<FieldOfStudy> fieldOfStudies;

    /**
     * Has to be defined as a List of Strings because
     * the user should be able to add custom
     * specializations in addition to the predefined ones.
     */
    @ElementCollection
    @Column(name = "specialization")
    private List<Specialization> specializations;

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    @Column(name = "study_levels", nullable = false)
    private List<StudyLevel> studyLevels = new ArrayList<>();

    @Column(name = "prev_training_req")
    private Boolean prevTrainingRequired;

    @Column(name = "other_requirements", length = 500)
    private String otherRequirements;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 1)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "language_1", nullable = false)
    private Language language1;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "language_1_level", nullable = false, length = 1)
    private LanguageLevel language1Level;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "language_1_op", length = 1)
    private LanguageOperator language1Operator;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "language_2")
    private Language language2;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "language_2_level", length = 1)
    private LanguageLevel language2Level;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "language_2_op", length = 1)
    private LanguageOperator language2Operator;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "language_3")
    private Language language3;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "language_3_level", length = 1)
    private LanguageLevel language3Level;

    // Work offered
    @Column(name = "work_description", nullable = false, length = 1000)
    private String workDescription;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "work_type", length = 1)
    private TypeOfWork typeOfWork;

    @Column(name = "min_weeks", nullable = false)
    private Integer minimumWeeks;

    @Column(name = "max_weeks", nullable = false)
    private Integer maximumWeeks;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "from_date")
    private Date fromDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "to_date")
    private Date toDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "from_date_2")
    private Date fromDate2;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "to_date_2")
    private Date toDate2;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "holidays_from")
    private Date holidaysFrom;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "holidays_to")
    private Date holidaysTo;

    @Column(name = "working_place")
    private String workingPlace;

    @Column(name = "nearest_airport")
    private String nearestAirport;

    @Column(name = "nearest_pub_transport")
    private String nearestPubTransport;

    @Column(name = "weekly_hours", nullable = false, scale = 5, precision = 3)
    private Float weeklyHours;

    @Column(name = "daily_hours", nullable = false, scale = 5, precision = 3)
    private Float dailyHours;

    /**
     * need big numbers, e.g. 1 EUR = 26.435,00 VND
     */
    @Column(name = "payment", scale = 12, precision = 2)
    private BigDecimal payment;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "currency", length = 3)
    private Currency currency;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_frequency", length = 1)
    private PaymentFrequency paymentFrequency;

    @Column(name = "deduction", scale = 2, precision = 0)
    private Integer deduction;

    // Accommodation
    @Column(name = "lodging_by")
    private String lodgingBy;

    @Column(name = "lodging_cost", scale = 12, precision = 2)
    private BigDecimal lodgingCost;

    @Column(name = "lodging_payment_frequency", length = 1)
    private PaymentFrequency lodgingPaymentFrequency;

    @Column(name = "living_cost", scale = 12, precision = 2)
    private BigDecimal livingCost;

    @Column(name = "living_payment_frequency", length = 1)
    private int livingPaymentFrequency;

    @Column(name = "canteen")
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
        return fieldOfStudies;
    }

    public void setFieldOfStudies(final List<FieldOfStudy> fieldOfStudies) {
        this.fieldOfStudies = fieldOfStudies;
    }

    public Date getFromDate2() {
        return fromDate2;
    }

    public void setFromDate2(final Date fromDate2) {
        this.fromDate2 = fromDate2;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Date getHolidaysFrom() {
        return holidaysFrom;
    }

    public void setHolidaysFrom(final Date holidaysFrom) {
        this.holidaysFrom = holidaysFrom;
    }

    public Date getHolidaysTo() {
        return holidaysTo;
    }

    public void setHolidaysTo(final Date holidaysTo) {
        this.holidaysTo = holidaysTo;
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
        return nominationDeadline;
    }

    public void setNominationDeadline(final Date nominationDeadline) {
        this.nominationDeadline = nominationDeadline;
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
        return specializations;
    }

    public void setSpecializations(final List<Specialization> specializations) {
        this.specializations = specializations;
    }

    public List<StudyLevel> getStudyLevels() {
        return studyLevels;
    }

    public void setStudyLevels(final Collection<StudyLevel> studyLevels) {
        this.studyLevels.addAll(studyLevels);
    }

    public Date getToDate2() {
        return toDate2;
    }

    public void setToDate2(final Date toDate2) {
        this.toDate2 = toDate2;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(final Date toDate) {
        this.toDate = toDate;
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
}
