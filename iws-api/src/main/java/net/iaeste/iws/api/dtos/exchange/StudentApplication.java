/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.exchange.IWSExchangeConstants;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.api.util.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Contains information about a Student applying for an Offer.</p>
 *
 * <p>The studentApplication contains the student data for the time when he/she
 * applied, therefore student information are duplicated for each
 * studentApplication.</p>
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studentApplication", propOrder = { "applicationId", "offerId", "offerState", "student", "status", "homeAddress", "email", "phoneNumber", "addressDuringTerms", "dateOfBirth", "university", "placeOfBirth", "nationality", "gender", "completedYearsOfStudy", "totalYearsOfStudy", "lodgingByIaeste", "language1", "language1Level", "language2", "language2Level", "language3", "language3Level", "available", "fieldOfStudies", "specializations", "passportNumber", "passportPlaceOfIssue", "passportValidUntil", "rejectByEmployerReason", "rejectDescription", "rejectInternalComment", "acceptance", "travelInformation", "nominatedAt", "attachments", "modified", "created" })
public final class StudentApplication extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private static final int FIELD_LENGTH = 100;

    @XmlElement(required = true, nillable = true)
    private String applicationId = null;

    /**
     * Offer Id for the {@link Offer} that the {@link Student} is
     * applying for.
     */
    @XmlElement(required = true, nillable = false) private String offerId = null;

    @XmlElement(required = true, nillable = true) private OfferState offerState = null;

    /** {@link Student} as User */
    @XmlElement(required = true, nillable = false) private Student student = null;

    /** Status of the {@link StudentApplication} */
    @XmlElement(required = true, nillable = true) private ApplicationStatus status = null;

    @XmlElement(required = true, nillable = true) private Address homeAddress = null;
    @XmlElement(required = true, nillable = true) private String email = null; // should be copied for an application if a student wants to use a different address for login
    @XmlElement(required = true, nillable = true) private String phoneNumber = null;
    @XmlElement(required = true, nillable = true) private Address addressDuringTerms = null;
    @XmlElement(required = true, nillable = true) private Date dateOfBirth = null;
    @XmlElement(required = true, nillable = true) private String university = null;
    @XmlElement(required = true, nillable = true) private String placeOfBirth = null;
    @XmlElement(required = true, nillable = true) private Country nationality = null;
    @XmlElement(required = true, nillable = true) private Gender gender = null;
    @XmlElement(required = true, nillable = true) private Integer completedYearsOfStudy = null;
    @XmlElement(required = true, nillable = true) private Integer totalYearsOfStudy = null;
    @XmlElement(required = true, nillable = true) private Boolean lodgingByIaeste = false;

    // Language is already part of the Student Object
    @XmlElement(required = true, nillable = true) private Language language1 = null;
    @XmlElement(required = true, nillable = true) private LanguageLevel language1Level = null;
    @XmlElement(required = true, nillable = true) private Language language2 = null;
    @XmlElement(required = true, nillable = true) private LanguageLevel language2Level = null;
    @XmlElement(required = true, nillable = true) private Language language3 = null;
    @XmlElement(required = true, nillable = true) private LanguageLevel language3Level = null;

    // The internship period is added as an "availability period" in the Student Object
    @XmlElement(required = true, nillable = true) private DatePeriod available = null;

    // Field of Studies & Specializations are part of the Student Object
    @XmlElement(required = true, nillable = true) private Set<FieldOfStudy> fieldOfStudies = EnumSet.noneOf(FieldOfStudy.class);
    @XmlElement(required = true, nillable = true) private List<String> specializations = new ArrayList<>(0);

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
    @XmlElement(required = true, nillable = true) private String passportNumber = null;
    @XmlElement(required = true, nillable = true) private String passportPlaceOfIssue = null;
    @XmlElement(required = true, nillable = true) private String passportValidUntil = null;

    @XmlElement(required = true, nillable = true) private String rejectByEmployerReason = null;
    @XmlElement(required = true, nillable = true) private String rejectDescription = null;
    @XmlElement(required = true, nillable = true) private String rejectInternalComment = null;

    @XmlElement(required = true, nillable = true) private StudentAcceptance acceptance = null;
    @XmlElement(required = true, nillable = true) private StudentAcceptanceConfirmation travelInformation = null;

    @XmlElement(required = true, nillable = true) private DateTime nominatedAt = null;

    /**
     * Files are attached to an Application as a List, meaning that it is
     * possible to have an arbitrary number of Files as part of the Application.
     */
    @XmlElement(required = true, nillable = true) private List<File> attachments = new ArrayList<>(0);

    @XmlElement(required = true, nillable = true) private DateTime modified = null;
    @XmlElement(required = true, nillable = true) private DateTime created = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public StudentApplication() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Copy Constructor.
     *
     * @param studentApplication StudentApplication Object to copy
     */
    public StudentApplication(final StudentApplication studentApplication) {
        if (studentApplication != null) {
            applicationId = studentApplication.applicationId;
            offerId = studentApplication.offerId;
            offerState = studentApplication.offerState;
            student = new Student(studentApplication.student);
            status = studentApplication.status;
            homeAddress = new Address(studentApplication.homeAddress);
            email = studentApplication.email;
            phoneNumber = studentApplication.phoneNumber;
            addressDuringTerms = new Address(studentApplication.addressDuringTerms);
            dateOfBirth = studentApplication.dateOfBirth;
            university = studentApplication.university;
            placeOfBirth = studentApplication.placeOfBirth;
            nationality = (studentApplication.nationality != null) ? new Country(studentApplication.nationality) : null;
            gender = studentApplication.gender;
            completedYearsOfStudy = studentApplication.completedYearsOfStudy;
            totalYearsOfStudy = studentApplication.totalYearsOfStudy;
            lodgingByIaeste = studentApplication.lodgingByIaeste;
            language1 = studentApplication.language1;
            language1Level = studentApplication.language1Level;
            language2 = studentApplication.language2;
            language2Level = studentApplication.language2Level;
            language3 = studentApplication.language3;
            language3Level = studentApplication.language3Level;
            available = studentApplication.available;
            fieldOfStudies = studentApplication.fieldOfStudies;
            specializations = studentApplication.specializations;
            passportNumber = studentApplication.passportNumber;
            passportPlaceOfIssue = studentApplication.passportPlaceOfIssue;
            passportValidUntil = studentApplication.passportValidUntil;
            rejectByEmployerReason = studentApplication.rejectByEmployerReason;
            rejectDescription = studentApplication.rejectDescription;
            rejectInternalComment = studentApplication.rejectInternalComment;
            acceptance = new StudentAcceptance(studentApplication.acceptance);
            travelInformation = new StudentAcceptanceConfirmation(studentApplication.travelInformation);
            nominatedAt = studentApplication.nominatedAt;
            attachments = studentApplication.attachments;
            modified = studentApplication.modified;
            created = studentApplication.created;
        }
    }

    // TODO add a copy of student data

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setApplicationId(final String applicationId) throws IllegalArgumentException {
        ensureValidId("applicationId", applicationId);
        this.applicationId = applicationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setOfferId(final String offerId) throws IllegalArgumentException {
        ensureNotNullAndValidId("offerId", offerId);
        this.offerId = offerId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferState(final OfferState offerState) {
        this.offerState = offerState;
    }

    public OfferState getOfferState() {
        return offerState;
    }

    public void setStudent(final Student student) throws IllegalArgumentException {
        ensureNotNullAndVerifiable("student", student);
        this.student = new Student(student);
    }

    public Student getStudent() {
        return new Student(student);
    }

    public void setStatus(final ApplicationStatus status) throws IllegalArgumentException {
        ensureNotNull("status", status);
        this.status = status;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setHomeAddress(final Address homeAddress) throws IllegalArgumentException {
        ensureVerifiable("homeAddress", homeAddress);
        this.homeAddress = new Address(homeAddress);
    }

    public Address getHomeAddress() {
        return new Address(homeAddress);
    }

    public void setEmail(final String email) throws IllegalArgumentException {
        ensureNotTooLong("email", email, FIELD_LENGTH);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(final String phoneNumber) throws IllegalArgumentException {
        ensureNotTooLong("phoneNumber", phoneNumber, 25);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddressDuringTerms(final Address addressDuringTerms) throws IllegalArgumentException {
        // The Address during term should not be mandatory, since there are
        // people who stay "at home" during term.
        ensureVerifiable("addressDuringTerms", addressDuringTerms);
        this.addressDuringTerms = new Address(addressDuringTerms);
    }

    public Address getAddressDuringTerms() {
        return new Address(addressDuringTerms);
    }

    public void setDateOfBirth(final Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setUniversity(final String university) throws IllegalArgumentException {
        ensureNotTooLong("university", university, FIELD_LENGTH);
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setPlaceOfBirth(final String placeOfBirth) throws IllegalArgumentException {
        ensureNotTooLong("placeOfBirth", placeOfBirth, FIELD_LENGTH);
        this.placeOfBirth = placeOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    /**
     * <p>Sets the Nationality of the student.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the value
     * is not a verifiable Object.</p>
     *
     * @param nationality Student Nationality
     * @throws IllegalArgumentException if not verifiable
     */
    public void setNationality(final Country nationality) throws IllegalArgumentException {
        ensureVerifiable("nationality", nationality);
        this.nationality = nationality;
    }

    public Country getNationality() {
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
        this.available = available;
    }

    public DatePeriod getAvailable() {
        return available;
    }

    public void setFieldOfStudies(final Set<FieldOfStudy> fieldOfStudies) throws IllegalArgumentException {
        ensureNotTooLong("fieldOfStudies", fieldOfStudies, IWSExchangeConstants.MAX_OFFER_FIELDS_OF_STUDY);
        this.fieldOfStudies = fieldOfStudies;
    }

    public Set<FieldOfStudy> getFieldOfStudies() {
        return fieldOfStudies;
    }

    public void setSpecializations(final List<String> specializations) throws IllegalArgumentException {
        ensureNotNullOrTooLong("specializations", specializations, IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS);
        this.specializations = specializations;
    }

    public List<String> getSpecializations() {
        return specializations;
    }

    public void setPassportNumber(final String passportNumber) throws IllegalArgumentException {
        ensureNotTooLong("passportNumber", passportNumber, FIELD_LENGTH);
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportPlaceOfIssue(final String passportPlaceOfIssue) throws IllegalArgumentException {
        ensureNotTooLong("passportPlaceOfIssue", passportPlaceOfIssue, FIELD_LENGTH);
        this.passportPlaceOfIssue = passportPlaceOfIssue;
    }

    public String getPassportPlaceOfIssue() {
        return passportPlaceOfIssue;
    }

    public void setPassportValidUntil(final String passportValidUntil) throws IllegalArgumentException {
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
    public void setAttachments(final List<File> attachments) throws IllegalArgumentException {
        ensureNotNull("attachments", attachments);
        this.attachments = attachments;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public void setModified(final DateTime modified) {
        this.modified = modified;
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

        isNotNull(validation, "offerId", offerId);
        isNotNull(validation, "student", student);

        return validation;
    }
}
