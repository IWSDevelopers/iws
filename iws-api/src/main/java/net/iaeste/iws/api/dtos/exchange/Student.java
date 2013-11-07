/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.Student
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos.exchange;

import static net.iaeste.iws.api.util.Copier.copy;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.exchange.IWSExchangeConstants;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.Specialization;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.api.util.DateTime;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Standard IAESTE Student.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Student extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private User user = null;
    private StudyLevel studyLevel = StudyLevel.B;
    private Set<FieldOfStudy> fieldOfStudies = EnumSet.noneOf(FieldOfStudy.class);
    private Set<Specialization> specializations = EnumSet.noneOf(Specialization.class);
    private DatePeriod available = null;
    private Language language1 = null;
    private LanguageLevel language1Level = null;
    private Language language2 = null;
    private LanguageLevel language2Level = null;
    private Language language3 = null;
    private LanguageLevel language3Level = null;
    private DateTime modified = null;
    private DateTime created = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Student() {
    }

    /**
     * Default Constructor.
     *
     * @param user User
     */
    public Student(final User user) {
        setUser(user);
    }

    /**
     * Copy Constructor.
     *
     * @param student Student Object to copy
     */
    public Student(final Student student) {
        if (student != null) {
            setUser(student.user);
            available = student.available;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUser(final User user) throws IllegalArgumentException {
        ensureVerifiable("user", user);
        this.user = new User(user);
    }

    public User getUser() {
        return new User(user);
    }

    public void setStudyLevel(final StudyLevel studyLevel) throws IllegalArgumentException {
        this.studyLevel = copy(studyLevel);
    }

    public StudyLevel getStudyLevel() {
        return studyLevel;
    }

    public void setFieldOfStudies(final Set<FieldOfStudy> fieldOfStudies) throws IllegalArgumentException {
        ensureNotTooLong("fieldOfStudies", fieldOfStudies, IWSExchangeConstants.MAX_OFFER_FIELDS_OF_STUDY);
        this.fieldOfStudies = copy(fieldOfStudies);
    }

    public Set<FieldOfStudy> getFieldOfStudies() {
        return copy(fieldOfStudies);
    }

    public void setSpecializations(final Set<Specialization> specializations) throws IllegalArgumentException {
        ensureNotTooLong("specializations", specializations, IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS);
        this.specializations = copy(specializations);
    }

    public Set<Specialization> getSpecializations() {
        return copy(specializations);
    }

    public void setAvailable(final DatePeriod available) {
        this.available = new DatePeriod(available);
    }

    public DatePeriod getAvailable() {
        return new DatePeriod(available);
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
     * Sets the Student latest modification DateTime. Note, this field is
     * controlled by the IWS, and cannot be altered by users.
     *
     * @param modified DateTime of latest modification
     */
    public void setModified(final DateTime modified) {
        this.modified = modified;
    }

    public DateTime getModified() {
        return modified;
    }

    /**
     * Sets the Student Creation DateTime. Note, this field is controlled by the
     * IWS, and cannot be altered by users.
     *
     * @param created Offer Creation DateTime
     */
    public void setCreated(final DateTime created) {
        this.created = created;
    }

    public DateTime getCreated() {
        return created;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "user", user);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Student)) {
            return false;
        }

        final Student student = (Student) obj;

        if (user != null ? !user.equals(student.user) : student.user != null) {
            return false;
        }
        if (studyLevel != null ? studyLevel != student.studyLevel : student.studyLevel != null) {
            return false;
        }
        if (fieldOfStudies != null ? !fieldOfStudies.equals(student.fieldOfStudies) : student.fieldOfStudies != null) {
            return false;
        }
        if (specializations != null ? !specializations.equals(student.specializations) : student.specializations != null) {
            return false;
        }
        if (available != null ? !available.equals(student.available) : student.available != null) {
            return false;
        }
        if (language1 != student.language1) {
            return false;
        }
        if (language1Level != student.language1Level) {
            return false;
        }
        if (language2 != student.language2) {
            return false;
        }
        if (language2Level != student.language2Level) {
            return false;
        }
        if (language3 != student.language3) {
            return false;
        }
        if (language3Level != student.language3Level) {
            return false;
        }
        if (modified != null ? !modified.equals(student.modified) : student.modified != null) {
            return false;
        }
        return !(created != null ? !created.equals(student.created) : student.created != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (user != null ? user.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (studyLevel != null ? studyLevel.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (fieldOfStudies != null ? fieldOfStudies.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (specializations != null ? specializations.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (available != null ? available.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language1 != null ? language1.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language1Level != null ? language1Level.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language2 != null ? language2.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language2Level != null ? language2Level.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language3 != null ? language3.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (language3Level != null ? language3Level.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (modified != null ? modified.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (created != null ? created.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Student{" +
                "user=" + user +
                ", studyLevels=" + studyLevel +
                ", fieldOfStudies=" + fieldOfStudies +
                ", specializations=" + specializations +
                ", available=" + available +
                ", language1=" + language1 +
                ", language1Level=" + language1Level +
                ", language2=" + language2 +
                ", language2Level=" + language2Level +
                ", language3=" + language3 +
                ", language3Level=" + language3Level +
                ", modified=" + modified +
                ", created=" + created +
                '}';
    }
}
