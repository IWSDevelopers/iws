/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.exchange.OfferEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.ExchangeType;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.OfferType;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.persistence.monitoring.Monitored;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.Externable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        // Query is used by the Migration Tool
        @NamedQuery(name = "offer.findByOldOfferId",
                query = "select o from OfferEntity o " +
                        "where o.oldOfferId = :ooid"),
        @NamedQuery(name = "offer.findAllForGroup",
                query = "select o from OfferEntity o " +
                        "where o.employer.group.id = :gid"),
        @NamedQuery(name = "offer.findByGroupAndExternalIdAndRefNo",
                query = "select o from OfferEntity o " +
                        "where o.employer.group.id = :gid" +
                        "  and o.externalId = :eoid" +
                        "  and o.refNo = :refno"),
        @NamedQuery(name = "offer.findByGroupAndId",
                query = "select o from OfferEntity o " +
                        "where o.employer.group.id = :gid" +
                        "  and o.id = :id"),
        @NamedQuery(name = "offer.findByGroupAndIds",
                query = "select o from OfferEntity o " +
                        "where o.employer.group.id = :gid" +
                        "  and o.id in :ids"),
        @NamedQuery(name = "offer.findByGroupAndExternalIds",
                query = "select o from OfferEntity o " +
                        "where o.employer.group.id = :gid" +
                        "  and o.externalId in :eoids"),
        @NamedQuery(name = "offer.findByGroupAndExternalId",
                query = "select o from OfferEntity o " +
                        "where o.employer.group.id = :gid" +
                        "  and o.externalId = :eoid"),
        @NamedQuery(name = "offer.findByGroupAndRefNo",
                query = "select o from OfferEntity o " +
                        "where o.employer.group.id = :gid" +
                        "  and o.refNo = :refNo"),
        @NamedQuery(name = "offer.findByGroupAndLikeEmployerName",
                query = "select o from OfferEntity o " +
                        "where o.employer.group.id = :gid" +
                        "  and lower(o.employer.name) like :employer"),
        @NamedQuery(name = "offer.findByGroupAndEmployerName",
                query = "select o from OfferEntity o " +
                        "where o.employer.group.id = :gid" +
                        "  and o.employer.name = :employer"),
        @NamedQuery(name = "offer.findExpired",
                query = "select o from OfferEntity o " +
                        "where o.nominationDeadline <= :date" +
                        "  and o.status = 'SHARED'" +
                        "  and o.exchangeYear = :exchangeYear"),
        @NamedQuery(name = "offer.updateStateByIds",
                query = "update OfferEntity o " +
                        "set o.status = :status " +
                        "where o.id in :ids")})

