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
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.LanguageOperator;
import net.iaeste.iws.api.enums.PaymentFrequency;
import net.iaeste.iws.persistence.notification.Notifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * 3. Please add JavaDoc, it is very confusing to read the code.
 * </pre>
 * As written in mails and Trac - please start adding JavaDoc, to clarify what
 * is what. If you document the things in the DTO's (where it should be
 * regardlessly), you can simply refer to the information there with an @see.
 *
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyComplexClass, AssignmentToDateFieldFromParameter, OverlyLongMethod
 */
@NamedQueries({
        @NamedQuery(name = "OfferEntity.findAll", query = "SELECT o FROM OfferEntity o"),
        @NamedQuery(name = "OfferEntity.findById", query = "SELECT o FROM OfferEntity o WHERE o.id = :id"),
        @NamedQuery(name = "OfferEntity.findByIds", query = "SELECT o FROM OfferEntity o WHERE o.id IN :ids"),
        @NamedQuery(name = "OfferEntity.findByRefNo", query = "SELECT o FROM OfferEntity o WHERE o.refNo = :refNo"),
        @NamedQuery(name = "OfferEntity.findByEmployerName", query = "SELECT o FROM OfferEntity o WHERE o.id IN (SELECT ei.id FROM EmployerInformationView ei WHERE ei.employerName = :employerName AND ei.groupId = :groupId)"),
        @NamedQuery(name = "OfferEntity.findByLikeEmployerName", query = "SELECT o FROM OfferEntity o WHERE o.id IN (SELECT ei.id FROM EmployerInformationView ei WHERE ei.employerName LIKE :employerName AND ei.groupId = :groupId)"),
        @NamedQuery(name = "OfferEntity.findByOwnerId", query = "SELECT o FROM OfferEntity o WHERE o.group.id = :id"),
        @NamedQuery(name = "OfferEntity.findShared", query = "SELECT o FROM OfferEntity o"), // TODO michal: correct shared offers query
        @NamedQuery(name = "OfferEntity.deleteById", query = "DELETE FROM OfferEntity o WHERE o.id = :id"),
        @NamedQuery(name = "OfferEntity.deleteByIds", query = "DELETE FROM OfferEntity o WHERE o.id IN :ids")
})
@Entity
@Table(name = "offers")
public class OfferEntity implements Mergeable<OfferEntity>, Notifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @Column(name = "ref_no", nullable = false, unique = true)
    private String refNo = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "nomination_deadline")
    private Date nominationDeadline = null;

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
    private String employerName = null;

    @Column(name = "employer_address")
    private String employerAddress = null;

    @Column(name = "employer_address_2")
    private String employerAddress2 = null;

    @Column(name = "employer_business")
    private String employerBusiness = null;

    @Column(name = "employer_employees_cnt")
    private Integer employerEmployeesCount = null;

    @Column(name = "employer_website")
    private String employerWebsite = null;

    // TODO: add length to limit String/Lists
    @Column(name = "specializations")
    private String specializations = null;

    @Column(name = "study_levels", nullable = false)
    private String studyLevels = null;

    @Column(name = "study_fields", nullable = false)
    private String fieldOfStudies = null;

    @Column(name = "work_type")
    private String typeOfWork = null;

    @Column(name = "prev_training_req")
    private Boolean prevTrainingRequired = null;

    @Column(name = "other_requirements", length = 500)
    private String otherRequirements = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_1", nullable = false)
    private Language language1 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_1_level", nullable = false, length = 1)
    private LanguageLevel language1Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_1_op", length = 1)
    private LanguageOperator language1Operator = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_2")
    private Language language2 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_2_level", length = 1)
    private LanguageLevel language2Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_2_op", length = 1)
    private LanguageOperator language2Operator = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_3")
    private Language language3 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_3_level", length = 1)
    private LanguageLevel language3Level = null;

    // Work offered
    @Column(name = "work_description", nullable = false, length = 1000)
    private String workDescription = null;

    @Column(name = "min_weeks", nullable = false)
    private Integer minimumWeeks = null;

    @Column(name = "max_weeks", nullable = false)
    private Integer maximumWeeks = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "from_date", nullable = false)
    private Date fromDate = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date", nullable = false)
    private Date toDate = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "from_date_2")
    private Date fromDate2 = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date_2")
    private Date toDate2 = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "unavailable_from")
    private Date unavailableFrom = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "unavailable_to")
    private Date unavailableTo = null;

    @Column(name = "working_place")
    private String workingPlace = null;

    @Column(name = "nearest_airport")
    private String nearestAirport = null;

    @Column(name = "nearest_pub_transport")
    private String nearestPubTransport = null;

    @Column(name = "weekly_hours", nullable = false, scale = 5, precision = 3)
    private Float weeklyHours = null;

    @Column(name = "daily_hours", scale = 5, precision = 3)
    private Float dailyHours = null;

    /**
     * need big numbers, e.g. 1 EUR = 26.435,00 VND
     */
    @Column(name = "payment", scale = 12, precision = 2)
    private BigDecimal payment = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", length = 3)
    private Currency currency = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_frequency", length = 1)
    private PaymentFrequency paymentFrequency = null;

    @Column(name = "deduction", scale = 2, precision = 0)
    private Integer deduction = null;

    // Accommodation
    @Column(name = "lodging_by")
    private String lodgingBy = null;

    @Column(name = "lodging_cost", scale = 12, precision = 2)
    private BigDecimal lodgingCost = null;

    @Column(name = "lodging_cost_frequency", length = 1)
    private PaymentFrequency lodgingCostFrequency = null;

    @Column(name = "living_cost", scale = 12, precision = 2)
    private BigDecimal livingCost = null;

    @Column(name = "living_cost_frequency", length = 1)
    private PaymentFrequency livingCostFrequency = null;

    @Column(name = "canteen")
    private Boolean canteen = null;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(nullable = true, name = "group_id")
    private GroupEntity group = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created = new Date();

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

    public String getFieldOfStudies() {
        return fieldOfStudies;
    }

    public void setFieldOfStudies(final String fieldOfStudies) {
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

    public Date getUnavailableFrom() {
        return unavailableFrom;
    }

    public void setUnavailableFrom(final Date unavailableFrom) {
        this.unavailableFrom = unavailableFrom;
    }

    public Date getUnavailableTo() {
        return unavailableTo;
    }

    public void setUnavailableTo(final Date unavailableTo) {
        this.unavailableTo = unavailableTo;
    }

    @Override
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

    public String getSpecializations() {
        return specializations;
    }

    public void setSpecializations(final String specializations) {
        this.specializations = specializations;
    }

    public String getStudyLevels() {
        return studyLevels;
    }

    public void setStudyLevels(final String studyLevels) {
        this.studyLevels = studyLevels;
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

    public void setTypeOfWork(final String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public String getTypeOfWork() {
        return typeOfWork;
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

    public Date getModified() {
        return modified;
    }

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final OfferEntity obj) {
        // don't merge if objects are not the same entity
        if ((id != null) && (obj != null) && id.equals(obj.id)) {
            // Note, Id & refno are *not* allowed to be updated!
            employerName = obj.employerName;
            employerAddress = obj.employerAddress;
            employerAddress2 = obj.employerAddress2;
            employerBusiness = obj.employerBusiness;
            employerEmployeesCount = obj.employerEmployeesCount;
            employerWebsite = obj.employerWebsite;
            prevTrainingRequired = obj.prevTrainingRequired;
            otherRequirements = obj.otherRequirements;
            language1 = obj.language1;
            language1Level = obj.language1Level;
            language1Operator = obj.language1Operator;
            language2 = obj.language2;
            language2Level = obj.language2Level;
            language2Operator = obj.language2Operator;
            language3 = obj.language3;
            language3Level = obj.language3Level;
            workDescription = obj.workDescription;
            minimumWeeks = obj.minimumWeeks;
            maximumWeeks = obj.maximumWeeks;
            workingPlace = obj.workingPlace;
            nearestAirport = obj.nearestAirport;
            nearestPubTransport = obj.nearestPubTransport;
            weeklyHours = obj.weeklyHours;
            dailyHours = obj.dailyHours;
            payment = obj.payment;
            currency = obj.currency;
            paymentFrequency = obj.paymentFrequency;
            deduction = obj.deduction;
            lodgingBy = obj.lodgingBy;
            lodgingCost = obj.lodgingCost;
            lodgingCostFrequency = obj.lodgingCostFrequency;
            livingCost = obj.livingCost;
            livingCostFrequency = obj.livingCostFrequency;
            canteen = obj.canteen;
            nominationDeadline = obj.nominationDeadline;
            fromDate = obj.fromDate;
            toDate = obj.toDate;
            fromDate2 = obj.fromDate2;
            toDate2 = obj.toDate2;
            unavailableFrom = obj.unavailableFrom;
            unavailableTo = obj.unavailableTo;
            typeOfWork = obj.typeOfWork;
            fieldOfStudies = obj.fieldOfStudies;
            specializations = obj.specializations;
            studyLevels = obj.studyLevels;

            // Set the Modified value to 'now', so the time of
            // the last update is in the Record in the database.
            modified = new Date();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateNotificationMessage() {
        return "Offer " + refNo;
    }
}
