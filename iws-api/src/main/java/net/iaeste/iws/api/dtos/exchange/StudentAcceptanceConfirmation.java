/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;

/**
 * Contains a students travel information (N5b) for an application
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyComplexMethod
 */
public final class StudentAcceptanceConfirmation extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String applicationId = null;
    private Date departure = null;
    private TransportationType transportationType = null;
    private String departureFrom = null;
    private String transportNumber = null;
    private Date arrivalDateTime = null;
    private String phoneNumberDuringTravel = null;
    private Date lodgingRequiredFrom = null;
    private Date lodgingRequiredTo = null;
    private String otherInformation = null; // allow up to 5000 characters
    private String insuranceCompany = null;
    private String insuranceReceiptNumber = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public StudentAcceptanceConfirmation() {
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

    public void setArrivalDateTime(final Date arrivalDateTime) {
        ensureNotNull("arrivalDateTime", arrivalDateTime);
        this.arrivalDateTime = arrivalDateTime;
    }

    public Date getArrivalDateTime() {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StudentAcceptanceConfirmation)) {
            return false;
        }

        final StudentAcceptanceConfirmation that = (StudentAcceptanceConfirmation) obj;

        if (applicationId != null ? !applicationId.equals(that.applicationId) : that.applicationId != null) {
            return false;
        }
        if (departure != null ? !departure.equals(that.departure) : that.departure != null) {
            return false;
        }
        if (transportationType != that.transportationType) {
            return false;
        }
        if (departureFrom != null ? !departureFrom.equals(that.departureFrom) : that.departureFrom != null) {
            return false;
        }
        if (transportNumber != null ? !transportNumber.equals(that.transportNumber) : that.transportNumber != null) {
            return false;
        }
        if (arrivalDateTime != null ? !arrivalDateTime.equals(that.arrivalDateTime) : that.arrivalDateTime != null) {
            return false;
        }
        if (phoneNumberDuringTravel != null ? !phoneNumberDuringTravel.equals(that.phoneNumberDuringTravel) : that.phoneNumberDuringTravel != null) {
            return false;
        }
        if (lodgingRequiredFrom != null ? !lodgingRequiredFrom.equals(that.lodgingRequiredFrom) : that.lodgingRequiredFrom != null) {
            return false;
        }
        if (lodgingRequiredTo != null ? !lodgingRequiredTo.equals(that.lodgingRequiredTo) : that.lodgingRequiredTo != null) {
            return false;
        }
        if (otherInformation != null ? !otherInformation.equals(that.otherInformation) : that.otherInformation != null) {
            return false;
        }
        if (insuranceCompany != null ? !insuranceCompany.equals(that.insuranceCompany) : that.insuranceCompany != null) {
            return false;
        }

        return !(insuranceReceiptNumber != null ? !insuranceReceiptNumber.equals(that.insuranceReceiptNumber) : that.insuranceReceiptNumber != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (applicationId != null ? applicationId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (departure != null ? departure.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (transportationType != null ? transportationType.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (departureFrom != null ? departureFrom.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (transportNumber != null ? transportNumber.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (arrivalDateTime != null ? arrivalDateTime.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (phoneNumberDuringTravel != null ? phoneNumberDuringTravel.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (lodgingRequiredFrom != null ? lodgingRequiredFrom.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (lodgingRequiredTo != null ? lodgingRequiredTo.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (otherInformation != null ? otherInformation.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (insuranceCompany != null ? insuranceCompany.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (insuranceReceiptNumber != null ? insuranceReceiptNumber.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "StudentAcceptanceConfirmation{" +
                "applicationId='" + applicationId + '\'' +
                ", departure=" + departure +
                ", transportationType=" + transportationType +
                ", departureFrom='" + departureFrom + '\'' +
                ", transportNumber='" + transportNumber + '\'' +
                ", arrivalDateTime=" + arrivalDateTime +
                ", phoneNumberDuringTravel='" + phoneNumberDuringTravel + '\'' +
                ", lodgingRequiredFrom=" + lodgingRequiredFrom +
                ", lodgingRequiredTo=" + lodgingRequiredTo +
                ", otherInformation='" + otherInformation + '\'' +
                ", insuranceCompany='" + insuranceCompany + '\'' +
                ", insuranceReceiptNumber='" + insuranceReceiptNumber + '\'' +
                '}';
    }
}
