/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static net.iaeste.iws.api.util.Copier.copy;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.exchange.IWSExchangeConstants;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.api.util.DateTime;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
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

    private static final int FIELD_LENGTH = 100;

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
    private Boolean lodgingByIaeste = false;

    // Language is already part of the Student Object
    private Language language1 = null;
    private LanguageLevel language1Level = null;
    private Language language2 = null;
    private LanguageLevel language2Level = null;
    private Language language3 = null;
    private LanguageLevel language3Level = null;

    // The internshop period is added as an "availability period" in the Student Object
    private DatePeriod available = null;

    // Field of Studies & Specializations are part of the Student Object
    private Set<FieldOfStudy> fieldOfStudies = EnumSet.noneOf(FieldOfStudy.class);
    private List<String> specializations = new ArrayList<>(0);

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

    private String rejectByEmployerReason = null;
    private String rejectDescription = null;
    private String rejectInternalComment = null;

    private StudentAcceptance acceptance = null;
    private StudentAcceptanceConfirmation travelInformation = null;

    private DateTime nominatedAt = null;

    /**
     * Files are attached to an Application as a List, meaning that it is
     * possible to have an arbitrary number of Files as part of the Application.
     */
    private List<File> attachments = new ArrayList<>(0);

    private DateTime modified = null;
    private DateTime created = null;

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
            homeAddress = new Address(studentApplication.homeAddress);
            email = studentApplication.email;
            phoneNumber = studentApplication.phoneNumber;
            addressDuringTerms = new Address(studentApplication.addressDuringTerms);
            dateOfBirth = studentApplication.dateOfBirth;
            university = studentApplication.university;
            placeOfBirth = studentApplication.placeOfBirth;
            completedYearsOfStudy = studentApplication.completedYearsOfStudy;
            totalYearsOfStudy = studentApplication.totalYearsOfStudy;
            lodgingByIaeste = studentApplication.lodgingByIaeste;
            language1 = studentApplication.language1;
            language1Level = studentApplication.language1Level;
            language2 = studentApplication.language2;
            language2Level = studentApplication.language2Level;
            language3 = studentApplication.language3;
            language3Level = studentApplication.language3Level;
            available = copy(studentApplication.available);
            fieldOfStudies = copy(studentApplication.fieldOfStudies);
            specializations = copy(studentApplication.specializations);
            passportNumber = studentApplication.passportNumber;
            passportPlaceOfIssue = studentApplication.passportPlaceOfIssue;
            passportValidUntil = studentApplication.passportValidUntil;
            rejectByEmployerReason = studentApplication.rejectByEmployerReason;
            rejectDescription = studentApplication.rejectDescription;
            rejectInternalComment = studentApplication.rejectInternalComment;
            acceptance = new StudentAcceptance(studentApplication.acceptance);
            travelInformation = new StudentAcceptanceConfirmation(studentApplication.travelInformation);
            nominatedAt = studentApplication.nominatedAt;
            attachments = copy(studentApplication.attachments);
            modified = studentApplication.modified;
            created = studentApplication.created;
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
        this.offer = new Offer(offer);
    }

    public Offer getOffer() {
        return new Offer(offer);
    }

    public void setStudent(final Student student) {
        this.student = new Student(student);
    }

    public Student getStudent() {
        return new Student(student);
    }

    public void setStatus(final ApplicationStatus status) {
        this.status = status;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setHomeAddress(final Address homeAddress) {
        //ensureNotNullAndVerifiable("homeAddress", homeAddress);
        // Optional, please see Trac Ticket #512
        ensureVerifiable("homeAddress", homeAddress);
        this.homeAddress = new Address(homeAddress);
    }

    public Address getHomeAddress() {
        return new Address(homeAddress);
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEmail() {
        ensureNotTooLong("email", email, FIELD_LENGTH);
        return email;
    }

    public void setPhoneNumber(final String phoneNumber) {
        ensureNotTooLong("phoneNumber", phoneNumber, 25);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddressDuringTerms(final Address addressDuringTerms) {
        // The Address during term should not be mandatory, since there are
        // people who stay "at home" during term.
        ensureVerifiable("addressDuringTerms", addressDuringTerms);
        this.addressDuringTerms = new Address(addressDuringTerms);
    }

    public Address getAddressDuringTerms() {
        return new Address(addressDuringTerms);
    }

    public void setDateOfBirth(final Date dateOfBirth) {
        this.dateOfBirth = copy(dateOfBirth);
    }

    public Date getDateOfBirth() {
        return copy(dateOfBirth);
    }

    public void setUniversity(final String university) {
        ensureNotTooLong("university", university, FIELD_LENGTH);
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setPlaceOfBirth(final String placeOfBirth) {
        ensureNotTooLong("placeOfBirth", placeOfBirth, FIELD_LENGTH);
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

    public void setIsLodgingByIaeste(final Boolean lodgingByIaeste) {
        this.lodgingByIaeste = lodgingByIaeste;
    }

    public Boolean getIsLodgingByIaeste() {
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

    public void setAvailable(final DatePeriod available) {
        this.available = copy(available);
    }

    public DatePeriod getAvailable() {
        return copy(available);
    }

    public void setFieldOfStudies(final Set<FieldOfStudy> fieldOfStudies) {
        ensureNotTooLong("fieldOfStudies", fieldOfStudies, IWSExchangeConstants.MAX_OFFER_FIELDS_OF_STUDY);
        this.fieldOfStudies = copy(fieldOfStudies);
    }

    public Set<FieldOfStudy> getFieldOfStudies() {
        return copy(fieldOfStudies);
    }

    public void setSpecializations(final List<String> specializations) throws IllegalArgumentException {
        ensureNotNullOrTooLong("specializations", specializations, IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS);
        this.specializations = copy(specializations);
    }

    public List<String> getSpecializations() {
        return copy(specializations);
    }

    public void setPassportNumber(final String passportNumber) {
        ensureNotTooLong("passportNumber", passportNumber, FIELD_LENGTH);
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportPlaceOfIssue(final String passportPlaceOfIssue) {
        ensureNotTooLong("passportPlaceOfIssue", passportPlaceOfIssue, FIELD_LENGTH);
        this.passportPlaceOfIssue = passportPlaceOfIssue;
    }

    public String getPassportPlaceOfIssue() {
        return passportPlaceOfIssue;
    }

    public void setPassportValidUntil(final String passportValidUntil) {
        ensureNotTooLong("passportValidUntil", passportValidUntil, FIELD_LENGTH);
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

    public void setAcceptance(final StudentAcceptance acceptance) {
        this.acceptance = new StudentAcceptance(acceptance);
    }

    public StudentAcceptance getAcceptance() {
        return new StudentAcceptance(acceptance);
    }

    public void setTravelInformation(final StudentAcceptanceConfirmation travelInformation) {
        this.travelInformation = new StudentAcceptanceConfirmation(travelInformation);
    }

    public StudentAcceptanceConfirmation getTravelInformation() {
        return new StudentAcceptanceConfirmation(travelInformation);
    }

    public void setNominatedAt(final DateTime nominatedAt) {
        this.nominatedAt = nominatedAt;
    }

    public DateTime getNominatedAt() {
        return nominatedAt;
    }

    /**
     * Adds Attachments to the Application. If the List is null, then the method
     * will throw an {@code IllegalArgumentException}.
     *
     * @param attachments Attachments
     * @throws IllegalArgumentException if the attachments are null
     */
    public void setAttachments(final List<File> attachments) throws  IllegalArgumentException {
        ensureNotNull("attachments", attachments);
        this.attachments = copy(attachments);
    }

    public List<File> getAttachments() {
        return copy(attachments);
    }

    public void setModified(final DateTime modified) {
        this.modified = copy(modified);
    }

    public DateTime getModified() {
        return copy(modified);
    }

    public void setCreated(final DateTime created) {
        this.created = copy(created);
    }

    public DateTime getCreated() {
        return copy(created);
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
        // Made optional, please see Trac ticket #512
        //isNotNull(validation, "homeAddress", homeAddress);

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

        if (status != null ? status != studentApplication.status : studentApplication.status != null) {
            return false;
        }

        if (homeAddress != null ? !homeAddress.equals(studentApplication.homeAddress) : studentApplication.homeAddress != null) {
            return false;
        }

        if (email != null ? !email.equals(studentApplication.email) : studentApplication.email != null) {
            return false;
        }

        if (phoneNumber != null ? !phoneNumber.equals(studentApplication.phoneNumber) : studentApplication.phoneNumber != null) {
            return false;
        }

        if (addressDuringTerms != null ? !addressDuringTerms.equals(studentApplication.addressDuringTerms) : studentApplication.addressDuringTerms != null) {
            return false;
        }

        if (dateOfBirth != null ? !dateOfBirth.equals(studentApplication.dateOfBirth) : studentApplication.dateOfBirth != null) {
            return false;
        }

        if (university != null ? !university.equals(studentApplication.university) : studentApplication.university != null) {
            return false;
        }

        if (placeOfBirth != null ? !placeOfBirth.equals(studentApplication.placeOfBirth) : studentApplication.placeOfBirth != null) {
            return false;
        }

        if (completedYearsOfStudy != null ? !completedYearsOfStudy.equals(studentApplication.completedYearsOfStudy) : studentApplication.completedYearsOfStudy != null) {
            return false;
        }

        if (totalYearsOfStudy != null ? !totalYearsOfStudy.equals(studentApplication.totalYearsOfStudy) : studentApplication.totalYearsOfStudy != null) {
            return false;
        }

        if (lodgingByIaeste != studentApplication.lodgingByIaeste) {
            return false;
        }

        if (language1 != null ? language1 != studentApplication.language1 : studentApplication.language1 != null) {
            return false;
        }

        if (language1Level != null ? language1Level != studentApplication.language1Level : studentApplication.language1Level != null) {
            return false;
        }

        if (language2 != null ? language2 != studentApplication.language2 : studentApplication.language2 != null) {
            return false;
        }

        if (language2Level != null ? language2Level != studentApplication.language2Level : studentApplication.language2Level != null) {
            return false;
        }

        if (language3 != null ? language3 != studentApplication.language3 : studentApplication.language3 != null) {
            return false;
        }

        if (language3Level != null ? language3Level != studentApplication.language3Level : studentApplication.language3Level != null) {
            return false;
        }

        if (available != null ? !available.equals(studentApplication.available) : studentApplication.available != null) {
            return false;
        }

        if (fieldOfStudies != null ? !fieldOfStudies.equals(studentApplication.fieldOfStudies) : studentApplication.fieldOfStudies != null) {
            return false;
        }

        if (specializations != null ? !specializations.equals(studentApplication.specializations) : studentApplication.specializations != null) {
            return false;
        }

        if (passportNumber != null ? !passportNumber.equals(studentApplication.passportNumber) : studentApplication.passportNumber != null) {
            return false;
        }

        if (passportPlaceOfIssue != null ? !passportPlaceOfIssue.equals(studentApplication.passportPlaceOfIssue) : studentApplication.passportPlaceOfIssue != null) {
            return false;
        }

        if (passportValidUntil != null ? !passportValidUntil.equals(studentApplication.passportValidUntil) : studentApplication.passportValidUntil != null) {
            return false;
        }

        if (rejectByEmployerReason != null ? !rejectByEmployerReason.equals(studentApplication.rejectByEmployerReason) : studentApplication.rejectByEmployerReason != null) {
            return false;
        }

        if (rejectDescription != null ? !rejectDescription.equals(studentApplication.rejectDescription) : studentApplication.rejectDescription != null) {
            return false;
        }

        if (rejectInternalComment!= null ? !rejectInternalComment.equals(studentApplication.rejectInternalComment) : studentApplication.rejectInternalComment != null) {
            return false;
        }

        if (acceptance != null ? !acceptance.equals(studentApplication.acceptance) : studentApplication.acceptance != null) {
            return false;
        }

        if (travelInformation != null ? !travelInformation.equals(studentApplication.travelInformation) : studentApplication.travelInformation != null) {
            return false;
        }

        if (modified != null ? !modified.equals(studentApplication.modified) : studentApplication.modified != null) {
            return false;
        }

        if (created != null ? !created.equals(studentApplication.created) : studentApplication.created != null) {
            return false;
        }

        return !(nominatedAt != null ? !nominatedAt.equals(studentApplication.nominatedAt) : studentApplication.nominatedAt != null);
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
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (homeAddress != null ? homeAddress.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (email != null ? email.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (addressDuringTerms != null ? addressDuringTerms.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (university != null ? university.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (placeOfBirth != null ? placeOfBirth.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (completedYearsOfStudy != null ? completedYearsOfStudy.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (totalYearsOfStudy != null ? totalYearsOfStudy.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (lodgingByIaeste ? 1 : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language1 != null ? language1.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language1Level != null ? language1Level.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language2 != null ? language2.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language2Level != null ? language2Level.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language3 != null ? language3.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (language3Level != null ? language3Level.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (available != null ? available.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (fieldOfStudies != null ? fieldOfStudies.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (specializations != null ? specializations.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (passportNumber != null ? passportNumber.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (passportPlaceOfIssue != null ?  passportPlaceOfIssue.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (passportValidUntil != null ? passportValidUntil.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (acceptance != null ? acceptance.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (travelInformation != null ? travelInformation.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (nominatedAt != null ? nominatedAt.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (modified != null ? modified.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (created != null ? created.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "StudentApplication{" +
                "applicationId='" + applicationId + '\'' +
                ", offer='" + offer + '\'' +
                ", student='" + student + '\'' +
                ", status ='" + status + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", addressDuringTerms='" + addressDuringTerms + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", university='" + university + '\'' +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", completedYearsOfStudy='" + completedYearsOfStudy + '\'' +
                ", totalYearsOfStudy='" + totalYearsOfStudy + '\'' +
                ", lodgingByIaeste='" + lodgingByIaeste + '\'' +
                ", language1='" + language1 + '\'' +
                ", language1Level='" + language1Level + '\'' +
                ", language2='" + language2 + '\'' +
                ", language2Level='" + language2Level + '\'' +
                ", language3='" + language3 + '\'' +
                ", language3Level='" + language3Level + '\'' +
                ", available='" + available + '\'' +
                ", fieldOfStudies='" + fieldOfStudies + '\'' +
                ", specializations='" + specializations + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", passportPlaceOfIssue='" + passportPlaceOfIssue + '\'' +
                ", passportValidUntil='" + passportValidUntil + '\'' +
                ", acceptance='" + acceptance + '\'' +
                ", travelInformation='" + travelInformation + '\'' +
                ", nominatedAt='" + nominatedAt + '\'' +
                ", modified='" + modified + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}