@Entity
@Table(name = "offers")
@Monitored(name = "Offer", level = MonitoringLevel.DETAILED)
public class OfferEntity implements Externable<OfferEntity>, Notifiable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "offer_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * The content of this Entity is exposed externally, however to avoid that
     * someone tries to spoof the system by second guessing our Sequence values,
     * An External Id is used, the External Id is a Uniqie UUID value, which in
     * all external references is referred to as the "Id". Although this can be
     * classified as StO (Security through Obscrutity), there is no need to
     * expose more information than necessary.
     */
    @Column(name = "external_id", length = 36, unique = true, nullable = false, updatable = false)
    private String externalId = null;

    /**
     * The Standard reference number for all Offers. Note, that the migration
     * of the IW3 based Offers, means that all the old reference numbers are as
     * well migrated to the new format. The mapping of the old and new reference
     * numbers are automated, so it is easy to make a reverse mapping - hence,
     * we're not storing the old generated reference number.
     */
    @Column(name = "ref_no", length = 16, nullable = false, unique = true)
    private String refNo = null;

    @Monitored(name="Offer type", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "offer_type", length = 10, nullable = false)
    private OfferType offerType;

    @Monitored(name="Exchange type", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "exchange_type", length = 10, nullable = false)
    private ExchangeType exchangeType;

    /**
     * The IW3 database contained two reference numbers, System & Local RefNo.
     * The System refno is a generated reference number, whereas the local is
     * there to map the Offer to the local database.<br />
     *   With IWS, there was a Board decision that the Reference numbers should
     * follow a certain format, which means that all new Offers must be
     * generated with a proper new format. To ensure that it is still possible
     * for the Staff members to map back the migrated Offers, we're storing the
     * old local value.
     */
    @Column(name = "old_refno", length = 50)
    private String oldRefno = null;

    /**
     * To help migrating the Offer information and related data, we store the
     * old Offer Id as well, this way we can better find the data later on.
     */
    @Column(name = "old_offer_id")
    private Integer oldOfferId = null;

    @Column(name = "exchange_year", length = 4)
    private Integer exchangeYear = null;

    @ManyToOne
    @JoinColumn(name = "employer_id", referencedColumnName = "id", nullable = false)
    private EmployerEntity employer = null;

    @Monitored(name="Offer work description", level = MonitoringLevel.DETAILED)
    @Column(name = "work_description", length = 3000, nullable = false)
    private String workDescription = null;

    @Monitored(name="Offer type of work", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "work_type", length = 1)
    private TypeOfWork typeOfWork = null;

    @Monitored(name="Offer weekly hours", level = MonitoringLevel.DETAILED)
    @Column(name = "weekly_hours")
    private Float weeklyHours = null;

    @Monitored(name="Offer daily hours", level = MonitoringLevel.DETAILED)
    @Column(name = "daily_hours")
    private Float dailyHours = null;

    @Monitored(name = "Offer Weekly Work Days", level = MonitoringLevel.DETAILED)
    @Column(name = "work_days_per_week")
    private Float weeklyWorkDays = null;

    @Monitored(name="Offer study levels", level = MonitoringLevel.DETAILED)
    @Column(name = "study_levels", length = 25, nullable = false)
    private String studyLevels = null;

    @Monitored(name="Offer study fields", level = MonitoringLevel.DETAILED)
    @Column(name = "study_fields", length = 1000, nullable = false)
    private String fieldOfStudies = null;

    @Monitored(name="Offer specializations", level = MonitoringLevel.DETAILED)
    @Column(name = "specializations")
    private String specializations = null;

    @Monitored(name="Offer previous training required", level = MonitoringLevel.DETAILED)
    @Column(name = "prev_training_req")
    private Boolean prevTrainingRequired = null;

    @Monitored(name="Offer other requirements", level = MonitoringLevel.DETAILED)
    @Column(name = "other_requirements", length = 4000)
    private String otherRequirements = null;

    @Monitored(name="Offer minimum weeks", level = MonitoringLevel.DETAILED)
    @Column(name = "min_weeks", nullable = false)
    private Integer minimumWeeks = null;

    @Monitored(name="Offer maximum weeks", level = MonitoringLevel.DETAILED)
    @Column(name = "max_weeks", nullable = false)
    private Integer maximumWeeks = null;

    @Monitored(name="Offer first from date", level = MonitoringLevel.DETAILED)
    @Temporal(TemporalType.DATE)
    @Column(name = "from_date", nullable = false)
    private Date fromDate = null;

    @Monitored(name="Offer first too date", level = MonitoringLevel.DETAILED)
    @Temporal(TemporalType.DATE)
    @Column(name = "to_date", nullable = false)
    private Date toDate = null;

    @Monitored(name="Offer second from date", level = MonitoringLevel.DETAILED)
    @Temporal(TemporalType.DATE)
    @Column(name = "from_date_2")
    private Date fromDate2 = null;

    @Monitored(name="Offer second too date", level = MonitoringLevel.DETAILED)
    @Temporal(TemporalType.DATE)
    @Column(name = "to_date_2")
    private Date toDate2 = null;

    @Monitored(name="Offer unavailable from date", level = MonitoringLevel.DETAILED)
    @Temporal(TemporalType.DATE)
    @Column(name = "unavailable_from")
    private Date unavailableFrom = null;

    @Monitored(name="Offer unavailable to date", level = MonitoringLevel.DETAILED)
    @Temporal(TemporalType.DATE)
    @Column(name = "unavailable_to")
    private Date unavailableTo = null;

    @Monitored(name="Offer language skill 1", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "language_1", length = 255, nullable = false)
    private Language language1 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_1_level", length = 1, nullable = false)
    private LanguageLevel language1Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_1_op", length = 1)
    private LanguageOperator language1Operator = null;

    @Monitored(name="Offer language skill 2", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "language_2", length = 255)
    private Language language2 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_2_level", length = 1)
    private LanguageLevel language2Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_2_op", length = 1)
    private LanguageOperator language2Operator = null;

    @Monitored(name="Offer language skill 3", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "language_3", length = 255)
    private Language language3 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_3_level", length = 1)
    private LanguageLevel language3Level = null;

    /**
     * need big numbers, e.g. 1 EUR = 26.435,00 VND
     */
    @Monitored(name="Offer payment", level = MonitoringLevel.DETAILED)
    @Column(name = "payment")
    private BigDecimal payment = null;

    @Monitored(name="Offer payment frequency", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_frequency", length = 10)
    private PaymentFrequency paymentFrequency = null;

    @Monitored(name="Offer payment currency", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", length = 3)
    private Currency currency = null;

    @Monitored(name="Offer payment deduction", level = MonitoringLevel.DETAILED)
    @Column(name = "deduction", length = 20)
    private String deduction = null;

    @Monitored(name="Offer living cost", level = MonitoringLevel.DETAILED)
    @Column(name = "living_cost")
    private BigDecimal livingCost = null;

    @Monitored(name="Offer living cost frequency", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "living_cost_frequency")
    private PaymentFrequency livingCostFrequency = null;

    @Monitored(name="Offer lodging by", level = MonitoringLevel.DETAILED)
    @Column(name = "lodging_by", length = 255)
    private String lodgingBy = null;

    @Monitored(name="Offer lodging cost", level = MonitoringLevel.DETAILED)
    @Column(name = "lodging_cost")
    private BigDecimal lodgingCost = null;

    @Monitored(name="Offer lodging cost frequency", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "lodging_cost_frequency", length = 10)
    private PaymentFrequency lodgingCostFrequency = null;

    @Monitored(name="Offer nomination deadline", level = MonitoringLevel.DETAILED)
    @Temporal(TemporalType.DATE)
    @Column(name = "nomination_deadline")
    private Date nominationDeadline = null;

    @Monitored(name="Offer number of hard copies", level = MonitoringLevel.DETAILED)
    @Column(name = "number_of_hard_copies")
    private Integer numberOfHardCopies = null;

    @Monitored(name="Offer Additional Information", level = MonitoringLevel.DETAILED)
    @Column(name = "additional_information", length = 3000)
    private String additionalInformation = null;

    @Monitored(name="Offer Private Comment", level = MonitoringLevel.DETAILED)
    @Column(name = "private_comment", length = 10000)
    private String privateComment = null;

    @Monitored(name="Offer status", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25)
    private OfferState status = OfferState.NEW;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created = new Date();

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    public void setOldRefno(final String oldRefno) {
        this.oldRefno = oldRefno;
    }

    public String getOldRefno() {
        return oldRefno;
    }

    public void setOldOfferId(final Integer oldOfferId) {
        this.oldOfferId = oldOfferId;
    }

    public Integer getOldOfferId() {
        return oldOfferId;
    }

    public void setExchangeYear(final Integer exchangeYear) {
        this.exchangeYear = exchangeYear;
    }

    public Integer getExchangeYear() {
        return exchangeYear;
    }

    public void setEmployer(final EmployerEntity employer) {
        this.employer = employer;
    }

    public EmployerEntity getEmployer() {
        return employer;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModified(final Date modified) {
        this.modified = modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getModified() {
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getCreated() {
        return created;
    }

    // =========================================================================
    // Other Methods required for this Entity
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final OfferEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final OfferEntity obj) {
        // don't merge if objects are not the same entity
        if ((id != null) && (obj != null) && externalId.equals(obj.externalId)) {
            // Note, Id, ExternalId & refno are *not* allowed to be updated!
            // Also note, oldOfferId and oldRefNo are *not* allowed to be updated!
            offerType = obj.offerType;
            exchangeType = obj.exchangeType;

            // General Work Description
            workDescription = obj.workDescription;
            typeOfWork = obj.typeOfWork;
            studyLevels = obj.studyLevels;
            weeklyHours = obj.weeklyHours;
            dailyHours = obj.dailyHours;
            weeklyWorkDays = obj.weeklyWorkDays;
            fieldOfStudies = obj.fieldOfStudies;
            specializations = obj.specializations;
            prevTrainingRequired = obj.prevTrainingRequired;
            otherRequirements = obj.otherRequirements;

            // DatePeriod for the Offer
            minimumWeeks = obj.minimumWeeks;
            maximumWeeks = obj.maximumWeeks;
            fromDate = obj.fromDate;
            toDate = obj.toDate;
            fromDate2 = obj.fromDate2;
            toDate2 = obj.toDate2;
            unavailableFrom = obj.unavailableFrom;
            unavailableTo = obj.unavailableTo;

            // Language restrictions
            language1 = obj.language1;
            language1Level = obj.language1Level;
            language1Operator = obj.language1Operator;
            language2 = obj.language2;
            language2Level = obj.language2Level;
            language2Operator = obj.language2Operator;
            language3 = obj.language3;
            language3Level = obj.language3Level;

            // Payment & Cost information
            payment = obj.payment;
            paymentFrequency = obj.paymentFrequency;
            currency = obj.currency;
            deduction = obj.deduction;
            livingCost = obj.livingCost;
            livingCostFrequency = obj.livingCostFrequency;
            lodgingBy = obj.lodgingBy;
            lodgingCost = obj.lodgingCost;
            lodgingCostFrequency = obj.lodgingCostFrequency;

            // Other things
            nominationDeadline = obj.nominationDeadline;
            numberOfHardCopies = obj.numberOfHardCopies;
            additionalInformation = obj.additionalInformation;
            privateComment = obj.privateComment;
            status = obj.status;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<NotificationField, String> prepareNotifiableFields(final NotificationType type) {
        return new EnumMap<>(NotificationField.class);
    }
}
