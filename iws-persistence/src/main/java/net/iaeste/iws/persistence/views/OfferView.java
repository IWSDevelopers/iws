/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.EmployerView
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CompareToUsesNonFinalVariable
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findOfferByGroup",
                query = "select e from OfferView e " +
                        "where e.groupId = :gid "),
        @NamedQuery(name = "view.findOfferByGroupAndId",
                query = "select e from OfferView e " +
                        "where e.groupId = :gid" +
                        "  and e.offerExternalId = :oeid")
})
@Table(name = "offer_view")
public class OfferView extends AbstractView<OfferView> {

    /** {@see IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "offer_id", insertable = false, updatable = false)
    private Long offerId = null;

    @Column(name = "offer_external_id", insertable = false, updatable = false)
    private String offerExternalId = null;

    @Column(name = "ref_no", insertable = false, updatable = false)
    private String refNo = null;

    @Column(name = "work_description", insertable = false, updatable = false)
    private String workDescription = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_type", insertable = false, updatable = false)
    private TypeOfWork typeOfWork = null;


    @Column(name = "study_levels", insertable = false, updatable = false)
    private String studyLevels = null;

    @Column(name = "study_fields", insertable = false, updatable = false)
    private String fieldOfStudies = null;

    @Column(name = "specializations", insertable = false, updatable = false)
    private String specializations = null;

    @Column(name = "prev_training_req", insertable = false, updatable = false)
    private Boolean prevTrainingRequired = null;

    @Column(name = "other_requirements", insertable = false, updatable = false)
    private String otherRequirements = null;

    @Column(name = "min_weeks", insertable = false, updatable = false)
    private Integer minimumWeeks = null;

    @Column(name = "max_weeks", insertable = false, updatable = false)
    private Integer maximumWeeks = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "from_date", insertable = false, updatable = false)
    private Date fromDate = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date", insertable = false, updatable = false)
    private Date toDate = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "from_date_2", insertable = false, updatable = false)
    private Date fromDate2 = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date_2", insertable = false, updatable = false)
    private Date toDate2 = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "unavailable_from", insertable = false, updatable = false)
    private Date unavailableFrom = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "unavailable_to", insertable = false, updatable = false)
    private Date unavailableTo = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_1", insertable = false, updatable = false)
    private Language language1 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_1_level", insertable = false, updatable = false)
    private LanguageLevel language1Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_1_op", insertable = false, updatable = false)
    private LanguageOperator language1Operator = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_2", insertable = false, updatable = false)
    private Language language2 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_2_level", insertable = false, updatable = false)
    private LanguageLevel language2Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_2_op", insertable = false, updatable = false)
    private LanguageOperator language2Operator = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_3", insertable = false, updatable = false)
    private Language language3 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_3_level", insertable = false, updatable = false)
    private LanguageLevel language3Level = null;

    @Column(name = "payment", insertable = false, updatable = false)
    private BigDecimal payment = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_frequency", insertable = false, updatable = false)
    private PaymentFrequency paymentFrequency = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", insertable = false, updatable = false)
    private Currency currency = null;

    @Column(name = "deduction", insertable = false, updatable = false)
    private String deduction = null;

    @Column(name = "living_cost", insertable = false, updatable = false)
    private BigDecimal livingCost = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "living_cost_frequency", insertable = false, updatable = false)
    private PaymentFrequency livingCostFrequency = null;

    @Column(name = "lodging_by", insertable = false, updatable = false)
    private String lodgingBy = null;

    @Column(name = "lodging_cost", insertable = false, updatable = false)
    private BigDecimal lodgingCost = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "lodging_cost_frequency", insertable = false, updatable = false)
    private PaymentFrequency lodgingCostFrequency = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "nomination_deadline", insertable = false, updatable = false)
    private Date nominationDeadline = null;

    @Column(name = "number_of_hard_copies", insertable = false, updatable = false)
    private Integer numberOfHardCopies = null;

    @Column(name = "additional_information", insertable = false, updatable = false)
    private String additionalInformation = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", insertable = false, updatable = false)
    private OfferState status = OfferState.NEW;

    @Id
    @Column(name = "employer_id", insertable = false, updatable = false)
    private Long employerId = null;

    @Column(name = "employer_external_id", insertable = false, updatable = false)
    private String employerExternalId = null;

    @Column(name = "employer_name", insertable = false, updatable = false)
    private String employerName = null;

    @Column(name = "department", insertable = false, updatable = false)
    private String employerDepartment = null;

    @Column(name = "business", insertable = false, updatable = false)
    private String business = null;

    @Column(name = "number_of_employees", insertable = false, updatable = false)
    private Integer numberOfEmployees = null;

    @Column(name = "website", insertable = false, updatable = false)
    private String website = null;

    @Column(name = "working_place", insertable = false, updatable = false)
    private String workingPlace = null;

    @Column(name = "canteen", insertable = false, updatable = false)
    private Boolean canteen = null;

    @Column(name = "nearest_airport", insertable = false, updatable = false)
    private String nearestAirport = null;

    @Column(name = "nearest_public_transport", insertable = false, updatable = false)
    private String nearestPublicTransport = null;

    @Column(name = "weekly_hours", insertable = false, updatable = false)
    private Float weeklyHours = null;

    @Column(name = "daily_hours", insertable = false, updatable = false)
    private Float dailyHours = null;

    @Column(name = "group_id", insertable = false, updatable = false)
    private Long groupId = null;

    @Column(name = "group_external_id", insertable = false, updatable = false)
    private String groupExternalId = null;

    @Column(name = "grouptype", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private GroupType groupType = null;

    @Column(name = "groupname", insertable = false, updatable = false)
    private String groupName = null;

    @Column(name = "address_id", insertable = false, updatable = false)
    private Long addressId = null;

    @Column(name = "address_external_id", insertable = false, updatable = false)
    private String addressExternalId = null;

    @Column(name = "street1", insertable = false, updatable = false)
    private String street1 = null;

    @Column(name = "street2", insertable = false, updatable = false)
    private String street2 = null;

    @Column(name = "zip", insertable = false, updatable = false)
    private String zip = null;

    @Column(name = "city", insertable = false, updatable = false)
    private String city = null;

    @Column(name = "state", insertable = false, updatable = false)
    private String state = null;

    @Column(name = "country_name", insertable = false, updatable = false)
    private String countryName = null;

    @Column(name = "ns_firstname", insertable = false, updatable = false)
    private String nsFirstname = null;

    @Column(name = "ns_lastname", insertable = false, updatable = false)
    private String nsLastname = null;

    @Column(name = "offer_modified", insertable = false, updatable = false)
    private Date offerModified = null;

    @Column(name = "offer_created", insertable = false, updatable = false)
    private Date offerCreated = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setOfferId(final Long offerId) {
        this.offerId = offerId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferExternalId(final String offerExternalId) {
        this.offerExternalId = offerExternalId;
    }

    public String getOfferExternalId() {
        return offerExternalId;
    }

    public void setRefNo(final String refNo) {
        this.refNo = refNo;
    }

    public String getRefNo() {
        return refNo;
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

    public void setStatus(final OfferState status) {
        this.status = status;
    }

    public OfferState getStatus() {
        return status;
    }

    public void setEmployerId(final Long employerId) {
        this.employerId = employerId;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerExternalId(final String employerExternalId) {
        this.employerExternalId = employerExternalId;
    }

    public String getEmployerExternalId() {
        return employerExternalId;
    }

    public void setEmployerName(final String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerDepartment(final String employerDepartment) {
        this.employerDepartment = employerDepartment;
    }

    public String getEmployerDepartment() {
        return employerDepartment;
    }

    public void setBusiness(final String business) {
        this.business = business;
    }

    public String getBusiness() {
        return business;
    }

    public void setNumberOfEmployees(final Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setWorkingPlace(final String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setCanteen(final Boolean canteen) {
        this.canteen = canteen;
    }

    public Boolean getCanteen() {
        return canteen;
    }

    public void setNearestAirport(final String nearestAirport) {
        this.nearestAirport = nearestAirport;
    }

    public String getNearestAirport() {
        return nearestAirport;
    }

    public void setNearestPublicTransport(final String nearestPublicTransport) {
        this.nearestPublicTransport = nearestPublicTransport;
    }

    public String getNearestPublicTransport() {
        return nearestPublicTransport;
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

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupExternalId(final String groupExternalId) {
        this.groupExternalId = groupExternalId;
    }

    public String getGroupExternalId() {
        return groupExternalId;
    }

    public void setGroupType(final GroupType groupType) {
        this.groupType = groupType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setAddressId(final Long addressId) {
        this.addressId = addressId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressExternalId(final String addressExternalId) {
        this.addressExternalId = addressExternalId;
    }

    public String getAddressExternalId() {
        return addressExternalId;
    }

    public void setStreet1(final String street1) {
        this.street1 = street1;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet2(final String street2) {
        this.street2 = street2;
    }

    public String getStreet2() {
        return street2;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setNsFirstname(final String nsFirstname) {
        this.nsFirstname = nsFirstname;
    }

    public String getNsFirstname() {
        return nsFirstname;
    }

    public void setNsLastname(final String nsLastname) {
        this.nsLastname = nsLastname;
    }

    public String getNsLastname() {
        return nsLastname;
    }

    public void setOfferModified(final Date offerModified) {
        this.offerModified = offerModified;
    }

    public Date getOfferModified() {
        return offerModified;
    }

    public void setOfferCreated(final Date offerCreated) {
        this.offerCreated = offerCreated;
    }

    public Date getOfferCreated() {
        return offerCreated;
    }

    // =========================================================================
    // Standard View Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof EmployerView)) {
            return false;
        }

        // As the view is reading from the current data model, and the Id is
        // always unique. It is sufficient to compare against this field.
        final OfferView that = (OfferView) obj;
        return !(offerId != null ? !offerId.equals(that.offerId) : that.offerId != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return offerId != null ? offerId.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final OfferView o) {
        final int result = offerId.compareTo(o.offerId);

        return sortAscending ? result : -result;
    }
}
