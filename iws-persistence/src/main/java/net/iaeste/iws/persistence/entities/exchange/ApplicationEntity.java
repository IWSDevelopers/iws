/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.exchange.ApplicationEntity
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

import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.entities.AbstractUpdateable;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;

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
import java.util.Date;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries({
        @NamedQuery(name = "application.findByExternalId",
                query = "select a from ApplicationEntity a " +
                        "where a.externalId = :eid"),
        @NamedQuery(name = "application.findByOfferId",
                query = "select a from ApplicationEntity a " +
                        "where a.offerGroup.offer.id = :oid"),
        @NamedQuery(name = "application.findByOfferGroupIdAndStatuses",
                query = "select a from ApplicationEntity a " +
                        "where a.offerGroup.id = :ogid " +
                        "  and a.status in :statuses")
})
@Entity
@Table(name = "student_applications")
public class ApplicationEntity extends AbstractUpdateable<ApplicationEntity> implements Externable<ApplicationEntity> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "offer_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Column(name = "external_id", length = 36, unique = true, nullable = false, updatable = false)
    private String externalId = null;

    @ManyToOne(targetEntity = OfferGroupEntity.class)
    @JoinColumn(name = "offer_group_id", nullable = false, updatable = false)
    private OfferGroupEntity offerGroup = null;

    @ManyToOne(targetEntity = StudentEntity.class)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private ApplicationStatus status = null;

//    @ManyToOne(targetEntity = UserEntity.class)
//    @JoinColumn(name = "modified_by", nullable = false)
//    private UserEntity modifiedBy = null;

//    @ManyToOne(targetEntity = UserEntity.class)
//    @JoinColumn(name = "created_by", nullable = false)
//    private UserEntity createdBy = null;

    @ManyToOne(targetEntity = AddressEntity.class)
    @JoinColumn(name = "home_address_id", updatable = true)
    private AddressEntity homeAddress = null;

    @Column(name = "email")
    private String email = null;

    @Column(name = "phone_number")
    private String phoneNumber = null;

    @ManyToOne(targetEntity = AddressEntity.class)
    @JoinColumn(name = "address_during_terms_id", updatable = true)
    private AddressEntity addressDuringTerms = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private Date dateOfBirth = null;

    @Column(name = "university")
    private String university = null;

    @Column(name = "place_of_birth")
    private String placeOfBirth = null;

    @ManyToOne(targetEntity = CountryEntity.class)
    @JoinColumn(name = "nationality", referencedColumnName = "id")
    private CountryEntity nationality = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender = null;

    @Column(name = "completed_years_of_study")
    private Integer completedYearsOfStudy = null;

    @Column(name = "total_years_of_study")
    private Integer totalYearsOfStudy = null;

    @Column(name = "lodging_by_iaeste")
    private Boolean lodgingByIaeste = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_1", length = 255)
    private Language language1 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_1_level", length = 1)
    private LanguageLevel language1Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_2", length = 255)
    private Language language2 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_2_level", length = 1)
    private LanguageLevel language2Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_3", length = 255)
    private Language language3 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_3_level", length = 1)
    private LanguageLevel language3Level = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "internship_start")
    private Date internshipStart = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "internship_end")
    private Date internshipEnd = null;

    @Column(name = "study_fields", length = 1000)
    private String fieldOfStudies = null;

    @Column(name = "specializations")
    private String specializations = null;

    @Column(name = "passport_number")
    private String passportNumber = null;

    @Column(name = "passport_place_of_issue")
    private String passportPlaceOfIssue = null;

    @Column(name = "passport_valid_until")
    private String passportValidUntil = null;

    @Column(name = "reject_by_employer_reason", length = 100)
    private String rejectByEmployerReason = null;

    @Column(name = "reject_description", length = 1000)
    private String rejectDescription = null;

    @Column(name = "reject_internal_comment", length = 1000)
    private String rejectInternalComment = null;

//    @OneToOne(targetEntity = StudentAcceptanceEntity.class)
//    @JoinColumn(name = "acceptance", nullable = true)
//    private StudentAcceptanceEntity acceptance = null;

