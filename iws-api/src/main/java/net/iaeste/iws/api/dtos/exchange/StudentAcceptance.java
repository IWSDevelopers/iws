/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.StudentAcceptance
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
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains acceptance information (N5a) for an application.
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studentAcceptance", propOrder = { "applicationId", "firstWorkingDay", "workingPlace", "contactPerson", "contactPersonEmail", "contactPersonPhone", "confirmedPeriod", "additionalInformation" })
public final class StudentAcceptance extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private String applicationId = null;
    @XmlElement(required = true, nillable = true) private Date firstWorkingDay = null;
    // is often different than the company address on the offer
    @XmlElement(required = true, nillable = true) private String workingPlace = null;
    @XmlElement(required = true, nillable = true) private String contactPerson = null;
    @XmlElement(required = true, nillable = true) private String contactPersonEmail = null;
    @XmlElement(required = true, nillable = true) private String contactPersonPhone = null;
    @XmlElement(required = true, nillable = true) private DatePeriod confirmedPeriod = null;
    // allow up to 5000 characters
    @XmlElement(required = true, nillable = true) private String additionalInformation = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public StudentAcceptance() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Copy Constructor.
     *
     * @param studentAcceptance StudentAcceptance Object to copy
     */
    public StudentAcceptance(final StudentAcceptance studentAcceptance) {
        if (studentAcceptance != null) {
            applicationId = studentAcceptance.applicationId;
            firstWorkingDay = studentAcceptance.firstWorkingDay;
            workingPlace = studentAcceptance.workingPlace;
            contactPerson = studentAcceptance.contactPerson;
            contactPersonEmail = studentAcceptance.contactPersonEmail;
            contactPersonPhone = studentAcceptance.contactPersonPhone;
            confirmedPeriod = studentAcceptance.confirmedPeriod;
            additionalInformation = studentAcceptance.additionalInformation;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Application Id, which is the key for this Object. If the Id
     * is undefined, i.e. null, then the system will process it as a new
     * Application, causing errors if it already exists. Otherwise, if it is
     * set, the system will process it as if it is an existing Object, causing
     * problems if it doesn't exist.</p>
     *
     * <p>The value must be a valid Id, otherwise the method will throw an
     * {@code IllegalArgumentException}.</p>
     *
     * @param applicationId Application Id
     */
    public void setApplicationId(final String applicationId) throws IllegalArgumentException {
        ensureValidId("applicationId", applicationId);
        this.applicationId = applicationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setFirstWorkingDay(final Date firstWorkingDay) {
        this.firstWorkingDay = firstWorkingDay;
    }

    public Date getFirstWorkingDay() {
        return firstWorkingDay;
    }

    public void setWorkingPlace(final String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setContactPerson(final String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPersonEmail(final String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonPhone(final String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setConfirmedPeriod(final DatePeriod confirmedPeriod) {
        this.confirmedPeriod = new DatePeriod(confirmedPeriod);
    }

    public DatePeriod getConfirmedPeriod() {
        return new DatePeriod(confirmedPeriod);
    }

    public void setAdditionalInformation(final String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
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

        return validation;
    }
}
