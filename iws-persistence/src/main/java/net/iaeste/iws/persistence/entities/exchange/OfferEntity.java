/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.Updateable;

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
 * @since   1.7
 * @noinspection OverlyComplexClass, OverlyLongMethod
 */
@NamedQueries({
        @NamedQuery(
                name = "offer.findAllForGroup",
                query = "select o from OfferEntity o " +
                        "where o.group.id = :gid"),
        @NamedQuery(
                name = "offer.findByGroupAndExternalIdAndRefNo",
                query = "select o from OfferEntity o " +
                        "where o.group.id = :gid" +
                        "  and o.externalId = :eoid" +
                        "  and o.refNo = :refno"),
        @NamedQuery(
                name = "offer.findByGroupAndId",
                query = "select o from OfferEntity o " +
                        "where o.group.id = :gid" +
                        "  and o.id = :id"),
        @NamedQuery(
                name = "offer.findByGroupAndIds",
                query = "select o from OfferEntity o " +
                        "where o.group.id = :gid" +
                        "  and o.id in :ids"),
        @NamedQuery(
                name = "offer.findByGroupAndExternalIds",
                query = "select o from OfferEntity o " +
                        "where o.group.id = :gid" +
                        "  and o.externalId in :eoids"),
        @NamedQuery(
                name = "offer.findByGroupAndRefNo",
                query = "select o from OfferEntity o " +
                        "where o.group.id = :gid" +
                        "  and o.refNo = :refNo"),
        @NamedQuery(
                name = "offer.findByGroupAndLikeEmployerName",
                query = "select o from OfferEntity o " +
                        "where o.group.id = :gid" +
                        "  and o.employer.name like :employer"),
        @NamedQuery(
                name = "offer.findByGroupAndEmployerName",
                query = "select o from OfferEntity o " +
                        "where o.group.id = :gid" +
                        "  and o.employer.name = :employer"),
        @NamedQuery(
                name = "offer.deleteByGroupAndId",
                query = "delete from OfferEntity o " +
                        "where o.group.id = :gid" +
                        "  and o.id = :id"),
        @NamedQuery(
                name = "offer.deleteByGroupAndIds",
                query = "delete from OfferEntity o " +
                        "where o.group.id = :gid" +
                        "  and o.id in :ids")
})
@Entity
@Table(name = "offers")
public class OfferEntity implements Updateable<OfferEntity>, Notifiable {

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

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(nullable = true, name = "group_id")
    private GroupEntity group = null;

    // TODO 2013-08-13 by Kim - Reason: The field is fairly large, and thus implies it is not correctly mapped. Please check actual length required
    @Column(name = "ref_no", length = 255, nullable = false, unique = true)
    private String refNo = null;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer = null;

    @Column(name = "work_description", nullable = false, length = 1000)
    private String workDescription = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_type", length = 1)
    private TypeOfWork typeOfWork = null;

    @Column(name = "study_levels", length = 25, nullable = false)
    private String studyLevels = null;

    @Column(name = "study_fields", nullable = false)
    private String fieldOfStudies = null;

    @Column(name = "specializations")
    private String specializations = null;

    @Column(name = "prev_training_req")
    private Boolean prevTrainingRequired = null;

    @Column(name = "other_requirements", length = 500)
    private String otherRequirements = null;

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

    /**
     * need big numbers, e.g. 1 EUR = 26.435,00 VND
     */
    @Column(name = "payment", scale = 12, precision = 2)
    private BigDecimal payment = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_frequency", length = 10)
    private PaymentFrequency paymentFrequency = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", length = 3)
    private Currency currency = null;

    @Column(name = "deduction", length = 20)
    private String deduction = null;

    @Column(name = "living_cost", scale = 12, precision = 2)
    private BigDecimal livingCost = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "living_cost_frequency")
    private PaymentFrequency livingCostFrequency = null;

    @Column(name = "lodging_by")
    private String lodgingBy = null;

    @Column(name = "lodging_cost", scale = 12, precision = 2)
    private BigDecimal lodgingCost = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "lodging_cost_frequency", length = 10)
    private PaymentFrequency lodgingCostFrequency = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "nomination_deadline")
    private Date nominationDeadline = null;

    @Column(name = "number_of_hard_copies")
    private Integer numberOfHardCopies = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
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

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setRefNo(final String refNo) {
        this.refNo = refNo;
    }

    public String getRefNo() {
        return refNo;
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

            // General Work Description
            workDescription = obj.workDescription;
            typeOfWork = obj.typeOfWork;
            studyLevels = obj.studyLevels;
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
