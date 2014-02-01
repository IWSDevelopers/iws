/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.EmbeddedStudent
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
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.StudyLevel;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Embeddable
public class EmbeddedStudent {

    @Enumerated(EnumType.STRING)
    @Column(name = "student_study_level", insertable = false, updatable = false)
    private StudyLevel studyLevel = null;

    @Column(name = "student_study_fields", insertable = false, updatable = false)
    private String fieldOfStudies = null;

    @Column(name = "student_specializations", insertable = false, updatable = false)
    private String specializations = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "student_available_from", insertable = false, updatable = false)
    private Date availableFrom = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "student_available_to", insertable = false, updatable = false)
    private Date availableTo = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_language_1", insertable = false, updatable = false)
    private Language language1 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_language_1_level", insertable = false, updatable = false)
    private LanguageLevel language1Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_language_2", insertable = false, updatable = false)
    private Language language2 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_language_2_level", insertable = false, updatable = false)
    private LanguageLevel language2Level = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_language_3", insertable = false, updatable = false)
    private Language language3 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_language_3_level", insertable = false, updatable = false)
    private LanguageLevel language3Level = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "student_modified", insertable = false, updatable = false)
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "student_created", insertable = false, updatable = false)
    private Date created = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

    public void setStudyLevel(final StudyLevel studyLevel) {
        this.studyLevel = studyLevel;
    }

    public StudyLevel getStudyLevel() {
        return studyLevel;
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

    public void setAvailableFrom(final Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Date getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableTo(final Date availableTo) {
        this.availableTo = availableTo;
    }

    public Date getAvailableTo() {
        return availableTo;
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
