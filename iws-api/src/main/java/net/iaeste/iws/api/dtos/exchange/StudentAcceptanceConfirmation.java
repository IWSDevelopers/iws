package net.iaeste.iws.api.dtos.exchange;

import net.iaeste.iws.api.enums.TransportationType;
import net.iaeste.iws.api.util.Date;

/**
 * Contains a students travel information (N5b) for an application
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class StudentAcceptanceConfirmation {

    // TODO implement

    private String applicationId;

    private Date departure;
    private TransportationType transportationType;

    private String departureFrom;
    private String transportNumber;

    private Date arrivalDateTime;
    private String phoneNumberDuringTravel;

    private Date lodgingRequiredFrom;
    private Date lodgingRequiredTo;

    private String otherInformation; // allow up to 5000 characters

    private String insuranceCompany;
    private String insuranceReceiptNumber;

}