//    @OneToOne(targetEntity = StudentAcceptanceConfirmationEntity.class)
//    @JoinColumn(name = "travel_information", nullable = true)
//    private StudentAcceptanceConfirmationEntity travelInformation = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "nominated_at", nullable = true)
    private Date nominatedAt = null;

    /**
     * Last time the Entity was modified.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified = new Date();

    /**
     * Timestamp when the Entity was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
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

    public void setOfferGroup(final OfferGroupEntity offerGroup) {
        this.offerGroup = offerGroup;
    }

    public OfferGroupEntity getOfferGroup() {
        return offerGroup;
    }

    public void setStudent(final StudentEntity student) {
        this.student = student;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStatus(final ApplicationStatus status) {
        this.status = status;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setHomeAddress(final AddressEntity homeAddress) {
        this.homeAddress = homeAddress;
    }

    public AddressEntity getHomeAddress() {
        return homeAddress;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddressDuringTerms(final AddressEntity addressDuringTerms) {
        this.addressDuringTerms = addressDuringTerms;
    }

    public AddressEntity getAddressDuringTerms() {
        return addressDuringTerms;
    }

    public void setDateOfBirth(final Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setUniversity(final String university) {
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setPlaceOfBirth(final String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setNationality(final CountryEntity nationality) {
        this.nationality = nationality;
    }

    public CountryEntity getNationality() {
        return nationality;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setCompletedYearsOfStudy(final Integer completedYearsOfStudy) {
        this.completedYearsOfStudy = completedYearsOfStudy;
    }

    public Integer getCompletedYearsOfStudy() {
        return completedYearsOfStudy;
    }

    public void setTotalYearsOfStudy(final Integer totalYearsOfStudy) {
        this.totalYearsOfStudy = totalYearsOfStudy;
    }

    public Integer getTotalYearsOfStudy() {
        return totalYearsOfStudy;
    }

    public void setIsLodgingByIaeste(final boolean lodgingByIaeste) {
        this.lodgingByIaeste = lodgingByIaeste;
    }

    public boolean getIsLodgingByIaeste() {
        return lodgingByIaeste;
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

    public void setInternshipStart(final Date internshipStart) {
        this.internshipStart = internshipStart;
    }

    public Date getInternshipStart() {
        return internshipStart;
    }

    public void setInternshipEnd(final Date internshipEnd) {
        this.internshipEnd = internshipEnd;
    }

    public Date getInternshipEnd() {
        return internshipEnd;
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

    public void setPassportNumber(final String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportPlaceOfIssue(final String passportPlaceOfIssue) {
        this.passportPlaceOfIssue = passportPlaceOfIssue;
    }

    public String getPassportPlaceOfIssue() {
        return passportPlaceOfIssue;
    }

    public void setPassportValidUntil(final String passportValidUntil) {
        this.passportValidUntil = passportValidUntil;
    }

    public String getPassportValidUntil() {
        return passportValidUntil;
    }

    public void setRejectByEmployerReason(final String rejectByEmployerReason) {
        this.rejectByEmployerReason = rejectByEmployerReason;
    }

    public String getRejectByEmployerReason() {
        return rejectByEmployerReason;
    }

    public void setRejectDescription(final String rejectDescription) {
        this.rejectDescription = rejectDescription;
    }

    public String getRejectDescription() {
        return rejectDescription;
    }

    public void setRejectInternalComment(final String rejectInternalComment) {
        this.rejectInternalComment = rejectInternalComment;
    }

    public String getRejectInternalComment() {
        return rejectInternalComment;
    }

    public void setNominatedAt(final Date nominatedAt) {
        this.nominatedAt = nominatedAt;
    }

    public Date getNominatedAt() {
        return nominatedAt;
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
    // Standard Entity Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final ApplicationEntity obj) {
        int changes = 0;

        //changes += different("aaa", "bbb");

        return changes == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final ApplicationEntity obj) {
        // don't merge if objects are not the same entity
        if ((id != null) && (obj != null) && externalId.equals(obj.externalId)) {
            status = obj.status;
            nominatedAt = obj.nominatedAt;
            fieldOfStudies = obj.fieldOfStudies;
            specializations = obj.specializations;
            email = obj.email;
            phoneNumber = obj.phoneNumber;
            dateOfBirth = obj.dateOfBirth;
            university = obj.university;
            placeOfBirth = obj.placeOfBirth;
            nationality = obj.nationality;
            gender = obj.gender;
            completedYearsOfStudy = obj.completedYearsOfStudy;
            totalYearsOfStudy = obj.totalYearsOfStudy;
            lodgingByIaeste = obj.lodgingByIaeste;
            language1 = obj.language1;
            language1Level = obj.language1Level;
            language2 = obj.language2;
            language2Level = obj.language2Level;
            language3 = obj.language3;
            language3Level = obj.language3Level;
            internshipStart = obj.internshipStart;
            internshipEnd = obj.internshipEnd;
            passportNumber = obj.passportNumber;
            passportPlaceOfIssue = obj.passportPlaceOfIssue;
            passportValidUntil = obj.passportValidUntil;
            rejectByEmployerReason = obj.rejectByEmployerReason;
            rejectDescription = obj.rejectDescription;
            rejectInternalComment = obj.rejectInternalComment;
        }
    }
}
