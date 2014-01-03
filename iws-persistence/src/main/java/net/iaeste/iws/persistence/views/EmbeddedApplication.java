/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.EmbeddedApplication
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

import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Embeddable
public class EmbeddedApplication {
    @Column(name = "application_external_id", insertable = false, updatable = false)
    private String externalId = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status", insertable = false, updatable = false)
    private ApplicationStatus status = null;

    @Column(name = "application_email", insertable = false, updatable = false)
    private String email = null;

    @Column(name = "application_phone_number", insertable = false, updatable = false)
    private String phoneNumber = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "application_date_of_birth", insertable = false, updatable = false)
    private Date dateOfBirth = null;

    @Column(name = "application_university", insertable = false, updatable = false)
    private String university = null;

    @Column(name = "application_place_of_birth", insertable = false, updatable = false)
    private String placeOfBirth = null;

    @Column(name = "application_completed_years_of_study", insertable = false, updatable = false)
    private Integer completedYearsOfStudy = null;

    @Column(name = "application_total_years_of_study", insertable = false, updatable = false)
    private Integer totalYearsOfStudy = null;

    @Column(name = "application_lodging_by_iaeste", insertable = false, updatable = false)
    private Boolean lodgingByIaeste = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_language_1", insertable = false, updatable = false)
    private Language language1 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_language_1_level", insertable = false, updatable = false)
    private LanguageLevel language1Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_language_2", insertable = false, updatable = false)
    private Language language2 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_language_2_level", insertable = false, updatable = false)
    private LanguageLevel language2Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_language_3", insertable = false, updatable = false)
    private Language language3 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_language_3_level", insertable = false, updatable = false)
    private LanguageLevel language3Level = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "application_internship_start", insertable = false, updatable = false)
    private Date internshipStart = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "application_internship_end", insertable = false, updatable = false)
    private Date internshipEnd = null;

    @Column(name = "application_study_fields", insertable = false, updatable = false)
    private String fieldOfStudies = null;

    @Column(name = "application_specializations", insertable = false, updatable = false)
    private String specializations = null;

    @Column(name = "application_passport_number", insertable = false, updatable = false)
    private String passportNumber = null;

    @Column(name = "application_passport_place_of_issue", insertable = false, updatable = false)
    private String passportPlaceOfIssue = null;

    @Column(name = "application_passport_valid_until", insertable = false, updatable = false)
    private String passportValidUntil = null;

    @Column(name = "application_reject_by_employer_reason", insertable = false, updatable = false)
    private String rejectByEmployerReason = null;

    @Column(name = "application_reject_description", insertable = false, updatable = false)
    private String rejectDescription = null;

    @Column(name = "application_reject_internal_comment", insertable = false, updatable = false)
    private String rejectInternalComment = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "application_nominated_at", insertable = false, updatable = false)
    private Date nominatedAt = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "application_modified", insertable = false, updatable = false)
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "application_created", insertable = false, updatable = false)
    private Date created = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setStatus(final ApplicationStatus status) {
        this.status = status;
    }

    public ApplicationStatus getStatus() {
        return status;
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

    public void setLodgingByIaeste(final Boolean lodgingByIaeste) {
        this.lodgingByIaeste = lodgingByIaeste;
    }

    public Boolean isLodgingByIaeste() {
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

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }
}
