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
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.enums.TypeOfWork;
import net.iaeste.iws.api.utils.Copier;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
        @NamedQuery(name = "OfferEntity.findAll", query = "SELECT o FROM OfferEntity o"),
        @NamedQuery(name = "OfferEntity.findById", query = "SELECT o FROM OfferEntity o WHERE o.id = :id"),
        @NamedQuery(name = "OfferEntity.findByIds", query = "SELECT o FROM OfferEntity o WHERE o.id IN :ids"),
        @NamedQuery(name = "OfferEntity.findByRefNo", query = "SELECT o FROM OfferEntity o WHERE o.refNo = :refNo"),
        @NamedQuery(name = "OfferEntity.findByEmployerName", query = "SELECT o FROM OfferEntity o WHERE o.employerName= :employerName"),
        @NamedQuery(name = "OfferEntity.deleteById", query = "DELETE FROM OfferEntity o WHERE o.id = :id"),
        @NamedQuery(name = "OfferEntity.deleteByIds", query = "DELETE FROM OfferEntity o WHERE o.id IN :ids")
})
public class OfferEntity implements Mergeable<OfferEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ref_no", nullable = false, unique = true)
    private String refNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "nomination_deadline")
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

    /**
     * Has to be defined as a List of Strings because
     * the user should be able to add custom
     * specializations in addition to the predefined ones.
     */
    @ElementCollection
    @CollectionTable(name = "specializations", joinColumns = @JoinColumn(name = "offer_id"))
    @Column(name = "name", nullable = false)
    private List<String> specializations;

    @ElementCollection
    @CollectionTable(name = "study_levels", joinColumns = @JoinColumn(name = "offer_id"))
    @Column(name = "name", nullable = false)
    private List<StudyLevel> studyLevels = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "study_fields", joinColumns = @JoinColumn(name = "offer_id"))
    @Column(name = "name", nullable = false)
    private List<FieldOfStudy> fieldOfStudies = new ArrayList<>();

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
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "to_date", nullable = false)
    private Date toDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "from_date_2")
    private Date fromDate2;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "to_date_2")
    private Date toDate2;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "unavailable_from")
    private Date unavailableFrom;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "unavailable_to")
    private Date unavailableTo;

    @Column(name = "working_place")
    private String workingPlace;

    @Column(name = "nearest_airport")
    private String nearestAirport;

    @Column(name = "nearest_pub_transport")
    private String nearestPubTransport;

    @Column(name = "weekly_hours", nullable = false, scale = 5, precision = 3)
    private Float weeklyHours;

    @Column(name = "daily_hours", scale = 5, precision = 3)
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

    @Column(name = "lodging_cost_frequency", length = 1)
    private PaymentFrequency lodgingCostFrequency;

    @Column(name = "living_cost", scale = 12, precision = 2)
    private BigDecimal livingCost;

    @Column(name = "living_cost_frequency", length = 1)
    private PaymentFrequency livingCostFrequency;

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
        return Copier.copy(fromDate2);
    }

    public void setFromDate2(final Date fromDate2) {
        this.fromDate2 = Copier.copy(fromDate2);
    }

    public Date getFromDate() {
        return Copier.copy(fromDate);
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = Copier.copy(fromDate);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
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

    public List<String> getSpecializations() {
        return Copier.copy(specializations);
    }

    public void setSpecializations(final List<String> specializations) {
        this.specializations = Copier.copy(specializations);
    }

    public List<StudyLevel> getStudyLevels() {
        return Copier.copy(studyLevels);
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
     * {@inheritDoc}
     */
    @Override
    public void merge(final OfferEntity offer) {
        // TODO: keep in sync with transformers and Offer copy constructor
        this.setId(offer.getId());
        this.setRefNo(offer.getRefNo());
        this.setNominationDeadline(offer.getNominationDeadline());
        this.setEmployerName(offer.getEmployerName());
        this.setEmployerAddress(offer.getEmployerAddress());
        this.setEmployerAddress2(offer.getEmployerAddress2());
        this.setEmployerBusiness(offer.getEmployerBusiness());
        this.setEmployerEmployeesCount(offer.getEmployerEmployeesCount());
        this.setEmployerWebsite(offer.getEmployerWebsite());
        this.setFieldOfStudies(offer.getFieldOfStudies());
        this.setSpecializations(offer.getSpecializations());
        this.setPrevTrainingRequired(offer.getPrevTrainingRequired());
        this.setOtherRequirements(offer.getOtherRequirements());
        this.setGender(offer.getGender());
        this.setLanguage1(offer.getLanguage1());
        this.setLanguage1Level(offer.getLanguage1Level());
        this.setLanguage1Operator(offer.getLanguage1Operator());
        this.setLanguage2(offer.getLanguage2());
        this.setLanguage2Level(offer.getLanguage2Level());
        this.setLanguage2Operator(offer.getLanguage2Operator());
        this.setLanguage3(offer.getLanguage3());
        this.setLanguage3Level(offer.getLanguage3Level());
        this.setWorkDescription(offer.getWorkDescription());
        this.setTypeOfWork(offer.getTypeOfWork());
        this.setMinimumWeeks(offer.getMinimumWeeks());
        this.setMaximumWeeks(offer.getMaximumWeeks());
        this.setFromDate(offer.getFromDate());
        this.setToDate(offer.getToDate());
        this.setFromDate2(offer.getFromDate2());
        this.setToDate2(offer.getToDate2());
        this.setUnavailableFrom(offer.getUnavailableFrom());
        this.setUnavailableTo(offer.getUnavailableTo());
        this.setWorkingPlace(offer.getWorkingPlace());
        this.setNearestAirport(offer.getNearestAirport());
        this.setNearestPubTransport(offer.getNearestPubTransport());
        this.setWeeklyHours(offer.getWeeklyHours());
        this.setDailyHours(offer.getDailyHours());
        this.setPayment(offer.getPayment());
        this.setCurrency(offer.getCurrency());
        this.setPaymentFrequency(offer.getPaymentFrequency());
        this.setDeduction(offer.getDeduction());
        this.setLodgingBy(offer.getLodgingBy());
        this.setLodgingCost(offer.getLodgingCost());
        this.setLodgingCostFrequency(offer.getLodgingCostFrequency());
        this.setLivingCost(offer.getLivingCost());
        this.setLivingCostFrequency(offer.getLivingCostFrequency());
        this.setCanteen(offer.getCanteen());
        this.setStudyLevels(offer.getStudyLevels());
        this.setSpecializations(offer.getSpecializations());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfferEntity entity = (OfferEntity) o;

        if (canteen != null ? !canteen.equals(entity.canteen) : entity.canteen != null) {
            return false;
        }
        if (currency != entity.currency) {
            return false;
        }
        if (dailyHours != null ? !dailyHours.equals(entity.dailyHours) : entity.dailyHours != null) {
            return false;
        }
        if (deduction != null ? !deduction.equals(entity.deduction) : entity.deduction != null) {
            return false;
        }
        if (employerAddress != null ? !employerAddress.equals(entity.employerAddress) : entity.employerAddress != null) {
            return false;
        }
        if (employerAddress2 != null ? !employerAddress2.equals(entity.employerAddress2) : entity.employerAddress2 != null) {
            return false;
        }
        if (employerBusiness != null ? !employerBusiness.equals(entity.employerBusiness) : entity.employerBusiness != null) {
            return false;
        }
        if (employerEmployeesCount != null ? !employerEmployeesCount.equals(entity.employerEmployeesCount) : entity.employerEmployeesCount != null) {
            return false;
        }
        if (employerName != null ? !employerName.equals(entity.employerName) : entity.employerName != null) {
            return false;
        }
        if (employerWebsite != null ? !employerWebsite.equals(entity.employerWebsite) : entity.employerWebsite != null) {
            return false;
        }
        if (fieldOfStudies != null ? !fieldOfStudies.equals(entity.fieldOfStudies) : entity.fieldOfStudies != null) {
            return false;
        }
        if (fromDate != null ? !fromDate.equals(entity.fromDate) : entity.fromDate != null) {
            return false;
        }
        if (fromDate2 != null ? !fromDate2.equals(entity.fromDate2) : entity.fromDate2 != null) {
            return false;
        }
        if (gender != entity.gender) {
            return false;
        }
        if (id != null ? !id.equals(entity.id) : entity.id != null) {
            return false;
        }
        if (language1 != entity.language1) {
            return false;
        }
        if (language1Level != entity.language1Level) {
            return false;
        }
        if (language1Operator != entity.language1Operator) {
            return false;
        }
        if (language2 != entity.language2) {
            return false;
        }
        if (language2Level != entity.language2Level) {
            return false;
        }
        if (language2Operator != entity.language2Operator) {
            return false;
        }
        if (language3 != entity.language3) {
            return false;
        }
        if (language3Level != entity.language3Level) {
            return false;
        }
        if (livingCost != null ? !livingCost.equals(entity.livingCost) : entity.livingCost != null) {
            return false;
        }
        if (livingCostFrequency != entity.livingCostFrequency) {
            return false;
        }
        if (lodgingBy != null ? !lodgingBy.equals(entity.lodgingBy) : entity.lodgingBy != null) {
            return false;
        }
        if (lodgingCost != null ? !lodgingCost.equals(entity.lodgingCost) : entity.lodgingCost != null) {
            return false;
        }
        if (lodgingCostFrequency != entity.lodgingCostFrequency) {
            return false;
        }
        if (maximumWeeks != null ? !maximumWeeks.equals(entity.maximumWeeks) : entity.maximumWeeks != null) {
            return false;
        }
        if (minimumWeeks != null ? !minimumWeeks.equals(entity.minimumWeeks) : entity.minimumWeeks != null) {
            return false;
        }
        if (nearestAirport != null ? !nearestAirport.equals(entity.nearestAirport) : entity.nearestAirport != null) {
            return false;
        }
        if (nearestPubTransport != null ? !nearestPubTransport.equals(entity.nearestPubTransport) : entity.nearestPubTransport != null) {
            return false;
        }
        if (nominationDeadline != null ? !nominationDeadline.equals(entity.nominationDeadline) : entity.nominationDeadline != null) {
            return false;
        }
        if (otherRequirements != null ? !otherRequirements.equals(entity.otherRequirements) : entity.otherRequirements != null) {
            return false;
        }
        if (payment != null ? !payment.equals(entity.payment) : entity.payment != null) {
            return false;
        }
        if (paymentFrequency != entity.paymentFrequency) {
            return false;
        }
        if (prevTrainingRequired != null ? !prevTrainingRequired.equals(entity.prevTrainingRequired) : entity.prevTrainingRequired != null) {
            return false;
        }
        if (refNo != null ? !refNo.equals(entity.refNo) : entity.refNo != null) {
            return false;
        }
        if (!(specializations == null ? Collections.emptyList() : specializations).equals(
                entity.specializations == null ? Collections.emptyList() : entity.specializations)) {
            return false;
        }
        if (studyLevels != null ? !studyLevels.equals(entity.studyLevels) : entity.studyLevels != null) {
            return false;
        }
        if (toDate != null ? !toDate.equals(entity.toDate) : entity.toDate != null) {
            return false;
        }
        if (toDate2 != null ? !toDate2.equals(entity.toDate2) : entity.toDate2 != null) {
            return false;
        }
        if (typeOfWork != entity.typeOfWork) {
            return false;
        }
        if (unavailableFrom != null ? !unavailableFrom.equals(entity.unavailableFrom) : entity.unavailableFrom != null) {
            return false;
        }
        if (unavailableTo != null ? !unavailableTo.equals(entity.unavailableTo) : entity.unavailableTo != null) {
            return false;
        }
        if (weeklyHours != null ? !weeklyHours.equals(entity.weeklyHours) : entity.weeklyHours != null) {
            return false;
        }
        if (workDescription != null ? !workDescription.equals(entity.workDescription) : entity.workDescription != null) {
            return false;
        }
        if (workingPlace != null ? !workingPlace.equals(entity.workingPlace) : entity.workingPlace != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (refNo != null ? refNo.hashCode() : 0);
        result = 31 * result + (nominationDeadline != null ? nominationDeadline.hashCode() : 0);
        result = 31 * result + (employerName != null ? employerName.hashCode() : 0);
        result = 31 * result + (employerAddress != null ? employerAddress.hashCode() : 0);
        result = 31 * result + (employerAddress2 != null ? employerAddress2.hashCode() : 0);
        result = 31 * result + (employerBusiness != null ? employerBusiness.hashCode() : 0);
        result = 31 * result + (employerEmployeesCount != null ? employerEmployeesCount.hashCode() : 0);
        result = 31 * result + (employerWebsite != null ? employerWebsite.hashCode() : 0);
        result = 31 * result + (fieldOfStudies != null ? fieldOfStudies.hashCode() : 0);
        result = 31 * result + (specializations != null ? specializations.hashCode() : 0);
        result = 31 * result + (studyLevels != null ? studyLevels.hashCode() : 0);
        result = 31 * result + (prevTrainingRequired != null ? prevTrainingRequired.hashCode() : 0);
        result = 31 * result + (otherRequirements != null ? otherRequirements.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (language1 != null ? language1.hashCode() : 0);
        result = 31 * result + (language1Level != null ? language1Level.hashCode() : 0);
        result = 31 * result + (language1Operator != null ? language1Operator.hashCode() : 0);
        result = 31 * result + (language2 != null ? language2.hashCode() : 0);
        result = 31 * result + (language2Level != null ? language2Level.hashCode() : 0);
        result = 31 * result + (language2Operator != null ? language2Operator.hashCode() : 0);
        result = 31 * result + (language3 != null ? language3.hashCode() : 0);
        result = 31 * result + (language3Level != null ? language3Level.hashCode() : 0);
        result = 31 * result + (workDescription != null ? workDescription.hashCode() : 0);
        result = 31 * result + (typeOfWork != null ? typeOfWork.hashCode() : 0);
        result = 31 * result + (minimumWeeks != null ? minimumWeeks.hashCode() : 0);
        result = 31 * result + (maximumWeeks != null ? maximumWeeks.hashCode() : 0);
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        result = 31 * result + (fromDate2 != null ? fromDate2.hashCode() : 0);
        result = 31 * result + (toDate2 != null ? toDate2.hashCode() : 0);
        result = 31 * result + (unavailableFrom != null ? unavailableFrom.hashCode() : 0);
        result = 31 * result + (unavailableTo != null ? unavailableTo.hashCode() : 0);
        result = 31 * result + (workingPlace != null ? workingPlace.hashCode() : 0);
        result = 31 * result + (nearestAirport != null ? nearestAirport.hashCode() : 0);
        result = 31 * result + (nearestPubTransport != null ? nearestPubTransport.hashCode() : 0);
        result = 31 * result + (weeklyHours != null ? weeklyHours.hashCode() : 0);
        result = 31 * result + (dailyHours != null ? dailyHours.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (paymentFrequency != null ? paymentFrequency.hashCode() : 0);
        result = 31 * result + (deduction != null ? deduction.hashCode() : 0);
        result = 31 * result + (lodgingBy != null ? lodgingBy.hashCode() : 0);
        result = 31 * result + (lodgingCost != null ? lodgingCost.hashCode() : 0);
        result = 31 * result + (lodgingCostFrequency != null ? lodgingCostFrequency.hashCode() : 0);
        result = 31 * result + (livingCost != null ? livingCost.hashCode() : 0);
        result = 31 * result + (livingCostFrequency != null ? livingCostFrequency.hashCode() : 0);
        result = 31 * result + (canteen != null ? canteen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OfferEntity{" +
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
}
