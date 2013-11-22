/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.entities.AbstractUpdateable;
import net.iaeste.iws.persistence.entities.AddressEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@NamedQueries({
        @NamedQuery(name = "application.findByExternalId",
                query = "select a from ApplicationEntity a " +
                        "where a.externalId = :eid"),
        @NamedQuery(name = "application.findByOfferId",
                query = "select a from ApplicationEntity a " +
                        "where a.offer.id = :oid")
})
@Entity
@Table(name = "student_applications")
public class ApplicationEntity extends AbstractUpdateable<ApplicationEntity> implements Externable<ApplicationEntity> {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "offer_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Column(name = "external_id", length = 36, unique = true, nullable = false, updatable = false)
    private String externalId = null;

    @ManyToOne(targetEntity = OfferEntity.class)
    @JoinColumn(name = "offer_id", nullable = false, updatable = false)
    private OfferEntity offer = null;

    @ManyToOne(targetEntity = StudentEntity.class)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25, nullable = false)
    private ApplicationStatus status = null;

//    @ManyToOne(targetEntity = UserEntity.class)
//    @JoinColumn(name = "modified_by", nullable = false)
//    private UserEntity modifiedBy = null;

//    @ManyToOne(targetEntity = UserEntity.class)
//    @JoinColumn(name = "created_by", nullable = false)
//    private UserEntity createdBy = null;

    @ManyToOne(targetEntity = AddressEntity.class)
    @JoinColumn(name = "homeAddress_id", nullable = false, updatable = true)
    private AddressEntity homeAddress = null;

    @Column(name = "email")
    private String email = null;

    @Column(name = "phoneNumber")
    private String phoneNumber = null;

    @ManyToOne(targetEntity = AddressEntity.class)
    @JoinColumn(name = "addressDuringTerms_id", nullable = false, updatable = true)
    private AddressEntity addressDuringTerms = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "dateOfBirth")
    private Date dateOfBirth = null;

    @Column(name = "university")
    private String university = null;

    @Column(name = "placeOfBirth")
    private String placeOfBirth = null;

    @Column(name = "completedYearsOfStudy")
    private Integer completedYearsOfStudy = null;

    @Column(name = "totalYearsOfStudy")
    private Integer totalYearsOfStudy = null;

    @Column(name = "lodgingByIaeste")
    private boolean lodgingByIaeste = false;

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
    @Column(name = "internshipStart")
    private Date internshipStart = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "internshipEnd")
    private Date internshipEnd = null;

    @Column(name = "study_fields", length = 1000)
    private String fieldOfStudies = null;

    @Column(name = "specializations")
    private String specializations = null;

    @Column(name = "passportNumber")
    private String passportNumber = null;

    @Column(name = "passportPlaceOfIssue")
    private String passportPlaceOfIssue = null;

    @Column(name = "passportValidUntil")
    private String passportValidUntil = null;

//    @OneToOne(targetEntity = StudentAcceptanceEntity.class)
//    @JoinColumn(name = "acceptance", nullable = true)
//    private StudentAcceptanceEntity acceptance = null;

//    @OneToOne(targetEntity = StudentAcceptanceConfirmationEntity.class)
//    @JoinColumn(name = "travelInformation", nullable = true)
//    private StudentAcceptanceConfirmationEntity travelInformation = null;

    @Temporal(TemporalType.TIMESTAMP)
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

    public void setOffer(final OfferEntity offer) {
        this.offer = offer;
    }

    public OfferEntity getOffer() {
        return offer;
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

    public AddressEntity getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(AddressEntity homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AddressEntity getAddressDuringTerms() {
        return addressDuringTerms;
    }

    public void setAddressDuringTerms(AddressEntity addressDuringTerms) {
        this.addressDuringTerms = addressDuringTerms;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Integer getCompletedYearsOfStudy() {
        return completedYearsOfStudy;
    }

    public void setCompletedYearsOfStudy(Integer completedYearsOfStudy) {
        this.completedYearsOfStudy = completedYearsOfStudy;
    }

    public Integer getTotalYearsOfStudy() {
        return totalYearsOfStudy;
    }

    public void setTotalYearsOfStudy(Integer totalYearsOfStudy) {
        this.totalYearsOfStudy = totalYearsOfStudy;
    }

    public boolean getIsLodgingByIaeste() {
        return lodgingByIaeste;
    }

    public void setIsLodgingByIaeste(boolean lodgingByIaeste) {
        this.lodgingByIaeste = lodgingByIaeste;
    }

    public Language getLanguage1() {
        return language1;
    }

    public void setLanguage1(Language language1) {
        this.language1 = language1;
    }

    public LanguageLevel getLanguage1Level() {
        return language1Level;
    }

    public void setLanguage1Level(LanguageLevel language1Level) {
        this.language1Level = language1Level;
    }

    public Language getLanguage2() {
        return language2;
    }

    public void setLanguage2(Language language2) {
        this.language2 = language2;
    }

    public LanguageLevel getLanguage2Level() {
        return language2Level;
    }

    public void setLanguage2Level(LanguageLevel language2Level) {
        this.language2Level = language2Level;
    }

    public Language getLanguage3() {
        return language3;
    }

    public void setLanguage3(Language language3) {
        this.language3 = language3;
    }

    public LanguageLevel getLanguage3Level() {
        return language3Level;
    }

    public void setLanguage3Level(LanguageLevel language3Level) {
        this.language3Level = language3Level;
    }

    public Date getInternshipStart() {
        return internshipStart;
    }

    public void setInternshipStart(Date internshipStart) {
        this.internshipStart = internshipStart;
    }

    public Date getInternshipEnd() {
        return internshipEnd;
    }

    public void setInternshipEnd(Date internshipEnd) {
        this.internshipEnd = internshipEnd;
    }

    public String getFieldOfStudies() {
        return fieldOfStudies;
    }

    public void setFieldOfStudies(String fieldOfStudies) {
        this.fieldOfStudies = fieldOfStudies;
    }

    public String getSpecializations() {
        return specializations;
    }

    public void setSpecializations(String specializations) {
        this.specializations = specializations;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportPlaceOfIssue() {
        return passportPlaceOfIssue;
    }

    public void setPassportPlaceOfIssue(String passportPlaceOfIssue) {
        this.passportPlaceOfIssue = passportPlaceOfIssue;
    }

    public String getPassportValidUntil() {
        return passportValidUntil;
    }

    public void setPassportValidUntil(String passportValidUntil) {
        this.passportValidUntil = passportValidUntil;
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
        }
    }
}
