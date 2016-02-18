///*
// * =============================================================================
// * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
// * ----------------------------------------------------------------------------
// * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.Student
// * -----------------------------------------------------------------------------
// * This software is provided by the members of the IAESTE Internet Development
// * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
// * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
// *
// * This software is provided "as is"; the IDT or individuals within the IDT
// * cannot be held legally responsible for any problems the software may cause.
// * =============================================================================
// */
package net.iaeste.iws.api.dtos.exchange;
//
import net.iaeste.iws.api.constants.IWSConstants;//
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
//
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;//
import java.util.Map;//
import java.util.Set;
//
/**--
 * Standard IAESTE Student.--
 *--
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$--
 * @since   IWS 1.0
 *///
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "student", propOrder = { "user", "studyLevel", "fieldOfStudies", "specializations", "available", "language1", "language1Level", "language2", "language2Level", "language3", "language3Level", "modified", "created" })
public final class Student extends AbstractVerification {//
//
    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private User user = null;
    @XmlElement(required = true, nillable = true)  private StudyLevel studyLevel = StudyLevel.B;
    @XmlElement(required = true, nillable = true)  private Set<FieldOfStudy> fieldOfStudies = null;
    @XmlElement(required = true, nillable = true)  private Set<Specialization> specializations = null;
    @XmlElement(required = true, nillable = true)  private DatePeriod available = null;
    @XmlElement(required = true, nillable = true)  private Language language1 = null;
    @XmlElement(required = true, nillable = true)  private LanguageLevel language1Level = null;
    @XmlElement(required = true, nillable = true)  private Language language2 = null;
    @XmlElement(required = true, nillable = true)  private LanguageLevel language2Level = null;
    @XmlElement(required = true, nillable = true)  private Language language3 = null;
    @XmlElement(required = true, nillable = true)  private LanguageLevel language3Level = null;
    @XmlElement(required = true, nillable = true)  private DateTime modified = null;
    @XmlElement(required = true, nillable = true)  private DateTime created = null;
//
    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Student() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor.
     *
     * @param user User
     */
    public Student(final User user) {
        setUser(user);
    }
//
    /**
     * Copy Constructor.
     *
     * @param student Student Object to copy
     */
    public Student(final Student student) {//
        if (student != null) {//
            // We copy the Object over directly, without the checks from our
            // setters, since we trust that the previously created Student
            // Object is correct.
            user = new User(student.user);
            studyLevel = student.studyLevel;
            fieldOfStudies = student.fieldOfStudies;
            specializations = student.specializations;
            available = student.available;
            language1 = student.language1;
            language1Level = student.language1Level;
            language2 = student.language2;
            language2Level = student.language2Level;
            language3 = student.language3;
            language3Level = student.language3Level;
            modified = student.modified;
            created = student.created;
        }//
    }//
//
    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================
//
    /**
     * This getter is a simple wrapper for the UserId, since the Student is just
     * an add-on Object for the User, to provide the necessary Student
     * information.
     *
     * @return Student (User) Id
     */
    public String getStudentId() {
        return user != null ? user.getUserId() : null;
    }

    public void setUser(final User user) throws IllegalArgumentException {
        ensureVerifiable("user", user);
        this.user = new User(user);
    }//
//
    public User getUser() {
        return new User(user);
    }//
//
    public void setStudyLevel(final StudyLevel studyLevel) throws IllegalArgumentException {
        this.studyLevel = studyLevel;
    }

    public StudyLevel getStudyLevel() {
        return studyLevel;
    }

    public void setFieldOfStudies(final Set<FieldOfStudy> fieldOfStudies) throws IllegalArgumentException {
        ensureNotTooLong("fieldOfStudies", fieldOfStudies, IWSExchangeConstants.MAX_OFFER_FIELDS_OF_STUDY);
        this.fieldOfStudies = fieldOfStudies;
    }

    public Set<FieldOfStudy> getFieldOfStudies() {
        return fieldOfStudies;
    }

    public void setSpecializations(final Set<Specialization> specializations) throws IllegalArgumentException {
        ensureNotTooLong("specializations", specializations, IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS);
        this.specializations = specializations;
    }

    public Set<Specialization> getSpecializations() {
        return specializations;
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
//
    /**--
     * {@inheritDoc}
     *///
    @Override//
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);
//
        isNotNull(validation, "user", user);
//
        return validation;
    }//
}//
