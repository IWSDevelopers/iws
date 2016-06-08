/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.persistence.entities.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.persistence.entities.AbstractUpdateable;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.monitoring.Monitored;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "student.findById",
                query = "select s from StudentEntity s " +
                        "where s.id = :id"),
        @NamedQuery(name = "students.findByExternalIdForCountry",
                query = "select s from StudentEntity s, UserGroupEntity ug " +
                        "where ug.user.id = s.user.id" +
                        "  and ug.group.parentId = :parentId" +
                        "  and ug.user.externalId = :eid"),
        @NamedQuery(name = "student.deleteByUserId",
                query = "delete StudentEntity s " +
                        "where s.user.id = :uid")
})
@Entity
@Table(name = "students")
@Monitored(name = "Student", level = MonitoringLevel.DETAILED)
public final class StudentEntity extends AbstractUpdateable<StudentEntity> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * The Student is an add-on Object for the User. Which is why, this Object
     * contains no ExternalId, rather it relies on the User Object.
     */
    @OneToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private UserEntity user = null;

    @Monitored(name="Student study level", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "study_level", length = 25)
    private StudyLevel studyLevel = null;

    @Monitored(name="Student study fields", level = MonitoringLevel.DETAILED)
    @Column(name = "study_fields", length = 1000)
    private String fieldOfStudies = null;

    @Monitored(name="Student specializations", level = MonitoringLevel.DETAILED)
    @Column(name = "specializations")
    private String specializations = null;

    @Monitored(name="Student available from date", level = MonitoringLevel.DETAILED)
    @Temporal(TemporalType.DATE)
    @Column(name = "available_from")
    private Date availableFrom = null;

    @Monitored(name="Student available to date", level = MonitoringLevel.DETAILED)
    @Temporal(TemporalType.DATE)
    @Column(name = "available_to")
    private Date availableTo = null;

    @Monitored(name="Student language skill 1", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "language_1")
    private Language language1 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_1_level", length = 1)
    private LanguageLevel language1Level = null;

    @Monitored(name="Student language skill 2", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "language_2")
    private Language language2 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_2_level", length = 1)
    private LanguageLevel language2Level = null;

    @Monitored(name="Student language skill 3", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "language_3")
    private Language language3 = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_3_level", length = 1)
    private LanguageLevel language3Level = null;

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

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

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
    public boolean diff(final StudentEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final StudentEntity obj) {
        // don't merge if objects are not the same entity
        if ((id != null) && (obj != null) && isUserReady(user) && user.getExternalId().equals(obj.user.getExternalId())) {
            studyLevel = which(studyLevel, obj.studyLevel);
            fieldOfStudies = which(fieldOfStudies, obj.fieldOfStudies);
            specializations = which(specializations, obj.specializations);
            availableFrom = which(availableFrom, obj.availableFrom);
            availableTo = which(availableTo, obj.availableTo);
            language1 = which(language1, obj.language1);
            language1Level = which(language1Level, obj.language1Level);
            language2 = which(language2, obj.language2);
            language2Level = which(language2Level, obj.language2Level);
            language3 = which(language3, obj.language3);
            language3Level = which(language3Level, obj.language3Level);
        }
    }

    /**
     * We should not compare too many things in a single If statement, so this
     * method was extracted for clarity.
     *
     * @param user UserEntity
     * @return True if User is ready, i.e. is present and persisted
     */
    private static boolean isUserReady(final UserEntity user) {
        return (user != null) && (user.getExternalId() != null);
    }
}
