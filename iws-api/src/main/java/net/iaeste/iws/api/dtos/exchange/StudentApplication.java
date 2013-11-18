/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.StudentApplication
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.Specialization;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DateTime;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Contains information about a Student applying for an Offer
 * <p/>
 * The studentApplication contains the student data for the time
 * when he/she applied, therefore student information
 * are duplicated for each studentApplication.
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StudentApplication extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String applicationId = null;

    /** {@link Offer} that the {@link Student} is applying for */
    private Offer offer = null;

    /** {@link Student} as User */
    private Student student = null;

    /** Status of the {@link StudentApplication} */
    private ApplicationStatus status = null;

    // TODO Move to Student
    private Address homeAddress = null;
    private String email = null; // should be copied for an application if a student wants to use a different address for login
    private String phoneNumber = null;
    private Address addressDuringTerms = null;
    private Date dateOfBirth = null;
    private String university = null;
    private String placeOfBirth = null;
    private Integer completedYearsOfStudy = null;
    private Integer totalYearsOfStudy = null;
    private boolean lodgingByIaeste = false;

    // Language is already part of the Student Object
    private Language language1 = null;
    private LanguageLevel language1Level = null;
    private Language language2 = null;
    private LanguageLevel language2Level = null;
    private Language language3 = null;
    private LanguageLevel language3Level = null;

    // The internshop period is added as an "availability period" in the Student Object
    private Date internshipStart = null;
    private Date internshipEnd = null;

    // Field of Studies & Specializations are part of the Student Object
    private Set<FieldOfStudy> fieldOfStudies = EnumSet.noneOf(FieldOfStudy.class);
    private Set<Specialization> specializations = EnumSet.noneOf(Specialization.class);

    // TODO Critical information, what is the procedure to deal with this ?
    //   The problem is that certain countries have very strict rules regarding
    // private data, and Passport information can, together with name and
    // birthday, be used to steal identities!
    //   Suggestions, we use the RSA algorithm, where the receiving country &
    // student both provide the public keys, and we store the public keys
    // together with the encrypted values. We can also perform the actual
    // encryption/decryption operations, but under the notion that the
    // information is only provided over an encrypted channel, and that none of
    // the information is logged anywhere. Since we're talking encryption, and
    // critical information, it would be even better, if the operations were
    // performed client-side in a JavaScript. So all we store is the public keys
    // and the encrypted container.
    private String passportNumber = null;
    private String passportPlaceOfIssue = null;
    private String passportValidUntil = null;

    private StudentAcceptance acceptance = null;
    private StudentAcceptanceConfirmation travelInformation = null;

    private DateTime modified = null;
    private DateTime created = null;

    private DateTime nominatedAt = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public StudentApplication() {
    }

    /**
     * Copy Constructor.
     *
     * @param studentApplication StudentApplication Object to copy
     */
    public StudentApplication(final StudentApplication studentApplication) {
        if (studentApplication != null) {
            applicationId = studentApplication.applicationId;
            offer = new Offer(studentApplication.offer);
            student = new Student(studentApplication.student);
            status = studentApplication.status;
            nominatedAt = studentApplication.nominatedAt;
        }
    }

    // TODO add a copy of student data

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setApplicationId(final String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setOffer(final Offer offer) {
        this.offer = offer;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setStudent(final Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStatus(final ApplicationStatus status) {
        this.status = status;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setModified(final DateTime modified) {
        this.modified = modified;
    }

    public void setNominatedAt(final DateTime nominatedAt) {
        this.nominatedAt = nominatedAt;
    }

    public DateTime getNominatedAt() {
        return nominatedAt;
    }

    public DateTime getModified() {
        return modified;
    }

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
     *
     * It should be possible to create a partial application therefore
     * only studentId and offerId are validated.
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "offer", offer);
        isNotNull(validation, "student", student);

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

        if (!(obj instanceof StudentApplication)) {
            return false;
        }

        final StudentApplication studentApplication = (StudentApplication) obj;

        if (applicationId != null ? !applicationId.equals(studentApplication.applicationId) : studentApplication.applicationId != null) {
            return false;
        }

        if (offer != null ? !offer.equals(studentApplication.offer) : studentApplication.offer != null) {
            return false;
        }

        if (student != null ? !student.equals(studentApplication.student) : studentApplication.student != null) {
            return false;
        }

        if (nominatedAt != null ? !nominatedAt.equals(studentApplication.nominatedAt) : studentApplication.nominatedAt != null) {
            return false;
        }

        return !(status != null ? status != studentApplication.status : studentApplication.status != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (applicationId != null ? applicationId.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (offer != null ? offer.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (student != null ? student.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (status != null ? status.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (nominatedAt != null ? nominatedAt.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "StudentApplication{" +
                "applicationId='" + applicationId +
                ", offer='" + offer + '\'' +
                ", student='" + student + '\'' +
                ", status=" + status +
                ", nominatedAt=" + nominatedAt +
                '}';
    }
}
