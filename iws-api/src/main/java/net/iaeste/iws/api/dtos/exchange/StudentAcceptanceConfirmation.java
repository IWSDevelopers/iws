/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.StudentAcceptanceConfirmation
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
import net.iaeste.iws.api.enums.exchange.TransportationType;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains a students travel information (N5b) for an application
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studentAcceptanceConfirmation", propOrder = { "applicationId", "departure", "transportationType", "departureFrom", "transportNumber", "arrivalDateTime", "phoneNumberDuringTravel", "lodgingRequiredFrom", "lodgingRequiredTo", "otherInformation", "insuranceCompany", "insuranceReceiptNumber" })
public final class StudentAcceptanceConfirmation extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private String applicationId = null;
    @XmlElement(required = true, nillable = true) private Date departure = null;
    @XmlElement(required = true, nillable = true) private TransportationType transportationType = null;
    @XmlElement(required = true, nillable = true) private String departureFrom = null;
    @XmlElement(required = true, nillable = true) private String transportNumber = null;
    @XmlElement(required = true, nillable = true) private DateTime arrivalDateTime = null;
    @XmlElement(required = true, nillable = true) private String phoneNumberDuringTravel = null;
    @XmlElement(required = true, nillable = true) private Date lodgingRequiredFrom = null;
    @XmlElement(required = true, nillable = true) private Date lodgingRequiredTo = null;
    @XmlElement(required = true, nillable = true) private String otherInformation = null; // allow up to 5000 characters
    @XmlElement(required = true, nillable = true) private String insuranceCompany = null;
    @XmlElement(required = true, nillable = true) private String insuranceReceiptNumber = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public StudentAcceptanceConfirmation() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Copy Constructor.
     *
     * @param confirmation StudentAcceptanceConfirmation Object to copy
     */
    public StudentAcceptanceConfirmation(final StudentAcceptanceConfirmation confirmation) {
        if (confirmation != null) {
            applicationId = confirmation.applicationId;
            departure = confirmation.departure;
            transportationType = confirmation.transportationType;
            departureFrom = confirmation.departureFrom;
            transportNumber = confirmation.transportNumber;
            arrivalDateTime = confirmation.arrivalDateTime;
            phoneNumberDuringTravel = confirmation.phoneNumberDuringTravel;
            lodgingRequiredFrom = confirmation.lodgingRequiredFrom;
            lodgingRequiredTo = confirmation.lodgingRequiredTo;
            otherInformation = confirmation.otherInformation;
            insuranceCompany = confirmation.insuranceCompany;
            insuranceReceiptNumber = confirmation.insuranceReceiptNumber;
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

    public void setDeparture(final Date departure) {
        ensureNotNull("departure", departure);
        this.departure = departure;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setTransportationType(final TransportationType transportationType) {
        ensureNotNull("transportationType", transportationType);
        this.transportationType = transportationType;
    }

    public TransportationType getTransportationType() {
        return transportationType;
    }

    public void setDepartureFrom(final String departureFrom) {
        ensureNotNull("departureFrom", departureFrom);
        this.departureFrom = departureFrom;
    }

    public String getDepartureFrom() {
        return departureFrom;
    }

    public void setTransportNumber(final String transportNumber) {
        ensureNotNull("transportNumber", transportNumber);
        this.transportNumber = transportNumber;
    }

    public String getTransportNumber() {
        return transportNumber;
    }

    public void setArrivalDateTime(final DateTime arrivalDateTime) {
        ensureNotNull("arrivalDateTime", arrivalDateTime);
        this.arrivalDateTime = arrivalDateTime;
    }

    public DateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setPhoneNumberDuringTravel(final String phoneNumberDuringTravel) {
        ensureNotNull("phoneNumberDuringTravel", phoneNumberDuringTravel);
        this.phoneNumberDuringTravel = phoneNumberDuringTravel;
    }

    public String getPhoneNumberDuringTravel() {
        return phoneNumberDuringTravel;
    }

    public void setLodgingRequiredFrom(final Date lodgingRequiredFrom) {
        ensureNotNull("lodgingRequiredFrom", lodgingRequiredFrom);
        this.lodgingRequiredFrom = lodgingRequiredFrom;
    }

    public Date getLodgingRequiredFrom() {
        return lodgingRequiredFrom;
    }

    public void setLodgingRequiredTo(final Date lodgingRequiredTo) {
        ensureNotNull("lodgingRequiredTo", lodgingRequiredTo);
        this.lodgingRequiredTo = lodgingRequiredTo;
    }

    public Date getLodgingRequiredTo() {
        return lodgingRequiredTo;
    }

    public void setOtherInformation(final String otherInformation) {
        ensureNotNullOrTooLong("otherInformation", otherInformation, 5000);
        this.otherInformation = otherInformation;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setInsuranceCompany(final String insuranceCompany) {
        ensureNotNull("insuranceCompany", insuranceCompany);
        this.insuranceCompany = insuranceCompany;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceReceiptNumber(final String insuranceReceiptNumber) {
        ensureNotNull("insuranceReceiptNumber", insuranceReceiptNumber);
        this.insuranceReceiptNumber = insuranceReceiptNumber;
    }

    public String getInsuranceReceiptNumber() {
        return insuranceReceiptNumber;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        return new HashMap<>(0);
    }
}
