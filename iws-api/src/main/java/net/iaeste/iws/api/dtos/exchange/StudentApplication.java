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
import net.iaeste.iws.api.util.Verifications;
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
public final class StudentApplication extends Verifications {

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

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setApplicationId(final String applicationId) {
        ensureValidId("applicationId", applicationId);
        this.applicationId = applicationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setOfferId(final String offerId) {
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

    public void setStudent(final Student student) {
        ensureNotNullAndVerifiable("student", student);
        this.student = new Student(student);
    }

    public Student getStudent() {
        return new Student(student);
    }

    public void setStatus(final ApplicationStatus status) {
        ensureNotNull("status", status);
        this.status = status;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setHomeAddress(final Address homeAddress) {
        ensureVerifiable("homeAddress", homeAddress);
        this.homeAddress = new Address(homeAddress);
    }

    public Address getHomeAddress() {
        return new Address(homeAddress);
    }

    public void setEmail(final String email) {
        ensureNotTooLong("email", email, FIELD_LENGTH);
        this.email = email;
    }

    public String getEmail() {
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
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
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

    /**
     * <p>Sets the Nationality of the student.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the value
     * is not a verifiable Object.</p>
     *
     * @param nationality Student Nationality
     * @throws IllegalArgumentException if not verifiable
     */
    public void setNationality(final Country nationality) {
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

    public void setFieldOfStudies(final Set<FieldOfStudy> fieldOfStudies) {
        ensureNotTooLong("fieldOfStudies", fieldOfStudies, IWSExchangeConstants.MAX_OFFER_FIELDS_OF_STUDY);
        this.fieldOfStudies = immutableSet(fieldOfStudies);
    }

    public Set<FieldOfStudy> getFieldOfStudies() {
        return immutableSet(fieldOfStudies);
    }

    public void setSpecializations(final List<String> specializations) {
        ensureNotNullOrTooLong("specializations", specializations, IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS);
        this.specializations = immutableList(specializations);
    }

    public List<String> getSpecializations() {
        return immutableList(specializations);
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
    public void setAttachments(final List<File> attachments) {
        ensureNotNull("attachments", attachments);
        this.attachments = immutableList(attachments);
    }

    public List<File> getAttachments() {
        return immutableList(attachments);
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
