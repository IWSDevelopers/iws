package net.iaeste.iws.api.dtos.exchange;

import net.iaeste.iws.api.enums.exchange.TransportationType;
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

}
